package com.html.parser.url;

import com.html.parser.Node;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Navidom implements Parsable {

    public static final String SITE = "http://novidom.ba";

    private final String site;

    public Navidom(String site) {
        this.site = site;
    }

    @Override
    public List<Node> parse() throws IOException {
        List<Node> nodes = new ArrayList<>();
        Document doc = Jsoup.connect(site).get();

        Elements row = doc.getElementsByClass("col-xs-12 idk_realestate_info");
        for (Element element : row) {
            String price = element.getElementsByClass("idk_realestate_info-price").get(0).text();
            String url = element.select("a").attr("href");
            nodes.add(new Node(price, url));
        }

        return nodes;
    }

}
