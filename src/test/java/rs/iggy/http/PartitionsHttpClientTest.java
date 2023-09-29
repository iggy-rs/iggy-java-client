package rs.iggy.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.iggy.partition.PartitionsClient;
import rs.iggy.topic.TopicsClient;
import rs.iggy.user.UsersClient;
import static org.assertj.core.api.Assertions.assertThat;

class PartitionsHttpClientTest {

    IggyHttpClient iggy = new IggyHttpClient("http://localhost:3000");
    UsersClient usersClient = iggy.users();
    TopicsClient topicsClient = iggy.topics();
    PartitionsClient partitionsClient = iggy.partitions();

    @BeforeEach
    void beforeEach() {
        usersClient.login("iggy", "iggy");
    }


    @Test
    void shouldCreateAndDeletePartitions() {
        // given
        var topic = topicsClient.getTopic(42L, 42L);
        assert topic.partitionsCount() == 1L;

        // when
        partitionsClient.createPartitions(42L, 42L, 10L);

        // then
        topic = topicsClient.getTopic(42L, 42L);
        assertThat(topic.partitionsCount()).isEqualTo(11L);

        // when
        partitionsClient.deletePartitions(42L, 42L, 10L);

        // then
        topic = topicsClient.getTopic(42L, 42L);
        assertThat(topic.partitionsCount()).isEqualTo(1L);
    }

}
