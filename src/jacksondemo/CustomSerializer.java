package jacksondemo;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CustomSerializer extends JsonSerializer<List<Booking>>{
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");
    
    

    @Override
    public void serialize(List<Booking> t, JsonGenerator jg, SerializerProvider sp) throws IOException {
        jg.writeStartArray();
    }
        
    
}
