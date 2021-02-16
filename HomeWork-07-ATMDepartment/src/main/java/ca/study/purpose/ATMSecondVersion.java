package ca.study.purpose;

import ca.study.purpose.Memento_Pattern.Caretaker;
import ca.study.purpose.Memento_Pattern.Memento;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATMSecondVersion implements ATMOps {
    private Map<Bills, Integer> money = new HashMap<>();
    private String lastUndoSavepoint = "pre-starting";  //need for Memento only
    private ATMOps chain;                               //need for Chain Of Responsibility only
    private Caretaker careTaker = new Caretaker();      //need for Memento only

    public ATMSecondVersion(int numberOfBills) {
        money.put(Bills.FIVE_THOUSAND, numberOfBills);
        money.put(Bills.TWO_THOUSAND, numberOfBills);
        money.put(Bills.ONE_THOUSAND, numberOfBills);
        money.put(Bills.FIVE_HUNDRED, numberOfBills);
        money.put(Bills.TWO_HUNDRED, numberOfBills);
        money.put(Bills.ONE_HUNDRED, numberOfBills);
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
    public void createSavepoint(String savepointName) {    //need for Memento
        careTaker.saveMemento(new Memento(this.money, this.lastUndoSavepoint), savepointName);
        lastUndoSavepoint = savepointName;
    }

    @Override
    public void undo() {                      //Memento pattern
        setOriginatorState(lastUndoSavepoint);
    }

    @Override
    public void undoAll() {            //Memento pattern
        setOriginatorState("INITIAL");
        careTaker.clearSavePoints();
    }

    private void setOriginatorState(String savepointName) {    //Memento pattern
        Memento mem = careTaker.getMemento(savepointName);
        this.money = mem.getMoney();
        this.lastUndoSavepoint = mem.getLastUndoSavepoint();
    }

    public Map<Bills, Integer> collect() {                    //Command pattern
        Map<Bills, Integer> remainder = new HashMap<>(money);
        money.replaceAll((k, v) -> v = 0);
        createSavepoint("Collected");
        return remainder;
    }

    @Override
    public void setNextChain(ATMOps nextChain) {
        this.chain = nextChain;
    }  //Chain of responsibility pattern

    @Override
    public void dispense(Bills bill, int numberOfBills, List<ATMOps> atms) {  //Chain of responsibility pattern
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
