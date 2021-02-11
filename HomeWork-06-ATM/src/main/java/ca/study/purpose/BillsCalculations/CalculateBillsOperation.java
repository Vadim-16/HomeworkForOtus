package ca.study.purpose.BillsCalculations;

import ca.study.purpose.RubCells.ATMCell;

import java.util.List;

public interface CalculateBillsOperation {
    List<Integer> calculateBills(int Amount, List<ATMCell> bills);
}
