package com.compiler.model.attributeinfo;

import java.util.ArrayList;
import java.util.List;

import com.compiler.model.constantpool.ConstantPoolInfo;
import com.compiler.opcodes.Instructions;
import com.compiler.parser.ClassModelParser;
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
	
	@Override
	public void parseSelf(AttributeInfo attributeInfo, List<ConstantPoolInfo> cp_info) throws Exception{
		super.parseSelf(attributeInfo, cp_info);
		byte[] info = attributeInfo.getInfo();
		int[] index = new int[]{0};
		setMax_stack(TransformUtil.subBytes(info, index, 2));
		setMax_locals(TransformUtil.subBytes(info, index, 2));
		setCode_length(TransformUtil.subBytes(info, index, 4));
		int clen = TransformUtil.bytesToInt(getCode_length());
		setCode(TransformUtil.subBytes(info, index, clen));
		setException_table_length(TransformUtil.subBytes(info, index, 2));
		int elen = TransformUtil.bytesToInt(getException_table_length());
		if (elen > 0) {
			List<ExceptionTable> tables = new ArrayList<ExceptionTable>();
			for (int i = 0; i < elen; i++) {
				ExceptionTable exception_table = new ExceptionTable();
				exception_table.setStart_pc(TransformUtil.subBytes(info, index, 2));
				exception_table.setEnd_pc(TransformUtil.subBytes(info, index, 2));
				exception_table.setHandler_pc(TransformUtil.subBytes(info, index, 2));
				exception_table.setCatch_type(TransformUtil.subBytes(info, index, 2));
				tables.add(exception_table);
			}
		}
		
		setAttributes_count(TransformUtil.subBytes(info, index, 2));
		int count = TransformUtil.bytesToInt(getAttributes_count());
		if (count > 0) {
			List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
			for (int i = 0; i < count; i++) {
				AttributeInfo attribute_info = new AttributeInfo();
				attribute_info.setAttribute_name_index(TransformUtil.subBytes(info, index, 2));
				attribute_info.setAttribute_length(TransformUtil.subBytes(info, index, 4));
				int length = TransformUtil.bytesToInt(attribute_info.getAttribute_length());
				if (length > 0) {
					attribute_info.setInfo(TransformUtil.subBytes(info, index, length));
				}
				
				String name = ClassModelParser.getUTF8(cp_info, attribute_info.getAttribute_name_index());
				switch (name) {
				case AttributeInfo.LINENUMBERTABLE:
					LineNumberTable lineNumberTable = new LineNumberTable();
					lineNumberTable.parseSelf(attribute_info, cp_info);
					attributes.add(lineNumberTable);
					break;
				case AttributeInfo.LOCALVARIABLETABLE:
					LocalVariableTable localVariableTable = new LocalVariableTable();
					localVariableTable.parseSelf(attribute_info, cp_info);
					attributes.add(localVariableTable);
					break;
				case AttributeInfo.LOCALVARIABLETYPETABLE:
					LocalVariableTypeTable localVariableTypeTable = new LocalVariableTypeTable();
					localVariableTypeTable.parseSelf(attribute_info, cp_info);
					attributes.add(localVariableTypeTable);
					break;
				case AttributeInfo.STACKMAPTABLE:
					StackMapTable stackMapTable = new StackMapTable();
					stackMapTable.parseSelf(attribute_info, cp_info);
					attributes.add(stackMapTable);
					break;
				default:
					attributes.add(attribute_info);
					break;
				}
				
				
			}
			setAttributes(attributes);
		}
		
	}
	
	@Override
	public String print(int length) throws Exception {
		String space = TransformUtil.spaces(length);
		String result = "";
		try {
			Instructions instr = Instructions.getInstance();
			result += space + "max_stack : " + TransformUtil.bytesToInt(getMax_stack()) + "\n";
			result += space + "max_local : " + TransformUtil.bytesToInt(getMax_locals()) + "\n";
			result += space + "code_length : " + TransformUtil.bytesToInt(getCode_length()) + "\n";
			result += space + "code : \n" + instr.parse(getCode(), TransformUtil.spaces(length + 4)) + "\n";
			result += space + "exception_table length : " + TransformUtil.bytesToInt(getException_table_length()) + "\n";
			result += space + "attributes_count : " + TransformUtil.bytesToInt(getAttributes_count()) + "\n";
			List<AttributeInfo> attributes = getAttributes();
			for (int j = 0; j < attributes.size(); j++) {
//				String attr_name = ClassModelParser.getUTF8(cp_info, code_attributes.get(j).getAttribute_name_index());
//				if (attr_name.equals(AttributeInfo.STACKMAPTABLE)) {
					result += attributes.get(j).print(length + 4) + "\n";
//				}
			}
			
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
