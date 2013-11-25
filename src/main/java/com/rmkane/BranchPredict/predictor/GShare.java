package com.rmkane.BranchPredict.predictor;

import com.rmkane.BranchPredict.CounterArray;
import com.rmkane.BranchPredict.Utils;

public class GShare {

	private CounterArray counterArr;
	private int bitmask;

	public GShare() {
		this.counterArr = new CounterArray();
	}

	public GShare(final int sizeInBits) {
		this(sizeInBits, 0);
	}
	
	public GShare(final int sizeInBits, final int counterValue) {
		counterArr = new CounterArray(sizeInBits, counterValue);
		
		this.counterArr = new CounterArray(sizeInBits, counterValue);
		this.bitmask = Utils.bitMask(sizeInBits);
	}
	
	public int getValue(final long addr, final int branchHistoryRegister) {
		int index = computeIndex(addr, branchHistoryRegister);
		return counterArr.getValue(index);
	}

	public void train(final long addr, int branchHistoryRegister, boolean taken) {
		int index = computeIndex(addr, branchHistoryRegister);

		if (taken) {
			counterArr.increment(index);
		} else {
			counterArr.decrement(index);
		}
	}

	private int computeIndex(final long pc, final int bhr) {
		return (int) (pc & bitmask) ^ (bhr & bitmask);
	}
}