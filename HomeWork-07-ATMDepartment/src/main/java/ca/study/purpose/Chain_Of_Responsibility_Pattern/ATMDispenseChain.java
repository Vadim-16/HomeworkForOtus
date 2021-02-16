package ca.study.purpose.Chain_Of_Responsibility_Pattern;

import ca.study.purpose.ATMOps;
import ca.study.purpose.Bills;

import java.util.List;

public class ATMDispenseChain {
    private List<ATMOps> atms;

    public ATMDispenseChain(List<ATMOps> atms) {
        this.atms = atms;
        for (int i = 0; i < this.atms.size() - 1; i++) {
            this.atms.get(i).setNextChain(this.atms.get(i + 1));
        }
    }
    public void dispense(Bills bill, int numberOfBills) {
        atms.get(0).dispense(bill, numberOfBills, atms);
    }
}
