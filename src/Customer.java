import java.util.Random;


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

    //returns time it takes to shop based on shopping list size and other factors
    int getShoppingTime() {
        return (items / 2) + 1;
    }

    //
    boolean getWillChange() {
        return willChange;
    }

    public int getChosenCashier() {
        return chosenCashier;
    }


    //use this to determine patience level
    //decrease patience when called
    int decreasePatienceFactor() {
        if (patienceFactor >= 2) {
            patienceFactor--;
            return patienceFactor;
        }
        else {
            return patienceFactor;
        }
    }

    //returns only the patience factor
    int getPatienceFactor(){
        return patienceFactor;
    }


}
