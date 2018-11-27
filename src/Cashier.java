import java.util.ArrayList;

public class Cashier {
    //the checkout line is an arraylist of customer IDs
    //that can be identified by the supermarket
    private ArrayList<Integer> customers = new ArrayList<>(0);

    public Cashier(){
        customers = new ArrayList<>(1);
    }

    public ArrayList<Integer> getCustomers(){
        return customers;
    }

    public int getLineLength(){
        return customers.size();
    }

    public void addCustomerToQueue(int c){
        customers.add(c);
    }

    /***
     * "checks out" the first person in line,
     * removing them from the list.
     * returns an integer equal to the time it takes
     * for the customer to be finished.
     */
    //probably going to remove this as it doesn't work anymore and probably isn't needed
    /*
    public int checkOutNext(){
        //for now, takes one time-step per item to check out
        int time = customers.get(0).getItems();

        customers.remove(0);

        return time;
    }
    */
}
