package patikadev.view;

import patikadev.Helper.Config;
import patikadev.Helper.Helper;
import patikadev.Helper.Item;
import patikadev.model.Content;
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
    private JTable tbl_content_list;
    private JButton btn_add_content;
    private JComboBox cmb_course_list;
    private JTextField fld_content_title;
    private JTextField fld_content_caption;
    private JTextField fld_youtube_link;
    private JTable tbl_quiz;
    private JButton btn_question_add;
    private JComboBox cmb_questions_content;
    private JTextField fld_quiz_question;
    private JButton btn_delete;
    private JTextField fld_content_id;
    private Object[] row_courseList;
    private DefaultTableModel mdl_course_list;
    private DefaultTableModel mdl_content_list;
    private Educator educator;
    private Content content;
    private Object[] row_content_list;


    public EducatorGUI(Educator educator){
        this.educator=educator;
        add(wrapper);
        setSize(500,500);
        setLocation(Helper.screenCenterPoint("x",getSize()), Helper.screenCenterPoint("y",getSize()));
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle(Config.PROJECT_TITLE);
        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI login = new LoginGUI();
        });

        //courselist listesi tanım yeri
        mdl_course_list = new DefaultTableModel();
        Object[] col_courseList = {"ID", "Ders Adı", "Programlama Dili", "Dersin Patikası", "Eğitmen"}; //colonları oluşturduk.
        mdl_course_list.setColumnIdentifiers(col_courseList);
        row_courseList = new Object[col_courseList.length]; //satırları oluşturduk
        loadCourseModel();
        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);

        //contentlistesinin tanım kısmı

        mdl_content_list = new DefaultTableModel();
        Object[] col_contentList = {"ID", "İçerik Adı", "Açıklama", "Link", "Ders Adı"}; //colonları oluşturduk.
        mdl_content_list.setColumnIdentifiers(col_contentList);
        row_content_list = new Object[col_contentList.length]; //satırları oluşturduk
        loadContentModel();
        tbl_content_list.setModel(mdl_content_list);
        tbl_content_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_content_list.getTableHeader().setReorderingAllowed(false);

        tbl_content_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_content_id = tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(), 0).toString();
                fld_content_id.setText(selected_content_id); //seçtiğin satır kullanıcı id kutucuğunda görünecek.

            } catch (Exception ex) {

            }

        });



        btn_add_content.addActionListener(e -> {
            Item courseItem = (Item) cmb_course_list.getSelectedItem();

            if (Helper.isFieldEmpty(fld_content_title) || Helper.isFieldEmpty(fld_content_caption) || Helper.isFieldEmpty(fld_youtube_link)) {
                Helper.showMessage("fill");
            } else {
                if (Content.add(fld_content_title.getText(), fld_content_caption.getText(),fld_youtube_link.getText(), courseItem.getKey())) {
                    Helper.showMessage("done");
                    loadContentModel();
                    fld_content_caption.setText(null);
                    fld_content_title.setText(null);
                    fld_youtube_link.setText(null);

                } else {
                    Helper.showMessage("error");
                }

            }

        });

        btn_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_content_id)) {
                Helper.showMessage("fill");
            } else {
                if (Helper.confirm("sure")) {
                    if (Content.delete(Integer.parseInt(fld_content_id.getText()))) {
                        Helper.showMessage("done");

                        loadContentModel();
                        fld_content_id.setText(null);
                    } else {
                        Helper.showMessage("error");
                    }
                }
            }

        });
    }

    private void loadCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0); //tüm rowları sildik.
        cmb_course_list.removeAllItems();
        int i = 0;
        for (Course obj : Course.getListByUser(this.educator.getId())) {
            i = 0;
            row_courseList[i++] = obj.getId();
            row_courseList[i++] = obj.getName();
            row_courseList[i++] = obj.getLang();
            row_courseList[i++] = obj.getPatika().getName();
            row_courseList[i++] = obj.getEducator().getName();
            mdl_course_list.addRow(row_courseList);
            cmb_course_list.addItem(new Item(obj.getId(), obj.getName()));
        }
    }
    public void loadContentModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0); //tüm rowları sildik.
        int i = 0;
        for (Course course : Course.getListByUser(this.educator.getId())) {
            for (Content obj : Content.getListContentByCourse(course.getId())) {
                i = 0;
                row_content_list[i++] = obj.getId();
                row_content_list[i++] = obj.getName();
                row_content_list[i++] = obj.getDescription();
                row_content_list[i++] = obj.getLink();
                row_content_list[i++] = obj.getCourse().getName();
                mdl_content_list.addRow(row_content_list);

            }
        }
    }
}
