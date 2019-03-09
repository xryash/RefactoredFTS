package kz.woopig.requests;

import kz.woopig.entities.HttpFile;

public class UploadingFileRequest {

    private final HttpFile httpFile;
    private final String login;

    public UploadingFileRequest(HttpFile httpFile, String login) {
        this.httpFile = httpFile;
        this.login = login;
    }

    public HttpFile getHttpFile() {
        return httpFile;
    }

    public String getLogin() {
        return login;
    }

}
