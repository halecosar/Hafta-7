package patikadev.view;

import patikadev.Helper.Config;
import patikadev.Helper.Helper;
import patikadev.model.Course;
import patikadev.model.Educator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EducatorGUI extends JFrame {
    private JPanel wrapper;
    private JButton btn_logout;
    private JTabbedPane tabbed;
    private JPanel pnl_course_list;
    private JScrollPane scrol_course_list;
    private JTable tbl_course_list;
    private JPanel pnl_content_list;
    private JScrollPane scrl_content_list;
    private JPanel pnl_education_add;
    private JTable table1;
    private JButton btn_add_content;
    private JComboBox cmb_course_list;
    private JTextField fld_content_title;
    private JTextField fld_content_caption;
    private JTextField fld_youtube_link;
    private JTable tbl_quiz;
    private JButton btn_question_add;
    private JComboBox cmb_questions_content;
    private JTextField fld_quiz_question;
    private Object[] row_courseList;
    private DefaultTableModel mdl_course_list;
    private Educator educator;


    public EducatorGUI(Educator educator){
        this.educator=educator;
        add(wrapper);
        setSize(400,400);
        setLocation(Helper.screenCenterPoint("x",getSize()), Helper.screenCenterPoint("y",getSize()));
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle(Config.PROJECT_TITLE);
        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI login = new LoginGUI();
        });

        mdl_course_list = new DefaultTableModel();
        Object[] col_courseList = {"ID", "Ders Adı", "Programlama Dili", "Dersin Patikası", "Eğitmen"}; //colonları oluşturduk.
        mdl_course_list.setColumnIdentifiers(col_courseList);
        row_courseList = new Object[col_courseList.length]; //satırları oluşturduk
        loadCourseModel();
        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);

        loadCourseModel();
    }

    private void loadCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0); //tüm rowları sildik.
        int i = 0;
        for (Course obj : Course.getListByUser(this.educator.getId())) {
            i = 0;
            row_courseList[i++] = obj.getId();
            row_courseList[i++] = obj.getName();
            row_courseList[i++] = obj.getLang();
            row_courseList[i++] = obj.getPatika().getName();
            row_courseList[i++] = obj.getEducator().getName();
            mdl_course_list.addRow(row_courseList);

        }
    }
}
