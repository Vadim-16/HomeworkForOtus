import ca.study.purpose.ATM;
import ca.study.purpose.BillsCalculations.BigBills;
import ca.study.purpose.BillsCalculations.Thousands;
import ca.study.purpose.BillsCalculations.TwoThousands;
import ca.study.purpose.RubCells.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ATMTester {

    private ATM atm;

    @BeforeEach
    public void setUp() {
        atm = new ATM(20);
    }

    @Test
    @DisplayName("Check Balance")
    public void testBalance() {
        assertEquals(177200, atm.balance());
    }

    @Test
    @DisplayName("Withdraw Thousands")
    public void testWithdrawThousands() {
        atm.withdraw(30_000, new Thousands());
        assertEquals(147200, atm.balance(), "Thousands withdraw should work");
    }

    @Test
    @DisplayName("Withdraw TwoThousands")
    public void testWithdrawTwoThousands() {
        atm.withdraw(30_000, new TwoThousands());
        assertEquals(147200, atm.balance(), "TwoThousands withdraw should work");
    }

    @Test
    @DisplayName("Withdraw BigBills")
    public void testWithdrawBigBills() {
        atm.withdraw(30_000, new BigBills());
        assertEquals(147200, atm.balance(), "BigBills withdraw should work");
    }

    @Test
    @DisplayName("Deposit Rub10")
    public void testDepositRub10(){
        atm.deposit(new Rub10(2));
        assertEquals(177220, atm.balance(), "+20rub change");
    }

    @Test
    @DisplayName("Deposit Rub50")
    public void testDepositRub50(){
        atm.deposit(new Rub50(3));
        assertEquals(177350, atm.balance(), "+150rub change");
    }

    @Test
    @DisplayName("Deposit Rub100")
    public void testDepositRub100(){
        atm.deposit(new Rub100(5));
        assertEquals(177700, atm.balance(), "+500rub change");
    }

    @Test
    @DisplayName("Deposit Rub500")
    public void testDepositRub500(){
        atm.deposit(new Rub500(5));
        assertEquals(179700, atm.balance(), "+2500rub change");
    }

    @Test
    @DisplayName("Deposit Rub1000")
    public void testDepositRub1000(){
        atm.deposit(new Rub1000(5));
        assertEquals(182200, atm.balance(), "+5000rub change");
    }

    @Test
    @DisplayName("Deposit Rub2000")
    public void testDepositRub2000(){
        atm.deposit(new Rub2000(5));
        assertEquals(187200, atm.balance(), "+10_000rub change");
    }

    @Test
    @DisplayName("Deposit Rub5000")
    public void testDepositRub5000(){
        atm.deposit(new Rub5000(5));
        assertEquals(202200, atm.balance(), "+25_000rub change");
    }

}
