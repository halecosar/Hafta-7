package patikadev.model;

import patikadev.Helper.DBconnector;

import java.security.PublicKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private int id;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User(int id, String name, String uname, String password, String type) {
        this.id = id;
        this.name = name;
        this.uname = uname;
        this.password = password;
        this.type = type;
    }

    private String name;
    private String uname;
    private String password;
    private String type;

    public static ArrayList<User> getList(){
    ArrayList<User> userList = new ArrayList<>();
    String query = "SELECT * FROM user";
    User obj;
        try {
            Statement st = DBconnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPassword(rs.getString("pass"));
                obj.setType(rs.getString("type"));
                userList.add(obj);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

return userList;
    }
}

