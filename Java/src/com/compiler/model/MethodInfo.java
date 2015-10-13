package com.compiler.model;

import java.util.List;

import com.compiler.model.attributeinfo.AttributeInfo;

public class MethodInfo {
	private byte[] access_flags;
	private byte[] name_index;
	private byte[] descriptor_index;
	private byte[] attributes_count;
	private List<AttributeInfo> attributes;
	public byte[] getAccess_flags() {
		return access_flags;
	}
	public void setAccess_flags(byte[] access_flags) {
		this.access_flags = access_flags;
	}
	public byte[] getName_index() {
		return name_index;
	}
	public void setName_index(byte[] name_index) {
		this.name_index = name_index;
	}
	public byte[] getDescriptor_index() {
		return descriptor_index;
	}
	public void setDescriptor_index(byte[] descriptor_index) {
		this.descriptor_index = descriptor_index;
	}
	public byte[] getAttributes_count() {
		return attributes_count;
	}
	public void setAttributes_count(byte[] attributes_count) {
		this.attributes_count = attributes_count;
	}
	public List<AttributeInfo> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<AttributeInfo> attributes) {
		this.attributes = attributes;
	}
	
}
