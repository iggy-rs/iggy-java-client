package rs.iggy.http;

import rs.iggy.IggyClient;
import rs.iggy.stream.StreamClientBaseTest;

class StreamHttpClientTest extends StreamClientBaseTest {

    @Override
    protected IggyClient getClient() {
        return HttpClientFactory.create(iggyServer);
    }

}
