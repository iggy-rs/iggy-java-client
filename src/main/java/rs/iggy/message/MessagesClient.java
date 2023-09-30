package rs.iggy.message;

import java.util.List;
import java.util.Optional;

public interface MessagesClient {

    PolledMessages pollMessages(Long streamId, Long topicId, Optional<Long> partitionId, Long consumerId, PollingStrategy strategy, Long count, boolean autoCommit);

    void sendMessages(Long streamId, Long topicId, Partitioning partitioning, List<MessageToSend> messages);

}
