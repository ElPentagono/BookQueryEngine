package es.pentagono.invertedindex.serializers;

import es.pentagono.InvertedIndex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InvertedIndexSerializer implements es.pentagono.invertedindex.InvertedIndexSerializer {

    HashMap<String, List<List<String>>> tsvIndex = new HashMap<>();
    @Override
    public void serialize(InvertedIndex index) {
        index.getIndex().forEach((word,value) ->{
            for(String[] element : value){
                List<String> ocurrence = new ArrayList<>();
                ocurrence.add(element[0] + "\t" + element[1]); //TODO check \n
                addOcurrence(word, ocurrence, tsvIndex);
            }
        });
       System.out.println(tsvIndex);
    }
    private void addOcurrence(String word, List<String> ocurrence, HashMap<String, List<List<String>>> tsvIndex  ) {
        List<List<String>> ocurrences = tsvIndex.get(word);
        if(ocurrences == null){
            tsvIndex.put(word, new ArrayList<>(List.of(ocurrence)));
            return;
        }
        ocurrences.add(ocurrence);
    }
}
