package com.rmkane.BranchPredict.predictor;

import com.rmkane.BranchPredict.CounterArray;
import com.rmkane.BranchPredict.Utils;

public class TwoLevel {

	private CounterArray[] counterArr;
	private int bitMask;

	public TwoLevel() {
		this(2, 0, 4);
	}

	public TwoLevel(final int sizeInBits) {
		this(sizeInBits, 0, 4);
	}
	
	public TwoLevel(final int sizeInBits, final int counterValue, final int tables) {
		counterArr = new CounterArray[tables];
		
		for (int i = 0; i < tables; i++) {
			counterArr[i] = new CounterArray(sizeInBits, counterValue);
		}
		
		this.bitMask = Utils.bitMask(sizeInBits);
	}

	public int getValue(final long addr, final int historyRegister) {
		int index = computeIndex(addr);
		return counterArr[historyRegister].getValue(index);
	}

	public void train(final long addr, int historyRegister, boolean taken) {
		int index = computeIndex(addr);

		if (taken) {
			counterArr[historyRegister].increment(index);
		} else {
			counterArr[historyRegister].decrement(index);
		}
	}

	private int computeIndex(final long pc) {
		return (int) (pc & bitMask);
	}
}
