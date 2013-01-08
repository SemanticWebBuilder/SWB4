/*
 * Clase para identificar un elemento dado un catalogo de elementos y las variaciones
 * de un comando de voz
 */
package org.semanticwb.nlp.CommandTranslator;

import org.semanticwb.nlp.Utils.string;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vieyra
 * samuel.vieyra@infotec.com.mx
 */
public class ElementSelector {
    
    private String[] elements;
    private MatchedElementOfASentence selectedElement;
    private List<String> selectedSentences;
    private String[] speechSentences;
    private int ElementsMaxlength;
    
    public ElementSelector() {
    }
    
    public MatchedElementOfASentence getSelectedElement() {
        return selectedElement;
    }
    
    public List<String> getSelectedSentences() {
        return selectedSentences;
    }
    
    public String[] getElements() {
        return elements;
    }
    
    public String[] getSpeechSentences() {
        return speechSentences;
    }
    
    public void setSpeechSentences(String[] speechSentences) {
        this.speechSentences = speechSentences;
    }
    
    public void setElements(String[] elements) {
        this.elements = elements;
        ElementsMaxlength = 0;
        for (String element : elements) {
            if (element.length() > ElementsMaxlength) {
                ElementsMaxlength = element.split(" ").length;
            }
        }
    }
    
    public List<String> selectSpeechSentences() {
        List<String> searchedDirectSentences = searchDirectElements();
        if (searchedDirectSentences != null) {
            return searchedDirectSentences;
        }
        return selectSpeechSentences(speechSentences);
    }
    
    public List<String> searchDirectElements() {
        String identifiedElement = getDirectlyElement();
        if (!identifiedElement.equals("")) {
            selectedElement = new MatchedElementOfASentence(identifiedElement, identifiedElement, 1.0);
            return getSentenceswithElement(identifiedElement);
        }
        return null;
    }
    
    public String getDirectlyElement() {
        for (int i = ElementsMaxlength + 1; i > 0; i--) {
            for (String element : elements) {
                if (element.split(" ").length == i) {
                    for (String sentence : speechSentences) {
                        if (sentence.contains(element)) {
                            return element;
                        }
                    }
                }
            }
        }
        return "";
    }
    
    public List<String> getSentenceswithElement(String element) {
        selectedSentences = new ArrayList<String>();
        for (String sentence : speechSentences) {
            if (sentence.contains(element)) {
                selectedSentences.add(sentence.replace(element, string.normalizeElementName(element)));
            }
        }
        return selectedSentences;
    }
    
    private List<String> selectSpeechSentences(String[] speechSentence) {
        double greaterSimilarity = 0.0;
        MatchedElementOfASentence ElementsSentence = null;
        for (String sentence : speechSentence) {
            MatchedElementOfASentence SSE = getElementbySetence(sentence);
            if (SSE.getSimilarity() == greaterSimilarity) {
                selectedSentences.add(sentence.replace(SSE.getSubsetSimilar(), string.normalizeElementName(SSE.getElementSimilar())));
            } else if (SSE.getSimilarity() > greaterSimilarity) {
                greaterSimilarity = SSE.getSimilarity();
                ElementsSentence = SSE;
                selectedSentences = new ArrayList<String>();
                selectedSentences.add(sentence.replace(SSE.getSubsetSimilar(), string.normalizeElementName(SSE.getElementSimilar())));
            }
        }
        selectedElement = ElementsSentence;
        return selectedSentences;
    }
    
    private MatchedElementOfASentence getElementbySetence(String Sentence) {
        String moreSimilar = null;
        String elementSelected = null;
        double JWDistance = 0.85;
        double jwd;
        for (String subset : getSubsetString(Sentence)) {
            for (String element : elements) {
                if ((jwd = org.semanticwb.nlp.Utils.string.JaroWinklerDistance(subset, element, element.length())) > JWDistance) {
                    JWDistance = jwd;
                    elementSelected = element;
                    moreSimilar = subset;
                }
            }
        }
        return new MatchedElementOfASentence(moreSimilar, elementSelected, JWDistance);
    }
    
    private String[] getSubsetString(String sentence) {
        ArrayList<String> subsets = new ArrayList<String>();
        String[] words = sentence.split(" ");
        for (int i = 0; i < words.length; i++) {
            subsets.add(words[i]);
            String ant = words[i];
            for (int j = i + 1; j < words.length; j++) {
                ant = ant + " " + words[j];
                subsets.add(ant);
            }
        }
        return subsets.toArray(new String[]{});
    }

//    /**
//     * @param args the command line arguments
//     */
    public static void main(String[] args) {
        String[] sentences = {
            "enciende foco de la cocina",
            "el 100 del poco de cocina",
            "1.100 de un foco de la cocina",
            "enciende el foco de cocina",
            "Enciende el foco de cine",
            "Enciende focos de cocina",
            "Enciende el focus de la cocina"
        };
        String[] elements = {
            "foco de la cocina",
            "foco de la sala",
            "foco dos de la cocina",
            "foco de la habitacion",
            "foco dos de la cocina",
            "foco de la habitacion de samuel",
            "foco del baño",
            "foco dos de la cocina"
        };
        
        ElementSelector selectedSentence = new ElementSelector();
        selectedSentence.setElements(elements);
        selectedSentence.setSpeechSentences(sentences);
        
        for (String sentence : selectedSentence.selectSpeechSentences()) {
            System.out.println(sentence);
        }
        System.out.println(selectedSentence.getSelectedElement());
        System.out.println(selectedSentence.getSelectedElement().getElementSimilar());
        System.out.println(selectedSentence.getSelectedElement().getSubsetSimilar());
        System.out.println(selectedSentence.getSelectedElement().getSimilarity());
    }
}
