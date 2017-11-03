import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class RewiewPage extends Page{
    RewiewPage(String url){
        super(url);
    }
    public ArrayList parse_reviews_page() throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements reviews = doc.getElementsByClass("pp-review-i");
        ArrayList sentiments = new ArrayList(1);
        for (Element review:
                reviews) {
            ArrayList sentiment = new ArrayList(1);
            Element star = review.getElementsByClass("g-rating-stars-i").first();
            Elements texts = review.getElementsByClass("pp-review-text-i");
            if(star!=null)
                sentiment.add(Integer.valueOf(star.attr("content")));
            for (Element text:
                    texts) {
                sentiment.add(text.text());
            }

            sentiments.add(sentiment);
        }
        return sentiments;
    }
}