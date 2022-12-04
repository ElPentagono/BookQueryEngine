import es.pentagono.serializers.JsonMetadataSerializer;
import es.pentagono.serializers.TsvEventSerializer;
import es.pentagono.sources.GutenbergSource;
import es.pentagono.stores.FSDocumentStore;
import es.pentagono.tasks.DownloadTask;
import org.junit.Test;

public class TaskTest {
    @Test
    public void should_run_download_task() {
        DownloadTask task = new DownloadTask().from(new GutenbergSource()).into(new FSDocumentStore(new JsonMetadataSerializer(), new TsvEventSerializer()));
        task.execute();
    }
}
