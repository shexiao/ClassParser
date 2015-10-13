package com.compiler;

import java.io.FileInputStream;

import com.compiler.model.ClassModel;
import com.compiler.printer.IPrinter;
import com.compiler.reflect.PrinterProxy;

public class ParseByteCode {
	public static void main(String[] args) throws Exception {
//		String filepath = "F:\\迅雷下载\\asm-5.0.4-bin\\asm-5.0.4\\examples\\helloworld\\Example.class";
//		String filepath = "D:\\mars_workspace\\Java\\bin\\com\\sample\\Sample1.class";
		String filepath = "C:\\Users\\Administrator\\Desktop\\桌面文件\\Sample1.class";
		FileInputStream stream = new FileInputStream(filepath);
		try {
			Parser p = new Parser();
			p.parse(stream);
			ClassModel classModel = p.getClassModel();
			
			IPrinter cmp = PrinterProxy.createProxy();
			cmp.printHeader(classModel);
			cmp.printConstantPoolInfo(classModel);
			cmp.printClassInfo(classModel);
			cmp.printFieldInfo(classModel);
			cmp.printMethodInfo(classModel);
			cmp.printAttributeInfo(classModel);
			
		} finally {
			stream.close();
		}
		
	}
}
