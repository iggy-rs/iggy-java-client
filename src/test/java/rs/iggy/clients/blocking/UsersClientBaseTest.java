package rs.iggy.clients.blocking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class UsersClientBaseTest extends IntegrationTest {

    protected UsersClient usersClient;

    @BeforeEach
    void beforeEachBase() {
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
