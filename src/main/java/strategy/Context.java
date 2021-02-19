package strategy;

import ghost.CryptGost;

import java.util.Scanner;

public class Context {

    private Algorithm algorithmStategy;
    private Read readStrategy;
    private static String line;

    public Context() {
    }

    public Context(Algorithm algorithmStategy, Read readStrategy) {
        this.algorithmStategy = algorithmStategy;
        this.readStrategy = readStrategy;
    }

    public Algorithm getAlgorithmStategy() {
        return algorithmStategy;
    }

    public void setAlgorithmStategy(Algorithm algorithmStategy) {
        this.algorithmStategy = algorithmStategy;
    }

    public Read getReadStrategy() {
        return readStrategy;
    }

    public void setReadStrategy(Read readStrategy) {
        this.readStrategy = readStrategy;
    }

    public void cripto(String line) throws Exception {
        this.algorithmStategy.coder(line);
    }

    public String read() throws Exception {
        return this.readStrategy.read();
    }

    public static void main(String[] args) throws Exception {

        Context context = new Context();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select operation : (1) - read data from console  (2) - read data from file ");
        String answer = scanner.nextLine();
        if (answer.equals("1")) {
            context.setReadStrategy(new ReadConsole());
            line = context.read();
        } else if (answer.equals("2")) {
            context.setReadStrategy(new ReadFile());
            line = context.read();
        } else {
            System.out.println("You have selected the wrong operation");
        }
        System.out.println("Select operation : (1) - RSA  (2) - ELGamalAlgorithm (3) - DigitalSignature  " +
                "(4) - BASE64   (5) - Transposition    (6) - Gost 2012");
        String answer1 = scanner.nextLine();
        if (answer1.equals("1")) {
            context.setAlgorithmStategy(new RSA());
            context.cripto(line);
        } else if (answer1.equals("2")) {
            context.setAlgorithmStategy(new ELGamalAlgorithm());
            context.cripto(line);
        } else if (answer1.equals("3")) {
            context.setAlgorithmStategy(new DigitalSignature());
            context.cripto(line);
        } else if (answer1.equals("4")) {
            context.setAlgorithmStategy(new BASE64());
            context.cripto(line);
        } else if (answer1.equals("5")) {
            context.setAlgorithmStategy(new Transposition());
            context.cripto(line);
        } else if (answer1.equals("6")) {
            context.setAlgorithmStategy(new CryptGost());
            context.cripto(line);
        } else {
            System.out.println("You have selected the wrong operation");
        }

        System.out.println("  ");
        System.out.println("Do you want to change the encryption algorithm?  y/n");
        String answer2 = scanner.nextLine();
        if (answer2.equals("y")) {
            System.out.println("Select algorithm : (1) - RSA  (2) - ELGamalAlgorithm (3) - DigitalSignature  " +
                    "(4) - BASE64   (5) - Transposition   (6) - Gost 2012");
            String answer11 = scanner.nextLine();
            if (answer11.equals("1")) {
                context.setAlgorithmStategy(new RSA());
                context.cripto(line);
            } else if (answer11.equals("2")) {
                context.setAlgorithmStategy(new ELGamalAlgorithm());
                context.cripto(line);
            } else if (answer11.equals("3")) {
                context.setAlgorithmStategy(new DigitalSignature());
                context.cripto(line);
            } else if (answer11.equals("4")) {
                context.setAlgorithmStategy(new BASE64());
                context.cripto(line);
            } else if (answer11.equals("5")) {
                context.setAlgorithmStategy(new Transposition());
                context.cripto(line);
            }else if (answer1.equals("6")) {
                context.setAlgorithmStategy(new CryptGost());
                context.cripto(line);
            } else {
                System.out.println("You have selected the wrong operation");
            }
        } else if (answer2.equals("n")) {
            System.exit(0);
        } else {
            System.out.println("You have selected the wrong operation");
        }
    }
}
