/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indo;

import dictionary.AccessLimitExceededException_Exception;
import dictionary.EnId_bilingualdictionary;
import dictionary.EnId_bilingualdictionaryService_Impl;
import dictionary.InvalidParameterException_Exception;
import dictionary.NoAccessPermissionException_Exception;
import dictionary.NoValidEndpointsException_Exception;
import dictionary.ProcessFailedException_Exception;
import dictionary.ServerBusyException_Exception;
import dictionary.ServiceNotActiveException_Exception;
import dictionary.ServiceNotFoundException_Exception;
import dictionary.Translation;
import dictionary.UnsupportedLanguagePairException_Exception;
import dictionary.UnsupportedMatchingMethodException_Exception;
import indo.objects.Dictionaries;
import indo.objects.Translations;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Rahmat Hidayah SB
 */
public class KeywordTranslator {

    private static HashSet<String> inf = new HashSet<>();
    private static HashSet<String> sp = new HashSet<>();

    private Translations[] trans;

    public static Translations[] translate(String[] tokens, String[] tags) {
        Dictionaries dicts = EntityRecognizer.getDictionaries();
        Translations[] translated = new Translations[tokens.length];

        for (int idx = 0; idx < tokens.length; idx++) { //System.out.println("=====");
            Translations tempTr = new Translations();
            
            if (tags[idx].endsWith("#")) { //System.out.println("Dictionary");
                /**
                 * Translate using online dictionary
                 */
                String dictName = tags[idx].replace("#", "");
                HashMap<String, String> aDict = dicts.getDictionary(dictName);
                
                tokens[idx] = tokens[idx].replaceFirst("@", "");
                String ttemp = aDict.get(tokens[idx]);                
                tempTr.addTranslation(ttemp);
                translated[idx] = tempTr;
            } else if(tokens[idx].startsWith("@")){
                tempTr.addTranslation(tokens[idx].replaceFirst("@", ""));
                translated[idx] = tempTr;
            } else { //System.out.println("(ONLINE)");
                /**
                 * Translate using online dictionary
                 */
                
                String[] tres = translateUsingFWS(tokens[idx]);
                
                if (tres.length != 0) { //System.out.println("ada");
                    for (String at : tres) {
                        tempTr.addTranslation(at);
                    }
                    translated[idx] = tempTr;
                } else { //System.out.println("gaada");
                    tempTr.addTranslation(tokens[idx]);
                    translated[idx] = tempTr;
                }
            }
            if(tags[idx].equals("VB")){
                
            }
        }
        
        return translated;
    }

    private static String[] translateUsingFWS(String word) {
        String[] result;

        try {
            EnId_bilingualdictionary diService = new EnId_bilingualdictionaryService_Impl().getEnId_bilingualdictionary();
            Translation[] diPort = diService.search("Id", "En", word, "complete");
            HashSet<String> res = new HashSet<>();
            ArrayList<String> res_list = new ArrayList<>();

            for (Translation tr : diPort) {
                String[] tempTrans = tr.getTargetWords();
                for (String x : tempTrans) {
                    String[] sz = x.split(" ");
                    if (sz.length == 1 && res.add(x)) {
                        res_list.add(x);
                    }
                }
            }

            result = (String[]) res_list.toArray(new String[res.size()]);

            for (String x : result) {
                String aTemp = "";
                if (x.endsWith("ing")) {
                    aTemp = x.substring(0, x.length() - 3);
                    String doubleConsonant = x.substring(0, x.length() - 4);
                    if (res.contains(doubleConsonant)) {
                        res.remove(x);
                    } else if (res.contains(aTemp + "e")) {
                        res.remove(x);
                    }
                } else if (x.endsWith("ed")) {
                    aTemp = x.substring(0, x.length() - 2);
                    if (res.contains(aTemp)) {
                        res.remove(x);
                    } else if (res.contains(aTemp + "e")) {
                        res.remove(x);
                    } else {
                        aTemp = x.substring(0, x.length() - 3);
                    }
                } else if (x.endsWith("ies")) {
                    aTemp = x.substring(0, x.length() - 3) + "y";
                } else if (x.endsWith("s")) {
                    aTemp = x.substring(0, x.length() - 2);
                    if (res.contains(aTemp)) {
                        res.remove(x);
                    } else {
                        aTemp = x.substring(0, x.length() - 1);
                    }
                }
                if (res.contains(aTemp)) {
                    res.remove(x);
                }
            }
            result = (String[]) res.toArray(new String[res.size()]);

        } catch (ProcessFailedException_Exception | NoValidEndpointsException_Exception | ServiceNotActiveException_Exception | ServiceNotFoundException_Exception | AccessLimitExceededException_Exception | ServerBusyException_Exception | NoAccessPermissionException_Exception | RemoteException ex) {
            System.out.println(ex);
            Logger.getLogger(KeywordTranslator.class.getName()).log(Level.SEVERE, null, ex);
            return new String[0];
        } catch (UnsupportedMatchingMethodException_Exception ex) {
            System.out.println(ex);
            Logger.getLogger(KeywordTranslator.class.getName()).log(Level.SEVERE, null, ex);
            return new String[0];
        } catch (UnsupportedLanguagePairException_Exception ex) {
            System.out.println(ex);
            Logger.getLogger(KeywordTranslator.class.getName()).log(Level.SEVERE, null, ex);
            return new String[0];
        } catch (InvalidParameterException_Exception ex) {
            System.out.println(ex);
            Logger.getLogger(KeywordTranslator.class.getName()).log(Level.SEVERE, null, ex);
            return new String[0];
        }

        return result;
    }

    private static String[] translateUsingGlosbe(String word) {
        String[] result = null;
        try {
            String urlStr = "http://glosbe.com/gapi/translate?from=in&dest=en&format=json&phrase=" + word + "&pretty=true";
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn.getResponseCode() != 200) { //System.out.println("kosong " + conn.getResponseCode() + " : " + conn.getResponseMessage());
                return result;
            } else { // Buffer the result into a string
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                String JString = "";
                while ((line = rd.readLine()) != null) {
                    JString += line;
                }

                JSONObject main_json = new JSONObject(JString); //System.out.println("main : " + main_json);
                JSONArray arr = main_json.optJSONArray("tuc");
                if (arr.length() > 0) {
                    JSONObject temp;
                    // Excluding duplicate in translation
                    HashSet<String> res = new HashSet<>();
                    ArrayList<String> res_list = new ArrayList<>();
                    for (int ii = 0; ii < arr.length(); ii++) {
                        temp = arr.getJSONObject(ii); //System.out.println("aJSON : " + temp);
                        if (temp.has("phrase")) {
                            temp = temp.getJSONObject("phrase"); //System.out.println("--- new aJSON : " + temp);
                            String def = temp.getString("text");
                            def = def.replaceFirst("^to ", "");
                            String[] sz = def.split(" ");
                            if (sz.length == 1) {
                                if (res.add(def)) {
                                    res_list.add(def);
                                }
                            }
                        }
                    }

                    result = (String[]) res_list.toArray(new String[res.size()]);

                    for (String x : result) {
                        String aTemp = "";
                        if (x.endsWith("ing")) {
                            aTemp = x.substring(0, x.length() - 3);
                            String doubleConsonant = x.substring(0, x.length() - 4);
                            if (res.contains(doubleConsonant)) {
                                res.remove(x);
                            } else if (res.contains(aTemp + "e")) {
                                res.remove(x);
                            }
                        } else if (x.endsWith("ed")) {
                            aTemp = x.substring(0, x.length() - 2);
                            if (res.contains(aTemp)) {
                                res.remove(x);
                            } else if (res.contains(aTemp + "e")) {
                                res.remove(x);
                            } else {
                                aTemp = x.substring(0, x.length() - 3);
                            }
                        } else if (x.endsWith("ies")) {
                            aTemp = x.substring(0, x.length() - 3) + "y";
                        } else if (x.endsWith("s")) {
                            aTemp = x.substring(0, x.length() - 2);
                            if (res.contains(aTemp)) {
                                res.remove(x);
                            } else {
                                aTemp = x.substring(0, x.length() - 1);
                            }
                        }
                        if (res.contains(aTemp)) {
                            res.remove(x);
                        }
                    }
                    result = (String[]) res.toArray(new String[res.size()]);
                }
            }
        } catch (IOException | JSONException e) {
            System.out.println("Exception : " + e);
        }

        return result;
    }
    
    public static void main(String[] abc) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String in = br.readLine();
            while (!in.isEmpty()) {
                String[] tr = translateUsingFWS(in);
//                String[] tr = translateUsingGlosbe(in);
                
                System.out.println("size : " + tr.length);

                for (String r : tr) {
                    System.out.println("t : " + r);
                }

                in = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("ex : " + e);
        }
    }
}