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

import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;

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
     * <p>
     * Crea una instancia de una palabra con cierta forma léxica (la palabra en sí)
     * e idioma. Se asocia una etiqueta vacía a la nueva palabra, indicando que
     * no se conoce su función dentro de la oración.
     * 
     * @param lbl Word label. Forma léxica de la palabra.
     * @param lan Word Language. Idioma de la palabra.
     */
    public Word(String lbl, String lan) {
        lang = lan;
        label = lbl;
        wTag = new WordTag("","", "", "", "", "");
    }

    /**
     * Creates a new instance of a Word, based on the information of a 
     * {@link SemanticClass}. The lexical form for the new word will be the
     * display name of the {@link SemanticClass} (for the specified language).
     * <p>
     * Crea una instancia de una palabra, basado en la información de un objeto 
     * de tipo {@link SemanticClass}. La forma léxica para la nueva palabra
     * (la palabra en sí) será el display name (para el idioma especificado) del
     * {@link SemanticClass}.
     * 
     * @param cl    {@link SemanticClass} for the new Word. {@link SemanticClass}
     *              para formar la nueva palabra.
     * @param lan   Word Language. Idioma de la palabra.
     */
    public Word(SemanticClass cl, String lan) {
        lang = lan;
        label = cl.getDisplayName(lang);
        //wTag = new WordTag("OBJ", cl.getPrefix() + ":" + cl.getName(), cl.getName(), cl.getClassId(), "");
    }

    /**
     * Creates a new instance of a Word, based on the information of a 
     * {@link SemanticProperty}. The lexical form for the new word will be the
     * display name of the {@link SemanticProperty} for the specified language.
     * <p>
     * Crea una instancia de una palabra, basado en la información de un objeto
     * de tipo {@link SemanticProperty}. La forma léxica para la nueva palabra
     * será el display name (para el idioma especificado) del {@link SemanticProperty}.
     *
     * @param p     {@link SemanticProperty} for the new Word.
     *              {@link SemanticProperty} para formar la nueva palabra.
     * @param lan   Word Language. Idioma de la palabra.
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
     * Creates a new instance of a Word with the given lexical form,
     * {@link WordTag} and language.
     * <p>
     * Crea una instancia de una palabra con cierta forma léxica (la palabra en
     * sí), etiqueta e idioma.
     *
     * @param lbl   Lexical form of the word. Forma léxica de la palabra.
     * @param wt    {@link WordTag} for the word. {@link WordTag} de la palabra.
     * @param lan   Word Language. Idioma de la palabra.
     */
    public Word(String lbl, WordTag wt, String lan) {
        lang = lan;
        label = lbl;
        wTag = wt;
    }

    /**
     * Gets the lexical form of this Word.
     * <p>
     * Obtiene la forma léxica de la palabra.
     */
    public String getLexicalForm() {
        return label;
    }

    /**
     * Sets the lexical form for this Word.
     * <p>
     * Establece la forma léxica de la palabra.
     */
    public void setLexicalForm(String label) {
        this.label = label;
    }

    /**
     * Gets the {@link WordTag} of this Word.
     * Obtiene el {@link WordTag} de la palabra.
     */
    public WordTag getTag() {
        return wTag;
    }

    /**
     * Sets the {@link WordTag} of this Word.
     * Establece el {@link WordTag} de la palabra.
     */
    public void setTag(WordTag tag) {
        wTag = tag;
    }

    /**
     * Gets the language of this Word.
     * Obtiene el idioma de la palabra.
     */
    public String getLanguage() {
        return lang;
    }

    /**
     * Sets the language for this Word.
     * Establece el idioma de la palabra.
     */
    public void setLanguage(String newLang) {
        lang = newLang;
    }
}