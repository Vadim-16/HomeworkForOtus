package ca.study.purpose;

import ca.study.purpose.RubDenominals.*;

import java.util.ArrayList;

public class Rubles extends Money {
    private int ATMStartNumberOfBills;
    private Rub10 rub10 = new Rub10(ATMStartNumberOfBills);
    private Rub50 rub50 = new Rub50(ATMStartNumberOfBills);
    private Rub100 rub100 = new Rub100(ATMStartNumberOfBills);
    private Rub200 rub200 = new Rub200(ATMStartNumberOfBills);
    private Rub500 rub500 = new Rub500(ATMStartNumberOfBills);
    private Rub1000 rub1000 = new Rub1000(ATMStartNumberOfBills);
    private Rub2000 rub2000 = new Rub2000(ATMStartNumberOfBills);
    private Rub5000 rub5000 = new Rub5000(ATMStartNumberOfBills);

    public Rubles() {
        this.ATMStartNumberOfBills = 100;
    }

    public Rubles(int ATMStartNumberOfBills) {
        this.ATMStartNumberOfBills = ATMStartNumberOfBills;
    }

    void deposit(Bills Bills) {
        if (Bills instanceof Rub10) rub10.deposit(Bills.getCount());  //I don't like these ifs
        else if (Bills instanceof Rub50) rub50.deposit(Bills.getCount());
        else if (Bills instanceof Rub100) rub100.deposit(Bills.getCount());
        else if (Bills instanceof Rub200) rub200.deposit(Bills.getCount());
        else if (Bills instanceof Rub500) rub500.deposit(Bills.getCount());
        else if (Bills instanceof Rub1000) rub1000.deposit(Bills.getCount());
        else if (Bills instanceof Rub2000) rub2000.deposit(Bills.getCount());
        else if (Bills instanceof Rub5000) rub5000.deposit(Bills.getCount());
    }

    void withdraw(int Amount) {
        int restAmount = Amount;
        ArrayList<Integer> billCount = new ArrayList<>();
        ArrayList<Bills> bills = new ArrayList<>();
        bills.add(rub5000);
        bills.add(rub2000);
        bills.add(rub1000);
        bills.add(rub500);
        bills.add(rub200);
        bills.add(rub100);
        bills.add(rub50);
        bills.add(rub10);

        while (restAmount > 9) {
            for (Bills bill : bills) {
                if (restAmount >= bill.getValue()) {
                    int count = restAmount / bill.getValue();
                    if (bill.getCount() < count) {
                        System.out.println("Not enough funds");
                        return;
                    }
                    billCount.add(count);
                    restAmount = restAmount % bill.getValue();
                } else billCount.add(0);
            }
        }


    }
}
