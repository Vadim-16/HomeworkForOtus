package ca.study.purpose;

public enum Bills {
    FIVETHOUSAND(5000),
    TWOTHOUSAND(2000),
    ONETHOUSAND(1000),
    FIVEHUNDRED(500),
    TWOHUNDRED(200),
    ONEHUNDRED(100),
    FIFTY(50),
    TEN(10);

    public final int value;

    Bills(int value) {
        this.value = value;
    }
}

