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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.tree.CommonTree;
import org.semanticwb.SWBPlatform;
import org.semanticwb.nlp.SWBLexicon;
import org.semanticwb.nlp.analysis.EnglishLexer;
import org.semanticwb.nlp.analysis.SpanishLexer;
import org.semanticwb.nlp.spell.SWBSpellChecker;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;

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
    private SWBLexicon lex;        //Dictionary
    private CommonTokenStream tokens;   //TokenStream for parsing
    private ANTLRStringStream input;    //StringStream to parse
    private String nodeLabels = "SELECT|PRECON|PREDE|ASIGN|COMPL|COMPG|COMPLE|COMPGE|OFFSET|LIMIT|ORDER|COMPAS|COMPRNG|INTERVAL";
    private String eLog = "";   //Error log
    private int errCode = 0;    //Last error code
    private SWBSpellChecker speller = null;
    private boolean emptyQuery = false;
    private String subject = "";

    /**
     * Creates a new instance of {@link SWBSparqlTranslator} with the given
     * {@link SWBLexicon}.
     * <p>
     * Crea un {@link SWBSparqlTranslator} con el {@link SWBLexicon} especificado.
     *
     * @param dict  {@link SWBLexicon} for the new translator. Diccionario para
     *              el traductor.
     */
    public SWBSparqlTranslator(SWBLexicon dict) {
        lex = dict;
        speller = new SWBSpellChecker(dict.getSpellDictPath());
    }

    /**
     * Wheter the query has graph patterns.
     * <p>
     * Verifica si la consulta SparQl tiene patrones de grafo.
     *
     * @return  {@code true} if query has patterns, {@code false} otherwise.
     *          {@code true} si la consulta tiene al menos un patrón,
     *          {@code false} en caso contrario.
     */
    public boolean isEmptyQuery() {
        return emptyQuery;
    }

    /**
     * Gets the main subject of the query, that is, the object that contains
     * properties.
     * <p>
     * Extrae el sujeto principal de la consulta, es decir, el objeto del cual
     * se solicitan propiedades.
     *
     * @return main sobject in the sentence. Sujeto principal de la oración.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Try to get the range class (as a {@link SemanticClass}) of an ObjectType
     * property.
     * <p>
     * Obtiene el rango (como clase semántica) de una propiedad semántica de
     * tipo ObjectType.
     * 
     * @param propertyName  name of the property to check. Nombre de la propiedad
     *                      a verificar.
     * @param className     name of the {@link SemanticClass} with the specified
     *                      property. Nombre de la clase semántica que contiene
     *                      la propiedad.
     * @return              a {@link SemanticClass} which is the range class of
     *                      the property, {@code null} otherwise. Un objeto
     *                      {@link SemanticClass} que es el rango de la propiedad
     *                      o {@code null} si la propiedad no tiene clase rango.
     */
    public SemanticClass assertPropertyRangeClass(String propertyName, String className) {
        String name = lex.getObjWordTag(className).getObjId();
        boolean found = false;

        SemanticClass sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(name);
        if (sc != null) {
            Iterator<SemanticProperty> sit = sc.listProperties();
            while (sit.hasNext() && !found) {
                SemanticProperty sp = sit.next();

                if (lex.getSnowballForm(sp.getDisplayName(lex.getLanguage())).equalsIgnoreCase(lex.getSnowballForm(propertyName))) {
                    found = true;
                    if (sp.isObjectProperty()) {
                        SemanticClass rg = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(sp.getRangeClass().getURI());
                        if (rg != null) {
                            return rg;
                        }
                    } else {
                        errCode = 3;
                        eLog += "La propiedad " + sp.getDisplayName(lex.getLanguage()) + "no es de tipo Objeto.\n";
                    }
                }
            }

            if (!found) {
                eLog += "La clase " + sc.getDisplayName(lex.getLanguage()) + " no tiene una propiedad llamada ";
                eLog += propertyName + "\n";
                errCode = 3;
            }
        }
        return null;
    }

    /**
     * Gets the type (prefix + name) of the range class for an ObjectType property.
     * <p>
     * Obtiene el tipo (prefijo + nombre) de la clase rango para una propiedad
     * de tipo ObjectType.
     * 
     * @param propertyName name of the property to assert.
     * @param className name of the SemanticClass with the specified property.
     * @return prefix + name of the property, empty String if propertyName is
     *         not a SemanticProperty of className.
     */
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
                if (lex.getSnowballForm(sp.getDisplayName(lex.getLanguage())).equalsIgnoreCase(lex.getSnowballForm(propertyName))) {
                    found = true;
                    if (sp.isObjectProperty()) {
                        StringBuffer bf = new StringBuffer();
                        bf.append(sp.getRangeClass());

                        SemanticClass rg = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(bf.toString());
                        if (rg != null) {
                            res = res + lex.getObjWordTag(rg.getDisplayName(lex.getLanguage())).getType();
                        }
                    } else {
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
        return res;
    }

    /**
     * Validates if <b>propertyName</b> is a property of <b>className</b> in the
     * {@link SemanticVocabulary}.
     * <p>
     * Valida si <b>propertyName</b> es una propiedad de <b>className</b> dentro del
     * vocabulario semántico.
     * 
     * @param propertyName  name of the property to check. Nombre de la propiedad
     *                      a verificar.
     * @param className     name of the class which contains the propertyName.
     *                      Nombre de la clase que debería contener la propiedad.
     * @return              the RDF type of the property if it's a propery of 
     *                      <b>className</b>, empty string otherwise. Tipo RDF
     *                      de la propiedad siempre que ésta pertenezca a la
     *                      clase especificada, en otro caso devuelve una cadena
     *                      vacía.
     */
    private String assertPropertyType(String propertyName, String className) {
        String res = "";
        String name = lex.getObjWordTag(className).getObjId();
        boolean found = false;
        SemanticProperty sp = null;
        Iterator<SemanticProperty> sit;
        //System.out.println(">verificando " + propertyName + " de " + className);
        SemanticClass sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(name);
        if (sc != null) {
            sit = sc.listProperties();
            while (sit.hasNext() && !found) {
                sp = sit.next();
                if (lex.getSnowballForm(sp.getDisplayName(lex.getLanguage())).equalsIgnoreCase(lex.getSnowballForm(propertyName))) {
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
     * Gets a suggested query string correcting spelling errors.
     * <p>
     * Sugiere una consulta corrigiendo los errores ortográficos.
     *
     * @param sent      original mispelled query. Consulta original con errores.
     * 
     * @return Query    query string without spelling errors or the input query
     *                  if there are not suggestions. Consulta sin errores
     *                  ortográficos o la consulta de entrada en caso de no
     *                  haber sugerencias.
     */
    public String didYouMean(String sent) {
        String res = "";
        String[] stopWords = {"de", "of", "con", "with", "=", "<", ">",
            "<=", ">=", ",", "like", "como", "true", "false", "+"};

        List<String> sw = Arrays.asList(stopWords);

        ANTLRStringStream sinput = new ANTLRStringStream(sent);
        Lexer stokenizer = getLocaleLexer(lex.getLanguage(), sinput);
        CommonTokenStream stokens = new CommonTokenStream(stokenizer);

        if (speller == null) {
            return null;
        }

        org.antlr.runtime.Token tk;
        while ((tk = stokens.LT(1)) != org.antlr.runtime.Token.EOF_TOKEN) {
            String tkText = tk.getText().trim();
            if (!sw.contains(tkText) && (!tkText.startsWith("\"") && !tkText.endsWith("\""))) {
                boolean compound = false;

                if (tkText.startsWith("[") && tkText.endsWith("]")) {
                    tkText.replace("[", "").replace("]", "");
                    compound = true;
                }

                String[] suggestions = speller.suggestSimilar(tkText);
                if (suggestions == null || suggestions.length == 0) {
                    suggestions = speller.suggestSimilar(tkText);
                    if (suggestions == null || suggestions.length == 0) {
                        res = res + (compound ? "[" + tkText + "]" : tkText) + " ";
                    } else if (suggestions.length > 0) {
                        res = res + (compound ? "[" + suggestions[0] + "]" : suggestions[0]) + " ";
                    }
                } else if (suggestions.length > 0) {
                    res = res + (compound ? "[" + suggestions[0] + "]" : suggestions[0]) + " ";
                }
            } else {
                res = res + tkText + " ";
            }
            stokens.consume();
        }
        return res.trim();
    }

    /**
     * Fixes node names in the AST. Removes brackets in 'NAME' nodes.
     * <p>
     * Corrige los nombres de los nodos en el AST. Elimina los corchetes en los
     * nodos 'NAME'.
     *
     * @param tree AST to fix. AST a corregir.
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
            for (CommonTree t : child) {
                fixNames(t);
            }
        }
    }

    /**
     * Gets the code of the last error occured.
     * <p>
     * Obtiene el código del último error ocurrido.
     */
    public int getErrCode() {
        return errCode;
    }

    /**
     * Gets the error log generated in a parsing process.
     * <p>
     * Obtiene la bitácora de errores generada en el proceso de análisis.
     */
    public String getErrors() {
        return eLog;
    }

    /**
     * Transforms a PREDE node into a SparQL query fragment. If PREDE's first
     * child is a MODTO node, an '*' is added to the varList of the SELECT clause
     * for the query. Otherwise, varList consists of the names of all child
     * nodes of PREDE.
     * <p>
     * Transforma un nodo PREDE en un fragmento de consulta SparQl. Si el primer
     * hijo del nodo PREDE es un nodo MODTO, se agrega un '*' a la lista de
     * variables de la consulta en la clausula SELECT. En otro caso, la lista de
     * variables se forma de los nombres de todos los hijos del nodo PREDE.
     *
     * @param root      the PREDE node. El nodo PREDE.
     * @param parent    name of the parent object of PREDE node. Nombre del nodo
     *                  padre del nodo PREDE.
     *
     * @return          a SparQL query fragment, specifically a 'varList' for the
     *                  SelectQuery production of the SparQL query language
     *                  grammar. Un fragmento de consulta SparQl, específicamente
     *                  el 'varList' de variables resultado de la regla de producción
     *                  'SelectQuery' en la gramática de SparQl.
     */
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

    /**
     * Transforms an AST node into a SparQL query fragment.
     * <p>
     * Transforma un nodo del AST en un fragmento de consulta SparQl.
     *
     * @param root          AST node to transform. Nodo del AST a transformar.
     * @param parent        name of the parent object of the node (for searching
     *                      properties). Nombre del padre del nodo analizado
     *                      (necesario para buscar propiedades en el AST).
     * @param parentLabel   name of the parent object of the node (for attaching
     *                      properties). Texto en el padre del nodo analizado
     *                      (para agregar propiedades al varList).
     * 
     * @return              a SparQL query fragment for the AST node. Un
     *                      fragmento de consulta SparQl para el nodo del AST.
     */
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
                                assertPropertyType(cname, parent) + " ?" +
                                cname.replace(" ", "_").replaceAll("[\\(|\\)]", "") + ".\n";
                    }
                }
            } else {
                res = res + "?" + parent.replace(" ", "_").replaceAll("[\\(|\\)]", "") + " ?prop ?val.\n";
            }
        } else {
            if (child != null) {
                String rangeType = assertPropertyRangeType(nname, parent);
                if (!rangeType.equals("")) {
                    SemanticClass scl = assertPropertyRangeClass(nname, parent);
                    res = res + "?" + nname.replace(" ", "_").replaceAll("[\\(|\\)]", "") + " rdf:type " + rangeType + ".\n";
                    String pName = assertPropertyType(nname, parent);
                    if (!pName.equals("")) {
                        res = res + "?" + parent.replace(" ", "_").replaceAll("[\\(|\\)]", "") + " " + pName + " ?" +
                                nname.replace(" ", "_").replaceAll("[\\(|\\)]", "") + ".\n";
                    }
                    if (scl != null) {
                        String cName = scl.getDisplayName(lex.getLanguage());
                        for (CommonTree t : child) {
                            res += processNode(t, cName, nname);
                        }
                    }
                }
            } else {
                String pName = assertPropertyType(nname, parent);
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

    /**
     * Transforms a SELECT node in the AST to a SparQL query fragment.
     * <p>
     * Transforma un nodo SELECT en el AST en un fragmento de la consulta SparQl.
     *
     * @param root          SELECT node to transform. Nodo SELECT a transformar.
     * @param hasPrecon     wheter or not the AST has a PRECON node.
     *                      Indica si el AST contiene una preposición CON.
     * @param               hasPrede wheter or not the AST has a PREDE node.
     *                      Indica si el AST contiene una preposición DE.
     * @return              String of a SparQL query fragment. Fragmento de
     *                      consulta SparQl.
     */
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
                    String etype = lex.getObjWordTag(t.getText()).getType();
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

    /**
     * Transforms an ASIGN, COMPL, COMPG, COMPLE or COMPGE node into a SparQL
     * query fragment. The last four nodes generate a FILTER clause in the
     * resulting fragment.
     * <p>
     * Transforma un nodo ASIGN, COMPL, COMPG, COMPLE o COMPGE en un fragmento
     * de consulta SparQl. Los últimos cuatro nodos generan la clausula FILTER
     * en el fragmento resultante.
     *
     * @param root          one of the above nodes. Uno de los nodos mencionados
     *                      anteriormente.
     * @param parent        name of the parent object of the statement node 
     *                      (for searching). Nombre del nodo padre del nodo
     *                      analizado.
     * @param parentLabel   text of the parent object of the statement node
     *                      (for attaching). Texto del nodo padre del nodo
     *                      analizado.
     * @return              a SparQL query fragment, specifically a triple for
     *                      an ASIGN node or a triple and a FILTER clause for
     *                      the node. Un fragmento de consulta SparQl,
     *                      específicamente un patrón de tripla para un nodo
     *                      ASIGN o una tripla y una clausula FILTER.
     */
    private String processStatement(CommonTree root, String parent, String parentLabel) {
        String res = "";
        String pName = assertPropertyType(root.getChild(0).getText(), parent);
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

    /**
     * Starts deep parsing of the AST.
     * <p>
     * Inicia el análisis a profundidad del AST.
     *
     * @param root  root node to start parsing (usually child of SELECT node).
     *              Nodo raíz (usualmente hijo del nodo SELECT) para el análisis.
     * 
     * @return      SparQl query fragment. Fragmento de consulta SparQl.
     */
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

    /**
     * Transforms a Natural Language query to a SparQL query. Using an antlr
     * (http://www.antlr.org) parser, this method builds an AST
     * (Abstract Sintax Tree) and traverses it to generate the SparQL query.
     * <p>
     * Transforma una consulta en lenguaje natural a una consulta en SparQl.
     * Usando un analizador generado por antlr (http://www.antlr.org), el método
     * construye un AST (Árbol de sintáxis abstracta) y lo recorre para generar
     * la consulta SparQl.
     *
     * @param sent  Rescticted natural language sentence for the query.
     *              Oración en lenguaje natural restringido para la consulta.
     * @return      SparQl query sentence. Sentencia de la consulta SparQl.
     */
    public String translateSentence(String sent, boolean sRequired) {
        String res = "";
        CommonTree sTree = null;
        input = new ANTLRStringStream(sent);
        tokenizer = getLocaleLexer(lex.getLanguage(), input);
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

    /**
     * Prints the given AST with indentation.
     * <p>
     * Imprime un AST con indentación.
     *
     * @param root      AST to print. AST a imprimir.
     * @param indent    indentation string. Cadena de indentado.
     */
    private void traverseAST(CommonTree root, String indent) {
        System.out.println(indent + root.getText());
        List<CommonTree> chil = null;

        if (root.getChildCount() > 0) {
            chil = root.getChildren();
        }
        if (chil != null) {
            for (CommonTree t : chil) {
                traverseAST(t, indent + "  ");
            }
        }
    }

    /**
     * Gets a custom lexer for a specific language. To add support for more
     * languages you should create a 'Lang'Lexer class (where lang stands for
     * your language name) and add astatement to this method to return an
     * instance of such lexer.
     * <p>
     * Obtiene un analizador léxico específico para un lenguaje. Para agregar
     * soporte a más lenguajes, debe crearse una clase 'Lang'Lexer (donde 'Lang'
     * debe sustituirse por el nombre del lenguaje) y agregar una instrucción
     * a este método para devolver una instancia de dicha clase.
     *
     * @param langCode  code of the language to create a lexer for. Código de
     *                  lenguaje del analizador léxico.
     * @param input     {@link ANTLRStringStream} to analyze.
     *                  {@link ANTLRStringStream} a analizar.
     *
     * @return          Specific lexer for the given language. Analizador Léxico
     *                  para el idioma especificado.
     */
    public Lexer getLocaleLexer(String langCode, ANTLRStringStream input) {
        if (langCode.equals("es")) {
            return new SpanishLexer(input);
        } else if (langCode.equals("en")) {
            return new EnglishLexer(input);
        }
        return null;
    }
}
