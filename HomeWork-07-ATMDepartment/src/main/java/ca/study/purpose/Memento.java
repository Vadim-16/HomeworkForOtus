package ca.study.purpose;

import java.util.HashMap;

public class Memento {
    private HashMap<Bills, Integer> money;
    private String lastUndoSavepoint;

    public Memento(HashMap<Bills, Integer> money, String lastUndoSavepoint) {
        this.money = money;
    }

    public HashMap<Bills, Integer> getMoney() {
        return money;
    }

    public String getLastUndoSavepoint() {
        return lastUndoSavepoint;
    }
}
