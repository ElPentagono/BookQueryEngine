package es.pentagono.commands;

import es.pentagono.Command;

import java.util.Map;

public class StatsTypeCommand implements Command {
    @Override
    public String execute(Map<String, String> parameters) {
        String req = parameters.get(":type");
        return req;
    }
}
