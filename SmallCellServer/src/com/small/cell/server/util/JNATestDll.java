package com.small.cell.server.util;

import com.sun.jna.Library;  
import com.sun.jna.Native;  
  
public interface JNATestDll extends Library {  
    
    
    JNATestDll INSTANCE = (JNATestDll)Native.loadLibrary("D:\\dll\\BlowFish", JNATestDll.class);  
    
    
     int  BlowFishDecryptBin(char[]  orgBuf, int orgLen, char[] outStr, int outLen); 
     int BlowFishEncryptBin(char[]  orgBuf, int orgLen, char[] outStr, int outLen);
    
}  