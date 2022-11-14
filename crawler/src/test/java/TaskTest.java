import es.pentagono.serializers.JsonMetadataSerializer;
import es.pentagono.serializers.TsvEventSerializer;
import es.pentagono.sources.GutenbergSource;
import es.pentagono.stores.FileSystemDocumentStore;
import es.pentagono.tasks.DownloadTask;
import org.junit.Test;

public class TaskTest {
    @Test
    public void should_run_download_task() {
        DownloadTask task = new DownloadTask().from(new GutenbergSource()).into(new FileSystemDocumentStore(new JsonMetadataSerializer(), new TsvEventSerializer()));
        task.execute();
    }
}
