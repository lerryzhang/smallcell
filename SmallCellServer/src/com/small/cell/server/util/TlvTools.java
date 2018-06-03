package com.small.cell.server.util;

import java.util.ArrayList;
import java.util.List;

import com.small.cell.server.pojo.Tlv;

public class TlvTools {

	public static List<Tlv> unpack(String tlv) {
		int current = 0;// 遍历数据标签
		int lenValue = 0;// L的值
		int tagLen = 0;// tag的长度
		List<Tlv> tlvList = new ArrayList<Tlv>();

		// 将string数据转换成byte
		byte[] data = ByteAndStr16.HexString2Bytes(tlv);
		while (current < data.length) {
			Tlv tlvData = new Tlv();
			tagLen = Tlv.TlvTagLength;
			if (current + tagLen > data.length) {
				System.out.println("消息体1不符合TLV格式");
				break;
			}
			tlvData.setType(MyUtils.subBytes(data, current, tagLen));
			current += tagLen;
			if (current + Tlv.TlvLenLength > data.length) {
				System.out.println("消息体2不符合TLV格式");
				break;
			}
			lenValue = MyUtils.byteToInteger(MyUtils.subBytes(data, current,
					Tlv.TlvLenLength));
			tlvData.setLen(MyUtils.subBytes(data, current, Tlv.TlvLenLength));
			current += Tlv.TlvLenLength;
			if (current + lenValue > data.length) {
				System.out.println("消息体3不符合TLV格式");
				break;
			}
			byte[] temp = MyUtils.subBytes(data, current, lenValue);
			tlvData.setValue(temp);
			current += lenValue;
			tlvList.add(tlvData);
		}

		return tlvList;
	}

	public static void main(String args[]) {

		

	}
}