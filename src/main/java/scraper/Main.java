package scraper;

public class Main {
    public static void main(String[] args) {
        CacheScraper defaultParser = new CacheScraper();
        Home home = defaultParser.parse("https://www.newhomesource.com/plan/encore-wlh-taylor-morrison-austin-tx/1771471");
        System.out.println(home);
    }
}
