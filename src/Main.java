import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        int maxTime = 1000;
        System.out.println("Hello World!");

        //create customers here
        Customer customer1 = new Customer("Jae",7);


        /************ CREATING TIME ***************/
        //testing to see if setArrival actually works
        int currentTime = 0;

        while (currentTime < maxTime) {
            try {
                //handles event here

                TimeUnit.SECONDS.sleep(1);
                currentTime++;
            } catch (InterruptedException e) {


            }
            System.out.println("Current System Time: "+currentTime);
        }




    }
}