package rs.iggy.stream;

import rs.iggy.identifier.StreamId;
import java.util.List;

public interface StreamsClient {

    StreamDetails getStream(Long streamId);

    StreamDetails getStream(StreamId streamId);

    List<StreamBase> getStreams();

    void createStream(Long streamId, String name);

    void updateStream(Long streamId, String name);

    void updateStream(StreamId streamId, String name);

    void deleteStream(Long streamId);

    void deleteStream(StreamId streamId);

}
