package com.compiler.model.constantpool;

import java.io.InputStream;

import com.compiler.util.TransformUtil;

public class ConstantStringInfo extends ConstantPoolInfo {
	private byte[] string_index;

	public byte[] getString_index() {
		return string_index;
	}

	public void setString_index(byte[] string_index) {
		this.string_index = string_index;
	}
	
	
	@Override
	public void parse(InputStream in, byte tag, int index) throws Exception{
		super.parse(in, tag, index);
		byte[] bb = new byte[2];
		in.read(bb, 0, 2);
		setString_index(bb);
	}
	
	@Override
	public String toString() {
		String result = "";
		try {
			result = "const #" + getIndex() + " =String\t #" + TransformUtil.bytesToInt(string_index);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
