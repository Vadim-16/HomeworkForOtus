package ca.study.purpose;

import ca.study.purpose.BillsCalculations.BigBills;
import ca.study.purpose.BillsCalculations.Thousands;
import ca.study.purpose.BillsCalculations.TwoThousands;
import ca.study.purpose.RubCells.Rub500;

public class ATMRunner {
    public static void main(String[] args) {
        ATM atm = new ATM(20);
        atm.balance();

        atm.deposit(new Rub500(20));
        atm.balance();

        atm.withdraw(74_780, new TwoThousands());
        atm.balance();

        atm.withdraw(33_740, new BigBills());
        atm.balance();

        atm.withdraw(15_260, new Thousands());
        atm.balance();

    }
}
