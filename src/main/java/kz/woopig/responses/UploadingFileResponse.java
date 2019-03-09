package kz.woopig.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UploadingFileResponse {

    private final String identifier;

    public UploadingFileResponse(String identifier) {
        this.identifier = identifier;
    }

    @JsonProperty("identifier")
    public String getIdentifier() {
        return identifier;
    }
}
