/*
 * Esta clase permite utilizar la api de freeling para tratamiento de
 * lenguaje natural de textos
 */
package org.semanticwb.nlp.MorphoSyntacticAnalysis;
/**
 * @author vieyra
 * samuel.vieyra@infotec.com.mx
 */
import edu.upc.freeling.*;

public class FreelingParser {

    // Modify this line to be your FreeLing installation directory
    private static final String FREELINGDIR = "/usr/local";
    private static final String DATA = FREELINGDIR + "/share/freeling/";
    private static final String LANG = "es";
    
    private MacoOptions op;
    private Tokenizer tk;
    private Splitter sp;
    private Maco mf;
    private HmmTagger tg;
    private ChartParser parser;
    private DepTxala dep;
    private UkbWrap dis;
    private Nec neclass;
    
    private static FreelingParser instance;
    
    public static FreelingParser getInstance(){
        if(instance == null){
            instance = new FreelingParser();
        }
        return instance;
    }
    

    public FreelingParser() {
        System.loadLibrary("freeling_javaAPI");
        Util.initLocale( "default" );
        createMacoOptions();
        createAnalyzers();

    }

    public FreelingParser(String sentence) {
        System.loadLibrary("morfo_java");

        createMacoOptions();
        createAnalyzers();

        analyzeSentence(sentence);
    }

    private void createMacoOptions() {
        op = new MacoOptions(LANG);
        op.setActiveModules(false, true, true, true, true, true, true, true, true, true, false );
        op.setDataFiles(
                "",
                DATA + LANG + "/locucions.dat",
                DATA + LANG + "/quantities.dat",
                DATA + LANG + "/afixos.dat",
                DATA + LANG + "/probabilitats.dat",
                DATA + LANG + "/dicc.src",
                DATA + LANG + "/np.dat",
                DATA + "common/punct.dat",
                DATA + LANG + "/corrector/corrector.dat");
    }

    private void createAnalyzers() {
        tk = new Tokenizer(DATA + LANG + "/tokenizer.dat");
        sp = new Splitter(DATA + LANG + "/splitter.dat");
        mf = new Maco(op);

        tg = new HmmTagger(LANG, DATA + LANG + "/tagger.dat", true, 2);
        parser = new ChartParser(DATA + LANG + "/chunker/grammar-chunk.dat");
        dep =  new DepTxala( DATA + LANG + "/dep/dependences.dat",
            parser.getStartSymbol() );
        
        neclass = new Nec( DATA + LANG + "/nec/nec-ab.dat" );
        dis = new UkbWrap( DATA + LANG + "/ukb.dat" );
    }

    public ListSentence analyzeSentence(String sentence) {
        // tokenize
        ListWord l = tk.tokenize(sentence);
        // split sentences
        ListSentence ls = sp.split(l, false);
        // morphological analysis
        mf.analyze(ls);                    
        // PoS tagging
        tg.analyze(ls);                       
        //Disambiguator
        //dis.analyze(ls);
        // Chunk parser
        parser.analyze(ls);
        // Dependency parser
        dep.analyze(ls);
        return ls;
    }

    
    public static void main(String argv[]) {
        FreelingParser parser = new FreelingParser();
        parser.analyzeSentence("esto es un ejemplo.");
        ListSentence ls = parser.analyzeSentence("enciende el foco de la cocina el jueves a las ocho de la noche.");
        for(int i = 0; i < ls.size(); i++){
            TreeDepnode deptree = ls.get(i).getDepTree();
            printDepTree(deptree);
        }

    } // main
    
    public static void printDepTree(TreeDepnode deptree){
        printDepTree(deptree, 0);
    }

    public static void printDepTree(TreeDepnode deptree, int level){
        System.out.println("");
        for(int i = 0; i < level; i++) System.out.print(" ");
        Word w = deptree.getInfo().getWord();
        System.out.print(
                "(" + w.getForm() + " " + w.getLemma() + " " + w.getTag());
        System.out.print(")");
        for(int i = 0 ; i < deptree.numChildren() ; i++){
            TreeDepnode child = deptree.nthChildRef(i);
            if(child != null){
                printDepTree(child, ++level);
            }
        }
        
    }
    
}