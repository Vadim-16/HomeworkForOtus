package ca.study.purpose;

import ca.study.purpose.RubDenominals.*;


public class ATM {
    private Rubles rubles;

    public ATM(int ATMStartNumberOfBills){
        rubles = new Rubles(ATMStartNumberOfBills);
    }

    void deposit(Bills bills) {
        rubles.deposit(bills);
    }

    void withdraw(int Amount) {
        rubles.withdraw(Amount);
    }

    void balance(){
        rubles.balance();
    }
}
