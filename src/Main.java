import java.math.BigInteger;

public class Main {
    public static void main (String[] args) {
        //keyGen();
       RSA runner = new RSA("222395321787836518691", "154094292072161335507",
                "158256261900246722011", 65);
        //System.out.println("Message: "+ runner.encryptMessage("Delaware, Maryland, New Jersey, New York, Ohio, West Virginia", "222395321787836518691",
                //"158256261900246722011"));
       // System.out.println("Message: " + runner.decryptingMessage("113073535549861574219,41330179509411463749,143671647165577043262,5313114222390994501,132158891411143309618,90019404282813502545"));

        Cracker test = new Cracker("40877278455398742311971", "9601259708135601859547");
        System.out.println("Message: " + runner.secretMessage("38091009822123274557478", test.fermatFactoring(), "40877278455398742311971"));
    }
    static void keyGen(){
        RSA keyGen = new RSA(66);
        System.out.println("PrivateD: " + keyGen.getPrivateD());
        System.out.println("PublicPrivateN: " + keyGen.getPublicPrivateN());
        System.out.println("PublicE: " + keyGen.getPublicE());
        System.out.println("Bit Length: " + keyGen.getBitLength());
        System.out.println(keyGen.getBitLength() + ";" + keyGen.getPublicPrivateN() + ";" + keyGen.getPublicE());

    }
}


