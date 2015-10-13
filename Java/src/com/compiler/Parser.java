package com.compiler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.compiler.model.ClassModel;
import com.compiler.model.FieldInfo;
import com.compiler.model.MethodInfo;
import com.compiler.model.attributeinfo.AttributeInfo;
import com.compiler.model.constantpool.ConstantClassInfo;
import com.compiler.model.constantpool.ConstantIntegerOrFloatInfo;
import com.compiler.model.constantpool.ConstantInvokeDynamicInfo;
import com.compiler.model.constantpool.ConstantLongOrDoubleInfo;
import com.compiler.model.constantpool.ConstantMethodHandleInfo;
import com.compiler.model.constantpool.ConstantMethodTypeInfo;
import com.compiler.model.constantpool.ConstantNameAndTypeInfo;
import com.compiler.model.constantpool.ConstantPoolInfo;
import com.compiler.model.constantpool.ConstantRefInfo;
import com.compiler.model.constantpool.ConstantStringInfo;
import com.compiler.model.constantpool.ConstantUtf8Info;
import com.compiler.util.TransformUtil;

public class Parser {
	
	private ClassModel classModel;
	private int len = 0;
	
	public Parser() {
		if (classModel == null) {
			classModel = new ClassModel();
		}
	}
	
	
	
	public void parse(InputStream in) throws Exception {
		parseHeader(in);
		parseConstantPool(in);
		parseClassInfo(in);
		parseInterfaces(in);
		parseFields(in);
		parseMethods(in);
		parseAttributes(in);
		
		
//		int end = in.read();
//		System.out.println("end: " + end);
	}
	
	private void parseHeader(InputStream in) throws Exception {
		byte[] bb = new byte[4];
		in.read(bb, 0, 4);
		len += 4;
		this.classModel.setMagic(bb);
		bb = new byte[2];
		in.read(bb, 0, 2);
		len += 2;
		this.classModel.setMinor_version(bb);
		bb = new byte[2];
		in.read(bb, 0, 2);
		this.classModel.setMajor_version(bb);
	}
	
	private void parseConstantPool(InputStream in) throws Exception {
		byte[] bb = new byte[2];
		in.read(bb, 0, 2);
		
		this.classModel.setConstant_pool_count(bb);
			
		List<ConstantPoolInfo> cp_info = new ArrayList<ConstantPoolInfo>();
		cp_info.add(new ConstantPoolInfo());//
		int cp_length = TransformUtil.bytesToInt(bb);
		int index = 1;
		int step;
		while (index < cp_length) {
			step = parseConstantPool1(in, cp_info, index);
			if (step == 2) {
				cp_info.add(new ConstantPoolInfo());
			}
			index += step;
		}
		
		this.classModel.setConstant_pool(cp_info);
	}
	
	private int parseConstantPool1(InputStream in, List<ConstantPoolInfo> cp_info, int index) throws Exception {
		byte[] bb = new byte[1];
		in.read(bb, 0, 1);
		byte tag = bb[0];
		//System.out.printf("tag : %x\n", tag);
		switch (tag) {
		case ConstantPoolInfo.CONSTANT_CLASS:
			ConstantClassInfo const_class_info = new ConstantClassInfo();
			const_class_info.parse(in, tag, index);
			cp_info.add(const_class_info);
			return 1;
			
		case ConstantPoolInfo.CONSTANT_FIELDREF:
		case ConstantPoolInfo.CONSTANT_METHODREF:
		case ConstantPoolInfo.CONSTANT_INTERFACEMETHODREF:
			ConstantRefInfo const_ref_info = new ConstantRefInfo();
			const_ref_info.parse(in, tag, index);
			cp_info.add(const_ref_info);
			return 1;
			
		case ConstantPoolInfo.CONSTANT_STRING:
			ConstantStringInfo const_string_info = new ConstantStringInfo();
			const_string_info.parse(in, tag, index);
			cp_info.add(const_string_info);
			return 1;
			
		case ConstantPoolInfo.CONSTANT_INTEGER:			
		case ConstantPoolInfo.CONSTANT_FLOAT:
			ConstantIntegerOrFloatInfo const_int_or_float_info = new ConstantIntegerOrFloatInfo();
			const_int_or_float_info.parse(in, tag, index);
			cp_info.add(const_int_or_float_info);
			return 1;
			
		case ConstantPoolInfo.CONSTANT_LONG:
		case ConstantPoolInfo.CONSTANT_DOUBLE:
			ConstantLongOrDoubleInfo const_long_or_double_info = new ConstantLongOrDoubleInfo();
			const_long_or_double_info.parse(in, tag, index);
			cp_info.add(const_long_or_double_info);
			return 2;
			
		case ConstantPoolInfo.CONSTANT_NAMEANDTYPE:
			ConstantNameAndTypeInfo const_nameandtype_info = new ConstantNameAndTypeInfo();
			const_nameandtype_info.parse(in, tag, index);
			cp_info.add(const_nameandtype_info);
			return 1;
			
		case ConstantPoolInfo.CONSTANT_UTF8:
			ConstantUtf8Info const_utf8_info = new ConstantUtf8Info();
			const_utf8_info.parse(in, tag, index);
			cp_info.add(const_utf8_info);
			return 1;
			
		case ConstantPoolInfo.CONSTANT_METHODHANDLE:
			ConstantMethodHandleInfo const_methodhandle_info = new ConstantMethodHandleInfo();
			const_methodhandle_info.parse(in, tag, index);
			cp_info.add(const_methodhandle_info);
			return 1;
			
		case ConstantPoolInfo.CONSTANT_MOTHODTYPE:
			ConstantMethodTypeInfo const_methodtype_info = new ConstantMethodTypeInfo();
			const_methodtype_info.parse(in, tag, index);
			cp_info.add(const_methodtype_info);
			return 1;
			
		case ConstantPoolInfo.CONSTANT_INVOKEDYNAMIC:
			ConstantInvokeDynamicInfo const_invokedynamic_info = new ConstantInvokeDynamicInfo();
			const_invokedynamic_info.parse(in, tag, index);
			cp_info.add(const_invokedynamic_info);
			return 1;
		}
		return 1;
		//System.out.printf("tag : %x\n", tag);
	}

	private void parseClassInfo(InputStream in) throws Exception{
		byte[] bb = new byte[2];
		in.read(bb, 0, 2);
		this.classModel.setAccess_flags(bb);
		
		bb = new byte[2];
		in.read(bb, 0, 2);
		this.classModel.setThis_class(bb);
		
		bb = new byte[2];
		in.read(bb, 0, 2);
		this.classModel.setSuper_class(bb);
	}
	
	private void parseInterfaces(InputStream in) throws Exception {
		byte[] bb = new byte[2];
		in.read(bb, 0, 2);
		this.classModel.setInterfaces_count(bb);
		
		int count = TransformUtil.bytesToInt(bb);
		if (count == 0) return;
		
		List<byte[]> interfaces = new ArrayList<byte[]>();
		int i = 0;
		while(i < count) {
			bb = new byte[2];
			in.read(bb, 0, 2);
			interfaces.add(bb);
			i++;
		}
		this.classModel.setInterfaces(interfaces);
	}
	
	private void parseFields(InputStream in) throws Exception {
		byte[] bb = new byte[2];
		in.read(bb, 0, 2);
		this.classModel.setFields_count(bb);
		
		int count = TransformUtil.bytesToInt(bb);
		if (count == 0) return;
		
		List<FieldInfo> fields = new ArrayList<FieldInfo>();
		int index = 0;
		while (index < count) {
			FieldInfo fieldInfo = parseFields1(in);
			fields.add(fieldInfo);
			index++;
		}
		this.classModel.setFields(fields);
	}
	
	private FieldInfo parseFields1(InputStream in) throws Exception {
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
		while (index < count) {
			AttributeInfo attribute_info = parseAttributes2(in);
			attributes.add(attribute_info);
			index++;
		}
		field_info.setAttributes(attributes);
		return field_info;
	}
	
	private void parseMethods(InputStream in) throws Exception {
		byte[] bb = new byte[2];
		in.read(bb, 0, 2);
		this.classModel.setMethods_count(bb);
		
		int count = TransformUtil.bytesToInt(bb);
		if (count == 0) return;
		
		List<MethodInfo> methods = new ArrayList<MethodInfo>();
		int index = 0;
		while (index < count) {
			MethodInfo method_info = parseMethods1(in);
			methods.add(method_info);
			index++;
		}
		this.classModel.setMethods(methods);
	}
	
	private MethodInfo parseMethods1(InputStream in) throws Exception {
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
		while (index < count) {
			AttributeInfo attribute_info = parseAttributes2(in);
			attributes.add(attribute_info);
			index++;
		}
		method_info.setAttributes(attributes);
		return method_info;
	}
	
	private AttributeInfo parseAttributes2(InputStream in) throws Exception {
		AttributeInfo attribute_info = new AttributeInfo();
		byte[] bb = new byte[2];
		in.read(bb, 0, 2);
		attribute_info.setAttribute_name_index(bb);
		
		bb = new byte[4];
		in.read(bb, 0, 4);
		attribute_info.setAttribute_length(bb);
		
		int length = TransformUtil.bytesToInt(bb);
		if (length == 0) return attribute_info;
		
		bb = new byte[length];
		in.read(bb, 0, length);
		attribute_info.setInfo(bb);
		return attribute_info;
	}
	
	private void parseAttributes(InputStream in) throws Exception {
		byte[] bb = new byte[2];
		in.read(bb, 0, 2);
		this.classModel.setAttribute_count(bb);
		
		int count = TransformUtil.bytesToInt(bb);
		if (count == 0) return;
		
		List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
		int index = 0; 
		while (index < count) {
			AttributeInfo attribute_info = parseAttributes2(in);
			attributes.add(attribute_info);
			index++;
		}
		this.classModel.setAttributes(attributes);
	}


	public ClassModel getClassModel() {
		return classModel;
	}



	public void setClassModel(ClassModel classModel) {
		this.classModel = classModel;
	}
	
	
}
