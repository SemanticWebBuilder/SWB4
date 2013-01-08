/*
 * Esta clase parsea e identifica una oración
 * buscando la acción, modificador temporal y 
 * modificador de intensidad dentro de esta
 * oración
 */
package org.semanticwb.nlp.CommandTranslator;

import org.semanticwb.nlp.MorphoSyntacticAnalysis.FreelingParser;
import edu.upc.freeling.ListSentence;
import edu.upc.freeling.TreeDepnode;
import edu.upc.freeling.Word;

/**
 * @author vieyra 
 * samuel.vieyra@infotec.com.mx
 */
class SentenceParser {
    
    private String element;
    private String sentence;
    private Word action;
    private Word TemporalModifier;
    private Word IntensityModifier;
    private ListSentence AnalyzedSentence;

    public SentenceParser(String sentence, String element) {
        this.element = element;
//        System.out.println("\nAnalyzing " + sentence + " containing the element " + this.element);
        if(!sentence.endsWith(".")){
            sentence = sentence.concat(".");
        }
        //AnalyzedSentence = new FreelingParser().analyzeSentence(sentence);
        AnalyzedSentence = FreelingParser.getInstance().analyzeSentence(sentence);
        for (int i = 0; i < AnalyzedSentence.size(); i++) {
            parseSentence(AnalyzedSentence.get(i).getDepTree(), null);
        }
    }

    private void parseSentence(TreeDepnode current, TreeDepnode previous) {
        Word currentWord = current.getInfo().getWord();
         if (currentWord.getTag().startsWith("V")) {
//            System.out.println("La acción identificada es " + currentWord.get_lemma());
            action = currentWord;
        }
//        if (currentWord.get_parole().startsWith("N")) {
//            System.out.println("comparying + \"" + element+ "\".equals(\"" +currentWord.get_form()+ "\")" +element.equals(string.normalizeElementName(currentWord.get_form())));
//            System.out.println("El elemento identificado es " + element);
//        }
        if (currentWord.getTag().startsWith("Zp")) {
//            System.out.println("El modificador de intensidad identificado es " + currentWord.get_lemma());
            IntensityModifier = currentWord;
        }
        if (currentWord.getTag().startsWith("W")) {
//            System.out.println("El modificador temporal identificado es " + currentWord.get_lemma());
            TemporalModifier = currentWord;
        }
        for (int i = 0; i < current.numChildren(); i++) {
            parseSentence(current.nthChildRef(i), current);
        }
    }

    public String getElement() {
        return element;
    }

    public Word getAction() {
        return action;
    }

    public Word getTemporalModifier() {
        return TemporalModifier;
    }

    public Word getIntensityModifier() {
        return IntensityModifier;
    }

    public String getSentence() {
        return sentence;
    }

}
