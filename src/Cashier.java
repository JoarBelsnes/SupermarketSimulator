import java.util.ArrayList;

public class Cashier {
    private ArrayList<Customer> customers;

    public Cashier(){
        customers = new ArrayList<>(1);
    }

    public ArrayList<Customer> getCustomers(){
        return customers;
    }

    public int getLineLength(){
        return customers.size();
    }

    public void addCustomerToQueue(Customer c){
        customers.add(c);
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
