package patikadev.model;

import patikadev.Helper.DBconnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MyContent {
    int id;
    int content_id;
    int point;
    String comment;
    Content content;

    public MyContent(int id, int content_id, int point, String comment) {
        this.id = id;
        this.content_id = content_id;
        this.point = point;
        this.comment = comment;
        this.content = Content.getFetch(content_id);
    }
    public static ArrayList<MyCourse> getListByContent_id){
        ArrayList<MyCourse> myCourseList= new ArrayList<>();

        MyCourse obj;
        try {
            Statement st = DBconnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM mycourse WHERE user_id=" +user_id);
            while (rs.next()){

                int id= rs.getInt("id");
                int courseid= rs.getInt("course_id");

                obj = new MyCourse(id,courseid);
                myCourseList.add(obj);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return myCourseList;
    }}