/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.ephyra.querygeneration.generators;

import indo.objects.Translations;
import info.ephyra.answerselection.filters.AnswerPatternFilter;
import info.ephyra.answerselection.filters.AnswerTypeFilter;
import info.ephyra.answerselection.filters.FactoidsFromPredicatesFilter;
import info.ephyra.nlp.semantics.Predicate;
import info.ephyra.querygeneration.Query;
import info.ephyra.questionanalysis.AnalyzedQuestion;
import info.ephyra.questionanalysis.Term;
import info.ephyra.util.StringUtils;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Rahmat Hidayah SB
 */
public class BagOfTranslations extends QueryGenerator {

    /**
     * Score assigned to "bag of words" queries.
     */
    private static final float SCORE = 1.5f;
    /**
     * Answer extraction techniques for this query type.
     */
    private static final String[] EXTRACTION_TECHNIQUES = {
        AnswerTypeFilter.ID,
        FactoidsFromPredicatesFilter.ID
    };

    /**
     * Generates a "bag of words" query from the keywords in the question
     * string.
     *
     * @param aq analyzed question
     * @return <code>Query</code> objects
     */
    public Query[] generateQueries(AnalyzedQuestion aq) {
		// only generate a query if the answer type is known, predicates could
        // be extracted or the question is not a factoid question
        String[] ats = aq.getAnswerTypes();
        Predicate[] ps = aq.getPredicates();
        if (ats.length == 0 && ps.length == 0 && aq.isFactoid()) {
            return new Query[0];
        }

        String queryString = "";

        for (Translations x : aq.getTranslations()) {
            HashSet<String> translated = x.getTranslated();
            String temp = "";
            if (translated.size() > 1) {
                temp = "(";
                for (String ar : translated) {
                    temp += ar + " OR ";
                }
                temp += "#";
                temp = temp.replaceFirst(" OR #", ")");
                
            } else {
                for (String ar : translated) {
                    temp = ar;
                }
            }
            queryString += " " + temp;
        }
        queryString = queryString.trim();
//                System.out.println("query : " + queryString);

        // create query, set answer types
        Query[] queries = new Query[1];
        queries[0] = new Query(queryString, aq, SCORE);
        queries[0].setExtractionTechniques(EXTRACTION_TECHNIQUES);

        return queries;
    }
}
