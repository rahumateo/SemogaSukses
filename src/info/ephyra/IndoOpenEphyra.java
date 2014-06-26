package info.ephyra;

//What percentage of people in Italy relies on television for information?
import indo.KeywordExtractor;
import indo.QuestionAnalyzer;
import indo.objects.PreAnalyzedQuestion;
import info.ephyra.answerselection.AnswerSelection;
import info.ephyra.answerselection.filters.AnswerTypeFilter;
import info.ephyra.answerselection.filters.DuplicateFilter;
import info.ephyra.answerselection.filters.FactoidSubsetFilter;
import info.ephyra.answerselection.filters.FactoidsFromPredicatesFilter;
import info.ephyra.answerselection.filters.PredicateExtractionFilter;
import info.ephyra.answerselection.filters.QuestionKeywordsFilter;
import info.ephyra.answerselection.filters.ScoreCombinationFilter;
import info.ephyra.answerselection.filters.ScoreNormalizationFilter;
import info.ephyra.answerselection.filters.ScoreSorterFilter;
import info.ephyra.answerselection.filters.StopwordFilter;
import info.ephyra.answerselection.filters.TruncationFilter;
import info.ephyra.io.Logger;
import info.ephyra.io.MsgPrinter;
import info.ephyra.nlp.LingPipe;
import info.ephyra.nlp.NETagger;
import info.ephyra.nlp.OpenNLP;
import info.ephyra.nlp.SnowballStemmer;
import info.ephyra.nlp.StanfordNeTagger;
import info.ephyra.nlp.StanfordParser;
import info.ephyra.nlp.indices.FunctionWords;
import info.ephyra.nlp.indices.IrregularVerbs;
import info.ephyra.nlp.indices.Prepositions;
import info.ephyra.nlp.indices.WordFrequencies;
import info.ephyra.nlp.semantics.ontologies.Ontology;
import info.ephyra.nlp.semantics.ontologies.WordNet;
import info.ephyra.querygeneration.Query;
import info.ephyra.querygeneration.QueryGeneration;
import info.ephyra.querygeneration.generators.BagOfTermsG;
import info.ephyra.querygeneration.generators.BagOfWordsG;
import info.ephyra.querygeneration.generators.PredicateG;
import info.ephyra.querygeneration.generators.QuestionInterpretationG;
import info.ephyra.querygeneration.generators.QuestionReformulationG;
import info.ephyra.questionanalysis.AnalyzedQuestion;
import info.ephyra.questionanalysis.AnswerTypeTester;
import info.ephyra.questionanalysis.QuestionAnalysis;
import info.ephyra.questionanalysis.QuestionNormalizer;
import info.ephyra.search.Result;
import info.ephyra.search.Search;
import info.ephyra.search.searchers.IndriDocumentKM;
import info.ephyra.search.searchers.IndriKM;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

/**
 * <code>OpenEphyra</code> is an open framework for question answering (QA).
 *
 * @author Nico Schlaefer
 * @version 2008-03-23
 */
public class IndoOpenEphyra {

    static BufferedReader oku;
    static BufferedWriter yaz;
    static BufferedWriter cevap;
    static Date aDate;
    static long time;
    static long begin;

    static String fileRead = "res/test/clefidQ.txt";
    static String fileWrite = "res/test/clefidA.txt";
    static String fileCevap = "res/test/clefIndoProp.csv";

    /**
     * Factoid question type.
     */
    protected static final String FACTOID = "FACTOID";

    /**
     * Maximum number of factoid answers.
     */
    protected static final int FACTOID_MAX_ANSWERS = 1;
    /**
     * Absolute threshold for factoid answer scores.
     */
    protected static final float FACTOID_ABS_THRESH = 0;
    /**
     * Relative threshold for list answer scores (fraction of top score).
     */
    protected static final float LIST_REL_THRESH = 0.1f;

    /**
     * Serialized classifier for score normalization.
     */
    public static final String NORMALIZER
            = "res/scorenormalization/classifiers/"
            + "AdaBoost70_"
            + "Score+Extractors_"
            + "TREC10+TREC11+TREC12+TREC13+TREC14+TREC15+TREC8+TREC9"
            + ".serialized";

    /**
     * The directory of Ephyra, required when Ephyra is used as an API.
     */
    protected String dir;

    /**
     * Entry point of Ephyra. Initializes the engine and starts the command line
     * interface.
     *
     * @param args command line arguments are ignored
     */
    public static void main(String[] args) {
        // enable output of status and error messages
        MsgPrinter.enableStatusMsgs(true);
        MsgPrinter.enableErrorMsgs(true);

        // set log file and enable logging
        Logger.setLogfile("log/IndoOpenEphyra");
        Logger.enableLogging(true);
        try {
            oku = new BufferedReader(new FileReader(fileRead), 201);
            yaz = new BufferedWriter(new FileWriter(fileWrite));
            yaz.write("Answers:\n");
            cevap = new BufferedWriter(new FileWriter(fileCevap));
            cevap.write("Answers:\n");
            // , q , qw , qen, kw, at, time
            cevap.write(",Answer Types, Answer, Time Spent\n");

            yaz.close();
            cevap.close();

            aDate = new Date();
            printLog("*** System starts ***");
            // initialize Ephyra and start command line interface
            (new IndoOpenEphyra()).commandLine();

            oku.close();
        } catch (IOException e) {
        } finally {
        }
    }

    /**
     * <p>
     * Creates a new instance of Ephyra and initializes the system.</p>
     *
     * <p>
     * For use as a standalone system.</p>
     */
    protected IndoOpenEphyra() throws IOException {
        this("");
        QuestionAnalyzer.init();
        KeywordExtractor.loadStopwords();
    }

    /**
     * <p>
     * Creates a new instance of Ephyra and initializes the system.</p>
     *
     * <p>
     * For use as an API.</p>
     *
     * @param dir directory of Ephyra
     */
    public IndoOpenEphyra(String dir) {
        
        this.dir = dir;

        MsgPrinter.printInitializing();

        // create tokenizer
        MsgPrinter.printStatusMsg("Creating tokenizer...");
        if (!OpenNLP.createTokenizer(dir
                + "res/nlp/tokenizer/opennlp/EnglishTok.bin.gz")) {
            MsgPrinter.printErrorMsg("Could not create tokenizer.");
        }

        // create sentence detector
        MsgPrinter.printStatusMsg("Creating sentence detector...");
        if (!OpenNLP.createSentenceDetector(dir
                + "res/nlp/sentencedetector/opennlp/EnglishSD.bin.gz")) {
            MsgPrinter.printErrorMsg("Could not create sentence detector.");
        }
        LingPipe.createSentenceDetector();

        // create stemmer
        MsgPrinter.printStatusMsg("Creating stemmer...");
        SnowballStemmer.create();

        // create part of speech tagger
        MsgPrinter.printStatusMsg("Creating POS tagger...");
        if (!OpenNLP.createPosTagger(
                dir + "res/nlp/postagger/opennlp/tag.bin.gz",
                dir + "res/nlp/postagger/opennlp/tagdict")) {
            MsgPrinter.printErrorMsg("Could not create OpenNLP POS tagger.");
        }

        // create chunker
        MsgPrinter.printStatusMsg("Creating chunker...");
        if (!OpenNLP.createChunker(dir
                + "res/nlp/phrasechunker/opennlp/EnglishChunk.bin.gz")) {
            MsgPrinter.printErrorMsg("Could not create chunker.");
        }

        try {
            StanfordParser.initialize();
        } catch (Exception e) {
            MsgPrinter.printErrorMsg("Could not create Stanford parser.");
        }

        // create named entity taggers
        MsgPrinter.printStatusMsg("Creating NE taggers...");
        NETagger.loadListTaggers(dir + "res/nlp/netagger/lists/");
        NETagger.loadRegExTaggers(dir + "res/nlp/netagger/patterns.lst");
        MsgPrinter.printStatusMsg("  ...loading models");
        if (!StanfordNeTagger.isInitialized() && !StanfordNeTagger.init()) {
            MsgPrinter.printErrorMsg("Could not create Stanford NE tagger.");
        }
        MsgPrinter.printStatusMsg("  ...done");

        // create WordNet dictionary
        MsgPrinter.printStatusMsg("Creating WordNet dictionary...");
        if (!WordNet.initialize(dir
                + "res/ontologies/wordnet/file_properties.xml")) {
            MsgPrinter.printErrorMsg("Could not create WordNet dictionary.");
        }

        // load function words (numbers are excluded)
        MsgPrinter.printStatusMsg("Loading function verbs...");
        if (!FunctionWords.loadIndex(dir
                + "res/indices/functionwords_nonumbers")) {
            MsgPrinter.printErrorMsg("Could not load function words.");
        }

        // load prepositions
        MsgPrinter.printStatusMsg("Loading prepositions...");
        if (!Prepositions.loadIndex(dir
                + "res/indices/prepositions")) {
            MsgPrinter.printErrorMsg("Could not load prepositions.");
        }

        // load irregular verbs
        MsgPrinter.printStatusMsg("Loading irregular verbs...");
        if (!IrregularVerbs.loadVerbs(dir + "res/indices/irregularverbs")) {
            MsgPrinter.printErrorMsg("Could not load irregular verbs.");
        }

        // load word frequencies
        MsgPrinter.printStatusMsg("Loading word frequencies...");
        if (!WordFrequencies.loadIndex(dir + "res/indices/wordfrequencies")) {
            MsgPrinter.printErrorMsg("Could not load word frequencies.");
        }

        // load query reformulators
        MsgPrinter.printStatusMsg("Loading query reformulators...");
        if (!QuestionReformulationG.loadReformulators(dir
                + "res/reformulations/")) {
            MsgPrinter.printErrorMsg("Could not load query reformulators.");
        }

        // load answer types
        MsgPrinter.printStatusMsg("Loading answer types...");
        if (!AnswerTypeTester.loadAnswerTypes(dir
                + "res/answertypes/patterns/answertypepatterns")) {
            MsgPrinter.printErrorMsg("Could not load answer types.");
        }
    }

    /**
     * Reads a line from the command prompt.
     *
     * @return user input
     */
    protected String readLine() {
        try {
            return new java.io.BufferedReader(new java.io.InputStreamReader(System.in)).readLine();
        } catch (java.io.IOException e) {
            return new String("");
        }
    }

    /**
     * Initializes the pipeline for factoid questions.
     */
    protected void initFactoid() {
        // question analysis
        Ontology wordNet = new WordNet();
        // - dictionaries for term extraction
        QuestionAnalysis.clearDictionaries();
        QuestionAnalysis.addDictionary(wordNet);
        // - ontologies for term expansion
        QuestionAnalysis.clearOntologies();
        QuestionAnalysis.addOntology(wordNet);

        // query generation
        QueryGeneration.clearQueryGenerators();
        QueryGeneration.addQueryGenerator(new BagOfWordsG());
        QueryGeneration.addQueryGenerator(new BagOfTermsG());
        QueryGeneration.addQueryGenerator(new PredicateG());
        QueryGeneration.addQueryGenerator(new QuestionInterpretationG());
        QueryGeneration.addQueryGenerator(new QuestionReformulationG());

        // search
        // - knowledge miners for unstructured knowledge sources
        Search.clearKnowledgeMiners();

        for (String[] indriIndices : IndriKM.getIndriIndices()) {
            Search.addKnowledgeMiner(new IndriDocumentKM(indriIndices, false));
        }

        // - knowledge annotators for (semi-)structured knowledge sources
        Search.clearKnowledgeAnnotators();

        // answer extraction and selection
        // (the filters are applied in this order)
        AnswerSelection.clearFilters();
        // - answer extraction filters
        AnswerSelection.addFilter(new AnswerTypeFilter());
//        AnswerSelection.addFilter(new FactoidsFromPredicatesFilter());
        AnswerSelection.addFilter(new TruncationFilter());
        // - answer selection filters
        AnswerSelection.addFilter(new StopwordFilter());
        AnswerSelection.addFilter(new QuestionKeywordsFilter());
        AnswerSelection.addFilter(new DuplicateFilter());
        AnswerSelection.addFilter(new ScoreSorterFilter());
    }

    /**
     * Runs the pipeline and returns an array of up to <code>maxAnswers</code>
     * results that have a score of at least <code>absThresh</code>.
     *
     * @param aq analyzed question
     * @param maxAnswers maximum number of answers
     * @param absThresh absolute threshold for scores
     * @return array of results
     */
    protected Result[] runPipeline(AnalyzedQuestion aq, int maxAnswers,
            float absThresh) {
        // query generation
        MsgPrinter.printGeneratingQueries();
        Query[] queries = QueryGeneration.getQueries(aq);

        // search
        MsgPrinter.printSearching();
        Result[] results = Search.doSearch(queries);

        // answer selection
        MsgPrinter.printSelectingAnswers();
        results = AnswerSelection.getResults(results, maxAnswers, absThresh);

        return results;
    }

    /**
     * Returns the directory of Ephyra.
     *
     * @return directory
     */
    public String getDir() {
        return dir;
    }

    /**
     * <p>
     * A command line interface for Ephyra.</p>
     *
     * <p>
     * Repeatedly queries the user for a question, asks the system the question
     * and prints out and logs the results.</p>
     *
     * <p>
     * The command <code>exit</code> can be used to quit the program.</p>
     */
    public void commandLine() throws IOException {
        // initialize pipeline
        initFactoid();
        String type = FACTOID;  // default type
        printLog("*** Finish Initializing ***");

        begin = System.currentTimeMillis();

        while (true) {
            // query user for question, quit if user types in "exit"
            MsgPrinter.printQuestionPrompt();
//            String question = readLine().trim();
            begin = System.currentTimeMillis();
            String question = oku.readLine().trim();
//            printLog("\n*** Reading Question : " + question + " ***");
            question = QuestionAnalyzer.analyze(question);
            if (question.equalsIgnoreCase("exit")) {
                System.exit(0);
            }

            PreAnalyzedQuestion aPAQ = QuestionAnalyzer.getPAQ();

//            yaz = new BufferedWriter(new FileWriter(fileWrite, true));
//
//            String[] atype = aPAQ.getaType();
//            String at = "";
//            for (String x : atype) {
//                at = "-" + x + " ";
//            }
//            System.out.println("atype : " + at + "\n");
//
//            yaz.write("type : " + at + "\n");
//            yaz.close();
            
//            printQuestionProp();

            // ask question
            Result[] results = new Result[0];
            Logger.logFactoidStart(question);
            results = askFactoid(aPAQ, FACTOID_MAX_ANSWERS,
                    FACTOID_ABS_THRESH);
            Logger.logResults(results);
            Logger.logFactoidEnd();

            // print answers
            MsgPrinter.printAnswers(results);
            printAnswers(question, results);
            printLog("*** Finish Answering A Question ***");
        }
    }

    /**
     * Asks Ephyra a factoid question and returns up to <code>maxAnswers</code>
     * results that have a score of at least <code>absThresh</code>.
     *
     * @param paq
     * @param maxAnswers maximum number of answers
     * @param absThresh absolute threshold for scores
     * @return array of results
     */
    public Result[] askFactoid(PreAnalyzedQuestion paq, int maxAnswers,
            float absThresh) throws IOException {

        // analyze question
        MsgPrinter.printAnalyzingQuestion();
        AnalyzedQuestion aq = QuestionAnalysis.analyze(paq);

        // get answers
        Result[] results = runPipeline(aq, maxAnswers, absThresh);
        printQuestionProp(aq.getAnswerTypes(), results);
        
        return results;
    }

    private static void printLog(String message) throws IOException {
        yaz = new BufferedWriter(new FileWriter(fileWrite, true));

        System.out.println(message + " : " + getTimestamp());
        yaz.write(message + " : " + aDate.toString() + "\n");
        if (message.contains("Finish")) {
            System.out.println(" -- Time started : " + timestampFormatter.format(time));
            System.out.println(" -- Time finished : " + getTimestamp());
            long dur = (System.currentTimeMillis() - time) / 1000;
            System.out.println("*** Time spent : " + dur + " seconds ***");
            yaz.write("*** Time spent : " + dur + " seconds ***\n");
        }
        yaz.close();
        time = System.currentTimeMillis();
    }

    /**
     * the DateFormat object used in getTimespamt
     */
    private static SimpleDateFormat timestampFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * @return	a timestamp String for logging
     */
    public static synchronized String getTimestamp() {
        return timestampFormatter.format(System.currentTimeMillis());
    }

    public static synchronized void printAnswers(String question, Result[] results) throws IOException {
        yaz = new BufferedWriter(new FileWriter(fileWrite, true));

        yaz.write(" --                  Answer:                  --\n");
        for (int i = 0; i < results.length; i++) {
            yaz.write("[" + (i + 1) + "]\t" + results[i].getAnswer() + "\n");
            yaz.write("\tScore: " + results[i].getScore() + "\n");
            if (results[i].getDocID() != null) {
                yaz.write("\tDocument: " + results[i].getDocID() + "\n");
            }
        }
        yaz.write(" --               end of answer               --\n");
        yaz.close();
    }

    public static void printSystemEnds() throws IOException {
        yaz = new BufferedWriter(new FileWriter(fileWrite, true));
        cevap = new BufferedWriter(new FileWriter(fileCevap, true));

        yaz.write("All finished : " + " : " + aDate.toString() + "\n");
        long temp = System.currentTimeMillis();
        System.out.println("All finished : " + getTimestamp());
        System.out.println("Time spent for all questions : " + (temp - begin) / 1000 + " seconds");

        yaz.write("\nAll finished : " + getTimestamp());
        yaz.write("\nTime spent for all questions : " + (temp - begin) / 1000 + " seconds");
        cevap.write("," + (temp - begin) / 1000 + "\n");

        cevap.close();
        yaz.close();
    }
    
    private static void printQuestionProp(String[] at, Result[] results) throws IOException {
        cevap = new BufferedWriter(new FileWriter(fileCevap, true));
        
        cevap.write(",'");
        for (String aty : at) {
            cevap.write("-" + aty + " ");
        }
        cevap.write(",");
        if(results.length > 0) {
            Result re = results[0];
            String res = re.getAnswer().replaceAll(",", "-");
            cevap.write(res);
        }
        long time = (System.currentTimeMillis() - begin) / 1000;
        cevap.write("," + time);
        cevap.newLine();
        System.out.println("time spent : " + time + " seconds");

        cevap.close();
    }
}
// Who is the president of the US?
