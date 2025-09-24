package app.models;

public class Treller extends  User{
    
    public Treller() {}
    
    public Treller(long id, String fullName, Role role, String email, String password) {
        super(id, fullName, role, email, password);
    }
    public boolean isTreller() {
        return getRole() == Role.Treller;
    }
}