package com.small.cell.server.util;

import java.util.ArrayList;
import java.util.List;

import com.small.cell.server.pojo.Tlv;

public class TlvTools {

	public static List<Tlv> unpack(String tlv) {
		int current = 0;// �������ݱ�ǩ
		int lenValue = 0;// L��ֵ
		int tagLen = 0;// tag�ĳ���
		List<Tlv> tlvList = new ArrayList<Tlv>();

		// ��string����ת����byte
		byte[] data = ByteAndStr16.HexString2Bytes(tlv);
		while (current < data.length) {
			Tlv tlvData = new Tlv();
			tagLen = Tlv.TlvTagLength;
			if (current + tagLen > data.length) {
				System.out.println("��Ϣ��1������TLV��ʽ");
				break;
			}
			tlvData.setType(MyUtils.subBytes(data, current, tagLen));
			current += tagLen;
			if (current + Tlv.TlvLenLength > data.length) {
				System.out.println("��Ϣ��2������TLV��ʽ");
				break;
			}
			lenValue = MyUtils.byteToInteger(MyUtils.subBytes(data, current,
					Tlv.TlvLenLength));
			tlvData.setLen(MyUtils.subBytes(data, current, Tlv.TlvLenLength));
			current += Tlv.TlvLenLength;
			if (current + lenValue > data.length) {
				System.out.println("��Ϣ��3������TLV��ʽ");
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