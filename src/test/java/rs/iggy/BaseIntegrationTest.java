package rs.iggy;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import rs.iggy.http.IggyHttpClient;
import static java.util.Optional.empty;

@Testcontainers
public abstract class BaseIntegrationTest {

    @Container
    protected final GenericContainer<?> iggyServer = new GenericContainer(
            DockerImageName.parse("iggyrs/iggy:latest")).withExposedPorts(3000);

    protected void setUpStream(IggyHttpClient client) {
        client.streams().createStream(42L, "test-stream");
    }

    protected void setUpStreamAndTopic(IggyHttpClient client) {
        setUpStream(client);
        client.topics().createTopic(42L, 42L, 1L, empty(), "test-topic");
    }

    protected void login(IggyHttpClient client) {
        client.users().login("iggy", "iggy");
    }

}
