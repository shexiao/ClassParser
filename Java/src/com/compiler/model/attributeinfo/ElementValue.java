package com.compiler.model.attributeinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.compiler.model.constantpool.ConstantPoolInfo;
import com.compiler.parser.ClassModelParser;
import com.compiler.util.TransformUtil;

public class ElementValue {
	private byte tag;
	private Map<String, Object> value;
	private List<ConstantPoolInfo> cp_info;
	
	public ElementValue(List<ConstantPoolInfo> cp_info) {
		this.cp_info = cp_info;
	}
	
	
	
	public List<ConstantPoolInfo> getCp_info() {
		return cp_info;
	}



	public void setCp_info(List<ConstantPoolInfo> cp_info) {
		this.cp_info = cp_info;
	}



	public byte getTag() {
		return tag;
	}

	public void setTag(byte tag) {
		this.tag = tag;
	}

	public Map<String, Object> getValue() {
		return value;
	}

	public void setValue(Map<String, Object> value) {
		this.value = value;
	}

	public void parseSelf(byte[] info, int[] index) throws Exception{
		setTag(TransformUtil.subBytes(info, index, 1)[0]);
		Map<String, Object> value = new HashMap<String, Object>();
		char tag_char = (char)getTag();
		switch (tag_char) {
		case 'B':
		case 'C':
		case 'D':
		case 'F':
		case 'I':
		case 'J':
		case 'S':
		case 'Z':
		case 's':
			value.put("const_name_index", TransformUtil.subBytes(info, index, 2));
			break;
		case 'e':
			Map<String, Object> enum_const_value = new HashMap<String, Object>();
			enum_const_value.put("type_name_index", TransformUtil.subBytes(info, index, 2));
			enum_const_value.put("const_name_index", TransformUtil.subBytes(info, index, 2));
			value.put("enum_const_value", enum_const_value);
			break;
		case 'c':
			value.put("class_info_index", TransformUtil.subBytes(info, index, 2));
			break;
		case '@':
			Annotation annotation = new Annotation(getCp_info());
			annotation.parseSelf(info, index);
			value.put("annotation", annotation);
			break;
		case '[':
			Map<String, Object> array_value = new HashMap<String, Object>();
			byte[] num_values = TransformUtil.subBytes(info, index, 2);
			array_value.put("num_values", num_values);
			
			int num = TransformUtil.bytesToInt(num_values);
			List<ElementValue> elem_vals = new ArrayList<ElementValue>();
			for (int i = 0; i < num; i++) {
				ElementValue elem_val = new ElementValue(getCp_info());
				elem_val.parseSelf(info, index);
				elem_vals.add(elem_val);
			}
			array_value.put("values", elem_vals);
			value.put("array_value", array_value);
			break;
		}
		setValue(value);
	}
	
	public String print(String space) throws Exception{
		String result = "";
		char tag = (char)getTag();
		result += space + "tag : " + tag + "\n";
		
		Map<String, Object> value = getValue();
		switch (tag) {
		case 'B':
		case 'C':
		case 'D':
		case 'F':
		case 'I':
		case 'J':
		case 'S':
		case 'Z':
		case 's':
			result += space + "const_name : " + TransformUtil.bytesToInt((byte[])value.get("const_name_index")) + "\n";
			break;
		case 'e':
			Map<String, Object> enum_const_value = (Map<String, Object>) value.get("enum_const_value");
			result += space + "type_name : " + ClassModelParser.getUTF8(cp_info, (byte[])enum_const_value.get("type_name_index")) + "\n";
			result += space + "const_name : " + ClassModelParser.getUTF8(cp_info, (byte[])enum_const_value.get("const_name_index")) + "\n";
			break;
		case 'c':
			result += space + "class_info : " + ClassModelParser.getUTF8(cp_info, (byte[])value.get("class_info_index")) + "\n";
			break;
		case '@':
			Annotation annotation = (Annotation) value.get("annotation");
			result += annotation.toString();
			break;
		case '[':
			Map<String, Object> array_value = (Map<String, Object>)value.get("array_value");
			byte[] num_values = (byte[])array_value.get("num_values");
			result += space + "num_values : " + TransformUtil.bytesToInt(num_values) + "\n";

			List<ElementValue> elem_vals = (List<ElementValue>)array_value.get("values");
			for (int i = 0; i < elem_vals.size(); i++) {
				ElementValue elem_val = (ElementValue)elem_vals.get(i);
				result += elem_val.print(space);
			}
			break;
		}
		return result;
	}
}
