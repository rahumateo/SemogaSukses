/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indo;

import indo.objects.Dictionaries;
import indo.objects.Gazettier;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Rahmat Hidayah SB
 */
public class EntityRecognizer {

    private static Dictionaries listdictionary = new Dictionaries();

    private static Gazettier aGazetttier = new Gazettier();

    /**
     * Initializes the list-based NE taggers.
     *
     * @param listDirectory path of the directory the list files are located in
     */
    public static void loadListEntities(String listDirectory) {
        if (!aGazetttier.isEmpty()) {
            return;
        }

        System.out.println("  ...loading Indonesian Gazettier");

        File[] listFiles = new File(listDirectory).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".lst");
            }
        });
        Arrays.sort(listFiles);

        for (File aFile : listFiles) {
            loadGazettier(aFile);

            String listName = aFile.getName();
            listName = listName.substring(0, (listName.length() - 4));

            if (listName.endsWith("#")) {
                loadDictionary(aFile);
                listName = listName.substring(0, (listName.length() - 1)); //System.out.println("    ...for " + listName);
            }

            if (listName.contains("_")) {
                String[] tname = listName.split("_");
                Pattern p = Pattern.compile("([0-9]+)([A-Za-z#]*)");
                Matcher m = p.matcher(tname[1]);
                if (m.matches()) {
                    listName = m.group(2);
                }
            }
            System.out.println("    ...for " + listName);
        }
        System.out.println("... Loading Entities done!");
    }

    private static void loadGazettier(File fileName) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            HashSet<String> wordSet = new HashSet<>();

            String[] line;
            while (in.ready()) { //System.out.println("file : " + fileName);
                line = in.readLine().split(";");
                wordSet.add(line[0].toLowerCase()); //System.out.println("      .......... dicts : " + line[0]);
            }

            String category = fileName.getName().substring(0, fileName.getName().length() - 4);
//            if (category.endsWith("#")) {
//                category = category.substring(0, (category.length() - 1)); //System.out.println("    ...for " + listName);
//            } 
//            System.out.println("done : " + category);

            aGazetttier.addGazettier(category, wordSet);

        } catch (FileNotFoundException e) {
            System.out.println("Fail to load Gazettier : " + e);
        } catch (IOException ex) {
            Logger.getLogger("Fail to read Gazettier on file : " + fileName + ".lst");
        }
    }

    private static void loadDictionary(File fileName) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            HashMap<String, String> aDictionary = new HashMap<>();

            String category = fileName.getName().substring(0, fileName.getName().length() - 5);
            //System.out.println("dictionary : " + category);

            String[] line;
            while (in.ready()) { //System.out.println("file : " + fileName);
                line = in.readLine().split(";");
                aDictionary.put(line[0].toLowerCase(), line[1].toLowerCase()); //System.out.println(category + " : " + line[0] + " - " + line[1]);
            }
            if (category.endsWith("#")) {
                category = category.substring(0, (category.length() - 1)); //System.out.println("    ...for " + listName);
            }

            listdictionary.addDictionary(category, aDictionary);

        } catch (FileNotFoundException e) {
            System.out.println("Fail to load Gazettier : " + e);
        } catch (IOException ex) {
            Logger.getLogger("Fail to read Gazettier on file : " + fileName + ".lst");
        }
    }

    public static Dictionaries getDictionaries() {
        return listdictionary;
    }

    public static Gazettier getGazettier() {
        return aGazetttier;
    }

    public static void main(String[] args) {
        loadListEntities("res/indo/lists/");

//        cekGazettier();
        cekDictionary();
    }

    public static void cekGazettier() {
        System.out.println("--- checking Gazettier ---");
        Set<String> categoryList = aGazetttier.getCategories();
        Object[] ctList = categoryList.toArray();
        Arrays.sort(ctList);
        for (Object li : ctList) {
            System.out.println("list : " + (String) li);
        }

        Set<String> airportSetList = aGazetttier.getGazet("GEO_3AIRPORT");
        Object[] apList = airportSetList.toArray();
        Arrays.sort(apList);
        for (Object li : apList) {
            System.out.println("list : " + (String) li);
        }
    }

    public static void cekDictionary() {
        System.out.println("--- checking Dictionary ---");
        Set<String> categoryList = listdictionary.getCategories();
        Object[] ctList = categoryList.toArray();
        Arrays.sort(ctList);
        for (Object li : ctList) {
            System.out.println("list : " + (String) li);
        }

        HashMap<String, String> countryList = listdictionary.getDictionary("GEO_1COUNTRY");
        Set<String> cl = countryList.keySet();
        Object[] cList = cl.toArray();
        Arrays.sort(cList);
        for (Object li : cList) {
            System.out.println("     " + (String) li + " - " + countryList.get(li));
        }
    }
}
