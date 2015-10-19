package com.compiler.model.constantpool;

import java.io.InputStream;

import com.compiler.util.TransformUtil;

public class ConstantIntegerOrFloatInfo extends ConstantPoolInfo {
	private byte[] bytes;

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
	@Override
	public void parse(InputStream in, byte tag, int index) throws Exception{
		super.parse(in, tag, index);
		byte[] bb = new byte[4];
		in.read(bb, 0, 4);
		setBytes(bb);
	}
	
	@Override
	public String print(int length) throws Exception {
		String space = TransformUtil.spaces(length);
		String result = "";
		if (tag == CONSTANT_INTEGER) {
			
				result = space + "const #" + getIndex() + " =Integer" + space  + TransformUtil.bytesToInt(bytes);
		} else if (tag == CONSTANT_FLOAT) {
				result = space + "const #" + getIndex() + " =Float" + space + TransformUtil.bytesToFloat(bytes) + "f";
		}
		return result;
	}
}
