package kz.woopig.handlers;

import kz.woopig.entities.Account;
import kz.woopig.entities.LocalFile;
import kz.woopig.exceptions.DownloadingFileException;
import kz.woopig.exceptions.ServiceError;
import kz.woopig.providers.RootPathProvider;
import kz.woopig.repositories.AccountRepository;
import kz.woopig.repositories.FileRepository;
import kz.woopig.requests.DownloadingFileRequest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public interface DownloadingFileHandler {

    StreamingOutput handle(DownloadingFileRequest request);


    class DownloadingFileHandlerImpl implements DownloadingFileHandler {


        private final RootPathProvider provider;
        private final FileRepository.FileRepositoryImpl fileRepository;
        private final AccountRepository.AccountRepositoryImpl accountRepository;


        public DownloadingFileHandlerImpl() {
            this.provider = new RootPathProvider();
            this.fileRepository = new FileRepository.FileRepositoryImpl();
            this.accountRepository = new AccountRepository.AccountRepositoryImpl();
        }

        @Override
        public StreamingOutput handle(DownloadingFileRequest request) {

            System.out.println("Запрос обрабатывается");

            if (request == null) {
                throw new DownloadingFileException(new ServiceError("missingData", "Missing File data"), String.format("Missing Parameter: request"));
            }

            String login = request.getLogin();

            if (login == null) {
                throw new DownloadingFileException(new ServiceError("missingData", "Missing File data"), String.format("Missing Parameter: request.login"));
            }


            int fileId = request.getId();
            LocalFile localFile = fileRepository.getById(fileId);

            if (localFile == null) {
                throw new DownloadingFileException(new ServiceError("missingData", "Missing File data"), String.format("Missing Parameter: request.localFile"));
            }
            Account account = accountRepository.getByLogin(login);

            if (account.getId() != localFile.getHost()) {
                throw new DownloadingFileException(new ServiceError("AccessError", "user isnt file owner"), String.format("Access Error"));
            }
            File file = new File(provider.getRootPath() + localFile.getTargetFileName());

            if (file == null) {
                throw new DownloadingFileException(new ServiceError("MissingFile", "Missing File data"), String.format("Missing File"));
            }
            StreamingOutput stream = prepareStreamingOutput(file);
            return stream;
        }

        private StreamingOutput prepareStreamingOutput(final File file) {
            StreamingOutput stream = new StreamingOutput() {
                public void write(OutputStream out) throws IOException, WebApplicationException {

                    FileInputStream is = new FileInputStream(file);
                    int available;
                    while ((available = is.available()) > 0) {
                        out.write(is.read());
                    }
                    is.close();
                }
            };
            return stream;

        }
    }
}
