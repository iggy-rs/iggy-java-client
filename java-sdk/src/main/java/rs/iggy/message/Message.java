package rs.iggy.message;

import java.util.Map;
import java.util.Optional;

public record Message(
        MessageId id,
        byte[] payload,
        Optional<Map<String, HeaderValue>> headers
) {
}
