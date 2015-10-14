package com.compiler.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.compiler.model.ClassModel;
import com.compiler.model.attributeinfo.AttributeInfo;
import com.compiler.model.attributeinfo.Code;
import com.compiler.model.attributeinfo.LineNumberTable;
import com.compiler.model.attributeinfo.LocalVariableTable;
import com.compiler.model.constantpool.ConstantPoolInfo;
import com.compiler.model.constantpool.ConstantUtf8Info;
import com.compiler.util.TransformUtil;

public class AttributesParser implements IParser {
	private IParser next = null;
	
	@Override
	public void addNext(IParser next) {
		this.next = next;
	}	
	
	@Override
	public void parse(ClassModel classModel, InputStream in) throws Exception {
		byte[] bb = new byte[2];
		in.read(bb, 0, 2);
		classModel.setAttribute_count(bb);
		
		int count = TransformUtil.bytesToInt(bb);
		if (count == 0) {
			if (this.next != null) {
				this.next.parse(classModel, in);
			}
			return;
		}
		
		List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
		int index = 0; 
		while (index < count) {
			AttributeInfo attribute_info = parseDetail(classModel, in);
			attributes.add(attribute_info);
			index++;
		}
		classModel.setAttributes(attributes);
		
		if (this.next != null) {
			this.next.parse(classModel, in);
		}
	}

	public AttributeInfo parseDetail(ClassModel classModel, InputStream in) throws Exception {
		byte[] bb = new byte[2];
		in.read(bb, 0, 2);
		int name_index = TransformUtil.bytesToInt(bb);
		List<ConstantPoolInfo> cp_info = classModel.getConstant_pool();
		ConstantUtf8Info utf8Info = (ConstantUtf8Info)cp_info.get(name_index);
		String attribute_name = new String(utf8Info.getBytes(), "UTF-8");
		
		switch (attribute_name) {
		case AttributeInfo.CODE:
			Code code = new Code();
			code.parse(in, bb);
			code.parseSelf(code.getInfo());
			return code;
		case AttributeInfo.LOCALVARIABLETABLE:
			LocalVariableTable localVariableTable = new LocalVariableTable();
			localVariableTable.parse(in, bb);
			localVariableTable.parseSelf(localVariableTable.getInfo());
			return localVariableTable;
		case AttributeInfo.LINENUMBERTABLE:
			LineNumberTable lineNumberTable = new LineNumberTable();
			lineNumberTable.parse(in, bb);
			lineNumberTable.parseSelf(lineNumberTable.getInfo());
			return lineNumberTable;
		}
		
		AttributeInfo attribute_info = new AttributeInfo();
		attribute_info.parse(in, bb);
		return attribute_info;
	}
}
