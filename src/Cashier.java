import java.util.ArrayList;

public class Cashier {
    private ArrayList<Customer> customers;

    public Cashier(){
        customers = new ArrayList<>(1);
    }

    public ArrayList<Customer> getCustomers(){
        return customers;
    }

    public void addCustomerToQueue(Customer c){
        customers.add(c);
    }

    /***
     * "checks out" the first person in line,
     * removing them from the list.
     */
    public void checkOutNext(){
        //how long does it take to check out?
        //when/how to queue next checkout?

        customers.remove(0);
    }
}
