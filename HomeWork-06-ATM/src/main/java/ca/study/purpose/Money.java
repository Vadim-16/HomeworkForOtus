package ca.study.purpose;

import ca.study.purpose.RubDenominals.Bills;

abstract public class Money {

    abstract void deposit(Bills bills);

    abstract void withdraw(int Amount);

    abstract void balance();

}
