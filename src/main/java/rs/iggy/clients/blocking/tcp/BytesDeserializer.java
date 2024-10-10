package rs.iggy.clients.blocking.tcp;

import io.netty.buffer.ByteBuf;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.iggy.consumergroup.ConsumerGroup;
import rs.iggy.consumergroup.ConsumerGroupDetails;
import rs.iggy.consumergroup.ConsumerGroupMember;
import rs.iggy.consumeroffset.ConsumerOffsetInfo;
import rs.iggy.message.*;
import rs.iggy.partition.Partition;
import rs.iggy.stream.StreamBase;
import rs.iggy.stream.StreamDetails;
import rs.iggy.topic.CompressionAlgorithm;
import rs.iggy.topic.Topic;
import rs.iggy.topic.TopicDetails;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;

final class BytesDeserializer {

    private static final Logger log = LoggerFactory.getLogger(BytesDeserializer.class);

    private BytesDeserializer() {
    }

    static StreamBase readStreamBase(ByteBuf response) {
        var streamId = response.readUnsignedIntLE();
        var createdAt = readU64AsBigInteger(response);
        var topicsCount = response.readUnsignedIntLE();
        var size = readU64AsBigInteger(response);
        var messagesCount = readU64AsBigInteger(response);
        var nameLength = response.readByte();
        var name = response.readCharSequence(nameLength, StandardCharsets.UTF_8).toString();

        return new StreamBase(streamId, createdAt, name, size.toString(), messagesCount, topicsCount);
    }

    static StreamDetails readStreamDetails(ByteBuf response) {
        var streamBase = readStreamBase(response);

        List<Topic> topics = new ArrayList<>();
        if (response.isReadable()) {
            topics.add(readTopic(response));
        }

        return new StreamDetails(streamBase, topics);
    }

    public static TopicDetails readTopicDetails(ByteBuf response) {
        var topic = readTopic(response);

        List<Partition> partitions = new ArrayList<>();
        while (response.isReadable()) {
            partitions.add(readPartition(response));
        }

        return new TopicDetails(topic, partitions);
    }

    private static Partition readPartition(ByteBuf response) {
        var partitionId = response.readUnsignedIntLE();
        var createdAt = readU64AsBigInteger(response);
        var segmentsCount = response.readUnsignedIntLE();
        var currentOffset = readU64AsBigInteger(response);
        var size = readU64AsBigInteger(response);
        var messagesCount = readU64AsBigInteger(response);
        return new Partition(partitionId, createdAt, segmentsCount, currentOffset, size.toString(), messagesCount);
    }

    public static Topic readTopic(ByteBuf response) {
        var topicId = response.readUnsignedIntLE();
        var createdAt = readU64AsBigInteger(response);
        var partitionsCount = response.readUnsignedIntLE();
        var messageExpiry = readU64AsBigInteger(response);
        var compressionAlgorithmCode = response.readByte();
        var maxTopicSize = readU64AsBigInteger(response);
        var replicationFactor = response.readByte();
        var size = readU64AsBigInteger(response);
        var messagesCount = readU64AsBigInteger(response);
        var nameLength = response.readByte();
        var name = response.readCharSequence(nameLength, StandardCharsets.UTF_8).toString();
        return new Topic(topicId,
                createdAt,
                name,
                size.toString(),
                messageExpiry,
                CompressionAlgorithm.fromCode(compressionAlgorithmCode),
                maxTopicSize,
                (short) replicationFactor,
                messagesCount,
                partitionsCount);
    }

    public static ConsumerGroupDetails readConsumerGroupDetails(ByteBuf response) {
        var consumerGroup = readConsumerGroup(response);

        List<ConsumerGroupMember> members = new ArrayList<>();
        while (response.isReadable()) {
            members.add(readConsumerGroupMember(response));
        }

        return new ConsumerGroupDetails(consumerGroup, members);
    }

    private static ConsumerGroupMember readConsumerGroupMember(ByteBuf response) {
        var memberId = response.readUnsignedIntLE();
        var partitionsCount = response.readUnsignedIntLE();
        List<Long> partitionIds = new ArrayList<>();
        for (int i = 0; i < partitionsCount; i++) {
            partitionIds.add(response.readUnsignedIntLE());
        }
        return new ConsumerGroupMember(memberId, partitionsCount, partitionIds);
    }

    public static ConsumerGroup readConsumerGroup(ByteBuf response) {
        var groupId = response.readUnsignedIntLE();
        var partitionsCount = response.readUnsignedIntLE();
        var membersCount = response.readUnsignedIntLE();
        var nameLength = response.readByte();
        var name = response.readCharSequence(nameLength, StandardCharsets.UTF_8).toString();
        return new ConsumerGroup(groupId, name, partitionsCount, membersCount);
    }

    public static ConsumerOffsetInfo readConsumerOffsetInfo(ByteBuf response) {
        var partitionId = response.readUnsignedIntLE();
        var currentOffset = readU64AsBigInteger(response);
        var storedOffset = readU64AsBigInteger(response);
        return new ConsumerOffsetInfo(partitionId, currentOffset, storedOffset);
    }

    private static BigInteger readU64AsBigInteger(ByteBuf buffer) {
        var bytesArray = new byte[9];
        buffer.readBytes(bytesArray, 0, 8);
        ArrayUtils.reverse(bytesArray);
        return new BigInteger(bytesArray);
    }

    private static BigInteger readU128AsBigInteger(ByteBuf buffer) {
        var bytesArray = new byte[17];
        buffer.readBytes(bytesArray, 0, 16);
        ArrayUtils.reverse(bytesArray);
        return new BigInteger(bytesArray);
    }

    public static PolledMessages readPolledMessages(ByteBuf response) {
        var partitionId = response.readUnsignedIntLE();
        var currentOffset = readU64AsBigInteger(response);
        var messagesCount = response.readUnsignedIntLE();
        var messages = new ArrayList<Message>();
        while (response.isReadable()) {
            messages.add(readMessage(response));
        }
        return new PolledMessages(partitionId, currentOffset, messages);
    }

    private static Message readMessage(ByteBuf response) {
        var offset = readU64AsBigInteger(response);
        var stateCode = response.readByte();
        var state = MessageState.fromCode(stateCode);
        var timestamp = readU64AsBigInteger(response);
        var id = readU128AsBigInteger(response);
        var checksum = response.readUnsignedIntLE();
        var headersLength = response.readUnsignedIntLE();
        var headers = Optional.<Map<String, HeaderValue>>empty();
        if (headersLength > 0) {
            var headersMap = new HashMap<String, HeaderValue>();
            ByteBuf headersBytes = response.readBytes(Long.valueOf(headersLength).intValue());
            while (headersBytes.isReadable()) {
                var keyLength = headersBytes.readUnsignedIntLE();
                var key = headersBytes.readCharSequence(Long.valueOf(keyLength).intValue(), StandardCharsets.UTF_8).toString();
                var kindCode = headersBytes.readByte();
                var kind = HeaderKind.fromCode(kindCode);
                var valueLength = headersBytes.readUnsignedIntLE();
                var value = headersBytes.readCharSequence(Long.valueOf(valueLength).intValue(), StandardCharsets.UTF_8);
                headersMap.put(key, new HeaderValue(kind, String.valueOf(value)));
            }
            headers = Optional.of(headersMap);
        }
        var payloadLength = response.readUnsignedIntLE();
        var payload = new byte[Long.valueOf(payloadLength).intValue()];
        response.readBytes(payload);
        return new Message(offset, state, timestamp, id, checksum, headers, new String(payload));
    }
}
