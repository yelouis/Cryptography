import java.math.BigInteger;
import java.util.BitSet;
import java.util.Random;

public class RSA {
    private BigInteger primeP;
    private BigInteger primeQ;
    private BigInteger publicPrivateN;
    private BigInteger publicE;
    private BigInteger privateD;
    private BigInteger phiN;
    private int bitLength;

    public RSA(int bitLength) {
        this.bitLength = bitLength;
        this.publicPrivateN = BigInteger.ZERO;
        while (publicPrivateN.bitLength() < bitLength){
            this.primeP = BigInteger.probablePrime(bitLength/2 + 1, new Random());
            this.primeQ = BigInteger.probablePrime(bitLength/2 + 1, new Random());
            this.publicPrivateN = primeP.multiply(primeQ);
        }
        this.phiN = publicPrivateN.subtract(primeP).subtract(primeQ).add(BigInteger.ONE);

        this.publicE = BigInteger.probablePrime(phiN.bitLength(), new Random());
        while(publicE.compareTo(phiN) >= 0){
            this.publicE = BigInteger.probablePrime(phiN.bitLength(), new Random());
        }
        this.privateD = publicE.modInverse(phiN);
    }

    public RSA(String publicPrivateNS, String publicES, String privateDS, int bitLengthS){
        this.publicPrivateN = new BigInteger(publicPrivateNS);
        this.publicE = new BigInteger(publicES);
        this.privateD = new BigInteger(privateDS);
        this.bitLength = bitLengthS;

    }

    public BigInteger getPublicPrivateN() {
        return publicPrivateN;
    }

    public BigInteger getPublicE() {
        return publicE;
    }

    public int getBitLength() { return bitLength; }

    public BigInteger getPrivateD() { return privateD; }

    public String encryptMessage(String messageS, String senderNS, String senderES){
        BigInteger senderN = new BigInteger(senderNS);
        BigInteger senderE = new BigInteger(senderES);
        long [] message = BitSet.valueOf(messageS.getBytes()).toLongArray();
        String messageTotal = new String();
        for(long messageParts: message){
            messageTotal += "," + BigInteger.valueOf(messageParts).modPow(senderE, senderN);
        }

        return messageTotal;
    }

    public String decryptingMessage(String messageM){
        String [] messageParts = messageM.split(",");
        String messageList [] = new String[messageParts.length];
        String messageTotal = new String();
        int counter = 0;
        for(String parts: messageParts) {
            BigInteger part = new BigInteger(parts);
            messageList[counter] = new String(BigInteger.valueOf(part.modPow(privateD, publicPrivateN).
                    longValue()).toByteArray());
            String reverse = new StringBuilder(messageList[counter]).reverse().toString();
            messageTotal += reverse;
            counter++;
        }
        return messageTotal;
    }

    public String decryptingPublicMessage(String messageM, String senderNS, String senderES){
        BigInteger senderN = new BigInteger(senderNS);
        BigInteger senderE = new BigInteger(senderES);
        String [] messageParts = messageM.split(",");
        String messageList [] = new String[messageParts.length];
        String messageTotal = new String();
        int counter = 0;
        for(String parts: messageParts) {
            BigInteger part = new BigInteger(parts);
            messageList[counter] = new String(BigInteger.valueOf(part.modPow(senderE, senderN).
                    longValue()).toByteArray());
            String reverse = new StringBuilder(messageList[counter]).reverse().toString();
            messageTotal += reverse;
            counter++;
        }
        //System.out.println(messageTotal);
        return messageTotal;
    }

    public String secretMessage(String messageM, BigInteger keyD, String senderNS){
        BigInteger senderN = new BigInteger(senderNS);
        String [] messageParts = messageM.split(",");
        String messageList [] = new String[messageParts.length];
        String messageTotal = new String();
        int counter = 0;
        for(String parts: messageParts) {
            BigInteger part = new BigInteger(parts);
            messageList[counter] = new String(BigInteger.valueOf(part.modPow(keyD, senderN).
                    longValue()).toByteArray());
            String reverse = new StringBuilder(messageList[counter]).reverse().toString();
            messageTotal += reverse;
            counter++;
        }
        return messageTotal;
    }
}
