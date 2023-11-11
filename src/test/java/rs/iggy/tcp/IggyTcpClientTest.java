package rs.iggy.tcp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.iggy.IggyClient;
import rs.iggy.IntegrationTest;
import rs.iggy.user.UsersClient;
import static org.assertj.core.api.Assertions.assertThat;

class IggyTcpClientTest extends IntegrationTest {

    protected UsersClient usersClient;

    @Override
    protected IggyClient getClient() {
        return TcpClientFactory.create(iggyServer);
    }

    @BeforeEach
    void beforeEach() {
        usersClient = client.users();
    }

    @Test
    void shouldLogin() {
        // when
        var identityInfo = usersClient.login("iggy", "iggy");

        // then
        assertThat(identityInfo).isNotNull();
        assertThat(identityInfo.userId()).isEqualTo(1L);
    }

}
