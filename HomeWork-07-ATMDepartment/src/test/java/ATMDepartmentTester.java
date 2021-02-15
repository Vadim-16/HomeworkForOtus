import ca.study.purpose.ATMDepartment;
import ca.study.purpose.ATMDispenseChain;
import ca.study.purpose.ATMSecondVersion;
import ca.study.purpose.Bills;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ATMDepartmentTester {
    private ATMDepartment atmDepartment;
    private ATMSecondVersion atmSecondVersion;

    @BeforeEach
    public void setUp() {
        atmSecondVersion = new ATMSecondVersion(20);
        atmDepartment = new ATMDepartment(atmSecondVersion);
    }

    @Test
    public void testBalance() {
        assertEquals(177200, atmDepartment.balance());
    }

    @Test
    public void testCollect(){
        HashMap<Bills, Integer> collect = atmDepartment.collect(atmDepartment.getATMList().get(0));
        assertEquals(0 , atmDepartment.balance());
    }

    @Test
    public void testCollectAll(){
        HashMap<Bills, Integer> collect = atmDepartment.collectAll();
        assertEquals(0 , atmDepartment.balance());
    }

    @Test
    public void testRestoreAll(){
        HashMap<Bills, Integer> collect = atmDepartment.collectAll();
        atmDepartment.restoreAll();
        assertEquals(177200 , atmDepartment.balance());
    }

    @Test
    public void testAddDelete(){
        atmDepartment.addATM(new ATMSecondVersion(20));
        assertEquals(177200*2 , atmDepartment.balance());
        atmDepartment.deleteATM(atmDepartment.getATMList().get(0));
        assertEquals(177200 , atmDepartment.balance());
    }

    @Test
    public void testDepositWithdraw(){
        atmSecondVersion.deposit(Bills.FIVETHOUSAND, 10);
        assertEquals(177200 + 50_000, atmDepartment.balance());
        atmSecondVersion.withdraw(Bills.ONETHOUSAND, 15);
        assertEquals(177200 + 50_000 - 15_000, atmDepartment.balance());
    }

    @Test
    public void testATMDispenseChain(){
        atmDepartment.addATM(new ATMSecondVersion(20));
        ATMDispenseChain atmDispenseChain = new ATMDispenseChain(atmDepartment.getATMList());
        atmDispenseChain.dispense(Bills.FIVEHUNDRED , 30);
        assertEquals(177200 * 2 - 15_000, atmDepartment.balance());
    }

}
