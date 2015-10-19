package com.compiler.printer;

import com.compiler.annotation.Printer;
import com.compiler.model.ClassModel;

public interface IPrinter {
	
	@Printer(info="header info")
	public void printHeader(ClassModel classModel) throws Exception;
	
	@Printer(info="constant pool info")
	public void printConstantPoolInfo(ClassModel classModel) throws Exception;
	
	@Printer(info="class info")
	public void printClassInfo(ClassModel classModel) throws Exception;
	
	@Printer(info="field info")
	public void printFieldInfo(ClassModel classModel) throws Exception;
	
	@Printer(info="method info")
	public void printMethodInfo(ClassModel classModel) throws Exception;
	
	@Printer(info="attribute info")
	public void printAttributeInfo(ClassModel classModel) throws Exception;
}
