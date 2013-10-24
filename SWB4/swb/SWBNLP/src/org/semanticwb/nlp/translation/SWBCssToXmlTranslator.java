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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;

import org.semanticwb.nlp.analysis.CSSLexer;
import org.semanticwb.nlp.analysis.SimpleCSSParser;

import org.w3c.dom.*;

/**
 *
 * @author hasdai
 */
public class SWBCssToXmlTranslator {
    private SimpleCSSParser parser;     //ANTLR parser
    private CSSLexer tokenizer;   //ANTLR tokenizer
    private CommonTokenStream tokens;   //TokenStream for parsing
    private ANTLRStringStream input;    //StringStream to parse

    public void translateCSS (String css, String filePath) {
        //Se inicializa el parser
        input = new ANTLRStringStream(css);
        tokenizer = new CSSLexer(input);
        tokens = new CommonTokenStream(tokenizer);
        parser = new SimpleCSSParser(tokens);

        CommonTree sTree = null;
        try {
            //Se ejecuta el parser
            SimpleCSSParser.styleSheet_return qres = (SimpleCSSParser.styleSheet_return) parser.styleSheet();

            //Si no hay errores reportados por el parser
            if(parser.getErrorCount() == 0) {
                //Se obtiene el AST
                sTree = (CommonTree) qres.getTree();

                //Se obtiene el XML del AST
                Document doc = parse(sTree);

                if(doc != null) {
                    //Se escribe el archivo XML
                    Source source = new DOMSource(doc);
                    File f = new File(filePath);
                    Result res = new StreamResult(f);

                    Transformer xformer;
                    try {
                        xformer = TransformerFactory.newInstance().newTransformer();
                        xformer.transform(source, res);
                    } catch (TransformerConfigurationException ex) {
                        Logger.getLogger(SWBCssToXmlTranslator.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (TransformerException ex) {
                        Logger.getLogger(SWBCssToXmlTranslator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                System.out.println("El CSS no pasó");
            }
        } catch (org.antlr.runtime.RecognitionException ex) {
            System.out.println("El CSS no pasó");
        }        
    }

    public Document translateCSS (String css) {
        //Se inicializa el parser
        input = new ANTLRStringStream(css);
        tokenizer = new CSSLexer(input);
        tokens = new CommonTokenStream(tokenizer);
        parser = new SimpleCSSParser(tokens);

        CommonTree sTree = null;
        Document doc = null;
        try {
            SimpleCSSParser.styleSheet_return qres = (SimpleCSSParser.styleSheet_return) parser.styleSheet();
            if(parser.getErrorCount() == 0) {
                sTree = (CommonTree) qres.getTree();
                doc = parse(sTree);
            } else {
                System.out.println("El CSS no pasó");
            }
        } catch (org.antlr.runtime.RecognitionException ex) {
            System.out.println("El CSS no pasó");
        }
        return doc;
    }

    //Recorre el DOM y escribe sus nodos en la terminal
    public void traverseDom(Node root, String ident) {        
        NamedNodeMap atts = root.getAttributes();

        String at = "";
        for (int i = 0; i < atts.getLength(); i++) {
            Node n = atts.item(i);
            at += n.getNodeName() + " = \"" + n.getNodeValue() + "\" ";
        }

        System.out.println(ident + "<" + root.getNodeName() + " " + at.trim() + ">");

        NodeList childs = root.getChildNodes();

        for (int i = 0; i < childs.getLength(); i++) {
            traverseDom(childs.item(i), ident + "  ");
        }
    }

    //Parsea el AST para poblar el XML
    private Document parse(CommonTree node) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            //Get the DocumentBuilder
            DocumentBuilder domParser = factory.newDocumentBuilder();
            doc = domParser.newDocument();
            
            Element root = doc.createElement("css");
            ArrayList<Element> statements = new ArrayList<Element>();
            List<CommonTree> childs = (List<CommonTree>) node.getChildren();

            //Para cada nodo hijo (SELECTOR)
            for (CommonTree child : childs) {
                //Obtener las propiedades de cada selector y armar el nodo XML
                statements = processSelector(doc, child);
                for (Element e : statements) {
                    root.appendChild(e);
                }
            }
            doc.appendChild(root);
            return doc;
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(SWBCssToXmlTranslator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doc;
    }

    //Procesa el nodo SELECTOR y agrega las propiedades a las clases involucradas
    private ArrayList<Element> processSelector (Document doc, CommonTree node) {
        ArrayList<Element> classes = new ArrayList();
        ArrayList<Element> properties = new ArrayList<Element>();
        classes = processElements(doc, (CommonTree)node.getChild(0));
        properties = processStyle(doc, (CommonTree)node.getChild(1));

        for (Element c : classes) {
            for (Element p : properties) {
                c.appendChild(p.cloneNode(true));
            }
        }        
        return classes;
    }

    //Procesa los nodos para cada clase del CSS
    private ArrayList<Element> processElements(Document doc, CommonTree node) {
        ArrayList<Element> res = new ArrayList<Element>();
        for(CommonTree t : (List<CommonTree>) node.getChildren()) {
            Element el = processClass(doc, (CommonTree)t.getChild(0));
            res.add(el);
        }
        return res;
    }

    //Crea el nodo para las propiedades de estilo del CSS
    private ArrayList<Element> processStyle(Document doc, CommonTree node) {
        ArrayList<Element> res = new ArrayList<Element>();
        if(node.getChildren()!=null) {
            for(CommonTree t : (List<CommonTree>) node.getChildren()) {
                res.add(processProperty(doc, t));
            }
        }
        return res;
    }

    //Crea un nodo para una propiedad del CSS
    private Element processProperty(Document doc, CommonTree node) {
        Element el = doc.createElement("property");
        el.setAttribute("name", node.getChild(0).getChild(0).getText());
        el.setAttribute("value", processVal((CommonTree)node.getChild(1)));
        return el;
    }

    //Crea el nodo para una clase del CSS
    private Element processClass(Document doc, CommonTree node) {
        Element el = doc.createElement("class");
        el.setAttribute("name", node.getText());
        return el;
    }

    //Une los nodos de texto bajo un nodo VAL
    private String processVal (CommonTree node) {
        String res = "";
        if (node.getChildCount() > 0) {
            for (CommonTree t : (List<CommonTree>) node.getChildren()) {
                    res += t.getText() + " ";
            }
        }
        return res.trim();
    }
}