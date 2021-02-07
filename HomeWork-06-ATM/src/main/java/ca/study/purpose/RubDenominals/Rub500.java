package ca.study.purpose.RubDenominals;

import ca.study.purpose.Rubles;

public class Rub500 extends Rubles implements Bills {
    public static final int value = 500;
    private int count;

    public Rub500(int count) {
        this.count = count;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void deposit(int billCount) {
        this.count += billCount;
    }

    @Override
    public void withdraw(int billCount) {
        this.count -= billCount;
    }

    @Override
    public int getCount() {
        return count;
    }

}
