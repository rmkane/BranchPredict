package com.rmkane.BranchPredict;
public class CounterArray {

	private Counter[] counterArr;
	private int bits;

	public CounterArray() {
		bits = 0;
		counterArr = null;
	}
	
	public CounterArray(final int sizeInBits, final int counterValue) {
		bits = sizeInBits;
		counterArr = new Counter[(int) Math.pow(2, bits)];

		for (int i = 0; i < counterArr.length; i++) {
			counterArr[i] = new Counter(counterValue);
		}
	}

	public void increment(final int addr) {
		counterArr[addr].increment();
	}

	public void decrement(final int addr) {
		counterArr[addr].decrement();
	}
	
	public int rows() {
		return counterArr.length;
	}

	public int getValue(long index) {
		int intIndex = (int) index;
		
		return counterArr[intIndex].getValue();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int row = 0; row < rows(); row++) {
			sb.append(String.format("[%s | %s]\n",
					Utils.longToBin(row, bits),
					Utils.longToBin(counterArr[row].getValue(), 2)));
		}
		return sb.toString();
	}
}