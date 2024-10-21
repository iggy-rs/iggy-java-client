package rs.iggy.clients.blocking.http;

import rs.iggy.clients.blocking.ConsumerOffsetsClientBaseTest;
import rs.iggy.clients.blocking.IggyClient;

class ConsumerOffsetsHttpClientTest extends ConsumerOffsetsClientBaseTest {

    @Override
    protected IggyClient getClient() {
        return HttpClientFactory.create(iggyServer);
    }

}
