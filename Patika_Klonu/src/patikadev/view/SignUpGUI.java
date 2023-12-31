package patikadev.view;

import patikadev.Helper.Config;
import patikadev.Helper.Helper;
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
    private JButton btn_back;

    public SignUpGUI() {
        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
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
            }}
        Helper.setLayout();
        add(wrapper);
        setSize(350, 450);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);



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
        btn_back.addActionListener(e -> {
            LoginGUI loginGUI=new LoginGUI();
            dispose();
        });
    }

}