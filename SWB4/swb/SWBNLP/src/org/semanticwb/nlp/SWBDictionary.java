/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp;

import java.util.HashMap;
import java.util.zip.Deflater;
import org.semanticwb.SWBPlatform;

/**
 * Diccionario para procesamiento de lenguaje natural. Contiene uno po varios
 * LEXIcons en distintos idiomas.
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class SWBDictionary {

    private static HashMap<String, SWBLocaleLexicon> lexicons;
    private static SWBDictionary SWBDictInstance = null;    
    private static HashMap<String, String> langMap;
    private static String defLang = "es";
    private static String spellDictPath;

    private SWBDictionary () {}

    private synchronized static void createInstance() {
        if (SWBDictInstance == null) {
            SWBDictInstance = new SWBDictionary();
            lexicons = new HashMap<String, SWBLocaleLexicon>();            
            SWBDictInstance.addLexicon(new SWBLocaleLexicon());
        }
    }

    public static SWBDictionary getInstance() {
        if (SWBDictInstance == null) createInstance();

        if (langMap == null) {
            langMap = new HashMap<String, String>();

            //TODO: habrá que cargar los idiomas de otro lado. Un archivo?
            langMap.put("es", "Spanish");
            langMap.put("en", "English");
            langMap.put("de", "Dutch");
            langMap.put("pt", "Portuguese");
            langMap.put("ru", "Russian");
        }
        
        if (spellDictPath == null) {
            spellDictPath = SWBPlatform.createInstance().getPlatformWorkPath() + "/index/spell_" +
                    defLang + ".txt";
        }
        return SWBDictInstance;
    }

    public SWBLocaleLexicon getLexicon(String languageCode) {
        return lexicons.get(languageCode);
    }

    public SWBLocaleLexicon getDefaultLexicon() {
        return getLexicon(defLang);
    }

    public void addLexicon(SWBLocaleLexicon lexic) {
        lexicons.put(lexic.getLanguageCode(), lexic);
    }

    public String getPrefixQuery(String languageCode) {
        if (lexicons.get(languageCode) == null) return null;
        return lexicons.get(languageCode).getPrefixString();
    }

    public Tag getWordTag(String lexForm, boolean asObject) {
        return getLexicon(defLang).getWord(lexForm, asObject).getTag();
    }

    public void setWordTag(String lexForm, Tag tag, boolean asObject) {
        getLexicon(defLang).getWord(lexForm, asObject).setTag(tag);
    }

    public void setLocale(String langCode) {
        if (lexicons.get(langCode) != null) defLang = langCode;
    }

    public void addLocale(String langCode, String langName) {
        if (lexicons.get(langCode) != null) return;
        lexicons.put(langCode, new SWBLocaleLexicon(langCode, langName));
        langMap.put(langCode, langName);
    }

    public static String getLanguageName(String languageCode) {
        return langMap.get(languageCode);
    }

    public String getSpellDictPath() {
        return spellDictPath;
    }
}
