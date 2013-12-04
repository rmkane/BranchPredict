package com.rmkane.BranchPredict;

import com.rmkane.BranchPredict.predictor.BimodalBranchPredictor;
import com.rmkane.BranchPredict.predictor.BranchPredictor;
import com.rmkane.BranchPredict.predictor.GShareBranchPredictor;
import com.rmkane.BranchPredict.predictor.StaticBranchPredictor;
import com.rmkane.BranchPredict.predictor.TwoLevelPredictor;

public class App {

	private static final int START_BIT = 5, END_BIT = 25;
	
	private static int currIndex = 1;
	private static String traceFileName = "branch-trace-gcc.trace.gz";
	private static String[] output;

	static {
		output = new String[4];

		StringBuffer buffer = new StringBuffer();
		buffer.append("BHT Size,");

		for (int b = START_BIT; b < END_BIT; b++)
			buffer.append(String.format("%s,",
					Utils.label((int) Math.pow(2, b))));

		output[0] = buffer.toString();
	}

	public static void main(String[] args) {
		run(new StaticBranchPredictor(traceFileName, true), "Static Branch Predictor - Always Taken");
		run(new StaticBranchPredictor(traceFileName, false), "Static Branch Predictor - Always Not Taken");
		run(new BimodalBranchPredictor(traceFileName, START_BIT, END_BIT), "Bimodal Branch Predictor");
		run(new GShareBranchPredictor(traceFileName, START_BIT, END_BIT), "GShare Branch Predictor");
		run(new TwoLevelPredictor(traceFileName, START_BIT, END_BIT), "Two-Level Branch Predictor");
		printResults();
	}
	
	private static void run(BranchPredictor bp, String name) {
		String underline = Utils.repeat('-', name.length());
		System.out.printf("%s\n%s\n%s\n", Utils.separator(), name, underline);
		bp.execute();
		
		if (bp.getPredictions() != null) {
			output[currIndex++] =  bp.getPredictions();
		}
	}
	
	private static void printResults() {
		String sep = Utils.separator();
		System.out.printf("%s\nRESULTS:\n%s\n", sep, sep);

		for (String out : output) {
			System.out.println(out);
		}
	}
}

/* OUTPUT:

------------------------------------------------------------
Static Branch Predictor - Always Taken
--------------------------------------
Always Taken             : true
Correct Prediction       : 6211595 / 16416279 or %37.84
Misprediction            : 10204684 / 16416279 or %62.16
------------------------------------------------------------

------------------------------------------------------------
Static Branch Predictor - Always Not Taken
------------------------------------------
Always Taken             : false
Correct Prediction       : 10204684 / 16416279 or %62.16
Misprediction            : 6211595 / 16416279 or %37.84
------------------------------------------------------------

------------------------------------------------------------
Bimodal Branch Predictor
------------------------
Branch History Table Size: 32B
Correct Prediction       : 11543027 / 16416279 or %70.31
Misprediction            : 4873252 / 16416279 or %29.69
------------------------------------------------------------
Branch History Table Size: 64B
Correct Prediction       : 11881980 / 16416279 or %72.38
Misprediction            : 4534299 / 16416279 or %27.62
------------------------------------------------------------
Branch History Table Size: 128B
Correct Prediction       : 12318670 / 16416279 or %75.04
Misprediction            : 4097609 / 16416279 or %24.96
------------------------------------------------------------
Branch History Table Size: 256B
Correct Prediction       : 12783932 / 16416279 or %77.87
Misprediction            : 3632347 / 16416279 or %22.13
------------------------------------------------------------
Branch History Table Size: 512B
Correct Prediction       : 13306139 / 16416279 or %81.05
Misprediction            : 3110140 / 16416279 or %18.95
------------------------------------------------------------
Branch History Table Size: 1K
Correct Prediction       : 13798174 / 16416279 or %84.05
Misprediction            : 2618105 / 16416279 or %15.95
------------------------------------------------------------
Branch History Table Size: 2K
Correct Prediction       : 14137802 / 16416279 or %86.12
Misprediction            : 2278477 / 16416279 or %13.88
------------------------------------------------------------
Branch History Table Size: 4K
Correct Prediction       : 14368276 / 16416279 or %87.52
Misprediction            : 2048003 / 16416279 or %12.48
------------------------------------------------------------
Branch History Table Size: 8K
Correct Prediction       : 14470560 / 16416279 or %88.15
Misprediction            : 1945719 / 16416279 or %11.85
------------------------------------------------------------
Branch History Table Size: 16K
Correct Prediction       : 14562263 / 16416279 or %88.71
Misprediction            : 1854016 / 16416279 or %11.29
------------------------------------------------------------
Branch History Table Size: 32K
Correct Prediction       : 14576368 / 16416279 or %88.79
Misprediction            : 1839911 / 16416279 or %11.21
------------------------------------------------------------
Branch History Table Size: 64K
Correct Prediction       : 14588954 / 16416279 or %88.87
Misprediction            : 1827325 / 16416279 or %11.13
------------------------------------------------------------
Branch History Table Size: 128K
Correct Prediction       : 14591334 / 16416279 or %88.88
Misprediction            : 1824945 / 16416279 or %11.12
------------------------------------------------------------
Branch History Table Size: 256K
Correct Prediction       : 14602207 / 16416279 or %88.95
Misprediction            : 1814072 / 16416279 or %11.05
------------------------------------------------------------
Branch History Table Size: 512K
Correct Prediction       : 14602141 / 16416279 or %88.95
Misprediction            : 1814138 / 16416279 or %11.05
------------------------------------------------------------
Branch History Table Size: 1M
Correct Prediction       : 14602465 / 16416279 or %88.95
Misprediction            : 1813814 / 16416279 or %11.05
------------------------------------------------------------
Branch History Table Size: 2M
Correct Prediction       : 14602464 / 16416279 or %88.95
Misprediction            : 1813815 / 16416279 or %11.05
------------------------------------------------------------
Branch History Table Size: 4M
Correct Prediction       : 14602464 / 16416279 or %88.95
Misprediction            : 1813815 / 16416279 or %11.05
------------------------------------------------------------
Branch History Table Size: 8M
Correct Prediction       : 14602464 / 16416279 or %88.95
Misprediction            : 1813815 / 16416279 or %11.05
------------------------------------------------------------
Branch History Table Size: 16M
Correct Prediction       : 14602464 / 16416279 or %88.95
Misprediction            : 1813815 / 16416279 or %11.05
------------------------------------------------------------

------------------------------------------------------------
GShare Branch Predictor
-----------------------
Branch History Table Size: 32B
Correct Prediction       : 11291270 / 16416279 or %68.78
Misprediction            : 5125009 / 16416279 or %31.22
------------------------------------------------------------
Branch History Table Size: 64B
Correct Prediction       : 11600748 / 16416279 or %70.67
Misprediction            : 4815531 / 16416279 or %29.33
------------------------------------------------------------
Branch History Table Size: 128B
Correct Prediction       : 12041659 / 16416279 or %73.35
Misprediction            : 4374620 / 16416279 or %26.65
------------------------------------------------------------
Branch History Table Size: 256B
Correct Prediction       : 12519723 / 16416279 or %76.26
Misprediction            : 3896556 / 16416279 or %23.74
------------------------------------------------------------
Branch History Table Size: 512B
Correct Prediction       : 13120728 / 16416279 or %79.93
Misprediction            : 3295551 / 16416279 or %20.07
------------------------------------------------------------
Branch History Table Size: 1K
Correct Prediction       : 13731333 / 16416279 or %83.64
Misprediction            : 2684946 / 16416279 or %16.36
------------------------------------------------------------
Branch History Table Size: 2K
Correct Prediction       : 14110201 / 16416279 or %85.95
Misprediction            : 2306078 / 16416279 or %14.05
------------------------------------------------------------
Branch History Table Size: 4K
Correct Prediction       : 14408715 / 16416279 or %87.77
Misprediction            : 2007564 / 16416279 or %12.23
------------------------------------------------------------
Branch History Table Size: 8K
Correct Prediction       : 14564688 / 16416279 or %88.72
Misprediction            : 1851591 / 16416279 or %11.28
------------------------------------------------------------
Branch History Table Size: 16K
Correct Prediction       : 14649273 / 16416279 or %89.24
Misprediction            : 1767006 / 16416279 or %10.76
------------------------------------------------------------
Branch History Table Size: 32K
Correct Prediction       : 14683319 / 16416279 or %89.44
Misprediction            : 1732960 / 16416279 or %10.56
------------------------------------------------------------
Branch History Table Size: 64K
Correct Prediction       : 14697200 / 16416279 or %89.53
Misprediction            : 1719079 / 16416279 or %10.47
------------------------------------------------------------
Branch History Table Size: 128K
Correct Prediction       : 14699765 / 16416279 or %89.54
Misprediction            : 1716514 / 16416279 or %10.46
------------------------------------------------------------
Branch History Table Size: 256K
Correct Prediction       : 14711671 / 16416279 or %89.62
Misprediction            : 1704608 / 16416279 or %10.38
------------------------------------------------------------
Branch History Table Size: 512K
Correct Prediction       : 14711945 / 16416279 or %89.62
Misprediction            : 1704334 / 16416279 or %10.38
------------------------------------------------------------
Branch History Table Size: 1M
Correct Prediction       : 14712537 / 16416279 or %89.62
Misprediction            : 1703742 / 16416279 or %10.38
------------------------------------------------------------
Branch History Table Size: 2M
Correct Prediction       : 14712600 / 16416279 or %89.62
Misprediction            : 1703679 / 16416279 or %10.38
------------------------------------------------------------
Branch History Table Size: 4M
Correct Prediction       : 14712600 / 16416279 or %89.62
Misprediction            : 1703679 / 16416279 or %10.38
------------------------------------------------------------
Branch History Table Size: 8M
Correct Prediction       : 14712600 / 16416279 or %89.62
Misprediction            : 1703679 / 16416279 or %10.38
------------------------------------------------------------
Branch History Table Size: 16M
Correct Prediction       : 14712600 / 16416279 or %89.62
Misprediction            : 1703679 / 16416279 or %10.38
------------------------------------------------------------

------------------------------------------------------------
Two-Level Branch Predictor
--------------------------
Branch History Table Size: 32B
Correct Prediction       : 11547966 / 16416279 or %70.34
Misprediction            : 4868313 / 16416279 or %29.66
------------------------------------------------------------
Branch History Table Size: 64B
Correct Prediction       : 12005448 / 16416279 or %73.13
Misprediction            : 4410831 / 16416279 or %26.87
------------------------------------------------------------
Branch History Table Size: 128B
Correct Prediction       : 12662558 / 16416279 or %77.13
Misprediction            : 3753721 / 16416279 or %22.87
------------------------------------------------------------
Branch History Table Size: 256B
Correct Prediction       : 13283258 / 16416279 or %80.92
Misprediction            : 3133021 / 16416279 or %19.08
------------------------------------------------------------
Branch History Table Size: 512B
Correct Prediction       : 13917348 / 16416279 or %84.78
Misprediction            : 2498931 / 16416279 or %15.22
------------------------------------------------------------
Branch History Table Size: 1K
Correct Prediction       : 14336621 / 16416279 or %87.33
Misprediction            : 2079658 / 16416279 or %12.67
------------------------------------------------------------
Branch History Table Size: 2K
Correct Prediction       : 14587037 / 16416279 or %88.86
Misprediction            : 1829242 / 16416279 or %11.14
------------------------------------------------------------
Branch History Table Size: 4K
Correct Prediction       : 14732146 / 16416279 or %89.74
Misprediction            : 1684133 / 16416279 or %10.26
------------------------------------------------------------
Branch History Table Size: 8K
Correct Prediction       : 14781972 / 16416279 or %90.04
Misprediction            : 1634307 / 16416279 or %9.96
------------------------------------------------------------
Branch History Table Size: 16K
Correct Prediction       : 14818214 / 16416279 or %90.27
Misprediction            : 1598065 / 16416279 or %9.73
------------------------------------------------------------
Branch History Table Size: 32K
Correct Prediction       : 14829249 / 16416279 or %90.33
Misprediction            : 1587030 / 16416279 or %9.67
------------------------------------------------------------
Branch History Table Size: 64K
Correct Prediction       : 14835022 / 16416279 or %90.37
Misprediction            : 1581257 / 16416279 or %9.63
------------------------------------------------------------
Branch History Table Size: 128K
Correct Prediction       : 14836011 / 16416279 or %90.37
Misprediction            : 1580268 / 16416279 or %9.63
------------------------------------------------------------
Branch History Table Size: 256K
Correct Prediction       : 14838240 / 16416279 or %90.39
Misprediction            : 1578039 / 16416279 or %9.61
------------------------------------------------------------
Branch History Table Size: 512K
Correct Prediction       : 14838216 / 16416279 or %90.39
Misprediction            : 1578063 / 16416279 or %9.61
------------------------------------------------------------
Branch History Table Size: 1M
Correct Prediction       : 14838496 / 16416279 or %90.39
Misprediction            : 1577783 / 16416279 or %9.61
------------------------------------------------------------
Branch History Table Size: 2M
Correct Prediction       : 14838498 / 16416279 or %90.39
Misprediction            : 1577781 / 16416279 or %9.61
------------------------------------------------------------
Branch History Table Size: 4M
Correct Prediction       : 14838498 / 16416279 or %90.39
Misprediction            : 1577781 / 16416279 or %9.61
------------------------------------------------------------
Branch History Table Size: 8M
Correct Prediction       : 14838498 / 16416279 or %90.39
Misprediction            : 1577781 / 16416279 or %9.61
------------------------------------------------------------
Branch History Table Size: 16M
Correct Prediction       : 14838498 / 16416279 or %90.39
Misprediction            : 1577781 / 16416279 or %9.61
------------------------------------------------------------

------------------------------------------------------------
RESULTS:
------------------------------------------------------------
BHT Size,32B,64B,128B,256B,512B,1K,2K,4K,8K,16K,32K,64K,128K,256K,512K,1M,2M,4M,8M,16M,
Bimodal,70.31,72.38,75.04,77.87,81.05,84.05,86.12,87.52,88.15,88.71,88.79,88.87,88.88,88.95,88.95,88.95,88.95,88.95,88.95,88.95,
GShare,68.78,70.67,73.35,76.26,79.93,83.64,85.95,87.77,88.72,89.24,89.44,89.53,89.54,89.62,89.62,89.62,89.62,89.62,89.62,89.62,
TwoLevel,70.34,73.13,77.13,80.92,84.78,87.33,88.86,89.74,90.04,90.27,90.33,90.37,90.37,90.39,90.39,90.39,90.39,90.39,90.39,90.39,

*/