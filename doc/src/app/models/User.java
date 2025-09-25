package app.models;


public class User {
    private long id;
    private String fullName;
    public enum Role {
        User,
        Auditor,
        Treller,
        Manager,
        Admin
    }
    private Role role;
    private String email;
    private String password;
    private boolean loggedIn; // Statut de connexion stocké en base

    public User() {}

    public User(long id, String fullName, Role role, String email, String password) {
        this.id = id;
        this.fullName = fullName;
        this.role = role;
        this.email = email;
        this.password = password;
        this.loggedIn = false; // Par défaut non connecté
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
