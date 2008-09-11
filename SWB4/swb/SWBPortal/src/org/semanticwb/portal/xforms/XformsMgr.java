/*
 * XformsMgr.java
 *
 * Created on 1 de julio de 2008, 05:23 PM
 */

package org.semanticwb.portal.xforms;

import org.w3c.dom.*;
import org.semanticwb.xforms.lib.WBXformsContainer;
import java.util.*;
import org.semanticwb.xforms.drop.RDFElement;
import org.semanticwb.xforms.ui.*;
import org.semanticwb.xforms.ui.container.*;
import com.arthurdo.parser.*;
import java.io.ByteArrayInputStream;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericXformsResource;

/**
 *
 * @author  jorge.jimenez
 */
public class XformsMgr extends WBXformsContainer 
{
    private static Logger log = SWBUtils.getLogger(GenericXformsResource.class);
    
    ArrayList rdfElements;
    Object RDFElement;
    String xmlInit;
    HashMap instanceElements;
    protected String head=null;
    
    /** Creates a new instance of XformsMgr */
    public XformsMgr() {
        instanceElements=new HashMap();
        rdfElements=new ArrayList();
    }
    
    public void setObj(ArrayList rdfElements) {
        this.rdfElements=rdfElements;
        createXformDoc();
    }
    
    public Document getXformDoc() {
        String xml=show();
        String xmlBinds=showBinds();
        return finalXmlForm(xml,xmlBinds);
    }
    
    private Document finalXmlForm(String xmlElements,String xmlBinds) {
        Document dom=null;
        try {
            dom=SWBUtils.XML.getNewDocument();
            Element root = null;
            root=dom.createElement("html");
            root.setAttribute("xmlns:xforms","http://www.w3.org/2002/xforms");
            root.setAttribute("xmlns:ev","http://www.w3.org/2001/xml-events");
            root.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
            root.setAttribute("xmlns:xsd","http://www.w3.org/2001/XMLSchema");
            root.setAttribute("xmlns:chiba","http://chiba.sourceforge.net/xforms");
            dom.appendChild(root);
            
            Element headChild=dom.createElement("head");
            root.appendChild(headChild);
            
            Element modelChild=dom.createElement("xforms:model");
            modelChild.setAttribute("id","wb-model");
            headChild.appendChild(modelChild);
            
            add2Node(headChild, SWBUtils.XML.xmlToDom("<script>"+head+"</script>"));            
            
            Element submissionChild=dom.createElement("xforms:submission");
            submissionChild.setAttribute("id","wb-save");
            submissionChild.setAttribute("action","{$wbRedirect}");
            submissionChild.setAttribute("method","post");
            submissionChild.setAttribute("replace","instance");
            modelChild.appendChild(submissionChild);
            
            Element instanceChild=dom.createElement("xforms:instance");
            instanceChild.setAttribute("id","wb-instance");
            instanceChild.setAttribute("xmlns","x");
            //instanceChild.setAttribute("src","{$wbInstance}");
            modelChild.appendChild(instanceChild);
            
            Element dataChild=dom.createElement("data");
            instanceChild.appendChild(dataChild);
            
            createInstanceElements(dom,dataChild);
            
            xmlBinds="<data>"+xmlBinds+"</data>";
            add2Node(modelChild, SWBUtils.XML.xmlToDom(xmlBinds));
            
            Element bodyChild=dom.createElement("body");
            root.appendChild(bodyChild);
            
            xmlElements="<data>"+xmlElements+"</data>";
            
            add2Node(bodyChild, SWBUtils.XML.xmlToDom(xmlElements));
            
            dom=putPrefix2(dom);
        } catch(Exception e) {
            log.error(e);
        }
        return dom;
    }
    
    private Document putPrefix2(Document dom) {
        String xml=SWBUtils.XML.domToXml(dom);
        
        HtmlTag tag = new HtmlTag();
        StringBuffer ret = new StringBuffer();
        try{
            HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new ByteArrayInputStream(xml.getBytes()));
            while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF) {
                int ttype = tok.getTokenType();
                if (ttype == HtmlStreamTokenizer.TT_TAG || ttype == HtmlStreamTokenizer.TT_COMMENT) 
                {
                    if(ttype==HtmlStreamTokenizer.TT_COMMENT && tok.getRawString().equals("<!-- -->")) {
                        continue;
                    }
                    tok.parseTag(tok.getStringValue(), tag);

                    if (tok.getRawString().toLowerCase().startsWith("<!--[if")) {
                        continue;
                    }
                    if ((tag.getTagString().toLowerCase().equals("input")
                    || tag.getTagString().toLowerCase().equals("label")
                    || tag.getTagString().toLowerCase().equals("alert")
                    || tag.getTagString().toLowerCase().equals("hint")
                    || tag.getTagString().toLowerCase().equals("trigger")
                    || tag.getTagString().toLowerCase().equals("action")
                    || tag.getTagString().toLowerCase().equals("submit")
                    || tag.getTagString().toLowerCase().equals("bind")
                    || tag.getTagString().toLowerCase().equals("send")
                    || tag.getTagString().toLowerCase().equals("group")
                    || tag.getTagString().toLowerCase().equals("upload")
                    || tag.getTagString().toLowerCase().equals("secret")
                    || tag.getTagString().toLowerCase().equals("select")
                    || tag.getTagString().toLowerCase().equals("select1")
                    
                    || tag.getTagString().toLowerCase().equals("switch")
                    || tag.getTagString().toLowerCase().equals("case")
                    || tag.getTagString().toLowerCase().equals("trigger")
                    || tag.getTagString().toLowerCase().equals("action"))
                    
                    || tag.getTagString().toLowerCase().equals("item")
                    || tag.getTagString().toLowerCase().equals("value")
                    || tag.getTagString().toLowerCase().equals("toggle")
                    || tag.getTagString().toLowerCase().equals("range")
                    || tag.getTagString().toLowerCase().equals("textarea")
                    || tag.getTagString().toLowerCase().equals("output")
                    && !tok.getRawString().startsWith("<!--"))
                    {
                        if (!tag.isEndTag()) 
                        {
                            ret.append("<xforms:");
                            ret.append(tag.getTagString());
                            ret.append(" ");
                            Enumeration en = tag.getParamNames();
                            String name = "";
                            String value = "";
                            while (en.hasMoreElements())
                            {
                                name = (String) en.nextElement();
                                value = tag.getParam(name);
                                ret.append(name);
                                ret.append("=\"");
                                ret.append(value);
                                ret.append("\" ");
                            }
                            if(tag.isEmpty())
                            {
                                ret.append("/");
                            }
                            ret.append(">");
                        }else{
                            ret.append("</xforms:");
                            ret.append(tag.getTagString());
                            ret.append(">");
                        }
                    }else{
                        ret.append(tok.getRawString());
                    }
                }else if (ttype == HtmlStreamTokenizer.TT_TEXT)
                {
                    ret.append(tok.getRawString());
                }
            }
        }catch(Exception e){
            log.error(e);
        }
        xml=ret.toString().replaceAll("<html", "<html xmlns=\"http://www.w3.org/1999/xhtml\"");
        xml=xml.replaceAll("id=\"wb-instance\">", "id=\"wb-instance\" xmlns=\"\">");
        dom=SWBUtils.XML.xmlToDom(xml);
        return dom;
    }
    
    private void add2Node(Node node,Document dom) {
        try {
            NodeList nNodes=dom.getFirstChild().getChildNodes();
            for(int i=0;i<nNodes.getLength();i++) {
                node.appendChild(node.getOwnerDocument().importNode(nNodes.item(i),true));
            }
        }catch(Exception e){
            log.error(e);
        }
    }
    
    private void createInstanceElements(Document dom,Node dataChild) {
        Iterator itElements=instanceElements.keySet().iterator();
        while(itElements.hasNext()){
            String idElement=(String)itElements.next();
            String idElementValue=(String)instanceElements.get(idElement);
            Element xfEleChild=dom.createElement(idElement);
            if(idElementValue!=null){
                xfEleChild.appendChild(dom.createTextNode(idElementValue));
            }
            dataChild.appendChild(xfEleChild);
        }
    }
    
    private void createXformDoc() {
        XFForm XFform=new XFForm();
        Iterator itElements=rdfElements.iterator();
        while(itElements.hasNext()) {
            RDFElement rdfElement=(RDFElement)itElements.next();
            if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("TEXT")) {
                XFText xftext = new XFText(rdfElement);
                instanceElements.put(xftext.getId(),xftext.getValue());
                XFform.add(xftext);
            }if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("OUTPUT")) {
                XFOutput xfoutput = new XFOutput(rdfElement);
                instanceElements.put(xfoutput.getId(),xfoutput.getValue());
                XFform.add(xfoutput);
            }else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("UPLOAD")) {
                XFUpload xfupload = new XFUpload(rdfElement);
                instanceElements.put(xfupload.getId(),xfupload.getValue());
                XFform.add(xfupload);
            }else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("TEXTAREA")) {
                XFTextArea xftextarea = new XFTextArea(rdfElement);
                instanceElements.put(xftextarea.getId(),xftextarea.getValue());
                XFform.add(xftextarea);
            }else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("SECRET")) {
                XFSecret xfsecret = new XFSecret(rdfElement);
                instanceElements.put(xfsecret.getId(),xfsecret.getValue());
                XFform.add(xfsecret);
            }else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("GROUP")) {
                XFGroup xfgroup = new XFGroup(rdfElement,instanceElements);
                XFform.add(xfgroup);
            }else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("SELECT")) {
                XFSelect xfselect = new XFSelect(rdfElement,instanceElements);
                XFform.add(xfselect);
            }else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("SWITCH")) {
                XFSwitch xfswitch = new XFSwitch(rdfElement,instanceElements);
                XFform.add(xfswitch);
            }else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("RANGE")) {
                XFRange xfrange = new XFRange(rdfElement);
                instanceElements.put(xfrange.getId(),xfrange.getValue());
                XFform.add(xfrange);
            }else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("STATICTEXT")) {
                XFStaticText xfstaticText = new XFStaticText(rdfElement);
                if(!xfstaticText.isInhead()){
                    XFform.add(xfstaticText);
                }else{
                    head=xfstaticText.getXml();
                }
           }
            else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("SUBMIT")) {
                XFButton xfsubmit = new XFButton(rdfElement);
                XFform.add(xfsubmit);
            }
        }
        add(XFform);
    }
    
}
