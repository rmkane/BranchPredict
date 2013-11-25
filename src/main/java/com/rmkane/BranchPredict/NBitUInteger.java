package com.rmkane.BranchPredict;

public final class NBitUInteger extends Number implements
		Comparable<NBitUInteger> {

	private static final long serialVersionUID = 1L;
	private int value;
	private final int bits;
	private final int bitMask;

	public static final int MIN_VALUE = 0x0;
	public static final int MAX_VALUE = 0x7fffffff;

	public NBitUInteger() {
		this(0);
	}

	public NBitUInteger(int value) {
		this(value, 32);
	}

	public NBitUInteger(int value, int bits) {
		this.bits = bits;
		this.bitMask = bitMask(bits);
		this.value = extractLowerBits(value);
	}

	private final int extractLowerBits(final int value) {
		return value & bitMask;
	}

	public int bitMask() {
		return this.bitMask;
	}

	public int bits() {
		return this.bits;
	}

	@Override
	public int intValue() {
		return this.value;
	}

	@Override
	public long longValue() {
		return (long) this.value;
	}

	@Override
	public float floatValue() {
		return (float) this.value;
	}

	@Override
	public double doubleValue() {
		return (double) this.value;
	}

	public int compareTo(NBitUInteger other) {
		return compare(this.value, other.value);
	}

	public static final int compare(final int x, final int y) {
		return (x < y) ? -1 : ((x == y) ? 0 : 1);
	}

	public final NBitUInteger shiftRight(final int amount) {
		this.value = this.value >> amount;

		return this;
	}

	public final NBitUInteger shiftRight() {
		return shiftRight(1);
	}

	public final NBitUInteger shiftRightPrependBit(final int bit) {
		this.value = (bit << (bits - 1)) | shiftRight().intValue();

		return this;
	}

	public final NBitUInteger shiftLeft(final int amount) {
		this.value = (this.value << amount) & bitMask;

		return this;
	}

	public final NBitUInteger shiftLeft() {
		return shiftLeft(1);
	}

	public final NBitUInteger shiftLeftAppendBit(final int bit) {
		this.value = shiftLeft().intValue() | bit;

		return this;
	}

	public final String toBinaryString() {
		return toBinaryString(this.value, this.bits);
	}

	public static final String toBinaryString(int value, int bits) {
		StringBuilder binStr = new StringBuilder(Integer.toBinaryString(value));

		while (binStr.length() < bits)
			binStr.insert(0, '0');

		return binStr.toString();
	}

	public static final int bitMask(final int bits) {
		return (1 << bits) - 1;
	}
}