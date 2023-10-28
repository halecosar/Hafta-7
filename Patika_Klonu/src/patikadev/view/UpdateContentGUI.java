package patikadev.view;

import patikadev.Helper.Config;
import patikadev.Helper.Helper;
import patikadev.model.Content;
import patikadev.model.Operator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateContentGUI extends JFrame {
    private Content content;
    private JPanel wrapper;
    private JTextField fld_content_name;
    private JButton btn_update;
    private JTextField fld_content_description;
    private JTextField fld_content_link;

    public UpdateContentGUI(Content content) {
        this.content = content;
        add(wrapper);
        setSize(400, 300);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        fld_content_name.setText(content.getName());
        fld_content_description.setText(content.getDescription());
        fld_content_link.setText(content.getLink());

        btn_update.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_content_name) || Helper.isFieldEmpty(fld_content_description) || Helper.isFieldEmpty(fld_content_link))
            {
                Helper.showMessage("fill");
            }
            else{
                if (Content.update(content.getId(), fld_content_name.getText(), fld_content_description.getText(), fld_content_link.getText()))
                {
                    Helper.showMessage("done");
                }
                dispose();

            }

        });
    }


}
