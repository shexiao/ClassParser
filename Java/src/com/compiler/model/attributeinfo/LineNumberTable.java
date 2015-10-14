package com.compiler.model.attributeinfo;

import java.util.ArrayList;
import java.util.List;

import com.compiler.util.TransformUtil;

public class LineNumberTable extends AttributeInfo{
	private byte[] line_number_table_length;
	private List<LineNumberTable1> line_number_table;

	public byte[] getLine_number_table_length() {
		return line_number_table_length;
	}

	public void setLine_number_table_length(byte[] line_number_table_length) {
		this.line_number_table_length = line_number_table_length;
	}

	public List<LineNumberTable1> getLine_number_table() {
		return line_number_table;
	}

	public void setLine_number_table(List<LineNumberTable1> line_number_table) {
		this.line_number_table = line_number_table;
	}
	
	@Override
	public void parseSelf(byte[] info) throws Exception {
		int index = 0;
		setLine_number_table_length(TransformUtil.subBytes(info, index, 2));
		index += 2;
		
		int length = TransformUtil.bytesToInt(getLine_number_table_length());
		if (length > 0) {
			List<LineNumberTable1> tables = new ArrayList<LineNumberTable1>();
			for (int i = 0; i < length; i++) {
				LineNumberTable1 table = new LineNumberTable1();
				table.setStart_pc(TransformUtil.subBytes(info, index, 2));
				index += 2;
				table.setLine_number(TransformUtil.subBytes(info, index, 2));
				index += 2;
				tables.add(table);
			}
			setLine_number_table(tables);
		}
	}

	class LineNumberTable1 {
		private byte[] start_pc;
		private byte[] line_number;

		public byte[] getStart_pc() {
			return start_pc;
		}

		public void setStart_pc(byte[] start_pc) {
			this.start_pc = start_pc;
		}

		public byte[] getLine_number() {
			return line_number;
		}

		public void setLine_number(byte[] line_number) {
			this.line_number = line_number;
		}

	}
}
