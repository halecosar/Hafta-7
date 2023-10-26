package patikadev.view;

import patikadev.Helper.Config;
import patikadev.Helper.DBconnector;
import patikadev.Helper.Helper;
import patikadev.model.Content;
import patikadev.model.Course;
import patikadev.model.Patika;
import patikadev.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StudentGUI extends JFrame {
    private JPanel wrapper;
    private JButton btn_logout;
    private JTabbedPane tabbed;
    private JPanel pnl_patika_list;
    private JPanel pnl_course_list;
    private JScrollPane scrl_patika_list;
    private JTable tbl_patika_list;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_list;
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private JPopupMenu patikaMenu;
    private Student student;

    public StudentGUI(Student student) {
        this.student=student;
        add(wrapper);
        setSize(400, 400);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        setResizable(false);

        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI login = new LoginGUI();
        });

        mdl_patika_list = new DefaultTableModel();
        Object[] col_patikaList = {"ID", "name"}; //colonları oluşturduk.
        mdl_patika_list.setColumnIdentifiers(col_patikaList);
        row_patika_list = new Object[col_patikaList.length]; //satırları oluşturduk
        loadPatikaModel();
        tbl_patika_list.setModel(mdl_patika_list);
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_patika_list.getTableHeader().setReorderingAllowed(false);

        tbl_patika_list.addMouseListener(new MouseAdapter() {
        });

        patikaMenu = new JPopupMenu();
        JMenuItem addMenu = new JMenuItem("Derse kayıt ol");
        patikaMenu.add(addMenu);

        tbl_patika_list.setComponentPopupMenu(patikaMenu);

        addMenu.addActionListener(e -> {
            int select_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(), 0).toString());
            CourseRegisterGUI courseRegisterGUI = new CourseRegisterGUI(Patika.getFetch(select_id));
            courseRegisterGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPatikaModel();
                }
            });

        });

        tbl_patika_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_patika_list.rowAtPoint(point);
                tbl_patika_list.setRowSelectionInterval(selected_row, selected_row); // sağ tıkladığın yer seçili geliyor.
            }
        });
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

}
