package kz.woopig.handlers;

import kz.woopig.entities.Account;
import kz.woopig.entities.LocalFile;
import kz.woopig.exceptions.DownloadingFileException;
import kz.woopig.exceptions.ServiceError;
import kz.woopig.providers.RootPathProvider;
import kz.woopig.repositories.AccountRepository;
import kz.woopig.repositories.FileRepository;
import kz.woopig.requests.DownloadingFileRequest;

import javax.ws.rs.core.StreamingOutput;
import java.io.File;
import java.io.FileInputStream;

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
                throw new DownloadingFileException(new ServiceError("missingData", "Missing File data"), "Missing Parameter: request");
            }

            String login = request.getLogin();

            if (login == null) {
                throw new DownloadingFileException(new ServiceError("missingData", "Missing File data"), "Missing Parameter: request.login");
            }


            int fileId = request.getId();
            LocalFile localFile = fileRepository.getById(fileId);

            if (localFile == null) {
                throw new DownloadingFileException(new ServiceError("missingData", "Missing File data"), "Missing Parameter: request.localFile");
            }
            Account account = accountRepository.getByLogin(login);

            if (account.getId() != localFile.getHost()) {
                throw new DownloadingFileException(new ServiceError("AccessError", "user isnt file owner"), "Access Error");
            }
            File file = new File(provider.getRootPath() + localFile.getTargetFileName());

            return prepareStreamingOutput(file);
        }

        private StreamingOutput prepareStreamingOutput(final File file) {
            return out -> {

                FileInputStream is = new FileInputStream(file);
                int available;
                while ((available = is.available()) > 0) {
                    out.write(is.read());
                }
                is.close();
            };

        }
    }
}
