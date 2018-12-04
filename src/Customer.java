import java.util.Random;

/**
 * class representing a customer of the supermarket
 * has a (ideally unique) ID, and number of items
 * keeps track of its chosen cashier and whether it will change lines
 * based on patience factor and time in line
 * contributors: Jae, Liam
 */
public class Customer {
    private int id;
    private int items;
    private int chosenCashier;
    private boolean willChange; //default to false
    private int patienceFactor; //between 0-10, lower the patience the higher chance to leave/change

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

    /******* Getters and Setters  *******/
    /* SETTERS */
    void setId(int id) {
        this.id = id;
    }

    void setItems(int items) {
        this.items = items;
    }

    void setWillChange(boolean willChange1) {
        this.willChange = willChange1;
    }

    void setPatienceFactor(int patienceFactor1) {
        this.patienceFactor = patienceFactor1;
    }

    public void setChosenCashier(int chosenCashier) {
        this.chosenCashier = chosenCashier;
    }

    /* GETTERS */
    int getId() {
        return id;
    }

    int getItems() {
        return items;
    }

    /**
     * calculates shopping time based on the number of items to buy
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

    public int getChosenCashier() {
        return chosenCashier;
    }


    //use this to determine patience level
    //decrease patience when called

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

    //only returns the patience factor
    int getPatienceFactor(){
        return patienceFactor;
    }


}
