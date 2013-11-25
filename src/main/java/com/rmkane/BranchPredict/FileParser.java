package com.rmkane.BranchPredict;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;

public class FileParser {

	public static final String UTF_8 = "UTF-8";
	
	protected String traceFileName;
	
	public void readTextFile(String filename) {
		FileReader decoder = null;
		BufferedReader reader = null;

		try {
			decoder = new FileReader(filename);
			reader = new BufferedReader(decoder);
			
			parseLines(reader);
		} catch (Exception e) {
			handleException(e);
		} finally {
			try {
				if (reader != null)
					reader.close();
				if (decoder != null)
					decoder.close();
			} catch (IOException e) {
				handleException(e);
			}
		}
	}

	public void readZipFile(String filename, String encoding) {
		String _encoding = encoding != null ? encoding : UTF_8;
		InputStream fileStream = null;
		InputStream gzipStream = null;
		Reader decoder = null;
		BufferedReader reader = null;

		try {
			// If in the same directory as this file.
			//fileStream = new FileInputStream(filename);
			
			fileStream = this.getClass().getClassLoader().getResourceAsStream(filename);
			gzipStream = new GZIPInputStream(fileStream);
			decoder = new InputStreamReader(gzipStream, _encoding);
			reader = new BufferedReader(decoder);
			
			parseLines(reader);
		} catch (Exception e) {
			handleException(e);
		} finally {
			try {
				if (reader != null)
					reader.close();
				if (decoder != null)
					decoder.close();
				if (gzipStream != null)
					gzipStream.close();
				if (fileStream != null)
					fileStream.close();
			} catch (IOException e) {
				handleException(e);
			}
		}
	}

	protected void parseLines(BufferedReader reader) {
		String line = null;
		int count = 0;
		
		try {
			while ((line = reader.readLine()) != null) {
				parseLine(line, count++);
			}
		} catch (IOException e) {
			handleException(e);
		}
	}

	protected void parseLine(final String line, int count) {
		Instruction instruction = null;
		
		try {
			instruction = new Instruction(line);
		} catch (ParseException e) {
			handleException(e);
		}
		
		System.out.println(instruction);
	}

	protected void handleException(Exception e) {
		String errMsg = null;

		if (e instanceof FileNotFoundException) {
			errMsg = "FILE NOT FOUND: " + e.getMessage();
		}

		if (e instanceof ZipException) {
			errMsg = "NOT ZIP FILE: " + e.getMessage();
		}

		if (errMsg != null) {
			System.out.println(errMsg);
		} else {
			e.printStackTrace();
		}
	}	
}
