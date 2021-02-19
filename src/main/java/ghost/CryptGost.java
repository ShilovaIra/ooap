package ghost;

import ru.zinal.gosthash.GostHash;
import strategy.Algorithm;

import java.math.BigInteger;
import java.security.spec.ECPoint;
import java.util.Scanner;

public class CryptGost implements Algorithm {

    private void encrypt(BigInteger d, String message) {

        //параметры для создания подписи
        // d - ключ подписи
       // BigInteger d = new BigInteger("55441196065363246126355624130324183196576709222340016572108097750006097525544",10);
        // hash код сообщения
        byte[] hash = GostHash.hash(message);
        byte[] err_hash = GostHash.hash("helllo");

        String signature = Gost_R34_10_2012.Sign(hash, d);

        //верификация подписи
        // точка Q - ключ проверки подписи
        BigInteger xQ = new BigInteger("57520216126176808443631405023338071176630104906313632182896741342206604859403",10);
        BigInteger yQ = new BigInteger("17614944419213781543809391949654080031942662045363639260709847859438286763994",10);
        // Q = dP
        ECPoint Q = ECArithmetics.scalmult(Gost_R34_10_2012.getEllipticPointP(), d);
        boolean result = Gost_R34_10_2012.SignCheck(err_hash,signature,Q);

        System.out.println("Signature: " + signature);
        System.out.println("Is correct: " + result);
    }

    @Override
    public void coder(String line) throws Exception {
        System.out.println("Enter biginteger key: ");
        Scanner scanner = new Scanner(System.in);
        BigInteger d = scanner.nextBigInteger();
        this.encrypt(d, line);
    }
}