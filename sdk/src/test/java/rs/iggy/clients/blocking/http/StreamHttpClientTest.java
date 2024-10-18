package rs.iggy.clients.blocking.http;

import rs.iggy.clients.blocking.IggyClient;
import rs.iggy.clients.blocking.StreamClientBaseTest;

class StreamHttpClientTest extends StreamClientBaseTest {

    @Override
    protected IggyClient getClient() {
        return HttpClientFactory.create(iggyServer);
    }

}
