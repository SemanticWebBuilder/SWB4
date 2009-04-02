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
public class tTranslator {

    private tParser parser;
    private sLexer tokenizer;
    private Lexicon lex;
    private CommonTokenStream tokens;
    private ANTLRStringStream input;
    private String nodeLabels = "SELECT|PRECON|PREDE|ASIGN|COMPL|COMPG|COMPLE|COMPGE|OFFSET|LIMIT|ORDER";
    private String eLog = "";
    private int errCode = 0;
    private String language = "es";

    public tTranslator (Lexicon dict) {
        lex = dict;
        language = dict.getLanguage();
    }

    /**
     * Transforms a Natural Language query to a SparQL query. Using an ANTLR
     * parser, this method builds an AST (Abstract Sintax Tree) and traverses it
     * to generate the SparQL query.
     * @param sent Rescticted-Natural Language sentence for the query.
     * @return SparQL query sentence.
     */
    public String translateSentence(String sent) {
        String res = "";
        CommonTree sTree = null;
        input = new ANTLRStringStream(sent);
        tokenizer = new sLexer(input);
        tokens = new CommonTokenStream(tokenizer);
        parser = new tParser(tokens);

        try {
            tParser.squery_return qres = (tParser.squery_return) parser.squery();
            if (parser.getErrorCount() == 0) {
                sTree = (CommonTree) qres.getTree();
                fixNames(sTree);
                res += processSelectQuery(sTree, parser.hasPrecon(), parser.hasPrede());
                //System.out.println(sTree.toStringTree());
                traverseAST(sTree, "");
                System.out.println(res);
                return res;
            } else {
                return "no se pudo obtener AST.\n";
            }
        } catch (org.antlr.runtime.RecognitionException ex) {
            return "No se ha podido traducir la consulta.\n";
        }
    }

    /**
     * Transforms a SELECT node in the AST to a SparQL query fragment.
     * @param root SELECT node.
     * @param isCompound
     * @return
     */
    private String processSelectQuery(CommonTree root, boolean hasPrecon, boolean hasPrede) {
        String limitoff = "";
        String order = "";
        String res = "";
        String varList = "";

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
                        order = order + "?" + oit.next().getText().replace(" ", "_") + " ";
                    }
                    order = order.trim() + "\n";
                } else if (t.getText().equals("OFFSET")) {
                    limitoff = limitoff + " OFFSET " + t.getChild(0).getText() + "\n";
                } else {
                    if (hasPrede) {
                        if (hasPrecon) {
                            varList += getVarList((CommonTree)t.getChild(1), t.getText());
                        } else {
                            varList += getVarList((CommonTree)t.getChild(0), t.getText());
                        }
                    } else {
                        varList = varList + "?" + t.getText().replace(" ", "_");
                    }
                    res = res + varList + "\nWHERE \n{\n";
                    String etype = lex.getObjWordTag(t.getText()).getType();
                    if(!etype.equals("")) {
                        res = res + "?" + t.getText().replace(" ", "_") + " rdf:type " + etype
                            + ".\n";
                        res += startParsing(t);
                    } else {
                        errCode = 2;
                        eLog = eLog + t.getText() + " no es una clase.";
                    }
                }
            }
        }
        return res + "}" + order + limitoff;
    }

    private String startParsing(CommonTree root) {
        String res = "";
        List<CommonTree> child = root.getChildren();

        if (child != null) {
            Iterator<CommonTree> cit = child.iterator();
            while (cit.hasNext()) {
                CommonTree t = cit.next();
                res += processNode(t, root.getText());
            }
        }
        return res;
    }

    /**
     * Transforms an AST node into a SparQL query fragment.
     * @param root AST node to transform.
     * @param parent name of the parent object of the node.
     * @return a SparQL query fragment for the AST node.
     */
    private String processNode(CommonTree root, String parent) {
        String res = "";
        List<CommonTree> child = null;
        String nname = root.getText();

        if (nname.equals("PRECON")) {
             child = root.getChildren();

            if (child != null) {
                Iterator<CommonTree> cit = child.iterator();
                while (cit.hasNext()) {
                    CommonTree t = cit.next();
                    res += processNode(t, parent);
                }
            }
        } else if (nname.equals("ASIGN") || nname.equals("COMPL") ||
                        nname.equals("COMPG") || nname.equals("COMPGE") ||
                        nname.equals("COMPLE")) {
             res += processStatement(root, parent);
        } else if (nname.equals("PREDE")) {
            if (!root.getChild(0).getText().equals("MODTO")) {
                child = root.getChildren();

                if (child != null) {
                    Iterator<CommonTree> cit = child.iterator();
                    while (cit.hasNext()) {
                        CommonTree t = cit.next();
                        String pName = "";
                        pName = assertPropertyType(t.getText(), parent);
                        if (!pName.equals("")) {
                            res = res + "?" + parent + " " + pName + " " + "?" + t.getText() + ".\n";
                        }
                    }
                }
            } else {
                res = res + "?" + parent + " ?prop ?val.\n";
            }
        } else if (nodeLabels.indexOf(nname) == -1) {
            if (root.getChildren() != null) {
                String pName = assertPropertyType(nname, parent);
                if (!pName.equals("")) {
                res = res + "?" + nname + " rdf:type " +
                        pName + ".\n";
                }
                res = res + "?" + parent + " ?_" + nname + " ?" + nname + ".\n";
                res += startParsing(root);
            } else {
                String pName = assertPropertyType(nname, parent);
                if(!pName.equals("")) {
                    res = res + "?" + parent + " " +
                        pName + " ?" + nname + ".\n";
                }
            }
        }
        return res;
    }

    private String processPrecon (CommonTree root, String parent) {
        String res = "";
        List<CommonTree> child = root.getChildren();

        if (child != null) {
            Iterator<CommonTree> cit = child.iterator();
            while(cit.hasNext()) {
                CommonTree t = cit.next();
                String nname = t.getText();
                System.out.println("p::__" + nname);

                if (nname.equals("ASIGN") || nname.equals("COMPL") ||
                        nname.equals("COMPG") || nname.equals("COMPGE") ||
                        nname.equals("COMPLE")) {
                    res += processStatement(t, parent);
                } else {
                    String pName = assertPropertyType(nname, parent);
                    if (!pName.equals("")) {
                        res = res + "?" + nname + " rdf:type " + pName + ".\n";
                        res = res + "?" + parent + " ?_" + nname + "?" + nname + ".\n";
                    }
                }
            }
        }
        return res;
    }

    /**
     * Transforms a PREDE node into a SparQL query fragment. If PREDE's first
     * child is a MODTO node, an '*' is added to the varList of the SELECT clause
     * for the query. Otherwise, varList consists of the names of all child
     * nodes of PREDE.
     * @param root the PREDE node.
     * @param parent name of the parent object of PREDE node.
     * @return a SparQL query fragment, specifically a varList for the SelecQuery
     * production of the SparQL query language grammar.
     */
    private String getVarList(CommonTree root, String parent) {
        String res = "";

        if (root.getChild(0).getText().equals("MODTO")) {
            return "*";
        }

        List<CommonTree> child = root.getChildren();
        if (child != null) {
            Iterator<CommonTree> cit = child.iterator();
            while (cit.hasNext()) {
                CommonTree t = cit.next();
                res = res + "?" + t.getText() + " ";
            }
        }
        return res.trim();
    }

    /**
     * Transforms an ASIGN, COMPL, COMPG, COMPLE or COMPGE node into a SparQL
     * query fragment. The last four nodes generate a FILTER clause in the
     * resulting fragment.
     * @param root one of the above nodes.
     * @param parent name of the parent object of the statement node.
     * @return a SparQL query fragment, specifically a triple for an ASIGN node
     * or a triple and a FILTER clause for the node.
     */
    private String processStatement(CommonTree root, String parent) {
        String res = "";
        String pName = assertPropertyType(root.getChild(0).getText(), parent);

        if (root.getText().equals("ASIGN")) {
            if (!pName.equals("")) {
                res = res + "?" + parent + " " + pName + " " + root.getChild(1).getText() + ".\n";
            }
        } else if (root.getText().equals("COMPL")) {
                if (!pName.equals("")) {
                    res = res + "?" + parent + " " + pName + " ?v_" + root.getChild(0).getText() + ".\n";
                    res = res + "FILTER ( ?v_" + root.getChild(0).getText() + " < " + root.getChild(1).getText() + ").\n";
                }
            } else if (root.getText().equals("COMPG")) {
                if (!pName.equals("")) {
                    res = res + "?" + parent + " " + pName + " ?v_" + root.getChild(0) + ".\n";
                    res = res + "FILTER ( ?v_" + root.getChild(0).getText() + " > " + root.getChild(1).getText() + ").\n";
                }
            } else if (root.getText().equals("COMPLE")) {
                if (!pName.equals("")) {
                    res = res + "?" + parent + " " + pName + " ?v_" + root.getChild(0) + ".\n";
                    res = res + "FILTER ( ?v_" + root.getChild(0).getText() + " <= " + root.getChild(1).getText() + ").\n";
                }
            } else if (root.getText().equals("COMPGE")) {
                if (!pName.equals("")) {
                    res = res + "?" + parent + " " + pName + " ?v_" + root.getChild(0) + ".\n";
                    res = res + "FILTER ( ?v_" + root.getChild(0).getText() + " >= " + root.getChild(1).getText() + ").\n";
                }
            } else {
                if (!pName.equals("")) {
                    res = res + "?" + parent + " " + pName + " ?" + root.getText().replace(" ", "_") + ".\n";
                }
            }
        return res;
    }

    /**
     * Validates if propertyName is a property of className in the
     * SemanticVocabulary.
     * @param propertyName name of the property to get type for.
     * @param className name of the class which contains the propertyName.
     * @return the RDF type of propertyName if it's a propery of className,
     * empty String otherwise.
     */
    private String assertPropertyType(String propertyName, String className) {
        String res = "";
        String name = lex.getObjWordTag(className).getType().replace(":", "_");
        //System.out.println("--" + name + ", " + pName);
        boolean found = false;
        SemanticProperty sp = null;
        Iterator<SemanticProperty> sit;

        SemanticClass sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(name);
        //System.out.println(sc.getDisplayName(lex.getLanguage()));
        if (sc != null) {
            sit = sc.listProperties();
            while (sit.hasNext() && !found) {
                sp = sit.next();
                //System.out.println("  " + sp.getDisplayName(lex.getLanguage()));
                if (sp.getDisplayName(lex.getLanguage()).equals(propertyName)) {
                    res = res + sp.getPrefix() + ":" + sp.getName();
                    found = true;
                }
            }
            if (!found) {
                eLog += "La clase " + sc.getDisplayName(lex.getLanguage()) + " no tiene una propiedad llamada ";
                if (sit.hasNext()) {
                    sp = sit.next();
                    eLog += sp.getDisplayName(lex.getLanguage()) + "\n";
                } else {
                    eLog += propertyName + "\n";
                }
                errCode = 3;
            }
        }
        return res;
    }

    /**
     * Fixes node names in the AST. Removes brackets in NAME nodes.
     * @param tree AST to fix.
     */
    private void fixNames(CommonTree tree) {
        //If the node is the root of a NAME
        if (nodeLabels.indexOf(tree.getText()) == -1) {
            //Set the current node's text to be the child text
            tree.token.setText(tree.getText().replaceAll("[\\[|\\]]", ""));
        }

        //Process all children of current node
        List<CommonTree> child = tree.getChildren();
        if (child != null) {
            Iterator<CommonTree> cit = tree.getChildren().iterator();
            while (cit.hasNext()) {
                fixNames(cit.next());
            }
        }
    }

    /**
     * Prints the given AST with indentation.
     * @param root AST to print.
     * @param indent indentation string
     */
    private void traverseAST(CommonTree root, String indent) {
        System.out.println(indent + root.getText());
        List<CommonTree> chil = null;

        if (root.getChildCount() > 0) {
            chil = root.getChildren();
        }
        if (chil != null) {
            Iterator<CommonTree> cit = chil.iterator();
            while (cit.hasNext()) {
                CommonTree t = cit.next();
                traverseAST(t, indent + "  ");
            }
        }
    }

    public int getErrCode () {
        return errCode;
    }

    public String getErrors () {
        return eLog;
    }

}