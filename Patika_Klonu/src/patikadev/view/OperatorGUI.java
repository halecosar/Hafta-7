package patikadev.view;

import patikadev.Helper.Config;
import patikadev.Helper.Helper;
import patikadev.model.Course;
import patikadev.model.Operator;
import patikadev.model.Patika;
import patikadev.model.User;

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
    private final Operator operator;
    //bir tabloya dinamik olarak yapıları nasıl aktaracağız dbden çekip:
    private DefaultTableModel mdl_userlist; // bir modele ihtiyacımız var bu sebeple burayı oluşturduk. (yukarıda elle yaptk)
    private Object[] row_userlist;
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private JPopupMenu patikaMenu;
    private DefaultTableModel mdl_course_list;
    private Object[] row_courseList;

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
            }
        });
// ## UserLİst

        //PatikaList bailangıç
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
                }
            });

        });

        deleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure")) ;
            int select_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(), 0).toString());
            if (Patika.delete(select_id)) {
                Helper.showMessage("done");
                loadPatikaModel();
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
        // ## PatikaList

//CourseLİst başlangıç:
        mdl_course_list = new DefaultTableModel();
        Object[] col_courseList = {"ID", "Ders Adı", "Programlama Dili", "Dersin Patikası", "Eğitmen"}; //colonları oluşturduk.
        mdl_course_list.setColumnIdentifiers(col_courseList);
        row_courseList = new Object[col_courseList.length]; //satırları oluşturduk
        loadCourseList();


        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);


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
                        } else {
                            Helper.showMessage("error");
                        }
                    }
                }
            }
        });
        btn_user_sh.addActionListener(e -> {
            String name = fld_sr_username.getText();
            String uname = fld_sr_user_uname.getText();
            String type = cmb_sr_usertype.getSelectedItem().toString();
            String query = User.searchQuery(name, uname, type);

            ArrayList<User> searchingUser = User.SearchuserList(query);
            loadUserModel(searchingUser);

        });
        btn_logout.addActionListener(e -> {
            dispose();

        });
        btn_patika_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_patika_name)) {
                Helper.showMessage("fill");
            } else {
                if (Patika.add(fld_patika_name.getText())) {
                    Helper.showMessage("done");
                    loadPatikaModel();
                    fld_patika_name.setText(null);
                } else {
                    Helper.showMessage("error");
                }
            }

        });
    }

    private void loadCourseList() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0); //tüm rowları sildik.
        int i = 0;
        for (Course obj : Course.getList()) {
            i = 0;
            row_courseList[i++]= obj.getId();
            row_courseList[i++]=obj.getName();
            row_courseList[i++]=obj.getLang();
            row_courseList[i++]=obj.getPatika().getName();
            row_courseList[i++]=obj.getEducator().getName();
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

    public static void main(String[] args) {
        Helper.setLayout();
        Operator op = new Operator();
        op.setId(1);
        op.setName("Hale Coşar");
        op.setPassword("1234");
        op.setUname("hale");
        op.setType("operator");
        OperatorGUI opGUI = new OperatorGUI(op); // hangi sınıftan nesne ürettiysek GUI'ye onu yolladık.


    }
}
