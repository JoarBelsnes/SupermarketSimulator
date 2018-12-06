import java.util.Random;

/**
 * class representing a customer of the supermarket
 * has a (ideally unique) ID, and number of items
 * keeps track of its chosen cashier and whether it will change lines
 * based on patience factor and time in line
 * contributors: Jae, Liam, Joshua
 */
public class Customer {
    private int id;
    private int items;
    private int chosenCashier;
    private boolean willChange; //default to false
    private boolean willAbandon; //default to false
    private int patienceFactor; //between 0-10, lower the patience the higher chance to leave/change
    private int numChanges = 0;
    private boolean hasArrived = false;
    private boolean hasDeparted = false;
    private boolean hasQueued = false;
    private boolean rageQuit = false;
    private int timeArrived = 0;
    private int timeQueued = 0;
    private int timeDeparted = 0;

    Customer() {
        id = 0;
        items = 0;
        willChange = false;

        Random rand = new Random();
        patienceFactor = rand.nextInt(10) + 1;
    }

    Customer(int id, int items) {
        this.id = id;
        this.items = items;

        Random rand = new Random();
        this.patienceFactor = rand.nextInt(15) + 1;
    }

    //-------- GETTERS AND SETTERS ------------//

    public int getId() {
        return this.id;
    }

    void setId(int id) {
        this.id = id;
    }

    public int getItems() {
        return this.items;
    }

    void setItems(int items) {
        this.items = items;
    }

    /**
     * calculates shopping time based on the number of items to buy
     *
     * @return time spent shopping
     * contributors: Liam
     */
    int getShoppingTime() {
        return (items / 2) + 1;
    }

    /**
     * @return whether the customer will change lines
     * contributors: Jae
     */
    boolean getWillChange() {
        return willChange;
    }

    void setWillChange(boolean willChange1) {
        this.willChange = willChange1;
    }

    public int getChosenCashier() {
        return chosenCashier;
    }

    public void setChosenCashier(int chosenCashier) {
        this.chosenCashier = chosenCashier;
    }

    //only returns the patience factor
    int getPatienceFactor() {
        return patienceFactor;
    }

    void setPatienceFactor(int patienceFactor1) {
        this.patienceFactor = patienceFactor1;
    }

    //returns true if customer has arrived to store
    public boolean hasArrived() {
        return hasArrived;
    }

    public void setHasArrived(int i) {
        this.hasArrived = true;
        this.timeArrived = i;
    }

    public int getArrivedTime() {
        return this.timeArrived;
    }

    //returns true if customer has finished shopping and has queued for checkout
    public boolean hasQueued() {
        return hasQueued;
    }

    public void setHasQueued(int i) {
        this.hasQueued = true;
        this.timeQueued = i;
    }

    public int getQueuedTime() {
        return this.timeQueued;
    }

    //returns true if customer has departed from the store
    public boolean hasDeparted() {
        return hasDeparted;
    }

    public void setHasDeparted(int i) {
        this.hasDeparted = true;
        this.timeDeparted = i;
    }

    public int getDepartedTime() {
        return this.timeDeparted;
    }


    public boolean hasRageQuit() {
        return this.rageQuit;
    }

    public void rageQuit() {
        this.rageQuit = true;
    }

    public boolean willAbandon() {
        return willAbandon;
    }

    public void setWillAbandon(boolean willAbandon) {
        this.willAbandon = willAbandon;
    }

    public int getNumChanges() {
        return numChanges;
    }

    public void setNumChanges(int numChanges) {
        this.numChanges = numChanges;
    }

    // ----------------- END GETTERS AND SETTERS ------------ //

    /**
     * decreases patience level
     * @return new patience level
     * contributors: Jae
     */
    int decreasePatienceFactor() {
        if (patienceFactor >= 2) {
            patienceFactor--;
        }
        return patienceFactor;
    }

}