package com.compiler.model.attributeinfo;

import java.util.ArrayList;
import java.util.List;

import com.compiler.util.TransformUtil;

public class LocalVariableTable extends AttributeInfo{
	private byte[] local_variable_table_length;
	private List<LocalVariableTable1> local_variable_table;

	public byte[] getLocal_variable_table_length() {
		return local_variable_table_length;
	}

	public void setLocal_variable_table_length(byte[] local_variable_table_length) {
		this.local_variable_table_length = local_variable_table_length;
	}

	public List<LocalVariableTable1> getLocal_variable_table() {
		return local_variable_table;
	}

	public void setLocal_variable_table(List<LocalVariableTable1> local_variable_table) {
		this.local_variable_table = local_variable_table;
	}
	
	@Override
	public void parseSelf(byte[] info) throws Exception {
		int index = 0;
		setLocal_variable_table_length(TransformUtil.subBytes(info, index, 2));
		index += 2;
		
		int length = TransformUtil.bytesToInt(getLocal_variable_table_length());
		if (length > 0) {
			List<LocalVariableTable1> tables = new ArrayList<LocalVariableTable1>();
			for (int i = 0; i < length; i++) {
				LocalVariableTable1 table = new LocalVariableTable1();
				table.setStart_pc(TransformUtil.subBytes(info, index, 2));
				index += 2;
				table.setLength(TransformUtil.subBytes(info, index, 2));
				index += 2;
				table.setName_index(TransformUtil.subBytes(info, index, 2));
				index += 2;
				table.setDescriptor_index(TransformUtil.subBytes(info, index, 2));
				index += 2;
				table.setIndex(TransformUtil.subBytes(info, index, 2));
				tables.add(table);
			}
			setLocal_variable_table(tables);
		}
	}

	class LocalVariableTable1 {
		private byte[] start_pc;
		private byte[] length;
		private byte[] name_index;
		private byte[] descriptor_index;
		private byte[] index;

		public byte[] getStart_pc() {
			return start_pc;
		}

		public void setStart_pc(byte[] start_pc) {
			this.start_pc = start_pc;
		}

		public byte[] getLength() {
			return length;
		}

		public void setLength(byte[] length) {
			this.length = length;
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

		public byte[] getIndex() {
			return index;
		}

		public void setIndex(byte[] index) {
			this.index = index;
		}

	}
}
