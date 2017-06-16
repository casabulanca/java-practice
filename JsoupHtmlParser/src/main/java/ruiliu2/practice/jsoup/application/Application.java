package ruiliu2.practice.jsoup.application;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 *
 * Created by ruiliu2 on 2017/4/26.
 */
public class Application {

    public static void main(String... args) {
        Document document = Jsoup.parse("");
        System.out.println(document.text());
    }
}
