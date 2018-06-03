package com.small.cell.server.pojo;

public class BlowfishUtil {
	//密钥关联的key
		
		//BlowFish实现
	    private static BlowFish blowfish=null;
		
		public BlowfishUtil(String key){
			
			blowfish = new BlowFish(key); 
			
		}
		
		/**
		 * 加密 
		 */
		public  String encrypt(String plainText){
			return blowfish.encryptString(plainText);
		}
		
		/**
		 * 解密 
		 */
		public  String decrypt(String cryptText){
			return blowfish.decryptString(cryptText);
		}
		
		
	}
