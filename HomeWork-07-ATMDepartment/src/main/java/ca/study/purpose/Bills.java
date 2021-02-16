package ca.study.purpose;

public enum Bills {
    FIVE_THOUSAND(5000),
    TWO_THOUSAND(2000),
    ONE_THOUSAND(1000),
    FIVE_HUNDRED(500),
    TWO_HUNDRED(200),
    ONE_HUNDRED(100),
    FIFTY(50),
    TEN(10);

    public final int value;

    Bills(int value) {
        this.value = value;
    }
}

