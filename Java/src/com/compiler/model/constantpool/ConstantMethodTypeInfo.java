package com.compiler.model.constantpool;

import java.io.InputStream;

public class ConstantMethodTypeInfo extends ConstantPoolInfo {
	private byte[] descriptor_index;

	public byte[] getDescriptor_index() {
		return descriptor_index;
	}

	public void setDescriptor_index(byte[] descriptor_index) {
		this.descriptor_index = descriptor_index;
	}
	
	@Override
	public void parse(InputStream in, byte tag, int index) throws Exception{
		super.parse(in, tag, index);
		byte[] bb = new byte[2];
		in.read(bb, 0, 2);
		setDescriptor_index(bb);
	}
}
