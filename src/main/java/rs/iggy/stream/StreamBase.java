package rs.iggy.stream;

import java.math.BigInteger;

public record StreamBase(
        Long id,
        BigInteger createdAt,
        String name,
        BigInteger sizeBytes,
        BigInteger messagesCount,
        Long topicsCount
) {
}
