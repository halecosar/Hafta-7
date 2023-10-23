package patikadev.model;

import patikadev.Helper.DBconnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Patika {
    private int id;

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

    public Patika(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private String name;

    public static ArrayList<Patika> getList() {
        ArrayList<Patika> patikaList = new ArrayList<>();
        Patika obj;
        try {
            Statement st = DBconnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM patika");
            while (rs.next()) {
                obj = new Patika(rs.getInt("id"), rs.getString("name"));
                patikaList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return patikaList;
    }

    public static boolean add(String name) { //patika ekleme metodu.
        String query = "INSERT INTO patika (name) VALUES (?)";
        try {
            PreparedStatement pr = DBconnector.getInstance().prepareStatement(query);
            pr.setString(1, name); // 1. soru işaretine dışarıdan aldığım değeri ekledim.
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;

    }

    public static boolean update(int id, String name) {
        String query = "UPDATE patika SET name =? WHERE id = ?";
        try {
            PreparedStatement pr = DBconnector.getInstance().prepareStatement(query);
            pr.setString(1, name);
            pr.setInt(2, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }
    public static Patika getFetch(int id) {
        Patika obj = null;
        String query = "SELECT * FROM patika WHERE id=?";
        try {
            PreparedStatement pr = DBconnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new Patika(rs.getInt("id"),rs.getString("name"));


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return obj;
    }
    public static boolean delete(int id){
        String query = "DELETE FROM patika WHERE id=?";
        ArrayList<Course> courseList = Course.getList();
        for (Course c : courseList){
            if (c.getPatika().getId()==id){
                Course.delete(c.getId());
            }
        }
        try {
            PreparedStatement pr = DBconnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
}
