package com.compiler.model.constantpool;

import java.io.InputStream;

import com.compiler.util.TransformUtil;

public class ConstantIntegerOrFloatInfo extends ConstantPoolInfo {
	private byte[] bytes;

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
	@Override
	public void parse(InputStream in, byte tag, int index) throws Exception{
		super.parse(in, tag, index);
		byte[] bb = new byte[4];
		in.read(bb, 0, 4);
		setBytes(bb);
	}
	
	@Override
	public String toString() {
		String result = "";
		try {
			if (tag == CONSTANT_INTEGER) {
				
					result = "const #" + getIndex() + " =Integer\t " + TransformUtil.bytesToInt(bytes);
			} else if (tag == CONSTANT_FLOAT) {
					result = "const #" + getIndex() + " =Float\t " + TransformUtil.bytesToFloat(bytes) + "f";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
