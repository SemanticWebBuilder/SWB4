/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author hasdai
 */
public class Lexicon {
    //TODO: Poner el léxico en un árbol
    private ArrayList<Word> pLexic;
    private ArrayList<Word> oLexic;

    /**
     * Constructor.
     * @param lex Diccionario de palabras.
     */
    public Lexicon(ArrayList<Word> lex) {
        Iterator<Word> wit = lex.iterator();
        pLexic = new ArrayList<Word>();
        oLexic = new ArrayList<Word>();

        while(wit.hasNext()) {
            Word t = wit.next();

            if(t.getTag().getLabel().equals("PRO")) {
                pLexic.add(t);
            } else {
                oLexic.add(t);
            }
        }
    }

    public Lexicon() {
        pLexic = new ArrayList<Word>();
        oLexic = new ArrayList<Word>();
    }

    /**
     * Agrega una palabra al diccionario verificando que no exista previamente,
     * en cuyo caso no la inserta.
     * @param w palabra a agregar.
     */
    public void addWord(Word w) {
        if (!entryExist(w)) {
            if (w.getTag().getLabel().equals("PRO")) {
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
                return new WordTag(pLexic.get(i).getTag().getLabel(),
                        pLexic.get(i).getTag().getType());
            }
        }

        for (int i = 0; i < oLexic.size(); i++) {
            if (oLexic.get(i).getLabel().toUpperCase().compareTo(w.getLabel().toUpperCase()) == 0) {
                return new WordTag(oLexic.get(i).getTag().getLabel(),
                        oLexic.get(i).getTag().getType());
            }
        }
        return new WordTag("VAR", "");
    }

    public WordTag getPropWordTag(Word w) {
        for (int i = 0; i < pLexic.size(); i++) {
            if (pLexic.get(i).getLabel().toUpperCase().compareTo(w.getLabel().toUpperCase()) == 0) {
                return new WordTag(pLexic.get(i).getTag().getLabel(),
                        pLexic.get(i).getTag().getType());
            }
        }
        return new WordTag("VAR", "");
    }

    public WordTag getPropWordTag(String label) {
        for (int i = 0; i < pLexic.size(); i++) {
            if (pLexic.get(i).getLabel().toUpperCase().compareTo(label.toUpperCase()) == 0) {
                return new WordTag(pLexic.get(i).getTag().getLabel(),
                        pLexic.get(i).getTag().getType());
            }
        }
        return new WordTag("VAR", "");
    }

    public WordTag getObjWordTag(Word w) {
        for (int i = 0; i < oLexic.size(); i++) {
            if (oLexic.get(i).getLabel().toUpperCase().compareTo(w.getLabel().toUpperCase()) == 0) {
                return new WordTag(oLexic.get(i).getTag().getLabel(),
                        oLexic.get(i).getTag().getType());
            }
        }
        return new WordTag("VAR", "");
    }

    public WordTag getObjWordTag(String label) {
        for (int i = 0; i < oLexic.size(); i++) {
            if (oLexic.get(i).getLabel().toUpperCase().compareTo(label.toUpperCase()) == 0) {
                return new WordTag(oLexic.get(i).getTag().getLabel(),
                        oLexic.get(i).getTag().getType());
            }
        }
        return new WordTag("VAR", "");
    }

    public WordTag getWordTag(String label) {
        boolean found = false;

        for (int i = 0; i < pLexic.size() && !found; i++) {
            if (pLexic.get(i).getLabel().toUpperCase().compareTo(label.toUpperCase()) == 0) {
                return new WordTag(pLexic.get(i).getTag().getLabel(),
                        pLexic.get(i).getTag().getType());
            }
        }

        for (int i = 0; i < oLexic.size() && !found; i++) {
            if (oLexic.get(i).getLabel().toUpperCase().compareTo(label.toUpperCase()) == 0) {
                return new WordTag(oLexic.get(i).getTag().getLabel(),
                        oLexic.get(i).getTag().getType());
            }
        }
        return new WordTag("VAR", "");
    }

    /**
     * Verifica si ya existe una palabra en el diccionario.
     * @param entry palabra a verificar.
     * @return true si la palabra existe en el diccionario.
     */
    public boolean entryExist(Word entry)
    {
        boolean found = false;
        //TODO: Arreglar para hacer la búsqueda en un árbol
        for (int i = 0; i < pLexic.size() && !found; i++)
        {
            if (pLexic.get(i).getLabel().toUpperCase().compareTo(entry.getLabel().toUpperCase()) == 0
                    && pLexic.get(i).getTag().getLabel().compareTo(entry.getTag().getLabel()) == 0
                    && pLexic.get(i).getTag().getType().compareTo(entry.getTag().getType())==0) {
                found = true;
            }
        }

        for (int i = 0; i < oLexic.size() && !found; i++)
        {
            if (oLexic.get(i).getLabel().toUpperCase().compareTo(entry.getLabel().toUpperCase()) == 0
                    && oLexic.get(i).getTag().getLabel().compareTo(entry.getTag().getLabel()) == 0
                    && oLexic.get(i).getTag().getType().compareTo(entry.getTag().getType())==0) {
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

            res = res + t.getLabel() + "[" + t.getTag().getLabel() + ", " +
                    t.getTag().getType() + "]\n";
        }

        wit = oLexic.iterator();
        while (wit.hasNext()) {
            Word t = wit.next();

            res = res + t.getLabel() + "[" + t.getTag().getLabel() + ", " +
                    t.getTag().getType() + "]\n";
        }
        return res;
    }
}