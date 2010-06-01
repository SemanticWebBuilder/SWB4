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

import java.util.HashMap;
import org.semanticwb.SWBPlatform;

/**
 * Diccionario para procesamiento de lenguaje natural. Contiene uno o varios
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
        return getLexicon(defLang).getWord(lexForm, asObject).getSelectedTag();
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
