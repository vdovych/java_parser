import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Rewiews extends Page{
    Rewiews(String url){
        super(url);
    }
    public void parse_reviews() throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.getElementsByClass("paginator-catalog-l-link");
        int num;
        if (!elements.isEmpty())
            num = Integer.valueOf(elements.last().text());
        else
            num = 0;
        ArrayList sentiments = new ArrayList(1);
        for (int i = 0; i <= num; i++) {
            RewiewPage pg = new RewiewPage(url + String.format("page=%d",i));
            sentiments.addAll(pg.parse_reviews_page());
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
}