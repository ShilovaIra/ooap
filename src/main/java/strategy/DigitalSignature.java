package strategy;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Signature;

public class DigitalSignature implements Algorithm{

    public void coder(String line) throws Exception {
        System.out.println("DigitalSignature algorithm");
        System.out.println(line);
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstanceStrong();
        generator.initialize(2048, random);
        KeyPair keyPair = generator.generateKeyPair();
        // Digital Signature
        Signature dsa = Signature.getInstance("SHA256withRSA");
        dsa.initSign(keyPair.getPrivate());
        // Update and sign the data
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        byte[] data = cipher.doFinal(line.getBytes());
        dsa.update(data);
        byte[] signature = dsa.sign();
        // Verify signature
        dsa.initVerify(keyPair.getPublic());
        dsa.update(data);
        boolean verifies = dsa.verify(signature);
        System.out.println("Signature is ok: " + verifies);
        // Decrypt if signature is correct
        if (verifies) {
            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            byte[] result = cipher.doFinal(data);
            System.out.println("Decrypt:" + new String(result));
        }
    }
}
