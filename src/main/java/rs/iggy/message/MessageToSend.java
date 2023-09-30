package rs.iggy.message;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

public record MessageToSend(
        BigInteger id,
        byte[] payload,
        Optional<Map<String, HeaderValue>> headers
) {
}
