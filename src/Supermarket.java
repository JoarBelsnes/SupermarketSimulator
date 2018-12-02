import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.PriorityQueue;
import java.util.Random;

public class Supermarket {
    private int maxSimulationTime;
    private int numCustomers;
    private int numCashiers;
    private int[] arrivalTimes;
    private double meanTime;
    private PriorityQueue<Event> eventQueue;
    private ArrayList<Customer> customers;
    private ArrayList<Cashier> cashiers;

    public int getMaxSimulationTime() {
        return maxSimulationTime;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    private int currentTime = 0;
    private Random rand = new Random();

    //constructor
    public Supermarket(int maxSimulationTime, int numCustomers, int numCashiers) {
        this.maxSimulationTime = maxSimulationTime;
        this.numCustomers = numCustomers;
        this.numCashiers = numCashiers;
        this.meanTime = maxSimulationTime / 4;
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
            //change random number to number generated with formula
            eventQueue.add(new Event(i, type.CUSTOMER_ARRIVES, arrivalTimes[i]));
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
     * @return arraylist of all customers
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
     *
     * @param c customer to calculate time for
     * @return int time
     */
    private int checkoutTime(Customer c) {
        //update this with a better formula
        return (c.getItems() / 3) + 5;
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

    public void step() {
        Event currentEvent;
        try {
            //handles event here
            while (eventQueue.size() > 0 && eventQueue.peek().getStartTime() == currentTime) {
                //poll returns the event while removing it from the queue, use that to handle it
                currentEvent = eventQueue.poll();
                if (currentEvent != null) {
                    handleEvent(currentEvent);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Current System Time: " + currentTime + "\n");
        currentTime++;
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
                //set customer's cashier choice
                customers.get(event.getCustomerID()).setChosenCashier(chosenCashier);
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
                addFinishCheckout(event.getCustomerID(), chosenCashier);

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
                eventQueue.remove(event);
                //remove customer from their checkout line
                cashiers.get(customers.get(event.getCustomerID()).getChosenCashier()).removeCustomerFromQueue(event.getCustomerID());
                System.out.println("Customer " + event.getCustomerID() + " was removed from event queue!\n");
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
                    //remove from current line
                    cashiers.get(customers.get(event.getCustomerID())
                            .getChosenCashier()).removeCustomerFromQueue(event.getCustomerID());
                    //add to new line
                    cashiers.get(chosenCashier2).addCustomerToQueue(event.getCustomerID());
                    //remove current event
                    eventQueue.remove(event);
                    System.out.println("Customer " + event.getCustomerID() + " changed lines!\n");

                    //remove current FINISH_CHECKOUT and add a new one
                    removeFinishCheckout(event.getCustomerID());
                    addFinishCheckout(event.getCustomerID(), chosenCashier2);

                } else {
                    System.out.println("Customer " + event.getCustomerID() + " is in the shortest line!\n");

                }


                break;


            //when a customer abandons the store, simply remove them from the simulation
            //and REMOVE their existing finish_checkout event
            case CUSTOMER_ABANDON:
                System.out.println("Customer " + event.getCustomerID() + " abandoned the store");

                //remove from checkout line
                cashiers.get(customers.get(event.getCustomerID()).getChosenCashier()).removeCustomerFromQueue(event.getCustomerID());
                eventQueue.remove(event);

                //remove the FINISH_CHECKOUT event
                removeFinishCheckout(event.getCustomerID());

                System.out.println("Customer " + event.getCustomerID() + " abandoned the store!");
                break;
        }
    }

    private void addFinishCheckout(int customerID, int cashierID) {
        eventQueue.add(new Event(customerID, type.CUSTOMER_FINISH_CHECKOUT,
                checkoutLineTime(cashiers.get(cashierID)) + currentTime));
    }

    /**
     * removes the FINISH_CHECKOUT event for the specified customer
     * used when changing lines or abandoning
     *
     * @param id of the customer leaving the line
     */
    private void removeFinishCheckout(int id) {
        try {
            for (Event e : eventQueue) {
                if (e.getCustomerID() == id && e.getEventType() == type.CUSTOMER_FINISH_CHECKOUT) {
                    eventQueue.remove(e);
                }
            }
        } catch (ConcurrentModificationException e) {
            //e.printStackTrace();
        }
    }

    public void addEventToQueue(Event e) {
        eventQueue.add(e);
    }

}
