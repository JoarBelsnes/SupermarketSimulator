public class Customer {
    private int id;
    private int items;

    Customer() {
        id = 0;
        items = 0;
    }

    Customer(int id, int items) {
        this.id = id;
        this.items = items;
    }

    /******* Getters and Setters  *******/
    /* SETTERS */
    void setId(int id) {
        this.id = id;
    }

    void setItems(int items) {
        this.items = items;
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
        return items / 2;
    }


}