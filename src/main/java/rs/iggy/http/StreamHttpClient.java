package rs.iggy.http;

import com.fasterxml.jackson.core.type.TypeReference;
import rs.iggy.stream.StreamBase;
import rs.iggy.stream.StreamClient;
import rs.iggy.stream.StreamDetails;
import java.util.List;

class StreamHttpClient implements StreamClient {

    private final HttpClient httpClient;
    private static final String STREAMS = "/streams";

    public StreamHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public StreamDetails getStream(Long streamId) {
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
        var request = httpClient.preparePutRequest(STREAMS + "/" + streamId, new UpdateStream(name));
        httpClient.execute(request);
    }

    @Override
    public void deleteStream(Long streamId) {
        var request = httpClient.prepareDeleteRequest(STREAMS + "/" + streamId);
        httpClient.execute(request);
    }

    record CreateStream(Long streamId, String name) {
    }

    record UpdateStream(String name) {
    }
}
