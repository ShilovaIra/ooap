package strategy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Transposition implements Algorithm {

    String coder;

    // получение символов из строки ключей
    private static int[] getSymbols(String line) {
        String line1 = line.replaceAll("[\\s]{2,}", " ");
        String[] s = line1.split(" ");
        try {
            int[] array = new int[s.length];
            for (int i = 0; i < s.length; i++) {
                array[i] = Integer.parseInt(s[i]);
            }
            return array;
        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода");
            return null;
        }
    }
    // максимальное значение
    private static boolean maxValue(int[] array) {
        int max = -1;
        for (int i = 0; i < array.length; i++) {
            if (!(array[i] < 100 && array[i] > 0))
                return false;
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max)
                max = array[i];
        }
        if (array.length != max) {
            return false;
        } else {
            return true;
        }
    }
    // проверка на повторяющиеся значения в ключе
    private static boolean repeat(int[] array) {
        Integer[] arrayInt = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            arrayInt[i] = new Integer(array[i]);
        }
        Set<Integer> unique = new HashSet<Integer>(Arrays.asList(arrayInt));
        if (unique.size() == array.length) {
            return true;
        } else {
            return false;
        }
    }
    // перевод строки в последовательность символов
    private static String[] getSymbolsString(String line) {
        String[] array = new String[line.length()];
        for (int i = 0; i < line.length(); i++) {
            array[i] = Character.toString(line.charAt(i));
        }
        return array;
    }

    //шифрование
    private static String coder(String s, int[] cipher, int[] cipherStlb, int str, int slb) {
        String[][] coder = new String[str][slb];
        int j = 0;
        int k = slb;
        for (int i = 0; i < cipher.length; i++) {
            coder[cipher[i] - 1] = getSymbolsString(s.substring(j, k));
            j += slb;
            k += slb;
        }
        for (int i = 0; i < str; i++) {
            for (int l = 0; l < slb; l++) {
                System.out.print(coder[i][l] + " ");
            }
            System.out.println();
        }
        String result = "";
        for (int i = 0; i < cipherStlb.length; i++) {
            for (int l = 0; l < str; l++) {
                result += coder[l][cipherStlb[i] - 1];
            }
        }
        return result;
    }
    // расшифрование
    private static String decoder(String s, int[] cipher, int[] cipherStlb, int str, int slb) {
        String[][] decoder = new String[str][slb];
        int j = 0;
        for (int i = 0; i < cipherStlb.length; i++) {
            for (int l = 0; l < str; l++) {
                decoder[l][cipherStlb[i] - 1] = Character.toString(s.charAt(j));
                j++;
            }
        }
        for (int i = 0; i < str; i++) {
            for (int l = 0; l < slb; l++) {
                System.out.print(decoder[i][l] + " ");
            }
            System.out.println();
        }
        String result = "";
        for (int i = 0; i < cipher.length; i++) {
            for (int k = 0; k < slb; k++) {
                result += decoder[cipher[i] - 1][k];
            }

        }
        return result;
    }

    @Override
    public void coder(String line) throws Exception {

        System.out.println("Transposition algorithm");

        Scanner scanner = new Scanner(System.in);

        // вычисление размерности ключа строк и столбцов
        int sizeSlb = (int) Math.sqrt(line.length());
        int sizeStr = (int) Math.ceil((double) line.length() / sizeSlb);

        // добавление пробелов в конец строки, если размерность ключа не является делителем длины строки
        if ((double) line.length() % sizeSlb != 0) {
            while ((double) line.length() % sizeSlb != 0) {
                line += " ";
            }
        }

        // ввод ключей
        System.out.println("Ключ размерности " + sizeStr + ": ");
        String w = scanner.nextLine();
        int[] symbols = getSymbols(w);
        System.out.println("Ключ размерности " + sizeSlb + ": ");
        String w1 = scanner.nextLine();
        int[] cipher = getSymbols(w1);
        try {
            if (symbols.length == sizeStr && maxValue(symbols) && repeat(symbols) && cipher.length == sizeSlb && maxValue(cipher) && repeat(cipher)) {
                // результат шифрования
                coder = coder(line, symbols, cipher, sizeStr, sizeSlb);
                System.out.println("Закодированная строка: " + coder);
            } else {
                System.out.println("Ошибка");
            }
        } catch (NullPointerException e) {
            System.out.println("Ошибка ввода");
        }

        System.out.println("Расшифрование:");

        System.out.println("Введите шифр строк " + sizeStr + ": ");
        String str = scanner.nextLine();
        int[] symbols11 = getSymbols(str);
        System.out.println("Введите шифр столбцов " + sizeSlb + ": ");
        String str1 = scanner.nextLine();
        int[] symbols1 = getSymbols(str1);
        if (symbols11.length == sizeStr && symbols1.length == sizeSlb && maxValue(symbols) && maxValue(symbols1) && repeat(symbols) && repeat(symbols1)) {
            System.out.println("Расшифрованная строка: " + decoder(coder, getSymbols(str), getSymbols(str1), sizeStr, sizeSlb));
        } else {
            System.out.println("Вы ввели неверный шифр.");
        }
    }
}
