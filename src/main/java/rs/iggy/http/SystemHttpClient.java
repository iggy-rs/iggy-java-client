package rs.iggy.http;

import com.fasterxml.jackson.core.type.TypeReference;
import rs.iggy.system.ClientInfo;
import rs.iggy.system.ClientInfoDetails;
import rs.iggy.system.Stats;
import rs.iggy.system.SystemClient;
import java.util.List;

class SystemHttpClient implements SystemClient {

    private final HttpClient httpClient;
    private static final String STATS = "/stats";
    private static final String CLIENTS = "/clients";
    private static final String PING = "/ping";

    public SystemHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Stats getStats() {
        var request = httpClient.prepareGetRequest(STATS);
        return httpClient.execute(request, Stats.class);
    }

    @Override
    public List<ClientInfo> getClients() {
        var request = httpClient.prepareGetRequest(CLIENTS);
        return httpClient.execute(request, new TypeReference<>() {
        });
    }

    @Override
    public ClientInfoDetails getClient(String clientId) {
        var request = httpClient.prepareGetRequest(CLIENTS + "/" + clientId);
        return httpClient.execute(request, ClientInfoDetails.class);
    }

    @Override
    public String ping() {
        var request = httpClient.prepareGetRequest(PING);
        return httpClient.execute(request);
    }
}
