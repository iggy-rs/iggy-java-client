package rs.iggy.partition;

import java.math.BigInteger;

public record Partition(
        Long id,
        BigInteger createdAt,
        Long segmentsCount,
        BigInteger currentOffset,
        BigInteger sizeBytes,
        BigInteger messagesCount
) {
}
