import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
public class Main {

    public static void main(String[] args) {

        Supermarket supermarket = new Supermarket(20,10,2);
        supermarket.run();
    }
}