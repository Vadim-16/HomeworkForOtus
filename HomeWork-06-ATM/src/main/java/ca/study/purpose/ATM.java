package ca.study.purpose;

import ca.study.purpose.RubDenominals.*;


public class ATM {
    public static void main(String[] args) {
        Rubles rubles = new Rubles(100);
        rubles.balance();

        rubles.deposit(new Rub500(50));
        rubles.balance();

        rubles.withdraw(706_280);
        rubles.balance();

        rubles.withdraw(200_370);
        rubles.balance();

        rubles.withdraw(4_910);
        rubles.balance();
    }
}
