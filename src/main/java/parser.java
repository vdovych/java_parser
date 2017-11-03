import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class parser{
    public static void main(String[] args) throws IOException {
        String url = "https://hard.rozetka.com.ua/ua/computers/c80095/filter/";
        Page p = new Page(url);
        p.parse_category();
    }
}