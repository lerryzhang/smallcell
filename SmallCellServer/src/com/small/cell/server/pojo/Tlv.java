package com.small.cell.server.pojo;

import com.small.cell.server.util.ByteAndStr16;

public class Tlv {
	public final static int TlvTagLength = 2;
	public final static int TlvLenLength = 2;
	byte[] type;
	byte[] len;
	byte[] value;

	public byte[] getType() {
		return type;
	}

	public void setType(byte[] type) {
		this.type = type;
	}

	public byte[] getLen() {
		return len;
	}

	public void setLen(byte[] len) {
		this.len = len;
	}

	public byte[] getValue() {
		return value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s%s%s", ByteAndStr16.Bytes2HexString(type),ByteAndStr16.Bytes2HexString(len),ByteAndStr16.Bytes2HexString(value));
	}

}