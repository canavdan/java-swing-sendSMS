
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class telefonkodlari {

    String iso33;
    private String name, nicename, iso, iso3, numcode, phonecode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNicename() {
        return nicename;
    }

    public void setNicename(String nicename) {
        this.nicename = nicename;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public String getNumcode() {
        return numcode;
    }

    public void setNumcode(String numcode) {
        this.numcode = numcode;
    }

    public String getPhonecode() {
        return phonecode;
    }

    public void setPhonecode(String phonecode) {
        this.phonecode = phonecode;
    }

    ArrayList<String> ulkeisim = new ArrayList<>();

    public ArrayList<String> doldur() {
        try {
            Connection con = null;
            Statement statement = null;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/sms?useSSL=false", "root", "admin");
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM kod");
            while (rs.next()) {
                ulkeisim.add(rs.getString("iso3"));
            }
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return ulkeisim;
    }

    public String karsiligibul(int a) {
        try {
            a = a + 1;

            Connection con = null;
            Statement statement = null;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/sms?useSSL=false", "root", "admin");
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM kod WHERE id= '" + Integer.toString(a) + "' ");
            while (rs.next()) {
                iso33 = rs.getString("phonecode");
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return iso33;
    }
}
