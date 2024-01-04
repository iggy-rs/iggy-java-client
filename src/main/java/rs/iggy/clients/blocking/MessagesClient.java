package rs.iggy.clients.blocking;

import rs.iggy.identifier.ConsumerId;
import rs.iggy.identifier.StreamId;
import rs.iggy.identifier.TopicId;
import rs.iggy.message.MessageToSend;
import rs.iggy.message.Partitioning;
import rs.iggy.message.PolledMessages;
import rs.iggy.message.PollingStrategy;
import java.util.List;
import java.util.Optional;

public interface MessagesClient {

    PolledMessages pollMessages(Long streamId, Long topicId, Optional<Long> partitionId, Long consumerId, PollingStrategy strategy, Long count, boolean autoCommit);

    PolledMessages pollMessages(StreamId streamId, TopicId topicId, Optional<Long> partitionId, ConsumerId consumerId, PollingStrategy strategy, Long count, boolean autoCommit);

    void sendMessages(Long streamId, Long topicId, Partitioning partitioning, List<MessageToSend> messages);

    void sendMessages(StreamId streamId, TopicId topicId, Partitioning partitioning, List<MessageToSend> messages);

}
