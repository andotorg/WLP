package com.farm.core;

import java.net.NetworkInterface;
import java.util.Enumeration;

import com.farm.core.auth.util.MD5;

 
public class SequenceService {
	 
	protected static String getMD5(String message) {
		String md5 = new MD5().getMD5ofStr(message);
		return getSplitString(md5);
	}

	public static String InitKey() {
		return getMD5(getSigarSequence());
	}

	 
	protected static String getSplitString(String str) {

		return getSplitString(str.substring(0, 16), "-", 4);
	}

	 
	protected static String getSplitString(String str, String split, int length) {
		int len = str.length();
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < len; i++) {
			if (i % length == 0 && i > 0) {
				temp.append(split);
			}
			temp.append(str.charAt(i));
		}
		String[] attrs = temp.toString().split(split);
		StringBuilder finalMachineCode = new StringBuilder();
		for (String attr : attrs) {
			if (attr.length() == length) {
				finalMachineCode.append(attr).append(split);
			}
		}
		String result = finalMachineCode.toString().substring(0, finalMachineCode.toString().length() - 1);
		return result;
	}

	 
	protected static String getSigarSequence() {
		return "asdf";
	}
	     
    public static String getMacAddress() throws Exception{  
        Enumeration<NetworkInterface> ni = NetworkInterface.getNetworkInterfaces();  
          
        while(ni.hasMoreElements()){  
            NetworkInterface netI = ni.nextElement();  
              
            byte[] bytes = netI.getHardwareAddress();  
            if(netI.isUp() && netI != null && bytes != null && bytes.length == 6){  
                StringBuffer sb = new StringBuffer();  
                for(byte b:bytes){  
                     
                     sb.append(Integer.toHexString((b&240)>>4));  
                     
                     sb.append(Integer.toHexString(b&15));  
                     sb.append("-");  
                 }  
                 sb.deleteCharAt(sb.length()-1);  
                 return sb.toString().toUpperCase();   
            }  
        }  
        return null;  
    }  
	public static void main(String[] args) {
		System.out.println(SequenceService.InitKey());
	}
}