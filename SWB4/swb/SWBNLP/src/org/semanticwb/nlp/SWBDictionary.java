/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp;

import java.util.HashMap;

/**
 * Diccionario para procesamiento de lenguaje natural. Contiene uno po varios
 * LEXIcons en distintos idiomas.
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class SWBDictionary {

    private HashMap<String, LocaleLexicon> lexicons;
    private static SWBDictionary SWBDictInstance = new SWBDictionary();
    private String defLang = "es";

    private SWBDictionary () {
        lexicons.put(defLang, new LocaleLexicon());
    }

    public static SWBDictionary getInstance () {
        return SWBDictInstance;
    }

    public LocaleLexicon getLexicon(String language) {
        return lexicons.get(language);
    }

    public LocaleLexicon getDefaultLexicon() {
        return getLexicon(defLang);
    }

    public void addLexicon(LocaleLexicon lexic) {
        lexicons.put(lexic.getLanguageCode(), lexic);
    }
}
