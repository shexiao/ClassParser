package com.compiler.model.attributeinfo;

public class StackMapTable extends AttributeInfo {
	private byte[] number_of_entries;

	public byte[] getNumber_of_entries() {
		return number_of_entries;
	}

	public void setNumber_of_entries(byte[] number_of_entries) {
		this.number_of_entries = number_of_entries;
	}
}
