package ca.study.purpose.Chain_Of_Responsibility_Pattern;

import ca.study.purpose.Bills;

public interface DispenseChain { //перенес в интерфейс ATMOps (по сути можно удалить)

    void setNextChain(DispenseChain nextChain);

    void dispense(Bills bill, int numberOfBills);
}
