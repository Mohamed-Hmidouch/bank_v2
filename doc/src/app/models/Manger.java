package app.models;

public class Manger extends User {
    
    public Manger() {}
    
    public Manger(long id, String fullName, Role role, String email, String password) {
        super(id, fullName, role, email, password);
    }
    public boolean isManger(){
        return getRole() == Role.Manager;
    }
}
