package rs.iggy.clients.blocking.http;

import rs.iggy.clients.blocking.IggyClient;
import rs.iggy.clients.blocking.PersonalAccessTokensBaseTest;

class PersonalAccessTokensHttpClientTest extends PersonalAccessTokensBaseTest {

    @Override
    protected IggyClient getClient() {
        return HttpClientFactory.create(iggyServer);
    }

}
