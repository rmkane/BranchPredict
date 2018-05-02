package com.rmkane.BranchPredict;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NBitUIntegerTest {
	private NBitUInteger x;
	
	@Before
	public void setUp() {
		x = new NBitUInteger(13, 4);
	}
	
	@Test
	public void shiftTest() {
		printResult(x, 13);

		x.shiftRight().shiftRight().shiftRight();
		printResult(x, 1);

		x.shiftLeft(2);
		printResult(x, 4);

		x.shiftLeftAppendBit(1).shiftLeftAppendBit(1).shiftLeftAppendBit(1);
		printResult(x, 7);

		x.shiftRightPrependBit(1);
		printResult(x, 11);
	}

	private void printResult(NBitUInteger value, int expected) {
		assertEquals(expected, value.intValue());
		
		System.out.printf("%s == %s => %b\n", value.toBinaryString(),
				NBitUInteger.toBinaryString(expected, value.bits()),
				(value.intValue() == expected));
	}
}