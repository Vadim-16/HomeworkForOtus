package ca.study.purpose;

import java.util.List;
import java.util.Map;

public interface ATMDepartmentOps {
    public List<ATMOps> getATMList();
    public void addATM(ATMOps atm);
    public void deleteATM(ATMOps atm);
    public double balance();
    public Map<Bills, Integer> collectAll();
    public void restoreAll();
}
