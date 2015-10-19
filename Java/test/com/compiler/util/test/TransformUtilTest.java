package com.compiler.util.test;

import org.junit.Test;

import com.compiler.util.TransformUtil;

public class TransformUtilTest {
	
	@Test
	public void testBytesToInt() {
		int i = (255 * 256 * 256 );
		byte[] bytes = new byte[]{(byte)0xff, (byte)0x00, (byte)0x00};
		int result;
		try {
			result = TransformUtil.bytesToInt(bytes);
			System.out.println(result);
			org.junit.Assert.assertArrayEquals(new int[]{i}, new int[]{result});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//@Test
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
	
	//@Test
	public void testBytesToFloat() {
		byte[] bytes = new byte[]{(byte)0xc1, (byte)0x16, (byte)0x66, (byte)0x66};
		byte[] bytes1 = new byte[]{(byte)0x41, (byte)0x16, (byte)0x66, (byte)0x66};
		try {
			float result = TransformUtil.bytesToFloat(bytes);
			System.out.println(result);
			org.junit.Assert.assertArrayEquals(new float[]{-9.4f}, new float[]{result}, 0);
			result = TransformUtil.bytesToFloat(bytes1);
			System.out.println(result);
			org.junit.Assert.assertArrayEquals(new float[]{9.4f}, new float[]{result}, 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
	public void testBytesToDouble() {
		byte[] bytes0 = new byte[]{(byte)0x40, (byte)0x20, (byte)0x99, (byte)0x99};
		byte[] bytes1 = new byte[]{(byte)0x99, (byte)0x99, (byte)0x99, (byte)0x9a};
		
		byte[] bytes2 = new byte[]{(byte)0xc0, (byte)0x20, (byte)0x99, (byte)0x99};
		byte[] bytes3 = new byte[]{(byte)0x99, (byte)0x99, (byte)0x99, (byte)0x9a};
		try {
			double result = TransformUtil.bytesToDouble(bytes0, bytes1);
			System.out.println(result);
			org.junit.Assert.assertArrayEquals(new double[]{8.3d}, new double[]{result}, 0);
			
			result = TransformUtil.bytesToDouble(bytes2, bytes3);
			System.out.println(result);
			org.junit.Assert.assertArrayEquals(new double[]{-8.3d}, new double[]{result}, 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
