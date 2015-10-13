package com.compiler.annotation.parser;

import java.lang.reflect.Method;

import com.compiler.annotation.Printer;
import com.compiler.printer.ClassModelPrinter;

public class PrinterParser {
	public static void parseMethodAnnotation() {
		Method[] methods = ClassModelPrinter.class.getDeclaredMethods();
		for (Method method : methods) {
			boolean has = method.isAnnotationPresent(Printer.class);
			if (has) {
				Printer printer = method.getAnnotation(Printer.class);
				System.out.println("info : " + printer.info());
			}
		}
	}
	
	public static void main(String[] args) {
		parseMethodAnnotation();
	}
}
