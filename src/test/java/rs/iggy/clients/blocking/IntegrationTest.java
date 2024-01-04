package rs.iggy.clients.blocking;

import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import static java.util.Optional.empty;

@Testcontainers
public abstract class IntegrationTest {

    public static final int HTTP_PORT = 3000;
    public static final int TCP_PORT = 8090;

    @Container
    protected final GenericContainer<?> iggyServer = new GenericContainer(DockerImageName.parse("iggyrs/iggy:latest"))
            .withExposedPorts(HTTP_PORT, TCP_PORT);

    protected IggyClient client;

    @BeforeEach
    void beforeEachIntegrationTest() {
        client = getClient();
    }

    abstract protected IggyClient getClient();

    protected void setUpStream() {
        client.streams().createStream(42L, "test-stream");
    }

    protected void setUpStreamAndTopic() {
        setUpStream();
        client.topics().createTopic(42L, 42L, 1L, empty(), "test-topic");
    }

    protected void login() {
        client.users().login("iggy", "iggy");
    }

}
