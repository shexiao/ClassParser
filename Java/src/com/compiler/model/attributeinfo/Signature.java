package com.compiler.model.attributeinfo;

import java.util.List;

import com.compiler.model.constantpool.ConstantPoolInfo;

public class Signature extends AttributeInfo {
	private byte[] signature_index;

	public byte[] getSignature_index() {
		return signature_index;
	}

	public void setSignature_index(byte[] signature_index) {
		this.signature_index = signature_index;
	}
	
	@Override
	public void parseSelf(AttributeInfo attributeInfo, List<ConstantPoolInfo> cp_info) throws Exception {
		super.parseSelf(attributeInfo, cp_info);
		setSignature_index(attributeInfo.getInfo());
	}
}
