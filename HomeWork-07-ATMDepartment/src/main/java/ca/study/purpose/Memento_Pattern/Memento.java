package ca.study.purpose.Memento_Pattern;

import ca.study.purpose.Bills;

import java.util.HashMap;
import java.util.Map;

public class Memento {
    private final Map<Bills, Integer> money;
    private final String lastUndoSavepoint;

    public Memento(Map<Bills, Integer> money, String lastUndoSavepoint) {
        this.money = new HashMap<>(money);
        this.lastUndoSavepoint = lastUndoSavepoint;
    }

    public Map<Bills, Integer> getMoney() {
        return money;
    }

    public String getLastUndoSavepoint() {
        return lastUndoSavepoint;
    }
}
