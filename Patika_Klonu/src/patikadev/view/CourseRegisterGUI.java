package patikadev.view;

import patikadev.Helper.Config;
import patikadev.Helper.Helper;
import patikadev.Helper.Item;
import patikadev.model.Course;
import patikadev.model.Patika;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CourseRegisterGUI extends JFrame{
    private JPanel wrapper;
    private JComboBox cmb_select_courses;
    private Patika patika;

    public CourseRegisterGUI(Patika patika)  {
        this.patika=patika;
        this.wrapper = wrapper;
        add(wrapper);
        setSize(400,400);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        Helper.setLayout();
        loadCourseModel();
    }
    private void loadCourseModel() {
        cmb_select_courses.removeAllItems();
        for (Course obj : Course.getCourseListByPatikaID(this.patika.getId())) {
            cmb_select_courses.addItem(new Item(obj.getId(), obj.getName()));
        }
    }
}
