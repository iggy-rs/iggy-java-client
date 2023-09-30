package rs.iggy.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.iggy.message.*;
import rs.iggy.user.UsersClient;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;

class MessagesHttpClientTest {

    private static final Random RANDOM = new Random();
    private static final Logger LOG = LoggerFactory.getLogger(MessagesHttpClientTest.class);
    IggyHttpClient iggy = new IggyHttpClient("http://localhost:3000");
    UsersClient usersClient = iggy.users();
    MessagesClient messagesClient = iggy.messages();

    @BeforeEach
    void beforeEach() {
        usersClient.login("iggy", "iggy");
    }

    @Test
    void shouldGetMessages() {
        // when
        var polledMessages = messagesClient.pollMessages(42L, 42L, empty(), 0L,
                new PollingStrategy(PollingKind.Last, BigInteger.TEN), 10L, false);

        // then
        assertThat(polledMessages).isNotNull();
    }

    @Test
    void shouldSendMessage() {
        String text = "message from java sdk";
        messagesClient.sendMessages(42L, 42L, Partitioning.partitionId(1L),
                List.of(new MessageToSend(getRandomId(), text.getBytes(), empty())));
    }

    private static BigInteger getRandomId() {
        return new BigInteger(uuidToBytes(UUID.randomUUID()));
    }

    private static byte[] uuidToBytes(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return byteBuffer.array();
    }

}
