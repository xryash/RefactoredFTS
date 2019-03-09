package kz.woopig.handlers;

import kz.woopig.entities.Account;
import kz.woopig.entities.LocalFile;
import kz.woopig.exceptions.DownloadingFileException;
import kz.woopig.exceptions.ServiceError;
import kz.woopig.repositories.AccountRepository;
import kz.woopig.repositories.FileRepository;

import java.util.List;

public interface FileListHandler {

    List<LocalFile> handle(String login);


    class FileListHandlerImpl implements FileListHandler {


        private final FileRepository.FileRepositoryImpl fileRepository;
        private final AccountRepository.AccountRepositoryImpl accountRepository;


        public FileListHandlerImpl() {
            this.fileRepository = new FileRepository.FileRepositoryImpl();
            this.accountRepository = new AccountRepository.AccountRepositoryImpl();
        }

        @Override
        public List<LocalFile> handle(String login) {
            System.out.println("Запрос обрабатывается");

            Account account = accountRepository.getByLogin(login);

            if(account == null)
                throw new DownloadingFileException(new ServiceError("NotExistingData", "Not existing account data"), "Account doesnt exist");

            return fileRepository.getFilesByHost(account.getId());
        }
    }

}
