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
public class Gazettier {
    private HashMap<String, HashSet> gazet;
    
    public Gazettier() {
        gazet = new HashMap<>();
    }
    
    public void clearGazettier(){
        gazet = null;
    }
    
    public void addGazettier(String category, HashSet<String> ga){
        gazet.put(category, ga);
    }
    
    public Set<String> getCategories(){
        if (!gazet.isEmpty()) {
            return gazet.keySet();
        }
        return null;
    }
    
    public HashSet<String> getGazet(String category){
        if(categoryExists(category)){
            return gazet.get(category);
        }
        return null;
    }
    
    public boolean categoryExists(String category){
        return gazet.containsKey(category);
    }
    
    public boolean isEmpty(){
        return gazet.isEmpty();
    }
    
    public int size(){
        return gazet.size();
    }
}
