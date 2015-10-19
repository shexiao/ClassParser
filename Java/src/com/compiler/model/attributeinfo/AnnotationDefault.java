package com.compiler.model.attributeinfo;

import java.util.List;

import com.compiler.model.constantpool.ConstantPoolInfo;
import com.compiler.util.TransformUtil;

public class AnnotationDefault extends AttributeInfo {
	private ElementValue default_value;

	public ElementValue getDefault_value() {
		return default_value;
	}

	public void setDefault_value(ElementValue default_value) {
		this.default_value = default_value;
	}
	
	@Override
	public void parseSelf(AttributeInfo attributeInfo, List<ConstantPoolInfo> cp_info) throws Exception {
		super.parseSelf(attributeInfo, cp_info);
		
		int[] index = new int[]{0};
		ElementValue element_value = new ElementValue(getCp_info());
		element_value.parseSelf(attributeInfo.getInfo(), index);
		setDefault_value(element_value);
	}
	
	@Override
	public String print(int length) throws Exception {
		String space = TransformUtil.spaces(length);
		String result = "";
		result += getDefault_value().print(space);
		return result;
	}
}
