package es.pentagono;

import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.List;

public class InvertedIndex {

    Multimap<String, String[]> index;
    public InvertedIndex(Multimap<String, String[]> index) {
        this.index = index;
    }
    public List<String[]> lookupQuery(String word){
        return new ArrayList<>(this.index.get(word));
    }
    public Multimap<String, String[]> getIndex(){
        return this.index;
    }
}
