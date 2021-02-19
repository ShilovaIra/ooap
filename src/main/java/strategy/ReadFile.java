package strategy;

import java.io.*;
import java.util.Scanner;

public class ReadFile implements Read {

    String line;

    @Override
    public String read() throws Exception {
        File file = new File("inputStrategy.txt");
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        return reader.readLine();
    }
}
