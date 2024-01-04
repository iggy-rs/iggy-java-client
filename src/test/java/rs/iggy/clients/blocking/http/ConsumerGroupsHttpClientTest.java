package rs.iggy.clients.blocking.http;

import rs.iggy.clients.blocking.ConsumerGroupsClientBaseTest;
import rs.iggy.clients.blocking.IggyClient;

class ConsumerGroupsHttpClientTest extends ConsumerGroupsClientBaseTest {

    @Override
    protected IggyClient getClient() {
        return HttpClientFactory.create(iggyServer);
    }

}
