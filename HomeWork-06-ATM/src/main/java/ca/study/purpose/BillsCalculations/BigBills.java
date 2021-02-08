package ca.study.purpose.BillsCalculations;

import ca.study.purpose.BillsCalculations.CalculateBillsOperation;
import ca.study.purpose.RubCells.ATMCell;

import java.util.ArrayList;

public class BigBills implements CalculateBillsOperation {

    @Override
    public ArrayList<Integer> calculateBills(int amount, ArrayList<ATMCell> bills) {
        int restAmount = amount;
        ArrayList<Integer> billCount = new ArrayList<>();

        for (ATMCell bill : bills) {
            int valueOfBill = bill.getValue();
            int numberOfBills = bill.getCount();
            if (restAmount >= valueOfBill) {
                int count = restAmount / valueOfBill;
                if (numberOfBills < count) {
                    billCount.add(numberOfBills);
                    restAmount = restAmount - valueOfBill * numberOfBills;
                } else {
                    billCount.add(count);
                    restAmount = restAmount % valueOfBill;
                }
            } else billCount.add(0);
        }
        if (restAmount > 0) return null;
        else return billCount;
    }
}