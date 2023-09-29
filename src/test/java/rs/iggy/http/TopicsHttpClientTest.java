package rs.iggy.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.iggy.stream.StreamsClient;
import rs.iggy.topic.TopicsClient;
import rs.iggy.user.UsersClient;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;

class TopicsHttpClientTest {

    IggyHttpClient iggy = new IggyHttpClient("http://localhost:3000");
    UsersClient usersClient = iggy.users();
    StreamsClient streamsClient = iggy.streams();
    TopicsClient topicsClient = iggy.topics();

    @BeforeEach
    void beforeEach() {
        usersClient.login("iggy", "iggy");
    }

    @Test
    void shouldCreateTopic() {
        // given
        streamsClient.createStream(42L, "test-stream");

        // when
        topicsClient.createTopic(42L, 42L, 1L, empty(), "test-topic");
        var topic = topicsClient.getTopic(42L, 42L);

        // then
        assertThat(topic).isNotNull();
    }

}
