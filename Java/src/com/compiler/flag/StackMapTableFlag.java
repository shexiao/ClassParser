package com.compiler.flag;

public class StackMapTableFlag {
	public static final byte SAME = 63;
	public static final byte SAME_LOCALS_1_STACK_ITEM = 127;
	public static final byte SAME_LOCALS_1_STACK_ITEM_EXTENDED = (byte)247;
	public static final byte CHOP = (byte)250;
	public static final byte SAME_FRAME_EXTENDED = (byte)251;
	public static final byte APPEND = (byte)254;
	public static final byte FULL_FRAME = (byte)255;
	
	public static final byte ITEM_TOP = 0;
	public static final byte ITEM_INTEGER = 1;
	public static final byte ITEM_FLOAT = 2;
	public static final byte ITEM_LONG = 4;
	public static final byte ITEM_DOUBLE = 3;
	public static final byte ITEM_NULL = 5;
	public static final byte ITEM_UNINITIALIZEDTHIS = 6;
	public static final byte ITEM_OBJECT = 7;
	public static final byte ITEM_UNINITIALIZED = 8;
	
	public static String parseTag(byte tag) {
		switch (tag) {
		case ITEM_TOP: return "item_top";
		case ITEM_INTEGER: return "item_integer";
		case ITEM_FLOAT: return "item_float";
		case ITEM_LONG: return "item_long";
		case ITEM_DOUBLE: return "item_double";
		case ITEM_NULL: return "item_null";
		case ITEM_UNINITIALIZEDTHIS: return "item_uninitialzedthis";
		case ITEM_OBJECT: return "item_object";
		case ITEM_UNINITIALIZED: return "item_uninitialized";
		default:return "";
		}
	}
	
	public static String parseFrameType(byte frame_type) {
		int type = Byte.toUnsignedInt(frame_type);
		if (type >= 0 && type <= Byte.toUnsignedInt(SAME)) return "same";
		if (type > Byte.toUnsignedInt(SAME) && type <= Byte.toUnsignedInt(SAME_LOCALS_1_STACK_ITEM)) return "same_locals_1_stack_item";
		if (type == Byte.toUnsignedInt(SAME_LOCALS_1_STACK_ITEM_EXTENDED)) return "same_locals_1_stack_item_extended";
		if (type > Byte.toUnsignedInt(SAME_LOCALS_1_STACK_ITEM_EXTENDED) && type <= Byte.toUnsignedInt(CHOP)) return "chop";
		if (type == Byte.toUnsignedInt(SAME_FRAME_EXTENDED)) return "same_frame_extended";
		if (type > Byte.toUnsignedInt(SAME_FRAME_EXTENDED) && type <= Byte.toUnsignedInt(APPEND)) return "append";
		if (type == Byte.toUnsignedInt(FULL_FRAME)) return "full_frame";
		return "";
	}
}
