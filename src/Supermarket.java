import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * class representing a supermarket, controls the entire simulation
 * contains all customers, cashiers, and events
 * has a priority queue of events based on start time,
 * and handles events according to type
 * contributors: Liam, Jae
 */
public class Supermarket {
    private int arrivalWindow;
    private int numCustomers;
    private int[] arrivalTimes;
    private double meanTime;
    private PriorityQueue<Event> eventQueue;
    private ArrayList<Customer> customers;
    private ArrayList<Cashier> cashiers;

    public int getArrivalWindow() {
        return arrivalWindow;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    private int currentTime = 0;

    //constructor
    public Supermarket(int arrivalWindow, int numCustomers, int numCashiers, int meanTime) {
        this.arrivalWindow = arrivalWindow;
        this.numCustomers = numCustomers;
        this.meanTime = meanTime;
        arrivalTimes = new int[numCustomers];
        customers = generateCustomers();
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
        for (int i = 0; i < numCustomers; i++) {
            eventQueue.add(new Event(i, type.CUSTOMER_ARRIVES, arrivalTimes[i]));
        }

    }

    // getters
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
     * @return arraylist of all customers
     * contributors: Liam
     */
    private ArrayList<Customer> generateCustomers() {
        Random rand = new Random();

        //generate arrival times exponentially
        double u, x;
        for (int i = 0; i < arrivalTimes.length; i++) {
            u = rand.nextDouble();
            x = -meanTime * Math.log(1.0 - u); //natural log
            arrivalTimes[i] = (int) (x);
        }

        for (int i : arrivalTimes) {
            System.out.print(i + ", ");
        }

        ArrayList<Customer> customers = new ArrayList<>(numCustomers);
        for (int i = 0; i < numCustomers; i++) {
            //items range from 1-20
            customers.add(new Customer(i, rand.nextInt(20) + 1));
        }
        return customers;
    }

    /**
     * calculates the time it takes for a customer to check out
     * @param c customer to calculate time for
     * @return int time
     * contributors: Liam
     */
    private int checkoutTime(Customer c) {
        //update this with a better formula
        return (c.getItems() / 4) + 1;
    }

    /**
     * calculates the time it takes for every customer in line at a cashier to check out
     * @param c cashier to calculate time for
     * @return int time
     * contributors: Liam
     */
    private int checkoutLineTime(Cashier c) {
        int totalTime = 0;
        for (int id : c.getCustomers()) {
            totalTime += checkoutTime(customers.get(id));
        }
        return totalTime;
    }

    /**
     * steps forward one unit of time in the simulation
     * if events are found at the new time, handle all of them
     * @return true if there are any events at the new time
     * contributors: Liam
     */
    public boolean step() {
        Event currentEvent;
        boolean eventFound = false;
        try {
            //handles event here
            while (eventQueue.size() > 0 && eventQueue.peek().getStartTime() == currentTime) {
                //poll returns the event while removing it from the queue, use that to handle it
                currentEvent = eventQueue.poll();
                if (currentEvent != null) {
                    handleEvent(currentEvent);
                    eventFound = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Current System Time: " + currentTime + "\n");
        currentTime++;
        return eventFound;
    }

    /***
     * handles events, based on the event type
     * @param event the event to handle
     * contributors: Liam, Jae
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
                //flag customer as having arrived
                customers.get(event.getCustomerID()).setHasArrived(currentTime);
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
                //set customer's cashier choice
                customers.get(event.getCustomerID()).setChosenCashier(chosenCashier);

                //If customer changes line, reset customer checkoutTime to formula
                if ((customers.get(0).getPatienceFactor() <= 5) && (customers.get(0).getPatienceFactor() >= 1)) {
                    eventQueue.add(new Event(event.getCustomerID(), type.CUSTOMER_CHANGE_LINE, currentTime + 5));
                } else if (customers.get(0).getPatienceFactor() > 5) {
                    customers.get(0).setPatienceFactor(customers.get(0).decreasePatienceFactor());
                    //System.out.println("Customer " + event.getCustomerID() + " patience factor after: " + customers.get(0).getPatienceFactor() + "\n");
                } else {
                    System.out.println("Customer " + event.getCustomerID() + " is very impatient.\n");
                }

                //add finish checkout event after everyone else in line is done
                addFinishCheckout(event.getCustomerID(), chosenCashier);

                //print queue of current cashier to test
                System.out.print("Queue of cashier " + chosenCashier + ": ");
                for (int i : cashiers.get(chosenCashier).getCustomers()) {
                    System.out.print(i + ", ");
                }
                System.out.println("");
                customers.get(event.getCustomerID()).setHasQueued(currentTime);
                break;

            //when a customer finishes checkout, simply remove them from the simulation
            case CUSTOMER_FINISH_CHECKOUT:
                System.out.println("Customer " + event.getCustomerID() + " finished checkout");
                //remove customer from their checkout line
                cashiers.get(customers.get(event.getCustomerID()).getChosenCashier()).removeCustomerFromQueue(event.getCustomerID());
                System.out.println("Customer " + event.getCustomerID() + " was removed from event queue!\n");
                //flag customer as having departed
                customers.get(event.getCustomerID()).setHasDeparted(currentTime);
                break;

            //when a customer changes lines, queue ready for checkout again
            //and REMOVE their existing finish_checkout event for the current cashier
            case CUSTOMER_CHANGE_LINE:
                System.out.println("Customer " + event.getCustomerID() + " wants to change lines");
                //remove customer from line, and add to cashier's line with shortest line using
                //algorithm that we already have for shortest line.

                //flag to determine whether customer actually changes lines when they want to
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
                    //remove from current line
                    cashiers.get(customers.get(event.getCustomerID())
                            .getChosenCashier()).removeCustomerFromQueue(event.getCustomerID());
                    //change chosen cashier
                    customers.get(event.getCustomerID()).setChosenCashier(chosenCashier2);
                    //add to new line
                    cashiers.get(chosenCashier2).addCustomerToQueue(event.getCustomerID());
                    System.out.println("Customer " + event.getCustomerID() + " changed lines!\n");
                    //remove current FINISH_CHECKOUT and add a new one
                    removeFinishCheckout(event.getCustomerID());

                    /**
                     * Here we want to make sure we can check the customers number of line changes
                     */
                    //increase numChanges by 1
                    customers.get(event.getCustomerID()).
                            setNumChanges(customers.get(event.getCustomerID()).getNumChanges()+1);

                    //if customer has made enough line changes...
                    if(customers.get(event.getCustomerID()).getNumChanges() >= 2){
                        //customer abandons
                        eventQueue.add(new Event(event.getCustomerID(), type.CUSTOMER_ABANDON, currentTime+2));
                    }
                    else{
                        //otherwise, add them to the chosen line
                        addFinishCheckout(event.getCustomerID(), chosenCashier2);
                    }
                } else {
                    System.out.println("Customer " + event.getCustomerID() + " is in the shortest line!\n");
                }
                break;

            //when a customer abandons the store, simply remove them from the simulation
            //and REMOVE their existing finish_checkout event
            case CUSTOMER_ABANDON:
                System.out.println("Customer " + event.getCustomerID() + " abandoned the store");
                //remove from checkout line
                cashiers.get(customers.get(event.getCustomerID()).getChosenCashier()).
                        removeCustomerFromQueue(event.getCustomerID());
                //remove the FINISH_CHECKOUT event
                removeFinishCheckout(event.getCustomerID());
                System.out.println("Customer " + event.getCustomerID() + " abandoned the store!");
                //flag customer as having departed
                customers.get(event.getCustomerID()).setHasDeparted(currentTime);
                break;
        }
    }

    /**
     * adds a new CUSTOMER_FINISH_CHECKOUT event to the queue
     * used after changing lines, for the new line
     * @param customerID customer who changed lines
     * @param cashierID cashier that customer changed to
     * contributors: Liam
     */
    private void addFinishCheckout(int customerID, int cashierID) {
        eventQueue.add(new Event(customerID, type.CUSTOMER_FINISH_CHECKOUT,
                checkoutLineTime(cashiers.get(cashierID)) + currentTime));
    }

    /**
     * removes the FINISH_CHECKOUT event for the specified customer
     * used when changing lines or abandoning
     * @param id of the customer leaving the line
     * contributors: Liam
     */
    private void removeFinishCheckout(int id) {
        try {
            for (Event e : eventQueue) {
                if (e.getCustomerID() == id && e.getEventType() == type.CUSTOMER_FINISH_CHECKOUT) {
                    eventQueue.remove(e);
                    break;
                }
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }

    /**
     * adds an event to the queue
     * @param e event to add
     */
    public void addEventToQueue(Event e) {
        eventQueue.add(e);
    }

    public void results(){
        for(Customer i : customers){
            System.out.println("Customer ID: " + " Customer Entered at: " +
                    i.getArrivedTime() + " Customer Queued at: " + i.getQueuedTime() +
                    " Customer Entered at: " + i.getDepartedTime());
        }
    }

    /**
     * calculates the number of customers in the store, not in any checkout lines
     * @return number of customers shopping
     */
    public int getNumberOfShoppingCustomers(){
        int returnValue = 0;
        for (Customer i : customers){
            if(i.hasArrived() && !i.hasQueued() && !i.hasDeparted()){
                returnValue++;
            }
        }
        return returnValue;
    }
}
