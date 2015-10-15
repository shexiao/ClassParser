package com.compiler.opcodes;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

import com.compiler.annotation.OpcodeInfo;
import com.compiler.util.TransformUtil;

public class Instructions {
	private static final Instructions instructions = new Instructions();
	public static final int NUM = 1;
	public static final int TYPE = 2;
	
	Map<Byte, String> opmap = new HashMap<Byte, String>();
	Map<Byte, Map<Integer, Integer>> opcodeInfoMap = new HashMap<Byte, Map<Integer, Integer>>();
	private Instructions() {
		Class<Opcodes> opcodes = Opcodes.class;
		Field[] fields = opcodes.getDeclaredFields();
		for (Field field : fields) {
			try {
				byte fieldValue = field.getByte(opcodes);
				if (field.isAnnotationPresent(OpcodeInfo.class)) {
					Map<Integer, Integer> info = new HashMap<Integer, Integer>();
					OpcodeInfo opcodeInfo = field.getAnnotation(OpcodeInfo.class);
					info.put(NUM, opcodeInfo.num());
					info.put(TYPE, opcodeInfo.type());
					opcodeInfoMap.put(fieldValue, info);
				}
				opmap.put(fieldValue, field.getName().toLowerCase());
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Instructions getInstance() {
		return instructions;
	}
	
	public String parse(byte[] code, String space) throws Exception {
		Queue<Byte> queue = new ArrayDeque<Byte>();
		for (int i = 0; i < code.length; i++) {
			queue.add(code[i]);
		}
		String result = "";
		while (!queue.isEmpty()) {
			result += space + compile(queue);
		}
		return result;
	}
	
	private String compile(Queue<Byte> code) throws Exception{
		String result = "";
		byte operand = code.poll();
		Map<Integer, Integer> opcodeInfo = opcodeInfoMap.get(operand);
		if (opcodeInfo == null) {
			throw new Exception("compile operand error");
		}
		int num = opcodeInfo.get(NUM);
		int type = opcodeInfo.get(TYPE);
		
		byte b1 = 0;
		byte b2 = 0;
		byte b3 = 0;
		byte b4 = 0;
		
		String op = opmap.get(operand);
		if (num == 0) {
			result = op;
		} else if (num == 1) {
			b1 = code.poll();
			
			if (type == 0) {
				result = op + "\t" + TransformUtil.bytesToInt(new byte[]{b1});
			} else if (type == 1) {
				result = op + "\t#" + TransformUtil.bytesToInt(new byte[]{b1});
			}
		} else if (num == 2) {
			b1 = code.poll();
			b2 = code.poll();
			if (type == 0) {
				result = op + "\t" + TransformUtil.bytesToInt(new byte[]{b1, b2});
			} else if (type == 1) {
				result = op + "\t#" + TransformUtil.bytesToInt(new byte[]{b1, b2});
			}
		} else if (num == 3) {
			b1 = code.poll();
			b2 = code.poll();
			b3 = code.poll();
			if (op.equals("multianewarray")) {
				result = op + "\t#" + TransformUtil.bytesToInt(new byte[]{b1, b2}) + ", " + TransformUtil.bytesToInt(new byte[]{b3});
			}
		} else if (num == 4) {
			b1 = code.poll();
			b2 = code.poll();
			b3 = code.poll();
			b4 = code.poll();
			if (op.equals("goto_w") || op.equals("jsr_w")) {
				result = op + "\t" + TransformUtil.bytesToInt(new byte[]{b1, b2, b3, b4});
			} else if (op.equals("invokedynamic") || op.equals("invokeinterface")) {
				result = op + "\t#" + TransformUtil.bytesToInt(new byte[]{b1, b2}) + ", " + TransformUtil.bytesToInt(new byte[]{b3}) + ", " + TransformUtil.bytesToInt(new byte[]{b4});
			}
		}
		return result + "\n";
	}
	
	public static void main(String[] args) {
		Instructions inst = Instructions.getInstance();
		
	}
}
