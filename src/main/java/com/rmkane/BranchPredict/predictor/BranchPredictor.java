package com.rmkane.BranchPredict.predictor;
import java.text.ParseException;

import com.rmkane.BranchPredict.FileParser;
import com.rmkane.BranchPredict.Instruction;
import com.rmkane.BranchPredict.Utils;

public abstract class BranchPredictor extends FileParser {

	protected int bit_start = 7;
	protected int bit_end = 15;
	
	protected int bits;
	protected long takenTotal;
	protected long totalInstructions;
	protected double[] takenPercentages;
	
	public BranchPredictor(String traceFileName) {
		this.traceFileName = traceFileName;
		
		resetValues();
	}
	
	public BranchPredictor(String traceFileName, int bitStart, int bitEnd) {
		this(traceFileName);
		
		this.bit_start = bitStart;
		this.bit_end = bitEnd;
	}
	
	public void execute() {
		int range = bit_end - bit_start;
		takenPercentages = new double[range];
	}
	
	public void updatePercentages() {
		if (bits > 0)
			takenPercentages[bits - bit_start] = Utils.percentValue(takenTotal, totalInstructions);
	}

	protected void resetValues() {
		this.takenTotal = 0;
		this.totalInstructions = 0;
	}
	
	protected Instruction parseInstruction(String line) {
		Instruction instruction = null;

		try {
			instruction = new Instruction(line);
		} catch (ParseException e) {
			handleException(e);
		}
		
		return instruction;
	}
	
	public void printBits() {
		System.out.printf("%-25s: %s\n", "Branch History Table Size", Utils.label((int) Math.pow(2, bits)));
	}
	
	public void printMetrics() {
		System.out.printf("%-25s: %s\n", "Correct Prediction", Utils.percentageMetrics(takenTotal, totalInstructions));
		System.out.printf("%-25s: %s\n", "Misprediction", Utils.percentageMetrics(totalInstructions - takenTotal, totalInstructions));
		System.out.println(Utils.separator());
		
		updatePercentages();
	}
	
	public String getPredictions() {
		if (takenPercentages != null) {
			StringBuffer buffer = new StringBuffer();
			
			buffer.append(String.format("%s,", getName()));
			
			for (double percentage : takenPercentages)
				buffer.append(String.format("%.2f,", percentage));
			
			return buffer.toString();
		}
		
		return null;
	}
	
	public String getName() {
		return "DEFAULT";
	}
}