package cn.tedu.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class WebUtils {
	private WebUtils(){}
	
	/**
	 * ����ַ�����Ϊnull����Ϊ""
	 * @param str
	 * @return boolean true-��ʾ�ַ���Ϊ��null��""
	 */
	public static boolean isNull(String str){
		return str == null || "".equals(str.trim());
	}
	
	/**
	 * ʹ��md5�����㷨���м���
	 * @param plainText
	 * @return
	 */
	public static String md5(String plainText) {
		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(
					plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("û��md5����㷨��");
		}
		String md5code = new BigInteger(1, secretBytes).toString(16);
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}

}
