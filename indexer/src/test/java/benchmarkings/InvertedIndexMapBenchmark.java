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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 2)
@Warmup(iterations = 5, time = 2)
@Measurement(iterations = 15, time = 2)
public class InvertedIndexMapBenchmark {

    private static final Random random = new Random();

    @State(Scope.Benchmark)
    public static class BenchmarkState {

        public List<Document> documents = new ArrayList<>();
        public List<String[]> tokenizedContents = new ArrayList<>();

        public BenchmarkState() {
            try {
                for (int i = 1; i < 11; i++) {
                    URL contentURL = GutenbergTokenizer.class.getClassLoader().getResource("contentTest" + i + ".txt");
                    String content = String.join("\n", Files.readAllLines(Path.of(contentURL.toURI())));
                    this.documents.add(new Document("1", new Metadata(null, null, null, null), content));
                    this.tokenizedContents.add(new GutenbergTokenizer().tokenize(documents.get(i - 1).content).keySet().toArray(new String[0]));
                }
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Benchmark
    public void InvertedIndexMapConsult(BenchmarkState state) {
        executeWith(new InvertedIndexBuilderMap(new GutenbergTokenizer()), state.documents, state.tokenizedContents);
    }

    public void executeWith(InvertedIndexBuilderMap builder, List<Document> documents, List<String[]> tokenizedContents) {
        for (int i = 0; i < 10; i++) {
            InvertedIndexMap index = builder.build(documents.get(i));
            index.lookupQuery(tokenizedContents.get(i)[random.nextInt(tokenizedContents.get(i).length)]);
        }
    }
}
