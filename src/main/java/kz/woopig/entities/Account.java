package kz.woopig.entities;

public class Account {

    private int id;
    private final String login;
    private final String password;
    private final String role;

    public Account(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Account(int id, String login, String password, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

// --Commented out by Inspection START (09.03.19 21:21):
//    public void setId(int id) {
//        this.id = id;
//    }
// --Commented out by Inspection STOP (09.03.19 21:21)

    public String getLogin() {
        return login;
    }

    @Override
    public String toString() {
        return "Account{" + "id=" + id + ", login=" + login + ", password=" + password  + ", role=" + role + '}';
    }






}
