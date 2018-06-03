package com.small.cell.server.pojo;

public class Res {

	public static final int SUCCESS = 0x00;// 操作成功
	public static final int InvalidMac = 0x01;// 非法的MAC地址
	public static final int KeyVerErr = 0x02;// 密钥校验出错
	public static final int SequenceErr = 0x03;// 报文序列号出错
	public static final int InvalidFormat = 0x04;// 格式错误
	
	
	public static final int ResLen = 0x0001;// 结果长度
}
