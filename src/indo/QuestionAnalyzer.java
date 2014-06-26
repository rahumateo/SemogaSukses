package indo;

import indo.objects.Dictionaries;
import indo.objects.Gazettier;
import indo.objects.PreAnalyzedQuestion;
import indo.objects.Translations;
import info.ephyra.io.MsgPrinter;
import info.ephyra.nlp.VerbFormConverter;
import info.ephyra.nlp.indices.IrregularVerbs;
import info.ephyra.nlp.indices.WordFrequencies;
import info.ephyra.nlp.semantics.ontologies.Ontology;
import info.ephyra.nlp.semantics.ontologies.WordNet;
import info.ephyra.questionanalysis.AnalyzedQuestion;
import info.ephyra.questionanalysis.QuestionAnalysis;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rahmat Hidayah SB
 */
public class QuestionAnalyzer {
//    private static HashSet<String> stopwords;

    private static PreAnalyzedQuestion paq;
//    private static AnalyzedQuestion aq;

    public static void main(String[] ebc) throws FileNotFoundException, IOException {
//        commandLine();
        qFile();
    }

    private static void commandLine() throws IOException {
        init();
        KeywordExtractor.loadStopwords();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String question;

        System.out.println("\nEnter your question:");
        try {
            while ((question = br.readLine().trim()) != null) {
                if (question.equalsIgnoreCase("exit")) {
                    System.exit(0);
                }

                String q = analyze(question);
                System.out.println("q : " + q);
                System.out.println("\nEnter your question:");
            }
        } catch (IOException ex) {
            Logger.getLogger(QuestionAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void init() throws IOException {
        System.out.println("---------Loading NE-----------");
        EntityRecognizer.loadListEntities("res/indo/lists/");
        System.out.println("---------Loading NE Done!-----------\n");
    }

    /**
     * Untuk eksperimen tipe 1, menggunakan Most Frequent Word
     * @param question
     * @return analyzedQ
     */
    public static String analyze(String question) {
        String qpoint = QuestionClassifier.detectQWord(question.toLowerCase().trim());
        String[] qBuilder;
        if (qpoint.length() == 0) {
            return "";
        }

        String qw = QuestionClassifier.getQw();
        String[] at = QuestionClassifier.getAt();

        qpoint = qpoint.replaceAll("  ", "").trim();
        KeywordExtractor.tokenize(qpoint);

        String[] tokens = KeywordExtractor.getKeywords();
        String[] tags = KeywordExtractor.getTags();

        Translations[] translated = KeywordTranslator.translate(tokens, tags);
//        for (Translations x : translated) {
//            System.out.println("awal: ");
//            for (String ar : x.getTranslated()) {
//                System.out.print(" - " + ar);
//            }
//            System.out.println("");
//        }

//        for(int ii = 0; ii < tokens.length; ii++){
//            System.out.println("token : " + tokens[ii] + "<" + tags[ii] + ">");
//        }
        translated = toSimplePast(translated, tags);

        String ques = qw;
        /**
         * Print attribute
         */
//        System.out.println("--------------------");
//        System.out.println("Question : " + question);
//        for (String x : at) {
//            System.out.println(x);
//        }
        String[] engTok = new String[tokens.length];
        for (int ii = 0; ii < translated.length; ii++) {
            Translations x = translated[ii];
//            System.out.println("akhir : ");
//            for (String ar : x.getTranslated()) {
//                System.out.println(" - " + ar);
//            }
            engTok[ii] = x.getMostFrequent();
            ques += x.getMostFrequent().trim() + " ";
        }

        ques = ques.trim();
        ques = ques.replaceAll("  ", " ");
        ques += "?";

//        System.out.println("final question : " + ques);
//        System.out.println("--------------------");
//        for(int ii = 0; ii < engTok.length; ii++){
//            System.out.println("kw l: " + engTok[ii] + "<" + tags[ii] + ">");
//        }
        ArrayList<String> akw = new ArrayList<>();
        ArrayList<String> atags = new ArrayList<>();

        for (int ii = 0; ii < tags.length; ii++) {
            if (!tags[ii].equalsIgnoreCase("stopword")) {
                akw.add(engTok[ii]);
                atags.add(tags[ii]);
            }
        }

        engTok = akw.toArray(new String[akw.size()]);
        tags = atags.toArray(new String[atags.size()]);

        
        paq = new PreAnalyzedQuestion(question, ques, qw, qpoint, engTok, tags, at);
//        aq = new AnalyzedQuestion(question, engTok, at);

        return ques;
    }

    /**
     * Untuk eksperimen tipe 2, menggunakan seluruh translasi sebagai keywords
     * @param question
     * @return analyzedQ
     */
    public static String analyze2(String question) {
        String qpoint = QuestionClassifier.detectQWord(question.toLowerCase().trim());
        String[] qBuilder;
        if (qpoint.length() == 0) {
            return "";
        }

        String qw = QuestionClassifier.getQw();
        String[] at = QuestionClassifier.getAt();

        qpoint = qpoint.replaceAll("  ", "").trim();
        KeywordExtractor.tokenize(qpoint);

        String[] tokens = KeywordExtractor.getKeywords();
        String[] tags = KeywordExtractor.getTags();

        Translations[] translated = KeywordTranslator.translate(tokens, tags);
        
        translated = toSimplePast(translated, tags);

        String ques = qw;
        String[] engTok = new String[tokens.length];
        for (int ii = 0; ii < translated.length; ii++) {
            Translations x = translated[ii];
            engTok[ii] = x.getMostFrequent();
            ques += x.getMostFrequent().trim() + " ";
        }

        ques = ques.trim();
        ques = ques.replaceAll("  ", " ");
        ques += "?";

        // Array of Kewywords
        ArrayList<String> akw = new ArrayList<>();
        // Array of Kewywords
        ArrayList<String> atags = new ArrayList<>();

        for (int ii = 0; ii < tags.length; ii++) {
            if (!tags[ii].equalsIgnoreCase("stopword")) {
                akw.add(engTok[ii]);
                atags.add(tags[ii]);
            }
        }

        engTok = akw.toArray(new String[akw.size()]);
        tags = atags.toArray(new String[atags.size()]);
        
//        System.out.println("######################");
//        System.out.println("#    Print atribut   #");
//        System.out.println("* Question : " + question);
//        System.out.println("* F question : " + ques);
//        System.out.println("* question word : " + qw);
//        System.out.println("* question point : " + qpoint);
//        System.out.println("* keywords & tags : ");
//        for(int ii = 0; ii < engTok.length; ii++){
//            System.out.println("   - " + engTok[ii] + " <" + tags[ii] + ">");
//        }
//        System.out.println("* AnswerType : ");
//        for(String x : at){
//            System.out.println("   - " + x);
//        }
//        System.out.println("* Translated : ");
//        for (int ii = 0; ii < tokens.length; ii++) {
//            System.out.println("   awal: " + tokens[ii]);
//            Translations x = translated[ii];
//            System.out.print("     akhir: ");
//            for (String ar : x.getTranslated()) {
//                System.out.print(ar + "; ");
//            }
//            System.out.println("");
//        }
//        System.out.println("######################");

        paq = new PreAnalyzedQuestion(question, ques, qw, qpoint, engTok, tags, at, translated);
//        aq = new AnalyzedQuestion(question, engTok, at);

        return ques;
    }

    public static String analyze() {
        String question = "Siapa penulis dari The Absence of Space and Time dan Absolute Power?";
        return analyze(question);
    }

    public static PreAnalyzedQuestion getPAQ() {
        return paq;
    }

    private static void qFile() {
        BufferedReader oku;
        BufferedWriter yaz;
        try {
            oku = new BufferedReader(new FileReader("res/test/indo/clefidQ.txt"), 201);

            init();
            while (true) {
                String question = oku.readLine().trim();

                if (question.isEmpty() || question.equalsIgnoreCase("exit")) {
                    System.exit(0);
                }
                System.out.println("*** Reading question : " + question + " ***");

                String q = analyze(question);
            }
        } catch (FileNotFoundException x) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IOexception : " + e);
        }
    }

    private static Translations[] toSimplePast(Translations[] tr, String[] tags) {
        Translations[] pastForms = new Translations[tr.length];
        for (int idx = 0; idx < tr.length; idx++) {
            if (tags[idx].equalsIgnoreCase("VB")) {
                HashSet<String> words = tr[idx].getTranslated();
                for (String aWord : words) {
                    String[] pf = VerbFormConverter.infinitiveToSimplePast(aWord);
                    if (pf.length > 0) {
                        pastForms[idx] = new Translations();
                        for (String aP : pf) {
                            pastForms[idx].addTranslation(aP);
                        }
                    } else {
                        pastForms[idx] = tr[idx];
                    }
                }
            } else {
                pastForms[idx] = tr[idx];
            }
        }

        return pastForms;
    }

}
