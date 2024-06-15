package models.USER;

import java.security.*;


public class User {
    private final int id;
    private final String username;
    private final String passwordHash;
    private final String email;
    private final boolean isAdmin;

    public User(int id,String username,String password,String email,boolean isAdmin){
        this.id = id;
        this.username = username;
        this.passwordHash = getPasswordHash(password);
        this.email = email;
        this.isAdmin = isAdmin;
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

    private static byte[] getHex(String pass){
        MessageDigest dontKnow ;
        try{
            dontKnow = MessageDigest.getInstance("SHA");
            dontKnow.update(pass.getBytes());
            return dontKnow.digest();
        } catch (NoSuchAlgorithmException e) {throw new RuntimeException(e);}
    }

    public static String getPasswordHash(String password){
        return hexToString(getHex(password));
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
