package ca.study.purpose.Command_Pattern;

import ca.study.purpose.Bills;

import java.util.Map;

public interface Command {
    Map<Bills, Integer> execute();
}
