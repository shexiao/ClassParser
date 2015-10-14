package com.compiler.parser;

import java.io.InputStream;

import com.compiler.model.ClassModel;

public class HeaderParser implements IParser {
	private IParser next = null;
	
	@Override
	public void addNext(IParser next) {
		this.next = next;
	}
	
	@Override
	public void parse(ClassModel classModel, InputStream in) throws Exception {
		byte[] bb = new byte[4];
		in.read(bb, 0, 4);
		classModel.setMagic(bb);
		bb = new byte[2];
		in.read(bb, 0, 2);
		classModel.setMinor_version(bb);
		bb = new byte[2];
		in.read(bb, 0, 2);
		classModel.setMajor_version(bb);
		
		if (this.next != null) {
			this.next.parse(classModel, in);
		}
	}

}
