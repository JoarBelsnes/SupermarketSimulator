import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupermarketTest {
    @Test
    void handleEvent_arrival(){
        Supermarket supermarketTest = new Supermarket(1,0,0);
        supermarketTest.addEventToQueue(new Event(0,type.CUSTOMER_ARRIVES,0));
        //supermarketTest.run();
        assertEquals(supermarketTest.getEventQueue().poll().getEventType(),type.CUSTOMER_READY_CHECKOUT);
    }

    @Test
    void handleEvent_readyCheckout(){
        Supermarket supermarketTest = new Supermarket(1,0,0);
        supermarketTest.addEventToQueue(new Event(0,type.CUSTOMER_READY_CHECKOUT,0));
        //supermarketTest.run();
        assertEquals(supermarketTest.getEventQueue().poll().getEventType(),type.CUSTOMER_FINISH_CHECKOUT);
    }

    @Test
    void handleEvent_finishCheckout(){

    }

    @Test
    void handleEvent_changeLine(){

    }

    @Test
    void handleEvent_abandon(){

    }

}