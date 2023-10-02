package rs.iggy.http;

import rs.iggy.IggyClient;
import rs.iggy.consumergroup.ConsumerGroupsClientBaseTest;

class ConsumerGroupsHttpClientTest extends ConsumerGroupsClientBaseTest {

    @Override
    protected IggyClient getClient() {
        return HttpClientFactory.create(iggyServer);
    }

}
