package com.compiler.model.attributeinfo;

import java.util.List;

import com.compiler.model.constantpool.ConstantPoolInfo;

public class SourceDebugExtension extends AttributeInfo {
	private byte[] debug_extension;

	public byte[] getDebug_extension() {
		return debug_extension;
	}

	public void setDebug_extension(byte[] debug_extension) {
		this.debug_extension = debug_extension;
	}
	
	@Override
	public void parseSelf(AttributeInfo attributeInfo, List<ConstantPoolInfo> cp_info) throws Exception {
		super.parseSelf(attributeInfo, cp_info);
		setDebug_extension(attributeInfo.getInfo());
	}
}
