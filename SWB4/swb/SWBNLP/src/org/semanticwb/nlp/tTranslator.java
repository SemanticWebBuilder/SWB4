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
                traverseAST(sTree, "");
                res += processSelectQuery(sTree, parser.hasPrecon(), parser.hasPrede());

                System.out.println("---");
                return res;
            } else {
                errCode = 2;
                eLog += "no se pudo obtener AST.\n";
                return "";
            }
        } catch (org.antlr.runtime.RecognitionException ex) {
            errCode = 2;
            eLog += "No se ha podido traducir la consulta.\n";
            return "";
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
                        order = order + "?" + oit.next().getText().replace(" ", "_").replaceAll("[\\(|\\)]", "") + " ";
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
                        varList = varList + "?" + t.getText().replace(" ", "_").replaceAll("[\\(|\\)]", "");
                    }
                    res = res + varList + "\nWHERE \n{\n";
                    String etype = lex.getObjWordTag(t.getText()).getType();
                    if(!etype.equals("")) {
                        res = res + "?" + t.getText().replace(" ", "_").replaceAll("[\\(|\\)]", "") + " rdf:type " + etype
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
                res += processNode(t, root.getText(), "");
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
    private String processNode(CommonTree root, String parent, String parentLabel) {
        String res = "";
        List<CommonTree> child = root.getChildren();
        String nname = root.getText();

        System.out.println(nname + "[ " + parent + ", " + parentLabel + "]");
        if (nname.equals("PRECON")) {
            //Procesar los hijos con el padre del actual
            if (child != null) {
                Iterator<CommonTree> cit = child.iterator();
                while (cit.hasNext()) {
                    CommonTree t = cit.next();

                    res += processNode(t, parent, parentLabel);
                }
            }
        } else if (nname.equals("ASIGN") || nname.equals("COMPG") || nname.equals("COMPL") ||
                    nname.equals("COMPLE") || nname.equals("COMPGE")) {
                    res = res + processStatement(root, parent, parentLabel);
        } else if (nname.equals("PREDE")) {
            if (!root.getChild(0).getText().equals("MODTO")) {
                if (child != null) {
                    Iterator<CommonTree> cit = child.iterator();
                    while (cit.hasNext()) {
                        CommonTree t = cit.next();
                        String cname = t.getText();

                        res = res + "?" + parent.replace(" ", "_") + " " +
                                assertPropertyType(cname, parent) + " ?" +
                                cname.replace(" ", "_") + ".\n";
                    }
                }
            } else {
                res = res + "?" + parent.replace(" ", "_") + " ?prop ?val.\n";
            }
        } else {
            if (child != null) {
                String rangeType = assertPropertyRangeType(nname, parent);
                if (!rangeType.equals("")) {
                    SemanticClass scl = assertPropertyRangeClass(nname, parent);
                    res = res + "?" + nname.replace(" ","_") + " rdf:type " + rangeType + ".\n";
                    String pName = assertPropertyType(nname, parent);
                    if (!pName.equals("")) {
                        res = res + "?" + parent.replace(" ","_") + " " + pName + " ?" +
                                nname.replace(" ", "_") + ".\n";
                    }
                    if (scl != null) {
                        String cName = scl.getDisplayName(lex.getLanguage());
                        Iterator<CommonTree> cit = child.iterator();
                        while (cit.hasNext()) {
                            CommonTree t = cit.next();
                            res += processNode(t, cName, nname);
                        }
                    }
                }
            } else {
                String pName = assertPropertyType(nname, parent);
                if (!pName.equals("")) {
                    if (!parentLabel.equals("")) {
                        res = res + "?" + parentLabel.replace(" ", "_") +
                            " " + pName + " ?" + nname.replace(" ", "_") + ".\n";
                    } else {
                        res = res + "?" + parent.replace(" ", "_") +
                            " " + pName + " ?" + nname.replace(" ", "_") + ".\n";
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
            return "?" + parent.replace(" ", "_") + " ?prop ?val";
        }

        List<CommonTree> child = root.getChildren();
        if (child != null) {
            Iterator<CommonTree> cit = child.iterator();
            while (cit.hasNext()) {
                CommonTree t = cit.next();
                res = res + "?" + t.getText().replace(" ", "_").replaceAll("[\\(|\\)]", "") + " ";
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
    private String processStatement(CommonTree root, String parent, String parentLabel) {
        String res = "";
        String pName = assertPropertyType(root.getChild(0).getText(), parent);

        if (root.getText().equals("ASIGN")) {
            if (!pName.equals("")) {
                res = res + "?" + parentLabel.replace(" ", "_") + " " + pName + " " + root.getChild(1).getText() + ".\n";
            }
        } else if (root.getText().equals("COMPL")) {
                if (!pName.equals("")) {
                    res = res + "?" + parent.replace(" ", "_") + " " + pName + " ?v_" + root.getChild(0).getText().replace(" ", "_") + ".\n";
                    res = res + "FILTER ( ?v_" + root.getChild(0).getText().replace(" ", "_") + " < " + root.getChild(1).getText() + ").\n";
                }
            } else if (root.getText().equals("COMPG")) {
                if (!pName.equals("")) {
                    res = res + "?" + parent.replace(" ", "_") + " " + pName + " ?v_" + root.getChild(0).getText().replace(" ", "_") + ".\n";
                    res = res + "FILTER ( ?v_" + root.getChild(0).getText().replace(" ", "_") + " > " + root.getChild(1).getText() + ").\n";
                }
            } else if (root.getText().equals("COMPLE")) {
                if (!pName.equals("")) {
                    res = res + "?" + parent.replace(" ", "_") + " " + pName + " ?v_" + root.getChild(0).getText().replace(" ", "_") + ".\n";
                    res = res + "FILTER ( ?v_" + root.getChild(0).getText().replace(" ", "_") + " <= " + root.getChild(1).getText() + ").\n";
                }
            } else if (root.getText().equals("COMPGE")) {
                if (!pName.equals("")) {
                    res = res + "?" + parent.replace(" ", "_") + " " + pName + " ?v_" + root.getChild(0).getText().replace(" ", "_") + ".\n";
                    res = res + "FILTER ( ?v_" + root.getChild(0).getText().replace(" ", "_") + " >= " + root.getChild(1).getText() + ").\n";
                }
            } else {
                if (!pName.equals("")) {
                    res = res + "?" + parent.replace(" ", "_") + " " + pName + " ?" +
                            root.getText().replace(" ", "_").replaceAll("[\\(|\\)]", "") + ".\n";
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
        String name = lex.getObjWordTag(className).getObjId();
        boolean found = false;
        SemanticProperty sp = null;
        Iterator<SemanticProperty> sit;
        System.out.println(">verificando " + propertyName + " de " + className);
        SemanticClass sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(name);
        if (sc != null) {
            sit = sc.listProperties();
            while (sit.hasNext() && !found) {
                sp = sit.next();
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

    public SemanticClass assertPropertyRangeClass(String propertyName, String className) {
        String name = lex.getObjWordTag(className).getObjId();
        boolean found = false;
        SemanticProperty sp = null;
        Iterator<SemanticProperty> sit;

        SemanticClass sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(name);
        if (sc != null) {
            sit = sc.listProperties();
            while (sit.hasNext() && !found) {
                sp = sit.next();
                if (sp.getDisplayName(lex.getLanguage()).equals(propertyName)) {
                    found = true;
                    if (sp.isObjectProperty()) {
                        StringBuffer bf = new StringBuffer();
                        bf.append(sp.getRangeClass());

                        SemanticClass rg = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(bf.toString());
                        if (rg != null) {
                            return rg;
                        }
                    }
                    else {
                        errCode = 3;
                        eLog += "La propiedad " + sp.getDisplayName(lex.getLanguage()) + "no es de tipo Objeto.\n";
                    }
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
    return null;
    }

    public String assertPropertyRangeType(String propertyName, String className) {
        String res = "";
        String name = lex.getObjWordTag(className).getObjId();
        boolean found = false;
        SemanticProperty sp = null;
        Iterator<SemanticProperty> sit;

        SemanticClass sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(name);
        if (sc != null) {
            sit = sc.listProperties();
            while (sit.hasNext() && !found) {
                sp = sit.next();
                if (sp.getDisplayName(lex.getLanguage()).equals(propertyName)) {
                    found = true;
                    if (sp.isObjectProperty()) {
                        StringBuffer bf = new StringBuffer();
                        bf.append(sp.getRangeClass());

                        SemanticClass rg = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(bf.toString());
                        if (rg != null) {
                            res = res + lex.getObjWordTag(rg.getDisplayName(lex.getLanguage())).getType();
                        }
                    }
                    else {
                        errCode = 3;
                        eLog += "La propiedad " + sp.getDisplayName(lex.getLanguage()) + "no es de tipo Objeto.\n";
                    }
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
    return res ;
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