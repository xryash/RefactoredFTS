package kz.woopig.providers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


public class DataBaseProvider {

    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;

    public DataBaseProvider(String dbUrl, String dbUsername, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    public static DataBaseProvider dataBaseProvider() {
        String url =  "jdbc:postgresql://localhost:5050/ft_db";
        String username = "krasivaya_devochka";
        String pass = "thebestpostgres";

        return new DataBaseProvider(url, username, pass);
    }



    public JdbcTemplate jdbcTemplate() {
        DataSource dataSource = new DriverManagerDataSource(dbUrl, dbUsername, dbPassword);
        return new JdbcTemplate(dataSource);
    }
}
