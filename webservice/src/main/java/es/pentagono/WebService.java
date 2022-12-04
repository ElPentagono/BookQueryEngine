package es.pentagono;

import spark.Request;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WebService {
    private final Map<String, Command> commands;

    public WebService() {
        this.commands = new HashMap<>();
    }

    public void add(String route, Command command) {
        commands.put(route, command);
    }

    public void start() {
        Spark.port(8080);
        for (String route : commands.keySet())
            Spark.get(route, (req, res) -> commands.get(route).execute(parametersIn(req)));
    }

    private Map<String, String> parametersIn(Request request) {
        Map<String, String> result = request.queryMap().toMap().entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()[0]));
        result.putAll(request.params());
        return result;
    }
}
