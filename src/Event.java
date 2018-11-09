enum type{
    CUSTOMER_ARRIVES, CUSTOMER_READY_CHECKOUT, CUSTOMER_FINISH_CHECKOUT,
    CUSTOMER_CHANGE_LINE, CUSTOMER_ABANDON
}


public class Event {
    private type eventType;
    private int arrivalTime;




    //setters
    void setArrival(int arrivalTime1){
        this.arrivalTime = arrivalTime1;
    }

    //getters
    int getArrival(){
        return arrivalTime;
    }

}
