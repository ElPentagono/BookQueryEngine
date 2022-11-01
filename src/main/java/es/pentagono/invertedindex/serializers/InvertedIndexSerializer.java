package es.pentagono.invertedindex.serializers;

import es.pentagono.InvertedIndex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvertedIndexSerializer implements es.pentagono.invertedindex.InvertedIndexSerializer {


    @Override
    public Map<String, String> serialize(InvertedIndex invertedIndex) {
        Map<String, List<String[]>> index = invertedIndex.getIndex();
        Map<String, String> result = new HashMap<>();
        for (String key : index.keySet())
            result.put(key, processWord(index.get(key)));
        return result;
    }

    private String processWord(List<String[]> occurrences) {
        StringBuilder builder = new StringBuilder();
        for (String[] occurrence : occurrences)
            builder.append("\n" + occurrence[0] + "\t" + occurrence[1]);
        return builder.toString();
    }

}
