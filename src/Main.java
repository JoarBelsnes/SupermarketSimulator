import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        int maxTime = 1000;
        int numCustomers = 20;
        Random rand = new Random();

        //create customers here
        ArrayList<Customer> customers = generateCustomers(numCustomers);

        //create priority queue for events, ordered by start time
        PriorityQueue<Event> eventQueue = new PriorityQueue<>((e1, e2) -> {
            if (e1.getStartTime() > e2.getStartTime()) {
                return 1;
            }
            if (e1.getStartTime() < e2.getStartTime()) {
                return -1;
            }
            return 0;
        });

        //add customer arrivals to event queue
        for (Customer customer : customers) {
            eventQueue.add(new Event(customer, type.CUSTOMER_ARRIVES, rand.nextInt(101)));
        }

        /*
        //poll the entire queue for testing purposes; remove this when actually using it
        for(int i = 0; i < numCustomers; i++){
            System.out.println("Customer arriving at " + eventQueue.poll().getStartTime());
        }
        */

        /************ CREATING TIME ***************/
        //testing to see if setArrival actually works
        int currentTime = 0;

        while (currentTime < maxTime) {
            try {
                //handles event here

                //while firstEventInQueue is at currentTime
                //  handle firstEventInQueue
                //  remove firstEventInQueue

                TimeUnit.SECONDS.sleep(1);
                currentTime++;
            } catch (InterruptedException e) {


            }
            System.out.println("Current System Time: " + currentTime);
        }
    }

    //generates an arraylist of customers of a specified size, with random numbers of items
    private static ArrayList<Customer> generateCustomers(int numCustomers) {
        Random rand = new Random();
        ArrayList<Customer> customers = new ArrayList<>(numCustomers);
        for (int i = 0; i < numCustomers; i++) {
            customers.add(new Customer(Integer.toString(i), rand.nextInt(51)));
        }
        return customers;
    }
}