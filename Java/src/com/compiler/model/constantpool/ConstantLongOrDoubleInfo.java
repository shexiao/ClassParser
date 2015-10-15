package com.compiler.model.constantpool;

import java.io.InputStream;

import com.compiler.util.TransformUtil;

public class ConstantLongOrDoubleInfo extends ConstantPoolInfo {
	private byte[] high_bytes;
	private byte[] low_bytes;
	public byte[] getHigh_bytes() {
		return high_bytes;
	}
	public void setHigh_bytes(byte[] high_bytes) {
		this.high_bytes = high_bytes;
	}
	public byte[] getLow_bytes() {
		return low_bytes;
	}
	public void setLow_bytes(byte[] low_bytes) {
		this.low_bytes = low_bytes;
	}
	
	@Override
	public void parse(InputStream in, byte tag, int index) throws Exception{
		super.parse(in, tag, index);
		byte[] bb = new byte[4];
		in.read(bb, 0, 4);
		setHigh_bytes(bb);
		bb = new byte[4];
		in.read(bb, 0, 4);
		setLow_bytes(bb);
	}
	
	@Override
	public String toString() {
		String result = "";
		try {
			if (tag == ConstantPoolInfo.CONSTANT_LONG) {
				
					result = "const #" + getIndex() + " =Long\t " + TransformUtil.bytesToLong(high_bytes, low_bytes) + "L";
			} else if (tag == ConstantPoolInfo.CONSTANT_DOUBLE) {
				result = "const #" + getIndex() + " =Double\t " + TransformUtil.bytesToDouble(high_bytes, low_bytes) + "d";
			}
		} catch (Exception e) {
			
		}
		return result;
	}
}
