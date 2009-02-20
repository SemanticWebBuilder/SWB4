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
    private ArrayList<Word> lexic;

    /**
     * Constructor.
     * @param lex Diccionario de palabras.
     */
    public Lexicon(ArrayList<Word> lex) {
        lexic = lex;
    }

    public Lexicon() {
        lexic = new ArrayList<Word>();
    }

    /**
     * Agrega una palabra al diccionario verificando que no exista previamente,
     * en cuyo caso no la inserta.
     * @param w palabra a agregar.
     */
    public void addWord(Word w) {
        if (!entryExist(w)) {
            lexic.add(w);
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
        for (int i = 0; i < lexic.size(); i++) {
            if (lexic.get(i).getLabel().toUpperCase().compareTo(w.getLabel().toUpperCase()) == 0) {
                return new WordTag(lexic.get(i).getTag().getLabel(),
                        lexic.get(i).getTag().getType());
            }
        }
        return new WordTag("VAR", "");
    }

    public WordTag getWordTag(String label) {
        for (int i = 0; i < lexic.size(); i++) {
            if (lexic.get(i).getLabel().toUpperCase().compareTo(label.toUpperCase()) == 0) {
                return new WordTag(lexic.get(i).getTag().getLabel(),
                        lexic.get(i).getTag().getType());
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
        //TODO: Arreglar para hacer la búsqueda en un árbol
        for (int i = 0; i < lexic.size(); i++)
        {
            if (lexic.get(i).getLabel().toUpperCase().compareTo(entry.getLabel().toUpperCase()) == 0
                    && lexic.get(i).getTag().getLabel().compareTo(entry.getTag().getLabel()) == 0
                    && lexic.get(i).getTag().getType().compareTo(entry.getTag().getType())==0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve un iterador para recorrer el diccionario.
     * @return Iterador a las palabras del diciconario.
     */
    public Iterator<Word> listEntries()
    {
        return lexic.iterator();
    }
}