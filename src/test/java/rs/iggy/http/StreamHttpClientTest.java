package rs.iggy.http;

import org.junit.jupiter.api.Test;
import rs.iggy.stream.StreamClient;
import static org.assertj.core.api.Assertions.assertThat;

class StreamHttpClientTest {

    StreamClient client = new IggyHttpClient("http://localhost:3000").streams();

    @Test
    void shouldCreateStream() {
        // when
        client.createStream(42L, "test-stream");
        var stream = client.getStream(42L);

        // then
        assertThat(stream).isNotNull();
        assertThat(stream.id()).isEqualTo(42L);
        assertThat(stream.name()).isEqualTo("test-stream");
    }

    @Test
    void shouldDeleteStream() {
        // when
        client.deleteStream(42L);
        var streams = client.getStreams();

        // then
        assertThat(streams).isEmpty();
    }

}
