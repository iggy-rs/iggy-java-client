package rs.iggy.consumergroup;

import java.util.List;

public record ConsumerGroupDetails(
        Long id,
        String name,
        Long partitionsCount,
        Long membersCount,
        List<ConsumerGroupMember> members
) {
}
