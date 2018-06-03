package com.small.cell.server.pojo;

import java.io.Serializable;

public class PackageData implements Serializable {

	/**
	 * 12byte ��Ϣͷ
	 */
	protected MsgHeader msgHeader;

	/*
	 * ��Ϣ���ֽ�����
	 */
	protected String msgBodyBytes;

	public final static int msgHeaderLength = 12;

	public MsgHeader getMsgHeader() {
		return msgHeader;
	}

	public String getMsgBodyBytes() {
		return msgBodyBytes;
	}

	public void setMsgBodyBytes(String msgBodyBytes) {
		this.msgBodyBytes = msgBodyBytes;
	}

	public void setMsgHeader(MsgHeader msgHeader) {
		this.msgHeader = msgHeader;
	}

	// ��Ϣͷ
	public static class MsgHeader implements Serializable {
		// ֡��ʶ
		// byte[0-1]
		protected String msgFrameFlag;
		// ������
		// byte[2-3]
		protected String msgTypeCode;
		// �汾��
		// byte[4-5]
		protected String msgVersion;
		// ���к�
		// byte[6-9]
		protected String msgSeqNum;
		// ���ĳ���
		// byte[10-11]
		protected String msgLength;

		public String getMsgFrameFlag() {
			return msgFrameFlag;
		}

		public void setMsgFrameFlag(String msgFrameFlag) {
			this.msgFrameFlag = msgFrameFlag;
		}

		public String getMsgTypeCode() {
			return msgTypeCode;
		}

		public void setMsgTypeCode(String msgTypeCode) {
			this.msgTypeCode = msgTypeCode;
		}

		public String getMsgVersion() {
			return msgVersion;
		}

		public void setMsgVersion(String msgVersion) {
			this.msgVersion = msgVersion;
		}

		public String getMsgSeqNum() {
			return msgSeqNum;
		}

		public void setMsgSeqNum(String msgSeqNum) {
			this.msgSeqNum = msgSeqNum;
		}

		public String getMsgLength() {
			return msgLength;
		}

		public void setMsgLength(String msgLength) {
			this.msgLength = msgLength;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return String.format("%s%s%s%s%s", msgFrameFlag, msgTypeCode,
					msgVersion, msgSeqNum, msgLength);
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s%s", msgHeader, msgBodyBytes);
	}

}