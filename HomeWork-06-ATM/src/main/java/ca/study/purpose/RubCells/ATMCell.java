package ca.study.purpose.RubCells;

public interface ATMCell {

    int getValue();

    int getCount();

    int getAmount();

    void deposit(int billCount);

    void withdraw(int billCount);
}
