package ca.study.purpose;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATMSecondVersion implements ATMOps {
    private HashMap<Bills, Integer> money = new HashMap<>();
    private String lastUndoSavepoint = "pre-starting";
    private ATMOps chain;
    Caretaker careTaker = new Caretaker();

    public ATMSecondVersion(int numberOfBills) {
        money.put(Bills.FIVETHOUSAND, numberOfBills);
        money.put(Bills.TWOTHOUSAND, numberOfBills);
        money.put(Bills.ONETHOUSAND, numberOfBills);
        money.put(Bills.FIVEHUNDRED, numberOfBills);
        money.put(Bills.TWOHUNDRED, numberOfBills);
        money.put(Bills.ONEHUNDRED, numberOfBills);
        money.put(Bills.FIFTY, numberOfBills);
        money.put(Bills.TEN, numberOfBills);
        createSavepoint("INITIAL");
    }

    @Override
    public void deposit(Bills bill, int numberOfBills) {
        money.put(bill, money.get(bill) + numberOfBills);
        createSavepoint("Deposited " + numberOfBills + " " + bill.name());
    }

    @Override
    public boolean withdraw(Bills bill, int numberOfBills) {
        if (money.get(bill) >= numberOfBills) {
            money.put(bill, money.get(bill) - numberOfBills);
            createSavepoint("Withdrew " + numberOfBills + " " + bill.name());
            return true;
        } else {
            System.out.println("Not enough funds in ATMFirstVersion");
            return false;
        }
    }

    @Override
    public double balance() {
        double balance = 0;
        for (Map.Entry<Bills, Integer> entrySet : money.entrySet()) {
            balance += entrySet.getKey().value * entrySet.getValue();
        }
        return balance;
    }

    @Override
    public void createSavepoint(String savepointName) {
        careTaker.saveMemento(new Memento(this.money, this.lastUndoSavepoint), savepointName);
        lastUndoSavepoint = savepointName;
    }

    @Override
    public void undo() {
        setOriginatorState(lastUndoSavepoint);
    }

    @Override
    public void undoAll() {
        setOriginatorState("INITIAL");
        careTaker.clearSavePoints();
    }

    private void setOriginatorState(String savepointName) {
        Memento mem = careTaker.getMemento(savepointName);
        this.money = mem.getMoney();
        this.lastUndoSavepoint = mem.getLastUndoSavepoint();
    }

    public HashMap<Bills, Integer> collect() {
        HashMap<Bills, Integer> remainder = (HashMap<Bills, Integer>) money.clone();
        money.replaceAll((k, v) -> v = 0);
        createSavepoint("Collected");
        return remainder;
    }

    @Override
    public void setNextChain(ATMOps nextChain) {
        this.chain = nextChain;
    }

    @Override
    public void dispense(Bills bill, int numberOfBills, List<ATMOps> atms) {
        int currentNumberOfBills = money.get(bill);
        if (currentNumberOfBills >= numberOfBills) {
            money.put(bill, currentNumberOfBills - numberOfBills);
            createSavepoint("Dispensed " + numberOfBills + " " + bill.name());
        } else if (currentNumberOfBills > 0) {
            int remainder = numberOfBills - currentNumberOfBills;
            money.put(bill, 0);
            createSavepoint("Dispensed " + currentNumberOfBills + " " + bill.name());
            if (chain != null) {
                chain.dispense(bill, remainder, atms);
            } else {
                System.out.println("Not enough funds in the Department");
                atms.forEach(ATMOps::undo);
                atms.forEach(ATMOps::undo);
            }
        }
    }

}
