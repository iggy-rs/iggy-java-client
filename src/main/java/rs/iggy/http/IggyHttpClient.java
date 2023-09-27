package rs.iggy.http;

import rs.iggy.stream.StreamClient;
import rs.iggy.system.SystemClient;
import rs.iggy.users.UsersClient;

public class IggyHttpClient {

    private final SystemHttpClient systemClient;
    private final StreamHttpClient streamClient;
    private final UsersHttpClient usersClient;

    public IggyHttpClient(String url) {
        HttpClient httpClient = new HttpClient(url);
        systemClient = new SystemHttpClient(httpClient);
        streamClient = new StreamHttpClient(httpClient);
        usersClient = new UsersHttpClient(httpClient);
    }

    public SystemClient system() {
        return systemClient;
    }

    public StreamClient streams() {
        return streamClient;
    }

    public UsersClient users() {
        return usersClient;
    }

}
