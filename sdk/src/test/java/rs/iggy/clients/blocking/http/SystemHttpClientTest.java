package rs.iggy.clients.blocking.http;

import rs.iggy.clients.blocking.IggyClient;
import rs.iggy.clients.blocking.SystemClientBaseTest;

class SystemHttpClientTest extends SystemClientBaseTest {

    @Override
    protected IggyClient getClient() {
        return HttpClientFactory.create(iggyServer);
    }

}
