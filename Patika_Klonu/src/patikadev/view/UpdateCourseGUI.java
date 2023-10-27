package patikadev.view;

import patikadev.Helper.Config;
import patikadev.Helper.Helper;
import patikadev.model.Course;
import patikadev.model.Patika;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateCourseGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_course_name;
    private JTextField fld_course_lang;
    private JButton btn_update;
    private Course course;

    public UpdateCourseGUI(Course course) {
        this.course = course;
        add(wrapper);
        setSize(400, 300);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        fld_course_name.setText(course.getName());
        fld_course_lang.setText(course.getLang());

        btn_update.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_course_name) || Helper.isFieldEmpty(fld_course_lang)) {
                Helper.showMessage("fill");
            } else {
                if (Course.update(fld_course_name.getText(), fld_course_lang.getText(),this.course.getId())) {
                    Helper.showMessage("done");
                }
                dispose();

            }

        });

    }
}