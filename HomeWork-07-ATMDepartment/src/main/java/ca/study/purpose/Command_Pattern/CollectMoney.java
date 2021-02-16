package ca.study.purpose.Command_Pattern;

import ca.study.purpose.ATMOps;
import ca.study.purpose.Bills;
import ca.study.purpose.Command_Pattern.Command;

import java.util.Map;

public class CollectMoney implements Command {
    private ATMOps atm;

    public CollectMoney(ATMOps atm) {
        this.atm = atm;
    }

    @Override
    public Map<Bills, Integer> execute() {
        Map<Bills, Integer> remainder = atm.collect();
        return remainder;
    }
}
