package kz.woopig;

import kz.woopig.entities.Account;
import kz.woopig.repositories.AccountRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class AccountRepositoryTest  {


    private EmbeddedDatabase embeddedDatabase;

    private AccountRepository accountRepository;

    @Before
    public void connect() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addScripts("scheme.sql", "data.sql")
                .setType(EmbeddedDatabaseType.H2)
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        accountRepository = new AccountRepository.AccountRepositoryImpl(jdbcTemplate);
    }

    @After
    public void disconnect() {
        embeddedDatabase.shutdown();
    }

    @Test
    public void getById() {
        Assert.assertNotNull(accountRepository.getById(2));
        Assert.assertNull(accountRepository.getById(55));
        Assert.assertEquals("vanya", accountRepository.getById(2).getLogin());
    }

    @Test
    public void getByLogin() {
        Assert.assertNotNull(accountRepository.getByLogin("vanya"));
        Assert.assertNull(accountRepository.getByLogin("nonexisted user"));
        Assert.assertEquals(2, accountRepository.getByLogin("vanya").getId());
    }

    @Test
    public void save() {
        Account account = new Account(10, "ignat", "ignatspass", "Role");
        Assert.assertEquals(1, accountRepository.save(account));
        Account injected_account = new Account(10, null, "ignatspass", null);
        Assert.assertEquals(0, accountRepository.save(injected_account));
    }

    @Test
    public void getByLoginAndPassword() {
        Assert.assertNotNull(accountRepository.getByLoginAndPassword("igoresha", "topec12"));
        Assert.assertNull(accountRepository.getByLoginAndPassword("nonexisted user", "pass"));
        Assert.assertEquals("USER", accountRepository.getByLoginAndPassword("igoresha", "topec12").getRole());
    }
}
