import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SupermarketTest {

    @Test
    void step() {
        Supermarket supermarketTest = new Supermarket(0,1,1);
        assertEquals(supermarketTest.step(), true);
        assertEquals(supermarketTest.getCurrentTime(), 1);
    }

    @Test
    void addEventToQueue() {
        Supermarket supermarketTest = new Supermarket(0,0,0);
        assertEquals(supermarketTest.getEventQueue().poll(), null);
        supermarketTest.addEventToQueue(new Event(0,type.CUSTOMER_ARRIVES,0));
        assertEquals(supermarketTest.getEventQueue().poll().getEventType(), type.CUSTOMER_ARRIVES);
    }

    @Test
    void handleEvent_arrival() {
        Supermarket supermarketTest = new Supermarket(0, 1, 1);
        supermarketTest.step();
        assertEquals(supermarketTest.getEventQueue().poll().getEventType(), type.CUSTOMER_READY_CHECKOUT);
    }

    @Test
    void handleEvent_readyCheckout() {
        Supermarket supermarketTest = new Supermarket(0, 1, 1);
        supermarketTest.addEventToQueue(new Event(0, type.CUSTOMER_READY_CHECKOUT, 0));
        supermarketTest.step();
        assertEquals(supermarketTest.getEventQueue().poll().getEventType(), type.CUSTOMER_FINISH_CHECKOUT);
    }

    @Test
    void handleEvent_finishCheckout() {
        Supermarket supermarketTest = new Supermarket(0, 0, 0);
        supermarketTest.addEventToQueue(new Event(0, type.CUSTOMER_FINISH_CHECKOUT, 0));
        assertEquals(supermarketTest.step(), false);
    }

    @Test
    void handleEvent_changeLine() {
        Supermarket supermarketTest = new Supermarket(0, 1, 2);
        supermarketTest.addEventToQueue(new Event(0, type.CUSTOMER_READY_CHECKOUT, 1));
        supermarketTest.step();
        supermarketTest.addEventToQueue((new Event(0, type.CUSTOMER_CHANGE_LINE, 2)));
        supermarketTest.step();
        supermarketTest.step();
        assertEquals(supermarketTest.getEventQueue().poll().getEventType(), type.CUSTOMER_FINISH_CHECKOUT);
    }

    @Test
    void handleEvent_abandon() {
        Supermarket supermarketTest = new Supermarket(0, 0, 0);
        supermarketTest.addEventToQueue(new Event(0, type.CUSTOMER_ABANDON, 0));
        assertEquals(supermarketTest.step(), false);
    }

}