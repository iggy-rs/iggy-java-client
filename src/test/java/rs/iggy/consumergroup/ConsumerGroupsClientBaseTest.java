package rs.iggy.consumergroup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.iggy.IntegrationTest;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class ConsumerGroupsClientBaseTest extends IntegrationTest {

    protected ConsumerGroupsClient consumerGroupsClient;

    @BeforeEach
    void beforeEachBase() {
        consumerGroupsClient = client.consumerGroups();

        login();
    }

    @Test
    void shouldCreateAndDeleteConsumerGroup() {
        // given
        setUpStreamAndTopic();

        // when
        consumerGroupsClient.createConsumerGroup(42L, 42L, 42L, "consumer-group-42");

        var consumerGroupById = consumerGroupsClient.getConsumerGroup(42L, 42L, 42L);
        var consumerGroupByName = consumerGroupsClient.getConsumerGroup(42L, 42L, 42L);

        // then
        assertThat(consumerGroupById).isNotNull();
        assertThat(consumerGroupById.id()).isEqualTo(42L);
        assertThat(consumerGroupById.name()).isEqualTo("consumer-group-42");
        assertThat(consumerGroupById).isEqualTo(consumerGroupByName);

        // when
        consumerGroupsClient.deleteConsumerGroup(42L, 42L, 42L);

        // then
        assertThat(consumerGroupsClient.getConsumerGroups(42L, 42L)).isEmpty();
    }

    @Test
    void shouldDeleteConsumerGroupByName() {
        // given
        setUpStreamAndTopic();
        consumerGroupsClient.createConsumerGroup(42L, 42L, 42L, "consumer-group-42");
        var consumerGroup = consumerGroupsClient.getConsumerGroup(42L, 42L, 42L);
        assert consumerGroup != null;

        // when
        consumerGroupsClient.deleteConsumerGroup(42L, 42L, "consumer-group-42");

        // then
        assertThat(consumerGroupsClient.getConsumerGroups(42L, 42L)).isEmpty();
    }

    @Test
    void shouldGetAllConsumerGroups() {
        // given
        setUpStreamAndTopic();

        consumerGroupsClient.createConsumerGroup(42L, 42L, 42L, "consumer-group-42");
        consumerGroupsClient.createConsumerGroup(42L, 42L, 43L, "consumer-group-43");
        consumerGroupsClient.createConsumerGroup(42L, 42L, 44L, "consumer-group-44");

        // when
        var consumerGroups = consumerGroupsClient.getConsumerGroups(42L, 42L);

        // then
        assertThat(consumerGroups).hasSize(3);
        assertThat(consumerGroups)
                .map(ConsumerGroup::name)
                .containsExactlyInAnyOrder("consumer-group-42", "consumer-group-43", "consumer-group-44");
    }

}
