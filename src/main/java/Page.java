import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Page {
    String url;
    public Page(String url){
        this.url = url;
    }
    public void parse_category() throws IOException {
        File dir = new File("data");
        dir.mkdir();
        Document doc = Jsoup.connect(url).get();
        int num = Integer.valueOf(doc.getElementsByClass("paginator-catalog-l-link").last().text());
        for(int i=1;i<=num;i++){
            Category category = new Category(url + String.format("page=%d", i));
            category.parse_category();
        }
    }
}

