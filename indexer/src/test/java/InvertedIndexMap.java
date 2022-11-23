import java.util.List;
import java.util.Map;

public class InvertedIndexMap {

    Map<String, List<String[]>> index;
    public InvertedIndexMap(Map<String, List<String[]>> index) {
        this.index = index;
    }
    public List<String[]> lookupQuery(String word){
        return this.index.get(word);
    }
    public Map<String, List<String[]>> getIndex(){
        return this.index;
    }
}