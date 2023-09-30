package rs.iggy.consumergroup;

import java.util.List;

public record ConsumerGroupDetails(
        Long id,
        Long partitionsCount,
        Long membersCount,
        List<ConsumerGroupMember> members
) {
}
