package rs.iggy.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.iggy.partition.PartitionsClient;
import rs.iggy.topic.TopicDetails;
import rs.iggy.topic.TopicsClient;
import static org.assertj.core.api.Assertions.assertThat;

class PartitionsHttpClientTest extends BaseHttpClientIntegrationTest {

    TopicsClient topicsClient;
    PartitionsClient partitionsClient;

    @BeforeEach
    void beforeEach() {
        topicsClient = client.topics();
        partitionsClient = client.partitions();

        login(client);
        setUpStreamAndTopic(client);
    }


    @Test
    void shouldCreateAndDeletePartitions() {
        // given
        assert topicsClient.getTopic(42L, 42L).partitionsCount() == 1L;

        // when
        partitionsClient.createPartitions(42L, 42L, 10L);

        // then
        TopicDetails topic = topicsClient.getTopic(42L, 42L);
        assertThat(topic.partitionsCount()).isEqualTo(11L);

        // when
        partitionsClient.deletePartitions(42L, 42L, 10L);

        // then
        topic = topicsClient.getTopic(42L, 42L);
        assertThat(topic.partitionsCount()).isEqualTo(1L);
    }

}
