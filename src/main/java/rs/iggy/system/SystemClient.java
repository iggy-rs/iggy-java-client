package rs.iggy.system;

import java.util.List;

public interface SystemClient {
    Stats getStats();

    List<ClientInfo> getClients();

    ClientInfoDetails getClient(String clientId);

    String ping();
}
