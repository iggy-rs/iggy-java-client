package rs.iggy.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import rs.iggy.http.error.IggyHttpError;
import rs.iggy.http.error.IggyHttpException;
import java.io.IOException;

class HttpClient {

    private final String url;
    private final ObjectMapper objectMapper = ObjectMapperFactory.getInstance();

    HttpClient(String url) {
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
            return client.execute(request, response -> {
                handleErrorResponse(response);
                return objectMapper.readValue(response.getEntity().getContent(), type);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    String executeWithStringResponse(ClassicHttpRequest request) {
        try (var client = HttpClients.createDefault()) {
            return client.execute(request, response -> {
                handleErrorResponse(response);
                return new String(response.getEntity().getContent().readAllBytes());
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void execute(ClassicHttpRequest request) {
        try (var client = HttpClients.createDefault()) {
            client.execute(request, response -> {
                handleErrorResponse(response);
                return "";
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    ClassicHttpRequest prepareGetRequest(String path) {
        return ClassicRequestBuilder.get(url + path).build();
    }

    ClassicHttpRequest preparePostRequest(String path, Object body) {
        return addRequestBody(ClassicRequestBuilder.post(url + path), body);
    }

    ClassicHttpRequest preparePutRequest(String path, Object body) {
        return addRequestBody(ClassicRequestBuilder.put(url + path), body);
    }

    ClassicHttpRequest prepareDeleteRequest(String path) {
        return ClassicRequestBuilder.delete(url + path).build();
    }

    private ClassicHttpRequest addRequestBody(ClassicRequestBuilder requestBuilder, Object body) {
        try {
            return requestBuilder
                    .setHeader("Content-Type", "application/json")
                    .setEntity(objectMapper.writeValueAsString(body))
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private void handleErrorResponse(ClassicHttpResponse response) throws IOException {
        if (!isSuccessful(response.getCode())) {
            var error = objectMapper.readValue(response.getEntity().getContent(), IggyHttpError.class);
            throw new IggyHttpException(error);
        }
    }

    private static boolean isSuccessful(int statusCode) {
        return statusCode >= 200 && statusCode < 300;
    }
}
