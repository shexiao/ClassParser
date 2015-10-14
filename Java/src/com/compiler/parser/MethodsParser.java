package com.compiler.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.compiler.model.ClassModel;
import com.compiler.model.MethodInfo;
import com.compiler.model.attributeinfo.AttributeInfo;
import com.compiler.util.TransformUtil;

public class MethodsParser implements IParser {
	private IParser next = null;
	
	@Override
	public void addNext(IParser next) {
		this.next = next;
	}

	@Override
	public void parse(ClassModel classModel, InputStream in) throws Exception {
		byte[] bb = new byte[2];
		in.read(bb, 0, 2);
		classModel.setMethods_count(bb);
		
		int count = TransformUtil.bytesToInt(bb);
		if (count == 0) {
			if (this.next != null) {
				this.next.parse(classModel, in);
			}
			return;
		}
		
		List<MethodInfo> methods = new ArrayList<MethodInfo>();
		int index = 0;
		while (index < count) {
			MethodInfo method_info = parseDetail(classModel, in);
			methods.add(method_info);
			index++;
		}
		classModel.setMethods(methods);
		
		if (this.next != null) {
			this.next.parse(classModel, in);
		}
	}
	
	private MethodInfo parseDetail(ClassModel classModel, InputStream in) throws Exception {
		MethodInfo method_info = new MethodInfo();
		byte[] bb = new byte[2];
		in.read(bb, 0, 2);
		method_info.setAccess_flags(bb);
		
		bb = new byte[2];
		in.read(bb, 0, 2);
		method_info.setName_index(bb);
		
		bb = new byte[2];
		in.read(bb, 0, 2);
		method_info.setDescriptor_index(bb);
		
		bb = new byte[2];
		in.read(bb, 0, 2);
		method_info.setAttributes_count(bb);
		
		int count = TransformUtil.bytesToInt(bb);
		if (count == 0) return method_info;
		
		List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
		int index = 0;
		AttributesParser attributesParser = new AttributesParser();
		while (index < count) {
			AttributeInfo attribute_info = attributesParser.parseDetail(classModel, in);
			attributes.add(attribute_info);
			index++;
		}
		method_info.setAttributes(attributes);
		return method_info;
	}

}
