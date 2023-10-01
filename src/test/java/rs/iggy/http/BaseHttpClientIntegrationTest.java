package rs.iggy.http;

import org.junit.jupiter.api.BeforeEach;
import rs.iggy.BaseIntegrationTest;

abstract class BaseHttpClientIntegrationTest extends BaseIntegrationTest {

    protected IggyHttpClient client;

    @BeforeEach
    void beforeEachBase() {
        String address = iggyServer.getHost();
        Integer port = iggyServer.getFirstMappedPort();
        client = new IggyHttpClient("http://" + address + ":" + port);
    }
}
