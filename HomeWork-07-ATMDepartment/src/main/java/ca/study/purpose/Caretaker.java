package ca.study.purpose;

import java.util.HashMap;
import java.util.Map;

public class Caretaker {
    private final Map<String, Memento> savePointStorage = new HashMap<>();

    public void saveMemento(Memento memento,String savepointName){
        System.out.println("Saving state: "+savepointName);
        savePointStorage.put(savepointName, memento);
    }

    public Memento getMemento(String savepointName){
        System.out.println("Undo to state: "+savepointName);
        return savePointStorage.get(savepointName);
    }

    public void clearSavePoints(){
        System.out.println("Clearing all save points...");
        savePointStorage.clear();
    }
}
