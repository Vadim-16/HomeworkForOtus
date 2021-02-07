package ca.study.purpose;

import ca.study.purpose.RubDenominals.Rub500;

public class ATMRunner {
    public static void main(String[] args) {
        ATM atm = new ATM(100);
        atm.balance();

        atm.deposit(new Rub500(20));
        atm.balance();

        atm.withdraw(834_780);
        atm.balance();

        atm.withdraw(60_220);
        atm.balance();

        atm.withdraw(5_000);
        atm.balance();

    }
}
