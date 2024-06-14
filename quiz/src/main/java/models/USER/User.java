package models.USER;

import java.math.BigInteger;

public class User {
    private final int id;
    private final String username;
    private final String passwordHash;
    private final String email;
    private final boolean isAdmin;

    public User(int id,String username,String passwordHash,String email,boolean isAdmin){
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.isAdmin = isAdmin;
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
