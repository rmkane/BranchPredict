package com.rmkane.BranchPredict;
public class Counter {

	private final int MIN = 0, MAX = 3;
	private int value;

	public Counter(int value) {
		setValue(value);
	}

	public void increment() {
		value++;

		if (value > MAX)
			value = MAX;
	}

	public void decrement() {
		value--;

		if (value < MIN)
			value = MIN;
	}

	public void setValue(int value) {
		if (value < MIN)
			this.value = MIN;

		else if (value > MAX)
			this.value = MAX;

		else
			this.value = value;
	}

	public int getValue() {
		return value;
	}
}
