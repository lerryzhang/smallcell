package com.small.cell.server.util;

import java.io.IOException;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.small.cell.server.pojo.FrameFlag;
import com.small.cell.server.pojo.PackageData;
import com.small.cell.server.pojo.Para;
import com.small.cell.server.pojo.Tlv;
import com.small.cell.server.pojo.TypeCode;
import com.small.cell.server.pojo.PackageData.MsgHeader;
import com.small.cell.server.singleton.SessionMap;

public class test {

	/**
	 * @param args
	 */

	public static int[] value1 = { 80, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 14, 0, 0 };

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		SessionMap sessionMap = SessionMap.newInstance();

		
		PackageData packageData = new PackageData();
		MsgHeader msgHeader = new MsgHeader();
		msgHeader.setMsgFrameFlag(FrameFlag.Encrypt);
		msgHeader.setMsgTypeCode(TypeCode.ConfigureQueryRequest.getCode());
		msgHeader.setMsgVersion("000D");
		msgHeader.setMsgSeqNum("210000FB");
		msgHeader.setMsgLength("0016");
		packageData.setMsgHeader(msgHeader);
		String body = "000100060060B3987654";
		packageData.setMsgBodyBytes(MyExeUtil.getExeRes(Para.BlowFishMode_1, body));
		System.out.println("====="+packageData.toString());
		sessionMap.sendMessage("0060B3987654", IoBuffer.wrap(ByteAndStr16.HexString2Bytes(packageData
				.toString())));

	}

}
