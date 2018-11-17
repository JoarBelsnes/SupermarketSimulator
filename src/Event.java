enum type {
    CUSTOMER_ARRIVES, CUSTOMER_READY_CHECKOUT, CUSTOMER_FINISH_CHECKOUT,
    CUSTOMER_CHANGE_LINE, CUSTOMER_ABANDON
}

public class Event {
    private type eventType;
    private int startTime;
    private Customer customer;

    public Event(Customer customer, type eventType, int startTime) {
        this.customer = customer;
        this.eventType = eventType;
        this.startTime = startTime;
    }

    //setters
    void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    //getters
    int getStartTime() {
        return startTime;
    }

    Customer getCustomer() {
        return customer;
    }

    type getEventType() {
        return eventType;
    }


}
