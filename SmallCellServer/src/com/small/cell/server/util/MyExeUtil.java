package com.small.cell.server.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import com.small.cell.server.pojo.Para;

public class MyExeUtil {

	public static final int SUCCESS = 0; // 表示程序执行成功

	public static final String PATH = "C:/Users/Administrator/Desktop/Debug/Win32Project2.exe"; // 要执行的语句
	public static final String PASSWORD = "6t#$z@_321";
	//public static final String PASSWORD = "abc#$321";
	

	public static final String SUCCESS_MESSAGE = "程序执行成功！";

	public static final String ERROR_MESSAGE = "程序执行出错：";

	public static String getExeRes(int mode, String strSource)
			throws IOException, InterruptedException {

		String strRes = null;

		String str = String.format("%s %s %s %s", PATH, mode, PASSWORD,
				strSource);

		Runtime runtime = Runtime.getRuntime();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					runtime.exec(str).getInputStream()));
			String line = null;
			StringBuffer b = new StringBuffer();
			while ((line = br.readLine()) != null) {
				b.append(line + "\n");
			}
			strRes = b.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strRes;
	}
	
	
	public static void main(String args[]) throws IOException, InterruptedException{
		
		// getExeRes(1,"6777636E35505674716C6132314F5433");
		
		
		
		 System.out.println("===="+TlvTools.unpack(MyExeUtil.getExeRes(Para.BlowFishMode_2, "4FC28DA6A6E0A819712776BD0BF6A3D2767D6D23F98132C6D3FD43913AE63208CBF0CDEB7D2B032A")).get(0).getValue());
		
	}
	

}