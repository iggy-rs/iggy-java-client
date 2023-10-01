package rs.iggy;

import rs.iggy.consumergroup.ConsumerGroupsClient;
import rs.iggy.consumeroffset.ConsumerOffsetsClient;
import rs.iggy.message.MessagesClient;
import rs.iggy.partition.PartitionsClient;
import rs.iggy.stream.StreamsClient;
import rs.iggy.system.SystemClient;
import rs.iggy.topic.TopicsClient;
import rs.iggy.user.UsersClient;

public interface IggyClient {

    SystemClient system();

    StreamsClient streams();

    UsersClient users();

    TopicsClient topics();

    PartitionsClient partitions();

    ConsumerGroupsClient consumerGroups();

    ConsumerOffsetsClient consumerOffsets();

    MessagesClient messages();

}
