package ca.study.purpose.BillsCalculations;

import ca.study.purpose.RubCells.ATMCell;

import java.util.ArrayList;
import java.util.List;

public class Thousands implements CalculateBillsOperation{
    @Override
    public List<Integer> calculateBills(int amount, List<ATMCell> bills) {
        int restAmount = amount;
        List<Integer> billCount = new ArrayList<>();
        List<ATMCell> calculatedBills = new ArrayList<>(bills);

        ATMCell rub1000 = calculatedBills.get(2);
        calculatedBills.remove(2);
        calculatedBills.add(0, rub1000);

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
        billCount.add(2, numberOfBills);
        if (restAmount > 0) return null;
        else return billCount;
    }
}
