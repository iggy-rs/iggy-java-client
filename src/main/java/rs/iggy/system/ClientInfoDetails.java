package rs.iggy.system;

import java.util.List;
import java.util.Optional;

public record ClientInfoDetails(
        Long clientId,
        Optional<Long> userId,
        String address,
        String transport,
        Long consumerGroupsCount,
        List<ConsumerGroupInfo> consumerGroups
) {
}
