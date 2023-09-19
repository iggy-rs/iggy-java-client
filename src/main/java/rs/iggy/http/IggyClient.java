package rs.iggy.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import rs.iggy.system.ClientInfo;
import rs.iggy.system.ClientInfoDetails;
import rs.iggy.system.Stats;
import rs.iggy.system.SystemClient;
import java.io.IOException;
import java.util.List;

public class IggyClient implements SystemClient {

    private final String url;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new Jdk8Module())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, true)
            .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    public IggyClient(String url) {
        this.url = url;
    }

    @Override
    public Stats getStats() {
        var request = prepareGetRequest("/stats");
        return execute(request, Stats.class);
    }

    @Override
    public List<ClientInfo> getClients() {
        var request = prepareGetRequest("/clients");
        return execute(request, new TypeReference<>() {
        });
    }

    @Override
    public ClientInfoDetails getClient(String clientId) {
        var request = prepareGetRequest("/clients/" + clientId);
        return execute(request, ClientInfoDetails.class);
    }

    @Override
    public String ping() {
        var request = prepareGetRequest("/ping");
        return execute(request);
    }

    private <T> T execute(ClassicHttpRequest request, Class<T> clazz) {
        return execute(request, objectMapper.constructType(clazz));
    }

    private <T> T execute(ClassicHttpRequest request, TypeReference<T> typeReference) {
        return execute(request, objectMapper.constructType(typeReference));
    }

    private <T> T execute(ClassicHttpRequest request, JavaType type) {
        try (var client = HttpClients.createDefault()) {
            return client.execute(request, response -> objectMapper.readValue(response
                    .getEntity()
                    .getContent(), type));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String execute(ClassicHttpRequest request) {
        try (var client = HttpClients.createDefault()) {
            return client.execute(request, response -> new String(response.getEntity().getContent().readAllBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private ClassicHttpRequest prepareGetRequest(String path) {
        return ClassicRequestBuilder.get(url + path).build();
    }

}
