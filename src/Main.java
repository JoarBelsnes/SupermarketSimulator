public class Main {

    public static void main(String[] args) {
        Thread martThread = new Thread(() -> {
            Supermarket supermarket = new Supermarket(50, 10, 2);
            supermarket.call();
        });
        System.out.println("hi");
        martThread.start();
    }




}