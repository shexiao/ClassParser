package com.compiler.util.test;

import org.junit.Test;

import com.compiler.util.TransformUtil;

public class TransformUtilTest {
	
	@Test
	public void testBytesToInt() {
		int i = (127 * 256 * 256 * 256 );
		byte[] bytes = new byte[]{(byte)0x7f, (byte)0x00, (byte)0x00, (byte)0x00};
		int result;
		try {
			result = TransformUtil.bytesToInt(bytes);
			//System.out.println(result);
			org.junit.Assert.assertArrayEquals(new int[]{i}, new int[]{result});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testBytesToLong() {
		long i = -(128l * 256l * 256l * 256l + 256l * 256l * 256l * 256l);
		byte[] high_bytes = new byte[] {(byte)0xff, (byte)0x0ff, (byte)0x0ff, (byte)0xfe};
		byte[] low_bytes = new byte[] {(byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00};
		try {
			long result = TransformUtil.bytesToLong(high_bytes, low_bytes);
			System.out.println(i);
			System.out.println(result);
			org.junit.Assert.assertArrayEquals(new long[]{i}, new long[]{result});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
