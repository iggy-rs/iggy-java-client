package rs.iggy.http;

import rs.iggy.stream.StreamClient;
import rs.iggy.system.SystemClient;

public class IggyHttpClient {

    private final SystemHttpClient systemClient;
    private final StreamHttpClient streamClient;

    public IggyHttpClient(String url) {
        HttpClient httpClient = new HttpClient(url);
        systemClient = new SystemHttpClient(httpClient);
        streamClient = new StreamHttpClient(httpClient);
    }

    public SystemClient system() {
        return systemClient;
    }

    public StreamClient streams() {
        return streamClient;
    }

}
