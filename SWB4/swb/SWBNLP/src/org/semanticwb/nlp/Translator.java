/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.nlp;

import java.util.Iterator;
import java.util.List;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author hasdai
 */
public class Translator {

    //Word Dictionary
    private Lexicon lex;
    //Labels used for AST main nodes
    private String nodeLabels = "SELECT|ASK|PRECON|PREDE|ASIGN|COMPL|COMPG|COMPLE|COMPGE|NAME|COMPNAME|OFFSET|LIMIT";
    private sParser parser;
    private sLexer tokenizer;
    private CommonTokenStream tokens;
    private ANTLRStringStream input;
    private int errCode = 0;
    private String eLog = "";

    /**
     * Creates a new instance of a Natural Language to SparQL Translator with
     * the given dictionary.
     * @param lx Dictionary for the Translator to use.
     */
    public Translator(Lexicon lx) {
        lex = lx;
    }

    /**
     * Transforms a Natural Language query to a SparQL query.
     * @param sent Rescticted-Natural Language sentence for the query.
     * @return SparQL query sentence.
     */
    public String translateSentence(String sent) {
        CommonTree sTree = null;
        errCode = 0;
        input = new ANTLRStringStream(sent);
        tokenizer = new sLexer(input);
        tokens = new CommonTokenStream(tokenizer);
        parser = new sParser(tokens);

        try {
            sParser.squery_return qres = (sParser.squery_return) parser.squery();
            if (parser.getErrorCount() == 0) {
                sTree = (CommonTree) qres.getTree();
                fixTree(sTree);
                //Traverse(sTree, "");
                return getSparqlQuery(sTree, parser.isCompoundQuery());
            } else {
                errCode = 1;
                eLog += "no se pudo obtener AST.\n";
            }
        } catch (org.antlr.runtime.RecognitionException ex) {
            eLog += "No se ha podido traducir la consulta.\n";
            errCode = 2;
        }
        errCode = 2;
        eLog += "No se ha podido traducir la consulta.\n";
        return "";
    }

    public String getErrors() {
        return eLog;
    }

    /**
     * Gets the error code of the translation process.
     * @return 0 if no errors occured, 1 if AST was not generated and 2 if
     * translation was not accomplished.
     */
    public int getErrCode() {
        return errCode;
    }

    private String getSparqlQuery(CommonTree root, boolean isCompound) {
        String res = "";

        if (root.getText().equals("SELECT")) {
            res += ProcessSelect(root, isCompound);
        }
        return res;
    }

    private String ProcessSelect(CommonTree root, boolean isCompound) {
        String limitoff = "";
        String order = "";
        String res = "";

        List<CommonTree> child = root.getChildren();
        if (child != null) {
            res = "SELECT ";
            Iterator<CommonTree> cit = child.iterator();
            while (cit.hasNext()) {
                CommonTree t = cit.next();

                if (t.getText().equals("LIMIT")) {
                    limitoff = limitoff + " LIMIT " + t.getChild(0).getText() + "\n";
                } else if (t.getText().equals("ORDER")) {
                    order += " ORDER BY ";
                    Iterator<CommonTree> oit = t.getChildren().iterator();
                    while (oit.hasNext()) {
                        order = order + "?" + oit.next().getText() + " ";
                    }
                    order = order.trim() + "\n";
                } else if (t.getText().equals("OFFSET")) {
                    limitoff = limitoff + " OFFSET " + t.getChild(0).getText() + "\n";
                } else {
                    res += ProcessNode((CommonTree) t, isCompound);
                }
            }
        }
        return res + "}" + order + limitoff;
    }

    private String ProcessNode(CommonTree root, boolean isCompound) {
        String res = "";

        Iterator<CommonTree> cit;
        List<CommonTree> child = root.getChildren();

        if (child != null) {
            res = res + "?" + root.getText() + " ";
            if (isCompound) {
                res += getVars((CommonTree) root.getChild(0));
            }

            res = res + " \nWHERE\n{\n?" + root.getText() + " rdf:type " + lex.getObjWordTag(root.getText()).getType() + ".\n";
            if (isCompound) {
                res += ProcessPrede((CommonTree) root.getChild(0), root.getText());
            } else {
                res += ProcessPrecon((CommonTree) root.getChild(0), root.getText());
            }
        } else {
            res = res + " *\nWHERE\n{\n?" + root.getText() + " rdf:type " + lex.getObjWordTag(root.getText()).getType() + ".\n";
            res = res + "?" + root.getText() + " ?property ?value\n";
        }
        return res;
    }

    private String getVars(CommonTree root) {
        String res = " ";

        List<CommonTree> child = root.getChildren();
        if (child != null) {
            Iterator<CommonTree> cit = child.iterator();
            while (cit.hasNext()) {
                CommonTree t = cit.next();
                res = res + "?" + t.getText().replace(" ", "_") + " ";
            }
        }

        return res.trim();
    }

    /**
     * Fixes compound names in the AST. Merges all name children of a COMPNAME
     * node in the AST.
     * @param tree AST to fix.
     */
    private void fixTree(CommonTree tree) {
        List<CommonTree> child;

        //If the node is the root of a compound name
        if (tree.getText().equals("COMPNAME")) {
            String newText = "";
            child = tree.getChildren();

            //For each child
            for (int i = 0; i < child.size(); i++) {
                //If the node is a name
                if (nodeLabels.indexOf(tree.getChild(i).getText()) == -1) {
                    //Add node's text to the compound name
                    newText = newText + tree.getChild(i).getText() + " ";
                    //Delete name node
                    tree.deleteChild(i);
                    i--;
                }
            }
            //Set the current node's text to be the compound name
            tree.token.setText(newText.trim());
        }

        //Process all children of current node
        child = tree.getChildren();
        if (child != null) {
            Iterator<CommonTree> cit = tree.getChildren().iterator();
            while (cit.hasNext()) {
                fixTree(cit.next());
            }
        }
    }

    /*private String ProcessNode(CommonTree root, String parent) {
    String res = "";

    //if node label is PRECON
    if (root.getText().equals("PRECON")) {
    List<CommonTree> child = root.getChildren();

    if(child != null) {
    Iterator<CommonTree> cit = child.iterator();
    while(cit.hasNext()) {
    CommonTree t = cit.next();
    //If node is a NAME
    if (nodeLabels.indexOf(t.getText()) == -1 && t.getChildCount() > 0) {
    //ProcessNode with NAME as root
    res += ProcessNode(cit.next(), t.getText());
    } else {
    res += ProcessNode(cit.next(), parent);
    }
    }
    }
    } else if (root.getText().equals("PREDE")) {
    List<CommonTree> child = root.getChildren();

    if(child != null) {
    Iterator<CommonTree> cit = child.iterator();
    while (cit.hasNext()) {

    }

    cit = child.iterator();
    while(cit.hasNext()) {
    CommonTree t = cit.next();
    //If node is a NAME
    if (nodeLabels.indexOf(t.getText()) == -1 && t.getChildCount() > 0) {
    //ProcessNode with NAME as root
    res += ProcessNode(cit.next(), t.getText());
    } else {
    res += ProcessNode(cit.next(), parent);
    }
    }
    }
    } else if (root.getText().equals("ASIGN")) {
    //ProcessAsign
    res =res + "?" + parent + " " + lex.getWordTag(root.getChild(0).getText()).getType()
    + " " + root.getChild(1).getText() + ".\n";
    } else if (root.getText().equals("COMPL")) {
    //ProcessComp
    System.out.println("Processing COMPL");
    } else if (root.getText().equals("COMPG")) {
    //ProcessAsign
    System.out.println("Processing COMPG");
    } else if (root.getText().equals("COMPLE")) {
    //ProcessAsign
    System.out.println("Processing COMPLE");
    } else if (root.getText().equals("COMPGE")) {
    //ProcessAsign
    System.out.println("Processing COMPGE");
    } else {
    //ProcessName
    res = res + "?" + parent + " " + lex.getWordTag(root.getText()).getType()
    + " ?" + root.getText() + ".\n";
    }

    return res;
    }*/
    /**
     * Prints the given AST with indentation.
     * @param root AST to print.
     * @param indent indentation string
     */
    private void Traverse(CommonTree root, String indent) {
        System.out.println(indent + root.getText());
        List<CommonTree> chil = null;

        if (root.getChildCount() > 0) {
            chil = root.getChildren();
        }
        if (chil != null) {
            Iterator<CommonTree> cit = chil.iterator();
            while (cit.hasNext()) {
                CommonTree t = cit.next();
                Traverse(t, indent + "  ");
            }
        }
    }

    private String ProcessPrede(CommonTree root, String parent) {
        String res = "";
        String props = "";
        List<CommonTree> ch = root.getChildren();
        Iterator<CommonTree> cit = ch.iterator();

        while (cit.hasNext()) {
            CommonTree t = (CommonTree) cit.next();
            props = props + " ?" + parent + " " + lex.getPropWordTag(t.getText()).getType() + " ?" + t.getText().replace(" ", "_") + ".\n";
        }
        res += props;
        return res;
    }

    private String ProcessPrecon(CommonTree root, String parent) {
        String res = "";
        List<CommonTree> ch = root.getChildren();
        Iterator<CommonTree> cit = ch.iterator();

        while (cit.hasNext()) {
            CommonTree t = (CommonTree) cit.next();
            //String rootId = lex.getObjWordTag(t.getText()).getType();
            //System.out.println(">>" + getElmProperty(parent, t.getText()));
            String pName = "";
            if (t.getText().equals("ASIGN")) {
                pName = assertProperty(parent, t.getChild(0).getText());
                if (!pName.equals("")) {
                    res = res + "?" + parent + " " + pName + " " + t.getChild(1).getText() + ".\n";
                }
            } else if (t.getText().equals("COMPL")) {
                pName = assertProperty(parent, t.getChild(0).getText());
                if (!pName.equals("")) {
                    res = res + "?" + parent + " " + pName + " ?v_" + t.getChild(0) + ".\n";
                    res = res + "FILTER ( ?v_" + t.getChild(0).getText() + " < " + t.getChild(1).getText() + ").\n";
                }
            } else if (t.getText().equals("COMPG")) {
                pName = assertProperty(parent, t.getChild(0).getText());
                if (!pName.equals("")) {
                    res = res + "?" + parent + " " + pName + " ?v_" + t.getChild(0) + ".\n";
                    res = res + "FILTER ( ?v_" + t.getChild(0).getText() + " > " + t.getChild(1).getText() + ").\n";
                }
            } else if (t.getText().equals("COMPLE")) {
                pName = assertProperty(parent, t.getChild(0).getText());
                if (!pName.equals("")) {
                    res = res + "?" + parent + " " + pName + " ?v_" + t.getChild(0) + ".\n";
                    res = res + "FILTER ( ?v_" + t.getChild(0).getText() + " <= " + t.getChild(1).getText() + ").\n";
                }
            } else if (t.getText().equals("COMPGE")) {
                pName = assertProperty(parent, t.getChild(0).getText());
                if (!pName.equals("")) {
                    res = res + "?" + parent + " " + pName + " ?v_" + t.getChild(0) + ".\n";
                    res = res + "FILTER ( ?v_" + t.getChild(0).getText() + " >= " + t.getChild(1).getText() + ").\n";
                }
            } else {
                pName = assertProperty(parent, t.getChild(0).getText());
                System.out.println("--" + pName);
                if (!pName.equals("")) {
                    res = res + "?" + parent + " " + pName + " ?" + t.getText().replace(" ", "_") + ".\n";
                }
            }
        }
        return res;
    }

    private String assertProperty(String parent, String pName) {
        String res = "";
        String name = lex.getObjWordTag(parent).getType().replace(":", "_");
        //System.out.println("--" + name + ", " + pName);
        boolean found = false;
        SemanticProperty sp = null;

        SemanticClass sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(name);
        if (sc != null) {
            Iterator<SemanticProperty> sit = sc.listProperties();
            while (sit.hasNext() && !found) {
                sp = sit.next();
                if (sp.getDisplayName(lex.getLanguage()).equals(pName)) {
                    res = res + sp.getPrefix() + ":" + sp.getName();
                    found = true;
                }
            }
            sp = sit.next();
        }
        if (!found) {
            eLog += "La clase " + sc.getDisplayName(lex.getLanguage()) + " no tiene una propiedad llamada "
                    + sp.getDisplayName(lex.getLanguage()) + "\n";
            System.out.println("ERROR");
            errCode = 3;
        }
        return res;
    }
}