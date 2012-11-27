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
package org.semanticwb.portal.admin.admresources.util;

import java.util.HashMap;

import org.w3c.dom.*;
import org.w3c.dom.Document;

import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;

// TODO: Auto-generated Javadoc
/**
 * The Class StylerDomParser.
 */
public class StylerDomParser {
    
    /** The dom. */
    private Document dom;
    
    /** The serial. */
    private int serial;
    
    /** The cp. */
    private int cp;
    
    /** The tabs. */
    private HashMap tabs;
    
    /** The client. */
    private Resource client;

    /** The fstel. */
    private boolean fstel;

    /**
     * Instantiates a new styler dom parser.
     * 
     * @param xml the xml
     * @param client the client
     * @throws NullPointerException the null pointer exception
     */
    public StylerDomParser(String xml, Resource client) throws NullPointerException {
        parseXml(xml);
        this.client = client;
    }

    /**
     * Instantiates a new styler dom parser.
     * 
     * @param dom the dom
     * @param client the client
     * @throws NullPointerException the null pointer exception
     */
    public StylerDomParser(Document dom, Resource client) throws NullPointerException {
        this.dom = dom;
        this.client = client;
    }

    /**
     * Parses the xml.
     * 
     * @param xml the xml
     * @throws NullPointerException the null pointer exception
     */
    private void parseXml(String xml) throws NullPointerException {
        dom = SWBUtils.XML.xmlToDom(xml);
        if(dom==null) {
            throw new NullPointerException("document null");
        }
    }

    /**
     * Parses the.
     * 
     * @return the string
     */
    public String parse() {
        tabs = new HashMap();
        StringBuilder script = new StringBuilder();
        script.append(processStartDocument());
        script.append(processDocument());
        script.append(processEndDocument());
        return script.toString();
    }

    /**
     * Process document.
     * 
     * @return the string
     */
    private String processDocument() {
        StringBuilder script = new StringBuilder();
        Element docEle = dom.getDocumentElement();
        NodeList nl = docEle.getElementsByTagName("class");
        for(int i=0; i<nl.getLength(); i++) {
            Element el = (Element)nl.item(i);
            script.append(processClass(el));
        }
        return script.toString();
    }

    /**
     * Process class.
     * 
     * @param sel the sel
     * @return the string
     */
    private String processClass(Element sel) {
        StringBuilder script = new StringBuilder();
        String name = sel.getAttribute("name");
        HashMap<String,String> props = processProperties(sel);

        if(name != null) {
            if(!name.startsWith(".")) name="."+name;
            if(!fstel) {
                script.append("<script type=\"text/javascript\">\n");
                script.append("dojo.addOnLoad( function(){\n");
                script.append(" dojo.byId('stel').value='"+name+"';\n");
                script.append("});\n");
                script.append("</script>\n");
                fstel = true;
            }
            String v=null;
            script.append("<div dojoType=\"dijit.layout.ContentPane\" title=\""+name+"\" id=\""+name+"\" style=\"position:relative;\" >");
            script.append("<table border=\"0\" width=\"100%\" bgcolor=\"#F4F4DD\" >");
            script.append("<tr><td width=\"35%\"></td><td width=\"65%\"></td></tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">border-style :</td>");
            script.append("<td>");
            if(props.containsKey("border-style")) {
                v=props.get("border-style");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"border-style\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"border-style\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"\" />");
            }
            script.append("<div dojoType=\"dijit.Tooltip\" connectId=\"ss_"+serial+"\" position=\"below\">");
            script.append("<ul><li>none</li><li>solid</li><li>double</li><li>groove</li><li>ridge</li><li>inset</li><li>outset</li><li>dashed</li><li>dotted</li></ul>");
            script.append("</div>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">border-width :</td>");
            script.append("<td>");
            if(props.containsKey("border-width")) {
                v=props.get("border-width");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"border-width\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"border-width\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"\" />");
            }
            script.append("<div dojoType=\"dijit.Tooltip\" connectId=\"ss_"+serial+"\">");
            script.append("<ul><li>px</li><li>pt</li><li>em</li><li>pc</li><li>ridge</li><li>%</li></ul>");
            script.append("</div>");
            script.append("</td>");
            script.append("</tr>");
            
            script.append("<tr>");
            script.append("<td class=\"label\">border-color :</td>");
            script.append("<td>");
            if(props.containsKey("border-color")) {
                v=props.get("border-color");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"stl_"+cp+"\" name=\"border-color\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"stl_"+cp+"\" name=\"border-color\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"transparent\" />");
            }
            script.append("<img src=\"/swbadmin/images/color_palette.png\" onclick=\"toggle('cp_"+cp+"')\" style=\"cursor:pointer;\" />");
            script.append("<div id=\"cp_"+(cp)+"\"></div>");
            cp++;
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td colspan=\"2\" height=\"1\" bgcolor=\"#CCCCCC\">");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">font-family :</td>");
            script.append("<td>");
            if(props.containsKey("font-family")) {
                v=props.get("font-family");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"font-family\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"font-family\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"\" />");
            }
            script.append("<div dojoType=\"dijit.Tooltip\" connectId=\"ss_"+serial+"\">");
            script.append("<ul><li>Arial</li><li>Times New Roman</li><li>Courier New</li><li>Georgia</li><li>Verdana</li><li>Geneva</li></ul>");
            script.append("</div>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">font-size :</td>");
            script.append("<td>");
            if(props.containsKey("font-size")) {
                v=props.get("font-size");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"font-size\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"font-size\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"\" />");
            }
            script.append("<div dojoType=\"dijit.Tooltip\" connectId=\"ss_"+serial+"\">");
            script.append("<ul><li>9pt</li><li>10pt</li><li>12pt</li><li>xx-small</li><li>x-small</li><li>small</li><li>large</li><li>x-large</li><li>xx-large</li></ul>");
            script.append("</div>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">font-style :</td>");
            script.append("<td>");
            if(props.containsKey("font-style")) {
                v=props.get("font-style");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"font-style\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"font-style\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"\" />");
            }
            script.append("<div dojoType=\"dijit.Tooltip\" connectId=\"ss_"+serial+"\">");
            script.append("<ul><li>normal</li><li>italic</li><li>oblique</li></ul>");
            script.append("</div>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">font-weight :</td>");
            script.append("<td>");
            if(props.containsKey("font-weight")) {
                v=props.get("font-weight");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"font-weight\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"font-weight\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"\" />");
            }
            script.append("<div dojoType=\"dijit.Tooltip\" connectId=\"ss_"+serial+"\">");
            script.append("<ul><li>normal</li><li>bold</li><li>600</li><li>900</li></ul>");
            script.append("</div>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">font-variant :</td>");
            script.append("<td>");
            if(props.containsKey("font-variant")) {
                v=props.get("font-variant");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"font-variant\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"font-variant\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"\" />");
            }
            script.append("<div dojoType=\"dijit.Tooltip\" connectId=\"ss_"+serial+"\">");
            script.append("<ul><li>normal</li><li>small-caps</li><li>oblique</li></ul>");
            script.append("</div>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">vertical-align :</td>");
            script.append("<td>");
            if(props.containsKey("vertical-align")) {
                v=props.get("vertical-align");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"vertical-align\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"vertical-align\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"\" />");
            }
            script.append("<div dojoType=\"dijit.Tooltip\" connectId=\"ss_"+serial+"\">");
            script.append("<ul><li>baseline</li><li>top</li><li>middle</li><li>bottom</li></ul>");
            script.append("</div>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">text-align :</td>");
            script.append("<td>");
            if(props.containsKey("text-align")) {
                v=props.get("text-align");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"text-align\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"text-align\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"\" />");
            }
            script.append("<div dojoType=\"dijit.Tooltip\" connectId=\"ss_"+serial+"\">");
            script.append("<ul><li>left</li><li>right</li><li>center</li><li>justify</li></ul>");
            script.append("</div>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">text-decoration :</td>");
            script.append("<td>");
            if(props.containsKey("text-decoration")) {
                v=props.get("text-decoration");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"text-decoration\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"text-decoration\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"\" />");
            }
            script.append("<div dojoType=\"dijit.Tooltip\" connectId=\"ss_"+serial+"\">");
            script.append("<ul><li>underline</li><li>overline</li><li>line-through</li><li>blink</li><li>none</li></ul>");
            script.append("</div>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">case :</td>");
            script.append("<td>");
            if(props.containsKey("case")) {
                v=props.get("case");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"case\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"case\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"\" />");
            }
            script.append("<div dojoType=\"dijit.Tooltip\" connectId=\"ss_"+serial+"\">");
            script.append("<ul><li>capitalize</li><li>uppercase</li><li>lowercase</li><li>blink</li><li>none</li></ul>");
            script.append("</div>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">line-height :</td>");
            script.append("<td>");
            if(props.containsKey("line-height")) {
                v=props.get("line-height");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"line-height\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"line-height\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"\" />");
            }
            script.append("<div dojoType=\"dijit.Tooltip\" connectId=\"ss_"+serial+"\">");
            script.append("<ul><li>px</li><li>pt</li><li>em</li><li>pc</li><li>%</li></ul>");
            script.append("</div>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">color :</td>");
            script.append("<td>");
            if(props.containsKey("color")) {
                v=props.get("color");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"stl_"+cp+"\" name=\"color\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"stl_"+cp+"\" name=\"color\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"\" />");
            }
            script.append("<img src=\"/swbadmin/images/color_palette.png\" onclick=\"toggle('cp_"+cp+"')\" style=\"cursor:pointer;\" />");
            script.append("<div id=\"cp_"+(cp)+"\"></div>");
            cp++;
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td colspan=\"2\" height=\"1\" bgcolor=\"#CCCCCC\">");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">position :</td>");
            script.append("<td>");
            if(props.containsKey("position")) {
                v=props.get("position");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"position\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"position\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"\" />");
            }
            script.append("<div dojoType=\"dijit.Tooltip\" connectId=\"ss_"+serial+"\">");
            script.append("<ul><li>absolute</li><li>fixed</li><li>relative</li><li>static</li></ul>");
            script.append("</div>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">overflow :</td>");
            script.append("<td>");
            if(props.containsKey("overflow")) {
                v=props.get("overflow");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"overflow\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"overflow\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"\" />");
            }
            script.append("<div dojoType=\"dijit.Tooltip\" connectId=\"ss_"+serial+"\">");
            script.append("<ul><li>visible</li><li>hidden</li><li>scroll</li><li>auto</li></ul>");
            script.append("</div>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td colspan=\"2\" height=\"1\" bgcolor=\"#CCCCCC\">");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">width :</td>");
            script.append("<td>");
            if(props.containsKey("width")) {
                v=props.get("width");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"width\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"width\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"\" />");
            }
            script.append("<div dojoType=\"dijit.Tooltip\" connectId=\"ss_"+serial+"\">");
            script.append("<ul><li>px</li><li>pt</li><li>em</li><li>pc</li><li>%</li></ul>");
            script.append("</div>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">height :</td>");
            script.append("<td>");
            if(props.containsKey("height")) {
                v=props.get("height");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"height\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"height\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"\" />");
            }
            script.append("<div dojoType=\"dijit.Tooltip\" connectId=\"ss_"+serial+"\">");
            script.append("<ul><li>px</li><li>pt</li><li>em</li><li>pc</li><li>%</li></ul>");
            script.append("</div>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">float :</td>");
            script.append("<td>");
            if(props.containsKey("float")) {
                v=props.get("float");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"float\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"float\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"\" />");
            }
            script.append("<div dojoType=\"dijit.Tooltip\" connectId=\"ss_"+serial+"\">");
            script.append("<ul><li>left</li><li>right</li><li>none</li></ul>");
            script.append("</div>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">padding :</td>");
            script.append("<td>");
            if(props.containsKey("padding")) {
                v=props.get("padding");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"padding\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"padding\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"\" />");
            }
            script.append("<div dojoType=\"dijit.Tooltip\" connectId=\"ss_"+serial+"\">");
            script.append("<ul><li>px</li><li>pt</li><li>em</li><li>pc</li><li>%</li></ul>");
            script.append("</div>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">margin :</td>");
            script.append("<td>");
            if(props.containsKey("margin")) {
                v=props.get("margin");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"margin\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"margin\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"\" />");
            }
            script.append("<div dojoType=\"dijit.Tooltip\" connectId=\"ss_"+serial+"\">");
            script.append("<ul><li>px</li><li>pt</li><li>em</li><li>pc</li><li>%</li></ul>");
            script.append("</div>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td colspan=\"2\" height=\"1\" bgcolor=\"#CCCCCC\">");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">bg-color :</td>");
            script.append("<td>");
            if(props.containsKey("background-color")) {
                v=props.get("background-color");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"stl_"+cp+"\" name=\"background-color\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"stl_"+cp+"\" name=\"background-color\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"transparent\" />");
            }
            script.append("<img src=\"/swbadmin/images/color_palette.png\" onclick=\"toggle('cp_"+cp+"')\" style=\"cursor:pointer;\" />");
            script.append("<div id=\"cp_"+(cp)+"\"></div>");
            cp++;
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td colspan=\"2\" height=\"1\" bgcolor=\"#CCCCCC\">");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">cursor :</td>");
            script.append("<td>");
            if(props.containsKey("cursor")) {
                v=props.get("cursor");
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"cursor\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\""+v+"\" />");
            }else {
                script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"ss_"+(++serial)+"\" name=\"cursor\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" value=\"\" />");
            }
            script.append("<div dojoType=\"dijit.Tooltip\" connectId=\"ss_"+serial+"\" position=\"above\">");
            script.append("<ul><li>default</li><li>pointer</li><li>text</li></ul>");
            script.append("</div>");
            script.append("</td>");
            script.append("</tr>");

            script.append("</table>");
            script.append("</div>");

            tabs.put(name, props);
        }
        return script.toString();
    }

    /**
     * Process start document.
     * 
     * @return the string
     */
    private String processStartDocument() {
        StringBuilder script = new StringBuilder();

        script.append("<script type=\"text/javascript\">\n");
        script.append(" dojo.require(\"dijit.layout.TabContainer\");\n");
        script.append(" dojo.require(\"dijit.layout.ContentPane\");\n");
        script.append(" dojo.require(\"dijit.form.TextBox\");\n");
        script.append(" dojo.require(\"dijit.form.Form\");\n");
        script.append(" dojo.require(\"dijit.Tooltip\");\n");        
        script.append(" dojo.require(\"dijit.ColorPalette\");\n");
        script.append(" dojo.require(\"dojo.fx\");\n");

        script.append("function expande(oId) {\n");
        script.append("    var anim1 = dojo.fx.wipeIn( {node:oId, duration:500 });\n");
        script.append("    var anim2 = dojo.fadeIn({node:oId, duration:500});\n");
        script.append("    dojo.fx.combine([anim1,anim2]).play();\n");
        script.append("}");

        script.append("function collapse(oId) {\n");
        script.append("        var anim1 = dojo.fx.wipeOut( {node:oId, duration:500 });\n");
        script.append("        var anim2 = dojo.fadeOut({node:oId, duration:600});\n");
        script.append("        dojo.fx.combine([anim1, anim2]).play();\n");
        script.append("}\n");

        script.append("function toggle(oId) {\n");
        script.append("        var o = dojo.byId(oId);\n");
        script.append("        if(o.style.display=='block' || o.style.display==''){\n");
        script.append("                collapse(oId);\n");
        script.append("        }else {\n");
        script.append("                expande(oId);\n");
        script.append("    }\n");
        script.append("}\n");

        script.append("dojo.addOnLoad( function(){\n");
        script.append("    var tc = dijit.byId('tc_"+client.getId()+"');\n");
        script.append("    dojo.connect(tc, 'selectChild', function(child){ dojo.byId('stel').value=child.id; });\n");
        script.append("});\n");

        script.append("</script>\n");

        script.append("<input id=\"stel\" type=\"hidden\" />");
        script.append("<div dojoType=\"dijit.layout.TabContainer\" id=\"tc_"+client.getId()+"\" style=\"position:relative;margin-left:15px; width:480px;height:250px;\" tabStrip=\"true\" tabPosition=\"right-h\" >\n");
        return script.toString();
    }

    /**
     * Process end document.
     * 
     * @return the string
     */
    private String processEndDocument() {
        StringBuilder script = new StringBuilder();
        script.append("</div>\n");

        script.append("<script type=\"text/javascript\">\n");
        script.append("dojo.addOnLoad(function(){\n");
        for(long i=0L; i<cp; i++) {
            script.append("        var plt_"+client.getId()+"_"+i+" = new dijit.ColorPalette( {palette:'7x10', onChange: function(val){\n");
            script.append("                dojo.byId('stl_"+i+"').value = val;\n");
            script.append("                collapse('cp_"+i+"');\n");
            script.append("                sendData(dojo.byId('stel').value, dojo.byId('stl_"+i+"').name, escape(val)); ");
            script.append("        } }, 'cp_"+i+"' );\n");
            script.append("        dojo.byId('cp_"+i+"').style.display='none';\n");
        }
        script.append("});\n");
        script.append("</script>\n");
        return script.toString();
    }

    /**
     * Gets the tabs.
     * 
     * @return the tabs
     */
    public HashMap getTabs() {
        return tabs;
    }

    /**
     * The main method.
     * 
     * @param argv the arguments
     */
    public static void main(String argv[]) {

    }

    /**
     * Process properties.
     * 
     * @param sel the sel
     * @return the hash map
     */
    private HashMap<String,String> processProperties(Element sel) {
        HashMap<String,String> attrs = new HashMap();
        NodeList nl = sel.getElementsByTagName("property");
        if(nl!=null && nl.getLength()>0) {
            for(int i=0; i<nl.getLength(); i++) {
                Element e = (Element)nl.item(i);
                attrs.put(e.getAttribute("name"), e.getAttribute("value").replaceAll("\\s", ""));
            }
        }
        return attrs;
    }
}