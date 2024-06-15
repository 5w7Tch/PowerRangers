package models.USER;

public class User {
    private int id;
    private final String username;
    private final String passwordHash;
    private final String email;
    private final boolean isAdmin;

    public User(int id,String username,String password,String email,boolean isAdmin){
        this.id = id;
        this.username = username;
        this.passwordHash = Hasher.getPasswordHash(password);
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public void setId(int id){
        this.id = id;
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
