package ca.study.purpose;

import java.util.HashMap;
import java.util.List;

public interface ATMOps {
    void deposit(Bills bill, int amount);
    boolean withdraw(Bills bill, int numberOfBills);
    double balance();

    void createSavepoint(String savepointName);
    void undo();
    void undoAll();

    HashMap<Bills, Integer> collect();

    void setNextChain(ATMOps nextChain);
    void dispense(Bills bill, int numberOfBills, List<ATMOps> atms);
}
