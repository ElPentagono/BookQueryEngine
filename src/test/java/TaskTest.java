import es.pentagono.crawler.serializers.JsonMetadataSerializer;
import es.pentagono.crawler.serializers.TsvEventSerializer;
import es.pentagono.crawler.sources.GutenbergSource;
import es.pentagono.crawler.stores.FileSystemDocumentStore;
import es.pentagono.crawler.tasks.DownloadTask;
import org.junit.Test;

public class TaskTest {
    @Test
    public void should_run_download_task() {
        DownloadTask task = new DownloadTask().from(new GutenbergSource()).into(new FileSystemDocumentStore(new JsonMetadataSerializer(), new TsvEventSerializer()));
        task.execute();
    }
}
