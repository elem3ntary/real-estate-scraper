package scraper;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter @Builder @ToString
public class Home {
    private String url;
    private int price;
    private int bed;
    private double bath;
    private int garage;
}
