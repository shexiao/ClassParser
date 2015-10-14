package com.compiler.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.compiler.model.ClassModel;
import com.compiler.model.FieldInfo;
import com.compiler.model.attributeinfo.AttributeInfo;
import com.compiler.util.TransformUtil;

public class FieldsParser implements IParser {
	private IParser next = null;
	
	@Override
	public void addNext(IParser next) {
		this.next = next;
	}

	@Override
	public void parse(ClassModel classModel, InputStream in) throws Exception {
		byte[] bb = new byte[2];
		in.read(bb, 0, 2);
		classModel.setFields_count(bb);
		
		int count = TransformUtil.bytesToInt(bb);
		if (count == 0) {
			if (this.next != null) {
				this.next.parse(classModel, in);
			}
			return;
		}
		
		List<FieldInfo> fields = new ArrayList<FieldInfo>();
		int index = 0;
		while (index < count) {
			FieldInfo fieldInfo = parseDetail(classModel, in);
			fields.add(fieldInfo);
			index++;
		}
		classModel.setFields(fields);
		
		if (this.next != null) {
			this.next.parse(classModel, in);
		}
	}
	
	private FieldInfo parseDetail(ClassModel classModel, InputStream in) throws Exception {
		FieldInfo field_info = new FieldInfo();
		byte[] bb = new byte[2];
		in.read(bb, 0, 2);
		field_info.setAccess_flags(bb);
		
		bb = new byte[2];
		in.read(bb, 0, 2);
		field_info.setName_index(bb);
		
		bb = new byte[2];
		in.read(bb, 0, 2);
		field_info.setDescriptor_index(bb);
		
		bb = new byte[2];
		in.read(bb, 0, 2);
		field_info.setAttributes_count(bb);
		
		int count = TransformUtil.bytesToInt(bb);
		if (count == 0) return field_info;
		
		List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
		int index = 0;
		AttributesParser attributesParser = new AttributesParser();
		while (index < count) {
			AttributeInfo attribute_info = attributesParser.parseDetail(classModel, in);
			attributes.add(attribute_info);
			index++;
		}
		field_info.setAttributes(attributes);
		return field_info;
	}

}
