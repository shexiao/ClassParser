package com.compiler.model.constantpool;

import java.io.InputStream;

import com.compiler.util.TransformUtil;

public class ConstantUtf8Info extends ConstantPoolInfo {
	private byte[] length;
	private byte[] bytes;
	public byte[] getLength() {
		return length;
	}
	public void setLength(byte[] length) {
		this.length = length;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
	@Override
	public void parse(InputStream in, byte tag, int index) throws Exception{
		super.parse(in, tag, index);
		byte[] bb = new byte[2];
		in.read(bb, 0, 2);
		setLength(bb);
		int length = TransformUtil.bytesToInt(bb);
		bb = new byte[length];
		in.read(bb, 0, length);
		setBytes(bb);
	}
	
	@Override
	public String toString() {
		String result = "";
		try {
			//System.out.println(TransformUtil.getHex(bytes));
			result = "const #" + getIndex() + " =Utf8\t " + new String(bytes, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
