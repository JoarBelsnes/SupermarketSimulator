import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
public class Customer {
    private String name;
    private int items;
    private long arrival; //time in milliseconds

    Customer(){

        this.name = "Customer1";
        this.items = 0;
        this.arrival = arrival;



    }
    Customer(String name, int items){
        this.name = name;
        this.items = items;
    }

    /******* Getters and Setters  *******/
    /* SETTERS */
    void setName(String name){
        this.name = name;
    }

    void setItems(int items){
        this.items = items;
    }

    void setArrival(){
        arrival = System.currentTimeMillis();
        this.arrival= TimeUnit.MILLISECONDS.toSeconds(arrival);
    }

    /* GETTERS */
    String getName(){
        return this.name;
    }

    int getItems(){
        return this.items;
    }

    long getArrival(){
        return arrival;
    }

    long getElapsedTime(){
        return ((TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()))-arrival);
    }




}