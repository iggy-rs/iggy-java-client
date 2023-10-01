package rs.iggy.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.iggy.message.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;

class MessagesHttpClientTest extends BaseHttpClientIntegrationTest {

    MessagesClient messagesClient;

    @BeforeEach
    void beforeEach() {
        messagesClient = client.messages();

        login(client);
    }

    @Test
    void shouldSendAndGetMessages() {
        // given
        setUpStreamAndTopic(client);

        // when
        String text = "message from java sdk";
        messagesClient.sendMessages(42L, 42L, Partitioning.partitionId(1L),
                List.of(new MessageToSend(getRandomId(), text.getBytes(), empty())));

        var polledMessages = messagesClient.pollMessages(42L, 42L, empty(), 0L,
                new PollingStrategy(PollingKind.Last, BigInteger.TEN), 10L, false);

        // then
        assertThat(polledMessages.messages()).hasSize(1);
    }

    @Test
    void shouldSendMessageWithBalancedPartitioning() {
        // given
        setUpStreamAndTopic(client);

        // when
        String text = "message from java sdk";
        messagesClient.sendMessages(42L, 42L, Partitioning.balanced(),
                List.of(new MessageToSend(getRandomId(), text.getBytes(), empty())));

        var polledMessages = messagesClient.pollMessages(42L, 42L, empty(), 0L,
                new PollingStrategy(PollingKind.Last, BigInteger.TEN), 10L, false);

        // then
        assertThat(polledMessages.messages()).hasSize(1);
    }

    @Test
    void shouldSendMessageWithMessageKeyPartitioning() {
        // given
        setUpStreamAndTopic(client);

        // when
        String text = "message from java sdk";
        messagesClient.sendMessages(42L, 42L, Partitioning.messagesKey("test-key"),
                List.of(new MessageToSend(getRandomId(), text.getBytes(), empty())));

        var polledMessages = messagesClient.pollMessages(42L, 42L, empty(), 0L,
                new PollingStrategy(PollingKind.Last, BigInteger.TEN), 10L, false);

        // then
        assertThat(polledMessages.messages()).hasSize(1);
    }

    private static BigInteger getRandomId() {
        return new BigInteger(1, uuidToBytes(UUID.randomUUID()));
    }

    private static byte[] uuidToBytes(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return byteBuffer.array();
    }

}
