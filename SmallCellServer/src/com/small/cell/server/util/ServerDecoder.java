package com.small.cell.server.util;



import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.small.cell.server.pojo.PackageData;

public class ServerDecoder extends CumulativeProtocolDecoder {
	private static Logger LOG = LoggerFactory.getLogger(ServerDecoder.class);

	@Override
	public boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		if (in.remaining() > PackageData.msgHeaderLength) {
			in.mark();
			byte[] sizeBytes = new byte[PackageData.msgHeaderLength];
			in.get(sizeBytes, 0, PackageData.msgHeaderLength);// 读取消息头
			int size = (int) MyUtils.parseIntFromBytes(sizeBytes, 10, 2);
			if (size - PackageData.msgHeaderLength > in.remaining()) {// 如果消息内容不够，则重置，相当于不读取size
				System.out.println("缓存中的消息体长度不够");
				in.reset();
				LOG.info("缓存中的消息体长度不够");
				return false;// 父类接收新数据，以拼凑成完整数据
			} else {
				in.reset();
				byte[] bytes = new byte[size];
				in.get(bytes, 0, size);
				out.write(bytes); // 发送出去 就算完成了
				if (in.remaining() > 0) {// 如果读取内容后还粘了包，就让父类再重读 一次，进行下一次解析
					return true;
				}
			}
		}
		return false;
	}

}
