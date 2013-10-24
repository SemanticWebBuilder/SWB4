/*
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
 */
package org.semanticwb.nlp.translation;

import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.nlp.analysis.ComplexParser;
import java.util.List;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.tree.CommonTree;
import org.semanticwb.SWBPlatform;
import org.semanticwb.nlp.SWBDictionary;
import org.semanticwb.nlp.SWBLocaleLexicon;
import org.semanticwb.nlp.Tag;
import org.semanticwb.nlp.analysis.EnglishLexer;
import org.semanticwb.nlp.analysis.SpanishLexer;
import org.semanticwb.nlp.spell.SWBSpellChecker;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;

/**
 * A controlled language to SparQl query translator. Uses the Abstract Sintax Tree
 * (AST) of a sentence obtained with the antlr generated parser ({@link ComplexParser})
 * in order to build a structured SparQl query.
 * <p>
 * Un traductor de consultas en lenguaje natural controlado a consultas SparQl. Usa el árbol
 * de sintaxis abstracta (AST) de una oración obtenido con el analizador generado
 * por antlr ({@link ComplexParser}) para construir una consulta SparQl
 * estructurada.
 *
 * @author Hasdai Pacheco {ebenezer.sanchez@infotec.com.mx}
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
    private String lang;
    private static final String  SPARQL_SELECT_DISTINCT = "SELECT DISTINCT";
    private static final String  SPARQL_ORDER = "ORDER BY";
    private static final String  AST_LIMIT_TAG = "LIMIT";
    private static final String  AST_ORDER_TAG = "ORDER";
    private static final String  AST_OFFSET_TAG = "OFFSET";
    private static final String  AST_DEFINE_TAG = "DEFINE";
    private static final String  AST_PRECON_TAG = "DEFINE";
    private static final String  AST_PREDE_TAG = "PREDE";
    private static final String  AST_ASIGNATION_TAG = "ASIGN";
    private static final String  AST_GREATERTHAN_TAG = "COMPG";
    private static final String  AST_LESSTHAN_TAG = "COMPL";
    private static final String  AST_LESSTHANEQUALS_TAG = "COMPLE";
    private static final String  AST_GREATERTHANEQUALS_TAG = "COMPGE";
    private static final String  AST_LIKE_TAG = "COMPAS";
    private static final String  AST_BETWEEN_TAG = "COMPRNG";
    private static final String  AST_ALLFROM_TAG = "MODTO";

    /**
     * Constructor.
     * Creates a new instance of a {@link SWBSparqlTranslator} with the given 
     * {@link SWBDictionary}.
     * <p>
     * Crea una nueva instancia de un {@link SWBSparqlTranslator} con el 
     * {@link SWBDictionary} especificado.
     * @param dict {@link SWBDictionary} con el vocabulario a utilizar.
     */
    public SWBSparqlTranslator(SWBDictionary dict) {
        lex = dict;
        //TODO: agregar soporte multi-lenguajes
        lang = "es";
        speller = new SWBSpellChecker(lex.getSpellDictPath());
    }

    /**
     * Translates a controlled language query to a SPARQL query.
     * <p>
     * Traduce una sentencia en lenguaje natural controlado a una consulta en SPARQL.
     * @param sent Sentence to translate. <p> Sentencia a traducir.
     * @param sRequired Wheter the main subject is required inthe query. 
     * <p> Indica si el sujeto principal se requiere en la consulta.
     * @return Translated SPARQL query. <p> Consulta SPARQL traducida.
     */
    public String translateSentence(String sent, boolean sRequired) {
        StringBuilder res = new StringBuilder();
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
                res.append(processSelectQuery(sTree, parser.hasPrecon(), parser.hasPrede(), sRequired));
                //System.out.println(res.toString());
            } else {
                errCode = 2;
                eLog += "La consulta no está bien escrita.\n";
            }
        } catch (org.antlr.runtime.RecognitionException ex) {
            errCode = 2;
            eLog += "No se ha podido traducir la consulta.\n";
        }
        return res.toString();
    }

    /**
     * Preprocess the names of nodes to strip square brackets generated in the AST.
     * <p>
     * Preprocesa los nombres de los nodos generados en el AST para eliminar los corchetes.
     * @param tree AST.
     */
    private void fixNames(CommonTree tree) {
        //If the node is the root of a NAME
        if (nodeLabels.indexOf(tree.getText()) == -1) {
            //Set the current node's text to be the child text
            tree.token.setText(tree.getText().replaceAll("[\\[|\\]]", ""));
        }

        //Process all children of current node
        List<CommonTree> child = (List<CommonTree>) tree.getChildren();
        if (child != null) {
            for (CommonTree t : child) {
                fixNames(t);
            }
        }
    }

    /**
     * Processes a SELECT SPARQL query. <p> Procesa una consulta SELECT en SPARQL. 
     * @param root Root node of AST. <p> Nodo raíz del AST.
     * @param hasPrecon Wheter the user queries for an object with matching properties.
     * <p> Indica si el usuario requiere un objeto con ciertas propiedades definidas.
     * @param hasPrede Wheter the user requires queries for properties of an object.
     * <p> Indica si el usuario requiere ciertas propiedades de un objeto.
     * @param subjectRequired Wheter the main subject is required inthe query. 
     * <p> Indica si el sujeto principal se requiere en la consulta.
     * @return SPARQL SELECT query. <p> Consulta SELECT de SPARQL.
     */
    private String processSelectQuery(CommonTree root, boolean hasPrecon, boolean hasPrede, boolean subjectRequired) {
        StringBuilder res = new StringBuilder();
        String subject = "";
        String limitoff = "";
        String order = "";
        StringBuilder varList = new StringBuilder();
        StringBuilder patterns = new StringBuilder();

        List<CommonTree> child = (List<CommonTree>) root.getChildren();
        if (child != null) {
            res.append(SPARQL_SELECT_DISTINCT+" ");
            for (CommonTree t : child) {
                if (t.getText().equals(AST_LIMIT_TAG)) {
                    limitoff = limitoff + " "+ AST_LIMIT_TAG + " " + t.getChild(0).getText() + "\n";
                } else if (t.getText().equals(AST_ORDER_TAG)) {
                    order = order + " " + SPARQL_ORDER + " ";
                    for (CommonTree oit : (List<CommonTree>) t.getChildren()) {
                        order = order + "?" + oit.getText().replace(" ", "_").replaceAll("[\\(|\\)]", "") + " ";
                    }
                    order = order.trim() + "\n";
                } else if (t.getText().equals(AST_OFFSET_TAG)) {
                    limitoff = limitoff + " " + AST_OFFSET_TAG + " " + t.getChild(0).getText() + "\n";
                } else if (t.getText().equals(AST_DEFINE_TAG)) {
                    subject = t.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", "");
                    res.append(" ?description \nWHERE \n{\n");
                    String etype = "";
                    if (lex.getLexicon(lang).getWord(subject, true) != null) {
                        etype = lex.getLexicon(lang).getWord(subject, true).getTags().get(0).getTagInfoParam(SWBLocaleLexicon.PARAM_ID);
                    }
                    patterns.append(etype);
                    patterns.append(" rdfs:comment ?description.\n");
                } else {
                    subject = t.getText().replace(" ", "_").replaceAll("[\\(|\\)]", "");
                    if (hasPrede) {
                        if (hasPrecon) {
                            varList.append(getVarList((CommonTree) t.getChild(1), t.getText()));
                        } else {
                            varList.append(getVarList((CommonTree) t.getChild(0), t.getText()));
                        }
                    } else {
                        varList.append("?").append(t.getText().replace(" ", "_").replaceAll("[\\(|\\)]", ""));
                    }
                    if (varList.indexOf(subject) > 0 && subjectRequired) {
                        res.append("?");
                        res.append(subject);
                        res.append(" ");
                    }
                    res.append(varList);
                    res.append("\nWHERE \n{\n");
                    String etype = "";
                    if (lex.getLexicon(lang).getWord(t.getText(), true) != null) {
                        etype = lex.getLexicon(lang).getWord(t.getText(), true).getTags().get(0).getTagInfoParam(SWBLocaleLexicon.PARAM_ID);
                    }
                    //String etype = lex.getObjWordTag(t.getText()).getType();
                    if (!etype.equals("")) {
                        patterns.append("?").append(t.getText().replace(" ", "_").replaceAll("[\\(|\\)]", ""));
                        patterns.append(" rdf:type ").append(etype).append(".\n");
                        patterns.append(parseAST(t));
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
        res.append(patterns).append("}").append(order).append(limitoff);
        return res.toString(); 
    }

    /**
     * Parses the AST and processes each node to build the final SPARQL query.
     * <p>
     * Parsea el AST y procesa cada nodo para construir la consulta SPARQL.
     * @param root AST root node.<p>Nodo raíz del AST.
     * @return 
     */
    private String parseAST(CommonTree root) {
        String res = "";
        List<CommonTree> child = (List<CommonTree>) root.getChildren();

        if (child != null) {
            for (CommonTree t : child) {
                res += processNode(t, root.getText(), root.getText());
            }
        }
        return res;
    }

    /**
     * Processes a node from the AST.
     * <p>
     * Procesa un nodo del AST.
     * @param root Root node for recursive processing.<p>Nodo raíz para procesamiento recursivo.
     * @param parent Parent node.<p>Nodo padre
     * @param parentLabel Parent node label.<p>Etiqueta del nodo padre.
     * @return 
     */
    private String processNode(CommonTree root, String parent, String parentLabel) {
        StringBuilder res = new StringBuilder();
        List<CommonTree> child = (List<CommonTree>) root.getChildren();
        String nname = root.getText();

        //System.out.println(nname + "[ " + parent + ", " + parentLabel + "]");
        if (nname.equals(AST_PRECON_TAG)) {
            //Procesar los hijos con el padre del actual
            if (child != null) {
                for (CommonTree t : child) {
                    res.append(processNode(t, parent, parentLabel));
                }
            }
        } else if (nname.equals(AST_ASIGNATION_TAG) || nname.equals(AST_GREATERTHAN_TAG) || nname.equals(AST_LESSTHAN_TAG) ||
                nname.equals(AST_LESSTHANEQUALS_TAG) || nname.equals(AST_GREATERTHANEQUALS_TAG) || nname.equals(AST_LIKE_TAG) ||
                nname.equals(AST_BETWEEN_TAG)) {
            res.append(processStatement(root, parent, parentLabel));
        } else if (nname.equals(AST_PREDE_TAG)) {
            if (!root.getChild(0).getText().equals(AST_ALLFROM_TAG)) {
                if (child != null) {
                    for (CommonTree t : child) {
                        String cname = t.getText();
                        res.append("?").append(parent.replace(" ", "_").replaceAll("[\\(|\\)]", "")).append(" ").append(getPropertyName(cname, parent));
                        res.append(" ?").append(cname.replace(" ", "_").replaceAll("[\\(|\\)]", "")).append(".\n");
                    }
                }
            } else {
                res.append("?").append(parent.replace(" ", "_").replaceAll("[\\(|\\)]", "")).append(" ?prop ?val.\n");
            }
        } else {
            if (child != null) {
                String rangeClassName = getPropertyRangePrefixedName(nname, parent);
                if (!rangeClassName.equals("")) {
                    String scl = getPropertyRangeClassName(nname, parent);
                    res.append("?").append(nname.replace(" ", "_").replaceAll("[\\(|\\)]", "")).append(" rdf:type ").append(rangeClassName).append(".\n");
                    String pName = getPropertyName(nname, parent);
                    if (!pName.equals("")) {
                        res.append("?").append(parent.replace(" ", "_").replaceAll("[\\(|\\)]", "")).append(" ").append(pName).append(" ?");
                        res.append(nname.replace(" ", "_").replaceAll("[\\(|\\)]", "")).append(".\n");
                    }
                    if (!scl.equals("")) {
                        String cName = scl;
                        for (CommonTree t : child) {
                            res.append(processNode(t, cName, nname));
                        }
                    }
                }
            } else {
                String pName = getPropertyName(nname, parent);
                if (!pName.equals("")) {
                    if (!parentLabel.equals("")) {
                        res.append("?").append(parentLabel.replace(" ", "_").replaceAll("[\\(|\\)]", "")).append(" ");
                        res.append(pName).append(" ?").append(nname.replace(" ", "_").replaceAll("[\\(|\\)]", "")).append(".\n");
                    } else {
                        res.append("?").append(parent.replace(" ", "_").replaceAll("[\\(|\\)]", "")).append(" ").append(pName);
                        res.append(" ?").append(nname.replace(" ", "_").replaceAll("[\\(|\\)]", "")).append(".\n");
                    }
                }
            }
        }
        return res.toString();
    }

    /**
     * Processes a statement node.<p>Procesa un nodo de sentencia.
     * @param root Root node for recursive processing.<p>Nodo raíz para procesamiento recursivo.
     * @param parent Parent node.<p>Nodo padre
     * @param parentLabel Parent node label.<p>Etiqueta del nodo padre.
     * @return SPARQL query fragment for the statement.<p>Fragmento de consulta SPARQL para el nodo.
     */
    private String processStatement(CommonTree root, String parent, String parentLabel) {
        StringBuilder res = new StringBuilder();
        String pName = getPropertyName(root.getChild(0).getText(), parent);
        res.append("?").append(parentLabel.replace(" ", "_").replaceAll("[\\(|\\)]", "")).append(" ").append(pName);
        
        //System.out.println("verificando " + root.getChild(0).getText() + " de " + parent + " con etiqueta " + parentLabel);
        if (root.getText().equals(AST_ASIGNATION_TAG)) {
            if (!pName.equals("")) {
                res.append(" ").append(root.getChild(1).getText()).append(".\n");
            }
        } else if (root.getText().equals(AST_LIKE_TAG)) {
            if (!pName.equals("")) {
                res.append(" ?v_").append(root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", "")).append(".\n");
                res.append("FILTER regex( ?v_").append(root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", ""));
                res.append(", ").append(root.getChild(1).getText()).append(", \"i\").\n");
            }
        } else if (root.getText().equals(AST_BETWEEN_TAG)) {
            if (!pName.equals("")) {
                res.append(" ?v_").append(root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", "")).append(".\n");
                res.append("FILTER ( ?v_").append(root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", ""));
                res.append(" >= ").append(root.getChild(1).getChild(0).getText()).append(" && ?v_").append(root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", ""));
                res.append(" <= ").append(root.getChild(1).getChild(1).getText()).append(").\n");
            }
        } else if (root.getText().equals(AST_LESSTHAN_TAG)) {
            if (!pName.equals("")) {
                res.append(" ?v_").append(root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", "")).append(".\n");
                res.append("FILTER ( ?v_").append(root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", ""));
                res.append(" < ").append(root.getChild(1).getText()).append(").\n");
            }
        } else if (root.getText().equals(AST_GREATERTHAN_TAG)) {
            if (!pName.equals("")) {
                res.append(" ?v_").append(root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", "")).append(".\n");
                res.append("FILTER ( ?v_").append(root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", ""));
                res.append(" > ").append(root.getChild(1).getText()).append(").\n");
            }
        } else if (root.getText().equals(AST_LESSTHANEQUALS_TAG)) {
            if (!pName.equals("")) {
                res.append(" ?v_").append(root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", "")).append(".\n");
                res.append("FILTER ( ?v_").append(root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", ""));
                res.append(" <= ").append(root.getChild(1).getText()).append(").\n");
            }
        } else if (root.getText().equals(AST_GREATERTHANEQUALS_TAG)) {
            if (!pName.equals("")) {
                res.append(" ?v_").append(root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", "")).append(".\n");
                res.append("FILTER ( ?v_").append(root.getChild(0).getText().replace(" ", "_").replaceAll("[\\(|\\)]", ""));
                res.append(" >= ").append(root.getChild(1).getText()).append(").\n");
            }
        } else {
            if (!pName.equals("")) {
                res.append(" ?").append(root.getText().replace(" ", "_").replaceAll("[\\(|\\)]", "")).append(".\n");
            }
        }
        return res.toString();
    }

    /**
     * Gets the list of required property variables in the SPARQL query from the AST.
     * <p>
     * Obtiene del AST la lista de variables para las propiedades requeridas en la consulta SPARQL.
     * @param root Root node for variables.<p>Nodo raíz que contiene las variables.
     * @param parent Parent node name (subject in SPARQL query).<p>Nombre del nodo padre (el sujeto en la consulta SPARQL).
     * @return the property var list for the SPARQL query, e.g. '?title ?comment'.<p>
     * Lista de variables para la consulta SPARQL. p.e. '?title ?comment'.
     */
    private String getVarList(CommonTree root, String parent) {
        StringBuilder res = new StringBuilder();
        if (root.getChild(0).getText().equals(AST_ALLFROM_TAG)) { //'todo de' query
            res.append("?");
            res.append(parent.replace(" ", "_").replaceAll("[\\(|\\)]", ""));
            res.append(" ?prop ?val");
        } else {
            List<CommonTree> child = (List<CommonTree>) root.getChildren();
            if (child != null) {
                for (CommonTree t : child) {
                    res.append("?").append(res.append(t.getText().replace(" ", "_").replaceAll("[\\(|\\)]", "")));
                    res.append(" ");
                }
            }
        }
        return res.toString().trim();
    }

    /**
     * Gets the name of a property in the form prefix:propertyName for using it 
     * in the SPARQL query. <p>
     * Obtiene el nombre de una propiedad en la forma prefijo:NombrePropiedad 
     * para usarse en los patrones de la consulta SPARQL.
     * @param propertyName Name of the property in the query. <p> Nombre de la
     * propiedad en la consulta.
     * @param className Class name in the query. <p> Nombre de la clase en en la consulta.
     * @return Name of the property of the specified class in the form prefix:propertyName.
     * <p>
     * Nombre de la propiedad de la clase especificada en la forma prefijo:NombrePropiedad.
     */
    public String getPropertyName(String propertyName, String className) {
        SemanticVocabulary vocabulary = SWBPlatform.getSemanticMgr().getVocabulary();
        String ret = "";
        if (assertRelation(className, propertyName)) {
            SemanticClass sc = vocabulary.getSemanticClass(lex.getLexicon(lang).getWord(className, true).getTags().get(0).getTagInfoParam(SWBLocaleLexicon.PARAM_URI));
            if (sc != null) {
                SemanticProperty sp = vocabulary.getSemanticProperty(lex.getLexicon(lang).getWord(propertyName, false).getSelectedTag().getTagInfoParam(SWBLocaleLexicon.PARAM_URI));
                if (sp != null) {
                    ret = sp.getPrefix() + ":" + sp.getName();
                } else {
                    eLog += "La clase " + className + " no tiene una propiedad llamada " +
                            propertyName;
                    errCode = 3;
                }
            }
        }
        return ret;
    }

    /**
     * Checks wheter the specified className has the specified propertyName.
     * <p>
     * Verifica que la clase className contenga la propiedad propertyName.
     * @param className Class Name.<p>Nombre de la clase.
     * @param propertyName Property name. <p> Nombre de la propiedad.
     * @return true if the class has the specified property, false otherwise. <p>
     * true si la propiedad pertenece a la clase, falso de lo contrario.
     */
    public boolean assertRelation (String className, String propertyName) {
        SemanticVocabulary vocabulary = SWBPlatform.getSemanticMgr().getVocabulary();
        //System.out.println("--Asserting " + propertyName + " from " + className);
        boolean ret = false;
        SemanticClass sc = vocabulary.getSemanticClass(lex.getLexicon(lang).getWord(className, true).getTags().get(0).getTagInfoParam(SWBLocaleLexicon.PARAM_URI));

        if (sc != null) {
            if (lex.getLexicon(lang).getWord(propertyName, false) != null) {
                //Check property for all posible tags
                ArrayList<Tag> tags = lex.getLexicon(lang).getWord(propertyName, false).getTags();
                Iterator<Tag> tit = tags.iterator();

                while(tit.hasNext() && !ret) {
                    Tag t = tit.next();

                    SemanticProperty sp = vocabulary.getSemanticProperty(t.getTagInfoParam(SWBLocaleLexicon.PARAM_URI));
                    if (sp != null && sc.getProperty(sp.getName()) != null) {
                        ret = true;
                        lex.getLexicon(lang).getWord(propertyName, false).setSelectedTag(t);
                    }
                }
            }
        }
        return ret;
    }

//    public SemanticClass getPropertyRangeClass(String propertyName, String className) {
//        SemanticVocabulary vocabulary = SWBPlatform.getSemanticMgr().getVocabulary();
//        if (assertRelation(className, propertyName)) {
//            SemanticClass sc = vocabulary.getSemanticClass(lex.getLexicon(lang).getWord(className, true).getTags().get(0).getTagInfoParam(SWBLocaleLexicon.PARAM_URI));
//            if (sc != null) {
//                SemanticProperty sp = vocabulary.getSemanticProperty(lex.getLexicon(lang).getWord(propertyName, false).getSelectedTag().getTagInfoParam(SWBLocaleLexicon.PARAM_URI));
//                if (sp != null) {
//                    SemanticClass rg = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(sp.getRangeClass().getURI());
//                    if (rg != null) {
//                        return rg;
//                    }
//                } else {
//                    eLog += "La clase " + className + " no tiene una propiedad llamada " +
//                        propertyName + "\n";
//                    errCode = 3;
//                }
//            }
//        }
//        return null;
//    }
    
    /**
     * Gets the name of the range class of a property.
     * <p>
     * Obtiene el nombre de lq clase rango de una propiedad.
     * @param propertyName Name of the property.<p>Nombre de la propiedad.
     * @param className Name of the class.<p>Nombre de la clase.
     * @return Name of the range class for the property.<p>Nombre de la clase rango de la propiedad.
     */
    public String getPropertyRangeClassName(String propertyName, String className) {
        SemanticVocabulary vocabulary = SWBPlatform.getSemanticMgr().getVocabulary();
        String ret = "";
        if (assertRelation(className, propertyName)) {
            SemanticClass sc = vocabulary.getSemanticClass(lex.getLexicon(lang).getWord(className, true).getTags().get(0).getTagInfoParam(SWBLocaleLexicon.PARAM_URI));
            if (sc != null) {
                SemanticProperty sp = vocabulary.getSemanticProperty(lex.getLexicon(lang).getWord(propertyName, false).getSelectedTag().getTagInfoParam(SWBLocaleLexicon.PARAM_URI));
                if (sp != null) {
                    SemanticClass rg = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(sp.getRangeClass().getURI());
                    if (rg != null) {
                        ret = rg.getDisplayName(lex.getLexicon(lang).getLanguageCode());
                    }
                } else {
                    eLog += "La clase " + className + " no tiene una propiedad llamada " +
                        propertyName + "\n";
                    errCode = 3;
                }
            }
        }
        return ret;
    }

    /**
     * Gets the name of the range class of a property in the form prefix:ClassName.
     * <p>
     * Obtiene el nombre de la clase rango de una propiedad en la forma prefijo:NombreClase.
     * @param propertyName Property name.<p>Nombre de la propiedad.
     * @param className Class name.<p>Nombre de la clase.
     * @return name of the range class of a property in the form prefix:ClassName.<p>
     * nombre de la clase rango de una propiedad en la forma prefijo:NombreClase
     */
    public String getPropertyRangePrefixedName(String propertyName, String className) {
        String ret = "";
        if (assertRelation(className, propertyName)) {
            SemanticVocabulary vocabulary = SWBPlatform.getSemanticMgr().getVocabulary();
            SemanticClass sc = vocabulary.getSemanticClass(lex.getLexicon(lang).getWord(className, true).getTags().get(0).getTagInfoParam(SWBLocaleLexicon.PARAM_URI));
            if (sc != null) {
                SemanticProperty sp = vocabulary.getSemanticProperty(lex.getLexicon(lang).getWord(propertyName, false).getSelectedTag().getTagInfoParam(SWBLocaleLexicon.PARAM_URI));
                if (sp != null) {
                    SemanticClass rg = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(sp.getRangeClass().getURI());
                    if (rg != null) {
                        ret = rg.getPrefix() + ":" + rg.getName();
                    }
                } else {
                    eLog += "La clase " + className + " no tiene una propiedad llamada " +
                        propertyName + "\n";
                    errCode = 3;
                }
            }
        }
        return ret;
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
