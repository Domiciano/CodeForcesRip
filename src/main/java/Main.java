import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Main {

    private static char ABC = 'A';

    public static void main(String[] args) throws IOException {
        FileInputStream is = new FileInputStream("problems.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String url = "";
        while ((url = reader.readLine()) != null) {
            String html = getRequest(url);
            FileOutputStream fos = new FileOutputStream(ABC+".html");


            //
            Document doc = Jsoup.parse(html);
            doc.getElementById("sidebar").remove();
            doc.getElementById("header").remove();
            doc.getElementById("footer").remove();
            doc.getElementsByClass("second-level-menu").remove();
            doc.getElementsByClass("menu-box").remove();

            fos.write(doc.outerHtml().getBytes(StandardCharsets.UTF_8));
            fos.close();
            //


        }
    }

    public static String getRequest(String site) throws IOException {
        URL url = new URL(site);
        HttpsURLConnection http = (HttpsURLConnection) url.openConnection();
        InputStream is = http.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        transfer(is, baos);
        return baos.toString();
    }

    public static void transfer(InputStream is, OutputStream os) throws IOException {
        byte[] buff = new byte[1024];
        int readings = 0;
        while ((readings = is.read(buff)) != -1) {
            os.write(buff, 0, readings);
        }
        is.close();
        os.close();
    }

}
