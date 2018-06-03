package com.small.cell.server.adapter;

import java.io.IOException;
import java.util.List;

import com.small.cell.server.pojo.General;
import com.small.cell.server.pojo.PackageData;
import com.small.cell.server.pojo.Para;
import com.small.cell.server.pojo.Res;
import com.small.cell.server.pojo.Tlv;
import com.small.cell.server.pojo.TypeCode;

import com.small.cell.server.util.ByteAndStr16;
import com.small.cell.server.util.MyExeUtil;
import com.small.cell.server.util.MyUtils;
import com.small.cell.server.util.TlvTools;

public class AuthRequestAdapter {
	public static PackageData handler(PackageData packageData) {

		List<Tlv> tlvList = TlvTools.unpack(packageData.getMsgBodyBytes());
		Tlv macTlv = tlvList.get(0);
		Tlv resTlv = new Tlv();
		resTlv.setType(ByteAndStr16.HexString2Bytes(MyUtils
				.IntegerToString16For4(General.Res)));
		resTlv.setLen(ByteAndStr16.HexString2Bytes(MyUtils
				.IntegerToString16For4(Res.ResLen)));
		if (!MyUtils.valMac(ByteAndStr16.Bytes2HexString(macTlv.getValue())))
			resTlv.setValue(ByteAndStr16.HexString2Bytes(MyUtils
					.IntegerToString16For2(Res.InvalidMac)));
		// else if(){

		// }else if(){

		// }
		else

			resTlv.setValue(ByteAndStr16.HexString2Bytes(MyUtils
					.IntegerToString16For2(Res.SUCCESS)));

		String body = null;
		System.out.println("====s=s==s======"+String.format(
					"%s%s", macTlv, resTlv));
		try {
			body = MyExeUtil.getExeRes(Para.BlowFishMode_1, String.format(
					"%s%s", macTlv, resTlv));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		packageData.setMsgBodyBytes(body);
		packageData.getMsgHeader().setMsgTypeCode(
				TypeCode.AuthResponse.getCode());
		packageData.getMsgHeader().setMsgLength(
				MyUtils.IntegerToString16For4(PackageData.msgHeaderLength
						+ ByteAndStr16.HexString2Bytes(body).length));

		return packageData;

	}
}
