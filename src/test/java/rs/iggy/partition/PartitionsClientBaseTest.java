package rs.iggy.partition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.iggy.IntegrationTest;
import rs.iggy.topic.TopicDetails;
import rs.iggy.topic.TopicsClient;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class PartitionsClientBaseTest extends IntegrationTest {

    TopicsClient topicsClient;
    PartitionsClient partitionsClient;

    @BeforeEach
    void beforeEachBase() {
        topicsClient = client.topics();
        partitionsClient = client.partitions();

        login();
        setUpStreamAndTopic();
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
