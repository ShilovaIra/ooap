package strategy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ELGamalAlgorithm  implements Algorithm{

    // метод для шифрования строки
    public static String encryption (long p, long g, long y, String code, long k) {

        // первая часть зашифрованного кода - параметр а
        long aCode = (long) (Math.pow((double) g, (double) k));
        aCode %= p;
        Long pow = (long) Math.pow((double) y, (double) k);
        // вторая часть зашифрованного кода - параметр b
        long bCode = (pow %p) * Integer.parseInt(code);

        // зашифрованный код - пара чисел a и b
        String fullCode = String.valueOf(aCode) + ',' + bCode;
        return fullCode;
    }

    // метод для дешифрования
    public static String decryption (long p,long x, String code) {
        // выделяем части зашифрованного кода, разделенные запятой
        String[] fullCode = code.split(",");
        // получаем из частей кода исходное сообщение
        long message = ((long) Integer.parseInt(fullCode[1]) / ((long) Math.pow(Integer.parseInt(fullCode[0]), (double) x) % p));
        return Long.toString(message);
    }

    // поиск параметра g - первообразное по модулю р
    public static long findG (long p){
        for (long i = 0; i < p; i++)
            if (IsPRoot(p, i))
                return i;
        return 0;
    }

    public static boolean IsPRoot(long p, long root) {
        if (root == 0 || root == 1)
            return false;
        long current = 1;
        Set<Long> set = new HashSet<>();
        for (long i = 1; i < p; i++) {
            current = (current * root) % p;
            if (set.contains(current))
                return false;
            set.add(current);
        }
        return true;
    }

    //генерируем простое произвольное число ч меньшее р
    public static long generateX (long p) {
        long x = 2;
        while (x < p) {
            if (nod(p,x) == 1)
                return x;
            x++;
        }
        return 0;
    }

    // поиск НОД для определения взаимной простоты чисел
    static long nod (long x, long y) {
        long tmp = x % y;
        x = y;
        y = tmp;
        if (tmp > 0){
            return nod (x,y);
        }
        else {
            return x;
        }
    }

    // получаем буквы из их численных кодов
    public static String[] getLettersFromCodes (String[] lettersCode) {
        String[] line = new String[lettersCode.length];
        for (int i = 0; i < lettersCode.length; i++) {
            if (Integer.parseInt(lettersCode[i]) == 0) {
                line[i] = " ";
            } else {
                int code = Integer.parseInt(lettersCode[i]) - 1 + 'A';
                char[] symb = Character.toChars(code);
                for (int j = 0; j < symb.length; j++) {
                    line[i] = Character.toString(symb[j]);
                }
            }
        }
        return line;
    }

    // получаем численные коды букв
    public static Integer[] getLettersCodeFromLine (String[] encryptionString) {
        Integer[] lettersCode = new Integer[Arrays.asList(encryptionString).size()];
        for (int i = 0; i <encryptionString.length; i++) {
            char currentCharacter = encryptionString[i].charAt(0);
            if (Character.isDigit(currentCharacter)) {
                System.out.println("Wrong enter. Line may contain only UpperCase letter !");
                System.exit(0);
            } else if (currentCharacter == ' ') {
                lettersCode[i] = 0;
            } else if (Character.isUpperCase(currentCharacter)) {
                lettersCode[i] = currentCharacter - 'A' + 1;
                continue;
            }
            else {
                System.out.println("Incorrect symbol in line");
                System.exit(0);
            }
        }
        return lettersCode;
    }

    @Override
    public void coder(String line) throws Exception {
        System.out.println("ELGamal algorithm");
        Scanner scanner = new Scanner(System.in);
        String[] encryptionString = line.split(""); // строка, которую необходимо зашифровать
        Integer[] lettersCode = getLettersCodeFromLine(encryptionString); // получаем коды букв строки
        System.out.println(Arrays.asList(lettersCode));
        long max = -1;
        for (int i = 0; i <lettersCode.length ; i++) {
            if (lettersCode[i]>max)
                max = lettersCode[i]; // максимальный код букв
        }
        System.out.println("Enter p parameter large than " + max + ":");
        long p = (int) scanner.nextLong();
        if (p<=max) { // параметр р для шифрования
            System.out.println("Incorrect parameters!");
            System.exit(0);
        }
        long g_param = findG(p);
        if (g_param == 0) { // параметр g для шифрования
            System.out.println("Impossible find G");
            System.exit(0);
        }
        long x = generateX(p);
        if (x == 0) { // параметр х для шифрования
            System.out.println("Impossible find X");
            System.exit(0);
        }
        if (x > p || g_param > p || x < 0 || g_param < 0) { // проверка параметров на корректность
            System.out.println("Some parameters are incorrect!");
            System.exit(0);
        }
        long y = ((long)Math.pow((double) g_param, (double) x)) % p;
        System.out.println("Open key: (y: " + y + ", g: " + g_param + ", p: " + p + ")"); // данные открытого ключа
        System.out.println("Secret key X: " + x); // закрытый ключ
        String[] newEncryptionString = new String[lettersCode.length];
        long k = (long) (1 + Math.random()*(p-2));
        for (int i = 0; i < lettersCode.length ; i++)
            newEncryptionString[i] = encryption(p, g_param, y, Integer.toString(lettersCode[i]), k); // получение зашифрованной строки
        System.out.println("Encryption string: ");
        for (int i = 0; i < newEncryptionString.length; i++) {
            System.out.print(newEncryptionString[i] + " " );
        }

        //String[] decryptionString = newEncryptionString;// строка для дешифрования
        String[] decryptionRes = new String[newEncryptionString.length];
        for (int i = 0; i < newEncryptionString.length ; i++) {
            try {
                decryptionRes[i] = decryption(p, x, newEncryptionString[i]);
            } catch (ArithmeticException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Error with decode!");
                System.exit(0);
            }
        }
        String[] l = getLettersFromCodes(decryptionRes);
        System.out.println(" ");
        System.out.println("Decryption line:");
        for (int i = 0; i < l.length; i++) {
            System.out.print(l[i]);
        }

    }
}
