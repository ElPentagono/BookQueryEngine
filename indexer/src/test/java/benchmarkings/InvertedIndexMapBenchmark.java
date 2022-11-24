package benchmarkings;

import es.pentagono.Document;
import es.pentagono.Metadata;
import es.pentagono.tokenizers.GutenbergTokenizer;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 2)
@Warmup(iterations = 3, time = 2)
@Measurement(iterations = 3, time = 2)
public class InvertedIndexMapBenchmark {

    private static final Random random = new Random();

    @Benchmark
    public static void InvertedIndexMapConsult() throws URISyntaxException, IOException {
        executeWith(new InvertedIndexBuilderMap(new GutenbergTokenizer()));
    }

    private static void executeWith(InvertedIndexBuilderMap builder) throws URISyntaxException, IOException {
        URL contentURL = GutenbergTokenizer.class.getClassLoader().getResource("contentTest.txt");
        String content = String.join("\n", Files.readAllLines(Path.of(contentURL.toURI())));
        Document document = new Document("1",new Metadata(null,null,null,null), content);
        InvertedIndexMap index = builder.build(document);
        for (int i = 0; i <1000; i++)
            index.lookupQuery(document.content.split(" ")[random.nextInt()]);
    }
}
