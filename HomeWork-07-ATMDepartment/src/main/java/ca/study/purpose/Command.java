package ca.study.purpose;

import java.util.HashMap;

public interface Command {
    HashMap<Bills, Integer> execute();
}
