package patikadev.view;

import patikadev.Helper.Config;
import patikadev.Helper.Helper;
import patikadev.Helper.Item;
import patikadev.model.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tabbed;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_user_list;
    private JScrollPane scrol_userlist;
    private JTable table_userlist;
    private JPanel pnl_userform;
    private JTextField fld_uname;
    private JPasswordField fld_password;
    private JButton btn_user_add;
    private JLabel user;
    private JComboBox cmb_usertype;
    private JTextField fld_name_surname;
    private JLabel field_uname;
    private JTextField fld_user_id;
    private JButton btn_user_delete;
    private JTextField fld_sr_username;
    private JTextField fld_sr_user_uname;
    private JLabel fld_user_type;
    private JComboBox cmb_sr_usertype;
    private JButton btn_user_sh;
    private JPanel pnl_patika_list;
    private JScrollPane scrl_patika_list;
    private JTable tbl_patika_list;
    private JPanel pnl_patika_add;
    private JTextField fld_patika_name;
    private JButton btn_patika_add;
    private JPanel pnl_course_list;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_list;
    private JPanel pnl_course_add;
    private JTextField fld_course_name;
    private JTextField fld_course_lang;
    private JComboBox cmb_course_patika;
    private JComboBox cmb_course_user;
    private JButton btn_course_add;
    private JPanel pnl_content_list;
    private JPanel pnl_quiz_list;
    private JScrollPane scrl_content_list;
    private JTable tbl_content_list;
    private JScrollPane scrl_quiz_list;
    private JTable tbl_quiz_list;
    private final Operator operator;
    //bir tabloya dinamik olarak yapıları nasıl aktaracağız dbden çekip:
    private DefaultTableModel mdl_userlist; // bir modele ihtiyacımız var bu sebeple burayı oluşturduk. (yukarıda elle yaptk)
    private Object[] row_userlist;
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private JPopupMenu patikaMenu;
    private DefaultTableModel mdl_course_list;
    private Object[] row_courseList;
    private JPopupMenu courseMenu;
    private DefaultTableModel mdl_content_list;
    private Object[] row_contentList;
    private JPopupMenu contentMenu;
    private JPopupMenu quizMenu;
    private DefaultTableModel mdl_quiz_list;
    private Object[] row_quiz_list;

    public OperatorGUI(Operator operator) {
        this.operator = operator;

        add(wrapper);
        setSize(1200, 600);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        lbl_welcome.setText("Hoşgeldin : " + operator.getName());

        //UserList
        mdl_userlist = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }

        };
        Object[] col_userlist = {"ID", "Ad Soyad", "Kullanıcı Adı", "Şifre", "Üyelik Tipi"}; // modelimin kolon isimlerini obje veritipinde tanımladım array oluşturdum.
        mdl_userlist.setColumnIdentifiers(col_userlist); // süslü parantez içindekiler tablomdaki başlıklar.
        row_userlist = new Object[col_userlist.length];

        // Object [] firstRow = {1,"Hale Coşar","hale","123","operator"}; //manuel yol.
        // mdl_userlist.addRow(firstRow);

        loadUserModel();
        table_userlist.setModel(mdl_userlist);
        table_userlist.getTableHeader().setReorderingAllowed(false);
        table_userlist.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_user_id = table_userlist.getValueAt(table_userlist.getSelectedRow(), 0).toString();
                System.out.println(select_user_id);
                fld_user_id.setText(select_user_id); //seçtiğin satır kullanıcı id kutucuğunda görünecek.


            } catch (Exception ex) {

            }

        });
        table_userlist.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int user_id = Integer.parseInt(table_userlist.getValueAt(table_userlist.getSelectedRow(), 0).toString());
                String user_name = table_userlist.getValueAt(table_userlist.getSelectedRow(), 1).toString();
                String user_uname = table_userlist.getValueAt(table_userlist.getSelectedRow(), 2).toString();
                String user_pass = table_userlist.getValueAt(table_userlist.getSelectedRow(), 3).toString();
                String user_type = table_userlist.getValueAt(table_userlist.getSelectedRow(), 4).toString();

                if (User.update(user_id, user_name, user_uname, user_pass, user_type)) {
                    Helper.showMessage("done");

                }
                loadUserModel();
                loadEducatorCombo();
                loadCourseModel();
            }
        });
// ## UserLİst

        //PatikaList başlangıç
//eklediğin patikaları silebilmek için component.
        patikaMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");
        patikaMenu.add(updateMenu);
        patikaMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int select_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(), 0).toString());
            UpdatePatikaGUI updateGUI = new UpdatePatikaGUI(Patika.getFetch(select_id));
            updateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPatikaModel();
                    loadPatikaCombo();
                    loadCourseModel();
                }
            });

        });

        deleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure")) ;
            int select_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(), 0).toString());
            if (Patika.delete(select_id)) {
                Helper.showMessage("done");
                loadPatikaModel();
                loadPatikaCombo();
                loadCourseModel();
            } else {
                Helper.showMessage("error");
            }

        });


        mdl_patika_list = new DefaultTableModel();


        Object[] col_patika_list = {"ID", "Patika Adı"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new Object[col_patika_list.length];
        loadPatikaModel();

        tbl_patika_list.setModel(mdl_patika_list); //bu kod sayesinde listede göründü.
        tbl_patika_list.setComponentPopupMenu(patikaMenu); //116.satır
        tbl_patika_list.getTableHeader().setReorderingAllowed(false); // başlıkları sürükleyip değiştirmeyi kapattık, sabitler.
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(80); //burada da id kısmına ayrılan alanı daralttık.

        tbl_patika_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //super.mousePressed(e);
                Point point = e.getPoint();
                int selected_row = tbl_patika_list.rowAtPoint(point);
                tbl_patika_list.setRowSelectionInterval(selected_row, selected_row); // sağ tıkladığın yer seçili geliyor.
            }
        });
        // ## PatikaList bitti.

        //CourseList başlangıç:
        mdl_course_list = new DefaultTableModel();
        Object[] col_courseList = {"ID", "Ders Adı", "Programlama Dili", "Dersin Patikası", "Eğitmen"}; //colonları oluşturduk.
        mdl_course_list.setColumnIdentifiers(col_courseList);
        row_courseList = new Object[col_courseList.length]; //satırları oluşturduk
        loadCourseModel();
        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);

        loadPatikaCombo();
        loadEducatorCombo();
        //## CourseList bitiş.


        btn_user_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_name_surname) || Helper.isFieldEmpty(fld_uname) || Helper.isFieldEmpty(fld_password)) {
                Helper.showMessage("fill");

            } else {
                // Helper.showMessage("done");

                String name = fld_name_surname.getText();
                String uname = fld_uname.getText();
                String password = fld_password.getText();
                String type = cmb_usertype.getSelectedItem().toString();
                if (User.add(name, uname, password, type)) {
                    Helper.showMessage("done");
                    loadUserModel();
                    loadEducatorCombo();
                    fld_uname.setText(null); //PANELDEN EKLEME YAPARKEN EKLEME SONRASU KUTULARIN İÇİNİ BOŞALTTIK.
                    fld_name_surname.setText(null);
                    fld_password.setText(null);


                }


            }
        });
        btn_user_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.isFieldEmpty(fld_user_id)) {
                    Helper.showMessage("fill");
                } else {
                    if (Helper.confirm("sure")) {
                        int user_id = Integer.parseInt(fld_user_id.getText());
                        if (User.delete(user_id)) {
                            Helper.showMessage("done");
                            loadUserModel();
                            loadEducatorCombo();
                            loadCourseModel();
                            fld_user_id.setText(null);
                        } else {
                            Helper.showMessage("error");
                        }
                    }
                }
            }
        });
        btn_user_sh.addActionListener(e -> {
            ArrayList<User> searchingUser;
            String name = fld_sr_username.getText();
            String uname = fld_sr_user_uname.getText();
            String type = cmb_sr_usertype.getSelectedItem().toString();
            if (name.isEmpty() && uname.isEmpty() && type.isEmpty()) {
                searchingUser = User.getList();
                loadUserModel(searchingUser);
            } else {
                String query = User.searchQuery(name, uname, type);
                searchingUser = User.SearchuserList(query);
                loadUserModel(searchingUser);
            }

        });
        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI login = new LoginGUI();

        });
        btn_patika_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_patika_name)) {
                Helper.showMessage("fill");
            } else {
                if (Patika.add(fld_patika_name.getText())) {
                    Helper.showMessage("done");
                    loadPatikaModel();
                    loadPatikaCombo();
                    fld_patika_name.setText(null);
                } else {
                    Helper.showMessage("error");
                }
            }

        });
        btn_course_add.addActionListener(e -> {
            Item patikaItem = (Item) cmb_course_patika.getSelectedItem();
            Item userItem = (Item) cmb_course_user.getSelectedItem();
            if (Helper.isFieldEmpty(fld_course_name) || Helper.isFieldEmpty(fld_course_lang)) {
                Helper.showMessage("fill");
            } else {
                if (Course.add(userItem.getKey(), patikaItem.getKey(), fld_course_name.getText(), fld_course_lang.getText())) {
                    Helper.showMessage("done");
                    loadCourseModel();
                    fld_course_lang.setText(null);
                    fld_course_name.setText(null);

                } else {
                    Helper.showMessage("error");
                }

            }

        });
        tbl_course_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //super.mousePressed(e);
                Point point = e.getPoint();
                int selected_row = tbl_course_list.rowAtPoint(point);
                tbl_course_list.setRowSelectionInterval(selected_row, selected_row); // sağ tıkladığın yer seçili geliyor.
            }
        });

        courseMenu = new JPopupMenu();
        JMenuItem updateCourseMenu = new JMenuItem("Güncelle");
        JMenuItem deleteCourseMenu = new JMenuItem("Sil");
        courseMenu.add(updateCourseMenu);
        courseMenu.add(deleteCourseMenu);

        tbl_course_list.setComponentPopupMenu(courseMenu);

        updateCourseMenu.addActionListener(e -> {
            int select_id = Integer.parseInt(tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(), 0).toString());
            UpdateCourseGUI updateCourseGUI = new UpdateCourseGUI(Course.getFetch(select_id));
            updateCourseGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {

                    loadCourseModel();
                }
            });

        });
        deleteCourseMenu.addActionListener(e -> {
            if (Helper.confirm("sure")) ;
            int select_id = Integer.parseInt(tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(), 0).toString());
            if (Course.delete(select_id)) {
                Helper.showMessage("done");
                loadCourseModel();
            } else {
                Helper.showMessage("error");
            }

        });

        // content-silme güncelleme işlemleri
        contentMenu = new JPopupMenu();
        JMenuItem updateContentMenu = new JMenuItem("Güncelle");
        JMenuItem deleteContentMenu = new JMenuItem("Sil");
        contentMenu.add(updateContentMenu);
        contentMenu.add(deleteContentMenu);

        tbl_content_list.setComponentPopupMenu(contentMenu);

        mdl_content_list = new DefaultTableModel();
        Object[] col_content_list = {"ID", "Name", "Description", "link"};
        mdl_content_list.setColumnIdentifiers(col_content_list);
        row_contentList = new Object[col_content_list.length];
        loadContentModel();

        tbl_content_list.setModel(mdl_content_list); //bu kod sayesinde listede göründü.
        tbl_content_list.setComponentPopupMenu(contentMenu); //116.satır
        tbl_content_list.getTableHeader().setReorderingAllowed(false); // başlıkları sürükleyip değiştirmeyi kapattık, sabitler.
        tbl_content_list.getColumnModel().getColumn(0).setMaxWidth(80); //burada da id kısmına ayrılan alanı daralttık.

        updateContentMenu.addActionListener(e -> {
            int select_id = Integer.parseInt(tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(), 0).toString());
            UpdateContentGUI updateContentGUI = new UpdateContentGUI(Content.getFetch(select_id));
            updateContentGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {

                    loadContentModel();
                }
            });

        });
        deleteContentMenu.addActionListener(e -> {
            if (Helper.confirm("sure")) ;
            int select_id = Integer.parseInt(tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(), 0).toString());
            if (Content.delete(select_id)) {
                Helper.showMessage("done");
                loadContentModel();
            } else {
                Helper.showMessage("error");
            }
        });

        //content işlem sonu

        //quiz işlemleri
        quizMenu = new JPopupMenu();
        JMenuItem updateQuizMenu = new JMenuItem("Güncelle");
        JMenuItem deleteQuizMenu = new JMenuItem("Sil");
        quizMenu.add(updateQuizMenu);
        quizMenu.add(deleteQuizMenu);

        tbl_quiz_list.setComponentPopupMenu(quizMenu);

        mdl_quiz_list = new DefaultTableModel();
        Object[] col_quiz_list = {"ID", "Content Name", "Question"};
        mdl_quiz_list.setColumnIdentifiers(col_quiz_list);
        row_quiz_list = new Object[col_quiz_list.length];
        loadQuizModel();

        tbl_quiz_list.setModel(mdl_quiz_list); //bu kod sayesinde listede göründü.
        tbl_quiz_list.setComponentPopupMenu(quizMenu); //116.satır
        tbl_quiz_list.getTableHeader().setReorderingAllowed(false); // başlıkları sürükleyip değiştirmeyi kapattık, sabitler.
        tbl_quiz_list.getColumnModel().getColumn(0).setMaxWidth(80); //burada da id kısmına ayrılan alanı daralttık.

        updateQuizMenu.addActionListener(e -> {
            int select_id = Integer.parseInt(tbl_quiz_list.getValueAt(tbl_quiz_list.getSelectedRow(), 0).toString());
            UpdateQuizGUI updateQuizGUI = new UpdateQuizGUI(Quiz.getFetch(select_id));
            updateQuizGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {

                    loadQuizModel();
                }
            });

        });

        deleteQuizMenu.addActionListener(e -> {
            if (Helper.confirm("sure")) ;
            int select_id = Integer.parseInt(tbl_quiz_list.getValueAt(tbl_quiz_list.getSelectedRow(), 0).toString());
            if (Quiz.delete(select_id)) {
                Helper.showMessage("done");
                loadQuizModel();
            } else {
                Helper.showMessage("error");
            }
        });
        // quiz işlemleri sonu
    }

    private void loadCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0); //tüm rowları sildik.
        int i = 0;
        for (Course obj : Course.getList()) {
            i = 0;
            row_courseList[i++] = obj.getId();
            row_courseList[i++] = obj.getName();
            row_courseList[i++] = obj.getLang();
            row_courseList[i++] = obj.getPatika().getName();
            row_courseList[i++] = obj.getEducator().getName();
            mdl_course_list.addRow(row_courseList);

        }
    }

    private void loadPatikaModel() { //her veri aktardığımda modeli tmeizleyip tabloya geri aktaracağız.
        DefaultTableModel clearModel = (DefaultTableModel) tbl_patika_list.getModel();
        clearModel.setRowCount(0); //sıfırladdık.
        int i = 0;
        for (Patika obj : Patika.getList()) {
            i = 0;
            row_patika_list[i++] = obj.getId();
            row_patika_list[i++] = obj.getName();
            mdl_patika_list.addRow(row_patika_list);
        }

    }

    private void loadContentModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0); //tüm rowları sildik.
        int i = 0;
        for (Content obj : Content.getList()) {
            i = 0;
            row_contentList[i++] = obj.getId();
            row_contentList[i++] = obj.getName();
            row_contentList[i++] = obj.getDescription();
            row_contentList[i++] = obj.getLink();
            mdl_content_list.addRow(row_contentList);

        }
    }
    private void loadQuizModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_quiz_list.getModel();
        clearModel.setRowCount(0); //tüm rowları sildik.
        int i = 0;
        for (Quiz obj : Quiz.getList()) {
            i = 0;
            row_quiz_list[i++] = obj.getId();
            row_quiz_list[i++] = obj.getContent().getName();
            row_quiz_list[i++] = obj.getQuestion();
            mdl_quiz_list.addRow(row_quiz_list);

        }
    }


    public void loadUserModel() {
        DefaultTableModel clearModel = (DefaultTableModel) table_userlist.getModel(); //eklediğin satır listenin altında gelip eklemedeklerinin gelmemesine yarayan kod.
        clearModel.setRowCount(0);
        int i;

        for (User obj : User.getList()) { // veritabanından veri çekip yazdırmanın dinamik yolu.
            i = 0;
            row_userlist[i++] = obj.getId();
            row_userlist[i++] = obj.getName();
            row_userlist[i++] = obj.getUname();
            row_userlist[i++] = obj.getPassword();
            row_userlist[i++] = obj.getType();
            mdl_userlist.addRow(row_userlist);
        }
    }

    public void loadUserModel(ArrayList<User> list) {
        DefaultTableModel clearModel = (DefaultTableModel) table_userlist.getModel(); //eklediğin satır listenin altında gelip eklemedeklerinin gelmemesine yarayan kod.
        clearModel.setRowCount(0);

        for (User obj : list) { // veritabanından veri çekip yazdırmanın dinamik yolu.
            int i = 0;
            row_userlist[i++] = obj.getId();
            row_userlist[i++] = obj.getName();
            row_userlist[i++] = obj.getUname();
            row_userlist[i++] = obj.getPassword();
            row_userlist[i++] = obj.getType();
            mdl_userlist.addRow(row_userlist);
        }
    }

    public void loadPatikaCombo() {
        cmb_course_patika.removeAllItems();
        for (Patika obj : Patika.getList()) {
            cmb_course_patika.addItem(new Item(obj.getId(), obj.getName()));
        }
    }

    public void loadEducatorCombo() {
        cmb_course_user.removeAllItems();
        for (User obj : User.getList()) {
            if (obj.getType().equals("educator")) {
                cmb_course_user.addItem(new Item(obj.getId(), obj.getName()));
            }

        }

    }
}


