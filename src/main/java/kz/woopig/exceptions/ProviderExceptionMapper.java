package kz.woopig.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

public class ProviderExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<ProviderException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderExceptionMapper.class);

    @Override
    public Response toResponse(ProviderException providerException) {

        if(LOGGER.isErrorEnabled()) {
            LOGGER.error("An error occured", providerException);
        }

        HttpServiceError httpServiceError = providerException.getHttpServiceError();

        return Response
                .status(httpServiceError.getHttpStatusCode())
                .entity(httpServiceError.getServiceError())
                .build();
    }
}