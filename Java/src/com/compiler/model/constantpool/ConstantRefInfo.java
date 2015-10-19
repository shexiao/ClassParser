package com.compiler.model.constantpool;

import java.io.InputStream;

import com.compiler.util.TransformUtil;

public class ConstantRefInfo extends ConstantPoolInfo {
	private byte[] class_index;
	private byte[] name_and_type_index;
	public byte[] getClass_index() {
		return class_index;
	}
	public void setClass_index(byte[] class_index) {
		this.class_index = class_index;
	}
	public byte[] getName_and_type_index() {
		return name_and_type_index;
	}
	public void setName_and_type_index(byte[] name_and_type_index) {
		this.name_and_type_index = name_and_type_index;
	}
	
	@Override
	public void parse(InputStream in, byte tag, int index) throws Exception{
		super.parse(in, tag, index);
		byte[] bb = new byte[2];
		in.read(bb, 0, 2);
		setClass_index(bb);
		bb = new byte[2];
		in.read(bb, 0, 2);
		setName_and_type_index(bb);
	}
	
	@Override
	public String print(int length) {
		String space = TransformUtil.spaces(length);
		String result = "";
		String type = "";
		try {
			if (tag == CONSTANT_FIELDREF) {
				type = "field";
			} else if (tag == CONSTANT_METHODREF) {
				type = "method";
			} else if (tag == CONSTANT_INTERFACEMETHODREF) {
				type = "interfacemethod";
			}
			result = space + "const #" + getIndex() + " =" + type + space + "#" + TransformUtil.bytesToInt(class_index) + ".#" + TransformUtil.bytesToInt(name_and_type_index);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
