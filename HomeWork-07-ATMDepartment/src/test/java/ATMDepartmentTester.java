import ca.study.purpose.ATMDepartment;
import ca.study.purpose.Chain_Of_Responsibility_Pattern.ATMDispenseChain;
import ca.study.purpose.ATMSecondVersion;
import ca.study.purpose.Bills;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ATMDepartmentTester {
    private ATMDepartment atmDepartment;
    private ATMSecondVersion atmSecondVersion1;
    private ATMSecondVersion atmSecondVersion2;
    private ATMSecondVersion atmSecondVersion3;


    @BeforeEach
    public void setUp() {
        atmSecondVersion1 = new ATMSecondVersion(10);
        atmSecondVersion2 = new ATMSecondVersion(20);
        atmSecondVersion3 = new ATMSecondVersion(15);
        atmDepartment = new ATMDepartment(atmSecondVersion1, atmSecondVersion2, atmSecondVersion3);
    }

    @Test
    public void testBalance() {
        assertEquals(8_860 * 45, atmDepartment.balance());
    }

    @Test
    public void testCollect(){
        Map<Bills, Integer> collect = atmDepartment.collect(0);
        assertEquals(8_860 * 35 , atmDepartment.balance());
    }

    @Test
    public void testCollectAll(){
        Map<Bills, Integer> collect = atmDepartment.collectAll();
        assertEquals(0 , atmDepartment.balance());
    }

    @Test
    public void testRestoreAll(){
        Map<Bills, Integer> collect = atmDepartment.collectAll();
        atmDepartment.restoreAll();
        assertEquals(8_860 * 45 , atmDepartment.balance());
    }

    @Test
    public void testAddDelete(){
        atmDepartment.addATM(new ATMSecondVersion(20));
        assertEquals(8_860 * 65 , atmDepartment.balance());
        atmDepartment.deleteATM(atmDepartment.getATMList().get(0));
        assertEquals(8_860 * 55 , atmDepartment.balance());
    }

    @Test
    public void testDepositWithdraw(){
        atmSecondVersion1.deposit(Bills.FIVE_THOUSAND, 10);
        assertEquals(8_860 * 45 + 50_000, atmDepartment.balance());
        atmSecondVersion1.withdraw(Bills.ONE_THOUSAND, 9);
        assertEquals(8_860 * 45 + 50_000 - 9_000, atmDepartment.balance());
    }

    @Test
    public void testATMDispenseChain(){
        ATMDispenseChain atmDispenseChain = new ATMDispenseChain(atmDepartment.getATMList());
        atmDispenseChain.dispense(Bills.FIVE_HUNDRED, 30);
        assertEquals(8860 * 45 - 15_000, atmDepartment.balance());
    }

}
