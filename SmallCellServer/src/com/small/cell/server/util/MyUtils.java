package com.small.cell.server.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.mina.core.buffer.IoBuffer;

public class MyUtils {

	public static String parseStringFromBytes(byte[] data, int startIndex,
			int lenth) {
		return parseStringFromBytes(data, startIndex, lenth, null);
	}

	public static String parseStringFromBytes(byte[] data, int startIndex,
			int lenth, String defaultVal) {
		try {
			byte[] tmp = new byte[lenth];
			System.arraycopy(data, startIndex, tmp, 0, lenth);
			return new String(tmp, "UTF-8");
		} catch (Exception e) {

			e.printStackTrace();
			return defaultVal;
		}
	}

	public static String parseBcdStringFromBytes(byte[] data, int startIndex,
			int lenth) {
		return parseBcdStringFromBytes(data, startIndex, lenth, null);
	}

	public static String parseBcdStringFromBytes(byte[] data, int startIndex,
			int lenth, String defaultVal) {
		try {
			byte[] tmp = new byte[lenth];
			System.arraycopy(data, startIndex, tmp, 0, lenth);
			return ByteAndStr16.Bytes2HexString(tmp);
		} catch (Exception e) {

			e.printStackTrace();
			return defaultVal;
		}
	}

	public static int parseIntFromBytes(byte[] data, int startIndex, int length) {
		return parseIntFromBytes(data, startIndex, length, 0);
	}

	public static int parseIntFromBytes(byte[] data, int startIndex,
			int length, int defaultVal) {
		try {
			// 字节数大于4,从起始索引开始向后处理4个字节,其余超出部分丢弃
			final int len = length > 4 ? 4 : length;
			byte[] tmp = new byte[len];
			System.arraycopy(data, startIndex, tmp, 0, len);
			return byteToInteger(tmp);
		} catch (Exception e) {
			e.printStackTrace();
			return defaultVal;
		}
	}

	public static byte[] getBytes(IoBuffer in) {
		byte[] data = new byte[in.limit() - in.position()];
		in.get(data);
		return data;
	}

	/**
	 * BCD字节数组===>String
	 * 
	 * @param bytes
	 * @return 十进制字符串
	 */
	public static String bcd2String(byte[] bytes) {
		StringBuilder temp = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			// 高四位
			temp.append((bytes[i] & 0xf0) >>> 4);
			// 低四位
			temp.append(bytes[i] & 0x0f);
		}
		return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
				.toString().substring(1) : temp.toString();
	}

	/**
	 * 字符串==>BCD字节数组
	 * 
	 * @param str
	 * @return BCD字节数组
	 */
	public byte[] string2Bcd(String str) {
		// 奇数,前补零
		if ((str.length() & 0x1) == 1) {
			str = "0" + str;
		}

		byte ret[] = new byte[str.length() / 2];
		byte bs[] = str.getBytes();
		for (int i = 0; i < ret.length; i++) {

			byte high = ascII2Bcd(bs[2 * i]);
			byte low = ascII2Bcd(bs[2 * i + 1]);

			// TODO 只遮罩BCD低四位?
			ret[i] = (byte) ((high << 4) | low);
		}
		return ret;
	}

	public static byte ascII2Bcd(byte asc) {
		if ((asc >= '0') && (asc <= '9'))
			return (byte) (asc - '0');
		else if ((asc >= 'A') && (asc <= 'F'))
			return (byte) (asc - 'A' + 10);
		else if ((asc >= 'a') && (asc <= 'f'))
			return (byte) (asc - 'a' + 10);
		else
			return (byte) (asc - 48);
	}

	/**
	 * 把一个整形该为byte
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static byte integerTo1Byte(int value) {
		return (byte) (value & 0xFF);
	}

	/**
	 * 把一个整形该为1位的byte数组
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static byte[] integerTo1Bytes(int value) {
		byte[] result = new byte[1];
		result[0] = (byte) (value & 0xFF);
		return result;
	}

	/**
	 * 把一个整形改为2位的byte数组
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static byte[] integerTo2Bytes(int value) {
		byte[] result = new byte[2];
		result[0] = (byte) ((value >>> 8) & 0xFF);
		result[1] = (byte) (value & 0xFF);
		return result;
	}

	/**
	 * 把一个整形改为3位的byte数组
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static byte[] integerTo3Bytes(int value) {
		byte[] result = new byte[3];
		result[0] = (byte) ((value >>> 16) & 0xFF);
		result[1] = (byte) ((value >>> 8) & 0xFF);
		result[2] = (byte) (value & 0xFF);
		return result;
	}

	/**
	 * 把一个整形改为4位的byte数组
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static byte[] integerTo4Bytes(int value) {
		byte[] result = new byte[4];
		result[0] = (byte) ((value >>> 24) & 0xFF);
		result[1] = (byte) ((value >>> 16) & 0xFF);
		result[2] = (byte) ((value >>> 8) & 0xFF);
		result[3] = (byte) (value & 0xFF);
		return result;
	}

	/**
	 * 把byte[]转化位整形,通常为指令用
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static int byteToInteger(byte[] value) {
		int result;
		if (value.length == 1) {
			result = oneByteToInteger(value[0]);
		} else if (value.length == 2) {
			result = twoBytesToInteger(value);
		} else if (value.length == 3) {
			result = threeBytesToInteger(value);
		} else if (value.length == 4) {
			result = fourBytesToInteger(value);
		} else {
			result = fourBytesToInteger(value);
		}
		return result;
	}

	/**
	 * 把一个byte转化位整形,通常为指令用
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static int oneByteToInteger(byte value) {
		return (int) value & 0xFF;
	}

	/**
	 * 把一个2位的数组转化位整形
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static int twoBytesToInteger(byte[] value) {
		// if (value.length < 2) {
		// throw new Exception("Byte array too short!");
		// }
		int temp0 = value[0] & 0xFF;
		int temp1 = value[1] & 0xFF;
		return ((temp0 << 8) + temp1);
	}

	/**
	 * 把一个3位的数组转化位整形
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static int threeBytesToInteger(byte[] value) {
		int temp0 = value[0] & 0xFF;
		int temp1 = value[1] & 0xFF;
		int temp2 = value[2] & 0xFF;
		return ((temp0 << 16) + (temp1 << 8) + temp2);
	}

	/**
	 * 把一个4位的数组转化位整形,通常为指令用
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static int fourBytesToInteger(byte[] value) {
		// if (value.length < 4) {
		// throw new Exception("Byte array too short!");
		// }
		int temp0 = value[0] & 0xFF;
		int temp1 = value[1] & 0xFF;
		int temp2 = value[2] & 0xFF;
		int temp3 = value[3] & 0xFF;
		return ((temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
	}

	/**
	 * 把一个4位的数组转化位整形
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static long fourBytesToLong(byte[] value) throws Exception {
		// if (value.length < 4) {
		// throw new Exception("Byte array too short!");
		// }
		int temp0 = value[0] & 0xFF;
		int temp1 = value[1] & 0xFF;
		int temp2 = value[2] & 0xFF;
		int temp3 = value[3] & 0xFF;
		return (((long) temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
	}

	/**
	 * 把一个数组转化长整形
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static long bytes2Long(byte[] value) {
		long result = 0;
		int len = value.length;
		int temp;
		for (int i = 0; i < len; i++) {
			temp = (len - 1 - i) * 8;
			if (temp == 0) {
				result += (value[i] & 0x0ff);
			} else {
				result += (value[i] & 0x0ff) << temp;
			}
		}
		return result;
	}

	/**
	 * 把一个长整形改为byte数组
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static byte[] longToBytes(long value) {
		return longToBytes(value, 8);
	}

	/**
	 * 把一个长整形改为byte数组
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static byte[] longToBytes(long value, int len) {
		byte[] result = new byte[len];
		int temp;
		for (int i = 0; i < len; i++) {
			temp = (len - 1 - i) * 8;
			if (temp == 0) {
				result[i] += (value & 0x0ff);
			} else {
				result[i] += (value >>> temp) & 0x0ff;
			}
		}
		return result;
	}

	/**
	 * 得到一个消息ID
	 * 
	 * @return
	 * @throws Exception
	 */
	public static byte[] generateTransactionID() throws Exception {
		byte[] id = new byte[16];
		System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id,
				0, 2);
		System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id,
				2, 2);
		System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id,
				4, 2);
		System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id,
				6, 2);
		System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id,
				8, 2);
		System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id,
				10, 2);
		System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id,
				12, 2);
		System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id,
				14, 2);
		return id;
	}

	/**
	 * 把IP拆分位int数组
	 * 
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public static int[] getIntIPValue(String ip) throws Exception {
		String[] sip = ip.split("[.]");
		// if (sip.length != 4) {
		// throw new Exception("error IPAddress");
		// }
		int[] intIP = { Integer.parseInt(sip[0]), Integer.parseInt(sip[1]),
				Integer.parseInt(sip[2]), Integer.parseInt(sip[3]) };
		return intIP;
	}

	/**
	 * 把byte类型IP地址转化位字符串
	 * 
	 * @param address
	 * @return
	 * @throws Exception
	 */
	public static String getStringIPValue(byte[] address) throws Exception {
		int first = oneByteToInteger(address[0]);
		int second = oneByteToInteger(address[1]);
		int third = oneByteToInteger(address[2]);
		int fourth = oneByteToInteger(address[3]);

		return first + "." + second + "." + third + "." + fourth;
	}

	/**
	 * 合并字节数组
	 * 
	 * @param first
	 * @param rest
	 * @return
	 */
	public static byte[] concatAll(byte[] first, byte[]... rest) {
		int totalLength = first.length;
		for (byte[] array : rest) {
			if (array != null) {
				totalLength += array.length;
			}
		}
		byte[] result = Arrays.copyOf(first, totalLength);

		int offset = first.length;
		for (byte[] array : rest) {
			if (array != null) {
				System.arraycopy(array, 0, result, offset, array.length);
				offset += array.length;
			}
		}
		return result;
	}

	/**
	 * 合并字节数组
	 * 
	 * @param rest
	 * @return
	 */
	public static byte[] concatAll(List<byte[]> rest) {
		int totalLength = 0;
		for (byte[] array : rest) {
			if (array != null) {
				totalLength += array.length;
			}
		}
		byte[] result = new byte[totalLength];
		int offset = 0;
		for (byte[] array : rest) {
			if (array != null) {
				System.arraycopy(array, 0, result, offset, array.length);
				offset += array.length;
			}
		}
		return result;
	}

	public static float byte2Float(byte[] bs) {
		return Float
				.intBitsToFloat((((bs[3] & 0xFF) << 24)
						+ ((bs[2] & 0xFF) << 16) + ((bs[1] & 0xFF) << 8) + (bs[0] & 0xFF)));
	}

	public static float byteBE2Float(byte[] bytes) {
		int l;
		l = bytes[0];
		l &= 0xff;
		l |= ((long) bytes[1] << 8);
		l &= 0xffff;
		l |= ((long) bytes[2] << 16);
		l &= 0xffffff;
		l |= ((long) bytes[3] << 24);
		return Float.intBitsToFloat(l);
	}

	public static int getCheckSum4JT808(byte[] bs, int start, int end) {
		if (start < 0 || end > bs.length)
			throw new ArrayIndexOutOfBoundsException(
					"getCheckSum4JT808 error : index out of bounds(start="
							+ start + ",end=" + end + ",bytes length="
							+ bs.length + ")");
		int cs = 0;
		for (int i = start; i < end; i++) {
			cs ^= bs[i];
		}
		return cs;
	}

	public static int getBitRange(int number, int start, int end) {
		if (start < 0)
			throw new IndexOutOfBoundsException("min index is 0,but start = "
					+ start);
		if (end >= Integer.SIZE)
			throw new IndexOutOfBoundsException("max index is "
					+ (Integer.SIZE - 1) + ",but end = " + end);

		return (number << Integer.SIZE - (end + 1)) >>> Integer.SIZE
				- (end - start + 1);
	}

	public static int getBitAt(int number, int index) {
		if (index < 0)
			throw new IndexOutOfBoundsException("min index is 0,but " + index);
		if (index >= Integer.SIZE)
			throw new IndexOutOfBoundsException("max index is "
					+ (Integer.SIZE - 1) + ",but " + index);

		return ((1 << index) & number) >> index;
	}

	public static int getBitAtS(int number, int index) {
		String s = Integer.toBinaryString(number);
		return Integer.parseInt(s.charAt(index) + "");
	}

	@Deprecated
	public static int getBitRangeS(int number, int start, int end) {
		String s = Integer.toBinaryString(number);
		StringBuilder sb = new StringBuilder(s);
		while (sb.length() < Integer.SIZE) {
			sb.insert(0, "0");
		}
		String tmp = sb.reverse().substring(start, end + 1);
		sb = new StringBuilder(tmp);
		return Integer.parseInt(sb.reverse().toString(), 2);
	}

	public static byte[] subBytes(byte[] src, int begin, int count) {
		byte[] bs = new byte[count];
		System.arraycopy(src, begin, bs, 0, count);
		return bs;
	}

	public static byte[] objectToByteArray(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(obj);
			objectOutputStream.flush();
			bytes = byteArrayOutputStream.toByteArray();

		} catch (IOException e) {
			System.out.println("objectToByteArray failed, " + e);
		} finally {
			if (objectOutputStream != null) {
				try {
					objectOutputStream.close();
				} catch (IOException e) {
					System.out.println("close objectOutputStream failed, " + e);
				}
			}
			if (byteArrayOutputStream != null) {
				try {
					byteArrayOutputStream.close();
				} catch (IOException e) {
					System.out.println("close byteArrayOutputStream failed, "
							+ e);
				}
			}

		}
		return bytes;
	}

	public static String IntegerToString16For4(int in) {

		String st = Integer.toHexString(in).toUpperCase();
		st = String.format("%4s", st);
		st = st.replaceAll(" ", "0");
		return st;

	}

	public static String IntegerToString16For2(int in) {

		String st = Integer.toHexString(in).toUpperCase();
		st = String.format("%2s", st);
		st = st.replaceAll(" ", "0");
		return st;

	}

	public static boolean valMac(String mac) {

		String patternMac = "^[A-F0-9]{2}(-[A-F0-9]{2}){5}$";
		if (!Pattern.compile(patternMac).matcher(ConvertStrToMac(mac)).find())
			return false;
		return true;

	}

	public static String ConvertStrToMac(String mac) {

		StringBuffer sb = new StringBuffer("");

		for (int i = 0; i < mac.length(); i++) {
			sb.append(mac.charAt(i));
			if (i % 2 != 0 && i != mac.length() - 1) {
				sb.append("-");
			}
		}
		return sb.toString();
	}

}