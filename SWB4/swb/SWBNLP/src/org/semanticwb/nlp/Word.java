/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp;

import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;

/**
 * Una palabra es un conjunto de caracteres con un significado dependiente
 * del contexto. Las palabras y sus combinaciones dan origen a frases y
 * oraciones en cierto idioma, cuyo sentido y validez es asegurado por reglas
 * gramaticales establecidas para dicho idioma. En el procesamiento del lenguaje
 * natural, cada palabra puede considerarse por sí misma una unidad de
 * construcción de oraciones (forma léxica). Las palabras se almacenan en un
 * diccionario, junto con una etiqueta asociada que identifica su función
 * (o funciones) dentro de una oración (ej. verbo, sujeto, artículo).
 *
 * A word is a set of characters with a context-dependent meaning. Phrases and
 * sentences in a specific language are made of word combinations. The sense and
 * correctness of those phrases and sentences is granted by the set of grammar
 * rules defined for that language. In Natural Language Processing, a word can be
 * considered itself a sentence construction unit (token or lexical form). Words
 * are stored in a lexicon (dictionary) with an associated tag which marks its
 * function (or functions) in a sentence (verb, noun, determiner).
 *
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class Word {
    /** Lexical form of the word.
     * Forma léxica de la palabra.
     */
    private String label=""; //DisplayName
    /**Tag for the word as a {@link WordTag} object.
     * Etiqueta de la palabra como un objeto {@link WordTag}.
     */
    private WordTag wTag;   //POS-TAG
    /**Word language.
     * Idioma de la palabra.
     */
    private String lang = "es";

    /**
     * Creates a new instance of a Word with the given label (the word itself)
     * and language. An empty tag is associated to the new word as the word's
     * function in the sentence is unknown.
     *
     * Crea una instancia de una palabra con cierta forma léxica (la palabra en sí)
     * e idioma. Se asocia una etiqueta vacía a la nueva palabra, indicando que
     * no se conoce su función dentro de la oración.
     * 
     * @param lbl Forma léxica de la palabra. Word label.
     * @param lan Idioma de la palabra. Word Language.
     */
    public Word(String lbl, String lan) {
        lang = lan;
        label = lbl;
        wTag = new WordTag("","", "", "", "", "");
    }

    /**
     * (Specific SemanticWebBuilder constructor) Creates a new instance of a
     * Word, based on the information of a SemanticClass object. The label for
     * the new word (the word itself) will be the displayName of the SemanticClass
     * object (for the specified language).
     *
     * (Constructor específico de SemanticWebBuilder) Crea una instancia de una
     * palabra, basado en la información de un objeto de tipo SemanticClass. La
     * forma léxica para la nueva palabra (la palabra en sí) será el displayName
     * (para el idioma especificado) del objeto SemanticClass.
     * 
     * @param cl SemanticClass para formar la nueva palabra. SemanticClass for
     * the new Word.
     * @param lan Idioma de la palabra. Word Language.
     */
    public Word(SemanticClass cl, String lan) {
        lang = lan;
        label = cl.getDisplayName(lang);
        //wTag = new WordTag("OBJ", cl.getPrefix() + ":" + cl.getName(), cl.getName(), cl.getClassId(), "");
    }

    /**
     * (Specific SemanticWebBuilder constructor) Creates a new instance of a
     * Word, based on the information of a SemanticProperty object. The label for
     * the new word (the word itself) will be the displayName of the
     * SemanticProperty object (for the specified language).
     *
     * (Constructor específico de SemanticWebBuilder) Crea una instancia de una
     * palabra, basado en la información de un objeto de tipo SemanticProperty.
     * La forma léxica para la nueva palabra (la palabra en sí) será el displayName
     * (para el idioma especificado) del objeto SemanticProperty.
     *
     * @param p SemanticProperty para formar la nueva palabra. SemanticProperty
     * for the new Word.
     * @param lan Idioma de la palabra. Word Language.
     */
    public Word (SemanticProperty p, String lan) {
        lang = lan;
        label = p.getDisplayName(lang);

        //If p is an ObjectProperty
        if (p.isObjectProperty()) {
            //Get name of property range class
            StringBuffer bf = new StringBuffer();
            bf.append(p.getRangeClass());

            //Attempt to get range class
            SemanticClass rg = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(bf.toString());
            if (rg != null) {
               // wTag = new WordTag("PRO", p.getPrefix() + ":" + p.getName(), p.getName(), p.getPropId(), rg.getClassId());
            }
        } else {
           // wTag = new WordTag("PRO", p.getPrefix() + ":" + p.getName(), p.getName(), p.getPropId(), "");
        }
    }
    
    /**
     * Creates a new instance of a Word with the given label (the word itself),
     * {@link WordTag} and language.
     *
     * Crea una instancia de una palabra con cierta forma léxica (la palabra en
     * sí), etiqueta e idioma.
     *
     * @param lbl Forma léxica de la palabra. Word label.
     * @param wt {@link WordTag} de la palabra. {@link WordTag} for the word.
     * @param lan Idioma de la palabra. Word Language.
     */
    public Word(String lbl, WordTag wt, String lan) {
        lang = lan;
        label = lbl;
        wTag = wt;
    }

    /**
     * Gets the label of the current Word instance.
     * Obtiene la forma léxica de la palabra.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label of the current Word instance.
     * Establece la forma léxica de la palabra.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets the {@link WordTag} of the current Word instance.
     * Obtiene el {@link WordTag} de la palabra.
     */
    public WordTag getTag() {
        return wTag;
    }

    /**
     * Sets the {@link WordTag} of the current Word instance.
     * Establece el {@link WordTag} de la palabra.
     */
    public void setTag(WordTag tag) {
        wTag = tag;
    }

    /**
     * Gets the language of the current Word instance.
     * Obtiene el idioma de la palabra.
     */
    public String getLanguage() {
        return lang;
    }

    /**
     * Sets the language of the current Word instance.
     * Establece el idioma de la palabra.
     */
    public void setLanguage(String newLang) {
        lang = newLang;
    }
}