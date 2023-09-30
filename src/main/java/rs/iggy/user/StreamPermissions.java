package rs.iggy.user;

import java.util.Map;
import java.util.Optional;

public record StreamPermissions(
        boolean manageStream,
        boolean readStream,
        boolean manageTopics,
        boolean readTopics,
        boolean pollMessages,
        boolean sendMessages,
        Optional<Map<Long, TopicPermissions>> topics
) {
}
