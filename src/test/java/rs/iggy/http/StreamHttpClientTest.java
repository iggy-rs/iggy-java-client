package rs.iggy.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.iggy.stream.StreamsClient;
import static org.assertj.core.api.Assertions.assertThat;

class StreamHttpClientTest extends BaseHttpClientIntegrationTest {

    StreamsClient streamsClient;

    @BeforeEach
    void beforeEach() {
        streamsClient = client.streams();

        login(client);
    }

    @Test
    void shouldCreateAndDeleteStream() {
        // when
        streamsClient.createStream(42L, "test-stream");
        var stream = streamsClient.getStream(42L);

        // then
        assertThat(stream).isNotNull();
        assertThat(stream.id()).isEqualTo(42L);
        assertThat(stream.name()).isEqualTo("test-stream");

        // when
        streamsClient.deleteStream(42L);
        var streams = streamsClient.getStreams();

        // then
        assertThat(streams).isEmpty();
    }

}
