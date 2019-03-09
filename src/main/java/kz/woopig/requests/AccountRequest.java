package kz.woopig.requests;

public class AccountRequest {

    private final String data;

    public AccountRequest(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
