package strategy;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class RSA implements Algorithm{

    public void coder(String line) throws Exception{
        System.out.println("RSA algorithm");
        System.out.println("Data:" + line);
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        KeyPair keyPair = generator.generateKeyPair();
        // Encrypt with PRIVATE KEY
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        byte[] data = cipher.doFinal(line.getBytes());
        System.out.println(DatatypeConverter.printHexBinary(data));
        // Decrypt with PUBLIC KEY
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        byte[] result = cipher.doFinal(data);
        System.out.println("Decrypt:" + new String(result));
    }
}
