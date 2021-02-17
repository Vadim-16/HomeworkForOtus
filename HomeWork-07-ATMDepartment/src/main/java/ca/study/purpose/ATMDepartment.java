package ca.study.purpose;

import ca.study.purpose.Chain_Of_Responsibility_Pattern.ATMDispenseChain;
import ca.study.purpose.Command_Pattern.CollectMoney;
import ca.study.purpose.Command_Pattern.MoneyCollector;

import java.util.*;

public class ATMDepartment implements ATMDepartmentOps{
    private List<ATMOps> atms = new ArrayList<>();

    public static void main(String[] args) {
        ATMDepartment atmDepartment = new ATMDepartment(new ATMSecondVersion(10),
                new ATMSecondVersion(20),
                new ATMSecondVersion(15));
        System.out.println(atmDepartment.balance());

        atmDepartment.addATM(new ATMSecondVersion(5));
        System.out.println(atmDepartment.balance());

        ATMDispenseChain atmDispenser = new ATMDispenseChain(atmDepartment.atms);  //снимаю купюры по цепочки ответственности
        atmDispenser.dispense(Bills.ONE_HUNDRED, 49);
        System.out.println(atmDepartment.balance());

        atmDispenser.dispense(Bills.ONE_THOUSAND, 51);  //проверяю поведение в случае нехватки купюр во всем департаменте
        System.out.println(atmDepartment.balance());               // и откатываю при помощи Memento

        atmDepartment.collectAll();                   //снимаю все остатки в АТМах
        System.out.println(atmDepartment.balance());

        atmDepartment.restoreAll();                   //востанавливаю первоначальное (индивидуальное) состояние во всех АТМах
        System.out.println(atmDepartment.balance());

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

    public double balance() {
        double balance = 0;
        for (ATMOps atm : atms) {
            balance += atm.balance();
        }
        return balance;
    }

    public Map<Bills, Integer> collect(int atmNumber) {          //снимает остаток в АТМе
        MoneyCollector moneyCollector = new MoneyCollector();
        moneyCollector.takeCommand(new CollectMoney(atms.get(atmNumber)));
        return moneyCollector.CollectAll();
    }

    public Map<Bills, Integer> collectAll() {                 //снимает все остатки в АТМах
        MoneyCollector moneyCollector = new MoneyCollector();
        for (ATMOps atm : atms) {
            moneyCollector.takeCommand(new CollectMoney(atm));
        }
        return moneyCollector.CollectAll();
    }

    public void restoreAll() {      //востанавливает первоначальное (индивидуальное) состояние во всех АТМах
        for (ATMOps atm : atms) {
            atm.undoAll();
        }
    }

}
