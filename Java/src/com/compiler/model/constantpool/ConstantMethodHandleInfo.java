package com.compiler.model.constantpool;

import java.io.InputStream;

public class ConstantMethodHandleInfo extends ConstantPoolInfo {
	private byte reference_kind;
	private byte[] reference_index;
	public byte getReference_kind() {
		return reference_kind;
	}
	public void setReference_kind(byte reference_kind) {
		this.reference_kind = reference_kind;
	}
	public byte[] getReference_index() {
		return reference_index;
	}
	public void setReference_index(byte[] reference_index) {
		this.reference_index = reference_index;
	}
	
	@Override
	public void parse(InputStream in, byte tag, int index) throws Exception{
		super.parse(in, tag, index);
		byte[] bb = new byte[1];
		in.read(bb, 0, 1);
		setReference_kind(bb[0]);
		bb = new byte[2];
		in.read(bb, 0, 2);
		setReference_index(bb);
	}
}
