package com.compiler.model.constantpool;

import java.io.InputStream;

import com.compiler.util.TransformUtil;

public class ConstantClassInfo extends ConstantPoolInfo {
	private byte[] name_index;

	public byte[] getName_index() {
		return name_index;
	}

	public void setName_index(byte[] name_index) {
		this.name_index = name_index;
	}
	
	@Override
	public void parse(InputStream in, byte tag, int index) throws Exception{
		super.parse(in, tag, index);
		byte[] bb = new byte[2];
		in.read(bb, 0, 2);
		setName_index(bb);
	}
	
	@Override
	public String toString() {
		String result = "";
		try {
			result = "const #" + getIndex() + " =class\t #" + TransformUtil.bytesToInt(name_index);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
