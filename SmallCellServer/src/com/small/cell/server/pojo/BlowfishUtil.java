package com.small.cell.server.pojo;

public class BlowfishUtil {
	//��Կ������key
		
		//BlowFishʵ��
	    private static BlowFish blowfish=null;
		
		public BlowfishUtil(String key){
			
			blowfish = new BlowFish(key); 
			
		}
		
		/**
		 * ���� 
		 */
		public  String encrypt(String plainText){
			return blowfish.encryptString(plainText);
		}
		
		/**
		 * ���� 
		 */
		public  String decrypt(String cryptText){
			return blowfish.decryptString(cryptText);
		}
		
		
	}
