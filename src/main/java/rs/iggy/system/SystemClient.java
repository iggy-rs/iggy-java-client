package rs.iggy.system;

import java.util.List;

public interface SystemClient {

    Stats getStats();

    ClientInfoDetails getMe();

    ClientInfoDetails getClient(String clientId);

    List<ClientInfo> getClients();

    String ping();

}
