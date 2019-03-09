package kz.woopig.exceptions;

public class ProviderException extends RuntimeException  {
    private final HttpServiceError httpServiceError;

    public ProviderException(int code, ServiceError serviceError) {
        this.httpServiceError = createServiceError(code, serviceError);
    }

    public ProviderException(int code, ServiceError serviceError, String message) {
        super(message);

        this.httpServiceError = createServiceError(code, serviceError);
    }

    public ProviderException(int code, ServiceError serviceError, String message, Throwable cause) {
        super(message, cause);

        this.httpServiceError = createServiceError(code,serviceError);
    }

    public HttpServiceError getHttpServiceError() {
        return httpServiceError;
    }

    private static HttpServiceError createServiceError(int code, ServiceError serviceError) {
        return new HttpServiceError(code, serviceError);
    }
}
