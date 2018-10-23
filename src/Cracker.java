import java.math.BigInteger;

public class Cracker {
    private BigInteger publicPrivateN;
    private BigInteger publicE;

    public Cracker(String publicPrivateNS, String publicES) {
        this.publicE = new BigInteger(publicES);
        this.publicPrivateN = new BigInteger(publicPrivateNS);
    }

    public BigInteger fermatFactoring(){
        BigInteger x = bigIntSquareRoot(publicPrivateN);
        BigInteger xSquared = x.pow(2);
        BigInteger y = BigInteger.ZERO;
        BigInteger ySquared = y.pow(2);
        BigInteger q;
        BigInteger p;
        while(!xSquared.subtract(ySquared).equals(publicPrivateN)){
            if(xSquared.subtract(ySquared).compareTo(publicPrivateN) < 0){
                x = x.add(BigInteger.ONE);
                xSquared = x.pow(2);
            }
            else{
                y = y.add(BigInteger.ONE);
                ySquared = y.pow(2);
            }
        }
        p = x.add(y);
        q = x.subtract(y);
        //System.out.println(p + "," + q);
        return publicE.modInverse(publicPrivateN.subtract(p).subtract(q).add(BigInteger.ONE));
    }

    public BigInteger bigIntSquareRoot(BigInteger n){
        BigInteger guess = BigInteger.ONE;
        BigInteger a = n.divide(guess);
        while(guess.subtract(a).abs().compareTo(BigInteger.ONE) > 0){
            guess = (guess.add(a)).divide(new BigInteger("2"));
            a = n.divide(guess);
        }
        //System.out.println(guess);
        return guess;
    }
}
