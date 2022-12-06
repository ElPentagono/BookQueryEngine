package es.pentagono.serializers;

import com.google.gson.Gson;
import es.pentagono.Appearance;
import es.pentagono.AppearanceSerializer;

public class GsonAppearanceSerializer implements AppearanceSerializer {

    @Override
    public String serialize(Appearance appearance) {
        return new Gson().toJson(appearance, Appearance.class);
    }
}
