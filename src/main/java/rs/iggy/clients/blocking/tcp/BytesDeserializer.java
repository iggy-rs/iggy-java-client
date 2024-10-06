package rs.iggy.clients.blocking.tcp;

import io.netty.buffer.ByteBuf;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.iggy.stream.StreamBase;
import rs.iggy.stream.StreamDetails;
import rs.iggy.topic.Topic;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

final class BytesDeserializer {

    private static final Logger log = LoggerFactory.getLogger(BytesDeserializer.class);

    private BytesDeserializer() {
    }

    static StreamBase readStreamBase(ByteBuf response) {
        var streamId = response.readUnsignedIntLE();
        var createdAt = readU64ToBigInteger(response);
        var topicsCount = response.readUnsignedIntLE();
        var size = readU64ToBigInteger(response);
        var messagesCount = readU64ToBigInteger(response);
        var nameLength = response.readByte();
        var name = response.readCharSequence(nameLength, StandardCharsets.UTF_8).toString();

        return new StreamBase(streamId, createdAt, name, size.toString(), messagesCount, topicsCount);
    }

    static StreamDetails readStreamDetails(ByteBuf response) {
        var streamBase = readStreamBase(response);

        List<Topic> topics = Collections.emptyList();
        if (response.isReadable()) {
            log.debug("has more data"); //TODO(mm): 6.10.2024 Add topics implementation
        }

        return new StreamDetails(streamBase, topics);
    }

    private static BigInteger readU64ToBigInteger(ByteBuf buffer) {
        var bytesArray = new byte[8];
        buffer.readBytes(bytesArray);
        ArrayUtils.reverse(bytesArray);
        return new BigInteger(bytesArray);
    }

}
