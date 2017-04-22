/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class database {

    private String username, pass, datapass;
    Connection con = null;
    Statement statement = null;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean girisbilgilerikontrol() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/sms?useSSL=false", "root", "admin");
            statement = con.createStatement();
            ResultSet rs;
            rs = statement.executeQuery("SELECT * FROM giris where username='" + username + "' ;");

            while (rs.next()) {
                datapass = rs.getString("pass");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        if (datapass.equals(pass)) {
            return true;
        }
        return false;
    }

    public void secilenveriyisil(String num, String databaseicingiris2) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/sms?useSSL=false", "root", "admin");
            statement = con.createStatement();
            if (statement.executeUpdate("DELETE FROM " + databaseicingiris2 + " where numara='" + num + "'") != 1) {
                JOptionPane.showMessageDialog(null, "Silinemedi.");
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void databasemesajiekle(String gidennum, String mesaj, String tarih, String tabloadi) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/sms?useSSL=false", "root", "admin");
            statement = con.createStatement();
            PreparedStatement preparedStmt = con.prepareStatement("insert into " + tabloadi + "(kime,icerik,saat) VALUES (?,?,?)");
            preparedStmt.setString(1, gidennum);
            preparedStmt.setString(2, mesaj);
            preparedStmt.setString(3, tarih);
            int kontroleklendimi = preparedStmt.executeUpdate();
            if (kontroleklendimi == 1) {
                //Tabloyu sil ve databasein güncel halini tekrar tabloya koyar.
                JOptionPane.showMessageDialog(null, "Gönderildi.");
            } else {
                JOptionPane.showMessageDialog(null, "Gönderilemedi.");
            }
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void secilenkaydiduzenle(String databaseismi, String secilennum, String yeniisim, String yenisoyisim, String yeninum) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/sms?useSSL=false", "root", "admin");
            statement = con.createStatement();
 PreparedStatement preparedStmt = con.prepareStatement("UPDATE " + databaseismi + " SET isim=?,soyisim=?,numara=? WHERE numara=?");

            preparedStmt.setString(1, yeniisim);
            preparedStmt.setString(2, yenisoyisim);
            preparedStmt.setString(3, yeninum);
            preparedStmt.setString(4, secilennum);
            if (preparedStmt.executeUpdate() != 1) {
                JOptionPane.showMessageDialog(null, "Güncellenemedi.");
            } else {
                JOptionPane.showMessageDialog(null, "Güncellendi.");
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void databasemesajsil(String tabloadi, String tarih) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/sms?useSSL=false", "root", "admin");
            statement = con.createStatement();
            if (statement.executeUpdate("DELETE FROM " + tabloadi + " where saat='" + tarih + "'") != 1) {
                JOptionPane.showMessageDialog(null, "Silinemedi.");
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public boolean giriskaydiekle(String isim, String soyisim, String numara, String sifre, String mail) {
        if (kontroletnum(numara, mail) && kontroletmail(mail)) {
            String tabloadi = numara.replace("+", "");//Numaradan + yı atar
            StringBuilder str = new StringBuilder(tabloadi);
            StringBuilder str2 = new StringBuilder(tabloadi);
            str.append("r");//Numaranın sonuna r ekler.Rehber tablosu için
            str2.append("m");//Numaranın sonuna m ekler.Mesaj geçmişi tablosu için
            yenidatabasetablosu(str.toString(), str2.toString());//Yeni tablo oluşturur
            
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost/sms?useSSL=false", "root", "admin");
                statement = con.createStatement();
                PreparedStatement preparedStmt = con.prepareStatement("insert into giris (username,pass,maill,isim,soyisim) VALUES (?,?,?,?,?)");

                preparedStmt.setString(1, numara);
                preparedStmt.setString(2, sifre);
                preparedStmt.setString(3, mail);
                preparedStmt.setString(4, isim);
                preparedStmt.setString(5, soyisim);

                if (preparedStmt.executeUpdate() != 1) {
                    con.close();
                    JOptionPane.showMessageDialog(null, "Eklenemedi.");
                    return false;
                } else {
                    con.close();
                    JOptionPane.showMessageDialog(null, "Kayıt Başarılı.");
                    return true;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            }

        } else if (kontroletnum(numara, mail) && !kontroletmail(mail)) {
            JOptionPane.showMessageDialog(null, "Mail mevcut");
            return false;
        } else if (!kontroletnum(numara, mail) && kontroletmail(mail)) {
            JOptionPane.showMessageDialog(null, "Numara mevcut");

            return false;

        } else if (!kontroletnum(numara, mail) && !kontroletmail(mail)) {
            JOptionPane.showMessageDialog(null, "Mail ve numara mevcut");
            return false;
        }
        return false;
    }

    public boolean kontroletnum(String numara, String mail) {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/sms?useSSL=false", "root", "admin");
            statement = con.createStatement();
            PreparedStatement preparedStmt = con.prepareStatement("SELECT username,maill FROM giris WHERE username=?");

            preparedStmt.setString(1, numara);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                     return false;                       
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return true;
    }

    public boolean kontroletmail(String mail) {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/sms?useSSL=false", "root", "admin");
            statement = con.createStatement();
            PreparedStatement preparedStmt = con.prepareStatement("SELECT maill FROM giris WHERE maill=?");

            preparedStmt.setString(1, mail);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                return false;
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return true;
    }

    public void yenidatabasetablosu(String tabloadi, String tabloadi2) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/sms?useSSL=false", "root", "admin");
            statement = con.createStatement();

            String query = "CREATE TABLE `sms`.`" + tabloadi2 + "` (\n"
                    + "  `id` INT NOT NULL AUTO_INCREMENT,\n"
                    + "  `kime` VARCHAR(45) NULL,\n"
                    + "  `icerik` VARCHAR(245) NOT NULL,\n"
                    + "  `saat` VARCHAR(45) NULL,\n"
                    + "  PRIMARY KEY (`id`))";
            statement.executeUpdate(query);
            String query2 = "CREATE TABLE `sms`.`" + tabloadi + "` (\n"
                    + "  `id` INT NOT NULL AUTO_INCREMENT,\n"
                    + "  `isim` VARCHAR(45) NOT NULL,\n"
                    + "  `soyisim` VARCHAR(45) NULL,\n"
                    + "  `numara` VARCHAR(45) NOT NULL,\n"
                    + "  PRIMARY KEY (`id`));";
            statement.executeUpdate(query2);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public int sifredegistir(String eski, String yeni, String kullaniciadi) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/sms?useSSL=false", "root", "admin");
            statement = con.createStatement();
            PreparedStatement preparedStmt = con.prepareStatement("UPDATE giris SET pass=? WHERE username=? AND pass=?");

            preparedStmt.setString(1, yeni);
            preparedStmt.setString(2, kullaniciadi);
            preparedStmt.setString(3, eski);

            int a = preparedStmt.executeUpdate();

            con.close();
            return a;

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return 0;
    }
}
