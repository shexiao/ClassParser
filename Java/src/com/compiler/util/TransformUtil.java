package com.compiler.util;

public class TransformUtil {
	public static int bytesToInt(byte[] bytes) throws Exception {
		int result = 0;
		
		int len = bytes.length;
		if (len > 4) {
			throw new Exception();
		}
		
		int i = 0;
		while (i < len) {
			int offset = 8 * (len - 1 - i);
			result += ((bytes[i] << offset) & (0x0ff << offset));
			i++;
		}
		
		return result;
	}
	
	public static long bytesToLong(byte[] high_bytes, byte[] low_bytes) throws Exception {
		long result = 0l;

		int len = 4;
		if (high_bytes.length != len || low_bytes.length != len) {
			throw new Exception();
		}
		
		int i = 0;
		int offset;
		
		while (i < len) {
			offset = 8 * (len - 1 -i);
			long tmp = ((high_bytes[i] << offset) & (0x0ff << offset));
			tmp = tmp << 32;
			result += tmp;
			i++;
		}
		
		i = 0;
		while (i < len) {
			offset = 8 * (len - 1 -i);
			long tmp1 = (long)(low_bytes[i] & 0x0ff);
			result += tmp1 << offset;
			i++;
		}
		return result;
	}
	
	public static float bytesToFloat(byte[] bytes) throws Exception {
		if (bytes.length != 4) {
			throw new Exception("need 4 bytes to get one float number");
		}
		int bits = bytesToInt(bytes);
		int s = (((bits & 0x0ffffffff) >> 31)) == 0 ? 1 : -1;
		int e = ((bits & 0x0ffffffff) >> 23) & 0xff;
		int m = (e == 0) ? (bits & 0x0ffffffff & 0x7fffff) << 1 : (bits & 0xffffffff & 0x7fffff) | 0x800000;
		float f = (float)(s * m * Math.pow(2, e - 150));
		return f;
	}
	
	public static double bytesToDouble(byte[] high_bytes, byte[] low_bytes) throws Exception {
		if (high_bytes.length != 4 || low_bytes.length != 4) {
			throw new Exception("high_bytes and low_bytes all need length 4 to get one double number");
		}
		long bits = bytesToLong(high_bytes, low_bytes);
		int s = (((bits & 0x0ffffffffffffffffL) >> 63)) == 0L ? 1 : -1;
		int e = (int)(((bits & 0x0ffffffffffffffffL) >> 52) & 0x7ffL);
		long m = (e == 0) ? ((bits & 0x0ffffffffffffffffL & 0xfffffffffffffL) << 1) : ((bits & 0x0ffffffffffffffffL & 0xfffffffffffffL) | 0x10000000000000L);
		double d = (double)(s * m * Math.pow(2, e - 1075));
		return d;
	}
	
	public static char[] bytesToChars(byte[] bytes) {
		
		int len = bytes.length;
		char[] chars = new char[len];
		for (int i = 0; i < len; i++) {
			chars[i] = (char)bytes[i];
		}
		
		return chars;
	}
	
	public static String getHex(byte[] bytes) {
		String result = "";
		if (bytes == null || bytes.length == 0) return result;
		for (byte b : bytes) {
			result += String.format("%x ", b);
		}
		return result;
	}
	
	public static byte[] subBytes(byte[] bytes, int start, int len) throws Exception{
		if (start + len > bytes.length) {
			throw new Exception("out of bound");
		}
		byte[] result = new byte[len];
		int j = 0;
		for (int i = start; i < start + len; i++) {
			result[j] = bytes[i];
			j++;
		}
		return result;
	}
}
