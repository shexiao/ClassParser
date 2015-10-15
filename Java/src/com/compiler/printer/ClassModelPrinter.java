package com.compiler.printer;

import java.util.List;

import com.compiler.model.ClassModel;
import com.compiler.model.FieldInfo;
import com.compiler.model.MethodInfo;
import com.compiler.model.attributeinfo.AttributeInfo;
import com.compiler.model.attributeinfo.Code;
import com.compiler.model.constantpool.ConstantPoolInfo;
import com.compiler.parser.ClassModelParser;
import com.compiler.util.TransformUtil;

public class ClassModelPrinter implements IPrinter{
	
	
	public void printHeader(ClassModel classModel) throws Exception {
		System.out.println("\tMagic : " + TransformUtil.getHex(classModel.getMagic()));
		System.out.println("\tMinor_Version : " + TransformUtil.bytesToInt(classModel.getMinor_version()));
		System.out.println("\tMajor_Version : " + TransformUtil.bytesToInt(classModel.getMajor_version()));
	}
	
	
	public void printConstantPoolInfo(ClassModel classModel) throws Exception {
		System.out.println("\tcp_count : " + TransformUtil.bytesToInt(classModel.getConstant_pool_count()));
		System.out.println("\tcp_info size : " + classModel.getConstant_pool().size());
		System.out.println("\tcp_info: ");
		List<ConstantPoolInfo> cp_info = classModel.getConstant_pool();
		for (int index = 1; index < cp_info.size(); index++) {
			if (cp_info.get(index).getIndex() == 0) continue;
			System.out.println("\t" + cp_info.get(index).toString());
		}
	}
	
	
	public void printClassInfo(ClassModel classModel) throws Exception {
		System.out.println("\taccess_flag : " + ClassModelParser.parseClassAccessFlag(classModel.getAccess_flags()));
		System.out.println("\tthis_class : " + ClassModelParser.getConstatnClass(classModel.getConstant_pool(), classModel.getThis_class()));
		System.out.println("\tsuper_class : " + ClassModelParser.getConstatnClass(classModel.getConstant_pool(), classModel.getSuper_class()));
		System.out.println("\tinterfaces count : " + TransformUtil.bytesToInt(classModel.getInterfaces_count()));
	}


	@Override
	public void printFieldInfo(ClassModel classModel) throws Exception {
		System.out.println("\tfileds count : " + TransformUtil.bytesToInt(classModel.getFields_count()));
		List<FieldInfo> fields = classModel.getFields();
		if (fields != null) {
			for (FieldInfo field_info : fields) {
				System.out.println("\taccess_flag : " + ClassModelParser.parseFiledAccessFlag(field_info.getAccess_flags()));
				System.out.println("\tname_index : " + ClassModelParser.getUTF8(classModel.getConstant_pool(), field_info.getName_index()));
				System.out.println("\tdescriptor_index : " + ClassModelParser.getUTF8(classModel.getConstant_pool(), field_info.getDescriptor_index()));
				System.out.println("\tattributes count : " + TransformUtil.bytesToInt(field_info.getAttributes_count()));
			}
		}
	}


	@Override
	public void printMethodInfo(ClassModel classModel) throws Exception {
		System.out.println("\tmethods count : " + TransformUtil.bytesToInt(classModel.getMethods_count()));
		List<MethodInfo> methods = classModel.getMethods();
		if (methods != null) {
			for (MethodInfo method_info : methods) {
				System.out.println("\taccess_flag : " + ClassModelParser.parseMethodAccessFlag(method_info.getAccess_flags()));
				System.out.println("\tname_index : " + ClassModelParser.getUTF8(classModel.getConstant_pool(), method_info.getName_index()));
				System.out.println("\tdescriptor_index : " + ClassModelParser.getUTF8(classModel.getConstant_pool(), method_info.getDescriptor_index()));
				int count = TransformUtil.bytesToInt(method_info.getAttributes_count());
				System.out.println("\tattributes count : " + count);
				if (count > 0) {
					List<AttributeInfo> attributes = method_info.getAttributes();
					List<ConstantPoolInfo> cp_info = classModel.getConstant_pool();
					for (int i = 0; i < attributes.size(); i++) {
						AttributeInfo attribute_info = attributes.get(i);			
						String name = ClassModelParser.getUTF8(cp_info, attribute_info.getAttribute_name_index());
						System.out.println("\tattributes[" + i + "] name : " + name);
						System.out.println("\tattributes[" + i + "] attributes length : " + TransformUtil.bytesToInt(attribute_info.getAttribute_length()));
						if (name.equals(AttributeInfo.CODE)) {
							Code code = (Code) attribute_info;
							System.out.println("\tattributes[" + i + "] attributes info : ");
							System.out.println(code.toString());
							
							List<AttributeInfo> code_attributes = code.getAttributes();
							for (int j = 0; j < code_attributes.size(); j++) {
								System.out.println(code_attributes.get(j).toString());
							}
						} else {
							System.out.println("\t\t" + attribute_info.toString());
						}
						
						
					}
					
				}
				System.out.println();
			}
		}
	}


	@Override
	public void printAttributeInfo(ClassModel classModel) throws Exception {
		System.out.println("\tattributes count : " + TransformUtil.bytesToInt(classModel.getAttribute_count()));
		List<AttributeInfo> attributes = classModel.getAttributes();
		if (attributes != null) {
			for (AttributeInfo attribute_info : attributes) {
				System.out.println(attribute_info.toString());
			}
		}
	}

}
