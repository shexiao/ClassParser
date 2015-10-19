package com.compiler.model.attributeinfo;

import java.util.ArrayList;
import java.util.List;

import com.compiler.model.constantpool.ConstantPoolInfo;
import com.compiler.util.TransformUtil;

public class RuntimeVisibleParameterAnnotations extends AttributeInfo {
	private byte num_parameters;
	private List<Parameter> parameters;

	public byte getNum_parameters() {
		return num_parameters;
	}

	public void setNum_parameters(byte num_parameters) {
		this.num_parameters = num_parameters;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	
	@Override
	public void parseSelf(AttributeInfo attributeInfo, List<ConstantPoolInfo> cp_info) throws Exception {
		super.parseSelf(attributeInfo, cp_info);
		
		byte[] info = attributeInfo.getInfo();
		int[] index = new int[]{0};
		setNum_parameters(TransformUtil.subBytes(info, index, 1)[0]);
		
		int num_param = TransformUtil.bytesToInt(new byte[]{getNum_parameters()});
		if (num_param > 0) {
			List<Parameter> parameters = new ArrayList<Parameter>();
			for (int i = 0; i < num_param; i++) {
				Parameter parameter = new Parameter();
				parameter.setNum_annotations(TransformUtil.subBytes(info, index, 2));
				
				int num_anno = TransformUtil.bytesToInt(parameter.getNum_annotations());
				if (num_anno > 0) {
					List<Annotation> annotations = new ArrayList<Annotation>();
					for (int j = 0; j < num_anno; j++) {
						Annotation annotation = new Annotation(cp_info);
						annotation.parseSelf(info, index);
						annotations.add(annotation);
					}
					parameter.setAnnotations(annotations);
				}
				setParameters(parameters);
			}
		}
	}
	
	@Override
	public String print(int length) throws Exception {
		String space = TransformUtil.spaces(length);
		String space1 = TransformUtil.spaces(length + 4);
		String space2 = TransformUtil.spaces(length + 8);
		String result = "";
		try {
			result += space + "RuntimeInvisibleParameterAnnotations : \n";
			result += space1 + "num_parameters : " + TransformUtil.bytesToInt(new byte[]{getNum_parameters()})  + "\n";
			List<Parameter> parameters = getParameters();
			for (Parameter parameter : parameters) {
				result += space1 + "parameter: \n";
				result += space2 + "num_annotations: " + TransformUtil.bytesToInt(parameter.getNum_annotations()) + "\n";
				List<Annotation> annotations = parameter.getAnnotations();
				for (Annotation annotation : annotations) {
					result += annotation.print(length + 8);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	class Parameter {
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

	}
}
