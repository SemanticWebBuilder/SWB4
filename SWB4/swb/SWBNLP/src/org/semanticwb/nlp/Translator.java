/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
/**
 *
 * @author hasdai
 */
public class Translator {
    private int errCode = 0;
    private CommonTree sTree = null;
    private Lexicon lex;
    private String errLog = "";
    boolean ask = false;

    public Translator(Lexicon lx) {
        lex = lx;
    }

    public int getErrorCode() {
        return errCode;
    }

    public boolean isSelectQuery() {
        return !ask;
    }

    public String translateSentence(String sentence) {
        ANTLRStringStream input = new ANTLRStringStream(sentence);
        sLexer tokenizer = new sLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(tokenizer);
        String res = "";

        sParser parser = new sParser(tokens);
        try {
            sParser.squery_return qres = (sParser.squery_return) parser.squery(lex);
            if (parser.getErrorCount() == 0) {
                sTree = (CommonTree)qres.getTree();
                res = parser.getSparqlQuery();
            }
        } catch (RecognitionException e) {

        }
        return res;
    }

    public CommonTree getSentenceTree() {
        return sTree;
    }

    /**
     * Obtiene la lista de palabras en la lista de objetos de la consulta
     * @param r raíz del árbol para buscar el nodo de lista de objetos
     * @return Lista de palabras que representan objetos (se ignoran aquellas
     * que no constituyen un objeto).
     */
    public ArrayList<Word> getObjList(CommonTree r) {
        ArrayList<Word> res = new ArrayList<Word>();

        //Buscar el nodo de lista de objetos
        CommonTree tt = findNode(r, "OBJLIST");
        if (tt != null) {
            //Obtener los objetos
            List<CommonTree> child = tt.getChildren();

            //Recorrer los nodos de la lista de objetos
            Iterator<CommonTree> it = child.iterator();
            while (it.hasNext()) {
                CommonTree curNode = it.next();
                String nname = "";

                //Si es un nombre compuesto
                if (curNode.getText().compareTo("COMPNAME") == 0) {
                    List<CommonTree> comps = curNode.getChildren();
                    Iterator<CommonTree> cit = comps.iterator();

                    while (cit.hasNext()) {
                        nname = nname + cit.next().getText() + " ";
                    }
                } else {
                    nname = curNode.getText();
                }

                //Crea una palabra para buscarla en el diccionario
                Word temp = new Word(nname.trim());
                //Se establece la etiqueta del elemento
                temp.setTag(lex.getWordTag(temp));

                //Si es un objeto o una variable, agregarlo a la lista
                if (temp.getTag().getLabel().compareTo("OBJ") == 0) {
                    res.add(temp);
                } else if (temp.getTag().getLabel().compareTo("VAR") == 0) {
                    res.add(temp);
                }
            }
        }
        return res;
    }

    public ArrayList<String> getPropList(CommonTree r) {
        ArrayList<String> res = new ArrayList<String>();
        String propval = "";

        //Buscar el nodo de lista de objetos
        CommonTree tt = findNode(r, "PROPLIST");

        if (tt != null) {
            List<CommonTree> child = tt.getChildren();
            Iterator<CommonTree> it = child.iterator();

            while (it.hasNext()) {
                CommonTree curNode = it.next();
                String nname = "";
                propval = "";
                //System.out.println("Analizando " + curNode.getText());
                //Si es un nombre compuesto
                if (curNode.getText().compareTo("COMPNAME") == 0) {
                    List<CommonTree> comps = curNode.getChildren();
                    Iterator<CommonTree> cit = comps.iterator();


                    while (cit.hasNext()) {
                        nname = nname + cit.next().getText() + " ";
                    }
                } else if (curNode.getText().compareTo("ASIGN") == 0) {
                    nname = curNode.getChild(0).getText();
                    propval = curNode.getChild(1).getText();
                } else {
                    nname = curNode.getText();
                }

                Word temp = new Word(nname.trim());
                temp.setTag(lex.getWordTag(temp));

                //Si es una propiedad
                if (temp.getTag().getLabel().compareTo("PRO") == 0) {
                    res.add(temp.getTag().getType() + " ?" + temp.getLabel().replace(' ', '_'));
                }
            }
        }
        return res;
    }

    public CommonTree findNode(CommonTree root, String label) {
        CommonTree res = null;

        if (root.getText().compareTo(label) == 0) {
            res = root;
        } else {
            List<CommonTree> child = root.getChildren();

            if (child != null) {
                Iterator<CommonTree> it = child.iterator();
                while (it.hasNext()) {
                    res = findNode(it.next(), label);
                    if (res != null) {
                        return res;
                    }
                }
            }
        }
        return res;
    }
}