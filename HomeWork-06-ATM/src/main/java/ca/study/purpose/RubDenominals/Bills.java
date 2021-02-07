package ca.study.purpose.RubDenominals;

public interface Bills {

    int getValue();

    void deposit(int billCount);

    void withdraw(int billCount);

    int getCount();
}
