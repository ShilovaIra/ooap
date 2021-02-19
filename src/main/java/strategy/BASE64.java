package strategy;

import java.util.Base64;

public class BASE64 implements Algorithm{

    public void coder(String line) throws Exception {
        System.out.println("Base64 algorithm");
        System.out.println("Data:" + line);
        Base64.Encoder enc = java.util.Base64.getEncoder();
        byte[] encbytes = enc.encode(line.getBytes());
        System.out.println("Encrypt:");
        for (int i = 0; i < encbytes.length; i++)
        {
            System.out.printf("%c", (char) encbytes[i]);
            if (i != 0 && i % 4 == 0)
                System.out.print(' ');
        }
        System.out.println();
        Base64.Decoder dec = java.util.Base64.getDecoder();
        byte[] decbytes = dec.decode(encbytes);
        System.out.println("Decrypt:" + new String(decbytes));
    }
}
