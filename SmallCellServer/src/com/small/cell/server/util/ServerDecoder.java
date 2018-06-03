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
			in.get(sizeBytes, 0, PackageData.msgHeaderLength);// ��ȡ��Ϣͷ
			int size = (int) MyUtils.parseIntFromBytes(sizeBytes, 10, 2);
			if (size - PackageData.msgHeaderLength > in.remaining()) {// �����Ϣ���ݲ����������ã��൱�ڲ���ȡsize
				System.out.println("�����е���Ϣ�峤�Ȳ���");
				in.reset();
				LOG.info("�����е���Ϣ�峤�Ȳ���");
				return false;// ������������ݣ���ƴ�ճ���������
			} else {
				in.reset();
				byte[] bytes = new byte[size];
				in.get(bytes, 0, size);
				out.write(bytes); // ���ͳ�ȥ ���������
				if (in.remaining() > 0) {// �����ȡ���ݺ�ճ�˰������ø������ض� һ�Σ�������һ�ν���
					return true;
				}
			}
		}
		return false;
	}

}
