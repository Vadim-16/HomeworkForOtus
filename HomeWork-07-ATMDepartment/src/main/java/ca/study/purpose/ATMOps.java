package ca.study.purpose;

import java.util.List;
import java.util.Map;

public interface ATMOps {  //тут полная солянка. Пришлось вынести абстрактные методы разных паттернов в один интерфейс, чтобы везде все виделось.
    void deposit(Bills bill, int amount);
    boolean withdraw(Bills bill, int numberOfBills);
    double balance();

    //Memento
    void createSavepoint(String savepointName);
    void undo();
    void undoAll();

    //Command
    Map<Bills, Integer> collect();

    //Chain of responsibility
    void setNextChain(ATMOps nextChain);
    void dispense(Bills bill, int numberOfBills, List<ATMOps> atms);
}
