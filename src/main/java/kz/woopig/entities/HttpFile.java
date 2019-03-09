package kz.woopig.entities;

import java.io.InputStream;

public class HttpFile {
    private final String submittedFileName;
    private final InputStream stream;
    private final String directory;
    private final long size;

    public HttpFile(String submittedFileName, InputStream stream, String directory, long size) {
        this.submittedFileName = submittedFileName;
        this.stream = stream;
        this.directory = directory;
        this.size = size;
    }

    public String getSubmittedFileName() {
        return submittedFileName;
    }

    public InputStream getStream() {
        return stream;
    }

    public String getDirectory() {
        return directory;
    }

    public long getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "HttpFile{" + "submittedFileName=" + submittedFileName + ", stream=" + stream + ", directory=" + directory + ", size=" + size + '}';
    }

}
