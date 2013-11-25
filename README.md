#Java Branch Prediction

##Static Branch Predictor

A baseline measure of branch predictions to compare the dynamic methods. The two policies that are tested are: "always predict taken" and "always predict not taken".

##Bimodal - 2-Bit Bimodal Dynamic Branch Predictor

To make a prediction, the predictor selects a counter from the table using the lower-order n bits of the instruction's address (its program counter value). The direction of the prediction is mad based on the value of the counter. Values 00 or 01 are predicted not taken; values 10 or 11 are predicted taken.

##GShare - Correlating Prediction using Global Branch History

A gshare predictor is a more advanced dynamic branch predictor that uses the history of recently executed branches to predict the next branch. Gshare uses a global history register to record the taken/not-taken history of the last h branches. It does this by shifting in the most recent conditional branch outcome into the low-order bits of the branch history register. It then hashes the branch history and the PC of the branch when making predictions.

Whereas bimodal uses the lowest n bits of the program counter to index into the table, gshare uses the lowest n bits of the XOR (exclusive OR) of the program counter and the history register. (Note: in C/C++/Java/Python, the "^" operator is the XOR operator). Except for this different index into the table, the prediction mechanism is otherwise unchanged from a bimodal predictor. In essence a gshare predictor with zero history bits is basically just a bimodal predictor.

##TwoLevel - Correlating Prediction Using Two-Level Prediction

A two-level branch predictor uses a global history register to select one of m branch history tables. Each branch history table is a 2-bit bimodal predictor. The number of branch history tables is 2m.

The global history register records the taken/not-taken history of the last h branches. It does this by shifting in the most recent conditional branch outcome into the low-order bits of the branch history register. This is the exact same process that is used in the gshare predictor. The difference here is that the global history register value is used to select the branch history table to make the prediction. The low order bits of the branch address are used to select the entry of the branch history table selected by the global history register.