package rs.iggy.http;

import rs.iggy.system.SystemClient;

public class IggyHttpClient {

    private final SystemHttpClient systemClient;

    public IggyHttpClient(String url) {
        HttpClient httpClient = new HttpClient(url);
        systemClient = new SystemHttpClient(httpClient);
    }

    public SystemClient system() {
        return systemClient;
    }

}
