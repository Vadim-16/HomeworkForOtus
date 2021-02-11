package ca.study.purpose;

import ca.study.purpose.BillsCalculations.BigBills;
import ca.study.purpose.BillsCalculations.Thousands;
import ca.study.purpose.BillsCalculations.TwoThousands;
import ca.study.purpose.RubCells.Rub500;

public class ATMRunner {
    public static void main(String[] args) {
        ATMFirstModel atmFirstModel = new ATMFirstModel(20);
        atmFirstModel.balance();

        atmFirstModel.deposit(new Rub500(20));
        atmFirstModel.balance();

        atmFirstModel.withdraw(74_780, new TwoThousands());
        atmFirstModel.balance();

        atmFirstModel.withdraw(33_740, new BigBills());
        atmFirstModel.balance();

        atmFirstModel.withdraw(15_260, new Thousands());
        atmFirstModel.balance();

    }
}
