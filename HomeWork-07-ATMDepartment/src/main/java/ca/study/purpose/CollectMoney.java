package ca.study.purpose;

import java.util.HashMap;

public class CollectMoney implements Command {
    private ATMOps atm;

    public CollectMoney(ATMOps atm) {
        this.atm = atm;
    }

    @Override
    public HashMap<Bills, Integer> execute() {
        HashMap<Bills, Integer> remainder = atm.collect();
        return remainder;
    }
}
