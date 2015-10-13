package com.compiler;

import com.compiler.flag.AccessFlag;
import com.compiler.model.attributeinfo.Code;
import com.compiler.util.TransformUtil;

public class ClassModelParser {
	public static String parseClassAccessFlag(byte[] access_flag) {
		if (access_flag.length != 2) return "";
		
		String result = "";
		int flag = (access_flag[0] << 2) | access_flag[1];
//		System.out.printf("flag : %x,%x\n", access_flag[0], access_flag[1]);
//		System.out.printf("flag : %x\n", flag);
		if ((flag & AccessFlag.CLASS_ACC_PUBLIC) == AccessFlag.CLASS_ACC_PUBLIC) {
			result += "public ";
		}
		if ((flag & AccessFlag.CLASS_ACC_FINAL) == AccessFlag.CLASS_ACC_FINAL) {
			result += "final ";
		}
		if ((flag & AccessFlag.CLASS_ACC_SUPER) == AccessFlag.CLASS_ACC_SUPER) {
			result += "super ";
		}
		if ((flag & AccessFlag.CLASS_ACC_INTERFACE) == AccessFlag.CLASS_ACC_INTERFACE) {
			result += "interface ";
		}
		if ((flag & AccessFlag.CLASS_ACC_ABSTRACT) == AccessFlag.CLASS_ACC_ABSTRACT) {
			result += "abstract ";
		}
		if ((flag & AccessFlag.CLASS_ACC_SYNTHETIC) == AccessFlag.CLASS_ACC_SYNTHETIC) {
			result += "synthetic ";
		}
		if ((flag & AccessFlag.CLASS_ACC_ANNOTATION) == AccessFlag.CLASS_ACC_ANNOTATION) {
			result += "annotation ";
		}
		if ((flag & AccessFlag.CLASS_ACC_ENUM) == AccessFlag.CLASS_ACC_ENUM) {
			result += "enum ";
		}
		if (result.length() > 0) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}
	
	public static String parseFiledAccessFlag(byte[] access_flag) {
		if (access_flag.length != 2) return "";
		
		String result = "";
		int flag = (access_flag[0] << 2) | access_flag[1];
		if ((flag & AccessFlag.FIELD_ACC_PUBLIC) == AccessFlag.FIELD_ACC_PUBLIC) {
			result += "public ";
		}
		if ((flag & AccessFlag.FIELD_ACC_PRIVATE) == AccessFlag.FIELD_ACC_PRIVATE) {
			result += "private ";
		}
		if ((flag & AccessFlag.FIELD_ACC_PROTECTED) == AccessFlag.FIELD_ACC_PROTECTED) {
			result += "protected ";
		}
		if ((flag & AccessFlag.FIELD_ACC_STATIC) == AccessFlag.FIELD_ACC_STATIC) {
			result += "static ";
		}
		if ((flag & AccessFlag.FIELD_ACC_FINAL) == AccessFlag.FIELD_ACC_FINAL) {
			result += "final ";
		}
		if ((flag & AccessFlag.FIELD_ACC_VOLATILE) == AccessFlag.FIELD_ACC_VOLATILE) {
			result += "valatile ";
		}
		if ((flag & AccessFlag.FIELD_ACC_TRANSIENT) == AccessFlag.FIELD_ACC_TRANSIENT) {
			result += "transient ";
		}
		if ((flag & AccessFlag.FIELD_ACC_SYMTHETIC) == AccessFlag.FIELD_ACC_SYMTHETIC) {
			result += "symthetic ";
		}
		if ((flag & AccessFlag.FIELD_ACC_ENUM) == AccessFlag.FIELD_ACC_ENUM) {
			result += "enum ";
		}
		
		if (result.length() > 0) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}
	
	public static String parseMethodAccessFlag(byte[] access_flag) {
		if (access_flag.length != 2) return "";
		String result = "";
		
		int flag = (access_flag[0] << 2) | access_flag[1];
		if ((flag & AccessFlag.METHOD_ACC_PUBLIC) == AccessFlag.METHOD_ACC_PUBLIC) {
			result += "public ";
		}
		if ((flag & AccessFlag.METHOD_ACC_PRIVATE) == AccessFlag.METHOD_ACC_PRIVATE) {
			result += "private ";
		}
		if ((flag & AccessFlag.METHOD_ACC_PROTECTED) == AccessFlag.METHOD_ACC_PROTECTED) {
			result += "protected ";
		}
		if ((flag & AccessFlag.METHOD_ACC_STATIC) == AccessFlag.METHOD_ACC_STATIC) {
			result += "static ";
		}
		if ((flag & AccessFlag.METHOD_ACC_FINAL) == AccessFlag.METHOD_ACC_FINAL) {
			result += "final ";
		}
		if ((flag & AccessFlag.METHOD_ACC_SYNCHRONIZED) == AccessFlag.METHOD_ACC_SYNCHRONIZED) {
			result += "synchronized ";
		}
		if ((flag & AccessFlag.METHOD_ACC_BRIDGE) == AccessFlag.METHOD_ACC_BRIDGE) {
			result += "bridge ";
		}
		if ((flag & AccessFlag.METHOD_ACC_VARARGS) == AccessFlag.METHOD_ACC_VARARGS) {
			result += "varargs ";
		}
		if ((flag & AccessFlag.METHOD_ACC_NATIVE) == AccessFlag.METHOD_ACC_NATIVE) {
			result += "native ";
		}
		if ((flag & AccessFlag.METHOD_ACC_ABSTRACT) == AccessFlag.METHOD_ACC_ABSTRACT) {
			result += "abstract ";
		}
		if ((flag & AccessFlag.METHOD_ACC_STRICT) == AccessFlag.METHOD_ACC_STRICT) {
			result += "strict ";
		}
		if ((flag & AccessFlag.METHOD_ACC_SYNTHETIC) == AccessFlag.METHOD_ACC_SYNTHETIC) {
			result += "synthetic ";
		}
		
		if (result.length() > 0) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}
	
	
}
