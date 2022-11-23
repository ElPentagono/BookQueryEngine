package es.pentagono.serializers;

import com.google.common.collect.Multimap;
import es.pentagono.InvertedIndex;
import es.pentagono.InvertedIndexSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TsvInvertedIndexSerializer implements InvertedIndexSerializer {


    @Override
    public Map<String, String> serialize(InvertedIndex invertedIndex) {
        Multimap<String, String[]> index = invertedIndex.getIndex();
        Map<String, String> result = new HashMap<>();
        for (String key : index.keySet())
            result.put(key, processWord(new ArrayList<>(index.get(key))));
        return result;
    }

    private String processWord(List<String[]> occurrences) {
        StringBuilder builder = new StringBuilder();
        for (String[] occurrence : occurrences)
            builder.append("\n" + occurrence[0] + "\t" + occurrence[1]);
        return builder.toString();
    }

}
