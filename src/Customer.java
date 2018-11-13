
public class Customer {
    private String name;
    private int items;

    Customer() {
        name = "Customer1";
        items = 0;
    }

    Customer(String name, int items) {
        this.name = name;
        this.items = items;
    }

    /******* Getters and Setters  *******/
    /* SETTERS */
    void setName(String name1) {
        this.name = name1;
    }

    void setItems(int items1) {
        this.items = items1;
    }


    /* GETTERS */
    String getName() {
        return name;
    }

    int getItems() {
        return items;
    }


}