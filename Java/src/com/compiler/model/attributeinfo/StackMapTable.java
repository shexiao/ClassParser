package com.compiler.model.attributeinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.compiler.flag.StackMapTableFlag;
import com.compiler.model.constantpool.ConstantPoolInfo;
import com.compiler.parser.ClassModelParser;
import com.compiler.util.TransformUtil;

public class StackMapTable extends AttributeInfo {
	private byte[] number_of_entries;
	private List<Map<String, Object>> entries;

	public byte[] getNumber_of_entries() {
		return number_of_entries;
	}

	public void setNumber_of_entries(byte[] number_of_entries) {
		this.number_of_entries = number_of_entries;
	}
	
	public List<Map<String, Object>> getEntries() {
		return entries;
	}

	public void setEntries(List<Map<String, Object>> entries) {
		this.entries = entries;
	}

	@Override
	public void parseSelf(AttributeInfo attributeInfo, List<ConstantPoolInfo> cp_info) throws Exception {
		super.parseSelf(attributeInfo, cp_info);
		
		int[] index = new int[]{0};
		byte[] info = attributeInfo.getInfo();
		setNumber_of_entries(TransformUtil.subBytes(info, index, 2));
		
		int number = TransformUtil.bytesToInt(getNumber_of_entries());
		if (number > 0) {
			List<Map<String, Object>> entries = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < number; i++) {
				Map<String, Object> entry = new HashMap<String, Object>();
				byte[] frame_type = TransformUtil.subBytes(info, index, 1);
				entry.put("frame_type", frame_type[0]);
				int frame_type_int = Byte.toUnsignedInt(frame_type[0]);
				if (frame_type_int > Byte.toUnsignedInt(StackMapTableFlag.SAME) && frame_type_int <= Byte.toUnsignedInt(StackMapTableFlag.SAME_LOCALS_1_STACK_ITEM)) {
					Map<String, Object> stack = new HashMap<String, Object>();
					parseTypeInfo(stack, info, index);
					entry.put("stack", stack);
					
				} else if (frame_type[0] == StackMapTableFlag.SAME_LOCALS_1_STACK_ITEM_EXTENDED) {
					Map<String, Object> stack = new HashMap<String, Object>();
					byte[] offset_delta = TransformUtil.subBytes(info, index, 2);
					entry.put("offset_delta", offset_delta);
					parseTypeInfo(stack, info, index);
					entry.put("stack", stack);
					
				} else if ((frame_type_int > Byte.toUnsignedInt(StackMapTableFlag.SAME_LOCALS_1_STACK_ITEM_EXTENDED) && frame_type_int <= Byte.toUnsignedInt(StackMapTableFlag.CHOP)) || (frame_type[0]) == (StackMapTableFlag.SAME_FRAME_EXTENDED)) {
					byte[] offset_delta = TransformUtil.subBytes(info, index, 2);
					entry.put("offset_delta", offset_delta);
					
				} else if (frame_type_int > Byte.toUnsignedInt(StackMapTableFlag.SAME_FRAME_EXTENDED) && frame_type_int <= Byte.toUnsignedInt(StackMapTableFlag.APPEND)) {
					byte[] offset_delta = TransformUtil.subBytes(info, index, 2);
					entry.put("offset_delta", offset_delta);
					int count = (frame_type[0] & 0x0ff) - 251;
					List<Map<String, Object>> locals = new ArrayList<Map<String, Object>>();
					for (int j = 0; j < count; j++) {
						Map<String, Object> local = new HashMap<String, Object>();
						parseTypeInfo(local, info, index);
						locals.add(local);
					}
					entry.put("locals", locals);
					
				} else if (frame_type[0] == StackMapTableFlag.FULL_FRAME) {
					byte[] offset_delta = TransformUtil.subBytes(info, index, 2);
					entry.put("offset_delta", offset_delta);
					
					byte[] number_of_locals = TransformUtil.subBytes(info, index, 2);
					entry.put("number_of_locals", number_of_locals);
					
					int num_of_locals = TransformUtil.bytesToInt(number_of_locals);
					List<Map<String, Object>> locals = new ArrayList<Map<String, Object>>();
					for (int s = 0; s < num_of_locals; s++) {
						Map<String, Object> local = new HashMap<String, Object>();
						parseTypeInfo(local, info, index);
						locals.add(local);
					}
					entry.put("locals", locals);
					
					byte[] number_of_stack_items = TransformUtil.subBytes(info, index, 2);
					entry.put("number_of_stack_items", number_of_stack_items);
					
					int num_of_stack = TransformUtil.bytesToInt(number_of_stack_items);
					List<Map<String, Object>> stacks = new ArrayList<Map<String, Object>>();
					for (int t = 0; t < num_of_stack; t++) {
						Map<String, Object> stack = new HashMap<String, Object>();
						parseTypeInfo(stack, info, index);
						stacks.add(stack);
					}
					entry.put("stacks", stacks);
					
				}
				entries.add(entry);
			}
			setEntries(entries);
		}
	}
	
	private void parseTypeInfo(Map<String, Object> type_info, byte[] info, int[] index) throws Exception {
		byte[] tag = TransformUtil.subBytes(info, index, 1);
		type_info.put("tag", tag[0]);
		if (tag[0] == StackMapTableFlag.ITEM_UNINITIALIZED ) {
			byte[] offset = TransformUtil.subBytes(info, index, 2);
			type_info.put("offset", offset);
		} else if (tag[0] == StackMapTableFlag.ITEM_OBJECT) {
			byte[] cpool_index = TransformUtil.subBytes(info, index, 2);
			type_info.put("cpool_index", cpool_index);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String print(int length) throws Exception {
		String result = "";
		try {
			result += "\tStackMapTable :\n";
			int num_of_entries = TransformUtil.bytesToInt(getNumber_of_entries());
			for (int i = 0; i < num_of_entries; i++) {
				result += "\tentries[" + i + "]:\n";
				Map<String, Object> entry = this.entries.get(i);
				byte frame_type = (byte) entry.get("frame_type");
				result += "\t\tframe_type : " + StackMapTableFlag.parseFrameType(frame_type) + "\n";
				
				int frame_type_int = Byte.toUnsignedInt(frame_type);
				if (frame_type_int > Byte.toUnsignedInt(StackMapTableFlag.SAME) && frame_type_int <= Byte.toUnsignedInt(StackMapTableFlag.SAME_LOCALS_1_STACK_ITEM)) {
					result += "\t\tstack :\n";
					Map<String, Object> stack = (Map<String, Object>)entry.get("stack");
					result += typeInfo(stack);
					
				} else if (frame_type == StackMapTableFlag.SAME_LOCALS_1_STACK_ITEM_EXTENDED) {
					byte[] offset_delta = (byte[]) entry.get("offset_delta");
					result += "\t\toffset_delta : " + TransformUtil.bytesToInt(offset_delta) + "\n";
					Map<String, Object> stack = (Map<String, Object>) entry.get("stack");
					result += typeInfo(stack);
					
				} else if ((frame_type_int > Byte.toUnsignedInt(StackMapTableFlag.SAME_LOCALS_1_STACK_ITEM_EXTENDED) && frame_type_int <= Byte.toUnsignedInt(StackMapTableFlag.CHOP)) || frame_type == StackMapTableFlag.SAME_FRAME_EXTENDED) {
					byte[] offset_delta = (byte[]) entry.get("offset_delta");
					result += "\t\toffset_delta : " + TransformUtil.bytesToInt(offset_delta) + "\n";
					
				} else if (frame_type_int > Byte.toUnsignedInt(StackMapTableFlag.SAME_FRAME_EXTENDED) && frame_type_int <= Byte.toUnsignedInt(StackMapTableFlag.APPEND)) {
					byte[] offset_delta = (byte[]) entry.get("offset_delta");
					result += "\t\toffset_delta : " + TransformUtil.bytesToInt(offset_delta) + "\n";
					List<Map<String, Object>> locals = (List<Map<String, Object>>) entry.get("locals");
					int index = 0;
					for (Map<String, Object> local : locals) {
						result += "\t\tlocal[" + index + "]\n";
						result += typeInfo(local);
						index++;
					}
				} else if (frame_type == StackMapTableFlag.FULL_FRAME) {
					byte[] offset_delta = (byte[]) entry.get("offset_delta");
					result += "\t\toffset_delta : " + TransformUtil.bytesToInt(offset_delta) + "\n";
					
					byte[] number_of_locals = (byte[]) entry.get("number_of_locals");
					result += "\t\tnumber_of_locals : " + TransformUtil.bytesToInt(number_of_locals) + "\n";
					List<Map<String, Object>> locals = (List<Map<String, Object>>) entry.get("locals");
					int index = 0;
					for (Map<String, Object> local : locals) {
						result += "\t\tlocal["  + index +  "]\n";
						result += typeInfo(local);
						index++;
					}
					
					byte[] number_of_stack_items = (byte[]) entry.get("number_of_stack_items");
					result += "\t\tnumber_of_stack_items : " + TransformUtil.bytesToInt(number_of_stack_items) + "\n";
					List<Map<String, Object>> stacks = (List<Map<String, Object>>)entry.get("stacks");
					index = 0;
					for (Map<String, Object> stack : stacks) {
						result += "\t\tstack[" + index + "]\n";
						result += typeInfo(stack);
						index++;
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private String typeInfo(Map<String, Object> type_info) throws Exception{
		String result = "";
		byte tag = (byte)type_info.get("tag");
		result += "\t\t\ttag : " + StackMapTableFlag.parseTag(tag) + "\n";
		if (tag == StackMapTableFlag.ITEM_UNINITIALIZED ) {
			byte[] offset = (byte[])type_info.get("offset");
			result += "\t\t\toffset : " + TransformUtil.bytesToInt(offset) + "\n";

		} else if (tag == StackMapTableFlag.ITEM_OBJECT) {
			byte[] cpool_index = (byte[])type_info.get("cpool_index");
			result += "\t\t\tcpool_index : " + ClassModelParser.getConstatnClass(getCp_info(), cpool_index) + "\n";
		}
		return result;
	}
}
