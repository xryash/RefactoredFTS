package kz.woopig.repositories;

import kz.woopig.entities.LocalFile;

import kz.woopig.providers.DataBaseProvider;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.List;

public interface FileRepository {

    RowMapper<LocalFile> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
        return new LocalFile(
                            resultSet.getInt("id"),
                            resultSet.getInt("host"),
                            resultSet.getString("targetFileName"),
                            resultSet.getString("submittedFileName"),
                            resultSet.getString("directory"));
    };


    List<LocalFile> getAll();
    LocalFile getById(int id);
    List<LocalFile> getFilesByHost(int host);
    int save(LocalFile entity);
    int delete(int id);


    class FileRepositoryImpl implements FileRepository {

        private final JdbcTemplate jdbcTemplate;

        public FileRepositoryImpl () {
            DataBaseProvider provider = DataBaseProvider.dataBaseProvider();
            JdbcTemplate jdbcTemplate = provider.jdbcTemplate();
            this.jdbcTemplate = jdbcTemplate;
        }

        public FileRepositoryImpl (JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }


        private final static String GET_ALL_QUERY = "SELECT * FROM meta_files";
        private final static String GET_ONE_ID_QUERY = "SELECT * FROM meta_files WHERE id = ?";
        private final static String GET_ALL_HOST_QUERY = "SELECT * FROM meta_files WHERE host = ?";
        private final static String SAVE_ONE_QUERY = "INSERT INTO meta_files (id, host, targetFileName, submittedFileName, directory) VALUES (?, ?, ?, ?, ? )";
        private final static String DELETE_ONE_QUERY = "DELETE  FROM meta_files WHERE id = ?";


        @Override
        public List<LocalFile> getAll() {
            try {
                return jdbcTemplate.query(GET_ALL_QUERY, ROW_MAPPER);
            }
            catch (Exception e) {
                return null;
            }
        }

        @Override
        public LocalFile getById(int id) {
            try {
                return jdbcTemplate.queryForObject(GET_ONE_ID_QUERY, ROW_MAPPER, id);
            }
            catch (Exception e) {
                return null;
            }
        }

        @Override
        public List<LocalFile> getFilesByHost(int host) {
            try {
                return jdbcTemplate.query(GET_ALL_HOST_QUERY, ROW_MAPPER, host);
            }
            catch (Exception e) {
                return null;
            }
        }

        @Override
        public int save(LocalFile entity) {
            try {
                return jdbcTemplate.update(SAVE_ONE_QUERY,
                        entity.getId(), entity.getHost(),
                        entity.getTargetFileName(), entity.getSubmittedFileName(),
                        entity.getDirectory());
            }
            catch (Exception e) {
                return 0;
            }
        }

        @Override
        public int delete(int id) {
            try {
                return jdbcTemplate.update(DELETE_ONE_QUERY, id);
            }
            catch (Exception e) {
                return 0;
            }

        }
    }
}


