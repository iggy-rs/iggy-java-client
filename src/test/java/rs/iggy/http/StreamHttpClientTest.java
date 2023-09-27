package rs.iggy.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.iggy.stream.StreamsClient;
import rs.iggy.user.UsersClient;
import static org.assertj.core.api.Assertions.assertThat;

class StreamHttpClientTest {

    IggyHttpClient iggy = new IggyHttpClient("http://localhost:3000");
    UsersClient usersClient = iggy.users();
    StreamsClient streamsClient = iggy.streams();

    @BeforeEach
    void beforeEach() {
        usersClient.login("iggy", "iggy");
    }

    @Test
    void shouldCreateStream() {
        // when
        streamsClient.createStream(42L, "test-stream");
        var stream = streamsClient.getStream(42L);

        // then
        assertThat(stream).isNotNull();
        assertThat(stream.id()).isEqualTo(42L);
        assertThat(stream.name()).isEqualTo("test-stream");
    }

    @Test
    void shouldDeleteStream() {
        // when
        streamsClient.deleteStream(42L);
        var streams = streamsClient.getStreams();

        // then
        assertThat(streams).isEmpty();
    }

}
