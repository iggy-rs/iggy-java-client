package rs.iggy.topic;

import rs.iggy.partition.Partition;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public record TopicDetails(
        Long id,
        BigInteger createdAt,
        String name,
        BigInteger sizeBytes,
        Optional<Long> messageExpiry,
        BigInteger messagesCount,
        Long partitionsCount,
        List<Partition> partitions
) {
}
