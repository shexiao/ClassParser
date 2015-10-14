package com.compiler.opcodes;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Instructions {
	private static final Instructions instructions = new Instructions();
	
	Map<Byte, String> opmap = new HashMap<Byte, String>();
	private Instructions() {
		Class<Opcodes> opcodes = Opcodes.class;
		Field[] fields = opcodes.getDeclaredFields();
		for (Field field : fields) {
			try {
				opmap.put(field.getByte(opcodes), field.getName());
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Instructions getInstance() {
		return instructions;
	}
	
	public static void main(String[] args) {
		Instructions inst = Instructions.getInstance();
		
	}
}
