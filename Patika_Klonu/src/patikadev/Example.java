package patikadev;

import javax.swing.*;
import java.awt.*;

public class Example extends JFrame {
    private JPanel wrapper;
    private JPanel wtop;
    private JTextField fld_username;
    private JTextField fld_password;
    private JButton girişYapButton;

    public Example() {
        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            if ("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        add(wrapper);
        setSize(400, 300);
        setTitle("Uygulama Adı");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //çarpıya basınca program durması için.
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height / 2);
        setLocation(x,y);
        setVisible(true);
        girişYapButton.addActionListener(e -> {
           if (fld_username.getText().length()==0 || fld_password.getText().length()==0){
               JOptionPane.showMessageDialog(null,"Lütfen Tüm Alanları Doldurun!", "Hata",JOptionPane.INFORMATION_MESSAGE);
           }

        });

    }


}
