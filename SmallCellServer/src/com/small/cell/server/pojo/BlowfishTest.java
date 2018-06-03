package com.small.cell.server.pojo;

public class BlowfishTest {
	 public static void main(String[] args){
	  String keyString = "6t#$z@_321";
	
	  BlowfishUtil crypt = new BlowfishUtil(keyString);
	
	     System.out.println(crypt.decrypt("CD 01 B4 78 F3 1F 31 43 4A 14 5F BB 7B FF CF 4F 9F 30 11 2A 22 A8 99 AD 0F A8 EA 65 F1 B0 21 38 64 71 6F 67 D3 22 15 CD 45 85 F0 61 98 4F F2 56 3F CA 96 D0 49 C0 88 BF 7E 20 ED E6 84 02 EF 26 49 19 A0 97 F6 0D A6 F1 79 25 04 A5 8D 54 85 83 0F 0D 28 15 58 06 D4 F9 CC A9 5B 30 57 53 14 8B 23 BE 3A 00 1E 1B A9 35 49 F4 A0 50 B3 13 35 F4 5A 00 A9 5E AC 45 53 47 E2 C6 4F E8 97 69 58 9C FE 67 62 00 7B 24 E4 4B 75 3D 40 B9 A9 3C 79 8A CE 41 15 BA 3B 4D 89 58"));
	 }
	}