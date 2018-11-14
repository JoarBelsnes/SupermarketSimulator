import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CashierTest {

    @Test
    void addCustomerToQueue() {
        Cashier testCashier = new Cashier();
        Customer testCustomer = new Customer("Test", 10);
        testCashier.addCustomerToQueue(testCustomer);

        assertAll("customer",
                () -> assertEquals(testCashier.getCustomers().get(0).getName(), testCustomer.getName()),
                () -> assertEquals(testCashier.getCustomers().get(0).getItems(), testCustomer.getItems())

        );

    }

    @Test
    void checkOutNext() {

    }

}