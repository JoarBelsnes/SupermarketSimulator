import java.lang.reflect.Array;
import java.util.ArrayList;

public class Cashier {
    private ArrayList<Customer> customers;
    private String name;


    public Cashier(){
        customers = new ArrayList<>(1);
        name = "Cashier 1";
    }

    public ArrayList<Customer> getCustomers(){
        return customers;
    }


    void setName(String name1){
        this.name = name1;
    }

    public String getName(){
        return name;
    }


    //cashier has an arrayList of customers
    public void addCustomerToQueue(Customer c){
        customers.add(c);
    }

    //cashier has an arrayList of customer
    public void addCustomerId(Customer c){
        customers.add(c);
    }


    //keep track of customers using ID's
    public int getCustomerId(Customer c){
        return c.getId();
    }

    //shows how long the cashier's line is
    public int getLineLength(){
        return customers.size();
    }


    /***
     * "checks out" the first person in line,
     * removing them from the list.
     * returns an integer equal to the time it takes
     * for the customer to be finished.
     */
    public int checkOutNext(){
        //for now, takes one time-step per item to check out
        int time = customers.get(0).getItems();

        customers.remove(0);

        return time;
    }
}
