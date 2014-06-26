/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indo.objects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Rahmat Hidayah SB
 */
public class Dictionaries {

    private static HashMap<String, HashMap> dictionaries;

    public Dictionaries() {
        dictionaries = new HashMap<>();
    }

    public void clearDictionaries() {
        dictionaries = null;
    }

    public Set<String> getCategories() {
        if (!dictionaries.isEmpty()) {
            return dictionaries.keySet();
        }
        return null;
    }

    public void addDictionary(String category, HashMap<String,String> aDict) {
        dictionaries.put(category, aDict);
    }

    public HashMap<String, String> getDictionary(String category) {
        if (categoryExists(category)) {
            return dictionaries.get(category);
        }
        return null;
    }

    private boolean categoryExists(String category) {
        return dictionaries.containsKey(category);
    }

    public boolean isEmpty() {
        return dictionaries.isEmpty();
    }

    public int size() {
        return dictionaries.size();
    }
}
