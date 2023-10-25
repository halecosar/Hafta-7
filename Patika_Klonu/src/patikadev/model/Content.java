package patikadev.model;

import patikadev.Helper.DBconnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Content {
    public Content(int id, String name, String description, String link, int course_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.link = link;
        this.course_id = course_id;
    }

    private int id;
    private String name;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    private String description;
    private String link;
    private int course_id;

    public static ArrayList<Course> getListByEducatorCourse(int user_id){
        ArrayList<Course> courseList= new ArrayList<>();

        Course obj;
        try {
            Statement st = DBconnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM course WHERE user_id=" +user_id);
            while (rs.next()){
                int id = rs.getInt("id");
                int userID = rs.getInt("user_id");
                int patika_id = rs.getInt("patika_id");
                String name = rs.getString("name");
                String lang = rs.getString("lang");
                obj = new Course(id,user_id,patika_id,name,lang);
                courseList.add(obj);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courseList;
    }
}
