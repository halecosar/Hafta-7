package patikadev.model;

import patikadev.Helper.DBconnector;
import patikadev.Helper.Helper;

import java.security.PublicKey;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

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

    public static ArrayList<User> getList() {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user";
        User obj;
        try {
            Statement st = DBconnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
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

    public static boolean add(String name, String uname, String pass, String type) {
        String query = "INSERT INTO user (name,uname,pass,type) VALUES (?,?,?,?) ";
        User findUser = User.getFetch(uname);
        if (findUser != null) {
            Helper.showMessage("Bu kullanıcı adı daha önceden eklenmiş. Lütfen farklı bir kullanıcı adı giriniz.");

            return false;
        }

        try {
            PreparedStatement pr = DBconnector.getInstance().prepareStatement(query);
            pr.setString(1, name);
            pr.setString(2, uname);
            pr.setString(3, pass);
            pr.setString(4, type);

            int response = pr.executeUpdate();
            if (response == -1) {
                Helper.showMessage("error");
            }
            return response != -1;
            // database'e gönderelim;


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static User getFetch(String uname) {
        User obj = null;
        String query = "SELECT * FROM user WHERE uname = ?";
        try {
            PreparedStatement pr = DBconnector.getInstance().prepareStatement(query);
            pr.setString(1, uname);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPassword(rs.getString("pass"));
                obj.setType(rs.getString("type"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }

    public static User getFetch(int id) {
        User obj = null;
        String query = "SELECT * FROM user WHERE id = ?";
        try {
            PreparedStatement pr = DBconnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPassword(rs.getString("pass"));
                obj.setType(rs.getString("type"));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return obj;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM user WHERE id = ? ";
        ArrayList<Course> courseList = Course.getListByUser(id);
        for (Course c : courseList) {
            Course.delete(c.getId());
        }

        try {
            PreparedStatement pr = DBconnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);

            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static boolean update(int id, String name, String uname, String pass, String type) {
        String query = "UPDATE user SET name=?,uname=?,pass=?,type=?,id=? ";
        User findUser = User.getFetch(uname);
        if (findUser != null && findUser.getId() != id) {
            Helper.showMessage("Bu kullanıcı adı daha önceden eklenmiş. Lütfen farklı bir kullanıcı adı giriniz.");

            return false;
        }
        try {
            PreparedStatement pr = DBconnector.getInstance().prepareStatement(query);
            pr.setString(1, name);
            pr.setString(2, uname);
            pr.setString(3, pass);
            pr.setString(4, type);
            pr.setInt(5, id);
            return pr.executeUpdate() != -1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static ArrayList<User> SearchuserList(String query) {
        ArrayList<User> userList = new ArrayList<>();

        User obj;
        try {
            Statement st = DBconnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
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

    public static String searchQuery(String name, String uname, String type) {
        String query = "SELECT * FROM user WHERE uname LIKE '%{{uname}}%' AND name LIKE '%{{name}}%'";

        query = query.replace("{{uname}}", uname);
        query = query.replace("{{name}}", name);
        if (!type.isEmpty()) {
            query += "AND TYPE= '{{type}}'";
            query = query.replace("{{type}}", type);
        }
        query = query.replace("{{type}}", type);
        return query;

    }

    public static User getFetch(String uname, String password) {
        User obj = null;
        String query = "SELECT * FROM user WHERE uname = ? AND pass= ?";
        try {
            PreparedStatement pr = DBconnector.getInstance().prepareStatement(query);
            pr.setString(1, uname);
            pr.setString(2, password);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                switch (rs.getString("type")) {
                    case "operator":
                        obj = new Operator();
                        break;
                    case "educator":
                        obj = new Educator();
                        break;
                    default:
                        obj = new User();
                }

                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPassword(rs.getString("pass"));
                obj.setType(rs.getString("type"));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return obj;
    }
}

