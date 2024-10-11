package rs.iggy.message;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

public record Message(
        BigInteger offset,
        MessageState state,
        BigInteger timestamp,
        BigInteger id,
        Long checksum,
        Optional<Map<String, HeaderValue>> headers,
        byte[] payload
) {
}
