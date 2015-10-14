package com.compiler.model.attributeinfo;

import java.util.List;

import com.compiler.model.constantpool.ConstantPoolInfo;
import com.compiler.parser.ClassModelParser;

public class SourceFile extends AttributeInfo {
	private byte[] sourcefile_index;

	public byte[] getSourcefile_index() {
		return sourcefile_index;
	}

	public void setSourcefile_index(byte[] sourcefile_index) {
		this.sourcefile_index = sourcefile_index;
	}
	
	@Override
	public void parseSelf(AttributeInfo attributeInfo, List<ConstantPoolInfo> cp_info) throws Exception {
		super.parseSelf(attributeInfo, cp_info);
		setSourcefile_index(attributeInfo.getInfo());
	}
	
	@Override
	public String toString() {
		String result = "";
		try {
			result += "\tSourceFile : " + ClassModelParser.getUTF8(getCp_info(), sourcefile_index) + "\n";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
