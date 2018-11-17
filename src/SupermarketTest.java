import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupermarketTest {
    @Test
    void run() {

    }

    //want to make sure that when this is called that setArrived is always true.
    @Test
    void setArrived(){
        boolean test1 = false;
        boolean test2 = true;

        Customer testCustomer = new Customer();
        assertEquals(testCustomer.arrived(),test1);


        testCustomer.setArrived();
        assertEquals(testCustomer.arrived(),test2);
    }

}