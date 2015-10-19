package com.compiler.model.attributeinfo;

import java.util.List;

import com.compiler.model.constantpool.ConstantPoolInfo;
import com.compiler.parser.ClassModelParser;
import com.compiler.util.TransformUtil;

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
	public String print(int length) throws Exception {
		String space = TransformUtil.spaces(length);
		String result = "";
		try {
			result += space + "SourceFile : " + ClassModelParser.getUTF8(getCp_info(), sourcefile_index) + "\n";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
