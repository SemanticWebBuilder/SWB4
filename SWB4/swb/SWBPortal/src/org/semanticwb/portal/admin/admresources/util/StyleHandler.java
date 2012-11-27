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

/**
 *
 * @author Carlos Ramos
 */

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;
import java.util.HashMap;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

import org.semanticwb.model.Resource;

// TODO: Auto-generated Javadoc
/**
 * The Class StyleHandler.
 */
public class StyleHandler extends DefaultHandler {
    
    /** The serial. */
    private long serial;
    
    /** The cp. */
    private long cp;
    
    /** The matriz. */
    private HashMap matriz;
    
    /** The client. */
    private Resource client;

    /** The style file. */
    File styleFile;
    
    /** The script. */
    StringBuilder script = new StringBuilder();

    /**
     * Instantiates a new style handler.
     * 
     * @param filename the filename
     * @param client the client
     * @throws ParserConfigurationException the parser configuration exception
     * @throws SAXException the sAX exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public StyleHandler(String filename, Resource client) throws ParserConfigurationException, SAXException, IOException {
        this(new File(filename), client);
    }

    /**
     * Instantiates a new style handler.
     * 
     * @param file the file
     * @param client the client
     * @throws ParserConfigurationException the parser configuration exception
     * @throws SAXException the sAX exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public StyleHandler(File file, Resource client) throws ParserConfigurationException, SAXException, IOException {
        this.client = client;

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(file, this );        
    }

    /**
     * Instantiates a new style handler.
     * 
     * @param is the is
     * @param client the client
     * @throws ParserConfigurationException the parser configuration exception
     * @throws SAXException the sAX exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public StyleHandler(InputStream is, Resource client) throws ParserConfigurationException, SAXException, IOException {
        this.client = client;
        
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(is,  this);

    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#startDocument()
     */
    @Override
    public void startDocument() throws SAXException {
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
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#endDocument()
     */
    @Override
    public void endDocument() throws SAXException {
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
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement (String uri, String localName, String qName, Attributes attributes) throws SAXException {
	String eName = localName; // element name
        if ("".equals(eName)) eName = qName; // namespaceAware = false
        
        if(eName.equalsIgnoreCase("class")) {
            String name=null, selected="";
            
            for (int i = 0; i < attributes.getLength(); i++) {
                String aName = attributes.getLocalName(i); // Attr name
                if ("".equalsIgnoreCase(aName)) aName = attributes.getQName(i);
                if(aName.equalsIgnoreCase("name"))
                    name = attributes.getValue(i);
                else if(aName.equalsIgnoreCase("selected"))
                    selected = attributes.getValue(i);

            }
            if(name != null) {
                script.append("<div dojoType=\"dijit.layout.ContentPane\" title=\""+name+"\" id=\""+name+"\" selected=\""+selected+"\">");

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
            }
        }        
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void endElement (String uri, String localName, String qName) throws SAXException {
        nl();
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
     */
    @Override
    public void characters (char ch[], int start, int length) throws SAXException {
//	String s = new String(ch, start, length);
//        emit(s);
    }

    /**
     * Gets the script.
     * 
     * @return the script
     */
    public String getScript() {
        return script.toString();
    }

    /**
     * Emit.
     * 
     * @param s the s
     * @throws SAXException the sAX exception
     */
    private void emit(String s) throws SAXException {
//        try {
//            out.write(s);
//            out.flush();
//        } catch (IOException e) {
//            throw new SAXException("I/O error", e);
//        }
    }

    /**
     * Nl.
     * 
     * @throws SAXException the sAX exception
     */
    private void nl() throws SAXException {
        script.append("\n");
//        String lineEnd = System.getProperty("line.separator");
//            script.append(lineEnd);
    }

    /**
     * The main method.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {
        try {
            StyleHandler handler = new StyleHandler("D:/temp/Videojuego/src/xml/recurso.xml", null);
            System.out.println(handler.getScript());
        }catch(Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Gets the matriz.
     * 
     * @return the matriz
     */
    public HashMap getMatriz() {
        return matriz;
    }

}
