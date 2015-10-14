package com.compiler.parser;

import java.io.InputStream;

import com.compiler.model.ClassModel;

public interface IParser {
	public void parse(ClassModel classModel, InputStream in) throws Exception;
	public void addNext(IParser next);
}
