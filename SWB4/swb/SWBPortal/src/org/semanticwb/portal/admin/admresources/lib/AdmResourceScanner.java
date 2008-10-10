/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * File:           admresourcescanner.java
 * Generated from: admresource.dtd
 * Date:           15 de marzo de 2004  04:00 AM
 *
 * @author  Infotec
 */
package org.semanticwb.portal.admin.admresources.lib;

import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;
/**
 * Objeto que escanea un DOM y extrae sus elementos
 * Object that scanner a DOM and get its elements
 *
 * Example:
 * <pre>
 *     javax.xml.parsers.DocumentBuilderFactory builderFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
 *     javax.xml.parsers.DocumentBuilder builder = builderFactory.newDocumentBuilder();
 *     org.w3c.dom.Document document = builder.parse (new org.xml.sax.InputSource (???));
 *     <font color="blue">admresourcescanner scanner = new admresourcescanner (document);</font>
 *     <font color="blue">scanner.visitDocument();</font>
 * </pre>
 *
 * @see org.w3c.dom.Document
 * @see org.w3c.dom.Element
 * @see org.w3c.dom.NamedNodeMap
 */
public class AdmResourceScanner 
{
    private static Logger log = SWBUtils.getLogger(AdmResourceScanner.class);
    
    
    WBAdmResourceUtils admResUtils=new WBAdmResourceUtils();
    org.w3c.dom.Document indom;
    org.w3c.dom.Document outdom;
    org.w3c.dom.Node     frmroot;
    org.w3c.dom.Node     frmnode;
    org.w3c.dom.Element  frmelement;
    String strError="";
    boolean bError=false;

    /** Create new AdmResourceScanner with org.w3c.dom.Document.
     * @param xml       XML
     * @param outdom    Output Document
     */
    public AdmResourceScanner(String xml) {
        try
        { 
            this.indom=SWBUtils.XML.xmlToDom(xml); 
            if(this.indom==null)
            {
                bError=true;
                log.error("Admresource XmltoDom() - Error while transformig dom to xml");
            }
        }
        catch(Exception e)
        {
            bError=true;
            log.error("Admresource XmltoDom()", e);
        }
        
        try{ this.outdom=SWBUtils.XML.getNewDocument(); }
        catch(SWBException e)
        {
            bError=true;
            log.error("Admresource getNewDocument()", e);            
        }
    }
    
    /** Create new AdmResourceScanner with org.w3c.dom.Document.
     * @param indom     Input Document
     */
    public AdmResourceScanner(org.w3c.dom.Document indocument) {
        this.indom=indocument;
        try{outdom=SWBUtils.XML.getNewDocument();}
        catch(SWBException e){bError=true;}
    }
   
    /** Scan through org.w3c.dom.Document document. */
    public boolean visitDocument()
    {
        org.w3c.dom.Element element=null;
        if (indom != null) element = indom.getDocumentElement();
        if (element != null && element.getTagName().toUpperCase().equals("ADMRESOURCE")) visitAdmResource(element);
        return bError;
    }

    /** Return org.w3c.dom.Document document. */
    public org.w3c.dom.Document getOutDocument()
    {
        return this.outdom;
    }
    
    /** Scan through org.w3c.dom.Element named 
     * ADMRESOURCE.
     */
    void visitAdmResource(org.w3c.dom.Element element) 
    {
        org.w3c.dom.Element root = this.outdom.createElement(element.getTagName());
        this.outdom.appendChild(root);
        this.frmroot = this.outdom.getFirstChild();
        boolean bOk=false;
        
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) 
        {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) 
            {
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
                    // ((org.w3c.dom.CDATASection)node).getData();
                    break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                    org.w3c.dom.Element elementNode = (org.w3c.dom.Element)node;
                    String tagName=elementNode.getTagName().toUpperCase();
                    if (tagName.equals("XSLFILE"))
                    {
                        bOk=true;
                        visitXslFile(elementNode);
                    }
                    else if (tagName.equals("FORM")) 
                    {
                        bOk=true;
                        visitForm(elementNode);
                    }
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
                    // ((org.w3c.dom.ProcessingInstruction)node).getTarget();
                    // ((org.w3c.dom.ProcessingInstruction)node).getData();
                    break;
                case org.w3c.dom.Node.TEXT_NODE:
                    // ((org.w3c.dom.Text)node).getData();
                    break;
            }
            if(!bOk) 
            {
                if(org.w3c.dom.Node.ELEMENT_NODE == node.getNodeType())
                {
                    bError=true;
                    log.error("Admresource - Invalid element node: "+ ((org.w3c.dom.Element)node).getTagName()); 
                }
            }
            bOk=false;
        }
    }

    /** Scan through org.w3c.dom.Element named 
     * XSLFILE.
     */
    void visitXslFile(org.w3c.dom.Element element) 
    {
        this.frmelement = this.outdom.createElement(element.getTagName());
        this.frmroot.appendChild(this.frmelement);

        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) 
        {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) 
            {
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
                    org.w3c.dom.CDATASection cdata = this.outdom.createCDATASection(((org.w3c.dom.CDATASection)node).getData());
                    this.frmelement.appendChild(cdata);
                    break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                    //org.w3c.dom.Element elementNode = (org.w3c.dom.Element)node;
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
                    // ((org.w3c.dom.ProcessingInstruction)node).getTarget();
                    // ((org.w3c.dom.ProcessingInstruction)node).getData();
                    break;
                case org.w3c.dom.Node.TEXT_NODE:
                    org.w3c.dom.Text frmtext = this.outdom.createTextNode(((org.w3c.dom.Text)node).getData());
                    this.frmelement.appendChild(frmtext);
                    break;
            }
        }
    }
    
    /** Scan through org.w3c.dom.Element named 
     * FORM.
     */
    void visitForm(org.w3c.dom.Element element) 
    {
        String[] formAttrs = {"name","action","method","enctype","accept","accept-charset"};
        this.frmelement = this.outdom.createElement(element.getTagName());
        boolean bOk=false;

        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) 
        {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr)attrs.item(i);
            strError="";
            bOk=evalCoreAttributes(attr);
            if(bOk) this.frmelement.setAttribute(attr.getName().toLowerCase(), attr.getValue());
            if(!bOk)
            {
                for(int j=0; j < formAttrs.length; j++)
                {
                    if (attr.getName().toLowerCase().equals(formAttrs[j])) 
                    {
                        if(formAttrs[j].equals("name"))
                        {
                            if(admResUtils.isCDATA(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The required value is a basic data type CDATA)";
                        }
                        else if(formAttrs[j].equals("method"))
                        {
                            if(admResUtils.isFormMethod(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The valid values are GET or POST)";
                        }
                        else bOk=true;
                        
                        if(bOk) this.frmelement.setAttribute(formAttrs[j], attr.getValue());
                        break;
                    }
                }
            }
            if(!bOk) 
            {
                bError=true;
                log.error("Form - Invalid attribute: " +attr.getName()+ strError);
            }
            bOk=false;
        }
        this.frmroot.appendChild(this.frmelement);
        this.frmnode = this.frmroot.getLastChild();
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) 
        {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) 
            {
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
                    // ((org.w3c.dom.CDATASection)node).getData();
                    break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                    org.w3c.dom.Element elementNode = (org.w3c.dom.Element)node;
                    String tagName=elementNode.getTagName().toUpperCase();
                    if (tagName.equals("ADMDBCONNMGR")) visitAdmDBConnMgr(elementNode);
                    if (tagName.equals("READONLY")) visitReadonly(elementNode);
                    if (tagName.equals("IMG")) visitImg(elementNode);
                    if (tagName.equals("INPUT")) visitInput(elementNode);
                    if (tagName.equals("SELECT")) visitSelect(elementNode);
                    if (tagName.equals("TEXTAREA")) visitTextarea(elementNode);                    
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
                    // ((org.w3c.dom.ProcessingInstruction)node).getTarget();
                    // ((org.w3c.dom.ProcessingInstruction)node).getData();
                    break;
                case org.w3c.dom.Node.TEXT_NODE:
                    // ((org.w3c.dom.Text)node).getData();
                    break;
            }
        }
    }
    /** Scan through org.w3c.dom.Element named 
     * ADMDBCONNMGR. 
     */
    void visitAdmDBConnMgr(org.w3c.dom.Element element) 
    { 
        String[] dbconnAttrs = {"defconn","conname","tablename","fieldname","fieldwhere"};
        this.frmelement = this.outdom.createElement("ADMDBCONNMGR");
        boolean bOk=false;
        
        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) 
        {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr)attrs.item(i);
            strError="";
            if(!bOk)
            {
                for(int j=0; j < dbconnAttrs.length; j++)
                {
                    if (attr.getName().toLowerCase().equals(dbconnAttrs[j])) 
                    {
                        if(dbconnAttrs[j].equals("defconn"))
                        {
                            if(admResUtils.isBoolean(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The valid values are TRUE or FALSE)";
                        }                         
                        else if(dbconnAttrs[j].equals("conname") || dbconnAttrs[j].equals("tablename") || dbconnAttrs[j].equals("fieldname") || dbconnAttrs[j].equals("fieldwhere"))
                        {
                            if(admResUtils.isCDATA(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The required value is a basic data type CDATA)";
                        }                            
                        else bOk=true;
                        
                        if(bOk) this.frmelement.setAttribute(dbconnAttrs[j], attr.getValue());
                        break;
                    }
                }
            }
            if(!bOk) 
            {
                bError=true;
                log.error("AdmDBConnMgr - Invalid attribute: " +attr.getName()+ strError);
            }
            bOk=false;
        }
        this.frmnode.appendChild(appendChild(element));
    }
    
    /** Scan through org.w3c.dom.Element named 
     * READONLY. 
     */
    void visitReadonly(org.w3c.dom.Element element) 
    { 
        String[] readonlyAttrs = {"name","value","size","maxlength","label"};
        this.frmelement = this.outdom.createElement("READONLY");
        boolean bOk=false;
        
        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) 
        {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr)attrs.item(i);
            strError="";
            bOk=evalCoreAttributes(attr);
            if(bOk) this.frmelement.setAttribute(attr.getName().toLowerCase(), attr.getValue());
            if(!bOk)
            {
                for(int j=0; j < readonlyAttrs.length; j++)
                {
                    if (attr.getName().toLowerCase().equals(readonlyAttrs[j])) 
                    {
                        if(readonlyAttrs[j].equals("name") || readonlyAttrs[j].equals("value") || readonlyAttrs[j].equals("label"))
                        {
                            if(admResUtils.isCDATA(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The required value is a basic data type CDATA)";
                                                      
                        }                            
                        else if(readonlyAttrs[j].equals("size") || readonlyAttrs[j].equals("maxlength"))
                        {
                            if(admResUtils.isNumber(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The required value is a data type NUMBER)";
                        }                            
                        else bOk=true;
                        
                        if(bOk) this.frmelement.setAttribute(readonlyAttrs[j], attr.getValue());
                        break;
                    }
                }
            }
            if(!bOk) 
            {
                bError=true;
                log.error("Readonly - Invalid attribute: " +attr.getName()+ strError);
            }
            bOk=false;
        }
        this.frmnode.appendChild(appendChild(element));
    }

    /** Scan through org.w3c.dom.Element named 
     * IMG. 
     */
    void visitImg(org.w3c.dom.Element element) 
    { 
        String[] imgAttrs = {"name","src","usemap","value","alt","label","width","height","hspace","vspace","border","accesskey","disabled"};
        this.frmelement = this.outdom.createElement("IMG");
        boolean bOk=false;
        
        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) 
        {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr)attrs.item(i);
            strError="";
            bOk=evalCoreAttributes(attr);
            if(bOk) this.frmelement.setAttribute(attr.getName().toLowerCase(), attr.getValue());
            if(!bOk)
            {
                for(int j=0; j < imgAttrs.length; j++)
                {
                    if (attr.getName().toLowerCase().equals(imgAttrs[j])) 
                    {
                        if(imgAttrs[j].equals("name") || imgAttrs[j].equals("value") || imgAttrs[j].equals("alt") || imgAttrs[j].equals("label"))
                        {
                            if(admResUtils.isCDATA(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The required value is a basic data type CDATA)";
                        }                            
                        else if(imgAttrs[j].equals("width") || imgAttrs[j].equals("height") || imgAttrs[j].equals("hspace") || imgAttrs[j].equals("vspace") || imgAttrs[j].equals("border"))
                        {
                            if(admResUtils.isNumber(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The required value is a data type NUMBER)";
                        }
                        else if(imgAttrs[j].equals("disabled"))
                        {
                            if(admResUtils.isBoolean(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The valid values are TRUE or FALSE)";
                        }
                        else if(imgAttrs[j].equals("accesskey"))
                        {
                            if(admResUtils.isCharacter(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The valid value a single character.)";
                        }
                        else bOk=true;
                        
                        if(bOk) this.frmelement.setAttribute(imgAttrs[j], attr.getValue());
                        break;
                    }
                }
            }
            if(!bOk) 
            {
                bError=true;
                log.error("Img - Invalid attribute: " +attr.getName()+ strError);
            }
            bOk=false;
        }
        this.frmnode.appendChild(appendChild(element));
    }
    
    /** Scan through org.w3c.dom.Element named
     * INPUT. 
     */
    void visitInput(org.w3c.dom.Element element) 
    {
        String[] inputAttrs = new String[20];
        inputAttrs[0]="type";
        inputAttrs[1]="name";
        inputAttrs[2]="value";
        inputAttrs[3]="label";
        
        org.w3c.dom.Attr attrType=element.getAttributeNode("type");
        String strType="";
        if(attrType.getValue()!=null) strType=attrType.getValue().toLowerCase();
        if(strType.equals("text") || strType.equals("password")) 
        {
            inputAttrs[4]="accesskey";
            inputAttrs[5]="aling";
            inputAttrs[6]="disabled";
            inputAttrs[7]="size";
            inputAttrs[8]="maxlength";
            inputAttrs[9]="height";
            inputAttrs[10]="readonly";
            inputAttrs[11]="autocomplete";            
        }
        else if(strType.equals("checkbox") || strType.equals("radio")) 
        {
            inputAttrs[4]="accesskey";
            inputAttrs[5]="aling";
            inputAttrs[6]="disabled";
            inputAttrs[7]="readonly";
            inputAttrs[8]="checked"; 
        }            
        else if(strType.equals("file")) 
        {
            inputAttrs[4]="accesskey";
            inputAttrs[5]="aling";
            inputAttrs[6]="disabled";
            inputAttrs[7]="size";
            inputAttrs[8]="maxlength";
            inputAttrs[9]="readonly";
            inputAttrs[10]="accept";  
        }            
        else if(strType.equals("image")) 
        {
            inputAttrs[4]="accesskey";
            inputAttrs[5]="aling";
            inputAttrs[6]="disabled";
            inputAttrs[7]="usemap";
            inputAttrs[8]="src";
            inputAttrs[9]="alt";
            inputAttrs[10]="border";
            inputAttrs[11]="width";
            inputAttrs[12]="height";
            inputAttrs[13]="hspace";
            inputAttrs[14]="vspace";
        }            
        else if(strType.equals("button") || strType.equals("submit") || strType.equals("reset")) 
        {
            inputAttrs[4]="accesskey";
            inputAttrs[5]="aling";
            inputAttrs[6]="disabled";
            inputAttrs[7]="width";
            inputAttrs[8]="height";
        }            
        this.frmelement = this.outdom.createElement("INPUT");
        boolean bOk=false;
        
        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) 
        {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr)attrs.item(i);
            strError="";
            bOk=evalCoreAttributes(attr);
            if(bOk) this.frmelement.setAttribute(attr.getName().toLowerCase(), attr.getValue());
            if(!bOk && (strType.equals("text") || strType.equals("password") || strType.equals("file")))
            {
                bOk=evalWBScriptAttributes(attr);
                if(bOk) this.frmelement.setAttribute(attr.getName().toLowerCase(), attr.getValue());
            }
            if(!bOk)
            {
                for(int j=0; j < inputAttrs.length; j++)
                {
                    if (attr.getName().toLowerCase().equals(inputAttrs[j])) 
                    {
                        if(inputAttrs[j].equals("type"))
                        {
                            if(admResUtils.isInputType(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The valid values are TEXT, PASSWORD, HIDDEN, CHECKBOX, RADIO, FILE, IMAGE, BUTTON, SUBMIT or RESET)";
                        }
                        else if(inputAttrs[j].equals("name") || inputAttrs[j].equals("value") || inputAttrs[j].equals("alt") || inputAttrs[j].equals("label") || inputAttrs[j].equals("aling"))
                        {
                            if(admResUtils.isCDATA(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The required value is a basic data type CADTA)";
                        }                        
                        else if(inputAttrs[j].equals("checked") || inputAttrs[j].equals("disabled") || inputAttrs[j].equals("readonly") || inputAttrs[j].equals("autocomplete"))
                        {
                            if(admResUtils.isBoolean(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The valid values are TRUE or FALSE)";
                        }                               
                        else if(inputAttrs[j].equals("size") || inputAttrs[j].equals("maxlength") || inputAttrs[j].equals("width") || inputAttrs[j].equals("height") || inputAttrs[j].equals("border") || inputAttrs[j].equals("hspace") || inputAttrs[j].equals("vspace"))
                        {
                            if(admResUtils.isNumber(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The required value is a data type NUMBER)";                            
                        } 
                        else if(inputAttrs[j].equals("accesskey"))
                        {
                            if(admResUtils.isCharacter(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The valid value is a single character)";
                        }                            
                        else bOk=true;

                        if(bOk) this.frmelement.setAttribute(inputAttrs[j], attr.getValue());
                        break;
                    }
                }
            }
            if(!bOk) 
            {
                bError=true;
                log.error("Input type "+strType+" - Invalid attribute: " +attr.getName()+ strError);
            }
            bOk=false;
        }
        this.frmnode.appendChild(appendChild(element));
    }

    /** Scan through org.w3c.dom.Element named 
     * SELECT. 
     */
    void visitSelect(org.w3c.dom.Element element) 
    {
        String[] selectAttrs = {"name","size","multiple","disabled","label","width","align","accesskey"};
        this.frmelement = this.outdom.createElement("SELECT");
        boolean bOk=false;
        
        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) 
        {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr)attrs.item(i);
            strError="";
            bOk=evalCoreAttributes(attr);
            if(bOk) this.frmelement.setAttribute(attr.getName().toLowerCase(), attr.getValue());
            if(!bOk)
            {
                for(int j=0; j < selectAttrs.length; j++)
                {
                    if (attr.getName().toLowerCase().equals(selectAttrs[j])) 
                    {
                        if(selectAttrs[j].equals("name") || selectAttrs[j].equals("label") || selectAttrs[j].equals("aling"))
                        {
                            if(admResUtils.isCDATA(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The required value is a basic data type CDATA)";
                        }                            
                        else if(selectAttrs[j].equals("size") || selectAttrs[j].equals("width"))
                        {
                            if(admResUtils.isNumber(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The required value is a data type NUMBER)";
                        }                            
                        else if(selectAttrs[j].equals("multiple") || selectAttrs[j].equals("disabled"))
                        {
                            if(admResUtils.isBoolean(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The valid values are TRUE or FALSE)";
                        }
                        else if(selectAttrs[j].equals("accesskey"))
                        {
                            if(admResUtils.isCharacter(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The valid value is a single character)";
                        }                            
                        else bOk=true;
                        
                        if(bOk) this.frmelement.setAttribute(selectAttrs[j], attr.getValue());
                        break;
                    }
                }
            }
            if(!bOk) 
            {
                bError=true;
                log.error("Select - Invalid attribute: " +attr.getName()+ strError);
            }
            bOk=false;
        }
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) 
        {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) 
            {
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
                    // ((org.w3c.dom.CDATASection)node).getData();
                    break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                    org.w3c.dom.Element elementNode = (org.w3c.dom.Element)node;
                    if (elementNode.getTagName().toUpperCase().equals("OPTION")) 
                        visitOption(elementNode);
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
                    // ((org.w3c.dom.ProcessingInstruction)node).getTarget();
                    // ((org.w3c.dom.ProcessingInstruction)node).getData();
                    break;
            }
        }
        this.frmnode.appendChild(this.frmelement);
    }

    /** Scan through org.w3c.dom.Element named 
     * OPTION. 
     */
    void visitOption(org.w3c.dom.Element element) 
    { 
        String[] optionAttrs = {"selected","disabled","label","value","name"};
        org.w3c.dom.Element childElement = this.outdom.createElement("OPTION");
        boolean bOk=false;
        
        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) 
        {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr)attrs.item(i);
            strError="";
            bOk=evalCoreAttributes(attr);
            if(bOk) childElement.setAttribute(attr.getName().toLowerCase(), attr.getValue());
            if(!bOk)
            {
                for(int j=0; j < optionAttrs.length; j++)
                {
                    if (attr.getName().toLowerCase().equals(optionAttrs[j])) 
                    {
                        if(optionAttrs[j].equals("label") || optionAttrs[j].equals("value") || optionAttrs[j].equals("name"))
                        {
                            if(admResUtils.isCDATA(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The required value is a basic data type CDATA)";
                        }                            
                        else if(optionAttrs[j].equals("selected") || optionAttrs[j].equals("disabled"))
                        {
                            if(admResUtils.isBoolean(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The valid values are TRUE or FALSE)";
                        }
                        else bOk=true;

                        if(bOk) childElement.setAttribute(optionAttrs[j], attr.getValue());
                        break;
                    }
                }
            }
            if(!bOk) 
            {
                bError=true;
                log.error("Option - Invalid attribute: " +attr.getName()+ strError);
            }
            bOk=false;
        }
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) 
        {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) 
            {
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
                    // ((org.w3c.dom.CDATASection)node).getData();
                    break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                    //org.w3c.dom.Element nodeElement = (org.w3c.dom.Element)node;
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
                    // ((org.w3c.dom.ProcessingInstruction)node).getTarget();
                    // ((org.w3c.dom.ProcessingInstruction)node).getData();
                    break;
            }
        }
        this.frmelement.appendChild(childElement);
    }
    
    /** Scan through org.w3c.dom.Element named 
     * TEXTAREA. 
     */
    void visitTextarea(org.w3c.dom.Element element) 
    {
        String[] textareaAttrs = {"name","rows","cols","disabled","readonly","accesskey","label","value","width","height","wrap"};
        this.frmelement = this.outdom.createElement("TEXTAREA");
        boolean bOk=false;
        
        org.w3c.dom.NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) 
        {
            org.w3c.dom.Attr attr = (org.w3c.dom.Attr)attrs.item(i);
            strError="";
            bOk=evalCoreAttributes(attr);
            if(bOk) this.frmelement.setAttribute(attr.getName().toLowerCase(), attr.getValue());
            if(!bOk)
            {
                bOk=evalWBScriptAttributes(attr);
                if(bOk) this.frmelement.setAttribute(attr.getName().toLowerCase(), attr.getValue());
            }
            if(!bOk)
            {
                for(int j=0; j < textareaAttrs.length; j++)
                {
                    if (attr.getName().toLowerCase().equals(textareaAttrs[j])) 
                    {
                        if(textareaAttrs[j].equals("name") || textareaAttrs[j].equals("label") || textareaAttrs[j].equals("value"))
                        {
                            if(admResUtils.isCDATA(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The required value is a basic data type CDATA)";
                        }                            
                        else if(textareaAttrs[j].equals("rows") || textareaAttrs[j].equals("cols") || textareaAttrs[j].equals("width") || textareaAttrs[j].equals("height"))
                        {
                            if(admResUtils.isNumber(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The required value is a data type NUMBER)";
                        }                            
                        else if(textareaAttrs[j].equals("disabled") || textareaAttrs[j].equals("readonly") || textareaAttrs[j].equals("wrap"))
                        {
                            if(admResUtils.isBoolean(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The valid values are TRUE or FALSE)";
                        }                         
                        else if(textareaAttrs[j].equals("accesskey"))
                        {
                            if(admResUtils.isCharacter(attr.getValue())) bOk=true;
                            else strError=" or value: " +attr.getValue()+ " (The valid value is a single character)";
                        }                         
                        else bOk=true;
                        
                        if(bOk) this.frmelement.setAttribute(textareaAttrs[j], attr.getValue());
                        break;
                    }
                }
            }
            if(!bOk) 
            {
                bError=true;
                log.error("Textarea - Invalid attribute: " +attr.getName()+ strError);
            }
            bOk=false;
        }
        this.frmnode.appendChild(appendChild(element));
    }

    boolean evalCoreAttributes(org.w3c.dom.Attr attr)
    {
        String[] coreAttrs = {"class","style","moreattr"};
        boolean bOk=false;
        
        for(int j=0; j < coreAttrs.length; j++)
        {
            if (attr.getName().toLowerCase().equals(coreAttrs[j])) 
            {
                if(coreAttrs[j].equals("class") || coreAttrs[j].equals("moreattr"))
                {
                    if(admResUtils.isCDATA(attr.getValue())) bOk=true;
                    else strError=" or value: " +attr.getValue()+ " (The required value is a basic data type CDATA)";
                }
                else bOk=true;
                break;
            }
        }
        return bOk;
    }

    boolean evalWBScriptAttributes(org.w3c.dom.Attr attr)
    {
        String[] wbscriptAttrs = {"jsrequired","minsize","jsvaltype","jsvalidchars","isvalidchars","isshowchars"};
        boolean bOk=false;
        
        for(int j=0; j < wbscriptAttrs.length; j++)
        {
            if (attr.getName().toLowerCase().equals(wbscriptAttrs[j])) 
            {
                if(wbscriptAttrs[j].equals("jsvalidchars"))
                {
                    if(admResUtils.isCDATA(attr.getValue())) bOk=true;
                    else strError=" or value: " +attr.getValue()+ " (The required value is a basic data type CDATA)";
                }
                else if(wbscriptAttrs[j].equals("minsize"))
                {
                    if(admResUtils.isNumber(attr.getValue())) bOk=true;
                    else strError=" or value: " +attr.getValue()+ " (The required value is a data type NUMBER)";
                }
                else if(wbscriptAttrs[j].equals("jsrequired") || wbscriptAttrs[j].equals("isvalidchars") || wbscriptAttrs[j].equals("isshowchars")) 
                {
                    if(admResUtils.isBoolean(attr.getValue())) bOk=true;
                    else strError=" or value: " +attr.getValue()+ " (The valid values are TRUE or FALSE)";
                }
                else if(wbscriptAttrs[j].equals("jsvaltype")) 
                {
                    if(admResUtils.isJsValType(attr.getValue())) bOk=true;
                    else strError=" or value: " +attr.getValue()+ " (The valid values are js_alphabetic, js_numbers or js_email)";
                }                
                else bOk=true;
                break;
            }
        }
        return bOk;
    }    
    
    org.w3c.dom.Element appendChild(org.w3c.dom.Element element)
    {
        org.w3c.dom.NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) 
        {
            org.w3c.dom.Node node = nodes.item(i);
            switch (node.getNodeType()) 
            {
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
                    org.w3c.dom.CDATASection cdata = this.outdom.createCDATASection(((org.w3c.dom.CDATASection)node).getData());
                    this.frmelement.appendChild(cdata);
                    break;
                case org.w3c.dom.Node.ELEMENT_NODE:
                    //org.w3c.dom.Element elementNode = (org.w3c.dom.Element)node;
                    break;
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
                    // ((org.w3c.dom.ProcessingInstruction)node).getTarget();
                    // ((org.w3c.dom.ProcessingInstruction)node).getData();
                    break;
                case org.w3c.dom.Node.TEXT_NODE:
                    org.w3c.dom.Text text = this.outdom.createTextNode(((org.w3c.dom.Text)node).getData());
                    this.frmelement.appendChild(text);
                    break;
            }
        }
        return this.frmelement;
    }
}
