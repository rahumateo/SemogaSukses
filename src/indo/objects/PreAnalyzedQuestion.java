package indo.objects;

import info.ephyra.questionanalysis.Term;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Rahmat Hidayah SB
 */
public class PreAnalyzedQuestion {
    private static String q_indo;
    private static String q_en;
    private static String qWord;
    private static String qPoint;
    private static String[] keywords;
    private static String[] tags;
    private static String[] aType;
    private static Translations[] transl;
    
    public PreAnalyzedQuestion(String quid, String quen, String q_Word, String q_Point, String[] kws, String[] tgs, String[] aT) {
        PreAnalyzedQuestion.q_indo = quid;
        PreAnalyzedQuestion.q_en = quen;
        PreAnalyzedQuestion.qWord = q_Word;
        PreAnalyzedQuestion.qPoint = q_Point;
        PreAnalyzedQuestion.keywords = kws;
        PreAnalyzedQuestion.tags = tgs;
        PreAnalyzedQuestion.aType = aT;
//        for(int ii = 0; ii < keywords.length; ii++){
//            System.out.println("kw b: " + keywords[ii] + "<" + tags[ii] + ">");
//        }
//        updateKW();
//        for(int ii = 0; ii < keywords.length; ii++){
//            System.out.println("kw : " + keywords[ii] + "<" + tags[ii] + ">");
//        }
    }
    
    public PreAnalyzedQuestion(String quid, String quen, String q_Word, String q_Point, String[] kws, String[] tgs, String[] aT, Translations[] trl) {
        this(quid, quen, q_Word, q_Point, kws, tgs, aT);
        PreAnalyzedQuestion.transl = trl;
//        
//        ArrayList<Term> art = new ArrayList<>();
//        for(int ii = 0; ii < transl.length; ii++){
//            HashSet<String> ttr = transl[ii].getTranslated();
//            for(String x : ttr){
//                if(tags[ii].length() > 0){
//                    art.add(new Term(x, "NN"));
//                } else {
//                    art.add(new Term(x, tags[ii]));
//                }
//            }
//        }
//        
//        terms = art.toArray(new Term[art.size()]);
    }
    
    public static String getQ_indo() {
        return q_indo;
    }

    public static String getQ_en() {
        return q_en;
    }

    public static String getqWord() {
        return qWord;
    }

    public static String getqPoint() {
        return qPoint;
    }

    public static String[] getKeywords() {
        return keywords;
    }

    public static String[] getTags() {
        return tags;
    }

    public static String[] getaType() {
        return aType;
    }

    public static Translations[] getTransl() {
        return transl;
    }
}
