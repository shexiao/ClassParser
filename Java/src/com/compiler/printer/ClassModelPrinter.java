package com.compiler.printer;

import java.util.List;

import com.compiler.model.ClassModel;
import com.compiler.model.FieldInfo;
import com.compiler.model.MethodInfo;
import com.compiler.model.attributeinfo.AttributeInfo;
import com.compiler.model.constantpool.ConstantPoolInfo;
import com.compiler.parser.ClassModelParser;
import com.compiler.util.TransformUtil;

public class ClassModelPrinter implements IPrinter{
	private int space_length = 4;
	private String space = TransformUtil.spaces(space_length);
	
	public void printHeader(ClassModel classModel) throws Exception {
		System.out.println(space + "Magic : " + TransformUtil.getHex(classModel.getMagic()));
		System.out.println(space + "Minor_Version : " + TransformUtil.bytesToInt(classModel.getMinor_version()));
		System.out.println(space + "Major_Version : " + TransformUtil.bytesToInt(classModel.getMajor_version()));
	}
	
	
	public void printConstantPoolInfo(ClassModel classModel) throws Exception {
		System.out.println(space + "cp_count : " + TransformUtil.bytesToInt(classModel.getConstant_pool_count()));
		System.out.println(space + "cp_info size : " + classModel.getConstant_pool().size());
		System.out.println(space + "cp_info: ");
		List<ConstantPoolInfo> cp_info = classModel.getConstant_pool();
		for (int index = 1; index < cp_info.size(); index++) {
			if (cp_info.get(index).getIndex() == 0) continue;
			System.out.println(space + cp_info.get(index).print(space_length + 4));
		}
	}
	
	
	public void printClassInfo(ClassModel classModel) throws Exception {
		System.out.println(space + "access_flag : " + ClassModelParser.parseClassAccessFlag(classModel.getAccess_flags()));
		System.out.println(space + "this_class : " + ClassModelParser.getConstatnClass(classModel.getConstant_pool(), classModel.getThis_class()));
		System.out.println(space + "super_class : " + ClassModelParser.getConstatnClass(classModel.getConstant_pool(), classModel.getSuper_class()));
		System.out.println(space + "interfaces count : " + TransformUtil.bytesToInt(classModel.getInterfaces_count()));
	}


	@Override
	public void printFieldInfo(ClassModel classModel) throws Exception {
		System.out.println(space + "fileds count : " + TransformUtil.bytesToInt(classModel.getFields_count()));
		List<FieldInfo> fields = classModel.getFields();
		if (fields != null) {
			for (FieldInfo field_info : fields) {
				System.out.println(space + "access_flag : " + ClassModelParser.parseFiledAccessFlag(field_info.getAccess_flags()));
				System.out.println(space + "name_index : " + ClassModelParser.getUTF8(classModel.getConstant_pool(), field_info.getName_index()));
				System.out.println(space + "descriptor_index : " + ClassModelParser.getUTF8(classModel.getConstant_pool(), field_info.getDescriptor_index()));
				System.out.println(space + "attributes count : " + TransformUtil.bytesToInt(field_info.getAttributes_count()));
				int count = TransformUtil.bytesToInt(field_info.getAttributes_count());
				if (count > 0) {
					List<AttributeInfo> attributes = field_info.getAttributes();
					List<ConstantPoolInfo> cp_info = classModel.getConstant_pool();
					for (int i = 0; i < attributes.size(); i++) {
						AttributeInfo attribute_info = attributes.get(i);			
						String name = ClassModelParser.getUTF8(cp_info, attribute_info.getAttribute_name_index());
						System.out.println(space + "attributes[" + i + "] name : " + name);
						System.out.println(space + "attributes[" + i + "] attributes length : " + TransformUtil.bytesToInt(attribute_info.getAttribute_length()));
						System.out.println(space + "attributes[" + i + "] attributes info : ");
						System.out.println(attribute_info.print(space_length + 4));						
					}
					
				}
			}
		}
	}


	@Override
	public void printMethodInfo(ClassModel classModel) throws Exception {
		System.out.println(space + "methods count : " + TransformUtil.bytesToInt(classModel.getMethods_count()));
		List<MethodInfo> methods = classModel.getMethods();
		if (methods != null) {
			for (MethodInfo method_info : methods) {
				System.out.println(space + "access_flag : " + ClassModelParser.parseMethodAccessFlag(method_info.getAccess_flags()));
				System.out.println(space + "name_index : " + ClassModelParser.getUTF8(classModel.getConstant_pool(), method_info.getName_index()));
				System.out.println(space + "descriptor_index : " + ClassModelParser.getUTF8(classModel.getConstant_pool(), method_info.getDescriptor_index()));
				int count = TransformUtil.bytesToInt(method_info.getAttributes_count());
				System.out.println(space + "attributes count : " + count);
				if (count > 0) {
					List<AttributeInfo> attributes = method_info.getAttributes();
					List<ConstantPoolInfo> cp_info = classModel.getConstant_pool();
					for (int i = 0; i < attributes.size(); i++) {
						AttributeInfo attribute_info = attributes.get(i);			
						String name = ClassModelParser.getUTF8(cp_info, attribute_info.getAttribute_name_index());
						System.out.println(space + "attributes[" + i + "] name : " + name);
						System.out.println(space + "attributes[" + i + "] attributes length : " + TransformUtil.bytesToInt(attribute_info.getAttribute_length()));
						System.out.println(space + "attributes[" + i + "] attributes info : ");
						System.out.println(attribute_info.print(space_length + 4));						
					}
					
				}
				System.out.println();
			}
		}
	}


	@Override
	public void printAttributeInfo(ClassModel classModel) throws Exception {
		System.out.println(space + "attributes count : " + TransformUtil.bytesToInt(classModel.getAttribute_count()));
		List<AttributeInfo> attributes = classModel.getAttributes();
		if (attributes != null) {
			for (AttributeInfo attribute_info : attributes) {
				System.out.println(attribute_info.print(space_length + 4));
			}
		}
	}

}
