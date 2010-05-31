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

package org.semanticwb.nlp.translation;

import org.semanticwb.nlp.analysis.ComplexParser;
import java.util.List;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.tree.CommonTree;
import org.semanticwb.SWBPlatform;
import org.semanticwb.nlp.SWBDictionary;
import org.semanticwb.nlp.analysis.EnglishLexer;
import org.semanticwb.nlp.analysis.SpanishLexer;
import org.semanticwb.nlp.spell.SWBSpellChecker;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;

/**
 * A natural language to SparQl query translator. Uses the Abstract Sintax Tree
 * (AST) of a sentence obtained with the antlr generated parser ({@link ComplexParser})
 * in order to build a structured SparQl query.
 * <p>
 * Un traductor de consultas en lenguaje natural a consultas SparQl. Usa el árbol
 * de sintaxis abstracta (AST) de una oración obtenido con el analizador generado
 * por antlr ({@link ComplexParser}) para construir una consulta SparQl
 * estructurada.
 *
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class SWBSparqlTranslator {

    private ComplexParser parser;     //ANTLR parser
    private Lexer tokenizer;   //ANTLR tokenizer
    private SWBDictionary lex;        //Dictionary
    private CommonTokenStream tokens;   //TokenStream for parsing
    private ANTLRStringStream input;    //StringStream to parse
    private String nodeLabels = "SELECT|PRECON|PREDE|ASIGN|COMPL|COMPG|COMPLE|COMPGE|OFFSET|LIMIT|ORDER|COMPAS|COMPRNG|INTERVAL";
    private String eLog = "";   //Error log
    private int errCode = 0;    //Last error code
    private SWBSpellChecker speller = null;
    private boolean emptyQuery = false;
    private String subject = "";
    private SemanticVocabulary vocabulary;
    private String lang;

    public SWBSparqlTranslator(SWBDictionary dict) {
        lex = dict;
        //TODO: agregar soporte multi-lenguajes
        lang = "es";
        vocabulary = SWBPlatform.getSemanticMgr().getVocabulary();
        speller = new SWBSpellChecker(lex.getSpellDictPath());
    }

    public String translateSentence(String sent, boolean sRequired) {
        String res = "";
        CommonTree sTree = null;
        input = new ANTLRStringStream(sent);
        tokenizer = getLocaleLexer(lex.getLexicon(lang).getLanguageCode(), input);
        tokens = new CommonTokenStream(tokenizer);
        parser = new ComplexParser(tokens);

        try {
            ComplexParser.squery_return qres = (ComplexParser.squery_return) parser.squery();
            if (parser.getErrorCount() == 0) {
                sTree = (CommonTree) qres.getTree();
                fixNames(sTree);
                //traverseAST(sTree, "");
                res += processSelectQuery(sTree, parser.hasPrecon(), parser.hasPrede(), sRequired);
                //System.out.println(res);
                return res;
            } else {
                errCode = 2;
                eLog += "La consulta no está bien escrita.\n";
                return "";
            }
        } catch (org.antlr.runtime.RecognitionException ex) {
            errCode = 2;
            eLog += "No se ha podido traducir la consulta.\n";
            return "";
        }
    }

    private void fixNames(CommonTree tree) {
        //If the node is the root of a NAME
        if (nodeLabels.indexOf(tree.getText()) == -1) {
            //Set the current node's text to be the child text
            tree.token.setText(tree.getText().replaceAll("[\\[|\\]]", ""));
        }

        //Process all children of current node
        List<CommonTree> child = tree.getChildren();
        if (child != null) {
            for (CommonTree t : child) {
                fixNames(t);
            }
        }
    }

    private String processSelectQuery(CommonTree root, boolean hasPrecon, boolean hasPrede, boolean subjectRequired) {
        String limitoff = "";
        String order = "";
        String res = "";
        String varList = "";
        String patterns = "";

        List<CommonTree> child = root.getChildren();
        if (child != null) {
            res = "SELECT DISTINCT ";
            for (CommonTree t : child) {
                if (t.getText().equals("LIMIT")) {
                    limitoff = limitoff + " LIMIT " + t.getChild(0).getText() + "\n";
                } else if (t.getText().equals("ORDER")) {
                    order += " ORDER BY ";
                    for (CommonTree oit : (List<CommonTree>) t.getChildren()) {
                        order = order + "?" + oit.getText().replace(" ", "_").replaceAll("[\\(|\\)]", "") + " ";
                    }
                    order = order.trim() + "\n";
                } else if (t.getText().equals("OFFSET")) {
                    limitoff = limitoff + " OFFSET " + t.getChild(0).getText() + "\n";
                } else {
                    subject = t.getText().replace(" ", "_").replaceAll("[\\(|\\)]", "");
                    if (hasPrede) {
                        if (hasPrecon) {
                            varList += getVarList((CommonTree) t.getChild(1), t.getText());
                        } else {
                            varList += getVarList((CommonTree) t.getChild(0), t.getText());
                        }
                    } else {
                        varList = varList + "?" + t.getText().replace(" ", "_").replaceAll("[\\(|\\)]", "");
                    }
                    if (!varList.contains(subject) && subjectRequired) {
                        res = res + "?" + subject + " " + varList + "\nWHERE \n{\n";
                    } else {
                        res = res + varList + "\nWHERE \n{\n";
                    }
                    String etype = "";
                    if (lex.getLexicon(lang).getWord(t.getText(), true) != null) {
                        etype = lex.getLexicon(lang).getWord(t.getText(), true).getTag().getId();
                    }
                    //String etype = lex.getObjWordTag(t.getText()).getType();
                    if (!etype.equals("")) {
                        patterns = patterns + "?" + t.getText().replace(" ", "_").replaceAll("[\\(|\\)]", "") + " rdf:type " + etype + ".\n";
                        patterns += startParsing(t);
                    } else {
                        errCode = 2;
                        eLog = eLog + t.getText() + " no es una clase.";
                    }
                }
            }
        }
        if (patterns.equals("")) {
            emptyQuery = true;
        }
        return res + patterns + "}" + order + limitoff;
    }

    private String startParsing(CommonTree root) {
        String res = "";
        List<CommonTree> child = root.getChildren();

        if (child != null) {
            for (CommonTree t : child) {
                res += processNode(t, root.getText(), root.getText());
            }
        }
        return res;
    }

    private String processNode(CommonTree root, String parent, String parentLabel) {
        String res = "";
        List<CommonTree> child = root.getChildren();
        String nname = root.getText();

        //System.out.println(nname + "[ " + parent + ", " + parentLabel + "]");
        if (nname.equals("PRECON")) {
            //Procesar los hijos con el padre del actual
            if (child != null) {
                for (CommonTree t : child) {
                    res += processNode(t, parent, parentLabel);
                }
            }
        } else if (nname.equals("ASIGN") || nname.equals("COMPG") || nname.equals("COMPL") ||
                nname.equals("COMPLE") || nname.equals("COMPGE") || nname.equals("COMPAS") ||
                nname.equals("COMPRNG")) {
            res = res + processStatement(root, parent, parentLabel);
        } else if (nname.equals("PREDE")) {
            if (!root.getChild(0).getText().equals("MODTO")) {
                if (child != null) {
                    for (CommonTree t : child) {
                        String cname = t.getText();

                        res = res + "?" + parent.replace(" ", "_").replaceAll("[\\(|\\)]", "") + " " +
                                getPropertyType(cname, parent) + " ?" +
                                cname.replace(" ", "_").replaceAll("[\\(|\\)]", "") + ".\n";
                    }
                }
            } else {
                res = res + "?" + parent.replace(" ", "_").replaceAll("[\\(|\\)]", "") + " ?prop ?val.\n";
            }
        } else {
            if (child != null) {
                String rangeType = getPropertyRangeType(nname, parent);
                if (!rangeType.equals("")) {
                    SemanticClass scl = getPropertyRangeClass(nname, parent);
                    res = res + "?" + nname.replace(" ", "_").replaceAll("[\\(|\\)]", "") + " rdf:type " + rangeType + ".\n";
                    String pName = getPropertyType(nname, parent);
                    if (!pName.equals("")) {
                        res = res + "?" + parent.replace(" ", "_").replaceAll("[\\(|\\)]", "") + " " + pName + " ?" +
                                nname.replace(" ", "_").replaceAll("[\\(|\\)]", "") + ".\n";
                    }
                    if (scl != null) {
                        String cName = scl.getDisplayName(lex.getLexicon(lang).getLanguageCode());
                        for (CommonTree t : child) {
                            res += processNode(t, cName, nname);
                        }
                    }
                }
            } else {
                String pName = getPropertyType(nname, parent);
                if (!pName.equals("")) {
                    if (!parentLabel.equals("")) {
                        res = res + "?" + parentLabel.replace(" ", "_").replaceAll("[\\(|\\)]", "") +
                                " " + pName + " ?" + nname.replace(" ", "_").replaceAll("[\\(|\\)]", "") + ".\n";
                    } else {
                        res = res + "?" + parent.replace(" ", "_").replaceAll("[\\(|\\)]", "") +
                                " " + pName + " ?" + nname.replace(" ", "_").replaceAll("[\\(|\\)]", "") + ".\n";
                    }
                }
            }
        }
        return res;
    }

    private String processStatement(CommonTree root, String parent, String parentLabel) {
        String res = "";
        String pName = getPropertyType(root.getChild(0).getText(), parent);
        //System.out.println("verificando " + root.getChild(0).getText() + " de " + parent + " con etiqueta " + parentLabel);
        if (root.getText().equals("ASIGN")) {
            if (!pName.equals("")) {
                res = res + "?" + parentLabel.replace(" ", "_").replaceAll("[\\(|\\)]", "") +
                        " " + pName + " " + root.getChild(1).getText() + ".\n";
            }
        } else if (root.getText().equals("COMPAS")) {
            if (!pName.equals("")) {
                res = res + "?" + parentLabel.replace(" ", "_").replaceAll("[\\(|\\)]", "") +
                        " " + pName + " ?v_" + root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", "") +
                        ".\n";
                res = res + "FILTER regex( ?v_" + root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", "") +
                        ", "+ root.getChild(1).getText() +", \"i\").\n";
            }
        } else if (root.getText().equals("COMPRNG")) {
            if (!pName.equals("")) {
                res = res + "?" + parentLabel.replace(" ", "_").replaceAll("[\\(|\\)]", "") +
                        " " + pName + " ?v_" + root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", "") +
                        ".\n";
                res = res + "FILTER ( ?v_" + root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", "") +
                        " >= " + root.getChild(1).getChild(0).getText() + " && ?v_" +
                        root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", "") + " <= " +
                        root.getChild(1).getChild(1).getText() +").\n";
            }
        } else if (root.getText().equals("COMPL")) {
            if (!pName.equals("")) {
                res = res + "?" + parentLabel.replace(" ", "_").replaceAll("[\\(|\\)]", "") +
                        " " + pName + " ?v_" + root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", "") +
                        ".\n";
                res = res + "FILTER ( ?v_" + root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", "") +
                        " < " + root.getChild(1).getText() + ").\n";
            }
        } else if (root.getText().equals("COMPG")) {
            if (!pName.equals("")) {
                res = res + "?" + parentLabel.replace(" ", "_").replaceAll("[\\(|\\)]", "") + " " + pName +
                        " ?v_" + root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", "") + ".\n";
                res = res + "FILTER ( ?v_" + root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", "") +
                        " > " + root.getChild(1).getText() + ").\n";
            }
        } else if (root.getText().equals("COMPLE")) {
            if (!pName.equals("")) {
                res = res + "?" + parentLabel.replace(" ", "_").replaceAll("[\\(|\\)]", "") + " " + pName +
                        " ?v_" + root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", "") + ".\n";
                res = res + "FILTER ( ?v_" + root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", "") +
                        " <= " + root.getChild(1).getText() + ").\n";
            }
        } else if (root.getText().equals("COMPGE")) {
            if (!pName.equals("")) {
                res = res + "?" + parentLabel.replace(" ", "_").replaceAll("[\\(|\\)]", "") + " " + pName +
                        " ?v_" + root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", "") +
                        ".\n";
                res = res + "FILTER ( ?v_" + root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", "") +
                        " >= " + root.getChild(1).getText() + ").\n";
            }
        } else {
            if (!pName.equals("")) {
                res = res + "?" + parentLabel.replace(" ", "_").replaceAll("[\\(|\\)]", "") + " " + pName + " ?" +
                        root.getText().replace(" ", "_").replaceAll("[\\(|\\)]", "") + ".\n";
            }
        }
        return res;
    }

    private String getVarList(CommonTree root, String parent) {
        String res = "";

        if (root.getChild(0).getText().equals("MODTO")) {
            return "?" + parent.replace(" ", "_").replaceAll("[\\(|\\)]", "") + " ?prop ?val";
        }

        List<CommonTree> child = root.getChildren();
        if (child != null) {
            for (CommonTree t : child) {
                res = res + "?" + t.getText().replace(" ", "_").replaceAll("[\\(|\\)]", "") + " ";
            }
        }
        return res.trim();
    }

    private String getPropertyType(String propertyName, String className) {
        if (assertRelation(className, propertyName)) {
            SemanticClass sc = vocabulary.getSemanticClass(lex.getLexicon(lang).getWord(className, true).getTag().getURI());
            if (sc != null) {
                SemanticProperty sp = vocabulary.getSemanticProperty(lex.getLexicon(lang).getWord(propertyName, false).getTag().getURI());
                if (sp != null) {
                    return sp.getPrefix() + ":" + sp.getName();
                } else {
                    eLog += "La clase " + className + " no tiene una propiedad llamada " +
                            propertyName;
                    errCode = 3;
                }
            }
        }
        return "";
    }

    public boolean assertRelation (String className, String propertyName) {
        boolean ret = false;
        SemanticClass sc = vocabulary.getSemanticClass(lex.getLexicon(lang).getWord(className, true).getTag().getURI());
        if (sc != null) {
            if (lex.getLexicon(lang).getWord(propertyName, false) != null) {
                SemanticProperty sp = vocabulary.getSemanticProperty(lex.getLexicon(lang).getWord(propertyName, false).getTag().getURI());
                if (sp != null && sc.getProperty(sp.getName()) != null) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    public SemanticClass getPropertyRangeClass(String propertyName, String className) {
        if (assertRelation(className, propertyName)) {
            SemanticClass sc = vocabulary.getSemanticClass(lex.getLexicon(lang).getWord(className, true).getTag().getURI());
            if (sc != null) {
                SemanticProperty sp = vocabulary.getSemanticProperty(lex.getLexicon(lang).getWord(propertyName, false).getTag().getURI());
                if (sp != null) {
                    SemanticClass rg = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(sp.getRangeClass().getURI());
                    if (rg != null) {
                        return rg;
                    }
                } else {
                    eLog += "La clase " + className + " no tiene una propiedad llamada " +
                        propertyName + "\n";
                    errCode = 3;
                }
            }
        }
        return null;
    }

    public String getPropertyRangeType(String propertyName, String className) {
        if (assertRelation(className, propertyName)) {
            SemanticClass sc = getPropertyRangeClass(propertyName, className);
            return sc.getPrefix() + ":" + sc.getName();
        }
        return "";
    }

    public Lexer getLocaleLexer(String langCode, ANTLRStringStream input) {
        if (langCode.equals("es")) {
            return new SpanishLexer(input);
        } else if (langCode.equals("en")) {
            return new EnglishLexer(input);
        }
        return null;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrors() {
        return eLog;
    }

    public boolean isEmptyQuery() {
        return emptyQuery;
    }
}
