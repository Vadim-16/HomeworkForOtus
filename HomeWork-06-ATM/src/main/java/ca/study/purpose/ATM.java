package ca.study.purpose;

import ca.study.purpose.BillsCalculations.CalculateBillsOperation;
import ca.study.purpose.RubCells.ATMCell;

public interface ATM {
    void deposit(ATMCell atmCell);
    void withdraw(int amount, CalculateBillsOperation calculateBillsOperation);
    int balance();
}
