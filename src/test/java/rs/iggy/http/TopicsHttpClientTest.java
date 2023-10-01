package rs.iggy.http;

import rs.iggy.IggyClient;
import rs.iggy.topic.TopicsClientBaseTest;

class TopicsHttpClientTest extends TopicsClientBaseTest {

    @Override
    protected IggyClient getClient() {
        return HttpClientFactory.create(iggyServer);
    }

}
