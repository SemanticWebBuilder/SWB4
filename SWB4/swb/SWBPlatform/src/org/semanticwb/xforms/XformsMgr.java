/*
 * XformsMgr.java
 *
 * Created on 1 de julio de 2008, 05:23 PM
 */

package org.semanticwb.xforms;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.semanticwb.xforms.lib.WBXformsContainer;
import com.infotec.appfw.util.AFUtils;
import java.util.*;
import org.semanticwb.xforms.drop.RDFElement;
import org.semanticwb.xforms.ui.*;
import org.semanticwb.xforms.ui.container.*;

/**
 *
 * @author  jorge.jimenez
 */
public class XformsMgr extends WBXformsContainer {
    
    ArrayList rdfElements;
    Object RDFElement;
    String xmlInit;
    HashMap instanceElements;
    
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
            dom=com.infotec.appfw.util.AFUtils.getInstance().getNewDocument();
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
            add2Node(modelChild,AFUtils.getInstance().XmltoDom(xmlBinds));
            
            Element bodyChild=dom.createElement("body");
            root.appendChild(bodyChild);
            
            xmlElements="<data>"+xmlElements+"</data>";
            
            add2Node(bodyChild,AFUtils.getInstance().XmltoDom(xmlElements));
            dom=putPrefix(dom);
        } catch(Exception e) {
            AFUtils.log(e);
        }
        return dom;
    }
    
    private Document putPrefix(Document dom) {
        String xml=AFUtils.getInstance().DomtoXml(dom);
        xml=xml.replaceAll("<html", "<html xmlns=\"http://www.w3.org/1999/xhtml\"");
        xml=xml.replaceAll("id=\"wb-instance\">", "id=\"wb-instance\" xmlns=\"\">");
        xml=xml.replaceAll("<input", "<xforms:input");
        xml=xml.replaceAll("</input>", "</xforms:input>");
        xml=xml.replaceAll("<label", "<xforms:label");
        xml=xml.replaceAll("</label>", "</xforms:label>");
        xml=xml.replaceAll("<alert", "<xforms:alert");
        xml=xml.replaceAll("</alert>", "</xforms:alert>");
        xml=xml.replaceAll("<hint", "<xforms:hint");
        xml=xml.replaceAll("</hint>", "</xforms:hint>");
        xml=xml.replaceAll("<trigger", "<xforms:trigger");
        xml=xml.replaceAll("</trigger>", "</xforms:trigger>");
        xml=xml.replaceAll("<action", "<xforms:action");
        xml=xml.replaceAll("</action>", "</xforms:action>");
        xml=xml.replaceAll("<submit", "<xforms:submit");
        xml=xml.replaceAll("</submit>", "</xforms:submit>");
        xml=xml.replaceAll("<send", "<xforms:send");
        xml=xml.replaceAll("</send>", "</xforms:send>");
        xml=xml.replaceAll("<bind", "<xforms:bind");
        xml=xml.replaceAll("</bind>", "</xforms:bind>");
        xml=xml.replaceAll("<group", "<xforms:group");
        xml=xml.replaceAll("</group>", "</xforms:group>");
        xml=xml.replaceAll("<upload", "<xforms:upload");
        xml=xml.replaceAll("</upload>", "</xforms:upload>");
        xml=xml.replaceAll("<secret", "<xforms:secret");
        xml=xml.replaceAll("</secret>", "</xforms:secret>");
        xml=xml.replaceAll("<select", "<xforms:select");
        xml=xml.replaceAll("</select>", "</xforms:select>");
        xml=xml.replaceAll("</select1>", "</xforms:select1>");
        
        xml=xml.replaceAll("<switch", "<xforms:switch");
        xml=xml.replaceAll("</switch>", "</xforms:switch>");        
        xml=xml.replaceAll("<case", "<xforms:case");
        xml=xml.replaceAll("</case>", "</xforms:case>");
        xml=xml.replaceAll("<trigger", "<xforms:trigger");
        xml=xml.replaceAll("</trigger>", "</xforms:trigger>");
        xml=xml.replaceAll("<action", "<xforms:action");
        xml=xml.replaceAll("</action>", "</xforms:action>");
        
        
        xml=xml.replaceAll("<item", "<xforms:item");
        xml=xml.replaceAll("</item>", "</xforms:item>");
        
        xml=xml.replaceAll("<value", "<xforms:value");
        xml=xml.replaceAll("</value>", "</xforms:value>");
        
        xml=xml.replaceAll("<toggle", "<xforms:toggle");
        xml=xml.replaceAll("</toggle>", "</xforms:toggle>");
        
        //TextArea
        xml=xml.replaceAll("<textarea", "<xforms:textarea");
        xml=xml.replaceAll("</textarea>", "</xforms:textarea>");
        
        xml=xml.replaceAll("<output", "<xforms:output");
        xml=xml.replaceAll("</output>", "</xforms:output>");
        
        dom=AFUtils.getInstance().XmltoDom(xml);
        return dom;
    }
    
    private void add2Node(Node node,Document dom) {
        try {
            NodeList nNodes=dom.getFirstChild().getChildNodes();
            for(int i=0;i<nNodes.getLength();i++) {
                node.appendChild(node.getOwnerDocument().importNode(nNodes.item(i),true));
            }
        }catch(Exception e){
            AFUtils.log(e);
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
            }else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("SUBMIT")) {
                XFButton xfsubmit = new XFButton(rdfElement);
                XFform.add(xfsubmit);
            }
        }
        add(XFform);
    }
    
}
