package models.USER;

public class User {
    private int id;
    private final String username;
    private String passwordHash;
    private final String email;
    private final boolean isAdmin;

    public User(int id,String username,String password,String email,boolean isAdmin){
        this.id = id;
        this.username = username;
        this.passwordHash = Hasher.getPasswordHash(password);
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public void setHash(String passwordHash){
        this.passwordHash=passwordHash;
    }

    public void setId(int id){
        this.id = id;
    }

    public Integer getId() {
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

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof User)) return false;
        User user = (User) obj;
        return user.id==this.id && user.getUsername().equals(this.username) && user.getEmail().equals(this.email) && user.getPasswordHash().equals(this.passwordHash) && this.isAdmin==user.isAdmin;
    }

    @Override
    public String toString(){
String.join(" ", this.id, this.username, this.email, this.passwordHash, this.isAdmin);
    }
}
