
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import java.io.StringReader;
import java.io.IOException;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.semanticwb.social.util.lucene.SpanishAnalizer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jorge.jimenez
 */
public class SnowBallTest{
    
    private static final String[] strings = {
        "la constitución de México es la mejor",
        "queso vueno ta chido"
    };

    private static final Analyzer[] analyzers = new Analyzer[]{
        //new WhitespaceAnalyzer(),
        //new SimpleAnalyzer(),
        //new StopAnalyzer(),
        //new StandardAnalyzer(),
        //new SnowballAnalyzer("English", StopAnalyzer.ENGLISH_STOP_WORDS_SET),
        new SpanishAnalizer(),
    };

    public static void main(String[] args) throws IOException {
        for (int i = 0; i<strings.length; i++) {
            analyze(strings[i]);
        }
    }

    private static void analyze(String text) throws IOException {
        System.out.println("Analzying:" + text);
        for (int i = 0; i < analyzers.length; i++) {
            Analyzer analyzer = analyzers[i];
            System.out.println("\t" + analyzer.getClass().getName() + ":");
            System.out.print("\t\t");
            TokenStream tokenStream = analyzer.tokenStream("contents", new StringReader(text));
            OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
            CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);

            while (tokenStream.incrementToken()) {
                int startOffset = offsetAttribute.startOffset();
                int endOffset = offsetAttribute.endOffset();
                String term = charTermAttribute.toString();
                System.out.println("term Jorge:"+term);
            }
            System.out.println("\n");
        }
    }
}
