package kz.woopig.filters;

import kz.woopig.entities.Account;
import kz.woopig.exceptions.ProviderException;
import kz.woopig.exceptions.ServiceError;

import javax.annotation.Priority;
import javax.annotation.security.PermitAll;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.StringTokenizer;

import kz.woopig.repositories.AccountRepository;
import org.glassfish.jersey.internal.util.Base64;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";

    @Context
    private ResourceInfo resourceInfo;

    private final AccountRepository.AccountRepositoryImpl accountRepository;

    public AuthenticationFilter() {
        accountRepository = new AccountRepository.AccountRepositoryImpl();
    }

    public void filter(final ContainerRequestContext requestContext) {

        Method method = resourceInfo.getResourceMethod();
        if (method.isAnnotationPresent(PermitAll.class)) {return;}

        final String authorization = requestContext.getHeaderString(AUTHORIZATION_PROPERTY);

        if (authorization == null || authorization.isEmpty()) {
            System.out.println("authorization is null");
            throw new ProviderException(
                    Response.Status.UNAUTHORIZED.getStatusCode(),
                    new ServiceError("UNAUTHORIZED", "UNAUTHORIZED"),
                    String.format("UNAUTHORIZED"));
        }

        final String encodedUserPassword = authorization.replaceFirst(AUTHENTICATION_SCHEME + " ", "");
        final String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final String login = tokenizer.nextToken();
        final String password = tokenizer.nextToken();

        final Account account = accountRepository.getByLoginAndPassword(login, password);

        if (account == null) {
            System.out.println("account is null");
            throw new ProviderException(
                    Response.Status.UNAUTHORIZED.getStatusCode(),
                    new ServiceError("UNAUTHORIZED", "UNAUTHORIZED"),
                    String.format("UNAUTHORIZED"));
        }

        final SecurityContext securityContext
                = requestContext.getSecurityContext();
        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return () -> account.getLogin();
            }

            @Override
            public boolean isUserInRole(String role) {
                return account.getRole().equals(role);
            }

            @Override
            public boolean isSecure() {
                return securityContext.isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                return null;
            }
        });

    }

}