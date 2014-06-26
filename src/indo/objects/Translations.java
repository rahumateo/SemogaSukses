/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indo.objects;

import info.ephyra.nlp.indices.WordFrequencies;
import info.ephyra.nlp.semantics.ontologies.WordNet;
import java.util.HashSet;

/**
 *
 * @author Rahmat Hidayah SB
 */
public class Translations {

    private HashSet<String> translated;

    public Translations() {
        translated = new HashSet<>();
    }

    public void addTranslation(String tr) {
        translated.add(tr);
    }

    public HashSet<String> getTranslated() {
        return translated;
    }

    public String getMostFrequent() {        
        String x = "";
        int fr = 0;
        int tfr;
        for (String temp : translated) {
            tfr = WordFrequencies.lookup(temp);
            if (tfr == 0){
                x = temp;
            } else if(tfr > fr) {
                x = temp;
                fr = tfr;
            }
        }        
        return x;
    }
}
