package kz.woopig.providers;

public class RootPathProvider {

    private final String ROOT_PATH = "\\home\\xryash\\ft_storage\\";
    private final String path;

    public RootPathProvider() {
        this.path = ROOT_PATH;
    }

    public String getRootPath() {
        return path;
    }
}
