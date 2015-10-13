package com.compiler.model.constantpool;

import java.io.InputStream;

public class ConstantInvokeDynamicInfo extends ConstantPoolInfo {
	private byte[] boostrap_method_attr_index;
	private byte[] name_and_type_index;
	public byte[] getBoostrap_method_attr_index() {
		return boostrap_method_attr_index;
	}
	public void setBoostrap_method_attr_index(byte[] boostrap_method_attr_index) {
		this.boostrap_method_attr_index = boostrap_method_attr_index;
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
		setBoostrap_method_attr_index(bb);
		bb = new byte[2];
		in.read(bb, 0, 2);
		setName_and_type_index(bb);
	}
}
