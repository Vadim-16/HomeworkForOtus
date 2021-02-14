package ca.study.purpose;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ATMDepartment {
    private List<ATMOps> atms = new ArrayList<>();

    public static void main(String[] args) {
        ATMDepartment atmDepartment = new ATMDepartment(new ATMSecondVersion(10),
                new ATMSecondVersion(20),
                new ATMSecondVersion(15));


    }

    public ATMDepartment(ATMOps... atm) {
        atms.addAll(Arrays.asList(atm));
    }

    public List<ATMOps> getATMList() {
        return atms;
    }

    public void addATM(ATMOps atm) {
        atms.add(atm);
    }

    public void deleteATM(ATMOps atm) {
        atms.remove(atm);
    }

    public HashMap<Bills, Integer> collect(ATMOps atm) {
        MoneyCollector moneyCollector = new MoneyCollector();
        moneyCollector.takeCommand(new CollectMoney(atm));
        return moneyCollector.CollectAll();
    }

    public HashMap<Bills, Integer> collectAll() {
        HashMap<Bills, Integer> totalRemainder = new HashMap<>();

        MoneyCollector moneyCollector = new MoneyCollector();
        for (ATMOps atm : atms) {
            moneyCollector.takeCommand(new CollectMoney(atm));
        }
        return moneyCollector.CollectAll();
    }

    public void restoreAll() {
        for (ATMOps atm : atms) {
            atm.undoAll();
        }
    }

}
