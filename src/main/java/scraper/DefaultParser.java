package scraper;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class DefaultParser implements Scraper{
    private static final String PRICE_TAG = ".detail__price-from";
    private static final String BED_TAG = ".nhs_BedsInfo";
    private static final String BATH_TAG = ".nhs_BathsInfo";
    private static final String GARAGE_TAG = ".nhs_GarageInfo";
    private static final String defaultStringCleaner = "[^0-9.,]";

    @Override @SneakyThrows
    public Home parse(String url) {
        Document doc = Jsoup.connect(url).get();
        int price = getPrice(doc);
        int bedCount = getBedCount(doc);
        double bathCount = getBathCount(doc);
        int garageCount = getGarageCount(doc);

        Home home = Home.builder().price(price).garage(garageCount).bath(bathCount).bed(bedCount).url(url).build();

        return home;
    }

    private int getPrice(Document doc) {
        Element priceTag = doc.selectFirst(PRICE_TAG).nextElementSibling();
        String priceText = getNormalizedElementText(priceTag, "[^0-9]");
        return Integer.parseInt(priceText);
    }

    private int getBedCount(Document doc) {
        Element priceTag = doc.selectFirst(BED_TAG);
        String priceText = getNormalizedElementText(priceTag, defaultStringCleaner);
        return Integer.parseInt(priceText);
    }

    private double getBathCount(Document doc) {
        Element priceTag = doc.selectFirst(BATH_TAG);
        String priceText = getNormalizedElementText(priceTag, defaultStringCleaner);
        return Double.parseDouble(priceText);
    }

    private int getGarageCount(Document doc) {
        Element priceTag = doc.selectFirst(GARAGE_TAG);
        String priceText = getNormalizedElementText(priceTag, defaultStringCleaner);
        return Integer.parseInt(priceText);
    }

    private String getNormalizedElementText(Element element, String regex) {
        return element.text().replaceAll(regex, "");
    }
}
