package kz.woopig.requests;

public class DownloadingFileRequest {

    private final int id;
    private final String login;

    public DownloadingFileRequest(int id, String login) {
        this.id = id;
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String toString() {
        return "DownloadingFileRequest{" + "id=" + id + ", login=" + login + '}';
    }
}
