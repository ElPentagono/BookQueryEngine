package es.pentagono;

import java.util.Map;

public interface Command {
    String execute(Map<String,String> parameters);
}
