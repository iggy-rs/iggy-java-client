package rs.iggy.topic;

import java.math.BigInteger;
import java.util.Optional;

public record Topic(
        Long id,
        BigInteger createdAt,
        String name,
        BigInteger sizeBytes,
        Optional<Long> messageExpiry,
        BigInteger messagesCount,
        Long partitionsCount
) {
}
