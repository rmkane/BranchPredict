package com.rmkane.BranchPredict;

public class Utils {
	private static final int KB, MB, GB;

	static {
		KB = 1024;
		MB = (int) Math.pow(KB, 2);
		GB = MB * KB;
	}

	public final static String longToBin(final long value, final int bits) {
		StringBuilder binStr = new StringBuilder(Long.toBinaryString(value));

		while (binStr.length() < bits)
			binStr.insert(0, '0');

		return binStr.toString();
	}

	public static int shiftRightPrependBit(int intVal, int size, int bit) {
		return (bit << (size - 1)) | (intVal >> 1);
	}

	public static int shiftLeftAppendBit(int intVal, int size, int bit) {
		return ((intVal << 1) & bitMask(size)) | bit;
	}

	public static int bitMask(int bits) {
		return (1 << bits) - 1;
	}

	public static String repeat(final char ch, final int len) {
		StringBuffer sb = new StringBuffer();

		while (sb.length() < len)
			sb.append(ch);

		return sb.toString();
	}

	public static String separator() {
		return repeat('-', 60);
	}

	public static String percentageMetrics(long subtotal, long total) {
		return String.format("%d / %d or %s",
				subtotal, total, percentage(subtotal, total));
	}
	
	public final static String percentage(long subtotal, long total) {
		return String.format("%%%.2f", percentValue(subtotal, total));
	}
	
	public final static double percentValue(long a, long b) {
		return divideLong(a, b) * 100.0d;
	}
	
	public final static double divideLong(long a, long b) {
		return ((double) a / b);
	}
	
	public static String label(int bits) {
		Object[] vals = formatBits(bits);
		return String.format("%1d%s", (Integer) vals[0], (String) vals[1]);
	}
	
	private static Object[] formatBits(int bits) {
		if (bits < 1e3)
			return new Object[] { bits, "B" };
		else if (bits < 1e6)
			return new Object[] { bits / KB, "K" };
		else if (bits < 1e9)
			return new Object[] { bits / MB, "M" };
		else
			return new Object[] { bits / GB, "G" };
	}
}