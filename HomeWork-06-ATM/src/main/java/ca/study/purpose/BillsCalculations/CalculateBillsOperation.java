package ca.study.purpose.BillsCalculations;

import ca.study.purpose.RubCells.ATMCell;

import java.util.ArrayList;

public interface CalculateBillsOperation {
    ArrayList<Integer> calculateBills(int Amount, ArrayList<ATMCell> bills);
}
