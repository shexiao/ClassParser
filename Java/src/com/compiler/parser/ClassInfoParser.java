package com.compiler.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.compiler.model.ClassModel;
import com.compiler.util.TransformUtil;

public class ClassInfoParser implements IParser {
	private IParser next = null;
	
	@Override
	public void addNext(IParser next) {
		this.next = next;
	}

	@Override
	public void parse(ClassModel classModel, InputStream in) throws Exception {
		byte[] bb = new byte[2];
		in.read(bb, 0, 2);
		classModel.setAccess_flags(bb);
		
		bb = new byte[2];
		in.read(bb, 0, 2);
		classModel.setThis_class(bb);
		
		bb = new byte[2];
		in.read(bb, 0, 2);
		classModel.setSuper_class(bb);
		
		bb = new byte[2];
		in.read(bb, 0, 2);
		classModel.setInterfaces_count(bb);
		
		int count = TransformUtil.bytesToInt(bb);
		if (count == 0) {
			if (this.next != null) {
				this.next.parse(classModel, in);
			}
			return;
		}
		
		List<byte[]> interfaces = new ArrayList<byte[]>();
		int i = 0;
		while(i < count) {
			bb = new byte[2];
			in.read(bb, 0, 2);
			interfaces.add(bb);
			i++;
		}
		classModel.setInterfaces(interfaces);
		
		if (this.next != null) {
			this.next.parse(classModel, in);
		}
	}

}
