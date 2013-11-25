package com.rmkane.BranchPredict.predictor;

import com.rmkane.BranchPredict.Instruction;
import com.rmkane.BranchPredict.NBitUInteger;

public class GShareBranchPredictor extends BranchPredictor {
	
	private GShare gshare;
	private NBitUInteger globalHistoryRegister;

	public GShareBranchPredictor(String traceFileName) {
		super(traceFileName);
		resetValues();
	}
	
	public GShareBranchPredictor(String traceFileName, int bitStart, int bitEnd) {
		super(traceFileName, bitStart, bitEnd);
		resetValues();
	}
	
	@Override
	public void execute() {
		super.execute();
		
		for (int bits = bit_start; bits < bit_end; bits++) {
			resetValues();
			this.bits = bits;
			gshare = new GShare(bits, 0);
			readZipFile(traceFileName, BranchPredictor.UTF_8);
			printMetrics();
		}
		
		System.out.println();
	}
	
	@Override
	protected void resetValues() {
		super.resetValues();
		
		this.globalHistoryRegister = new NBitUInteger(bits);
	}
	
	@Override
	protected void parseLine(final String line, int count) {
		Instruction instruction = parseInstruction(line);
		int value = gshare.getValue(instruction.getAddress(), globalHistoryRegister.intValue());
		boolean actual = value > 1;
		
		if (actual == instruction.isTaken())
			takenTotal++;
		
		totalInstructions++;
		
		gshare.train(instruction.getAddress(), globalHistoryRegister.intValue(), instruction.isTaken());
		
		globalHistoryRegister.shiftLeftAppendBit(actual ? 1 : 0);
	}
	
	@Override
	public void printMetrics() {
		printBits();
		
		super.printMetrics();
	}
	
	@Override
	public String getName() {
		return "GShare";
	}

}