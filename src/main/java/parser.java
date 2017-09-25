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
        parse_category(url);
    }
    private static void parse_category(String url) throws IOException {
        File dir = new File("data");
        dir.mkdir();
        Document doc = Jsoup.connect(url).get();
        int num = Integer.valueOf(doc.getElementsByClass("paginator-catalog-l-link").last().text());
        for(int i=1;i<=num;i++){
            String pg = url + String.format("page=%d", i);
            parse_category_page(pg);
        }
    }
    private static void parse_category_page(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.getElementsByClass("g-i-tile-i-title");
        for (Element element:
                elements) {
            parse_reviews(element.getElementsByAttribute("href").first().attr("href") + "comments/");
        }

    }
    private static void parse_reviews(String url) throws IOException{
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.getElementsByClass("paginator-catalog-l-link");
        int num;
        if (!elements.isEmpty())
            num = Integer.valueOf(elements.last().text());
        else
            num = 0;
        ArrayList sentiments = new ArrayList(1);
        for (int i = 0; i <= num; i++) {
            String pg = url + String.format("page=%d",i);
            sentiments.addAll(parse_reviews_page(pg));
        }
        String filename = "data/" + url.split("/")[4] + ".csv";
        FileWriter writer = new FileWriter(filename);
        for (int i = 0; i < sentiments.size(); i++) {
            ArrayList sentiment = (ArrayList) sentiments.get(i);
            for (int j = 0; j < sentiment.size(); j++) {
                if(j == 0){
                    if (sentiment.get(j).getClass()==Integer.class) {
                        writer.write(String.valueOf((int) sentiment.get(j)));
                    }
                    else {
                        writer.write("0");
                        writer.write(",");
                        writer.write((String)sentiment.get(j));
                    }
                }
                else
                    writer.write((String)sentiment.get(j));
                writer.write(",");
            }
            writer.write("\n\n");
        }
        writer.flush();
        writer.close();
        System.out.println(String.format("%d rewiews written about %s", sentiments.size(),url.split("/")[4]));
    }
    private static ArrayList parse_reviews_page(String url) throws IOException{
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