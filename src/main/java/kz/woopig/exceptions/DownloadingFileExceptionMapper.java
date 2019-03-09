package kz.woopig.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class DownloadingFileExceptionMapper implements ExceptionMapper<DownloadingFileException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadingFileExceptionMapper.class);

    @Override
    public Response toResponse(DownloadingFileException e) {

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