package ca.study.purpose;

import java.util.*;

public class ATMDepartment {
    private List<ATMOps> atms = new ArrayList<>();

    public static void main(String[] args) {
        ATMDepartment atmDepartment = new ATMDepartment(new ATMSecondVersion(10),
                new ATMSecondVersion(20),
                new ATMSecondVersion(15));
        System.out.println(atmDepartment.balance());

        ATMDispenseChain atmDispenser = new ATMDispenseChain(atmDepartment.atms);
        atmDispenser.dispense(Bills.ONEHUNDRED, 40);
        System.out.println(atmDepartment.balance());

        atmDispenser.dispense(Bills.ONETHOUSAND, 46);
        System.out.println(atmDepartment.balance());

        atmDepartment.collectAll();
        System.out.println(atmDepartment.balance());

        atmDepartment.restoreAll();
        System.out.println(atmDepartment.balance());

    }

    public ATMDepartment(ATMOps... atm) {
        atms.addAll(Arrays.asList(atm));
    }

    public List<ATMOps> getATMList() {
        return atms;
    }

    public void addATM(ATMOps atm) {
        atms.add(atm);
    }

    public void deleteATM(ATMOps atm) {
        atms.remove(atm);
    }

    public double balance() {
        double balance = 0;
        for (ATMOps atm : atms) {
            balance += atm.balance();
        }
        return balance;
    }

    public HashMap<Bills, Integer> collect(ATMOps atm) {
        MoneyCollector moneyCollector = new MoneyCollector();
        moneyCollector.takeCommand(new CollectMoney(atm));
        return moneyCollector.CollectAll();
    }

    public HashMap<Bills, Integer> collectAll() {
        MoneyCollector moneyCollector = new MoneyCollector();
        for (ATMOps atm : atms) {
            moneyCollector.takeCommand(new CollectMoney(atm));
        }
        return moneyCollector.CollectAll();
    }

    public void restoreAll() {
        for (ATMOps atm : atms) {
            atm.undoAll();
        }
    }

}
