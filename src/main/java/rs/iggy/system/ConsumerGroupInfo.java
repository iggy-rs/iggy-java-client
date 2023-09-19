package rs.iggy.system;

public record ConsumerGroupInfo(
        String streamId,
        String topicId,
        String consumerGroupId
) {
}
