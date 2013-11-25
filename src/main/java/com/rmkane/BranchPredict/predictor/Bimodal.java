package com.rmkane.BranchPredict.predictor;

import com.rmkane.BranchPredict.CounterArray;

public class Bimodal extends CounterArray {

	public Bimodal() {
		super();
	}
	
	public Bimodal(int sizeInBits) {
		super(sizeInBits, 0);
	}

	public void increment(final long addr) {
		super.increment((int) (addr % rows()));
	}

	public void decrement(final long addr) {
		super.decrement((int) (addr % rows()));
	}

	public int getValue(final long addr) {
		return super.getValue((int) (addr % rows()));
	}
	
	public void train(long addr, boolean taken) {
		if (taken)
			increment(addr);
		else
			decrement(addr);
	}
}