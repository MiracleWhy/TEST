package com.uton.carsokApi.pay.lakala.util;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DES  implements SecurityOperator{

    private byte[] desKey;

    public DES(String desKey) {
        this.desKey = desKey.getBytes();
    }

    public DES() {
	}

	public byte[] desEncrypt(byte[] plainText) throws Exception {
       return desEncrypt(plainText,this.desKey);
    }
    public byte[] desEncrypt(byte[] plainText,byte[] desKey) throws Exception {
        SecureRandom sr = new SecureRandom();
        byte rawKeyData[] = desKey;
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, sr);
        byte data[] = plainText;
        byte encryptedData[] = cipher.doFinal(data);
        return encryptedData;
    }
    public byte[] desDecrypt(byte[] encryptText) throws Exception {
    	return desDecrypt(encryptText,this.desKey);
    }
    public byte[] desDecrypt(byte[] encryptText,byte[] desKey) throws Exception {
        SecureRandom sr = new SecureRandom();
        byte rawKeyData[] =desKey;
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, sr);
        byte encryptedData[] = encryptText;
        byte decryptedData[] = cipher.doFinal(encryptedData);
        return decryptedData;
    }
    public String encrypt(String input) throws Exception {
        return Base64.encode(desEncrypt(input.getBytes("UTF-8")));
    }

    public String decrypt(String input) throws Exception {
        byte[] result = Base64.decode(input);
        return new String(desDecrypt(result),"UTF-8");
    }

    public static String base64Encode(byte[] s) {
        if (s == null)
            return null;
        BASE64Encoder b = new sun.misc.BASE64Encoder();
        return b.encode(s);
    }

    public static byte[] base64Decode(String s) throws IOException {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(s);
        return b;
    }

    public static void main(String[] args) throws Exception {
        String key = "F83DB6BF6B291FF19E944FEAAB64B9BC";
        String input = "111111";
        DES crypt = new DES(key);
        System.out.println("Encode:" + crypt.encrypt(input));
        System.out.println("Decode:" + crypt.decrypt(crypt.encrypt(input)));
//        System.out.println("Decode:" + crypt.decrypt("50A28C85A5CEE765"));
    }

	public String decrypt(String key, String sSrc) throws Exception {
		 byte[] result = Base64.decode(sSrc);
	       return new String(desDecrypt(result,sSrc.getBytes()),"UTF-8");
	}

	public String encrypt(String key, String sSrc) throws Exception {
		byte[] result = Base64.decode(sSrc);
		return new String( desEncrypt(result,key.getBytes()),"utf-8");
	}

	public boolean verifySign(String message, String signStr, String key)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public String sign(String message, String key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
