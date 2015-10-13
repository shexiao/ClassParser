package com.compiler.model;

import java.util.List;

import com.compiler.model.attributeinfo.AttributeInfo;
import com.compiler.model.constantpool.ConstantPoolInfo;

public class ClassModel {
	private byte[] magic;
	private byte[] minor_version;
	private byte[] major_version;
	private byte[] constant_pool_count;
	private List<ConstantPoolInfo> constant_pool;
	private byte[] access_flags;
	private byte[] this_class;
	private byte[] super_class;
	private byte[] interfaces_count;
	private List<byte[]> interfaces;
	private byte[] fields_count;
	private List<FieldInfo> fields;
	private byte[] methods_count;
	private List<MethodInfo> methods;
	private byte[] attribute_count;
	private List<AttributeInfo> attributes;
	
	public byte[] getThis_class() {
		return this_class;
	}
	public void setThis_class(byte[] this_class) {
		this.this_class = this_class;
	}
	public byte[] getMagic() {
		return magic;
	}
	public void setMagic(byte[] magic) {
		this.magic = magic;
	}
	public byte[] getMinor_version() {
		return minor_version;
	}
	public void setMinor_version(byte[] minor_version) {
		this.minor_version = minor_version;
	}
	public byte[] getMajor_version() {
		return major_version;
	}
	public void setMajor_version(byte[] major_version) {
		this.major_version = major_version;
	}
	public byte[] getConstant_pool_count() {
		return constant_pool_count;
	}
	public void setConstant_pool_count(byte[] constant_pool_count) {
		this.constant_pool_count = constant_pool_count;
	}
	public List<ConstantPoolInfo> getConstant_pool() {
		return constant_pool;
	}
	public void setConstant_pool(List<ConstantPoolInfo> constant_pool) {
		this.constant_pool = constant_pool;
	}
	public byte[] getAccess_flags() {
		return access_flags;
	}
	public void setAccess_flags(byte[] access_flags) {
		this.access_flags = access_flags;
	}
	public byte[] getSuper_class() {
		return super_class;
	}
	public void setSuper_class(byte[] super_class) {
		this.super_class = super_class;
	}
	public byte[] getInterfaces_count() {
		return interfaces_count;
	}
	public void setInterfaces_count(byte[] interfaces_count) {
		this.interfaces_count = interfaces_count;
	}
	public List<byte[]> getInterfaces() {
		return interfaces;
	}
	public void setInterfaces(List<byte[]> interfaces) {
		this.interfaces = interfaces;
	}
	public byte[] getFields_count() {
		return fields_count;
	}
	public void setFields_count(byte[] fields_count) {
		this.fields_count = fields_count;
	}
	public List<FieldInfo> getFields() {
		return fields;
	}
	public void setFields(List<FieldInfo> fields) {
		this.fields = fields;
	}
	public byte[] getMethods_count() {
		return methods_count;
	}
	public void setMethods_count(byte[] methods_count) {
		this.methods_count = methods_count;
	}
	public List<MethodInfo> getMethods() {
		return methods;
	}
	public void setMethods(List<MethodInfo> methods) {
		this.methods = methods;
	}
	public byte[] getAttribute_count() {
		return attribute_count;
	}
	public void setAttribute_count(byte[] attribute_count) {
		this.attribute_count = attribute_count;
	}
	public List<AttributeInfo> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<AttributeInfo> attributes) {
		this.attributes = attributes;
	}

	
	
}
