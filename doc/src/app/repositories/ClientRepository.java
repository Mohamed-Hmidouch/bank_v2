package app.repositories;

import java.sql.Connection;

import app.models.Client;
import app.repositories.interfaces.ClientInterface;
import app.utils.DatabaseConnection;

public class ClientRepository implements ClientInterface{
    private final Connection conn;

    public ClientRepository(){
        this.conn = DatabaseConnection.getConnection();
    }

    public void save(Client client){
        String sql = "";
    }
}