package ca.study.purpose;

import ca.study.purpose.BillsCalculations.CalculateBillsOperation;
import ca.study.purpose.RubCells.*;

import java.util.ArrayList;
import java.util.List;

public class ATMFirstModel implements ATM {
    private final List<ATMCell> billTypes = new ArrayList<>();

    public ATMFirstModel(int ATMStartNumberOfBills) {
        billTypes.add(new Rub5000(ATMStartNumberOfBills));
        billTypes.add(new Rub2000(ATMStartNumberOfBills));
        billTypes.add(new Rub1000(ATMStartNumberOfBills));
        billTypes.add(new Rub500(ATMStartNumberOfBills));
        billTypes.add(new Rub200(ATMStartNumberOfBills));
        billTypes.add(new Rub100(ATMStartNumberOfBills));
        billTypes.add(new Rub50(ATMStartNumberOfBills));
        billTypes.add(new Rub10(ATMStartNumberOfBills));
    }

    public void deposit(ATMCell atmCell) {
        System.out.println("Depositing " + atmCell.getCount() * atmCell.getValue() + "rub...");
        Class<? extends ATMCell> clazz = atmCell.getClass();
        for (ATMCell bills : billTypes) {
            if (clazz.isInstance(bills)) {
                bills.deposit(atmCell.getCount());
            }
        }
        System.out.println("Deposit of " + atmCell.getCount() * atmCell.getValue() + "rub is successful!");
        System.out.println("====================================");
    }

    public void withdraw(int amount, CalculateBillsOperation calculateBillsOperation) {
        if (amount % 10 != 0) {
            System.out.println("ATM does not work with coins. Please try again.");
            return;
        }
        System.out.println("Withdrawing " + amount + "rub(" + calculateBillsOperation.getClass().getSimpleName() + "):");
        List<Integer> billCount = calculateBillsOperation.calculateBills(amount, billTypes);

        //funds check in ATM (enough?)
        if (billCount == null){
            System.out.println("Not enough funds in ATM");
            System.out.println("====================================");
            return;
        }

        //withdraw from ATM storage
        for (int i = 0; i < billTypes.size(); i++) {
            billTypes.get(i).withdraw(billCount.get(i));
            System.out.println(billTypes.get(i).getValue() + "s - x" + billCount.get(i));
        }
        System.out.println("Withdraw of " + amount + "rub is successful!");
        System.out.println("====================================");

    }

    public int balance() {
        System.out.println("Checking ATM funds...");
        int balance = billTypes.stream().mapToInt(ATMCell::getAmount).sum();
        System.out.println(balance);
        System.out.println("====================================");
        return balance;
    }
}
