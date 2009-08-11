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
 


package com.infotec.wb.resources.database;

import java.util.HashMap;
import java.util.Vector;
import java.util.Iterator;
import java.util.Enumeration;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

//import com.infotec.appfw.lib.dtdscanners.wbUserTypes;

/**
 * Scanner of DOM tree.
 *
 * Example:
 * <pre>
 *     javax.xml.parsers.DocumentBuilderFactory builderFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
 *     javax.xml.parsers.DocumentBuilder builder = builderFactory.newDocumentBuilder();
 *     org.w3c.dom.Document document = builder.parse (new org.xml.sax.InputSource (???));
 *     <font color="blue">FormasScanner scanner = new FormasScanner (document);</font>
 *     <font color="blue">scanner.visitDocument();</font>
 * </pre>
 *
 * @see org.w3c.dom.Document
 * @see org.w3c.dom.Element
 * @see org.w3c.dom.NamedNodeMap
 *
 * @author: Sergio Tellez
 * @version 1.2
 */
public class FormScanner {
	
	/**
	 * Form definition DOM.
	 */
    private org.w3c.dom.Document document;
    
    /**
	 * Form definition DOM incextrainfo.
	 */
    private org.w3c.dom.Document secdocument;
    
    /**
	 * Scanning DOM tree result.
	 */
    private org.w3c.dom.Document prindoc;
    
    /**
	 * Scanner user type.
	 */
    //private wbUserTypes scanner;
    
    /**
	 * DOM root element.
	 */
    private org.w3c.dom.Element wrkelement;
    
    /**
	 * DOM root node .
	 */
    private org.w3c.dom.Node wrknode;
    
    /**
	 * DOM building arguments.
	 */
    private HashMap data;

    /** 
     * Create new FormScanner with org.w3c.dom.Document.
     * @param document Document Base
     * @param secdocument Document incextrainfo
     * @param prindoc Document result
     */
    public FormScanner(org.w3c.dom.Document document, org.w3c.dom.Document secdocument, org.w3c.dom.Document prindoc) {
        this.document = document;
        this.secdocument = secdocument;
        this.prindoc = prindoc;
        //scanner = new wbUserTypes(secdocument);
    }

    /** 
     * Create new FormScanner with org.w3c.dom.Document.
     * @param document Document Base
     * @param prindoc Document result
     */
    public FormScanner(org.w3c.dom.Document document, org.w3c.dom.Document prindoc) {
        this.document = document;
        this.secdocument = null;
        this.prindoc = prindoc;
    }

    /**
	 * Create new FormScanner with org.w3c.dom.Document.
	 * 
	 * @param document Document base
	 * @param prindoc Document result
	 * @param data arguments from data source 
	 */
    public FormScanner(Document document, Document prindoc, HashMap data) {
	  this.data = data;
	  this.secdocument = null;
	  this.prindoc = prindoc;
      this.document = document;
    }    
    
    /**
	 * Constructor.
	 * 
	 * @param document Document base
	 * @param secdocument Document incextrainfo
	 * @param prindoc Document result
	 * @param data arguments from data source
	 */
    public FormScanner(Document document, Document secdocument, Document prindoc, HashMap data) {
        this.document = document;
        this.secdocument = secdocument;
        this.prindoc = prindoc;
        this.data = data;
        //this.scanner = new wbUserTypes(secdocument);
    }
    
    /** Scan through org.w3c.dom.Document document. */
    public void visitDocument() {
        org.w3c.dom.Element element = document.getDocumentElement();
        if ((element != null) && element.getTagName().equals("FORMA")) {
            visitElement_FORMA(element);
        }
    }

    /** Scan through org.w3c.dom.Element named FORMA. */
    void visitElement_FORMA(org.w3c.dom.Element element) {
    	// <FORMA>
        this.wrkelement = this.prindoc.createElement(element.getTagName());
        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr) attrs.item(i);
            if (attr.getName().equals("TIPO")) {
            	// <FORMA TIPO="???">
                wrkelement.setAttribute("TIPO", attr.getValue());
            }
            if (attr.getName().equals("NOMBRE")) {
            	// <FORMA NOMBRE="???">
                wrkelement.setAttribute("NOMBRE", attr.getValue());
            }
        }
        this.prindoc.appendChild(this.wrkelement);
        this.wrknode = prindoc.getFirstChild();
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) {
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
                    break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                    org.w3c.dom.Element nodeElement = (org.w3c.dom.Element) node;
                    if (nodeElement.getTagName().equals("TEXT")) {
                        visitElement_TEXT(nodeElement);
                    }
                    if (nodeElement.getTagName().equals("TEXTAREA")) {
                        visitElement_TEXTAREA(nodeElement);
                    }
                    if (nodeElement.getTagName().equals("FILE")) {
                        visitElement_FILE(nodeElement);
                    }
                    if (nodeElement.getTagName().equals("HIDDEN")) {
                        visitElement_HIDDEN(nodeElement);
                    }
                    if (nodeElement.getTagName().equals("SELECT")) {
                        visitElement_SELECT(nodeElement);
                    }
                    if (nodeElement.getTagName().equals("RADIOSET")) {
                        visitElement_RADIOSET(nodeElement);
                    }
                    if (nodeElement.getTagName().equals("PASSWORD")) {
                        visitElement_PASSWORD(nodeElement);
                    }
                    if (nodeElement.getTagName().equals("INCEXTRAINFO")) {
                        visitElement_INCEXTRAINFO(nodeElement);
                    }
                    if (nodeElement.getTagName().equals("MESSAGE")) {
                        visitElement_MESSAGE(nodeElement);
                    }
                    if (nodeElement.getTagName().equals("LABEL")) {
                        visitElement_LABEL(nodeElement);
                    }
                    if (nodeElement.getTagName().equals("LABELSET")) {
                        visitElement_LABELSET(nodeElement);
                    } 
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
                    break;
            }
        }
    }

    /** Scan through org.w3c.dom.Element named MESSAGE. */
    private void visitElement_MESSAGE(org.w3c.dom.Element element) {
        String url = new String();
        wrkelement = prindoc.createElement("MESSAGE");
        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr) attrs.item(i);
            if (attr.getName().equals("NOMBRE")){
            	// <TEXT NOMBRE="???">
            }
            if (attr.getName().equals("LIGA")) { 
            	// <TEXT TIPODATO="???">
                wrkelement.setAttribute("LIGA", attr.getValue());
            }
            if (attr.getName().equals("TEXTO")) {
            	// <TEXT ETIQUETA="???">
                wrkelement.setAttribute("TEXTO", attr.getValue());
            }
        }
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) {
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
                	org.w3c.dom.CDATASection wrkcdata =
                		prindoc.createCDATASection(((org.w3c.dom.CDATASection) node).getData());
                    wrkelement.appendChild(wrkcdata);
                    break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                    org.w3c.dom.Element nodeElement = (org.w3c.dom.Element) node;
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
                    break;
                case org.w3c.dom.Node.TEXT_NODE:
                    org.w3c.dom.Text wrktext = prindoc.createTextNode(((org.w3c.dom.Text) node).getData());
                    wrkelement.appendChild(wrktext);
                    break;
            }
        }
        wrknode.appendChild(wrkelement);
    }

    /** Scan through org.w3c.dom.Element named LABEL. */
    private void visitElement_LABEL(org.w3c.dom.Element element) {
        wrkelement = prindoc.createElement("LABEL");
        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr) attrs.item(i);
            if (attr.getName().equals("NOMBRE")){
            	// <TEXT NOMBRE="???">
                wrkelement.setAttribute("NOMBRE", attr.getValue());
            }
            if (attr.getName().equals("ETIQUETA"))
            { // <TEXT ETIQUETA="???">
                wrkelement.setAttribute("ETIQUETA", attr.getValue());
            }
        }
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) {
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
                	org.w3c.dom.CDATASection wrkcdata =
                		prindoc.createCDATASection(((org.w3c.dom.CDATASection) node).getData());
                    wrkelement.appendChild(wrkcdata);
                    break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                    org.w3c.dom.Element nodeElement = (org.w3c.dom.Element) node;
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
                    break;
                case org.w3c.dom.Node.TEXT_NODE:
                    org.w3c.dom.Text wrktext = prindoc.createTextNode(((org.w3c.dom.Text) node).getData());
                    wrkelement.appendChild(wrktext);
                    break;
            }
        }
        wrknode.appendChild(wrkelement);
    }

    /** Scan through org.w3c.dom.Element named TEXT. */
    private void visitElement_TEXT(org.w3c.dom.Element element) {
        wrkelement = prindoc.createElement("TEXT");
        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr) attrs.item(i);
            if (attr.getName().equals("NOMBRE"))
            { // <TEXT NOMBRE="???">
                wrkelement.setAttribute("NOMBRE", attr.getValue()); 
            }
            if (attr.getName().equals("TIPODATO"))
            { // <TEXT TIPODATO="???">
                wrkelement.setAttribute("TIPODATO", attr.getValue()); 
            }
            if (attr.getName().equals("ETIQUETA"))
            { // <TEXT ETIQUETA="???">
                wrkelement.setAttribute("ETIQUETA", attr.getValue());
            }
            if (attr.getName().equals("TAMANO"))
            { // <TEXT TAMANO="???">
                wrkelement.setAttribute("TAMANO", attr.getValue());
            }
            if (attr.getName().equals("TAMANOMAX"))
            { // <TEXT TAMANOMAX="???">
                wrkelement.setAttribute("TAMANOMAX", attr.getValue());
            }
            if (attr.getName().equals("REQUERIDO"))
            { // <TEXT REQUERIDO="???">
                wrkelement.setAttribute("REQUERIDO", attr.getValue());
            }
            if (attr.getName().equals("PATRON"))
            { // <TEXT PATRON="???">
                wrkelement.setAttribute("PATRON", attr.getValue());
            }
            if (attr.getName().equals("MENSAJE"))
            { // <TEXT MENSAJE="???">
                wrkelement.setAttribute("MENSAJE", attr.getValue());
            }
        }
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) {
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
                    org.w3c.dom.CDATASection wrkcdata =
                            prindoc.createCDATASection(((org.w3c.dom.CDATASection) node).getData());
                    wrkelement.appendChild(wrkcdata);
                    break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                    org.w3c.dom.Element nodeElement = (org.w3c.dom.Element) node;
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
                    break;
                case org.w3c.dom.Node.TEXT_NODE:
                    org.w3c.dom.Text wrktext = prindoc.createTextNode(((org.w3c.dom.Text) node).getData());
                    wrkelement.appendChild(wrktext);
                    break;
            }
        }
        wrknode.appendChild(wrkelement);
    }

    /** Scan through org.w3c.dom.Element named TEXTAREA. */
    private void visitElement_TEXTAREA(org.w3c.dom.Element element) {
        wrkelement = prindoc.createElement("TEXTAREA");
        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++)
        {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr) attrs.item(i);
            if (attr.getName().equals("NOMBRE"))
            { // <TEXTAREA NOMBRE="???">
                wrkelement.setAttribute("NOMBRE", attr.getValue());
            }
            if (attr.getName().equals("TIPODATO"))
            { // <TEXTAREA TIPODATO="???">
                wrkelement.setAttribute("TIPODATO", attr.getValue());
            }
            if (attr.getName().equals("ETIQUETA"))
            { // <TEXTAREA ETIQUETA="???">
                wrkelement.setAttribute("ETIQUETA", attr.getValue());
            }
            if (attr.getName().equals("COLUMNAS"))
            { // <TEXTAREA COLUMNAS="???">
                wrkelement.setAttribute("COLUMNAS", attr.getValue());
            }
            if (attr.getName().equals("RENGLONES"))
            { // <TEXTAREA RENGLONES="???">
                wrkelement.setAttribute("RENGLONES", attr.getValue());
            }
            if (attr.getName().equals("REQUERIDO"))
            { // <TEXTAREA REQUERIDO="???">
                wrkelement.setAttribute("REQUERIDO", attr.getValue());
            }
            if (attr.getName().equals("PATRON"))
            { // <TEXTAREA PATRON="???">
                wrkelement.setAttribute("PATRON", attr.getValue());
            }
            if (attr.getName().equals("MENSAJE"))
            { // <TEXTAREA MENSAJE="???">
                wrkelement.setAttribute("MENSAJE", attr.getValue());
            }
        }
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) {
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
                    org.w3c.dom.CDATASection wrkcdata =
                            prindoc.createCDATASection(((org.w3c.dom.CDATASection) node).getData());
                    wrkelement.appendChild(wrkcdata);
                    break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                    org.w3c.dom.Element nodeElement = (org.w3c.dom.Element) node;
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
                    break;
                case org.w3c.dom.Node.TEXT_NODE:
                    org.w3c.dom.Text wrktext = prindoc.createTextNode(((org.w3c.dom.Text) node).getData());
                    wrkelement.appendChild(wrktext);
                    break;
            }
        }
        wrknode.appendChild(wrkelement);
    }

    /** Scan through org.w3c.dom.Element named FILE. */
    private void visitElement_FILE(org.w3c.dom.Element element) {
        wrkelement = prindoc.createElement("FILE");
        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr) attrs.item(i);
            if (attr.getName().equals("NOMBRE"))
            { // <FILE NOMBRE="???">
                wrkelement.setAttribute("NOMBRE", attr.getValue());
            }
            if (attr.getName().equals("ETIQUETA"))
            { // <FILE ETIQUETA="???">
                wrkelement.setAttribute("ETIQUETA", attr.getValue());
            }
            if (attr.getName().equals("TAMANO"))
            { // <FILE TAMANO="???">
                wrkelement.setAttribute("TAMANO", attr.getValue());
            }
            if (attr.getName().equals("REQUERIDO"))
            { // <FILE REQUERIDO="???">
                wrkelement.setAttribute("REQUERIDO", attr.getValue());
            }
        }
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) {
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
                    break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                    org.w3c.dom.Element nodeElement = (org.w3c.dom.Element) node;
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
                    break;
                case org.w3c.dom.Node.TEXT_NODE:
                    break;
            }
        }
        wrknode.appendChild(wrkelement);
    }

    /** Scan through org.w3c.dom.Element named HIDDEN. */
    private void visitElement_HIDDEN(org.w3c.dom.Element element) {
        wrkelement = prindoc.createElement("HIDDEN");
        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr) attrs.item(i);
            if (attr.getName().equals("NOMBRE"))
            { // <HIDDEN NOMBRE="???">
                wrkelement.setAttribute("NOMBRE", attr.getValue());
            }
            if(attr.getName().equals("VALOR"))
            { // <HIDDEN VALOR="???">
            	wrkelement.setAttribute("VALOR", attr.getValue());
            }
        }
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) {
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
                    org.w3c.dom.CDATASection wrkcdata =
                            prindoc.createCDATASection(((org.w3c.dom.CDATASection) node).getData());
                    wrkelement.appendChild(wrkcdata);
                    break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                    org.w3c.dom.Element nodeElement = (org.w3c.dom.Element) node;
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
                    break;
                case org.w3c.dom.Node.TEXT_NODE:
                    org.w3c.dom.Text wrktext = prindoc.createTextNode(((org.w3c.dom.Text) node).getData());
                    wrkelement.appendChild(wrktext);
                    break;
            }
        }
        wrknode.appendChild(wrkelement);
    }

    /** Scan through org.w3c.dom.Element named LABELSET. */
    private void visitElement_LABELSET(org.w3c.dom.Element element) {
    	String name = new String();
        wrkelement = prindoc.createElement("LABELSET");
        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr) attrs.item(i);
            if (attr.getName().equals("NOMBRE"))
            { // <LABELSET NOMBRE="???">
            	name = attr.getValue();
                wrkelement.setAttribute("NOMBRE", attr.getValue());
            }
            if (attr.getName().equals("ETIQUETA"))
            { // <LABELSET ETIQUETA="???">
                wrkelement.setAttribute("ETIQUETA", attr.getValue());
            }
        }
        //if LABELSET must be filled from arguments
        if (data.containsKey(name)) {
            Iterator it = (Iterator)data.get(name);
            while (it.hasNext()) {
                Vector container = (Vector)it.next();
                String value = (String)container.elementAt(0);
                String option = (String)container.elementAt(1);
                Element wrkelement2 = prindoc.createElement("ELEMENTO");
                wrkelement2.setAttribute("VALOR", value);
                wrkelement2.setAttribute("ETIQUETA", option);
                wrkelement.appendChild(wrkelement2);
            }
        }
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) {
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
                    break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                    org.w3c.dom.Element nodeElement = (org.w3c.dom.Element) node;
                    if (nodeElement.getTagName().equals("ELEMENTO"))
                    {
                        visitElement_ELEMENTO(nodeElement);
                    }
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
                    break;
            }
        }
        wrknode.appendChild(wrkelement);
    }
    
    /** Scan through org.w3c.dom.Element named SELECT. */
    private void visitElement_SELECT(org.w3c.dom.Element element) {
    	String name = new String();
        wrkelement = prindoc.createElement("SELECT");
        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr) attrs.item(i);
            if (attr.getName().equals("NOMBRE"))
            { // <SELECT NOMBRE="???">
            	name = attr.getValue();
                wrkelement.setAttribute("NOMBRE", attr.getValue());
            }
            if (attr.getName().equals("TIPODATO"))
            { // <SELECT TIPODATO="???">
                wrkelement.setAttribute("TIPODATO", attr.getValue());
            }
            if (attr.getName().equals("ETIQUETA"))
            { // <SELECT ETIQUETA="???">
                wrkelement.setAttribute("ETIQUETA", attr.getValue());
            }
            if (attr.getName().equals("RENGLONES"))
            { // <SELECT RENGLONES="???">
                wrkelement.setAttribute("RENGLONES", attr.getValue());
            }
            if (attr.getName().equals("SELECCION"))
            { // <SELECT SELECCION="???">
                wrkelement.setAttribute("SELECCION", attr.getValue());
            }
            if (attr.getName().equals("ANIDADO"))
            { // <SELECT ANIDADO="???">
                wrkelement.setAttribute("ANIDADO", attr.getValue());
            }
        }
        //if SELECT must be filled from arguments
        if (data.containsKey(name)) {
            Iterator it = (Iterator)data.get(name);
            while (it.hasNext()) {
                Vector container = (Vector)it.next();
                String value = (String)container.elementAt(0);
                String option = (String)container.elementAt(1);
                Element wrkelement2 = prindoc.createElement("ELEMENTO");
                wrkelement2.setAttribute("VALOR", value);
                wrkelement2.setAttribute("ETIQUETA", option);
                wrkelement.appendChild(wrkelement2);
            }
        }
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) {
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
                    break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                    org.w3c.dom.Element nodeElement = (org.w3c.dom.Element) node;
                    if (nodeElement.getTagName().equals("ELEMENTO"))
                    {
                        visitElement_ELEMENTO(nodeElement);
                    }
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
                    break;
            }
        }
        wrknode.appendChild(wrkelement);
    }

    /** Scan through org.w3c.dom.Element named RADIOSET. */
    private void visitElement_RADIOSET(org.w3c.dom.Element element) {
    	String name = new String();
		HashMap values = new HashMap();
        wrkelement = prindoc.createElement("RADIOSET");
        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr) attrs.item(i);
            if (attr.getName().equals("NOMBRE"))
            { // <RADIOSET NOMBRE="???">
            	name = attr.getValue();
                wrkelement.setAttribute("NOMBRE", attr.getValue());
            }
            if (attr.getName().equals("TIPODATO"))
            { // <RADIOSET TIPODATO="???">
                wrkelement.setAttribute("TIPODATO", attr.getValue());
            }
            if (attr.getName().equals("ETIQUETA"))
            { // <RADIOSET ETIQUETA="???">
                wrkelement.setAttribute("ETIQUETA", attr.getValue());
            }
            if (attr.getName().equals("LAYOUT"))
            { // <RADIOSET LAYOUT="???">
                wrkelement.setAttribute("LAYOUT", attr.getValue());
            }
            if (attr.getName().equals("TIPO"))
            { // <RADIOSET TIPO="???">
                wrkelement.setAttribute("TIPO", attr.getValue());
            }
        }
        //if SELECT must be filled from arguments
        if (data.containsKey(name)) {
            Iterator it = (Iterator)data.get(name);
            while (it.hasNext()) {
                Vector container = (Vector)it.next();
                String value = (String)container.elementAt(0);
                String option = (String)container.elementAt(1);
                Element wrkelement2 = prindoc.createElement("ELEMENTO");
                wrkelement2.setAttribute("VALOR", value);
                wrkelement2.setAttribute("ETIQUETA", option);
                wrkelement.appendChild(wrkelement2);
            }
        }
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) {
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
                    break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                    org.w3c.dom.Element nodeElement = (org.w3c.dom.Element) node;
                    if (nodeElement.getTagName().equals("ELEMENTO"))
                    {
                        visitElement_ELEMENTO(nodeElement);
                    }
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
                    break;
            }
        }
        wrknode.appendChild(wrkelement);
    }

    /** Scan through org.w3c.dom.Element named PASSWORD. */
    private void visitElement_PASSWORD(org.w3c.dom.Element element) {
        wrkelement = prindoc.createElement("PASSWORD");
        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr) attrs.item(i);
            if (attr.getName().equals("NOMBRE"))
            { // <PASSWORD NOMBRE="???">
                wrkelement.setAttribute("NOMBRE", attr.getValue());
            }
            if (attr.getName().equals("TIPODATO"))
            { // <PASSWORD TIPODATO="???">
                wrkelement.setAttribute("TIPODATO", attr.getValue());
            }
            if (attr.getName().equals("ETIQUETA"))
            { // <PASSWORD ETIQUETA="???">
                wrkelement.setAttribute("ETIQUETA", attr.getValue());
            }
            if (attr.getName().equals("TAMANO"))
            { // <PASSWORD TAMANO="???">
                wrkelement.setAttribute("TAMANO", attr.getValue());
            }
            if (attr.getName().equals("TAMANOMAX"))
            { // <PASSWORD TAMANOMAX="???">
                wrkelement.setAttribute("TAMANOMAX", attr.getValue());
            }
            if (attr.getName().equals("REQUERIDO"))
            { // <PASSWORD REQUERIDO="???">
                wrkelement.setAttribute("REQUERIDO", attr.getValue());
            }
            if (attr.getName().equals("VERIFICAR"))
            { // <PASSWORD VERIFICAR="???">
                wrkelement.setAttribute("VERIFICAR", attr.getValue());
            }
            if (attr.getName().equals("PATRON"))
            { // <PASSWORD PATRON="???">
                wrkelement.setAttribute("PATRON", attr.getValue());
            }
            if (attr.getName().equals("MENSAJE"))
            { // <PASSWORD MENSAJE="???">
                wrkelement.setAttribute("MENSAJE", attr.getValue());
            }
        }
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) {
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
                    org.w3c.dom.CDATASection wrkcdata =
                            prindoc.createCDATASection(((org.w3c.dom.CDATASection) node).getData());
                    wrkelement.appendChild(wrkcdata);
                    break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                    org.w3c.dom.Element nodeElement = (org.w3c.dom.Element) node;
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
                    break;
                case org.w3c.dom.Node.TEXT_NODE:
                    org.w3c.dom.Text wrktext = prindoc.createTextNode(((org.w3c.dom.Text) node).getData());
                    wrkelement.appendChild(wrktext);
                    break;
            }
        }
        wrknode.appendChild(wrkelement);
    }

    /** Scan through org.w3c.dom.Element named ELEMENTO. */
    private void visitElement_ELEMENTO(org.w3c.dom.Element element) {
        org.w3c.dom.Element wrkelement2 = prindoc.createElement("ELEMENTO");
        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr) attrs.item(i);
            if (attr.getName().equals("VALOR"))
            { // <ELEMENTO VALOR="???">
                wrkelement2.setAttribute("VALOR", attr.getValue());
            }
            if (attr.getName().equals("ETIQUETA"))
            { // <ELEMENTO ETIQUETA="???">
                wrkelement2.setAttribute("ETIQUETA", attr.getValue());
            }
            if (attr.getName().equals("INICIAL"))
            { // <ELEMENTO INICIAL="???">
                wrkelement2.setAttribute("INICIAL", attr.getValue());
            }
        }
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) {
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
                    break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                	org.w3c.dom.Element nodeElement = (org.w3c.dom.Element) node;
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
                    break;
            }
        }
        wrkelement.appendChild(wrkelement2);
    }

    /** Scan through org.w3c.dom.Element named INCEXTRAINFO. */
    private void visitElement_INCEXTRAINFO(org.w3c.dom.Element element) {
        //call AttuserScanner
        //scanner.visitDocument(wrknode, prindoc);
    }
}
