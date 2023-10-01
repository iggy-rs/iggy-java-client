package rs.iggy.http;

import rs.iggy.IggyClient;
import rs.iggy.message.MessagesClientBaseTest;

class MessagesHttpClientTest extends MessagesClientBaseTest {

    @Override
    protected IggyClient getClient() {
        return HttpClientFactory.create(iggyServer);
    }

}
