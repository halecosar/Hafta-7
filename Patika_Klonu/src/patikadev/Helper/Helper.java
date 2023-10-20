package patikadev.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper { //yardımcı sınıf, içinde static kodlar olacak.
    public static void setLayout() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
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

    }

    public static int screenCenterPoint(String eksen, Dimension size) {
        int point;
        switch (eksen) {
            case "x":
                point = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
                break;
            case "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
                break;
            default:
                point = 0;
        }
        return point;
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();

    }

    public static void showMessage(String str) {
        optionPageTR();
        String msg;
        String title;
        switch (str) {
            case "fill":
                msg = "Lütfen Tüm Alanları Doldurunuz! ";
                title= "Hata!";
                break;
            case "done":
                msg="İşlem Başarılı!";
                title = "Sonuç";
                break;
            case "error":
                msg="Bir Hata Oluştu!";
                title = "Hata!";
                break;
            default:
                msg = str;
                title="Mesaj!";

        }
       JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }
    public static void optionPageTR(){
        UIManager.put("OptionPane.okButtonText","Tamam");

    }
}
