package rs.iggy.clients.blocking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class StreamClientBaseTest extends IntegrationTest {

    StreamsClient streamsClient;

    @BeforeEach
    void beforeEachBase() {
        streamsClient = client.streams();

        login();
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
