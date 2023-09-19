package rs.iggy.system;

import java.util.List;
import java.util.Optional;

public record ClientInfoDetails(
        String clientId,
        Optional<String> userId,
        String address,
        String transport,
        long consumerGroupsCount,
        List<ConsumerGroupInfo> consumerGroups
) {
}
