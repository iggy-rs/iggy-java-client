package rs.iggy.http;

import rs.iggy.users.IdentityInfo;
import rs.iggy.users.UsersClient;

class UsersHttpClient implements UsersClient {

    private final HttpClient httpClient;
    private static final String USERS = "/users";

    public UsersHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public IdentityInfo login(String username, String password) {
        var request = httpClient.preparePostRequest(USERS + "/login", new Login(username, password));
        var response = httpClient.execute(request, IdentityInfo.class);
        httpClient.setToken(response.token());
        return response;
    }

    record Login(String username, String password) {
    }

}
