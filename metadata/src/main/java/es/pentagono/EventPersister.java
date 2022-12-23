package es.pentagono;

public interface EventPersister extends Persister {
    void persist(String event);
}
