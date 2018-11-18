import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CashierTest {

    @Test
    void addCustomerToQueue() {
        Cashier testCashier = new Cashier();
        Customer testCustomer = new Customer(0, 10);
        testCashier.addCustomerToQueue(testCustomer);

        assertAll("customer",
                () -> assertEquals(testCashier.getCustomers().get(0).getId(), testCustomer.getId()),
                () -> assertEquals(testCashier.getCustomers().get(0).getItems(), testCustomer.getItems())

        );

    }

    @Test
    void checkOutNext() {
        Cashier testCashier = new Cashier();
        Customer c1 = new Customer(0,1);
        Customer c2 = new Customer(1,2);

        testCashier.addCustomerToQueue(c1);
        testCashier.addCustomerToQueue(c2);

        assertAll("checkoutTimes",
                () -> assertEquals(testCashier.checkOutNext(),c1.getItems()),
                () -> assertEquals(testCashier.checkOutNext(),c2.getItems())
        );

    }

}