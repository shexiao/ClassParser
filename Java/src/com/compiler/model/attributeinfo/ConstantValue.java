package com.compiler.model.attributeinfo;

import java.util.List;

import com.compiler.model.constantpool.ConstantPoolInfo;

public class ConstantValue extends AttributeInfo{
	private byte[] constantvalue_index;

	public byte[] getConstantvalue_index() {
		return constantvalue_index;
	}

	public void setConstantvalue_index(byte[] constantvalue_index) {
		this.constantvalue_index = constantvalue_index;
	}
	
	@Override
	public void parseSelf(AttributeInfo attributeInfo, List<ConstantPoolInfo> cp_info) throws Exception{
		super.parseSelf(attributeInfo, cp_info);
		setConstantvalue_index(attributeInfo.getInfo());
	}
}
