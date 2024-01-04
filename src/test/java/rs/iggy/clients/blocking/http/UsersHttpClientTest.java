package rs.iggy.clients.blocking.http;

import org.junit.jupiter.api.Test;
import rs.iggy.clients.blocking.IggyClient;
import rs.iggy.clients.blocking.UsersClientBaseTest;
import static org.assertj.core.api.Assertions.assertThat;

class UsersHttpClientTest extends UsersClientBaseTest {

    @Override
    protected IggyClient getClient() {
        return HttpClientFactory.create(iggyServer);
    }


    @Test
    void shouldGetTokens() {
        // when
        var identityInfo = usersClient.login("iggy", "iggy");

        // then
        assertThat(identityInfo).isNotNull();
        assertThat(identityInfo.tokens()).isPresent();
        assertThat(identityInfo.tokens().get().accessToken().token()).isNotBlank();
        assertThat(identityInfo.tokens().get().refreshToken().token()).isNotBlank();
    }

}
