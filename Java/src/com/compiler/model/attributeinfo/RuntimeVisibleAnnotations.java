package com.compiler.model.attributeinfo;

import java.util.ArrayList;
import java.util.List;

import com.compiler.model.constantpool.ConstantPoolInfo;
import com.compiler.util.TransformUtil;

public class RuntimeVisibleAnnotations extends AttributeInfo {
	private byte[] num_annotations;
	private List<Annotation> annotations;
	public byte[] getNum_annotations() {
		return num_annotations;
	}
	public void setNum_annotations(byte[] num_annotations) {
		this.num_annotations = num_annotations;
	}
	public List<Annotation> getAnnotations() {
		return annotations;
	}
	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}
	
	@Override
	public void parseSelf(AttributeInfo attributeInfo, List<ConstantPoolInfo> cp_info) throws Exception {
		super.parseSelf(attributeInfo, cp_info);
		
		byte[] info = attributeInfo.getInfo();
		int[] index = new int[]{0};
		setNum_annotations(TransformUtil.subBytes(info, index, 2));
		
		int num = TransformUtil.bytesToInt(getNum_annotations());
		if (num > 0) {
			List<Annotation> annotations = new ArrayList<Annotation>();
			for (int i = 0; i < num; i++) {
				Annotation annotation = new Annotation(cp_info);
				annotation.parseSelf(info, index);
				annotations.add(annotation);
			}
			setAnnotations(annotations);
		}
	}
	
	@Override
	public String print(int length) throws Exception {
		String space = TransformUtil.spaces(length);
		String space1 = TransformUtil.spaces(length + 4);
		String result = "";
		try {
			result += space + "RuntimeVisibleAnnotations : \n";
			result += space1 + "num_annotations : " + TransformUtil.bytesToInt(getNum_annotations())  + "\n";
			List<Annotation> annotations = getAnnotations();
			for (Annotation annotation : annotations) {
				result += annotation.print(length + 4);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
