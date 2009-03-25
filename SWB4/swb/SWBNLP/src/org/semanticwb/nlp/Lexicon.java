/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.nlp;

import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author hasdai
 */
public class Lexicon {
    //TODO: Poner el léxico en un árbol

    private ArrayList<Word> pLexic;
    private ArrayList<Word> oLexic;
    private String language = "es";

    /**
     * Constructor.
     * @param lex Diccionario de palabras.
     */
    public Lexicon(ArrayList<Word> lex, String lang) {
        Iterator<Word> wit = lex.iterator();
        pLexic = new ArrayList<Word>();
        oLexic = new ArrayList<Word>();
        language = lang;

        while (wit.hasNext()) {
            Word t = wit.next();

            if (t.getTag().getTag().equals("PRO")) {
                pLexic.add(t);
            } else {
                oLexic.add(t);
            }
        }
    }

    public String getLanguage() {
        return language;
    }

    public Lexicon(String lang) {
        pLexic = new ArrayList<Word>();
        oLexic = new ArrayList<Word>();

        //Create a new word dictionary instance
        Iterator<SemanticClass> its = SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClasses();

        //Traverse the ontology model to fill the dictionary
        while (its.hasNext()) {
            SemanticClass sc = its.next();

            this.addWord(new Word(sc.getDisplayName(lang),
                    new WordTag("OBJ", sc.getPrefix() + ":" + sc.getName(), sc.getName())));

            Iterator<SemanticProperty> ip = sc.listProperties();

            while (ip.hasNext()) {
                SemanticProperty prop = ip.next();

                this.addWord(new Word(prop.getDisplayName(lang),
                        new WordTag("PRO", prop.getPropId(), prop.getName())));
            }
        }
    }

    /**
     * Agrega una palabra al diccionario verificando que no exista previamente,
     * en cuyo caso no la inserta.
     * @param w palabra a agregar.
     */
    public void addWord(Word w) {
        if (!entryExist(w)) {
            if (w.getTag().getTag().equals("PRO")) {
                pLexic.add(w);
            } else {
                oLexic.add(w);
            }
        }
    }

    /**
     * Obtiene la etiqueta de la palabra especificada.
     * @param w palabra a etiquetar.
     * @return WordTag con la etiqueta de la palabra y su tipo. Si la palabra no
     *         existe en el diccionario, la etiqueta como variable.
     */
    public WordTag getWordTag(Word w) {
        //TODO: Arreglar para hacer la búsqueda en un árbol

        for (int i = 0; i < pLexic.size(); i++) {
            if (pLexic.get(i).getLabel().toUpperCase().compareTo(w.getLabel().toUpperCase()) == 0) {
                return new WordTag(pLexic.get(i).getTag().getTag(),
                        pLexic.get(i).getTag().getType(), pLexic.get(i).getTag().getWClassName());
            }
        }

        for (int i = 0; i < oLexic.size(); i++) {
            if (oLexic.get(i).getLabel().toUpperCase().compareTo(w.getLabel().toUpperCase()) == 0) {
                return new WordTag(oLexic.get(i).getTag().getTag(),
                        oLexic.get(i).getTag().getType(), oLexic.get(i).getTag().getWClassName());
            }
        }
        return new WordTag("VAR", "", "");
    }

    public WordTag getPropWordTag(Word w) {
        for (int i = 0; i < pLexic.size(); i++) {
            if (pLexic.get(i).getLabel().toUpperCase().compareTo(w.getLabel().toUpperCase()) == 0) {
                return new WordTag(pLexic.get(i).getTag().getTag(),
                        pLexic.get(i).getTag().getType(), pLexic.get(i).getTag().getWClassName());
            }
        }
        return new WordTag("VAR", "", "");
    }

    public WordTag getPropWordTag(String label) {
        for (int i = 0; i < pLexic.size(); i++) {
            if (pLexic.get(i).getLabel().toUpperCase().compareTo(label.toUpperCase()) == 0) {
                return new WordTag(pLexic.get(i).getTag().getTag(),
                        pLexic.get(i).getTag().getType(), pLexic.get(i).getTag().getWClassName());
            }
        }
        return new WordTag("VAR", "", "");
    }

    public WordTag getObjWordTag(Word w) {
        for (int i = 0; i < oLexic.size(); i++) {
            if (oLexic.get(i).getLabel().toUpperCase().compareTo(w.getLabel().toUpperCase()) == 0) {
                return new WordTag(oLexic.get(i).getTag().getTag(),
                        oLexic.get(i).getTag().getType(), oLexic.get(i).getTag().getWClassName());
            }
        }
        return new WordTag("VAR", "", "");
    }

    public WordTag getObjWordTag(String label) {
        for (int i = 0; i < oLexic.size(); i++) {
            if (oLexic.get(i).getLabel().toUpperCase().compareTo(label.toUpperCase()) == 0) {
                return new WordTag(oLexic.get(i).getTag().getTag(),
                        oLexic.get(i).getTag().getType(), oLexic.get(i).getTag().getWClassName());
            }
        }
        return new WordTag("VAR", "", "");
    }

    public WordTag getWordTag(String label) {
        boolean found = false;

        for (int i = 0; i < pLexic.size() && !found; i++) {
            if (pLexic.get(i).getLabel().toUpperCase().compareTo(label.toUpperCase()) == 0) {
                return new WordTag(pLexic.get(i).getTag().getTag(),
                        pLexic.get(i).getTag().getType(), pLexic.get(i).getTag().getWClassName());
            }
        }

        for (int i = 0; i < oLexic.size() && !found; i++) {
            if (oLexic.get(i).getLabel().toUpperCase().compareTo(label.toUpperCase()) == 0) {
                return new WordTag(oLexic.get(i).getTag().getTag(),
                        oLexic.get(i).getTag().getType(), oLexic.get(i).getTag().getWClassName());
            }
        }
        return new WordTag("VAR", "", "");
    }

    /**
     * Verifica si ya existe una palabra en el diccionario.
     * @param entry palabra a verificar.
     * @return true si la palabra existe en el diccionario.
     */
    public boolean entryExist(Word entry) {
        boolean found = false;
        //TODO: Arreglar para hacer la búsqueda en un árbol
        for (int i = 0; i < pLexic.size() && !found; i++) {
            if (pLexic.get(i).getLabel().toUpperCase().compareTo(entry.getLabel().toUpperCase()) == 0 && pLexic.get(i).getTag().getTag().compareTo(entry.getTag().getTag()) == 0 && pLexic.get(i).getTag().getType().compareTo(entry.getTag().getType()) == 0 && pLexic.get(i).getTag().getWClassName().compareTo(entry.getTag().getWClassName()) == 0) {
                found = true;
            }
        }

        for (int i = 0; i < oLexic.size() && !found; i++) {
            if (oLexic.get(i).getLabel().toUpperCase().compareTo(entry.getLabel().toUpperCase()) == 0 && oLexic.get(i).getTag().getTag().compareTo(entry.getTag().getTag()) == 0 && oLexic.get(i).getTag().getType().compareTo(entry.getTag().getType()) == 0) {
                found = true;
            }
        }
        return found;
    }

    /**
     * Devuelve un iterador para recorrer el diccionario.
     * @return Iterador a las palabras del diciconario.
     */
    public Iterator<Word> listObjEntries() {
        return oLexic.iterator();
    }

    public Iterator<Word> listPropEntries() {
        return pLexic.iterator();
    }

    @Override
    public String toString() {
        String res = "";

        Iterator<Word> wit = pLexic.iterator();
        while (wit.hasNext()) {
            Word t = wit.next();

            res = res + t.getLabel() + "[" + t.getTag().getTag() + ", " +
                    t.getTag().getType() + "]\n";
        }

        wit = oLexic.iterator();
        while (wit.hasNext()) {
            Word t = wit.next();

            res = res + t.getLabel() + "[" + t.getTag().getTag() + ", " +
                    t.getTag().getType() + "]\n";
        }
        return res;
    }
}