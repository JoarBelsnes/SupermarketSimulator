
public class Customer {
    private int id;
    private String name;
    private int items;

    //this will be useful to determine if the customer completed an arrival
    boolean arrived;
    boolean readyCheckout;


    Customer() {
        name = "Customer1";
        id = 1;
        items = 0;
        arrived = false;
        readyCheckout = false;
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

    void setId(int id1){
        this.id = id1;
    }

    //event setters
    void setArrived(){
        arrived = true;
    }

    void setReadyCheckout(){
        readyCheckout = true;
    }


    /* GETTERS */
    String getName() {
        return name;
    }

    int getItems() {
        return items;
    }

    int getId(){
        return id;
    }

    //event getters
    boolean arrived(){
        return arrived;
    }

    boolean isReadyCheckout(){
        return readyCheckout;
    }


}