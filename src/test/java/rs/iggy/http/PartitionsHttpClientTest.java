package rs.iggy.http;

import rs.iggy.IggyClient;
import rs.iggy.partition.PartitionsClientBaseTest;

class PartitionsHttpClientTest extends PartitionsClientBaseTest {

    @Override
    protected IggyClient getClient() {
        return HttpClientFactory.create(iggyServer);
    }

}
