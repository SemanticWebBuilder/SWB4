/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/

package org.semanticwb.nlp;

import java.util.ArrayList;
import java.util.List;

/**
 * Word. Palabra.
 * <p>
 * A word is a set of characters with a context-dependent meaning. Phrases and
 * sentences in a specific language are made of word combinations. The sense and
 * correctness of those phrases and sentences is granted by the set of grammar
 * rules defined for that language. In Natural Language Processing, a word can be
 * considered itself a sentence construction unit (token or lexical form). Words
 * are stored in a lexicon (dictionary) with an associated tag which marks its
 * function (or functions) in a sentence (verb, noun, determiner).
 * <p>
 * Una palabra es un conjunto de caracteres con un significado dependiente
 * del contexto. Las palabras y sus combinaciones dan origen a frases y
 * oraciones en cierto idioma, cuyo sentido y validez es asegurado por reglas
 * gramaticales establecidas para dicho idioma. En el procesamiento del lenguaje
 * natural, cada palabra puede considerarse por sí misma una unidad de
 * construcción de oraciones (forma léxica). Las palabras se almacenan en un
 * diccionario, junto con una etiqueta asociada que identifica su función
 * (o funciones) dentro de una oración (ej. verbo, sujeto, artículo).
 *
 * @see SWBLexicon
 * @see WordTag
 *
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class Word {
    private String lemma; //raíz de la palabra
    private String lexicalForm;//displayname
    private ArrayList<Tag> wTags;   //POS-TAG
    private ArrayList<String> synSet; //Conjunto de sinónimos
    private Tag selected;//Last selected tag

    /**
     * Creates a new instance of a Word with the given label (the word itself)
     * and language. An empty tag is associated to the new word as the word's
     * function in the sentence is unknown.
     * <p>
     * Crea una instancia de una palabra con cierta forma léxica (la palabra en sí)
     * e idioma. Se asocia una etiqueta vacía a la nueva palabra, indicando que
     * no se conoce su función dentro de la oración.
     * 
     * @param lbl Word label. Forma léxica de la palabra.
     * @param lan Word Language. Idioma de la palabra.
     */

    public Word(String lexForm) {
        lexicalForm = lexForm;
        lemma = "";
        wTags = new ArrayList<Tag>();
        selected = null;
    }

    public void setSelectedTag(Tag selected) {
        this.selected = selected;
    }

    public Tag getSelectedTag() {
        return selected;
    }

    public void addSynonym (String syn) {
        synSet.add(syn.trim());
    }

    public ArrayList<String> getSynSet () {
        return synSet;
    }

    public boolean isSynonymOf(Word w) {
        if (synSet.contains(w.getLemma()) || lemma.equals(w.getLemma()))
            return true;
        return false;
    }    

    /**
     * Gets the lexical form of this Word.
     * <p>
     * Obtiene la forma léxica de la palabra.
     */
    public String getLexicalForm() {
        return lexicalForm;
    }

    /**
     * Sets the lexical form for this Word.
     * <p>
     * Establece la forma léxica de la palabra.
     */
    public void setLexicalForm(String lexForm) {
        this.lexicalForm = lexForm;
    }

    /**
     * Gets the {@link WordTag} of this Word.
     * <p>
     * Obtiene el {@link WordTag} de la palabra.
     */
    public ArrayList<Tag> getTags() {
        return wTags;
    }

    public void addTag(Tag t) {
        wTags.add(t);
    }

    /**
     * Sets the {@link WordTag} of this Word.
     * <p>
     * Establece el {@link WordTag} de la palabra.
     */
    public void setTags(ArrayList<Tag> tags) {
        wTags = tags;
    }

    /**
     * Sets unique tag for Word Objects
     * @param t tag
     */
    public void setTag(Tag t) {
        wTags.clear();
        wTags.add(t);
        selected = t;
    }

    public void setLemma(String lem) {
        lemma = lem;
    }

    public String getLemma() {
        return lemma;
    }
}