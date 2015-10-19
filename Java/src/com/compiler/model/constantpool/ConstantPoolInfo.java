package com.compiler.model.constantpool;

import java.io.InputStream;

public class ConstantPoolInfo {
	public static final byte CONSTANT_CLASS = 0x07;
	public static final byte CONSTANT_FIELDREF = 0x09;
	public static final byte CONSTANT_METHODREF = 0x0a;
	public static final byte CONSTANT_INTERFACEMETHODREF = 0x0b;
	public static final byte CONSTANT_STRING = 0x08;
	public static final byte CONSTANT_INTEGER = 0x03;
	public static final byte CONSTANT_FLOAT = 0x04;
	public static final byte CONSTANT_LONG = 0x05;
	public static final byte CONSTANT_DOUBLE = 0x06;
	public static final byte CONSTANT_NAMEANDTYPE = 0x0c;
	public static final byte CONSTANT_UTF8 = 0x01;
	public static final byte CONSTANT_METHODHANDLE = 0x0f;
	public static final byte CONSTANT_MOTHODTYPE = 0x10;
	public static final byte CONSTANT_INVOKEDYNAMIC = 0x12;
	
	
	protected byte tag;
	protected int index;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public byte getTag() {
		return tag;
	}

	public void setTag(byte tag) {
		this.tag = tag;
	}
	
	public void parse(InputStream in, byte tag, int index) throws Exception{
		setIndex(index);
		setTag(tag);
	}
	
	public String print(int length) throws Exception{
		return toString();
	}
}
