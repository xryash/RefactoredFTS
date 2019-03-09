package kz.woopig.handlers;

import kz.woopig.entities.Account;
import kz.woopig.exceptions.AccountException;
import kz.woopig.exceptions.ServiceError;
import kz.woopig.repositories.AccountRepository;
import kz.woopig.requests.AccountRequest;
import kz.woopig.responses.AccountResponse;
import org.glassfish.jersey.internal.util.Base64;
import java.util.StringTokenizer;

public interface AccountHandler {

    AccountResponse handle(AccountRequest request);

    class AccountHandlerImpl implements AccountHandler {

        private final AccountRepository.AccountRepositoryImpl accountRepository;
        private static final String AUTHENTICATION_SCHEME = "Basic";


        public AccountHandlerImpl() {
            this.accountRepository = new AccountRepository.AccountRepositoryImpl();
        }

        @Override
        public AccountResponse handle(AccountRequest request) {
            System.out.println("Запрос обрабатывается");

            if(request == null) {
                throw new AccountException(new ServiceError("missingAccountError", "Missing Account data"), "Missing Parameter: request");
            }

            if(request.getData() == null || request.getData().isEmpty()) {
                throw new AccountException(new ServiceError("missingAccountError", "Missing Account data"), "Missing Parameter: request.authorization");
            }

            String authorization = request.getData();
            final String encodedUserPassword = authorization.replaceFirst(AUTHENTICATION_SCHEME + " ", "");
            String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));
            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            final String login = tokenizer.nextToken();
            final String password = tokenizer.nextToken();

            if(login == null) {
                throw new AccountException(new ServiceError("missingAccountError", "Missing Account data"), "Missing Parameter: username");
            }

            if(password == null) {
                throw new AccountException(new ServiceError("missingAccountError", "Missing Account data"), "Missing Parameter: password");
            }

            if (accountRepository.getByLogin(login) != null){
                throw new AccountException(new ServiceError("repeatingAccountError", "Repeating Account data"), "Repeating Parameter: request.account");
            }

            String role = "USER";
            Account account = new Account(login,password,role);

            if  (accountRepository.save(account) == 0){
                throw new AccountException(new ServiceError("savingAccountError", "Error saving"), "Saving Account failed");
            }
            System.out.println("Аккаунт сохранён в базу");
            System.out.println(account.toString());

            return new AccountResponse(new String(Base64.encode(account.getPassword().getBytes())));
        }

    }

}
