import java.util.ArrayList;

public class Cashier {
    //the checkout line is an arraylist of customer IDs
    //that can be identified by the supermarket
    private ArrayList<Integer> customers = new ArrayList<>(0);

    public Cashier() {
        customers = new ArrayList<>(1);
    }

    public ArrayList<Integer> getCustomers() {
        return customers;
    }

    public int getLineLength() {
        return customers.size();
    }

    public void addCustomerToQueue(int c) {
        customers.add(c);
    }

    /**
     * removes the customer with the specified ID from the queue
     * used for a customer changing lines or abandoning the store
     */
    public void removeCustomerFromQueue(int id) {
        try {
            customers.remove(customers.indexOf(id));
        } catch (IndexOutOfBoundsException e) {

        }
    }
}
