package patikadev.view;

import patikadev.Helper.Config;
import patikadev.Helper.Helper;
import patikadev.model.Operator;
import patikadev.model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tab_operator;
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
    private final Operator operator;
    //bir tabloya dinamik olarak yapıları nasıl aktaracağız dbden çekip:
    private DefaultTableModel mdl_userlist; // bir modele ihtiyacımız var bu sebeple burayı oluşturduk. (yukarıda elle yaptk)
    private Object[] row_userlist;

    public OperatorGUI(Operator operator) {
        this.operator = operator;

        add(wrapper);
        setSize(1200, 600);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        lbl_welcome.setText("Hoşgeldin : " + operator.getName());

        //ModelUserList
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

                if (User.update(user_id,user_name,user_uname,user_pass,user_type)){
                    Helper.showMessage("done");

                }
                loadUserModel();
            }
        });

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
                    int user_id = Integer.parseInt(fld_user_id.getText());
                    if (User.delete(user_id)) {
                        Helper.showMessage("done");
                        loadUserModel();
                    } else {
                        Helper.showMessage("error");
                    }
                }
            }
        });
        btn_user_sh.addActionListener(e -> {
            String name = fld_sr_username.getText();
            String uname = fld_sr_user_uname.getText();
            String type = cmb_sr_usertype.getSelectedItem().toString();
            String query = User.searchQuery(name,uname,type);

            ArrayList<User> searchingUser =User.SearchuserList(query);
            loadUserModel(searchingUser);

        });
        btn_logout.addActionListener(e -> {
            dispose();

        });
    }

    public void loadUserModel() {
        DefaultTableModel clearModel = (DefaultTableModel) table_userlist.getModel(); //eklediğin satır listenin altında gelip eklemedeklerinin gelmemesine yarayan kod.
        clearModel.setRowCount(0);

        for (User obj : User.getList()) { // veritabanından veri çekip yazdırmanın dinamik yolu.
            int i = 0;
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
