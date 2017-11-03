import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Category extends Page{
    Category(String url) {
        super(url);
    }
    public void parse_category() throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.getElementsByClass("g-i-tile-i-title");
        for (Element element:
                elements) {
            Rewiews rewiewPage = new Rewiews(element.getElementsByAttribute("href").first().attr("href") + "comments/");
            rewiewPage.parse_reviews();
        }
    }
}