package com.rmkane.BranchPredict;
import java.text.ParseException;

public class Instruction {

	private long address;
	private boolean taken;
	
	public Instruction(final String line) throws ParseException {
		String[] parts = line.trim().split("\\s");
		
		if (parts.length != 2)
			throw new ParseException("Malformed instruction", 0);
		
		this.address = Long.parseLong(parts[0]);
		this.taken = parts[1].equals("T");
	}
	
	public String takenStr() {
		return taken ? "T" : "N";
	}
	
	public long getAddress() {
		return address;
	}

	public boolean isTaken() {
		return taken;
	}
	
	public String toString() {
		return String.format("%s %s",
			Utils.longToBin(address, 32), takenStr());
	}
}