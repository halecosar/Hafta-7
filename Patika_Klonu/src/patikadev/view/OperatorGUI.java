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
    private JTextField field_username;
    private JTextField textField1;
    private JPasswordField passwordField1;
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

       // Object [] firstRow = {1,"Hale Coşar","hale","123","operator"}; //manuel yol.
       // mdl_userlist.addRow(firstRow);

        for (User obj : User.getList()){ // veritabanından veri çekip yazdırmanın dinamik yolu.
            Object [] row = new Object[col_userlist.length]; //obje array  boyutu tablomun boyutundan büyük olmamalı.
            row[0]= obj.getId();
            row[1]= obj.getName();
            row[2]= obj.getUname();
            row[3]= obj.getPassword();
            row[4]= obj.getType();
            mdl_userlist.addRow(row);
        }
        table_userlist.setModel(mdl_userlist);
        table_userlist.getTableHeader().setReorderingAllowed(false);
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
