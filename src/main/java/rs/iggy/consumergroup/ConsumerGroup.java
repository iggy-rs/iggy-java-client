package rs.iggy.consumergroup;

public record ConsumerGroup(
        Long id,
        Long partitionsCount,
        Long membersCount
) {
}
