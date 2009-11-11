package org.semanticwb.portal.admin.admresources.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import org.xml.sax.SAXException;

import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;

import org.w3c.dom.Document;

public class StylerDomParser {
    private Document dom;
    private long serial;
    private long cp;
    private HashMap matriz;
    private Resource client;

    StringBuilder script;
    
    public StylerDomParser(String xml, Resource client) throws NullPointerException {
        parseXml(xml);
        this.client = client;
        script = new StringBuilder();
    }

    public StylerDomParser(Document dom, Resource client) throws NullPointerException {
        this.dom = dom;
        this.client = client;
        script = new StringBuilder();
    }

    private void parseXml(String xml) throws NullPointerException {
        dom = SWBUtils.XML.xmlToDom(xml);
        if(dom==null) {
            throw new NullPointerException("document null");
        }
    }

    public String parse() {
        System.out.println("incia parse...");
        processStartDocument();
        parseDocument();
        processEndDocument();
        System.out.println("fin parse");
        return script.toString();
    }

    private void parseDocument() {
        System.out.println("inicia parseDocument...");
        Element docEle = dom.getDocumentElement();
        NodeList nl = docEle.getElementsByTagName("class");
        if(nl!=null && nl.getLength()>0) {
            for(int i=0; i<nl.getLength(); i++) {
                Element el = (Element)nl.item(i);
                processSelector(el);
            }
        }
        System.out.println("fin parseDocument");
    }

    private void processSelector(Element sel) {
        System.out.println("inicia processSelector...");
        String name = sel.getAttribute("name");
        HashMap props = processProperties(sel);
        
        if(name != null) {
            script.append("<div dojoType=\"dijit.layout.ContentPane\" title=\""+name+"\" id=\""+name+"\" >");
            script.append("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"3\" width=\"100%\" bgcolor=\"#E8E8BB\" >");
            //script.append("<tr><td width=\"12%\"></td><td width=\"18%\"></td><td width=\"12%\"></td><td width=\"18%\"></td><td width=\"12%\"></td><td width=\"18%\"></td></tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">border-style :");
            script.append("</td>");
            script.append("<td>");
            script.append("<select dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\"¡Valor inválido!\" id=\"fs_"+(++serial)+"\" name=\"border-style\" style=\"width:100px\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\">");
            script.append("<option value=\"empty\"></option>");
            script.append("<option value=\"none\">none</option>");
            script.append("<option value=\"solid\">solid</option>");
            script.append("<option value=\"double\">double</option>");
            script.append("<option value=\"groove\">groove</option>");
            script.append("<option value=\"ridge\">ridge</option>");
            script.append("<option value=\"inset\">inset</option>");
            script.append("<option value=\"outset\">outset</option>");
            script.append("<option value=\"dashed\">dashed</option>");
            script.append("<option value=\"dotted\">dotted</option>");
            script.append("</select>");
            script.append("</td>");
            script.append("<td class=\"label\">bg-color :</td>");
            script.append("<td>");
            script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"stl_"+cp+"\" name=\"background-color\" value=\"transparent\"  onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\" />");
            script.append("<img src=\"/swbadmin/images/color_palette.png\" onclick=\"toggle('cp_"+cp+"')\" style=\"cursor:pointer;\" />");
            script.append("<div id=\"cp_"+(cp)+"\"></div>");
            cp++;
            script.append("</td>");
            script.append("<td class=\"label\">font-family :</td>");
            script.append("<td>");
            script.append("<select dojoType=\"dijit.form.ComboBox\" id=\"cb_"+(serial++)+"\" name=\"font-family\" style=\"width:120px\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\">");
            script.append("<option value=\"empty\"></option>");
            script.append("<option value=\"Arial\" style=\"font-family:Arial\">Arial</option>");
            script.append("<option value=\"Times New Roman\" style=\"font-family:Times New Roman\">Times New Roman</option>");
            script.append("<option value=\"Courier New\" style=\"font-family:Courier New\">Courier New</option>");
            script.append("<option value=\"Georgia\" style=\"font-family:Georgia\">Georgia</option>");
            script.append("<option value=\"Verdana\" style=\"font-family:Verdana\">Verdana</option>");
            script.append("<option value=\"Geneva\" style=\"font-family:Geneva\">Geneva</option>");
            script.append("</select>");
            script.append("</td>");
            script.append("</tr>");
            script.append("<tr>");
            script.append("<td class=\"label\">border-width :</td>");
            script.append("<td>");
            script.append("<input dojoType=\"dijit.form.NumberSpinner\" smallDelta=\"1\" constraints=\"{min:1,max:250,places:0}\" id=\"is_"+(serial)+"\" name=\"border-width\" style=\"width:60px\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value+dojo.byId('cb_"+serial+"').value);\" />");
            script.append("<select dojoType=\"dijit.form.ComboBox\" id=\"cb_"+(serial)+"\" name=\"border-width\" style=\"width:70px\" style=\"width:100px\" onchange=\"sendData(dojo.byId('stel').value, this.name, dojo.byId('is_"+serial+"').value+this.value);\">");
            script.append("<option value=\"px\">px</option><option value=\"pt\">pt</option><option value=\"em\">em</option><option value=\"pc\">pc</option><option value=\"p100\">&permil;</option>");
            script.append("</select>");
            script.append("</td>");
            script.append("<td class=\"label\">width :</td>");
            script.append("<td>");
            script.append("<input dojoType=\"dijit.form.NumberSpinner\" smallDelta=\"1\" constraints=\"{min:0,places:0}\" id=\"is_"+(++serial)+"\" name=\"width\" style=\"width:60px\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value+dojo.byId('cb_"+serial+"').value);\" />");
            script.append("<select dojoType=\"dijit.form.ComboBox\" id=\"cb_"+(serial)+"\" name=\"width\" style=\"width:70px\" style=\"width:100px\" onchange=\"sendData(dojo.byId('stel').value, this.name, dojo.byId('is_"+serial+"').value+this.value);\">");
            script.append("<option value=\"px\">px</option><option value=\"pt\">pt</option><option value=\"em\">em</option><option value=\"pc\">pc</option><option value=\"p100\">&permil;</option>");
            script.append("</select>");
            script.append("</td>");
            script.append("<td class=\"label\">font-size :</td>");
            script.append("<td>");
            script.append("<select dojoType=\"dijit.form.ComboBox\" id=\"cb_"+(++serial)+"\" name=\"font-size\" style=\"width:100px\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\">");
            script.append("<option value=\"empty\"></option>");
            script.append("<option value=\"9pt\">9pt</option>");
            script.append("<option value=\"10pt\">10pt</option>");
            script.append("<option value=\"12pt\">12pt</option>");
            script.append("<option value=\"14pt\">14pt</option>");
            script.append("<option value=\"16pt\">16pt</option>");
            script.append("<option value=\"xx-small\">xx-small</option>");
            script.append("<option value=\"x-small\">x-small</option>");
            script.append("<option value=\"small\">small</option>");
            script.append("<option value=\"large\">large</option>");
            script.append("<option value=\"x-large\">x-large</option>");
            script.append("<option value=\"xx-large\">xx-large</option>");
            script.append("</select>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">border-color :</td>");
            script.append("<td>");
            script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"stl_"+cp+"\" name=\"border-color\" value=\"transparent\" />");
            script.append("<img src=\"/swbadmin/images/color_palette.png\" onclick=\"toggle('cp_"+cp+"')\" style=\"cursor:pointer;\" />");
            script.append("<div id=\"cp_"+(cp)+"\"></div>");
            cp++;
            script.append("</td>");
            script.append("<td class=\"label\">height :</td>");
            script.append("<td>");
            script.append("<input dojoType=\"dijit.form.NumberSpinner\" smallDelta=\"1\" constraints=\"{min:0,places:0}\" id=\"is_"+(++serial)+"\" name=\"height\" style=\"width:60px\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value+dojo.byId('cb_"+serial+"').value);\" />");
            script.append("<select dojoType=\"dijit.form.ComboBox\" id=\"cb_"+(serial)+"\" name=\"height\" style=\"width:70px\" style=\"width:100px\" onchange=\"sendData(dojo.byId('stel').value, this.name, dojo.byId('is_"+serial+"').value+this.value);\">");
            script.append("<option value=\"px\">px</option><option value=\"pt\">pt</option><option value=\"em\">em</option><option value=\"pc\">pc</option><option value=\"p100\">&permil;</option>");
            script.append("</select>");
            script.append("</td>");
            script.append("<td class=\"label\">font-weight :</td>");
            script.append("<td>");
            script.append("<select dojoType=\"dijit.form.ComboBox\" id=\"cb_"+(++serial)+"\" name=\"font-weight\" style=\"width:100px\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\">");
            script.append("<option value=\"empty\"></option>");
            script.append("<option value=\"normal\">normal</option>");
            script.append("<option value=\"bold\">bold</option>");
            script.append("<option value=\"600\">600</option>");
            script.append("<option value=\"900\">900</option>");
            script.append("</select>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td></td>");
            script.append("<td>");
            script.append("</td>");
            script.append("<td></td>");
            script.append("<td>");
            script.append("</td>");
            script.append("<td class=\"label\">font-style :</td>");
            script.append("<td>");
            script.append("<select dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\"¡Valor inválido!\" id=\"fs_"+(++serial)+"\" name=\"font-style\" style=\"width:100px\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\">");
            script.append("<option value=\"empty\"></option>");
            script.append("<option value=\"normal\">normal</option>");
            script.append("<option value=\"italic\">italic</option>");
            script.append("<option value=\"oblique\">oblique</option>");
            script.append("</select>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">padding :</td>");
            script.append("<td>");
            script.append("<input dojoType=\"dijit.form.NumberSpinner\" smallDelta=\"1\" constraints=\"{min:-250,max:250,places:0}\" id=\"is_"+(++serial)+"\" name=\"padding\" style=\"width:60px\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value+dojo.byId('cb_"+serial+"').value);\" />");
            script.append("<select dojoType=\"dijit.form.ComboBox\" id=\"cb_"+(serial)+"\" name=\"padding\" style=\"width:70px\" onchange=\"sendData(dojo.byId('stel').value, this.name, dojo.byId('is_"+serial+"').value+this.value);\">");
            script.append("<option value=\"px\">px</option><option value=\"pt\">pt</option><option value=\"em\">em</option><option value=\"pc\">pc</option><option value=\"p100\">&permil;</option>");
            script.append("</select>");
            script.append("</td>");
            script.append("<td class=\"label\">overflow :</td>");
            script.append("<td>");
            script.append("<select dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\"¡Valor inválido!\" id=\"fs_"+(++serial)+"\" name=\"overflow\" style=\"width:100px\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\">");
            script.append("<option value=\"empty\"></option><option value=\"visible\">visible</option><option value=\"hidden\">hidden</option><option value=\"scroll\">scroll</option><option value=\"auto\">auto</option>");
            script.append("</select>");
            script.append("</td>");
            script.append("<td class=\"label\">font-variant :</td>");
            script.append("<td>");
            script.append("<select dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\"¡Valor inválido!\" id=\"fs_"+(++serial)+"\" name=\"font-variant\" style=\"width:100px\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\">");
            script.append("<option value=\"empty\"></option>");
            script.append("<option value=\"normal\">normal</option>");
            script.append("<option value=\"small-caps\">small-caps</option>");
            script.append("<option value=\"oblique\">oblique</option>");
            script.append("</select>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">margin :</td>");
            script.append("<td>");
            script.append("<input dojoType=\"dijit.form.NumberSpinner\" smallDelta=\"1\" constraints=\"{min:-250,max:250,places:0}\" id=\"is_"+(++serial)+"\" name=\"margin\" style=\"width:60px\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value+dojo.byId('cb_"+serial+"').value);\" />");
            script.append("<select dojoType=\"dijit.form.ComboBox\" id=\"cb_"+(serial)+"\" name=\"margin\" style=\"width:70px\" onchange=\"sendData(dojo.byId('stel').value, this.name, dojo.byId('is_"+serial+"').value+this.value);\">");
            script.append("<option value=\"px\">px</option><option value=\"pt\">pt</option><option value=\"em\">em</option><option value=\"pc\">pc</option><option value=\"p100\">&permil;</option>");
            script.append("</select>");
            script.append("</td>");
            script.append("<td></td>");
            script.append("<td>");
            script.append("</td>");
            script.append("<td class=\"label\">color :</td>");
            script.append("<td>");
            script.append("<input type=\"text\" style=\"width:100px;\" dojoType=\"dijit.form.TextBox\" id=\"stl_"+cp+"\" name=\"color\" />");
            script.append("<img src=\"/swbadmin/images/color_palette.png\" onclick=\"toggle('cp_"+cp+"')\" style=\"cursor:pointer;\" />");
            script.append("<div id=\"cp_"+(cp)+"\"></div>");
            cp++;
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td></td>");
            script.append("<td>");
            script.append("</td>");
            script.append("<td class=\"label\">text-align :</td>");
            script.append("<td>");
            script.append("<select dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\"Valor inválido\" id=\"fs_"+(++serial)+"\" name=\"text-align\" style=\"width:100px\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\">");
            script.append("<option value=\"empty\"></option>");
            script.append("<option value=\"left\">left</option>");
            script.append("<option value=\"right\">right</option>");
            script.append("<option value=\"center\">center</option>");
            script.append("<option value=\"justify\">justify</option>");
            script.append("</select>");
            script.append("</td>");
            script.append("<td class=\"label\">line-height :</td>");
            script.append("<td><input dojoType=\"dijit.form.NumberSpinner\" smallDelta=\"1\" constraints=\"{min:0,max:250,places:0}\" id=\"is_"+(++serial)+"\" name=\"line-height\" style=\"width:60px\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value+dojo.byId('cb_"+serial+"').value);\" />");
            script.append("<select dojoType=\"dijit.form.ComboBox\" id=\"cb_"+(serial)+"\" name=\"line-height\" style=\"width:70px\" onchange=\"sendData(dojo.byId('stel').value, this.name, dojo.byId('is_"+serial+"').value+this.value);\">");
            script.append("<option value=\"px\">px</option><option value=\"pt\">pt</option><option value=\"em\">em</option><option value=\"pc\">pc</option><option value=\"p100\">&permil;</option>");
            script.append("</select>");
            script.append("</td>");
            script.append("</tr>");

            script.append("<tr>");
            script.append("<td class=\"label\">cursor :</td>");
            script.append("<td>");
            script.append("<select dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\"Valor inválido\" id=\"fs_"+(++serial)+"\" name=\"cursor\" style=\"width:100px\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\">");
            script.append("<option value=\"empty\"></option>");
            script.append("<option value=\"default\">default</option>");
            script.append("<option value=\"pointer\">pointer</option>");
            script.append("<option value=\"text\">text</option>");
            script.append("<option value=\"wait\">wait</option>");
            script.append("<option value=\"help\">help</option>");
            script.append("<option value=\"crosshair\">crosshair</option>");
            script.append("</select>");
            script.append("</td>");
            script.append("<td class=\"label\">text-decoration :</td>");
            script.append("<td>");
            script.append("<select dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\"Valor inválido\" id=\"fs_"+(++serial)+"\" name=\"text-decoration\" style=\"width:100px\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\">");
            script.append("<option value=\"empty\"></option>");
            script.append("<option value=\"underline\">underline</option>");
            script.append("<option value=\"overline\">overline</option>");
            script.append("<option value=\"line-through\">line-through</option>");
            script.append("<option value=\"blink\">blink</option>");
            script.append("<option value=\"none\">none</option>");
            script.append("</select>");
            script.append("</td>");
            script.append("<td class=\"label\">case :</td>");
            script.append("<td>");
            script.append("<select dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\"Valor inválido\" id=\"fs_"+(++serial)+"\" name=\"case\" style=\"width:100px\" onchange=\"sendData(dojo.byId('stel').value, this.name, this.value);\">");
            script.append("<option value=\"empty\"></option>");
            script.append("<option value=\"capitalize\">capitalize</option>");
            script.append("<option value=\"uppercase\">uppercase</option>");
            script.append("<option value=\"lowercase\">lowercase</option>");
            script.append("<option value=\"none\">none</option>");
            script.append("</select>");
            script.append("</td>");
            script.append("</tr>");

            script.append("</table>");
            script.append("</div>");
            nl();

            matriz.put(name, new HashMap());
            System.out.println("fin processSelector");
        }
    }

    private HashMap processProperties(Element sel) {
        HashMap attrs = new HashMap();
        NodeList nl = sel.getElementsByTagName("property");
        if(nl!=null && nl.getLength()>0) {
            for(int i=0; i<nl.getLength(); i++) {
                Element e = (Element)nl.item(i);
                attrs.put(e.getAttribute("name"), e.getAttribute("value"));
            }
        }
        return attrs;
    }

    private void nl() {
        script.append("\n");
    }

    private void processStartDocument() {
        System.out.println("inicia processStartDocument...");
        script.append("<style type=\"text/css\">\n");
        script.append("  body, div, table, input, select { font-family:helvetica,arial,sans-serif; font-size:10pt; }\n");
        script.append("  td { vertical-align:top; }\n");
        script.append("  .label { text-align:right; vertical-align:top; }");
        script.append("</style>\n");

        script.append("<script type=\"text/javascript\">\n");
        script.append(" dojo.require(\"dijit.layout.TabContainer\");\n");
        script.append(" dojo.require(\"dijit.layout.ContentPane\");\n");
        script.append(" dojo.require(\"dijit.form.NumberSpinner\");\n");
        script.append(" dojo.require(\"dijit.form.ComboBox\");\n");
        script.append(" dojo.require(\"dijit.form.FilteringSelect\");\n");
        script.append(" dojo.require(\"dijit.form.TextBox\");\n");
        //script.append(" dojo.require(\"dijit.form.Button\");\n");

        script.append(" dojo.require(\"dojo.fx\");\n");
        script.append(" dojo.require(\"dijit.ColorPalette\");\n");
        script.append(" dojo.require(\"dijit.form.Button\");\n");

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
        script.append("    dojo.connect(tc, 'selectChild', function(child){ dojo.byId('stel').value=child.id; console.log('child = '+child.title+' id = '+child.id); });\n");
        script.append("});\n");

        script.append("</script>\n");

        script.append("<input id=\"stel\" type=\"hidden\" />");
        script.append("<div dojoType=\"dijit.layout.TabContainer\" id=\"tc_"+client.getId()+"\" style=\"width: 100%;\" doLayout=\"false\">\n");

        matriz = new HashMap();
        System.out.println("fin processStartDocument");
    }
    
    private void processEndDocument() {
        System.out.println("inicia processEndDocuent...");
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
        System.out.println("fin processEndDocument");
    }

    public HashMap getMatriz() {
        return matriz;
    }

    public static void main(String argv[]) {

    }
}
