package com.compiler.model.attributeinfo;

import java.util.ArrayList;
import java.util.List;

import com.compiler.util.TransformUtil;

public class Code extends AttributeInfo{
	private byte[] max_stack;
	private byte[] max_locals;
	private byte[] code_length;
	private byte[] code;
	private byte[] exception_table_length;
	private List<ExceptionTable> exception_table;
	private byte[] attributes_count;
	private List<AttributeInfo> attributes;

	public byte[] getMax_stack() {
		return max_stack;
	}

	public void setMax_stack(byte[] max_stack) {
		this.max_stack = max_stack;
	}

	public byte[] getMax_locals() {
		return max_locals;
	}

	public void setMax_locals(byte[] max_locals) {
		this.max_locals = max_locals;
	}

	public byte[] getCode_length() {
		return code_length;
	}

	public void setCode_length(byte[] code_length) {
		this.code_length = code_length;
	}

	public byte[] getCode() {
		return code;
	}

	public void setCode(byte[] code) {
		this.code = code;
	}

	public byte[] getException_table_length() {
		return exception_table_length;
	}

	public void setException_table_length(byte[] exception_table_length) {
		this.exception_table_length = exception_table_length;
	}

	public List<ExceptionTable> getException_table() {
		return exception_table;
	}

	public void setException_table(List<ExceptionTable> exception_table) {
		this.exception_table = exception_table;
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
	
	public void parse(byte[] info) throws Exception{
		int index = 0;
		setMax_stack(TransformUtil.subBytes(info, index, 2));
		index += 2;
		setMax_locals(TransformUtil.subBytes(info, index, 2));
		index += 2;
		setCode_length(TransformUtil.subBytes(info, index, 4));
		index += 4;
		int clen = TransformUtil.bytesToInt(getCode_length());
		setCode(TransformUtil.subBytes(info, index, clen));
		index += clen;
		setException_table_length(TransformUtil.subBytes(info, index, 2));
		index += 2;
		int elen = TransformUtil.bytesToInt(getException_table_length());
		if (elen > 0) {
			List<ExceptionTable> tables = new ArrayList<ExceptionTable>();
			for (int i = 0; i < elen; i++) {
				ExceptionTable exception_table = new ExceptionTable();
				exception_table.setStart_pc(TransformUtil.subBytes(info, index, 2));
				index += 2;
				exception_table.setEnd_pc(TransformUtil.subBytes(info, index, 2));
				index += 2;
				exception_table.setHandler_pc(TransformUtil.subBytes(info, index, 2));
				index += 2;
				exception_table.setCatch_type(TransformUtil.subBytes(info, index, 2));
				index += 2;
				tables.add(exception_table);
			}
		}
		
		setAttributes_count(TransformUtil.subBytes(info, index, 2));
		index += 2;
		int count = TransformUtil.bytesToInt(getAttributes_count());
		if (count > 0) {
			List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
			for (int i = 0; i < count; i++) {
				AttributeInfo attribute_info = new AttributeInfo();
				attribute_info.setAttribute_name_index(TransformUtil.subBytes(info, index, 2));
				index += 2;
				
				attribute_info.setAttribute_length(TransformUtil.subBytes(info, index, 2));
				index += 2;
				
				int length = TransformUtil.bytesToInt(attribute_info.getAttribute_length());
				if (length > 0) {
					attribute_info.setInfo(TransformUtil.subBytes(info, index, length));
					index += length;
				}
				attributes.add(attribute_info);
			}
			setAttributes(attributes);
		}
		
	}
	
	@Override
	public String toString() {
		String result = "";
		try {
			result += "\t\tmax_stack : " + TransformUtil.bytesToInt(getMax_stack()) + "\n";
			result += "\t\tmax_local : " + TransformUtil.bytesToInt(getMax_locals()) + "\n";
			result += "\t\tcode_length : " + TransformUtil.bytesToInt(getCode_length()) + "\n";
			result += "\t\texception_table length : " + TransformUtil.bytesToInt(getException_table_length()) + "\n";
			result += "\t\tattributes_count : " + TransformUtil.bytesToInt(getAttributes_count()) + "\n";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	class ExceptionTable {
		private byte[] start_pc;
		private byte[] end_pc;
		private byte[] handler_pc;
		private byte[] catch_type;

		public byte[] getStart_pc() {
			return start_pc;
		}

		public void setStart_pc(byte[] start_pc) {
			this.start_pc = start_pc;
		}

		public byte[] getEnd_pc() {
			return end_pc;
		}

		public void setEnd_pc(byte[] end_pc) {
			this.end_pc = end_pc;
		}

		public byte[] getHandler_pc() {
			return handler_pc;
		}

		public void setHandler_pc(byte[] handler_pc) {
			this.handler_pc = handler_pc;
		}

		public byte[] getCatch_type() {
			return catch_type;
		}

		public void setCatch_type(byte[] catch_type) {
			this.catch_type = catch_type;
		}

	}
}
