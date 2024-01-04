package rs.iggy.clients.blocking.http;

import com.fasterxml.jackson.core.type.TypeReference;
import rs.iggy.clients.blocking.StreamsClient;
import rs.iggy.identifier.StreamId;
import rs.iggy.stream.StreamBase;
import rs.iggy.stream.StreamDetails;
import java.util.List;

class StreamsHttpClient implements StreamsClient {

    private static final String STREAMS = "/streams";
    private final HttpClient httpClient;

    public StreamsHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public StreamDetails getStream(Long streamId) {
        return getStream(StreamId.of(streamId));
    }

    @Override
    public StreamDetails getStream(StreamId streamId) {
        var request = httpClient.prepareGetRequest(STREAMS + "/" + streamId);
        return httpClient.execute(request, StreamDetails.class);
    }

    @Override
    public List<StreamBase> getStreams() {
        var request = httpClient.prepareGetRequest(STREAMS);
        return httpClient.execute(request, new TypeReference<>() {
        });
    }

    @Override
    public void createStream(Long streamId, String name) {
        var request = httpClient.preparePostRequest(STREAMS, new CreateStream(streamId, name));
        httpClient.execute(request);
    }

    @Override
    public void updateStream(Long streamId, String name) {
        updateStream(StreamId.of(streamId), name);
    }

    @Override
    public void updateStream(StreamId streamId, String name) {
        var request = httpClient.preparePutRequest(STREAMS + "/" + streamId, new UpdateStream(name));
        httpClient.execute(request);
    }

    @Override
    public void deleteStream(Long streamId) {
        deleteStream(StreamId.of(streamId));
    }

    @Override
    public void deleteStream(StreamId streamId) {
        var request = httpClient.prepareDeleteRequest(STREAMS + "/" + streamId);
        httpClient.execute(request);
    }

    record CreateStream(Long streamId, String name) {
    }

    record UpdateStream(String name) {
    }
}
