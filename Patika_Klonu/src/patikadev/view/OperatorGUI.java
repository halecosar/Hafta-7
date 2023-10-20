package patikadev.view;

import patikadev.Helper.Config;
import patikadev.Helper.Helper;
import patikadev.model.Operator;
import patikadev.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
    private final Operator operator;
    //bir tabloya dinamik olarak yapıları nasıl aktaracağız dbden çekip:
    private DefaultTableModel mdl_userlist; // bir modele ihtiyacımız var bu sebeple burayı oluşturduk. (yukarıda elle yaptk)
    private Object[] row_userlist;

    public OperatorGUI(Operator operator) {
        this.operator = operator;

        add(wrapper);
        setSize(1000, 500);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        lbl_welcome.setText("Hoşgeldin : " + operator.getName());

        //ModelUserList
        mdl_userlist = new DefaultTableModel();
        Object[] col_userlist = {"ID", "Ad Soyad", "Kullanıcı Adı", "Şifre", "Üyelik Tipi"}; // modelimin kolon isimlerini obje veritipinde tanımladım array oluşturdum.
        mdl_userlist.setColumnIdentifiers(col_userlist); // süslü parantez içindekiler tablomdaki başlıklar.
        row_userlist = new Object[col_userlist.length];

        // Object [] firstRow = {1,"Hale Coşar","hale","123","operator"}; //manuel yol.
        // mdl_userlist.addRow(firstRow);

        loadUserModel();
        table_userlist.setModel(mdl_userlist);
        table_userlist.getTableHeader().setReorderingAllowed(false);
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
