package rs.iggy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.iggy.clients.blocking.tcp.IggyTcpClient;
import rs.iggy.consumergroup.Consumer;
import rs.iggy.consumergroup.ConsumerGroupDetails;
import rs.iggy.identifier.ConsumerId;
import rs.iggy.identifier.StreamId;
import rs.iggy.identifier.TopicId;
import rs.iggy.message.Message;
import rs.iggy.message.PolledMessages;
import rs.iggy.message.PollingKind;
import rs.iggy.message.PollingStrategy;
import rs.iggy.stream.StreamDetails;
import rs.iggy.topic.CompressionAlgorithm;
import rs.iggy.topic.TopicDetails;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static java.util.Optional.empty;

public class SimpleConsumer {

    private static final String STREAM_NAME = "dev01";
    private static final StreamId STREAM_ID = StreamId.of(STREAM_NAME);
    private static final String TOPIC_NAME = "events";
    private static final TopicId TOPIC_ID = TopicId.of(TOPIC_NAME);
    private static final String GROUP_NAME = "simple-consumer";
    private static final ConsumerId GROUP_ID = ConsumerId.of(GROUP_NAME);
    private static final Logger log = LoggerFactory.getLogger(SimpleConsumer.class);

    public static void main(String[] args) {
        IggyTcpClient client = new IggyTcpClient("localhost", 8090);
        client.users().login("iggy", "iggy");

        createStream(client);
        createTopic(client);
        createConsumerGroup(client);
        client.consumerGroups().joinConsumerGroup(STREAM_ID, TOPIC_ID, GROUP_ID);

        List<Message> messages = new ArrayList<>();
        while (messages.size() < 1000) {
            PolledMessages polledMessages = client.messages()
                    .pollMessages(STREAM_ID,
                            TOPIC_ID,
                            empty(),
                            new Consumer(Consumer.Kind.ConsumerGroup, GROUP_ID),
                            new PollingStrategy(PollingKind.Next, BigInteger.ZERO),
                            10L,
                            true);
            messages.addAll(polledMessages.messages());
            log.debug("Fetched {} messages from partition {}, current offset {}",
                    polledMessages.messages().size(),
                    polledMessages.partitionId(),
                    polledMessages.currentOffset());
        }
    }

    private static void createStream(IggyTcpClient client) {
        Optional<StreamDetails> stream = client.streams().getStream(STREAM_ID);
        if (stream.isPresent()) {
            return;
        }
        client.streams().createStream(empty(), STREAM_NAME);
    }

    private static void createTopic(IggyTcpClient client) {
        Optional<TopicDetails> topic = client.topics().getTopic(STREAM_ID, TOPIC_ID);
        if (topic.isPresent()) {
            return;
        }
        client.topics()
                .createTopic(STREAM_ID,
                        empty(),
                        1L,
                        CompressionAlgorithm.none,
                        BigInteger.ZERO,
                        BigInteger.ZERO,
                        empty(),
                        TOPIC_NAME);

    }

    private static void createConsumerGroup(IggyTcpClient client) {
        Optional<ConsumerGroupDetails> consumerGroup = client.consumerGroups()
                .getConsumerGroup(STREAM_ID, TOPIC_ID, GROUP_ID);
        if (consumerGroup.isPresent()) {
            return;
        }
        client.consumerGroups().createConsumerGroup(STREAM_ID, TOPIC_ID, empty(), GROUP_NAME);
    }

}