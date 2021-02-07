package ca.study.purpose;

import ca.study.purpose.RubDenominals.*;

import java.util.ArrayList;

public class Rubles extends Money {
    private Rub10 rub10;
    private Rub50 rub50;
    private Rub100 rub100;
    private Rub200 rub200;
    private Rub500 rub500;
    private Rub1000 rub1000;
    private Rub2000 rub2000;
    private Rub5000 rub5000;

    public Rubles(int ATMStartNumberOfBills) {
        rub10 = new Rub10(ATMStartNumberOfBills);
        rub50 = new Rub50(ATMStartNumberOfBills);
        rub100 = new Rub100(ATMStartNumberOfBills);
        rub200 = new Rub200(ATMStartNumberOfBills);
        rub500 = new Rub500(ATMStartNumberOfBills);
        rub1000 = new Rub1000(ATMStartNumberOfBills);
        rub2000 = new Rub2000(ATMStartNumberOfBills);
        rub5000 = new Rub5000(ATMStartNumberOfBills);
    }

    void deposit(Bills bills) {
        if (bills instanceof Rub10) rub10.deposit(bills.getCount());  //I don't like these ifs
        else if (bills instanceof Rub50) rub50.deposit(bills.getCount());
        else if (bills instanceof Rub100) rub100.deposit(bills.getCount());
        else if (bills instanceof Rub200) rub200.deposit(bills.getCount());
        else if (bills instanceof Rub500) rub500.deposit(bills.getCount());
        else if (bills instanceof Rub1000) rub1000.deposit(bills.getCount());
        else if (bills instanceof Rub2000) rub2000.deposit(bills.getCount());
        else if (bills instanceof Rub5000) rub5000.deposit(bills.getCount());
        System.out.println("Deposit of " + bills.getCount() * bills.getValue() + "rub is successful!\n");
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

        //calculating amount and types of bills to be paid out
        for (Bills bill : bills) {
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

        //funds check in ATM (enough?)
        if (restAmount > 0) {
            System.out.println("Not enough funds in ATM\n");
            return;
        }

        //withdraw from ATM storage
        System.out.println("Withdrawing " + Amount + "rub from ATM:");
        for (int i = 0; i < bills.size(); i++) {
            bills.get(i).withdraw(billCount.get(i));
            System.out.println(bills.get(i).getValue() + "s - x" + billCount.get(i));
        }
        System.out.println("Withdraw of " + Amount + "rub is successful!\n");
    }

    void balance(){
        int balance = rub5000.getAmount() + rub2000.getAmount() + rub1000.getAmount() +
                rub500.getAmount() + rub200.getAmount() + rub100.getAmount()+
                rub50.getAmount() + rub10.getAmount();
        System.out.println("ATM holds " + balance + "rub\n");
    }
}
