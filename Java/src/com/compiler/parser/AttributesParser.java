package com.compiler.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.compiler.model.ClassModel;
import com.compiler.model.attributeinfo.AnnotationDefault;
import com.compiler.model.attributeinfo.AttributeInfo;
import com.compiler.model.attributeinfo.BootstrapMethods;
import com.compiler.model.attributeinfo.Code;
import com.compiler.model.attributeinfo.ConstantValue;
import com.compiler.model.attributeinfo.EnclosingMethod;
import com.compiler.model.attributeinfo.Exceptions;
import com.compiler.model.attributeinfo.InnerClasses;
import com.compiler.model.attributeinfo.LineNumberTable;
import com.compiler.model.attributeinfo.LocalVariableTable;
import com.compiler.model.attributeinfo.LocalVariableTypeTable;
import com.compiler.model.attributeinfo.RuntimeInvisibleAnnotations;
import com.compiler.model.attributeinfo.RuntimeInvisibleParameterAnnotations;
import com.compiler.model.attributeinfo.RuntimeVisibleAnnotations;
import com.compiler.model.attributeinfo.RuntimeVisibleParameterAnnotations;
import com.compiler.model.attributeinfo.Signature;
import com.compiler.model.attributeinfo.SourceDebugExtension;
import com.compiler.model.attributeinfo.SourceFile;
import com.compiler.model.attributeinfo.StackMapTable;
import com.compiler.model.constantpool.ConstantPoolInfo;
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
		List<ConstantPoolInfo> cp_info = classModel.getConstant_pool();
		String attribute_name = ClassModelParser.getUTF8(cp_info, bb);
		
		AttributeInfo attribute_info = new AttributeInfo();
		attribute_info.parse(in, bb);
		
		switch (attribute_name) {
		case AttributeInfo.CODE:
			Code code = new Code();
			code.parseSelf(attribute_info, cp_info);
			return code;
		case AttributeInfo.LOCALVARIABLETABLE:
			LocalVariableTable localVariableTable = new LocalVariableTable();
			localVariableTable.parseSelf(attribute_info, cp_info);
			return localVariableTable;
		case AttributeInfo.LINENUMBERTABLE:
			LineNumberTable lineNumberTable = new LineNumberTable();
			lineNumberTable.parseSelf(attribute_info, cp_info);
			return lineNumberTable;
		case AttributeInfo.CONSTANTVALUE:
			ConstantValue constantValue = new ConstantValue();
			constantValue.parseSelf(attribute_info, cp_info);
			return constantValue;
		case AttributeInfo.EXCEPTIONS:
			Exceptions exceptions = new Exceptions();
			exceptions.parseSelf(attribute_info, cp_info);
			return exceptions;
		case AttributeInfo.INNERCLASSES:
			InnerClasses innerClasses = new InnerClasses();
			innerClasses.parseSelf(attribute_info, cp_info);
			return innerClasses;
		case AttributeInfo.ENCLOSINGMETHOD:
			EnclosingMethod enclosingMethod = new EnclosingMethod();
			enclosingMethod.parseSelf(attribute_info, cp_info);
			return enclosingMethod;
		case AttributeInfo.SIGNATURE:
			Signature signature = new Signature();
			signature.parseSelf(attribute_info, cp_info);
			return signature;
		case AttributeInfo.SOURCEFILE:
			SourceFile sourceFile = new SourceFile();
			sourceFile.parseSelf(attribute_info, cp_info);
			return sourceFile;
		case AttributeInfo.SOURCEDEBUGEXTENSION:
			SourceDebugExtension sourceDebugExtension = new SourceDebugExtension();
			sourceDebugExtension.parseSelf(attribute_info, cp_info);
			return sourceDebugExtension;
		case AttributeInfo.LOCALVARIABLETYPETABLE:
			LocalVariableTypeTable localVariableTypeTable = new LocalVariableTypeTable();
			localVariableTypeTable.parseSelf(attribute_info, cp_info);
			return localVariableTypeTable;
		case AttributeInfo.BOOTSTRAPMETHODS:
			BootstrapMethods bootstrapMethods = new BootstrapMethods();
			bootstrapMethods.parseSelf(attribute_info, cp_info);
			return bootstrapMethods;
		case AttributeInfo.STACKMAPTABLE:
			StackMapTable stackMapTable = new StackMapTable();
			stackMapTable.parseSelf(attribute_info, cp_info);
			return stackMapTable;
		case AttributeInfo.RUNTIMEVISIBLEANNOTATIONS:
			RuntimeVisibleAnnotations runtimeVisibleAnnotations = new RuntimeVisibleAnnotations();
			runtimeVisibleAnnotations.parseSelf(attribute_info, cp_info);
			return runtimeVisibleAnnotations;
		case AttributeInfo.RUNTIMEINVISIBLEANNOTATIONS:
			RuntimeInvisibleAnnotations runtimeInvisibleAnnotations = new RuntimeInvisibleAnnotations();
			runtimeInvisibleAnnotations.parseSelf(attribute_info, cp_info);
			return runtimeInvisibleAnnotations;
		case AttributeInfo.RUNTIMEVISIBLEPARAMETERANNOTATIONS:
			RuntimeVisibleParameterAnnotations runtimeVParamAnno = new RuntimeVisibleParameterAnnotations();
			runtimeVParamAnno.parseSelf(attribute_info, cp_info);
			return runtimeVParamAnno;
		case AttributeInfo.RUNTIMEINVISIBLEPARAMETERANNOTATIONS:
			RuntimeInvisibleParameterAnnotations runtimeIParamAnno = new RuntimeInvisibleParameterAnnotations();
			runtimeIParamAnno.parseSelf(attribute_info, cp_info);
			return runtimeIParamAnno;
		case AttributeInfo.ANNOTATIONDEFAULT:
			AnnotationDefault annotationDefault = new AnnotationDefault();
			annotationDefault.parseSelf(attribute_info, cp_info);
			return annotationDefault;
		default:
			return attribute_info;
		}
	}
}
