package kz.woopig.exceptions;

public class UploadingFileException extends RuntimeException {

    private final HttpServiceError httpServiceError;

    public UploadingFileException(ServiceError serviceError) {
        this.httpServiceError = createServiceError(serviceError);
    }

    public UploadingFileException(ServiceError serviceError, String message) {
        super(message);

        this.httpServiceError = createServiceError(serviceError);
    }

    public UploadingFileException(ServiceError serviceError, String message, Throwable cause) {
        super(message, cause);

        this.httpServiceError = createServiceError(serviceError);
    }

    public HttpServiceError getHttpServiceError() {
        return httpServiceError;
    }

    private static HttpServiceError createServiceError(ServiceError serviceError) {
        return new HttpServiceError(400, serviceError);
    }
}

