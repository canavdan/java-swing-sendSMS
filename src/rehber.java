
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class rehber {

    private String isim, soyisim, numara;

    public rehber(String isim, String soyisim, String numara) {
        this.isim = isim;
        this.soyisim = soyisim;
        this.numara = numara;
    }

    @Override
    public String toString() {
        return isim + "     " + soyisim + "      " + numara;

    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getSoyisim() {
        return soyisim;
    }

    public void setSoyisim(String soyisim) {
        this.soyisim = soyisim;
    }

    public String getNumara() {
        return numara;
    }

    public void setNumara(String numara) {
        this.numara = numara;
    }

}
