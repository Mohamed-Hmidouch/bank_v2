package app.models;

public class Admin extends User{
    
    public Admin() {}
    
    public Admin(long id, String fullName, Role role, String email, String password) {
        super(id, fullName, role, email, password);
    }
    public boolean isAdmin() {
        return getRole() == Role.Admin;
    }
}
