package com.compiler.model.attributeinfo;

import java.util.ArrayList;
import java.util.List;

import com.compiler.model.constantpool.ConstantPoolInfo;
import com.compiler.parser.ClassModelParser;
import com.compiler.util.TransformUtil;

public class InnerClasses extends AttributeInfo {
	private byte[] number_of_classes;
	private List<Classes> classes;

	public byte[] getNumber_of_classes() {
		return number_of_classes;
	}

	public void setNumber_of_classes(byte[] number_of_classes) {
		this.number_of_classes = number_of_classes;
	}

	public List<Classes> getClasses() {
		return classes;
	}

	public void setClasses(List<Classes> classes) {
		this.classes = classes;
	}
	
	@Override
	public void parseSelf(AttributeInfo attributeInfo, List<ConstantPoolInfo> cp_info) throws Exception {
		super.parseSelf(attributeInfo, cp_info);
		byte[] info = attributeInfo.getInfo();
		
		int[] index = new int[]{0};
		setNumber_of_classes(TransformUtil.subBytes(info, index, 2));
		
		int number = TransformUtil.bytesToInt(getNumber_of_classes());
		if (number > 0) {
			List<Classes> clses = new ArrayList<Classes>();
			for (int i = 0; i < number; i++) {
				Classes cls = new Classes();
				cls.setInner_class_info_index(TransformUtil.subBytes(info, index, 2));
				cls.setOuter_class_info_index(TransformUtil.subBytes(info, index, 2));
				cls.setInner_name_index(TransformUtil.subBytes(info, index, 2));
				cls.setInner_class_access_flags(TransformUtil.subBytes(info, index, 2));
				
				clses.add(cls);
			}
			setClasses(clses);
		}
	}
	
	@Override
	public String print(int length) throws Exception {
		String space = TransformUtil.spaces(length);
		String space1 = TransformUtil.spaces(length + 4);
		String result = "";
		try {
			if (TransformUtil.bytesToInt(getNumber_of_classes()) > 0) {
				int index = 0;
				for (Classes cls : getClasses()) {
					result += space + "InnerClass[" + index + "]: \n";
					result += space1 + "inner_class_info : " + ClassModelParser.getConstatnClass(getCp_info(), cls.getInner_class_info_index()) + "\n";
					result += space1 + "outer_class_info : " + ClassModelParser.getConstatnClass(getCp_info(), cls.getOuter_class_info_index()) + "\n";
					result += space1 + "inner_name : " + ClassModelParser.getUTF8(getCp_info(), cls.getInner_name_index()) + "\n";
					result += space1 + "inner_class_access_flag : " + ClassModelParser.parseInnerClassAccessFlag(cls.getInner_class_access_flags()) + "\n";
					index++;
				}
			}
		} catch (Exception e) {
			
		}
		return result;
	}

	class Classes {
		private byte[] inner_class_info_index;
		private byte[] outer_class_info_index;
		private byte[] inner_name_index;
		private byte[] inner_class_access_flags;

		public byte[] getInner_class_info_index() {
			return inner_class_info_index;
		}

		public void setInner_class_info_index(byte[] inner_class_info_index) {
			this.inner_class_info_index = inner_class_info_index;
		}

		public byte[] getOuter_class_info_index() {
			return outer_class_info_index;
		}

		public void setOuter_class_info_index(byte[] outer_class_info_index) {
			this.outer_class_info_index = outer_class_info_index;
		}

		public byte[] getInner_name_index() {
			return inner_name_index;
		}

		public void setInner_name_index(byte[] inner_name_index) {
			this.inner_name_index = inner_name_index;
		}

		public byte[] getInner_class_access_flags() {
			return inner_class_access_flags;
		}

		public void setInner_class_access_flags(byte[] inner_class_access_flags) {
			this.inner_class_access_flags = inner_class_access_flags;
		}

	}
}
