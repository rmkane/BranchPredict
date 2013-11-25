package com.rmkane.BranchPredict.predictor;

import com.rmkane.BranchPredict.Instruction;
import com.rmkane.BranchPredict.Utils;

public class TwoLevelPredictor extends BranchPredictor {
	private TwoLevel twoLevel;
	private int historyRegister;
	private int historyBits;

	public TwoLevelPredictor(String traceFileName) {
		super(traceFileName);
		resetValues();
	}
	
	public TwoLevelPredictor(String traceFileName, int bitStart, int bitEnd) {
		super(traceFileName, bitStart, bitEnd);
		resetValues();
	}

	@Override
	public void execute() {
		super.execute();

		for (int bits = bit_start; bits < bit_end; bits++) {
			resetValues();
			this.bits = bits;
			twoLevel = new TwoLevel(bits, 0, 4);
			readZipFile(traceFileName, BranchPredictor.UTF_8);
			printMetrics();
		}
		
		System.out.println();
	}

	@Override
	protected void resetValues() {
		super.resetValues();

		this.historyRegister = 0;
		this.historyBits = 2;
	}

	@Override
	protected void parseLine(final String line, int count) {
		Instruction instruction = parseInstruction(line);
		int value = twoLevel.getValue(instruction.getAddress(), historyRegister);
		boolean actual = value > 1;

		if (actual == instruction.isTaken())
			takenTotal++;

		totalInstructions++;

		twoLevel.train(instruction.getAddress(), historyRegister,
				instruction.isTaken());

		historyRegister = Utils.shiftLeftAppendBit(historyRegister,
				historyBits, (actual ? 1 : 0));
	}

	@Override
	public void printMetrics() {
		printBits();

		super.printMetrics();
	}
	
	@Override
	public String getName() {
		return "TwoLevel";
	}

}