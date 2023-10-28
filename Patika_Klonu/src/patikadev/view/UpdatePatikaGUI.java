package patikadev.view;

import patikadev.Helper.Config;
import patikadev.Helper.Helper;
import patikadev.model.Operator;
import patikadev.model.Patika;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatePatikaGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_patika_name;
    private JButton btn_update;
    private JButton btn_back;
    private Patika patika;
    private Operator operator;

    public UpdatePatikaGUI(Patika patika, Operator operator) {
        this.operator=operator;
        this.patika = patika;
        add(wrapper);
        setSize(300, 150);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);


        fld_patika_name.setText(patika.getName());


        btn_update.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_patika_name)){
                Helper.showMessage("fill");
            }
            else {
                if (Patika.update(patika.getId(),fld_patika_name.getText()));{
                    Helper.showMessage("done");
                }
                dispose();

            }


        });
        btn_back.addActionListener(e -> {
            OperatorGUI operatorGUI= new OperatorGUI(this.operator);
            dispose();
        });
    }
}
