package patikadev.view;

import patikadev.Helper.Config;
import patikadev.Helper.Helper;
import patikadev.Helper.Item;
import patikadev.model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourseRegisterGUI extends JFrame {
    private JPanel wrapper;
    private JComboBox cmb_select_courses;
    private JButton btn_course_register;
    private JButton btn_back;
    private Patika patika;
    private Student user;

    public CourseRegisterGUI(Patika patika, Student user) {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        this.user = user;
        this.patika = patika;
        this.wrapper = wrapper;
        add(wrapper);
        setSize(400, 400);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        Helper.setLayout();
        loadCourseModel();

        btn_course_register.addActionListener(e -> {

            Item courseItem = (Item) cmb_select_courses.getSelectedItem();

            MyCourse myCourse = MyCourse.checkMyCourseByUserID(this.user.getId(), courseItem.getKey());
            if (myCourse!= null){
                Helper.showMessage("Bu derse daha önce kayıt oldunuz!");
                StudentGUI stuGUI = new StudentGUI(this.user);
                dispose();
            }
            else {
                if (MyCourse.add(this.user.getId(), courseItem.getKey())) {
                    Helper.showMessage("done");
                    StudentGUI stuGUI = new StudentGUI(this.user);
                    dispose();
                } else {
                    Helper.showMessage("error");
                }
            }




        });

        btn_back.addActionListener(e -> {
            StudentGUI studentGUI = new StudentGUI(this.user);
            dispose();
        });
    }

    private void loadCourseModel() {
        cmb_select_courses.removeAllItems();
        for (Course obj : Course.getCourseListByPatikaID(this.patika.getId())) {
            cmb_select_courses.addItem(new Item(obj.getId(), obj.getName()));
        }
    }
}
