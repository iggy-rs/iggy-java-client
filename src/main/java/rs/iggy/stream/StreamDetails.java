package rs.iggy.stream;

import rs.iggy.topic.Topic;
import java.math.BigInteger;
import java.util.List;

public record StreamDetails(
        Long id,
        BigInteger createdAt,
        String name,
        String size,
        BigInteger messagesCount,
        Long topicsCount,
        List<Topic> topics
) {
}
