package com.compiler.model.attributeinfo;

import java.util.List;

import com.compiler.model.constantpool.ConstantPoolInfo;
import com.compiler.util.TransformUtil;

public class EnclosingMethod extends AttributeInfo {
	private byte[] class_index;
	private byte[] method_index;
	public byte[] getClass_index() {
		return class_index;
	}
	public void setClass_index(byte[] class_index) {
		this.class_index = class_index;
	}
	public byte[] getMethod_index() {
		return method_index;
	}
	public void setMethod_index(byte[] method_index) {
		this.method_index = method_index;
	}
	
	@Override
	public void parseSelf(AttributeInfo attributeInfo, List<ConstantPoolInfo> cp_info) throws Exception {
		super.parseSelf(attributeInfo, cp_info);
		byte[] info = attributeInfo.getInfo();
		
		int index = 0;
		setClass_index(TransformUtil.subBytes(info, index, 2));
		index += 2;
		setMethod_index(TransformUtil.subBytes(info, index, 2));
	}
}
