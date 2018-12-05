public class Main {

    public static void main(String[] args) {
        Thread martThread = new Thread(() -> {
            Supermarket supermarket = new Supermarket(50, 30, 2, 10);
            int start = 0;
            int end = 100;
            while (start < end) {
                supermarket.step();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("FIST!");
                }
            }

        });
        martThread.start();

    }
}