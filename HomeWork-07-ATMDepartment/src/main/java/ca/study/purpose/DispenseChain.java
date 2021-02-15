package ca.study.purpose;

public interface DispenseChain {

    void setNextChain(DispenseChain nextChain);

    void dispense(Bills bill, int numberOfBills);
}
