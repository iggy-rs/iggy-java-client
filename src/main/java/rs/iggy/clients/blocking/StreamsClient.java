package rs.iggy.clients.blocking;

import rs.iggy.identifier.StreamId;
import rs.iggy.stream.StreamBase;
import rs.iggy.stream.StreamDetails;
import java.util.List;
import java.util.Optional;

public interface StreamsClient {

    StreamDetails getStream(Long streamId);

    StreamDetails getStream(StreamId streamId);

    List<StreamBase> getStreams();

    void createStream(Optional<Long> streamId, String name);

    void updateStream(Long streamId, String name);

    void updateStream(StreamId streamId, String name);

    void deleteStream(Long streamId);

    void deleteStream(StreamId streamId);

}
