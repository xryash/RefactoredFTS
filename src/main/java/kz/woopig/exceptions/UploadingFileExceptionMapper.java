package kz.woopig.exceptions;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class UploadingFileExceptionMapper implements ExceptionMapper<UploadingFileException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadingFileExceptionMapper.class);

    @Override
    public Response toResponse(UploadingFileException e) {

        if(LOGGER.isErrorEnabled()) {
            LOGGER.error("An error occured", e);
        }

        HttpServiceError httpServiceError = e.getHttpServiceError();

        return Response
                .status(httpServiceError.getHttpStatusCode())
                .entity(httpServiceError.getServiceError())
                .build();
    }
}
