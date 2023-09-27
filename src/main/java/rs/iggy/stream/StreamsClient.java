package rs.iggy.stream;

import java.util.List;

public interface StreamsClient {

    StreamDetails getStream(Long streamId);

    List<StreamBase> getStreams();

    void createStream(Long streamId, String name);

    void updateStream(Long streamId, String name);

    void deleteStream(Long streamId);

}
