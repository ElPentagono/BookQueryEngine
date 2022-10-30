package es.pentagono;

import java.util.HashMap;
import java.util.List;

public class InvertedIndex {

    HashMap<String, List<int[]>> index;
    public InvertedIndex(HashMap<String, List<int[]>> index) {
        this.index = index;
    }
    public List<int[]> lookup_query(String word){
        return this.index.get(word);
    }

}
