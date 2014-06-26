/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indo;

import indo.objects.Gazettier;
import info.ephyra.questionanalysis.Term;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import posTagger.Exception_Exception;
import posTagger.IOException_Exception;
import posTagger.POSTagger;
import posTagger.POSTagger_Service;

/**
 *
 * @author Rahmat Hidayah SB
 */
public class KeywordExtractor {
    private static HashSet<String> stopwords = new HashSet<>();
    private static String[] keywords;
    private static String[] tags;
    private static Term[] terms;

    public KeywordExtractor() {
        keywords = null;
        tags = null;
    }

    public static String[] getKeywords() {
        return keywords;
    }

    public static String[] getTags() {
        return tags;
    }
    
    public static Term[] getTerms(){
        ArrayList<Term> arrTerm = new ArrayList<>();
        
        for(int idx = 0; idx < keywords.length; idx++){
            Term temp = new Term(keywords[idx], tags[idx]);
            arrTerm.add(temp);
        }
        
        terms = (Term[]) arrTerm.toArray(new Term[arrTerm.size()]);
        return terms;
    }

    public static void tokenize(String sentence) {
        Gazettier gazet = EntityRecognizer.getGazettier();

        /**
         * check phrase dll di Gazettier
         */
        if (gazet != null && !gazet.isEmpty()) {
            sentence = checkPhrase(sentence);
        }

        HashMap<String, String> tagFWS = getPOSTag(sentence);
        sentence = sentence.replaceAll("  ", "").trim();
        keywords = sentence.split(" ");
        tags = new String[keywords.length];

        for (int ii = 0; ii < keywords.length; ii++) {
            String token = keywords[ii];
            /**
             * check if exists in Gazettier, if it exists, use the tag in
             * Gazettier
             */
            if (token.startsWith("@")) {
                String temp = "NN";
                Pattern p = Pattern.compile("@(.*)%(.*)");
                Matcher m = p.matcher(token);
                if (m.matches()) {
                    temp = m.group(2).replace("_", " ");
                    keywords[ii] = "@" + temp;
                    temp = m.group(1);
                }

                tags[ii] = temp;
            } /**
             * If it doesn't exists in Gazettier, use Postagger
             */
            else if (tagFWS.containsKey(token)) {
                tags[ii] = tagFWS.get(token).toUpperCase();
            }
             /**
             * If it doesn't exists in PosTagger, use default tag as "NN"
             */
            else {
                tags[ii] = "NN";
            }
            
            if (stopwords.contains(keywords[ii])){
                tags[ii] = "stopword";
            }
        }
    }

    /**
     * Check phrase dll kalo dia ada di Gazettier, maka dia dijadiin satu key
     *
     * @param sentence
     * @return sentence yang telah diproses pada Gazettier
     */
    private static String checkPhrase(String sentence) {
        Gazettier aGazet = EntityRecognizer.getGazettier();
        sentence = sentence + " ";
        Set<String> categoryList = aGazet.getCategories();
        Object[] ctList = categoryList.toArray();
        Arrays.sort(ctList);
        for (Object cat : ctList) { //System.out.println("o : " + cat.toString());
            Set<String> setList = aGazet.getGazet((String) cat);
            Object[] aList = setList.toArray();
            Arrays.sort(aList);
            for (Object li : aList) { //System.out.println("li : " + li.toString());
                if ((sentence.contains(" " + li.toString() + " ")) && (li.toString().length() > 0)) { //System.out.println("found : " + li);
                    String temp = li.toString().replace(" ", "_");
                    sentence = sentence.replaceFirst((String) li, "@" + cat + "%" + temp);
                }
            }
        }

        return sentence;
    }

    private static HashMap<String, String> getPOSTag(String sentence) {
        HashMap<String, String> htag = new HashMap<>();
        try {
            POSTagger_Service service = new POSTagger_Service();
            POSTagger port = service.getPOSTaggerPort();
            String result = port.getPOSTag(sentence);

            JSONObject aJSON = XML.toJSONObject(result); //        System.out.println("xml : " + aJSON);
            aJSON = aJSON.getJSONObject("document"); //        System.out.println("--- new xml : " + aJSON);
            JSONArray anArray;
            if ((anArray = aJSON.optJSONArray("element")) != null) {
                for (int ii = 0; ii < anArray.length(); ii++) {
                    aJSON = anArray.getJSONObject(ii); //                    System.out.println("JSON : " + aJSON.getString("word") + " - " + aJSON.getString("postag"));
                    htag.put(aJSON.getString("word"), aJSON.getString("postag"));
                }
            } else {
                aJSON = aJSON.optJSONObject("element");
                htag.put(aJSON.getString("word"), aJSON.getString("postag"));
            }
        } catch (IOException_Exception | Exception_Exception ex) {
            Logger.getLogger(KeywordExtractor.class.getName()).log(Level.SEVERE, null, ex);
            htag.put("NN", "NN");
            return htag;
        }
        return htag;
    }
        
    public static void loadStopwords() throws FileNotFoundException, IOException {
        BufferedReader oku;
        if (!stopwords.isEmpty()) {
            return;
        }
        System.out.println("  ...loading Indonesian Stopwords");
        oku = new BufferedReader(new FileReader("res/indo/stopwords_id.txt"));
        String line;
        while (oku.ready()) {
            line = oku.readLine().trim().toLowerCase();
            stopwords.add(line);
        }
    }
    
    public static void main(String[] args) {
        String q = "siapakah presiden *amerika_serikat dan *united_nation";
        tokenize(q);

        for (int ii = 0; ii < keywords.length; ii++) {
            System.out.println("id" + ii + " : " + keywords[ii] + " ; " + tags[ii]);
        }
    }
}
