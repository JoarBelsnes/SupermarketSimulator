import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CashierTest {

    @Test
    void addCustomerToQueue() {
        Cashier testCashier = new Cashier();
        Customer testCustomer = new Customer(0, 10);
        testCashier.addCustomerToQueue(testCustomer.getId());

        assertEquals((int)testCashier.getCustomers().get(0), testCustomer.getId());

    }

    @Test
    void removeCustomerFromQueue() {
        Cashier testCashier = new Cashier();
        Customer testCustomer = new Customer(0, 10);
        testCashier.addCustomerToQueue(testCustomer.getId());
        testCashier.removeCustomerFromQueue(testCustomer.getId());

        assertEquals(testCashier.getLineLength(), 0);
    }

}