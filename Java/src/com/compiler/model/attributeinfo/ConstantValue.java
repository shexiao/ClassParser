package com.compiler.model.attributeinfo;

import java.util.List;

import com.compiler.model.constantpool.ConstantPoolInfo;
import com.compiler.parser.ClassModelParser;
import com.compiler.util.TransformUtil;

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
	
	@Override
	public String print(int length) throws Exception {
		String space = TransformUtil.spaces(length);
		String result = "";
		try {
			result = space + "ConstantValue : " + ClassModelParser.getUTF8(getCp_info(), getConstantvalue_index());
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
