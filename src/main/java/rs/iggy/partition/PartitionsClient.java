package rs.iggy.partition;

public interface PartitionsClient {

    void createPartitions(Long streamId, Long topicId, Long partitionsCount);

    void deletePartitions(Long streamId, Long topicId, Long partitionsCount);

}
