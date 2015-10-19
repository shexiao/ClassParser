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
	public String print(int length) throws Exception {
		String space = TransformUtil.spaces(length);
		String result = "";
		result = space + "const #" + getIndex() + " =class" + space + " #" + TransformUtil.bytesToInt(name_index);
		return result;
	}
}
