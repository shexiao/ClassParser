package com.compiler.model.attributeinfo;

import java.io.InputStream;
import java.util.List;

import com.compiler.model.constantpool.ConstantPoolInfo;
import com.compiler.util.TransformUtil;

public class AttributeInfo {
	public static final String CONSTANTVALUE = "ConstantValue";
	public static final String CODE = "Code";
	public static final String STACKMAPTABLE = "StackMapTable";
	public static final String EXCEPTIONS = "Exceptions";
	public static final String INNERCLASSES = "InnerClasses";
	public static final String ENCLOSINGMETHOD = "EnclosingMethod";
	public static final String SYNTHETIC = "Synthetic";
	public static final String SIGNATURE = "Signature";
	public static final String SOURCEFILE = "SourceFile";
	public static final String SOURCEDEBUGEXTENSION = "SourceDebugExtension";
	public static final String LINENUMBERTABLE = "LineNumberTable";
	public static final String LOCALVARIABLETABLE = "LocalVariableTable";
	public static final String LOCALVARIABLETYPETABLE = "LocalVariableTypeTable";
	public static final String DEPRECATED = "Deprecated";
	public static final String RUNTIMEVISIBLEANNOTATIONS = "RuntimeVisibleAnnotations";
	public static final String RUNTIMEINVISIBLEANNOTATIONS = "RuntimeInvisibleAnnotations";
	public static final String RUNTIMEVISIBLEPARAMETERANNOTATIONS = "RuntimeVisibleParameterAnnotations";
	public static final String RUNTIMEINVISIBLEPARAMETERANNOTATIONS = "RuntimeInvisibleParameterAnnotations";
	public static final String ANNOTATIONDEFAULT = "AnnotationDefault";
	public static final String BOOTSTRAPMETHODS = "BootstrapMethods";

	private byte[] attribute_name_index;
	private byte[] attribute_length;
	private byte[] info;
	private List<ConstantPoolInfo> cp_info;
	
	public List<ConstantPoolInfo> getCp_info() {
		return cp_info;
	}

	public void setCp_info(List<ConstantPoolInfo> cp_info) {
		this.cp_info = cp_info;
	}

	public byte[] getInfo() {
		return info;
	}

	public void setInfo(byte[] info) {
		this.info = info;
	}

	public byte[] getAttribute_name_index() {
		return attribute_name_index;
	}

	public void setAttribute_name_index(byte[] attribute_name_index) {
		this.attribute_name_index = attribute_name_index;
	}

	public byte[] getAttribute_length() {
		return attribute_length;
	}

	public void setAttribute_length(byte[] attribute_length) {
		this.attribute_length = attribute_length;
	}

	public void parse(InputStream in, byte[] name_index) throws Exception {
		setAttribute_name_index(name_index);
		byte[] bb = new byte[4];
		in.read(bb, 0, 4);
		setAttribute_length(bb);
		
		int length = TransformUtil.bytesToInt(getAttribute_length());
		bb = new byte[length];
		in.read(bb, 0, length);
		setInfo(bb);
	}
	
	public void parseSelf(AttributeInfo attributeInfo, List<ConstantPoolInfo> cp_info) throws Exception {
		setAttribute_name_index(attributeInfo.getAttribute_name_index());
		setAttribute_length(attributeInfo.getAttribute_length());
		setInfo(attributeInfo.getInfo());
		setCp_info(cp_info);
	}
	
	public String print(int length) throws Exception {
		return toString();
	}
}
