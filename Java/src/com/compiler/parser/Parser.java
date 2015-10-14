package com.compiler.parser;

import java.io.InputStream;
import java.io.LineNumberInputStream;
import java.util.ArrayList;
import java.util.List;

import com.compiler.model.ClassModel;
import com.compiler.model.FieldInfo;
import com.compiler.model.MethodInfo;
import com.compiler.model.attributeinfo.AttributeInfo;
import com.compiler.model.attributeinfo.Code;
import com.compiler.model.attributeinfo.LineNumberTable;
import com.compiler.model.attributeinfo.LocalVariableTable;
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
		IParser headerParser = new HeaderParser();
		IParser constantPoolParser = new ConstantPoolParser();
		IParser classInfoParser = new ClassInfoParser();
		IParser fieldsParser = new FieldsParser();
		IParser methodsParser = new MethodsParser();
		IParser attributesParser = new AttributesParser();
		
		headerParser.addNext(constantPoolParser);
		constantPoolParser.addNext(classInfoParser);
		classInfoParser.addNext(fieldsParser);
		fieldsParser.addNext(methodsParser);
		methodsParser.addNext(attributesParser);
		
		headerParser.parse(classModel, in);
		
//		int end = in.read();
//		System.out.println("end: " + end);
	}

	public ClassModel getClassModel() {
		return classModel;
	}



	public void setClassModel(ClassModel classModel) {
		this.classModel = classModel;
	}
	
	
}
