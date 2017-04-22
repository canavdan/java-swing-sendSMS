
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class smsapi {

    private String kullanici, pass, gonderen, alici, ileti;

    public String getKullanici() {
        return kullanici;
    }

    public void setKullanici(String kullanici) {
        this.kullanici = kullanici;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getGonderen() {
        return gonderen;
    }

    public void setGonderen(String gonderen) {
        this.gonderen = gonderen;
    }

    public String getAlici() {
        return alici;
    }

    public void setAlici(String alici) {
        this.alici = alici;
    }

    public String getIleti() {
        return ileti;
    }

    public void setIleti(String ileti) {
        this.ileti = ileti;
    }

    public void gonder() {
        String xml = "<request>";
        xml += "<authentication>";
        xml += "<username>" + kullanici + "</username>";
        xml += "<password>" + pass + "</password>";
        xml += "</authentication>";
        xml += "<order>";
        xml += "<sender>ILETI MRKZI</sender>";
        xml += "<sendDateTime></sendDateTime>";
        xml += "<message>";
        xml += "        <text><![CDATA[" + ileti + "]]></text>";
        xml += "        <receipents>";
        xml += "                <number>" + alici + "</number>";
        xml += "        </receipents>";
        xml += "</message>";
        xml += "</order>";
        xml += "</request>";
        excutePost("http://api.iletimerkezi.com/v1/send-sms", xml);
    }
    public static String excutePost(String targetURL, String xml) {

        String urlParameters = null;
        try {
            urlParameters = "data=" + URLEncoder.encode(xml, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "UTF-8");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            //İstek yollar
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }
}
