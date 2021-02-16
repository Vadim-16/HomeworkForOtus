package ca.study.purpose.Command_Pattern;

import ca.study.purpose.Bills;
import ca.study.purpose.Command_Pattern.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoneyCollector {
    private List<Command> collectorList = new ArrayList<>();

    public void takeCommand(Command command) {
        collectorList.add(command);
    }

    public Map<Bills, Integer> CollectAll() {
        Map<Bills, Integer> totalMoney = new HashMap<>();

        for (Command command : collectorList) {
            Map<Bills, Integer> remainder = command.execute();
            for (Map.Entry<Bills, Integer> entry : remainder.entrySet()) {
                totalMoney.compute(entry.getKey(), (k, v) -> v == null ? remainder.get(k) : v + remainder.get(k));
            }

        }
        collectorList.clear();
        return totalMoney;
    }
}
