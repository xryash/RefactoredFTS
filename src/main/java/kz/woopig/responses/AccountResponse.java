package kz.woopig.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountResponse {
    private final String data;

    public AccountResponse(String data) {
        this.data = data;
    }

    @JsonProperty("data")
    public String getData() {
        return data;
    }
}
