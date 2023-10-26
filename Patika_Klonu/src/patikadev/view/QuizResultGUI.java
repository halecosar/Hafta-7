package patikadev.view;

import patikadev.Helper.Config;
import patikadev.Helper.Helper;
import patikadev.Helper.Item;
import patikadev.model.Content;
import patikadev.model.Quiz;
import patikadev.model.QuizResult;
import patikadev.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizResultGUI extends JFrame{
    private JPanel wrapper;
    private JTable tbl_quiz;
    private JTextField fld_answer;
    private JButton btn_save;
    private JTextField fld_quiz_id;
    private DefaultTableModel mdl_quiz_list;
    private Object[] row_quiz_list;
    private Content content;
    private Student student;

    public QuizResultGUI(Content content, Student student) throws HeadlessException {
        this.content = content;
        this.student = student;

        add(wrapper);
        setSize(400, 400);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        setResizable(false);

        mdl_quiz_list = new DefaultTableModel();
        Object[] col_quizList = {"ID", "content_name", "question"}; //colonları oluşturduk.
        mdl_quiz_list.setColumnIdentifiers(col_quizList);
        row_quiz_list = new Object[col_quizList.length]; //satırları oluşturduk
        loadQuizModel();
        tbl_quiz.setModel(mdl_quiz_list);
        tbl_quiz.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_quiz.getTableHeader().setReorderingAllowed(false);

        tbl_quiz.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_quiz_id = tbl_quiz.getValueAt(tbl_quiz.getSelectedRow(), 0).toString();
                fld_quiz_id.setText(selected_quiz_id); //seçtiğin satır kullanıcı id kutucuğunda görünecek.

            } catch (Exception ex) {

            }

        });

        btn_save.addActionListener(e -> {


            if (Helper.isFieldEmpty(fld_answer) ) {
                Helper.showMessage("fill");
            } else {
                if (QuizResult.add(Integer.parseInt(fld_quiz_id.getText()),this.student.getId(), fld_answer.getText())) {
                    Helper.showMessage("done");
                    fld_answer.setText(null);
                    fld_quiz_id.setText(null);

                } else {
                    Helper.showMessage("error");
                }

            }


        });
    }
    private void loadQuizModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_quiz.getModel();
        clearModel.setRowCount(0); //tüm rowları sildik.
        int i = 0;
        for (Quiz obj : Quiz.getListQuizByContent(this.content.getId())) {
            i = 0;
            row_quiz_list[i++] = obj.getId();
            row_quiz_list[i++] = obj.getContent().getName();
            row_quiz_list[i++] = obj.getQuestion();

            mdl_quiz_list.addRow(row_quiz_list);
        }

    }
}
