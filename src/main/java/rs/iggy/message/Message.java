package rs.iggy.message;

import java.math.BigInteger;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

public record Message(
        BigInteger offset,
        MessageState state,
        BigInteger timestamp,
        BigInteger id,
        Long checksum,
        Optional<Map<String, Map>> headers,
        String payload
) {

    String getDecodedPayload() {
        return new String(Base64.getDecoder().decode(payload));
    }

}
