/*
 * Esta clase parsea e identifica una oración
 * buscando la acción, modificador temporal y 
 * modificador de intensidad dentro de esta
 * oración
 */
package org.semanticwb.nlp.CommandTranslator;

import edu.upc.freeling.ListSentence;
import edu.upc.freeling.TreeDepnode;
import edu.upc.freeling.Word;
import org.semanticwb.nlp.MorphoSyntacticAnalysis.FreelingParser;

/**
 * @author vieyra samuel.vieyra@infotec.com.mx
 */
class SentenceParser {

    private String element;
    private String sentence;
    private Word action;
    private Word temporalModifier;
    private String calendarModifier = null;
    private Word intensityModifier;
    private ListSentence analyzedSentence;

    public SentenceParser(String sentence, String element) {
        this.element = element;
//        System.out.println("\nAnalyzing " + sentence + " containing the element " + this.element);
        if (!sentence.endsWith(".")) {
            sentence = sentence.concat(".");
        }
        //AnalyzedSentence = new FreelingParser().analyzeSentence(sentence);
        analyzedSentence = FreelingParser.getInstance().analyzeSentence(sentence);
        for (int i = 0; i < analyzedSentence.size(); i++) {
            parseSentence(analyzedSentence.get(i).getDepTree(), null);
        }
    }

    public String getCalendarModifier() {
        return calendarModifier;
    }

    private void parseSentence(TreeDepnode current, TreeDepnode previous) {
        Word currentWord = current.getInfo().getWord();
        if (currentWord.getTag().startsWith("V")) {
            action = currentWord;
        }
        if (currentWord.getTag().startsWith("Zp")) {
            intensityModifier = currentWord;
        }
        if (currentWord.getTag().startsWith("Zu")) {
            String previousWord = previous.getInfo().getWord().getForm();
            if (previousWord.equals("en") || previousWord.equals("dentro_de")) {
                calendarModifier = currentWord.getLemma();
            }
        }
        if (currentWord.getLemma().equals("segundo")) {
            String previousWord = previous.getInfo().getWord().getForm();
            if (previousWord.equals("en") || previousWord.equals("dentro_de")) {
                Word nextWord = current.nthChildRef(0).getInfo().getWord();
                if(nextWord.getTag().equals("Z")){
                    System.out.println("TM_sec:" + nextWord.getLemma());
                    calendarModifier = "TM_sec:" + nextWord.getLemma();
                }
            }
            
        }
        if (currentWord.getTag().startsWith("W")) {
            temporalModifier = currentWord;
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
        return temporalModifier;
    }

    public Word getIntensityModifier() {
        return intensityModifier;
    }

    public String getSentence() {
        return sentence;
    }

}
