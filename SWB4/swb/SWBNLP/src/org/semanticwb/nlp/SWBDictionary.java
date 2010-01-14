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

    private static HashMap<String, LocaleLexicon> lexicons;
    private static SWBDictionary SWBDictInstance = null;    
    private static HashMap<String, String> langMap;
    private String defLang = "es";

    private SWBDictionary () {}

    private synchronized static void createInstance() {
        if (SWBDictInstance == null) {
            SWBDictInstance = new SWBDictionary();

            lexicons = new HashMap<String, LocaleLexicon>();
            langMap = new HashMap<String, String>();
            langMap.put("es", "Spanish");
            langMap.put("en", "English");
            langMap.put("de", "Dutch");
            langMap.put("pt", "Portuguese");
            langMap.put("ru", "Russian");
            
            SWBDictInstance.addLexicon(new LocaleLexicon());
        }
    }

    public static SWBDictionary getInstance () {
        if (SWBDictInstance == null) createInstance();
        return SWBDictInstance;
    }

    public LocaleLexicon getLexicon(String languageCode) {
        return lexicons.get(languageCode);
    }

    public LocaleLexicon getLexicon() {
        return getLexicon(defLang);
    }

    public void addLexicon(LocaleLexicon lexic) {
        lexicons.put(lexic.getLanguageCode(), lexic);
    }

    public String getPrefixQuery(String languageCode) {
        if (lexicons.get(languageCode) == null) return null;
        return lexicons.get(languageCode).getPrefixString();
    }

    public Tag getWordTag(String lexForm, boolean asObject) {
        return getLexicon().getWord(lexForm, asObject).getTag();
    }

    public void setWordTag(String lexForm, Tag tag, boolean asObject) {
        getLexicon().getWord(lexForm, asObject).setTag(tag);
    }

    public void setLocale(String langCode) {
        if (lexicons.get(langCode) != null) defLang = langCode;
    }

    public void addLocale(String langCode, String langName) {
        if (lexicons.get(langCode) != null) return;
        lexicons.put(langCode, new LocaleLexicon(langCode, langName));
        langMap.put(langCode, langName);
    }
}
