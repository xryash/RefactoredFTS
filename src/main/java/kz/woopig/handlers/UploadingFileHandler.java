package kz.woopig.handlers;

import kz.woopig.providers.RootPathProvider;
import kz.woopig.entities.Account;
import kz.woopig.entities.HttpFile;
import kz.woopig.entities.LocalFile;
import kz.woopig.exceptions.UploadingFileException;
import kz.woopig.exceptions.ServiceError;
import kz.woopig.repositories.AccountRepository;
import kz.woopig.repositories.FileRepository;
import kz.woopig.requests.UploadingFileRequest;
import kz.woopig.responses.UploadingFileResponse;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public interface UploadingFileHandler {

    UploadingFileResponse handle(UploadingFileRequest request);

    class UploadingFileHandlerImpl implements UploadingFileHandler {


        private final RootPathProvider provider;
        private final FileRepository.FileRepositoryImpl fileRepository;
        private final AccountRepository.AccountRepositoryImpl accountRepository;


        public UploadingFileHandlerImpl() {
            this.provider = new RootPathProvider();
            this.fileRepository = new FileRepository.FileRepositoryImpl();
            this.accountRepository = new AccountRepository.AccountRepositoryImpl();
        }


        @Override
        public UploadingFileResponse handle(UploadingFileRequest request) {
            System.out.println("Запрос обрабатывается");

            if (request == null) {
                throw new UploadingFileException(new ServiceError("missingFile", "Missing File data request"), "Missing Parameter: request");
            }

            String login = request.getLogin();

            if (login == null) {
                throw new UploadingFileException(new ServiceError("missingFile", "Missing File data request.login"), "Missing Parameter: request.login");
            }

            final HttpFile httpFile = request.getHttpFile();

            verifyHttpFile(httpFile);

            final String targetFileName = UUID.randomUUID().toString();

            Account account = accountRepository.getByLogin(login);
            int host = account.getId();

            LocalFile localFile = new LocalFile(host, targetFileName, httpFile.getSubmittedFileName(), httpFile.getDirectory());

            if (fileRepository.save(localFile) == 0) {
                throw new UploadingFileException(new ServiceError("savingFileError", "Error saving"), "Saving File failed");
            }

            writeFile(httpFile.getStream(), targetFileName);

            return new UploadingFileResponse(targetFileName);
        }


        private void verifyHttpFile(HttpFile httpFile) {

            if (httpFile == null) {
                throw new UploadingFileException(new ServiceError("missingFile", "Missing File data request.httpFile"), "Missing Parameter: request.httpFile");
            }

            String directory = httpFile.getDirectory();
            long size = httpFile.getSize();
            String submittedFileName = httpFile.getSubmittedFileName();
            InputStream stream = httpFile.getStream();

            if (directory == null) {
                throw new UploadingFileException(new ServiceError("missingFile", "Missing File data httpFile.directory"), "Missing Parameter: httpFile.directory");
            }

            if (size <= 0) {
                throw new UploadingFileException(new ServiceError("missingFile", "Missing File data httpFile.size"), "Missing Parameter: httpFile.size");
            }

            if (submittedFileName == null) {
                throw new UploadingFileException(new ServiceError("missingFile", "Missing File data httpFile.submittedFileName"), "Missing Parameter: httpFile.submittedFileName");
            }

            if (stream == null) {
                throw new UploadingFileException(new ServiceError("missingFile", "Missing File data httpFile.stream"), "Missing Parameter: httpFile.stream");
            }
        }


        private void writeFile(InputStream stream, String fileName) {
            try {

                Files.copy(stream, Paths.get(provider.getRootPath(), fileName));
                //Files.copy(stream, target, options)

            } catch (Exception e) {
                throw new UploadingFileException(new ServiceError("storingFileError", "Error writing file"), "Writing File failed");
            }
        }
    }

}
