package com.compiler.model.attributeinfo;

import java.util.ArrayList;
import java.util.List;

import com.compiler.model.constantpool.ConstantPoolInfo;
import com.compiler.parser.ClassModelParser;
import com.compiler.util.TransformUtil;

public class Exceptions extends AttributeInfo {
	private byte[] number_of_exceptions;
	private List<byte[]> exception_index_table;
	public byte[] getNumber_of_exceptions() {
		return number_of_exceptions;
	}
	public void setNumber_of_exceptions(byte[] number_of_exceptions) {
		this.number_of_exceptions = number_of_exceptions;
	}
	public List<byte[]> getException_index_table() {
		return exception_index_table;
	}
	public void setException_index_table(List<byte[]> exception_index_table) {
		this.exception_index_table = exception_index_table;
	}
	
	@Override
	public void parseSelf(AttributeInfo attributeInfo, List<ConstantPoolInfo> cp_info) throws Exception {
		super.parseSelf(attributeInfo, cp_info);
		byte[] info = attributeInfo.getInfo();
		int index = 0;
		setNumber_of_exceptions(TransformUtil.subBytes(info, index, 2));
		index += 2;
		
		int number = TransformUtil.bytesToInt(getNumber_of_exceptions());
		if (number > 0) {
			List<byte[]> exceptions = new ArrayList<byte[]>();
			for (int i = 0; i < number; i++) {
				byte[] exception = TransformUtil.subBytes(info, index, 2);
				index += 2;
				exceptions.add(exception);
			}
			setException_index_table(exceptions);
		}
	}
	
	@Override
	public String toString() {
		String result = "";
		try {
			if (TransformUtil.bytesToInt(getNumber_of_exceptions()) > 0) {
				result = "Exceptions : \n";
				for (byte[] bb : getException_index_table()) {
					result += "\texception : " + ClassModelParser.getConstatnClass(getCp_info(), bb) + "\n";
				}
			}
		} catch (Exception e) {
			
		}
		return result;
	}
}
