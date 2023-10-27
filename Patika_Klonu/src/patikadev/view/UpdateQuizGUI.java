package patikadev.view;

import patikadev.Helper.Config;
import patikadev.Helper.Helper;
import patikadev.model.Content;
import patikadev.model.Quiz;

import javax.swing.*;
import java.awt.*;

public class UpdateQuizGUI extends JFrame {
    private JTextField fld_quiz_name;
    private JPanel wrapper;
    private JButton btn_save;
    Quiz quiz;

    public UpdateQuizGUI(Quiz quiz) {
        this.quiz = quiz;
        add(wrapper);
        setSize(400, 300);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        fld_quiz_name.setText(quiz.getQuestion());

        btn_save.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_quiz_name)) {
                Helper.showMessage("fill");
            } else {
                if (Quiz.update(fld_quiz_name.getText(), quiz.getId())) {

                        Helper.showMessage("done");
                    }
                    dispose();

                }

            });


        }
    }
