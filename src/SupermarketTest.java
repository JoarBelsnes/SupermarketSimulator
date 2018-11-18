import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SupermarketTest {
    @Test
    void run() {

    }

    @Test
    void getEventType(){
        Customer testCustomer = new Customer();

        //this is important because if the tests below work, then the setters are working
        Event event = new Event(testCustomer,type.CUSTOMER_ARRIVES,10);

        //check to see if CUSTOMER_READY_CHECKOUT setter is working
        event.setEventType(type.CUSTOMER_READY_CHECKOUT);
        assertEquals(event.getEventType(),type.CUSTOMER_READY_CHECKOUT);

        //check to see if CUSTOMER_ABANDON setter is working
        event.setEventType(type.CUSTOMER_ABANDON);
        assertEquals(event.getEventType(),type.CUSTOMER_ABANDON);

        //check to see if CUSTOMER_ARRIVES setter is working
        event.setEventType(type.CUSTOMER_ARRIVES);
        assertEquals(event.getEventType(),type.CUSTOMER_ARRIVES);

        //check to see if CUSTOMER_CHANGE_LINE setter is working
        event.setEventType(type.CUSTOMER_CHANGE_LINE);
        assertEquals(event.getEventType(),type.CUSTOMER_CHANGE_LINE);

        //check to see if CUSTOMER_FINISH_CHECKOUT is working
        event.setEventType(type.CUSTOMER_FINISH_CHECKOUT);
        assertEquals(event.getEventType(),type.CUSTOMER_FINISH_CHECKOUT);


    }

    @Test
    void Supermarket(){
        Customer customer = new Customer(20,15);
        Customer customer1 = new Customer(21,12);
        Cashier cashier = new Cashier();
        cashier.addCustomerToQueue(customer);
        cashier.addCustomerToQueue(customer1);

        assertEquals(cashier.getCustomers().get(0),customer);
        assertEquals(cashier.getCustomers().get(1),customer1);



    }


}