import es.pentagono.Document;
import es.pentagono.Metadata;
import es.pentagono.crawler.persisters.FSDocumentPersister;
import es.pentagono.crawler.serializers.JsonMetadataSerializer;
import es.pentagono.crawler.stores.FSDocumentStore;
import org.junit.Test;

public class StoreTest {
    @Test
    public void metadata_serializer() {
        JsonMetadataSerializer metadataSerializer = new JsonMetadataSerializer();
        System.out.println(metadataSerializer.serialize(new Metadata(
                "Moby Dick",
                "Herman Melville",
                "1851",
                "1851")));
    }

    @Test
    public void store_test() {
        FSDocumentStore store = new FSDocumentStore(new JsonMetadataSerializer());
        store.store(new Document(
                "1",
                new Metadata(
                        "Moby Dick",
                        "Herman Melville",
                        "1851",
                        "1851"),
                "Hello good morning"));

    }
}
