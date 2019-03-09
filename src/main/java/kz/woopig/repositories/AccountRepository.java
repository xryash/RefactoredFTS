package kz.woopig.repositories;

import kz.woopig.entities.Account;
import kz.woopig.providers.DataBaseProvider;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;

public interface AccountRepository {

    RowMapper<Account> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
        return new Account(
                resultSet.getInt("id"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                resultSet.getString("role"));
    };

    Account getById(int id);
    Account getByLogin(String login);
    int save(Account entity);
    Account getByLoginAndPassword(String login, String password);

    class AccountRepositoryImpl implements AccountRepository {

        private final JdbcTemplate jdbcTemplate;

        public AccountRepositoryImpl() {
            DataBaseProvider provider = DataBaseProvider.dataBaseProvider();
            JdbcTemplate jdbcTemplate = provider.jdbcTemplate();
            this.jdbcTemplate = jdbcTemplate;
        }

        public AccountRepositoryImpl(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }


        private final static String GET_ONE_ID_QUERY = "SELECT * FROM accounts WHERE id = ?";
        private final static String GET_ONE_LOGIN_QUERY = "SELECT * FROM accounts WHERE login = ?";
        private final static String SAVE_ONE_QUERY = "INSERT INTO accounts (id, login, password, role) VALUES (?, ?, ?, ? )";
        private final static String GET_ONE_LOGIN_PASS_QUERY = "SELECT * FROM accounts WHERE login = ? AND password = ?";


        @Override
        public Account getById(int id) {
            try {
                return jdbcTemplate.queryForObject(GET_ONE_ID_QUERY, ROW_MAPPER, id);
            }
            catch (Exception e) {
                return null;
            }
        }

        @Override
        public Account getByLogin(String login) {
            try {
                return jdbcTemplate.queryForObject(GET_ONE_LOGIN_QUERY, ROW_MAPPER, login);
            }
            catch (Exception e) {
                return null;
            }
        }

        @Override
        public int save(Account entity) {
            try {
                return jdbcTemplate.update(SAVE_ONE_QUERY,
                        entity.getId(), entity.getLogin(),
                        entity.getPassword(), entity.getRole());
            }
            catch (Exception e) {
                return 0;
            }
        }

        @Override
        public Account getByLoginAndPassword(String login, String password) {
            try {
                return jdbcTemplate.queryForObject(GET_ONE_LOGIN_PASS_QUERY, ROW_MAPPER, login, password);
            }
            catch (Exception e) {
                return null;
            }

        }
    }

}
