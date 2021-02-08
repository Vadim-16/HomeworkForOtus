package ca.study.purpose;

import ca.study.purpose.BillsCalculations.CalculateBillsOperation;
import ca.study.purpose.RubCells.*;

import java.util.ArrayList;

public class ATM {
    private final Rub10 rub10;
    private final Rub50 rub50;
    private final Rub100 rub100;
    private final Rub200 rub200;
    private final Rub500 rub500;
    private final Rub1000 rub1000;
    private final Rub2000 rub2000;
    private final Rub5000 rub5000;

    public ATM(int ATMStartNumberOfBills) {
        rub10 = new Rub10(ATMStartNumberOfBills);
        rub50 = new Rub50(ATMStartNumberOfBills);
        rub100 = new Rub100(ATMStartNumberOfBills);
        rub200 = new Rub200(ATMStartNumberOfBills);
        rub500 = new Rub500(ATMStartNumberOfBills);
        rub1000 = new Rub1000(ATMStartNumberOfBills);
        rub2000 = new Rub2000(ATMStartNumberOfBills);
        rub5000 = new Rub5000(ATMStartNumberOfBills);
    }

    public void deposit(ATMCell atmCell) {
        System.out.println("Depositing " + atmCell.getCount() * atmCell.getValue() + "rub...");
        if (atmCell instanceof Rub10) rub10.deposit(atmCell.getCount());
        else if (atmCell instanceof Rub50) rub50.deposit(atmCell.getCount());
        else if (atmCell instanceof Rub100) rub100.deposit(atmCell.getCount());
        else if (atmCell instanceof Rub200) rub200.deposit(atmCell.getCount());
        else if (atmCell instanceof Rub500) rub500.deposit(atmCell.getCount());
        else if (atmCell instanceof Rub1000) rub1000.deposit(atmCell.getCount());
        else if (atmCell instanceof Rub2000) rub2000.deposit(atmCell.getCount());
        else if (atmCell instanceof Rub5000) rub5000.deposit(atmCell.getCount());
        System.out.println("Deposit of " + atmCell.getCount() * atmCell.getValue() + "rub is successful!");
        System.out.println("====================================");
    }

    public void withdraw(int amount, CalculateBillsOperation calculateBillsOperation) {
        if (amount % 10 != 0) {
            System.out.println("ATM does not work with coins. Please try again.");
            return;
        }
        ArrayList<ATMCell> bills = new ArrayList<>();
        bills.add(rub5000);
        bills.add(rub2000);
        bills.add(rub1000);
        bills.add(rub500);
        bills.add(rub200);
        bills.add(rub100);
        bills.add(rub50);
        bills.add(rub10);

        System.out.println("Withdrawing " + amount + "rub(" + calculateBillsOperation.getClass().getSimpleName() + "):");
        ArrayList<Integer> billCount = calculateBillsOperation.calculateBills(amount, bills);

        //funds check in ATM (enough?)
        if (billCount == null){
            System.out.println("Not enough funds in ATM");
            System.out.println("====================================");
            return;
        }

        //withdraw from ATM storage
        for (int i = 0; i < bills.size(); i++) {
            bills.get(i).withdraw(billCount.get(i));
            System.out.println(bills.get(i).getValue() + "s - x" + billCount.get(i));
        }
        System.out.println("Withdraw of " + amount + "rub is successful!");
        System.out.println("====================================");

    }

    public void balance() {
        System.out.println("Checking ATM funds...");
        int balance = rub5000.getAmount() + rub2000.getAmount() + rub1000.getAmount() +
                rub500.getAmount() + rub200.getAmount() + rub100.getAmount() +
                rub50.getAmount() + rub10.getAmount();
        System.out.println("ATM holds " + balance + "rub");
        System.out.println("====================================");
    }
}
