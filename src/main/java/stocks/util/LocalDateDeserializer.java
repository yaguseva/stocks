package stocks.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class LocalDateDeserializer extends StdDeserializer<LocalDate> {
    protected LocalDateDeserializer() {
        super(LocalTime.class);
    }

    @Override
    public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return Instant.ofEpochMilli(jp.readValueAs(Long.class)).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
