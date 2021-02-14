package ca.study.purpose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoneyCollector {
    private List<Command> collectorList = new ArrayList<>();

    public void takeCommand(Command command) {
        collectorList.add(command);
    }

    public HashMap<Bills, Integer> CollectAll() {
        HashMap<Bills, Integer> totalMoney = new HashMap<>();

        for (Command command : collectorList) {
            HashMap<Bills, Integer> remainder = command.execute();
            for (Map.Entry<Bills, Integer> entry : remainder.entrySet()) {
                totalMoney.compute(entry.getKey(), (k, v) -> v == null ? remainder.get(k) : v + remainder.get(k));
            }

        }
        collectorList.clear();
        return totalMoney;
    }
}
