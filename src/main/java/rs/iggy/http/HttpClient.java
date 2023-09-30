package rs.iggy.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.iggy.http.error.IggyHttpError;
import rs.iggy.http.error.IggyHttpException;
import java.io.IOException;
import java.util.Optional;

class HttpClient {

    private static final Logger LOG = LoggerFactory.getLogger(HttpClient.class);

    private static final String AUTHORIZATION = "Authorization";
    private final String url;
    private final ObjectMapper objectMapper = ObjectMapperFactory.getInstance();
    private Optional<String> token = Optional.empty();

    HttpClient(String url) {
        this.url = url;
    }

    void setToken(Optional<String> token) {
        this.token = token;
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

    ClassicHttpRequest prepareGetRequest(String path, NameValuePair... params) {
        return ClassicRequestBuilder.get(url + path)
                .setHeader(AUTHORIZATION, getBearerToken())
                .addParameters(params)
                .build();
    }

    ClassicHttpRequest preparePostRequest(String path, Object body) {
        var builder = ClassicRequestBuilder.post(url + path)
                .setHeader(AUTHORIZATION, getBearerToken());
        return addRequestBody(builder, body);
    }

    ClassicHttpRequest preparePutRequest(String path, Object body) {
        var builder = ClassicRequestBuilder.put(url + path)
                .setHeader(AUTHORIZATION, getBearerToken());
        return addRequestBody(builder, body);
    }

    ClassicHttpRequest prepareDeleteRequest(String path, NameValuePair... params) {
        return ClassicRequestBuilder.delete(url + path)
                .setHeader(AUTHORIZATION, getBearerToken())
                .addParameters(params)
                .build();
    }

    private ClassicHttpRequest addRequestBody(ClassicRequestBuilder requestBuilder, Object body) {
        try {
            var encodedBody = objectMapper.writeValueAsString(body);
            LOG.debug("Request body: {}", encodedBody);
            return requestBuilder
                    .setHeader("Content-Type", "application/json")
                    .setEntity(encodedBody)
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

    private String getBearerToken() {
        return token.map(t -> "Bearer " + t).orElse("");
    }

    private static boolean isSuccessful(int statusCode) {
        return statusCode >= 200 && statusCode < 300;
    }
}
