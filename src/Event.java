enum type {
    CUSTOMER_ARRIVES, CUSTOMER_READY_CHECKOUT, CUSTOMER_FINISH_CHECKOUT,
    CUSTOMER_CHANGE_LINE, CUSTOMER_ABANDON
}

public class Event {
    private type eventType;
    private int startTime;
    private int customerID;

    public Event(int customerID, type eventType, int startTime) {
        this.customerID = customerID;
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

    int getCustomerID() {
        return customerID;
    }

    type getEventType() {
        return eventType;
    }

}
