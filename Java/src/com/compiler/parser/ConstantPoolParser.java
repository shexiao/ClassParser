package com.compiler.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.compiler.model.ClassModel;
import com.compiler.model.constantpool.ConstantClassInfo;
import com.compiler.model.constantpool.ConstantIntegerOrFloatInfo;
import com.compiler.model.constantpool.ConstantInvokeDynamicInfo;
import com.compiler.model.constantpool.ConstantLongOrDoubleInfo;
import com.compiler.model.constantpool.ConstantMethodHandleInfo;
import com.compiler.model.constantpool.ConstantMethodTypeInfo;
import com.compiler.model.constantpool.ConstantNameAndTypeInfo;
import com.compiler.model.constantpool.ConstantPoolInfo;
import com.compiler.model.constantpool.ConstantRefInfo;
import com.compiler.model.constantpool.ConstantStringInfo;
import com.compiler.model.constantpool.ConstantUtf8Info;
import com.compiler.util.TransformUtil;

public class ConstantPoolParser implements IParser {
	private IParser next = null;
	
	@Override
	public void addNext(IParser next) {
		this.next = next;
	}

	@Override
	public void parse(ClassModel classModel, InputStream in) throws Exception {
		byte[] bb = new byte[2];
		in.read(bb, 0, 2);
		
		classModel.setConstant_pool_count(bb);
			
		List<ConstantPoolInfo> cp_info = new ArrayList<ConstantPoolInfo>();
		cp_info.add(new ConstantPoolInfo());//
		int cp_length = TransformUtil.bytesToInt(bb);
		int index = 1;
		int step;
		while (index < cp_length) {
			step = parseDetail(classModel, in, cp_info, index);
			if (step == 2) {
				cp_info.add(new ConstantPoolInfo());
			}
			index += step;
		}
		
		classModel.setConstant_pool(cp_info);
		
		if (this.next != null) {
			this.next.parse(classModel, in);
		}
	}
	
	private int parseDetail(ClassModel classModel, InputStream in, List<ConstantPoolInfo> cp_info, int index) throws Exception{
		byte[] bb = new byte[1];
		in.read(bb, 0, 1);
		byte tag = bb[0];
		//System.out.printf("tag : %x\n", tag);
		switch (tag) {
		case ConstantPoolInfo.CONSTANT_CLASS:
			ConstantClassInfo const_class_info = new ConstantClassInfo();
			const_class_info.parse(in, tag, index);
			cp_info.add(const_class_info);
			return 1;
			
		case ConstantPoolInfo.CONSTANT_FIELDREF:
		case ConstantPoolInfo.CONSTANT_METHODREF:
		case ConstantPoolInfo.CONSTANT_INTERFACEMETHODREF:
			ConstantRefInfo const_ref_info = new ConstantRefInfo();
			const_ref_info.parse(in, tag, index);
			cp_info.add(const_ref_info);
			return 1;
			
		case ConstantPoolInfo.CONSTANT_STRING:
			ConstantStringInfo const_string_info = new ConstantStringInfo();
			const_string_info.parse(in, tag, index);
			cp_info.add(const_string_info);
			return 1;
			
		case ConstantPoolInfo.CONSTANT_INTEGER:			
		case ConstantPoolInfo.CONSTANT_FLOAT:
			ConstantIntegerOrFloatInfo const_int_or_float_info = new ConstantIntegerOrFloatInfo();
			const_int_or_float_info.parse(in, tag, index);
			cp_info.add(const_int_or_float_info);
			return 1;
			
		case ConstantPoolInfo.CONSTANT_LONG:
		case ConstantPoolInfo.CONSTANT_DOUBLE:
			ConstantLongOrDoubleInfo const_long_or_double_info = new ConstantLongOrDoubleInfo();
			const_long_or_double_info.parse(in, tag, index);
			cp_info.add(const_long_or_double_info);
			return 2;
			
		case ConstantPoolInfo.CONSTANT_NAMEANDTYPE:
			ConstantNameAndTypeInfo const_nameandtype_info = new ConstantNameAndTypeInfo();
			const_nameandtype_info.parse(in, tag, index);
			cp_info.add(const_nameandtype_info);
			return 1;
			
		case ConstantPoolInfo.CONSTANT_UTF8:
			ConstantUtf8Info const_utf8_info = new ConstantUtf8Info();
			const_utf8_info.parse(in, tag, index);
			cp_info.add(const_utf8_info);
			return 1;
			
		case ConstantPoolInfo.CONSTANT_METHODHANDLE:
			ConstantMethodHandleInfo const_methodhandle_info = new ConstantMethodHandleInfo();
			const_methodhandle_info.parse(in, tag, index);
			cp_info.add(const_methodhandle_info);
			return 1;
			
		case ConstantPoolInfo.CONSTANT_MOTHODTYPE:
			ConstantMethodTypeInfo const_methodtype_info = new ConstantMethodTypeInfo();
			const_methodtype_info.parse(in, tag, index);
			cp_info.add(const_methodtype_info);
			return 1;
			
		case ConstantPoolInfo.CONSTANT_INVOKEDYNAMIC:
			ConstantInvokeDynamicInfo const_invokedynamic_info = new ConstantInvokeDynamicInfo();
			const_invokedynamic_info.parse(in, tag, index);
			cp_info.add(const_invokedynamic_info);
			return 1;
		}
		return 1;
		//System.out.printf("tag : %x\n", tag);
	}

}
