package scraper;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CacheScraper implements Scraper{
    @Override @SneakyThrows
    public Home parse(String url) {

        Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(String.format("select count(*) as count from homes where url='%s'", url));
        int count  = rs.getInt("count");
        if (count == 0) {
            DefaultParser defaultParser = new DefaultParser();
            Home home = defaultParser.parse(url);
            insertHomeIntoDB(statement, home);
            return  home;
        } else {
            Home home = selectHomeFromDB(statement, url);
            return home;
        }
    }

    @SneakyThrows
    private int insertHomeIntoDB(Statement statement, Home home) {
        String queryString = String.format("insert into homes(url, price, beds, bathes, garage) values('%s', %s, %s,%s,%s)",
                home.getUrl(), home.getPrice(), home.getBed(), home.getBath(), home.getGarage());
        System.out.println(queryString);
        return statement.executeUpdate(queryString);
    }

    @SneakyThrows
    private Home selectHomeFromDB(Statement statement, String url) {
        ResultSet rs =  statement.executeQuery(String.format("select * from homes where url='%s'", url));
        return Home.builder().url(url).price(rs.getInt("price"))
                .bath(rs.getDouble("bathes"))
                .bed(rs.getInt("beds"))
                .garage(rs.getInt("garage"))
                .build();
    }
}
