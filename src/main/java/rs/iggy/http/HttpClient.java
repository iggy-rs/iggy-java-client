package rs.iggy.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import java.io.IOException;

class HttpClient {

    private final String url;
    private final ObjectMapper objectMapper = ObjectMapperFactory.getInstance();

    public HttpClient(String url) {
        this.url = url;
    }

    <T> T execute(ClassicHttpRequest request, Class<T> clazz) {
        return execute(request, objectMapper.constructType(clazz));
    }

    <T> T execute(ClassicHttpRequest request, TypeReference<T> typeReference) {
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

    String execute(ClassicHttpRequest request) {
        try (var client = HttpClients.createDefault()) {
            return client.execute(request, response -> new String(response.getEntity().getContent().readAllBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    ClassicHttpRequest prepareGetRequest(String path) {
        return ClassicRequestBuilder.get(url + path).build();
    }
}
