package com.compiler.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.compiler.annotation.Printer;
import com.compiler.printer.ClassModelPrinter;
import com.compiler.printer.IPrinter;

public class PrinterProxy implements InvocationHandler{

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (method.isAnnotationPresent(Printer.class)) {
			Printer printer = method.getAnnotation(Printer.class);
			System.out.println(printer.info());
		}
		Object result = method.invoke(new ClassModelPrinter(), args);
		System.out.println();
		return result;
	}

	
	public static IPrinter createProxy() {
		return (IPrinter)Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{IPrinter.class}, new PrinterProxy());
	}
}
