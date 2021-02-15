package ca.study.purpose;

import java.util.HashMap;

public class Memento {
    private HashMap<Bills, Integer> money;
    private String lastUndoSavepoint;

    public Memento(HashMap<Bills, Integer> money, String lastUndoSavepoint) {
        this.money = (HashMap<Bills, Integer>) money.clone();
        this.lastUndoSavepoint = lastUndoSavepoint;
    }

    public HashMap<Bills, Integer> getMoney() {
        return money;
    }

    public String getLastUndoSavepoint() {
        return lastUndoSavepoint;
    }
}
