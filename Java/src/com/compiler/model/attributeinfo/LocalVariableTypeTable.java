package com.compiler.model.attributeinfo;

import java.util.ArrayList;
import java.util.List;

import com.compiler.model.constantpool.ConstantPoolInfo;
import com.compiler.parser.ClassModelParser;
import com.compiler.util.TransformUtil;

public class LocalVariableTypeTable extends AttributeInfo {
	private byte[] local_variable_type_table_length;
	private List<TypeTable> local_variable_type_table;

	public byte[] getLocal_variable_type_table_length() {
		return local_variable_type_table_length;
	}

	public void setLocal_variable_type_table_length(byte[] local_variable_type_table_length) {
		this.local_variable_type_table_length = local_variable_type_table_length;
	}

	public List<TypeTable> getLocal_variable_type_table() {
		return local_variable_type_table;
	}

	public void setLocal_variable_type_table(List<TypeTable> local_variable_type_table) {
		this.local_variable_type_table = local_variable_type_table;
	}
	
	@Override
	public void parseSelf(AttributeInfo attributeInfo, List<ConstantPoolInfo> cp_info) throws Exception {
		super.parseSelf(attributeInfo, cp_info);
		byte[] info = attributeInfo.getInfo();
		int[] index = new int[]{0};
		setLocal_variable_type_table_length(TransformUtil.subBytes(info, index, 2));
		
		int length = TransformUtil.bytesToInt(getLocal_variable_type_table_length());
		if (length > 0) {
			List<TypeTable> typeTables = new ArrayList<TypeTable>();
			for (int i = 0; i < length; i++) {
				TypeTable typeTable = new TypeTable();
				typeTable.setStart_pc(TransformUtil.subBytes(info, index, 2));
				typeTable.setLength(TransformUtil.subBytes(info, index, 2));
				typeTable.setName_index(TransformUtil.subBytes(info,  index, 2));
				typeTable.setSignature_index(TransformUtil.subBytes(info, index, 2));
				typeTable.setIndex(TransformUtil.subBytes(info, index, 2));
				
				typeTables.add(typeTable);
			}
			setLocal_variable_type_table(typeTables);
		}
	}
	
	@Override
	public String print(int length) throws Exception {
		String space = TransformUtil.spaces(length);
		String space1 = TransformUtil.spaces(length + 4);
		String result = "";
		try {
			if (TransformUtil.bytesToInt(getLocal_variable_type_table_length()) > 0) {
				for (TypeTable table : getLocal_variable_type_table()) {
					result += space + "LocalVariableTypeTable[" + table.getIndex() + "]:\n";
					result += space1 + "start_pc : length -> " +  table.getStart_pc() + " : " + table.getLength() + "\n";
					result += space1 + "name : signature -> " + ClassModelParser.getUTF8(getCp_info(), table.getName_index()) + " : " + ClassModelParser.getUTF8(getCp_info(), table.getSignature_index()) + "\n";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	class TypeTable {
		private byte[] start_pc;
		private byte[] length;
		private byte[] name_index;
		private byte[] signature_index;
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

		public byte[] getSignature_index() {
			return signature_index;
		}

		public void setSignature_index(byte[] signature_index) {
			this.signature_index = signature_index;
		}

		public byte[] getIndex() {
			return index;
		}

		public void setIndex(byte[] index) {
			this.index = index;
		}

	}
}
