package models.USER;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Hasher {
    private Hasher(){};

    private static byte[] getHex(String pass){
        MessageDigest dontKnow ;
        try{
            dontKnow = MessageDigest.getInstance("SHA");
            dontKnow.update(pass.getBytes());
            return dontKnow.digest();
        } catch (NoSuchAlgorithmException e) {throw new RuntimeException(e);}
    }

    private static String hexToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i=0; i<bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff;  // remove higher bits, sign
            if (val<16) buff.append('0'); // leading 0
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }

    public static String getPasswordHash(String password){
        byte[] hex = Hasher.getHex(password);
        return Hasher.hexToString(hex);
    }
}
