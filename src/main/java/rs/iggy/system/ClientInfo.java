package rs.iggy.system;

import java.util.Optional;

public record ClientInfo(
        String clientId,
        Optional<String> userId,
        String address,
        String transport,
        long consumerGroupsCount
) {
}
