import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Supermarket {
    private int maxSimulationTime;
    private int numCustomers;
    private int numCashiers;

    private PriorityQueue<Event> eventQueue;
    private ArrayList<Customer> customers;
    private ArrayList<Cashier> cashiers;

    //make currentTime public to all classes
    public int currentTime = 0;
    private Random rand = new Random();

    //constructor
    public Supermarket(int maxSimulationTime, int numCustomers, int numCashiers) {
        this.maxSimulationTime = maxSimulationTime;
        this.numCustomers = numCustomers;
        this.numCashiers = numCashiers;

        customers = generateCustomers(numCustomers);
        cashiers = new ArrayList<Cashier>(numCashiers);

        //event queue is priority sorted by lowest time
        eventQueue = new PriorityQueue<>((e1, e2) -> {
            if (e1.getStartTime() > e2.getStartTime()) {
                return 1;
            }
            if (e1.getStartTime() < e2.getStartTime()) {
                return -1;
            }
            return 0;
        });

        //add customer arrivals to event queue
        //customer arrival time is true, therefore is ready for another event
        for (Customer customer : customers) {
            eventQueue.add(new Event(customer, type.CUSTOMER_ARRIVES, rand.nextInt(maxSimulationTime + 1)));
            customer.setArrived();
        }




    }

    /***
     * generates an arraylist of customers of a specified size, with random size shopping lists
     * @param numCustomers number of customers to enter the store
     * @return arraylist of all customers
     */
    private ArrayList<Customer> generateCustomers(int numCustomers) {

        Random rand = new Random();
        ArrayList<Customer> customers = new ArrayList<>(numCustomers);
        for (int i = 0; i < numCustomers; i++) {
            //items range from 1-50
            customers.add(new Customer(Integer.toString(i), rand.nextInt(51)));
        }
        return customers;
    }

    public void run() {
        currentTime = 0;
        Event currentEvent;
        boolean addComplete = false;
        boolean readyForCheckoutComplete = false;

        while (currentTime < maxSimulationTime) {
            try {
                //handles event here
                while (eventQueue.size() > 0 && eventQueue.peek().getStartTime() == currentTime) {
                    //poll returns the event while removing it from the queue, use that to handle it
                    currentEvent = eventQueue.poll();
                    handleEvent(currentEvent);
                }

                TimeUnit.SECONDS.sleep(1);
                currentTime++;
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Current System Time: " + currentTime);
        }
    }

    private void handleEvent(Event event){
        switch (event.getEventType()) {
            case CUSTOMER_ARRIVES:
                System.out.println("A customer arrived");
                break;
            case CUSTOMER_READY_CHECKOUT:
                System.out.println("A customer is ready for checkout");
                break;
            case CUSTOMER_FINISH_CHECKOUT:
                System.out.println("A customer finished checkout");
                break;
            case CUSTOMER_CHANGE_LINE:
                System.out.println("A customer wants to change lines");
                break;
            case CUSTOMER_ABANDON:
                System.out.println("A customer abandoned the store");
                break;
        }
    }
    //need this to determine event times
    public int getCurrentTime(){
        return currentTime;
    }

}
