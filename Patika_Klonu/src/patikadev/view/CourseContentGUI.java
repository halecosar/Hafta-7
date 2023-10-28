package patikadev.view;

import patikadev.Helper.Config;
import patikadev.Helper.Helper;
import patikadev.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class CourseContentGUI extends JFrame {
    private JPanel wrapper;
    private JTable tbl_mycontent;
    private JScrollPane scrl_mycontent;
    private JButton btn_save;
    private JTextField fld_selected_content;
    private JComboBox cmb_point;
    private JTextField fld_comment;
    private JButton btn_back;

    private DefaultTableModel mdl_course_content_list;
    private Object[] row_course_content_list;
    private JPopupMenu quizMenu;
    Course course;
    Student student;

    public CourseContentGUI(Course course, Student student) {

        this.student=student;
        this.course = course;
        add(wrapper);
        setSize(400, 400);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setTitle(Config.PROJECT_TITLE);
        Helper.setLayout();

        mdl_course_content_list = new DefaultTableModel();
        Object[] col_course_contentList = {"ID", "İçerik Adı", "Açıklama", "Link", "Ders Adı"}; //colonları oluşturduk.
        mdl_course_content_list.setColumnIdentifiers(col_course_contentList);
        row_course_content_list = new Object[col_course_contentList.length]; //satırları oluşturduk
        loadMyCourseContentModel();
        tbl_mycontent.setModel(mdl_course_content_list);
        tbl_mycontent.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_mycontent.getTableHeader().setReorderingAllowed(false);

        tbl_mycontent.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_content_id = tbl_mycontent.getValueAt(tbl_mycontent.getSelectedRow(), 0).toString();
                fld_selected_content.setText(selected_content_id); //seçtiğin satır kullanıcı id kutucuğunda görünecek.

            } catch (Exception ex) {

            }

        });
        btn_save.addActionListener(e -> {


            if (Helper.isFieldEmpty(fld_comment)) {
                Helper.showMessage("fill");

            } else {
                // Helper.showMessage("done");

                String comment = fld_comment.getText();
                int point = Integer.parseInt(cmb_point.getSelectedItem().toString());
                if (ContentEvaluation.add(Integer.parseInt(fld_selected_content.getText()),this.student.getId(),point,comment )) {
                    Helper.showMessage("done");
                    fld_comment.setText(null);
                    fld_selected_content.setText(null);
                }
            }

        });

        quizMenu = new JPopupMenu();
        JMenuItem quizlistmenu = new JMenuItem("Quizlere git");
        quizMenu.add(quizlistmenu);

        tbl_mycontent.setComponentPopupMenu(quizMenu);

        tbl_mycontent.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_mycontent.rowAtPoint(point);
                tbl_mycontent.setRowSelectionInterval(selected_row, selected_row); // sağ tıkladığın yer seçili geliyor.
            }
        });

        quizlistmenu.addActionListener(e -> {
            int select_id = Integer.parseInt(tbl_mycontent.getValueAt(tbl_mycontent.getSelectedRow(), 0).toString());
           QuizResultGUI quizResultGUI = new QuizResultGUI(Content.getFetch(select_id),this.student);
            quizResultGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {

                }
            });

        });


        btn_back.addActionListener(e -> {
            StudentGUI studentGUI=new StudentGUI(this.student);
            dispose();

        });
    }

    private void loadMyCourseContentModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_mycontent.getModel();
        clearModel.setRowCount(0); //tüm rowları sildik.
        int i = 0;
        for (Content obj : Content.getListContentByCourse(
                this.course.getId())) {
            i = 0;
            row_course_content_list[i++] = obj.getId();
            row_course_content_list[i++] = obj.getName();
            row_course_content_list[i++] = obj.getDescription();
            row_course_content_list[i++] = obj.getLink();
            row_course_content_list[i++] = obj.getCourse().getName();
            mdl_course_content_list.addRow(row_course_content_list);
        }
    }
}
