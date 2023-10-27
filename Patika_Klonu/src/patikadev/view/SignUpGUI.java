package patikadev.view;

import patikadev.Helper.Config;
import patikadev.Helper.Helper;
import patikadev.model.Educator;
import patikadev.model.Operator;
import patikadev.model.Student;
import patikadev.model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_name;
    private JTextField fld_username;
    private JPasswordField fld_password;
    private JButton btn_save;

    public SignUpGUI() {
        add(wrapper);
        setSize(500, 500);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
        Helper.setLayout();


        btn_save.addActionListener(e -> {

            User s = User.getFetch(fld_username.getText());
            if (s == null) {
                User.add(fld_name.getText(), fld_username.getText(), fld_password.getText(), "student");
                Helper.showMessage("Kayıt İşlemi Başarılı. Giriş Yapabilirsiniz.");
                dispose();
            } else {
                Helper.showMessage("Bu kullanıcı sistemde mevcut");
            }


        });
    }

}