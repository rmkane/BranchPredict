package com.rmkane.BranchPredict.predictor;

import com.rmkane.BranchPredict.Instruction;

public class BimodalBranchPredictor extends BranchPredictor {

	private Bimodal bimodal;
	
	public BimodalBranchPredictor(String traceFileName) {
		super(traceFileName);
	}
	
	public BimodalBranchPredictor(String traceFileName, int bitStart, int bitEnd) {
		super(traceFileName, bitStart, bitEnd);
	}
	
	@Override
	public void execute() {
		super.execute();
		
		for (int bits = bit_start; bits < bit_end; bits++) {
			resetValues();
			this.bits = bits;
			bimodal = new Bimodal(bits);
			readZipFile(traceFileName, BranchPredictor.UTF_8);
			printMetrics();
		}
		
		System.out.println();
	}
	
	@Override
	protected void parseLine(final String line, int count) {
		Instruction instruction = parseInstruction(line);
		int value = bimodal.getValue(instruction.getAddress());
		boolean actual = value > 1;
		
		if (actual == instruction.isTaken())
			takenTotal++;
		
		totalInstructions++;
		
		bimodal.train(instruction.getAddress(), instruction.isTaken());
	}
	
	@Override
	public void printMetrics() {
		printBits();
		
		super.printMetrics();
	}
	
	@Override
	public String getName() {
		return "Bimodal";
	}

}