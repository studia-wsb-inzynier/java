package pl.maropce.etutor.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {

    @Value("${date-time.format}")
    private String dateTimeFormat;

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);

            builder.serializers(new LocalDateTimeSerializer(formatter));
            builder.deserializers(new LocalDateTimeDeserializer(formatter));
        };
    }
}
