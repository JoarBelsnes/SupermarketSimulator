public class Main {

    public static void main(String[] args) {
        Thread martThread = new Thread(() -> {
            Supermarket supermarket = new Supermarket(50, 30, 2);
            //supermarket.call();
        });
        martThread.start();
    }




}