package com.smallcell.server.handler;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.small.cell.server.adapter.AlarmRequestAdapter;
import com.small.cell.server.adapter.AuthRequestAdapter;
import com.small.cell.server.adapter.EchoRequestAdapter;
import com.small.cell.server.adapter.ReportRequestAdapter;
import com.small.cell.server.pojo.FrameFlag;
import com.small.cell.server.pojo.PackageData;
import com.small.cell.server.pojo.Para;
import com.small.cell.server.pojo.TypeCode;
import com.small.cell.server.pojo.PackageData.MsgHeader;
import com.small.cell.server.pojo.Tlv;

import com.small.cell.server.singleton.SessionMap;
import com.small.cell.server.util.ByteAndStr16;
import com.small.cell.server.util.MyExeUtil;
import com.small.cell.server.util.MyUtils;

import com.small.cell.server.util.TlvTools;

/**
 * 服务器端消息处理器
 * 
 * @author mazaiting
 */
public class ServerHandler extends IoHandlerAdapter {

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		/**
		 * 自定义异常处理， 要不然异常会被“吃掉”；
		 */
		cause.printStackTrace();
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		/**
		 * 对接收到的消息报文进行解析；
		 */
		byte[] bytes = (byte[]) message;
		System.out.println("=======receive======"+ByteAndStr16.Bytes2HexString(bytes));
		PackageData packageData = new PackageData();
		MsgHeader msgHeader = new MsgHeader();
		msgHeader.setMsgFrameFlag(MyUtils.parseBcdStringFromBytes(bytes, 0, 2));
		msgHeader.setMsgTypeCode(MyUtils.parseBcdStringFromBytes(bytes, 2, 2));
		msgHeader.setMsgVersion(MyUtils.parseBcdStringFromBytes(bytes, 4, 2));
		// msgHeader.setMsgSeqNum(MyUtils.parseIntFromBytes(bytes, 6, 4));

		msgHeader.setMsgSeqNum(ByteAndStr16.Bytes2HexString(MyUtils.subBytes(
				bytes, 6, 4)));

		// msgHeader.setMsgLength(MyUtils.parseIntFromBytes(bytes, 10, 2));

		msgHeader.setMsgLength(ByteAndStr16.Bytes2HexString(MyUtils.subBytes(
				bytes, 10, 2)));
		packageData.setMsgHeader(msgHeader);
		if (FrameFlag.Encrypt.equals(msgHeader.getMsgFrameFlag())) {
			packageData.setMsgBodyBytes(MyExeUtil.getExeRes(
					Para.BlowFishMode_2, ByteAndStr16.Bytes2HexString(MyUtils
							.subBytes(bytes, PackageData.msgHeaderLength,
									Integer.valueOf(msgHeader.getMsgLength(),
											16)
											- PackageData.msgHeaderLength))));
		} else if (FrameFlag.NoEncrypt.equals(msgHeader.getMsgFrameFlag())) {

			packageData.setMsgBodyBytes(ByteAndStr16.Bytes2HexString(MyUtils
					.subBytes(bytes, PackageData.msgHeaderLength, Integer
							.valueOf(msgHeader.getMsgLength(), 16)

							- PackageData.msgHeaderLength)));
		}

		switch (TypeCode.getByValue(msgHeader.getMsgTypeCode())) {
		case AuthRequest:
			packageData = AuthRequestAdapter.handler(packageData);
			break;
		case ReportRequest:
			packageData = ReportRequestAdapter.handler(packageData);
			break;
		case ControlResponse:
			break;
		case ConfigureUpdateResponse:
			break;
		case ConfigureQueryResponse:
			System.out.println("chauxn qingqiu meidao");
			break;
		case AlarmRequest:
			packageData =AlarmRequestAdapter.handler(packageData);
			break;
		case EchoRequest:
			packageData = EchoRequestAdapter.handler(packageData);
			break;
		}
		// 将客户端的连接请求保存到session当中

		SessionMap sessionMap = SessionMap.newInstance();

		// MAC作为唯一标识码
		String mac=ByteAndStr16.Bytes2HexString(TlvTools.unpack(
				MyExeUtil.getExeRes(Para.BlowFishMode_2, packageData
						.getMsgBodyBytes())).get(0).getValue());
		sessionMap.addSession(mac, session);
		System.out.println("==="+sessionMap.getSize()+"==mac=="+mac+"========send===="+packageData.toString());
		session.write(IoBuffer.wrap(ByteAndStr16.HexString2Bytes(packageData
				.toString())));

	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		/**
		 * 当Session处于IDLE状态的时候，输出空闲状态次数；
		 */
		System.out.println("IDLE:" + session.getIdleCount(status));
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("会话关闭");
	}

	@Override
	public void messageSent(IoSession iosession, Object obj) throws Exception {
		System.out.println("服务端消息发送");
		super.messageSent(iosession, obj);
	}

	@Override
	public void sessionCreated(IoSession iosession) throws Exception {
		System.out.println("会话创建");
		super.sessionCreated(iosession);
	}

	@Override
	public void sessionOpened(IoSession iosession) throws Exception {
		System.out.println("会话打开");
		super.sessionOpened(iosession);
	}

}
