package rs.iggy.clients.blocking.http;

import rs.iggy.clients.blocking.IggyClient;
import rs.iggy.clients.blocking.MessagesClientBaseTest;

class MessagesHttpClientTest extends MessagesClientBaseTest {

    @Override
    protected IggyClient getClient() {
        return HttpClientFactory.create(iggyServer);
    }

}
