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
    private int currentTime = 0;
    private Random rand = new Random();

    //constructor
    public Supermarket(int maxSimulationTime, int numCustomers, int numCashiers) {
        this.maxSimulationTime = maxSimulationTime;
        this.numCustomers = numCustomers;
        this.numCashiers = numCashiers;
        customers = generateCustomers(numCustomers);
        cashiers = new ArrayList<Cashier>(numCashiers);

        //populate cashiers list
        for (int i = 0; i < numCashiers; i++) {
            cashiers.add(new Cashier());
        }

        //create event queue, priority sorted by lowest time
        eventQueue = new PriorityQueue<>((e1, e2) -> {
            if (e1.getStartTime() > e2.getStartTime()) {
                return 1;
            }
            if (e1.getStartTime() < e2.getStartTime()) {
                return -1;
            }
            return 0;
        });

        //add all customer arrivals to event queue
        for (Customer customer : customers) {
            //change random number to number generated with formula
            eventQueue.add(new Event(customer.getId(), type.CUSTOMER_ARRIVES, rand.nextInt(maxSimulationTime + 1)));
        }

    }

    public ArrayList<Cashier> getCashiers() {
        return cashiers;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public PriorityQueue<Event> getEventQueue() {
        return eventQueue;
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
            //should there be a formula for items?
            customers.add(new Customer(i, rand.nextInt(51)));
        }
        return customers;
    }

    /**
     * calculates the time it takes for a customer to check out
     *
     * @param c customer to calculate time for
     * @return int time
     */
    private int checkoutTime(Customer c) {
        //update this with a better formula
        return c.getItems() / 2;
    }

    /**
     * calculates the time it takes for every customer in line at a cashier to check out
     *
     * @param c cashier to calculate time for
     * @return int time
     */
    private int checkoutLineTime(Cashier c) {
        int totalTime = 0;
        for (int id : c.getCustomers()) {
            totalTime += checkoutTime(customers.get(id));
        }
        return totalTime;
    }

    public void run() {
        currentTime = 0;
        Event currentEvent;

        while (currentTime < maxSimulationTime) {
            try {
                //handles event here
                while (eventQueue.size() > 0 && eventQueue.peek().getStartTime() == currentTime) {
                    //poll returns the event while removing it from the queue, use that to handle it
                    currentEvent = eventQueue.poll();
                    if (currentEvent != null) {
                        handleEvent(currentEvent);
                    }
                }

                TimeUnit.SECONDS.sleep(1);
                currentTime++;
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Current System Time: " + currentTime + "\n");
        }
    }

    /***
     * handles events, based on the event type
     * @param event the event to handle
     */
    private void handleEvent(Event event) {
        switch (event.getEventType()) {

            //when a customer arrives in the store, decide when they'll be ready for checkout
            case CUSTOMER_ARRIVES:
                System.out.println("Customer " + event.getCustomerID() + " arrived\n");
                //calculate how long it takes to shop and enqueue the ready_checkout event
                eventQueue.add(new Event(event.getCustomerID(),
                        type.CUSTOMER_READY_CHECKOUT,
                        currentTime + customers.get(event.getCustomerID()).getShoppingTime()));
                break;

            //when a customer is ready for checkout, decide what line to go in and decide when they will be done
            case CUSTOMER_READY_CHECKOUT:
                System.out.println("Customer " + event.getCustomerID() + " is ready for checkout\n");
                //decide what line to go in: choose the one with the shortest line

                int minLineLength = cashiers.get(0).getLineLength();
                int chosenCashier = 0;
                for (Cashier c : cashiers) {
                    if (c.getLineLength() < minLineLength) {
                        minLineLength = c.getLineLength();
                        chosenCashier = cashiers.indexOf(c);
                    }
                }
                //add customer to chosen cashier's line
                cashiers.get(chosenCashier).addCustomerToQueue(event.getCustomerID());

                //how to calculate when customer will finish checkout?
                //what if they change lines?

                //Algorithm here for customer abandon store/line

                /**
                 * If customer changes line, reset customer checkoutTime to formula
                 */
                //System.out.println("Customer "+event.getCustomerID()+" patience factor before: "+customers.get(0).getPatienceFactor());

                if ((customers.get(0).getPatienceFactor() <= 5) && (customers.get(0).getPatienceFactor() >= 1)) {
                    eventQueue.add(new Event(event.getCustomerID(), type.CUSTOMER_CHANGE_LINE, currentTime + 5));
                } else if (customers.get(0).getPatienceFactor() > 5) {
                    customers.get(0).setPatienceFactor(customers.get(0).decreasePatienceFactor());
                    //System.out.println("Customer " + event.getCustomerID() + " patience factor after: " + customers.get(0).getPatienceFactor() + "\n");
                } else {
                    System.out.println("Customer " + event.getCustomerID() + " is very impatient.\n");
                }


                //add finish checkout event after everyone else in line is done
                eventQueue.add(new Event(event.getCustomerID(), type.CUSTOMER_FINISH_CHECKOUT,
                        checkoutTime(customers.get(event.getCustomerID())) + currentTime));

                //print queue of current cashier to test
                System.out.print("Queue of cashier " + chosenCashier + ": ");
                for (int i : cashiers.get(chosenCashier).getCustomers()) {
                    System.out.print(i + ", ");
                }
                System.out.println("");

                break;

            //when a customer finishes checkout, simply remove them from the simulation
            case CUSTOMER_FINISH_CHECKOUT:
                System.out.println("Customer " + event.getCustomerID() + " finished checkout");


                int testRemove = event.getCustomerID();
                eventQueue.remove(event);


                System.out.println("Customer " + testRemove + " was removed from event queue!\n");
                break;

            //when a customer changes lines, queue ready for checkout again
            //and REMOVE their existing finish_checkout event for the current cashier
            case CUSTOMER_CHANGE_LINE:
                System.out.println("Customer " + event.getCustomerID() + " wants to change lines");
                //remove customer from line, and add to cashier's line with shortest line using
                //algorithm that we already have for shortest line.


                /**
                 * Change line conditional goes here
                 */
                boolean changed = false;
                //take the shortest line and add current event to that line
                int minLineLength2 = cashiers.get(0).getLineLength();
                int chosenCashier2 = 0;

                for (Cashier c : cashiers) {
                    if (c.getLineLength() < minLineLength2) {
                        minLineLength2 = c.getLineLength();
                        chosenCashier2 = cashiers.indexOf(c);
                        changed = true;

                    }


                }
                if (changed) {
                    cashiers.get(chosenCashier2).addCustomerToQueue(event.getCustomerID());
                    eventQueue.remove(event); //remove current event (and customer) from current cashier
                    System.out.println("Customer " + event.getCustomerID() + " changed lines!\n");
                    //add customer to chosen cashier's line and remove from current cashier

                } else {
                    System.out.println("Customer " + event.getCustomerID() + " is in the shortest line!\n");

                }


                break;


            //when a customer abandons the store, simply remove them from the simulation
            //and REMOVE their existing finish_checkout event
            case CUSTOMER_ABANDON:
                System.out.println("Customer " + event.getCustomerID() + " abandoned the store");

                int testRemove2 = event.getCustomerID();
                eventQueue.remove(event);

                System.out.println("Customer " + testRemove2 + " abandoned the store!");
                break;
        }
    }

    public void addEventToQueue(Event e) {
        eventQueue.add(e);
    }

}
