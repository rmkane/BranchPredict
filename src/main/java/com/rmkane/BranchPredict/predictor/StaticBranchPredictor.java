package com.rmkane.BranchPredict.predictor;

import com.rmkane.BranchPredict.Instruction;

public class StaticBranchPredictor extends BranchPredictor {

	private boolean alwaysTaken;

	public StaticBranchPredictor(String traceFileName) {
		super(traceFileName);
	}
	
	public StaticBranchPredictor(String traceFileName, boolean alwaysTaken) {
		this(traceFileName);
		
		this.alwaysTaken = alwaysTaken;
	}

	@Override
	public void execute() {
		readZipFile(traceFileName, BranchPredictor.UTF_8);
		printMetrics();
		System.out.println();
	}
	
	@Override
	public void printMetrics() {
		System.out.printf("%-25s: %b\n", "Always Taken", alwaysTaken);
		
		super.printMetrics();
	}
	
	@Override
	protected void resetValues() {
		super.resetValues();
	}
	
	@Override
	protected void parseLine(final String line, int count) {
		Instruction instruction = parseInstruction(line);

		if (instruction.isTaken() == alwaysTaken)
			takenTotal++;
		
		totalInstructions++;
	}
	
	@Override
	public String getName() {
		return "Static";
	}
}