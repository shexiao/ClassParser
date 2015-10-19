package com.compiler.model.attributeinfo;

import java.util.ArrayList;
import java.util.List;

import com.compiler.model.constantpool.ConstantPoolInfo;
import com.compiler.parser.ClassModelParser;
import com.compiler.util.TransformUtil;

public class Annotation {
	private byte[] type_index;
	private byte[] num_element_value_pairs;
	private List<ElementValuePairs> element_value_pairs;

	private List<ConstantPoolInfo> cp_info;

	public Annotation(List<ConstantPoolInfo> cp_info) {
		this.cp_info = cp_info;
	}

	public List<ConstantPoolInfo> getCp_info() {
		return cp_info;
	}

	public void setCp_info(List<ConstantPoolInfo> cp_info) {
		this.cp_info = cp_info;
	}

	public byte[] getType_index() {
		return type_index;
	}

	public void setType_index(byte[] type_index) {
		this.type_index = type_index;
	}

	public byte[] getNum_element_value_pairs() {
		return num_element_value_pairs;
	}

	public void setNum_element_value_pairs(byte[] num_element_value_pairs) {
		this.num_element_value_pairs = num_element_value_pairs;
	}

	public List<ElementValuePairs> getElement_value_pairs() {
		return element_value_pairs;
	}

	public void setElement_value_pairs(List<ElementValuePairs> element_value_pairs) {
		this.element_value_pairs = element_value_pairs;
	}

	public void parseSelf(byte[] info, int[] index) throws Exception {
		setType_index(TransformUtil.subBytes(info, index, 2));
		setNum_element_value_pairs(TransformUtil.subBytes(info, index, 2));

		int num_elem = TransformUtil.bytesToInt(getNum_element_value_pairs());
		if (num_elem > 0) {
			List<ElementValuePairs> element_value_pairs = new ArrayList<ElementValuePairs>();
			for (int j = 0; j < num_elem; j++) {
				ElementValuePairs element_value_pair = new ElementValuePairs();
				element_value_pair.setElement_name_index(TransformUtil.subBytes(info, index, 2));

				ElementValue value = new ElementValue(getCp_info());
				value.parseSelf(info, index);
				element_value_pair.setValue(value);
				element_value_pairs.add(element_value_pair);
			}
			setElement_value_pairs(element_value_pairs);
		}
	}

	public String print(int length) {
		String space = TransformUtil.spaces(length);
		String space1 = TransformUtil.spaces(length + 4);
		String result = "";
		try {
			result += space + "annotation : \n";
			result += space + "type : " + ClassModelParser.getUTF8(cp_info, getType_index()) + "\n";
			result += space + "num_element_value_pairs : " + TransformUtil.bytesToInt(getNum_element_value_pairs())
					+ "\n";
			List<ElementValuePairs> element_value_pairs = getElement_value_pairs();
			for (ElementValuePairs element_value_pair : element_value_pairs) {
				result += space1 + "element_value_pair : \n";
				result += space1 + "element_name : "
						+ ClassModelParser.getUTF8(cp_info, element_value_pair.getElement_name_index()) + "\n";
				ElementValue element_value = element_value_pair.getValue();
				result += element_value.print(space1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	class ElementValuePairs {
		private byte[] element_name_index;
		private ElementValue value;

		public byte[] getElement_name_index() {
			return element_name_index;
		}

		public void setElement_name_index(byte[] element_name_index) {
			this.element_name_index = element_name_index;
		}

		public ElementValue getValue() {
			return value;
		}

		public void setValue(ElementValue value) {
			this.value = value;
		}

	}

}
