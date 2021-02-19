package strategy;

import java.util.Scanner;

public class ReadConsole implements Read{

    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter line: ");
        String line = scanner.nextLine();
        System.out.println("You entered the line: " + line);
        return line;
    }
}
