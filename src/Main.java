import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.Date;
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Customer customer1 = new Customer("Jae",7);


        /************ CREATING TIME ***************/
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");


        //testing to see if setArrival actually works
        try {
            TimeUnit.SECONDS.sleep(5);
        }catch(InterruptedException e){

        }
        customer1.setArrival();
        System.out.println("Customer1 Arrived at: "+customer1.getArrival());



    }
}