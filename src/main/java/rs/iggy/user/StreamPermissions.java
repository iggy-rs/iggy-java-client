package rs.iggy.user;

import java.util.HashMap;

public record StreamPermissions(
        boolean manageStream,
        boolean readStream,
        boolean manageTopics,
        boolean readTopics,
        boolean pollMessages,
        boolean sendMessages,
        HashMap<Long, TopicPermissions> topics
) {
}
