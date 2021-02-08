package ca.study.purpose.BillsCalculations;

import ca.study.purpose.RubCells.ATMCell;

import java.util.ArrayList;

public class TwoThousands implements CalculateBillsOperation {
    @Override
    public ArrayList<Integer> calculateBills(int amount, ArrayList<ATMCell> bills) {
        int restAmount = amount;
        ArrayList<Integer> billCount = new ArrayList<>();
        ArrayList<ATMCell> calculatedBills = new ArrayList<>(bills);

        ATMCell rub2000 = calculatedBills.get(1);
        calculatedBills.remove(1);
        calculatedBills.add(0, rub2000);

        for (ATMCell bill : calculatedBills) {
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
        Integer numberOfBills = billCount.get(0);
        billCount.remove(0);
        billCount.add(1, numberOfBills);
        if (restAmount > 0) return null;
        else return billCount;
    }
}
