package rs.iggy.clients.blocking.http;

import rs.iggy.clients.blocking.IggyClient;
import rs.iggy.clients.blocking.PartitionsClientBaseTest;

class PartitionsHttpClientTest extends PartitionsClientBaseTest {

    @Override
    protected IggyClient getClient() {
        return HttpClientFactory.create(iggyServer);
    }

}
