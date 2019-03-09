package kz.woopig;

import kz.woopig.entities.LocalFile;
import kz.woopig.repositories.FileRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;

public class FileRepositoryTest {

    private EmbeddedDatabase embeddedDatabase;


    private FileRepository fileRepository;

    @Before
    public void connect() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addScripts("scheme.sql", "data.sql")
                .setType(EmbeddedDatabaseType.H2)
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        fileRepository = new FileRepository.FileRepositoryImpl(jdbcTemplate);
    }

    @After
    public void disconnect() {
        embeddedDatabase.shutdown();
    }


    @Test
    public void getAll() {
        Assert.assertNotNull(fileRepository.getAll());
        Assert.assertEquals(3, fileRepository.getAll().size());
    }

    @Test
    public void getById() {
        Assert.assertNotNull(fileRepository.getById(25));
        Assert.assertNull(fileRepository.getById(55));
        Assert.assertEquals("lab1.txt", fileRepository.getById(25).getSubmittedFileName());
    }

    @Test
    public void getFilesByHost() {
        Assert.assertNotNull(fileRepository.getFilesByHost(2));
        Assert.assertNull(fileRepository.getById(55));
        Assert.assertEquals(2, fileRepository.getFilesByHost(2).size());
    }

    @Test
    public void save() {
        LocalFile file = new LocalFile(44, 3, "fijopFJOJFEFEJ[JO", "lab24.txt", "/labs/");
        Assert.assertEquals(1, fileRepository.save(file));
        LocalFile injected_file =  new LocalFile(44, 3, null, "lab24.txt", "/labs/");
        Assert.assertEquals(0, fileRepository.save(injected_file));
    }

    @Test
    public void delete() {
        Assert.assertEquals(1, fileRepository.delete(22));
        Assert.assertEquals(0, fileRepository.delete(111));
    }
}
