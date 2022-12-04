package es.pentagono.serializers;

import com.google.gson.Gson;
import es.pentagono.Event;
import es.pentagono.EventSerializer;

public class GsonEventSerializer implements EventSerializer {
    @Override
    public String serialize(Event event) {
        Gson gson = new Gson();
        return gson.toJson(event);
    }
}
