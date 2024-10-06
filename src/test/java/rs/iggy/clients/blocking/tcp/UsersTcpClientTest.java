package rs.iggy.clients.blocking.tcp;

import org.junit.jupiter.api.BeforeEach;
import rs.iggy.clients.blocking.IggyClient;
import rs.iggy.clients.blocking.UsersClient;
import rs.iggy.clients.blocking.UsersClientBaseTest;

class UsersTcpClientTest extends UsersClientBaseTest {

    protected UsersClient usersClient;

    @Override
    protected IggyClient getClient() {
        return TcpClientFactory.create(iggyServer);
    }

    @BeforeEach
    void beforeEach() {
        usersClient = client.users();
    }

}
