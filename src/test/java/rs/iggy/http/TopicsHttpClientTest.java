package rs.iggy.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.iggy.topic.TopicsClient;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;

class TopicsHttpClientTest extends BaseHttpClientIntegrationTest {

    TopicsClient topicsClient;

    @BeforeEach
    void beforeEach() {
        topicsClient = client.topics();

        login(client);
        setUpStream(client);
    }

    @Test
    void shouldCreateAndDeleteTopic() {
        // when
        topicsClient.createTopic(42L, 42L, 1L, empty(), "test-topic");
        var topic = topicsClient.getTopic(42L, 42L);

        // then
        assertThat(topic).isNotNull();
        assertThat(topic.id()).isEqualTo(42L);
        assertThat(topic.name()).isEqualTo("test-topic");

        // when
        topicsClient.deleteTopic(42L, 42L);

        // then
        assertThat(topicsClient.getTopics(42L)).isEmpty();
    }

}
