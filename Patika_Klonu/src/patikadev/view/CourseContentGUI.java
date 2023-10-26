package patikadev.view;

import patikadev.Helper.Config;
import patikadev.Helper.Helper;
import patikadev.Helper.Item;
import patikadev.model.Content;
import patikadev.model.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CourseContentGUI extends JFrame{
    private JPanel wrapper;
    private JTable tbl_mycontent;
    private JScrollPane scrl_mycontent;
    private JButton btn_save;
    private JTextField textField1;
    private JComboBox cmb_point;
    private JTextField fld_comment;

    private  DefaultTableModel mdl_course_content_list;
    private Object [] row_course_content_list;
    Content content;

    public CourseContentGUI(Content content) {
        this.content=content;
        add(wrapper);
        setSize(400,400);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setTitle(Config.PROJECT_TITLE);
        Helper.setLayout();

        mdl_course_content_list = new DefaultTableModel();
        Object[] col_course_contentList = {"ID", "İçerik ID", "Puan", "Yorum"}; //colonları oluşturduk.
        mdl_course_content_list.setColumnIdentifiers(col_course_contentList);
        row_course_content_list = new Object[col_course_contentList.length]; //satırları oluşturduk
        loadMyCourseContentModel();
        tbl_mycontent.setModel(mdl_course_content_list);
        tbl_mycontent.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_mycontent.getTableHeader().setReorderingAllowed(false);
    }

    private void loadMyCourseContentModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_mycontent.getModel();
        clearModel.setRowCount(0); //tüm rowları sildik.
        cmb_point.removeAllItems();

        int i = 0;
        for (Course obj : Course.getListByUser(this.educator.getId())) {
            i = 0;
            row_course_content_list[i++] = obj.getId();
            row_course_content_list[i++] = obj.getName();
            row_course_content_list[i++] = obj.getLang();
            row_course_content_list[i++] = obj.getPatika().getName();

            mdl_course_list.addRow(row_courseList);
            cmb_course_list.addItem(new Item(obj.getId(), obj.getName()));
            src_cmb_course.addItem(new Item(obj.getId(), obj.getName()));
        }
    }
}
