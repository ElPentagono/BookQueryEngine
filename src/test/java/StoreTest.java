import es.pentagono.Metadata;
import es.pentagono.crawler.serializers.JsonMetadataSerializer;
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
    }
}
