package com.compiler.model.attributeinfo;

import java.util.List;

import com.compiler.model.constantpool.ConstantPoolInfo;
import com.compiler.parser.ClassModelParser;
import com.compiler.util.TransformUtil;

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
	
	@Override
	public String print(int length) throws Exception {
		String space = TransformUtil.spaces(length);
		String result = "";
		try {
			result = space + "Signature : " + ClassModelParser.getUTF8(getCp_info(), getSignature_index());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
