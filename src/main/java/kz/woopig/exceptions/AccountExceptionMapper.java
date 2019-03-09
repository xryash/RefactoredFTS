package kz.woopig.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

public class AccountExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<AccountException> {

    private static final Logger logger = LoggerFactory.getLogger(AccountExceptionMapper.class);

    @Override
    public Response toResponse(AccountException accountException) {

        if(logger.isErrorEnabled()) {
            logger.error("An error occurred", accountException);
        }
        HttpServiceError httpServiceError = accountException.getHttpServiceError();

        return Response
                .status(httpServiceError.getHttpStatusCode())
                .entity(httpServiceError.getServiceError())
                .build();
    }
}
