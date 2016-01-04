package com.quix.aia.cn.imo.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public  class PasswordHashing {
	public static String EncryptBySHA2(String strSrc) {
		String strDes=null;
		strDes=Encrypt(strSrc,"SHA-256");
        return strDes;
    }

	//encName can set "SHA-256","SHA","MD5"....
	//SHA2 length=64
	//SHA  length=40
	//MD5  length=32
	public static String Encrypt(String strSrc,String encName) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            if (encName == null || encName.equals("")) {
                encName = "SHA-256";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }
	
	private static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

	public static void main(String a[])
	{
		String password = EncryptBySHA2("welcome1");
		System.out.println(password);
		
	}
}
