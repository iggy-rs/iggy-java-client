package rs.iggy.http;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

final class ObjectMapperFactory {

    private ObjectMapperFactory() {
    }

    private static final ObjectMapper INSTANCE = new ObjectMapper()
            .registerModule(new Jdk8Module())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, true)
            .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    static ObjectMapper getInstance() {
        return INSTANCE;
    }

}
