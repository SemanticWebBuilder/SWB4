
package org.semanticwb.bsc.resources.maps;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.fop.svg.PDFTranscoder;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import static org.semanticwb.bsc.PDFExportable.Mode_StreamPDF;
import static org.semanticwb.bsc.PDFExportable.Mode_StreamPNG;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author carlos.ramos
 */
public class RisksMap extends GenericResource {
    private static final Logger log = SWBUtils.getLogger(RisksMap.class);
    
    public static final String Action_UPDATE = "update";
    
    public static final String WORD_SPACING = "14 14 14 14 14 14 14 14 14 14"; // dx para los ejes de coordenadas, excluyendo el 0
    
    public static final int MARGEN_LEFT = 20; // Especifica el margen izquierdo del rectángulo de una perspectiva
    public static final int MARGEN_RIGHT = 10; // Especifica el margen derecho del rectángulo de una perspectiva
    public static final int MARGEN_TOP = 20; // Especifica el margen superior del rectángulo de una perspectiva
    public static final int MARGEN_BOTTOM = 20; // Especifica el margen inferior del rectángulo de una perspectiva
    
    public static final int HEADER_HEIGHT = 50; // altura del encabezado
    public static final int HEADER_1 = 24; // tamaño de fuente para título del mapa
    public static final int HEADER_2 = 16; // tamaño de fuente para subtítulo
    public static final int HEADER_3 = 14; // tamaño de fuente para coordenadas
    public static final int HEADER_4 = 12; // tamaño de fuente para texto
    
    public static final int CELL_WIDTH = 100; // ancho de las celdas de la tabla de riesgos
    
    public static final int BOX_SPACING = 16; // Especifica el espacio entre rectángulos internos de una perspectiva
    public static final int BOX_SPACING_LEFT = 8; // Especifica el espacio entre rectángulos internos de una perspectiva
    public static final int BOX_SPACING_RIGHT = 8; // Especifica el espacio entre rectángulos internos de una perspectiva
    public static final int BOX_SPACING_TOP = 8; // Especifica el espacio entre rectángulos internos de una perspectiva
    public static final int BOX_SPACING_BOTTOM = 8; // Especifica el espacio entre rectángulos internos de una perspectiva
    
    public static final int PADDING_TOP = 4; // Especifica el espacio libre arriba entre rectángulos para pintar las ligas
    public static final int PADDING_LEFT = 2; // Especifica el espacio libre a la izquieerda entre rectángulos para pintar las ligas
    public static final int PADDING_RIGHT = 2; // Especifica el espacio libre a la derecha entre rectángulos para pintar las ligas
    public static final int PADDING_DOWN = 4; // Especifica el espacio libre a la derecha entre rectángulos para pintar las ligas
    public static final String SVG_NS_URI = "http://www.w3.org/2000/svg";
    public static final String XLNK_NS_URI = "http://www.w3.org/1999/xlink";
    
//    @Override
//    public void setResourceBase(Resource base) throws SWBResourceException
//    {
//        super.setResourceBase(base);
//        this.width = assertValue(base.getAttribute("width","1050"));
//        this.height = assertValue(base.getAttribute("height","800"));
//    }
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final String mode = paramRequest.getMode();
        switch (mode) {
            case Mode_StreamPNG:
                doGetPNGImage(request, response, paramRequest);
                break;
            case Mode_StreamPDF:
                doGetPDFDocument(request, response, paramRequest);
                break;
            default:
                super.processRequest(request, response, paramRequest);
                break;
        }
    }
    
    /**
     * Genera la vista del mapa de riesgos
     *
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @param paramRequest un objeto de la plataforma de SWB con datos
     * adicionales de la petici&oacute;n
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta
     * con los recursos necesarios para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n
     * problema con la generaci&oacute;n o escritura de la respuesta
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        if(paramRequest.getCallMethod()==SWBParamRequest.Call_STRATEGY)
        {            
            final String suri = request.getParameter("suri");
            PrintWriter out = response.getWriter();
            SWBResourceURL url = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT);
            // impresión PNG
            out.print("<a href=\"#\" onclick=\"getFile('"+url.setMode(Mode_StreamPNG)+"')\" ");
            out.print(" class=\"swbstgy-toolbar-printPng\" title=\"");
            out.print(paramRequest.getLocaleString("msgPrintPNGImage"));
            out.print("\">");
            out.print(paramRequest.getLocaleString("msgPrintPNGImage"));
            out.println("</a>");
            
            // impresión PDF
            out.print("<a href=\"#\" onclick=\"getFile('"+url.setMode(Mode_StreamPDF)+"')\" ");
            out.print(" class=\"swbstgy-toolbar-printPdf\" title=\"");
            out.print(paramRequest.getLocaleString("msgPrintPDFDocument"));
            out.print("\">");
            out.print(paramRequest.getLocaleString("msgPrintPDFDocument"));
            out.println("</a>");
            
            out.println(" <form id=\"svgform\" accept-charset=\"utf-8\" method=\"post\" action=\"#\">");
            out.println("  <input type=\"hidden\" name=\"suri\" value=\""+suri+"\" />");
            out.println("  <input type=\"hidden\" id=\"data\" name=\"data\" value=\"\" />");
//            out.println("  <input type=\"button\" value=\"Imagen\" onclick=\"getFile('"+exportUrl.setMode(Mode_PNGImage)+"')\"  />");
//            out.println("  <input type=\"button\" value=\"PDF\" onclick=\"getFile('"+exportUrl.setMode(Mode_PDFDocument)+"')\"  />");
            out.println(" </form>");
            
            out.println(" <script type=\"text/javascript\">");
            out.println("  function getFile(url) {");
            out.println("   var form = document.getElementById('svgform');");
            out.println("   var svg = document.getElementsByTagName('svg')[0];");
            out.println("   var svg_xml = (new XMLSerializer).serializeToString(svg);");
            out.println("   form.action = url;");
            out.println("   form['data'].value = svg_xml;");
            out.println("   form.submit();");
            out.println("  };");
            out.println(" </script>");
            
        }
        else
        {
            PrintWriter out = response.getWriter();
            String SVGjs;
            try {
                 SVGjs = getSvg();
            }catch(XPathExpressionException xpe) {
                System.out.println(xpe.toString());
                out.println(xpe.getMessage());
                return;
            }
            out.println(SVGjs);
        }
    }
    
    public void doGetPNGImage(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        WebSite webSite = getResourceBase().getWebSite();
        response.setContentType("image/png; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Content-Disposition", "attachment; filename=\""+webSite.getTitle()+"_riskmap.png\"");
        if(webSite instanceof BSC) {
            final String data = request.getParameter("data");
            Document svg = SWBUtils.XML.xmlToDom(data);
            PNGTranscoder t = new PNGTranscoder();
            TranscoderInput input = new TranscoderInput(svg);
            TranscoderOutput output = new TranscoderOutput(response.getOutputStream());
            try {
                t.transcode(input, output);
                response.getOutputStream().flush();
                response.getOutputStream().close();
            }catch(TranscoderException tcdre) {
            }
        }
    }

    public void doGetPDFDocument(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        WebSite webSite = getResourceBase().getWebSite();
        response.setContentType("application/pdf; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Content-Disposition", "attachment; filename=\""+webSite.getTitle()+"_riskmap.pdf\"");
        if(webSite instanceof BSC) {
            final String data = request.getParameter("data");
            Document svg = SWBUtils.XML.xmlToDom(data);
            PDFTranscoder t = new PDFTranscoder();
            TranscoderInput input = new TranscoderInput(svg);
            TranscoderOutput output = new TranscoderOutput(response.getOutputStream());
            try {
                t.transcode(input, output);
                response.getOutputStream().flush();
                response.getOutputStream().close();
            }catch(TranscoderException tcdre) {
            }
        }
    }
    
    @Override
    public void doXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        WebSite webSite = getResourceBase().getWebSite();
        response.setContentType("text/xml:; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Content-Disposition", "attachment; filename=\""+webSite.getTitle()+".xml\"");
        
        if(webSite instanceof BSC)
        {
            BSC model = (BSC)webSite;
            PrintWriter out = response.getWriter();
            Document dom = model.getDom();
            out.println(SWBUtils.XML.domToXml(dom));
            out.flush();
            out.close();
        }
    }
    
    /**
     * Permite capturar y almacenar la informaci&oacute;n para configurar un
     * mapa estrat&eacute;gico
     *
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @param paramRequest un objeto de la plataforma de SWB con datos
     * adicionales de la petici&oacute;n
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta
     * con los recursos necesarios para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n
     * problema con la generaci&oacute;n o escritura de la respuesta
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base=getResourceBase();
        PrintWriter out = response.getWriter();

        String action = null != request.getParameter("act") && !"".equals(request.getParameter("act").trim()) ? request.getParameter("act").trim() : paramRequest.getAction();
        if(SWBParamRequest.Action_ADD.equals(action) || SWBParamRequest.Action_EDIT.equals(action))
        {
            StringBuilder htm = new StringBuilder();
            final String path = SWBPortal.getWebWorkPath()+base.getWorkPath()+"/";
            SWBResourceURL url = paramRequest.getActionUrl().setAction(Action_UPDATE);
            htm.append("<script type=\"text/javascript\">\n");
            htm.append("  dojo.require('dijit.layout.ContentPane');\n");
            htm.append("  dojo.require('dijit.form.Form');\n");
            htm.append("  dojo.require('dijit.form.TextBox');\n");
            htm.append("  dojo.require('dijit.form.ValidationTextBox');\n");
            htm.append("  dojo.require('dijit.form.RadioButton');\n");
            htm.append("  dojo.require('dijit.form.Button');\n");

            htm.append("  function setColor(domId, color) {\n");
            htm.append("     var col=dijit.byId(domId);\n");
            htm.append("     col.setValue(color);\n");
            htm.append("     dojo.style(dijit.byId(domId).domNode,'background',color);\n");
            htm.append("    var num=parseInt(\"FFFFFF\", 16)-parseInt(color.substring(1), 16);\n");
            htm.append("    var hex=num.toString(16);\n");
            htm.append("    while (hex.length < 6) {\n");
            htm.append("      hex = \"0\" + hex;\n");
            htm.append("    }\n");
            htm.append("    col.style.color='#'+hex;\n");
            htm.append("  }\n");

            htm.append("  dojo.addOnLoad(function(){\n");
            htm.append("    setColor('quadrant1Color','"+base.getAttribute("quadrant1Color","#2E2EFE")+"');\n");
            htm.append("    setColor('quadrant2Color','"+base.getAttribute("quadrant2Color","#FE2E2E")+"');\n");
            htm.append("    setColor('quadrant3Color','"+base.getAttribute("quadrant3Color","#2EFE64")+"');\n");
            htm.append("    setColor('quadrant4Color','"+base.getAttribute("quadrant4Color","#F7FE2E")+"');\n");
            htm.append("  });\n");
            htm.append("</script>\n");



            htm.append("<div class=\"swbform\">\n");
            htm.append("<form id=\"frmPromo\" dojoType=\"dijit.form.Form\" method=\"post\" action=\""+url+"\">\n");
            htm.append("<div title=\"Configuración del estilo\" open=\"true\" dojoType=\"dijit.TitlePane\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">\n");
            htm.append("<fieldset>\n");
            htm.append("    <legend>Estilo</legend>\n");
            htm.append("    <ul class=\"swbform-ul\">\n");

            htm.append("        <li class=\"swbform-li\">\n");
            htm.append("          <label for=\"imgWidth\" class=\"swbform-label\">Anchura de la imagen <i>(pixeles)</i></label>\n");
            htm.append("          <input type=\"text\" id=\"width\" name=\"width\" regExp=\"\\d+\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("width","1050")+"\" maxlength=\"4\" />\n");
            htm.append("        </li>\n");
            htm.append("        <li class=\"swbform-li\">\n");
            htm.append("          <label for=\"imgHeight\" class=\"swbform-label\">Altura de la imagen <i>(pixeles)</i></label>\n");
            htm.append("          <input type=\"text\" id=\"height\" name=\"height\" regExp=\"\\d+\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("height","800")+"\" maxlength=\"4\" />\n");
            htm.append("        </li>\n");

            // cuadrante 1
            htm.append("        <li class=\"swbform-li\">\n");
            htm.append("          <label for=\"quadrant1Color\" class=\"swbform-label\">Color del cuadrante I <i>(hexadecimal)</i></label>\n");
            htm.append("          <input type=\"text\" dojoType=\"dijit.form.TextBox\" id=\"quadrant1Color\" name=\"quadrant1Color\" value=\""+base.getAttribute("quadrant1Color","#0000FF")+"\" onblur=\"setColor('quadrant1Color',this.value);\" maxlength=\"7\"  />\n");
            htm.append("        </li>\n");
            htm.append("        <li class=\"swbform-li\">\n");
            htm.append("          <label class=\"swbform-label\"></label>\n");
            htm.append("          <img src=\"/swbadmin/images/colorgrid.gif\" width=\"292\" height=\"196\" border=\"0\" alt=\"RGB color mixer\" usemap=\"#gridmap1\" ismap=\"ismap\">\n");
            htm.append("          <map name=\"gridmap1\">\n");
            htm.append("<!--- Row 1 --->\n");
            htm.append("<area coords=\"2,2,18,18\" onclick=\"setColor('quadrant1Color','#330000')\">\n");
            htm.append("<area coords=\"18,2,34,18\" onclick=\"setColor('quadrant1Color','#333300')\">\n");
            htm.append("<area coords=\"34,2,50,18\" onclick=\"setColor('quadrant1Color','#336600')\">\n");
            htm.append("<area coords=\"50,2,66,18\" onclick=\"setColor('quadrant1Color','#339900')\">\n");
            htm.append("<area coords=\"66,2,82,18\" onclick=\"setColor('quadrant1Color','#33CC00')\">\n");
            htm.append("<area coords=\"82,2,98,18\" onclick=\"setColor('quadrant1Color','#33FF00')\">\n");
            htm.append("<area coords=\"98,2,114,18\" onclick=\"setColor('quadrant1Color','#66FF00')\">\n");
            htm.append("<area coords=\"114,2,130,18\" onclick=\"setColor('quadrant1Color','#66CC00')\">\n");
            htm.append("<area coords=\"130,2,146,18\" onclick=\"setColor('quadrant1Color','#669900')\">\n");
            htm.append("<area coords=\"146,2,162,18\" onclick=\"setColor('quadrant1Color','#666600')\">\n");
            htm.append("<area coords=\"162,2,178,18\" onclick=\"setColor('quadrant1Color','#663300')\">\n");
            htm.append("<area coords=\"178,2,194,18\" onclick=\"setColor('quadrant1Color','#660000')\">\n");
            htm.append("<area coords=\"194,2,210,18\" onclick=\"setColor('quadrant1Color','#FF0000')\">\n");
            htm.append("<area coords=\"210,2,226,18\" onclick=\"setColor('quadrant1Color','#FF3300')\">\n");
            htm.append("<area coords=\"226,2,242,18\" onclick=\"setColor('quadrant1Color','#FF6600')\">\n");
            htm.append("<area coords=\"242,2,258,18\" onclick=\"setColor('quadrant1Color','#FF9900')\">\n");
            htm.append("<area coords=\"258,2,274,18\" onclick=\"setColor('quadrant1Color','#FFCC00')\">\n");
            htm.append("<area coords=\"274,2,290,18\" onclick=\"setColor('quadrant1Color','#FFFF00')\">\n");
            htm.append("<!--- Row 2 --->\n");
            htm.append("<area coords=\"2,18,18,34\" onclick=\"setColor('quadrant1Color','#330033')\">\n");
            htm.append("<area coords=\"18,18,34,34\" onclick=\"setColor('quadrant1Color','#333333')\">\n");
            htm.append("<area coords=\"34,18,50,34\" onclick=\"setColor('quadrant1Color','#336633')\">\n");
            htm.append("<area coords=\"50,18,66,34\" onclick=\"setColor('quadrant1Color','#339933')\">\n");
            htm.append("<area coords=\"66,18,82,34\" onclick=\"setColor('quadrant1Color','#33CC33')\">\n");
            htm.append("<area coords=\"82,18,98,34\" onclick=\"setColor('quadrant1Color','#33FF33')\">\n");
            htm.append("<area coords=\"98,18,114,34\" onclick=\"setColor('quadrant1Color','#66FF33')\">\n");
            htm.append("<area coords=\"114,18,130,34\" onclick=\"setColor('quadrant1Color','#66CC33')\">\n");
            htm.append("<area coords=\"130,18,146,34\" onclick=\"setColor('quadrant1Color','#669933')\">\n");
            htm.append("<area coords=\"146,18,162,34\" onclick=\"setColor('quadrant1Color','#666633')\">\n");
            htm.append("<area coords=\"162,18,178,34\" onclick=\"setColor('quadrant1Color','#663333')\">\n");
            htm.append("<area coords=\"178,18,194,34\" onclick=\"setColor('quadrant1Color','#660033')\">\n");
            htm.append("<area coords=\"194,18,210,34\" onclick=\"setColor('quadrant1Color','#FF0033')\">\n");
            htm.append("<area coords=\"210,18,226,34\" onclick=\"setColor('quadrant1Color','#FF3333')\">\n");
            htm.append("<area coords=\"226,18,242,34\" onclick=\"setColor('quadrant1Color','#FF6633')\">\n");
            htm.append("<area coords=\"242,18,258,34\" onclick=\"setColor('quadrant1Color','#FF9933')\">\n");
            htm.append("<area coords=\"258,18,274,34\" onclick=\"setColor('quadrant1Color','#FFCC33')\">\n");
            htm.append("<area coords=\"274,18,290,34\" onclick=\"setColor('quadrant1Color','#FFFF33')\">\n");
            htm.append("<!--- Row 3 --->\n");
            htm.append("<area coords=\"2,34,18,50\" onclick=\"setColor('quadrant1Color','#330066')\">\n");
            htm.append("<area coords=\"18,34,34,50\" onclick=\"setColor('quadrant1Color','#333366')\">\n");
            htm.append("<area coords=\"34,34,50,50\" onclick=\"setColor('quadrant1Color','#336666')\">\n");
            htm.append("<area coords=\"50,34,66,50\" onclick=\"setColor('quadrant1Color','#339966')\">\n");
            htm.append("<area coords=\"66,34,82,50\" onclick=\"setColor('quadrant1Color','#33CC66')\">\n");
            htm.append("<area coords=\"82,34,98,50\" onclick=\"setColor('quadrant1Color','#33FF66')\">\n");
            htm.append("<area coords=\"98,34,114,50\" onclick=\"setColor('quadrant1Color','#66FF66')\">\n");
            htm.append("<area coords=\"114,34,130,50\" onclick=\"setColor('quadrant1Color','#66CC66')\">\n");
            htm.append("<area coords=\"130,34,146,50\" onclick=\"setColor('quadrant1Color','#669966')\">\n");
            htm.append("<area coords=\"146,34,162,50\" onclick=\"setColor('quadrant1Color','#666666')\">\n");
            htm.append("<area coords=\"162,34,178,50\" onclick=\"setColor('quadrant1Color','#663366')\">\n");
            htm.append("<area coords=\"178,34,194,50\" onclick=\"setColor('quadrant1Color','#660066')\">\n");
            htm.append("<area coords=\"194,34,210,50\" onclick=\"setColor('quadrant1Color','#FF0066')\">\n");
            htm.append("<area coords=\"210,34,226,50\" onclick=\"setColor('quadrant1Color','#FF3366')\">\n");
            htm.append("<area coords=\"226,34,242,50\" onclick=\"setColor('quadrant1Color','#FF6666')\">\n");
            htm.append("<area coords=\"242,34,258,50\" onclick=\"setColor('quadrant1Color','#FF9966')\">\n");
            htm.append("<area coords=\"258,34,274,50\" onclick=\"setColor('quadrant1Color','#FFCC66')\">\n");
            htm.append("<area coords=\"274,34,290,50\" onclick=\"setColor('quadrant1Color','#FFFF66')\">\n");
            htm.append("<!--- Row 4 --->\n");
            htm.append("<area coords=\"2,50,18,66\" onclick=\"setColor('quadrant1Color','#330099')\">\n");
            htm.append("<area coords=\"18,50,34,66\" onclick=\"setColor('quadrant1Color','#333399')\">\n");
            htm.append("<area coords=\"34,50,50,66\" onclick=\"setColor('quadrant1Color','#336699')\">\n");
            htm.append("<area coords=\"50,50,66,66\" onclick=\"setColor('quadrant1Color','#339999')\">\n");
            htm.append("<area coords=\"66,50,82,66\" onclick=\"setColor('quadrant1Color','#33CC99')\">\n");
            htm.append("<area coords=\"82,50,98,66\" onclick=\"setColor('quadrant1Color','#33FF99')\">\n");
            htm.append("<area coords=\"98,50,114,66\" onclick=\"setColor('quadrant1Color','#66FF99')\">\n");
            htm.append("<area coords=\"114,50,130,66\" onclick=\"setColor('quadrant1Color','#66CC99')\">\n");
            htm.append("<area coords=\"130,50,146,66\" onclick=\"setColor('quadrant1Color','#669999')\">\n");
            htm.append("<area coords=\"146,50,162,66\" onclick=\"setColor('quadrant1Color','#666699')\">\n");
            htm.append("<area coords=\"162,50,178,66\" onclick=\"setColor('quadrant1Color','#663399')\">\n");
            htm.append("<area coords=\"178,50,194,66\" onclick=\"setColor('quadrant1Color','#660099')\">\n");
            htm.append("<area coords=\"194,50,210,66\" onclick=\"setColor('quadrant1Color','#FF0099')\">\n");
            htm.append("<area coords=\"210,50,226,66\" onclick=\"setColor('quadrant1Color','#FF3399')\">\n");
            htm.append("<area coords=\"226,50,242,66\" onclick=\"setColor('quadrant1Color','#FF6699')\">\n");
            htm.append("<area coords=\"242,50,258,66\" onclick=\"setColor('quadrant1Color','#FF9999')\">\n");
            htm.append("<area coords=\"258,50,274,66\" onclick=\"setColor('quadrant1Color','#FFCC99')\">\n");
            htm.append("<area coords=\"274,50,290,66\" onclick=\"setColor('quadrant1Color','#FFFF99')\">\n");
            htm.append("<!--- Row 5 --->\n");
            htm.append("<area coords=\"2,66,18,82\" onclick=\"setColor('quadrant1Color','#3300CC')\">\n");
            htm.append("<area coords=\"18,66,34,82\" onclick=\"setColor('quadrant1Color','#3333CC')\">\n");
            htm.append("<area coords=\"34,66,50,82\" onclick=\"setColor('quadrant1Color','#3366CC')\">\n");
            htm.append("<area coords=\"50,66,66,82\" onclick=\"setColor('quadrant1Color','#3399CC')\">\n");
            htm.append("<area coords=\"66,66,82,82\" onclick=\"setColor('quadrant1Color','#33CCCC')\">\n");
            htm.append("<area coords=\"82,66,98,82\" onclick=\"setColor('quadrant1Color','#33FFCC')\">\n");
            htm.append("<area coords=\"98,66,114,82\" onclick=\"setColor('quadrant1Color','#66FFCC')\">\n");
            htm.append("<area coords=\"114,66,130,82\" onclick=\"setColor('quadrant1Color','#66CCCC')\">\n");
            htm.append("<area coords=\"130,66,146,82\" onclick=\"setColor('quadrant1Color','#6699CC')\">\n");
            htm.append("<area coords=\"146,66,162,82\" onclick=\"setColor('quadrant1Color','#6666CC')\">\n");
            htm.append("<area coords=\"162,66,178,82\" onclick=\"setColor('quadrant1Color','#6633CC')\">\n");
            htm.append("<area coords=\"178,66,194,82\" onclick=\"setColor('quadrant1Color','#6600CC')\">\n");
            htm.append("<area coords=\"194,66,210,82\" onclick=\"setColor('quadrant1Color','#FF00CC')\">\n");
            htm.append("<area coords=\"210,66,226,82\" onclick=\"setColor('quadrant1Color','#FF33CC')\">\n");
            htm.append("<area coords=\"226,66,242,82\" onclick=\"setColor('quadrant1Color','#FF66CC')\">\n");
            htm.append("<area coords=\"242,66,258,82\" onclick=\"setColor('quadrant1Color','#FF99CC')\">\n");
            htm.append("<area coords=\"258,66,274,82\" onclick=\"setColor('quadrant1Color','#FFCCCC')\">\n");
            htm.append("<area coords=\"274,66,290,82\" onclick=\"setColor('quadrant1Color','#FFFFCC')\">\n");
            htm.append("<!--- Row 6 --->\n");
            htm.append("<area coords=\"2,82,18,98\" onclick=\"setColor('quadrant1Color','#3300FF')\">\n");
            htm.append("<area coords=\"18,82,34,98\" onclick=\"setColor('quadrant1Color','#3333FF')\">\n");
            htm.append("<area coords=\"34,82,50,98\" onclick=\"setColor('quadrant1Color','#3366FF')\">\n");
            htm.append("<area coords=\"50,82,66,98\" onclick=\"setColor('quadrant1Color','#3399FF')\">\n");
            htm.append("<area coords=\"66,82,82,98\" onclick=\"setColor('quadrant1Color','#33CCFF')\">\n");
            htm.append("<area coords=\"82,82,98,98\" onclick=\"setColor('quadrant1Color','#33FFFF')\">\n");
            htm.append("<area coords=\"98,82,114,98\" onclick=\"setColor('quadrant1Color','#66FFFF')\">\n");
            htm.append("<area coords=\"114,82,130,98\" onclick=\"setColor('quadrant1Color','#66CCFF')\">\n");
            htm.append("<area coords=\"130,82,146,98\" onclick=\"setColor('quadrant1Color','#6699FF')\">\n");
            htm.append("<area coords=\"146,82,162,98\" onclick=\"setColor('quadrant1Color','#6666FF')\">\n");
            htm.append("<area coords=\"162,82,178,98\" onclick=\"setColor('quadrant1Color','#6633FF')\">\n");
            htm.append("<area coords=\"178,82,194,98\" onclick=\"setColor('quadrant1Color','#6600FF')\">\n");
            htm.append("<area coords=\"194,82,210,98\" onclick=\"setColor('quadrant1Color','#FF00FF')\">\n");
            htm.append("<area coords=\"210,82,226,98\" onclick=\"setColor('quadrant1Color','#FF33FF')\">\n");
            htm.append("<area coords=\"226,82,242,98\" onclick=\"setColor('quadrant1Color','#FF66FF')\">\n");
            htm.append("<area coords=\"242,82,258,98\" onclick=\"setColor('quadrant1Color','#FF99FF')\">\n");
            htm.append("<area coords=\"258,82,274,98\" onclick=\"setColor('quadrant1Color','#FFCCFF')\">\n");
            htm.append("<area coords=\"274,82,290,98\" onclick=\"setColor('quadrant1Color','#FFFFFF')\">\n");
            htm.append("<!--- Row 7 --->\n");
            htm.append("<area coords=\"2,98,18,114\" onclick=\"setColor('quadrant1Color','#0000FF')\">\n");
            htm.append("<area coords=\"18,98,34,114\" onclick=\"setColor('quadrant1Color','#0033FF')\">\n");
            htm.append("<area coords=\"34,98,50,114\" onclick=\"setColor('quadrant1Color','#0066FF')\">\n");
            htm.append("<area coords=\"50,98,66,114\" onclick=\"setColor('quadrant1Color','#0099FF')\">\n");
            htm.append("<area coords=\"66,98,82,114\" onclick=\"setColor('quadrant1Color','#00CCFF')\">\n");
            htm.append("<area coords=\"82,98,98,114\" onclick=\"setColor('quadrant1Color','#00FFFF')\">\n");
            htm.append("<area coords=\"98,98,114,114\" onclick=\"setColor('quadrant1Color','#99FFFF')\">\n");
            htm.append("<area coords=\"114,98,130,114\" onclick=\"setColor('quadrant1Color','#99CCFF')\">\n");
            htm.append("<area coords=\"130,98,146,114\" onclick=\"setColor('quadrant1Color','#9999FF')\">\n");
            htm.append("<area coords=\"146,98,162,114\" onclick=\"setColor('quadrant1Color','#9966FF')\">\n");
            htm.append("<area coords=\"162,98,178,114\" onclick=\"setColor('quadrant1Color','#9933FF')\">\n");
            htm.append("<area coords=\"178,98,194,114\" onclick=\"setColor('quadrant1Color','#9900FF')\">\n");
            htm.append("<area coords=\"194,98,210,114\" onclick=\"setColor('quadrant1Color','#CC00FF')\">\n");
            htm.append("<area coords=\"210,98,226,114\" onclick=\"setColor('quadrant1Color','#CC33FF')\">\n");
            htm.append("<area coords=\"226,98,242,114\" onclick=\"setColor('quadrant1Color','#CC66FF')\">\n");
            htm.append("<area coords=\"242,98,258,114\" onclick=\"setColor('quadrant1Color','#CC99FF')\">\n");
            htm.append("<area coords=\"258,98,274,114\" onclick=\"setColor('quadrant1Color','#CCCCFF')\">\n");
            htm.append("<area coords=\"274,98,290,114\" onclick=\"setColor('quadrant1Color','#CCFFFF')\">\n");
            htm.append("<!--- Row 8 --->\n");
            htm.append("<area coords=\"2,114,18,130\" onclick=\"setColor('quadrant1Color','#0000CC')\">\n");
            htm.append("<area coords=\"18,114,34,130\" onclick=\"setColor('quadrant1Color','#0033CC')\">\n");
            htm.append("<area coords=\"34,114,50,130\" onclick=\"setColor('quadrant1Color','#0066CC')\">\n");
            htm.append("<area coords=\"50,114,66,130\" onclick=\"setColor('quadrant1Color','#0099CC')\">\n");
            htm.append("<area coords=\"66,114,82,130\" onclick=\"setColor('quadrant1Color','#00CCCC')\">\n");
            htm.append("<area coords=\"82,114,98,130\" onclick=\"setColor('quadrant1Color','#00FFCC')\">\n");
            htm.append("<area coords=\"98,114,114,130\" onclick=\"setColor('quadrant1Color','#99FFCC')\">\n");
            htm.append("<area coords=\"114,114,130,130\" onclick=\"setColor('quadrant1Color','#99CCCC')\">\n");
            htm.append("<area coords=\"130,114,146,130\" onclick=\"setColor('quadrant1Color','#9999CC')\">\n");
            htm.append("<area coords=\"146,114,162,130\" onclick=\"setColor('quadrant1Color','#9966CC')\">\n");
            htm.append("<area coords=\"162,114,178,130\" onclick=\"setColor('quadrant1Color','#9933CC')\">\n");
            htm.append("<area coords=\"178,114,194,130\" onclick=\"setColor('quadrant1Color','#9900CC')\">\n");
            htm.append("<area coords=\"194,114,210,130\" onclick=\"setColor('quadrant1Color','#CC00CC')\">\n");
            htm.append("<area coords=\"210,114,226,130\" onclick=\"setColor('quadrant1Color','#CC33CC')\">\n");
            htm.append("<area coords=\"226,114,242,130\" onclick=\"setColor('quadrant1Color','#CC66CC')\">\n");
            htm.append("<area coords=\"242,114,258,130\" onclick=\"setColor('quadrant1Color','#CC99CC')\">\n");
            htm.append("<area coords=\"258,114,274,130\" onclick=\"setColor('quadrant1Color','#CCCCCC')\">\n");
            htm.append("<area coords=\"274,114,290,130\" onclick=\"setColor('quadrant1Color','#CCFFCC')\">\n");
            htm.append("<!--- Row 9 --->\n");
            htm.append("<area coords=\"2,130,18,146\" onclick=\"setColor('quadrant1Color','#000099')\">\n");
            htm.append("<area coords=\"18,130,34,146\" onclick=\"setColor('quadrant1Color','#003399')\">\n");
            htm.append("<area coords=\"34,130,50,146\" onclick=\"setColor('quadrant1Color','#006699')\">\n");
            htm.append("<area coords=\"50,130,66,146\" onclick=\"setColor('quadrant1Color','#009999')\">\n");
            htm.append("<area coords=\"66,130,82,146\" onclick=\"setColor('quadrant1Color','#00CC99')\">\n");
            htm.append("<area coords=\"82,130,98,146\" onclick=\"setColor('quadrant1Color','#00FF99')\">\n");
            htm.append("<area coords=\"98,130,114,146\" onclick=\"setColor('quadrant1Color','#99FF99')\">\n");
            htm.append("<area coords=\"114,130,130,146\" onclick=\"setColor('quadrant1Color','#99CC99')\">\n");
            htm.append("<area coords=\"130,130,146,146\" onclick=\"setColor('quadrant1Color','#999999')\">\n");
            htm.append("<area coords=\"146,130,162,146\" onclick=\"setColor('quadrant1Color','#996699')\">\n");
            htm.append("<area coords=\"162,130,178,146\" onclick=\"setColor('quadrant1Color','#993399')\">\n");
            htm.append("<area coords=\"178,130,194,146\" onclick=\"setColor('quadrant1Color','#990099')\">\n");
            htm.append("<area coords=\"194,130,210,146\" onclick=\"setColor('quadrant1Color','#CC0099')\">\n");
            htm.append("<area coords=\"210,130,226,146\" onclick=\"setColor('quadrant1Color','#CC3399')\">\n");
            htm.append("<area coords=\"226,130,242,146\" onclick=\"setColor('quadrant1Color','#CC6699')\">\n");
            htm.append("<area coords=\"242,130,258,146\" onclick=\"setColor('quadrant1Color','#CC9999')\">\n");
            htm.append("<area coords=\"258,130,274,146\" onclick=\"setColor('quadrant1Color','#CCCC99')\">\n");
            htm.append("<area coords=\"274,130,290,146\" onclick=\"setColor('quadrant1Color','#CCFF99')\">\n");
            htm.append("<!--- Row 10 --->\n");
            htm.append("<area coords=\"2,146,18,162\" onclick=\"setColor('quadrant1Color','#000066')\">\n");
            htm.append("<area coords=\"18,146,34,162\" onclick=\"setColor('quadrant1Color','#003366')\">\n");
            htm.append("<area coords=\"34,146,50,162\" onclick=\"setColor('quadrant1Color','#006666')\">\n");
            htm.append("<area coords=\"50,146,66,162\" onclick=\"setColor('quadrant1Color','#009966')\">\n");
            htm.append("<area coords=\"66,146,82,162\" onclick=\"setColor('quadrant1Color','#00CC66')\">\n");
            htm.append("<area coords=\"82,146,98,162\" onclick=\"setColor('quadrant1Color','#00FF66')\">\n");
            htm.append("<area coords=\"98,146,114,162\" onclick=\"setColor('quadrant1Color','#99FF66')\">\n");
            htm.append("<area coords=\"114,146,130,162\" onclick=\"setColor('quadrant1Color','#99CC66')\">\n");
            htm.append("<area coords=\"130,146,146,162\" onclick=\"setColor('quadrant1Color','#999966')\">\n");
            htm.append("<area coords=\"146,146,162,162\" onclick=\"setColor('quadrant1Color','#996666')\">\n");
            htm.append("<area coords=\"162,146,178,162\" onclick=\"setColor('quadrant1Color','#993366')\">\n");
            htm.append("<area coords=\"178,146,194,162\" onclick=\"setColor('quadrant1Color','#990066')\">\n");
            htm.append("<area coords=\"194,146,210,162\" onclick=\"setColor('quadrant1Color','#CC0066')\">\n");
            htm.append("<area coords=\"210,146,226,162\" onclick=\"setColor('quadrant1Color','#CC3366')\">\n");
            htm.append("<area coords=\"226,146,242,162\" onclick=\"setColor('quadrant1Color','#CC6666')\">\n");
            htm.append("<area coords=\"242,146,258,162\" onclick=\"setColor('quadrant1Color','#CC9966')\">\n");
            htm.append("<area coords=\"258,146,274,162\" onclick=\"setColor('quadrant1Color','#CCCC66')\">\n");
            htm.append("<area coords=\"274,146,290,162\" onclick=\"setColor('quadrant1Color','#CCFF66')\">\n");
            htm.append("<!--- Row 11 --->\n");
            htm.append("<area coords=\"2,162,18,178\" onclick=\"setColor('quadrant1Color','#000033')\">\n");
            htm.append("<area coords=\"18,162,34,178\" onclick=\"setColor('quadrant1Color','#003333')\">\n");
            htm.append("<area coords=\"34,162,50,178\" onclick=\"setColor('quadrant1Color','#006633')\">\n");
            htm.append("<area coords=\"50,162,66,178\" onclick=\"setColor('quadrant1Color','#009933')\">\n");
            htm.append("<area coords=\"66,162,82,178\" onclick=\"setColor('quadrant1Color','#00CC33')\">\n");
            htm.append("<area coords=\"82,162,98,178\" onclick=\"setColor('quadrant1Color','#00FF33')\">\n");
            htm.append("<area coords=\"98,162,114,178\" onclick=\"setColor('quadrant1Color','#99FF33')\">\n");
            htm.append("<area coords=\"114,162,130,178\" onclick=\"setColor('quadrant1Color','#99CC33')\">\n");
            htm.append("<area coords=\"130,162,146,178\" onclick=\"setColor('quadrant1Color','#999933')\">\n");
            htm.append("<area coords=\"146,162,162,178\" onclick=\"setColor('quadrant1Color','#996633')\">\n");
            htm.append("<area coords=\"162,162,178,178\" onclick=\"setColor('quadrant1Color','#993333')\">\n");
            htm.append("<area coords=\"178,162,194,178\" onclick=\"setColor('quadrant1Color','#990033')\">\n");
            htm.append("<area coords=\"194,162,210,178\" onclick=\"setColor('quadrant1Color','#CC0033')\">\n");
            htm.append("<area coords=\"210,162,226,178\" onclick=\"setColor('quadrant1Color','#CC3333')\">\n");
            htm.append("<area coords=\"226,162,242,178\" onclick=\"setColor('quadrant1Color','#CC6633')\">\n");
            htm.append("<area coords=\"242,162,258,178\" onclick=\"setColor('quadrant1Color','#CC9933')\">\n");
            htm.append("<area coords=\"258,162,274,178\" onclick=\"setColor('quadrant1Color','#CCCC33')\">\n");
            htm.append("<area coords=\"274,162,290,178\" onclick=\"setColor('quadrant1Color','#CCFF33')\">\n");
            htm.append("<!--- Row 12 --->\n");
            htm.append("<area coords=\"2,178,18,194\" onclick=\"setColor('quadrant1Color','#000000')\">\n");
            htm.append("<area coords=\"18,178,34,194\" onclick=\"setColor('quadrant1Color','#003300')\">\n");
            htm.append("<area coords=\"34,178,50,194\" onclick=\"setColor('quadrant1Color','#006600')\">\n");
            htm.append("<area coords=\"50,178,66,194\" onclick=\"setColor('quadrant1Color','#009900')\">\n");
            htm.append("<area coords=\"66,178,82,194\" onclick=\"setColor('quadrant1Color','#00CC00')\">\n");
            htm.append("<area coords=\"82,178,98,194\" onclick=\"setColor('quadrant1Color','#00FF00')\">\n");
            htm.append("<area coords=\"98,178,114,194\" onclick=\"setColor('quadrant1Color','#99FF00')\">\n");
            htm.append("<area coords=\"114,178,130,194\" onclick=\"setColor('quadrant1Color','#99CC00')\">\n");
            htm.append("<area coords=\"130,178,146,194\" onclick=\"setColor('quadrant1Color','#999900')\">\n");
            htm.append("<area coords=\"146,178,162,194\" onclick=\"setColor('quadrant1Color','#996600')\">\n");
            htm.append("<area coords=\"162,178,178,194\" onclick=\"setColor('quadrant1Color','#993300')\">\n");
            htm.append("<area coords=\"178,178,194,194\" onclick=\"setColor('quadrant1Color','#990000')\">\n");
            htm.append("<area coords=\"194,178,210,194\" onclick=\"setColor('quadrant1Color','#CC0000')\">\n");
            htm.append("<area coords=\"210,178,226,194\" onclick=\"setColor('quadrant1Color','#CC3300')\">\n");
            htm.append("<area coords=\"226,178,242,194\" onclick=\"setColor('quadrant1Color','#CC6600')\">\n");
            htm.append("<area coords=\"242,178,258,194\" onclick=\"setColor('quadrant1Color','#CC9900')\">\n");
            htm.append("<area coords=\"258,178,274,194\" onclick=\"setColor('quadrant1Color','#CCCC00')\">\n");
            htm.append("<area coords=\"274,178,290,194\" onclick=\"setColor('quadrant1Color','#CCFF00')\">\n");
            htm.append("</map>\n");
            htm.append("</li>\n");

            // cuadrante 2
            htm.append("        <li class=\"swbform-li\">\n");
            htm.append("          <label for=\"quadrant2Color\" class=\"swbform-label\">Color del cuadrante II <i>(hexadecimal)</i></label>\n");
            htm.append("          <input type=\"text\" dojoType=\"dijit.form.TextBox\" id=\"quadrant2Color\" name=\"quadrant2Color\" value=\""+base.getAttribute("quadrant2Color","#FF0000")+"\" onblur=\"setColor('quadrant2Color',this.value);\" maxlength=\"7\"  />\n");
            htm.append("        </li>\n");
            htm.append("        <li class=\"swbform-li\">\n");
            htm.append("          <label class=\"swbform-label\"></label>\n");
            htm.append("          <img src=\"/swbadmin/images/colorgrid.gif\" width=\"292\" height=\"196\" border=\"0\" alt=\"RGB color mixer\" usemap=\"#gridmap2\" ismap=\"ismap\">\n");
            htm.append("          <map name=\"gridmap2\">\n");
            htm.append("<!--- Row 1 --->\n");
            htm.append("<area coords=\"2,2,18,18\" onclick=\"setColor('quadrant2Color','#330000')\">\n");
            htm.append("<area coords=\"18,2,34,18\" onclick=\"setColor('quadrant2Color','#333300')\">\n");
            htm.append("<area coords=\"34,2,50,18\" onclick=\"setColor('quadrant2Color','#336600')\">\n");
            htm.append("<area coords=\"50,2,66,18\" onclick=\"setColor('quadrant2Color','#339900')\">\n");
            htm.append("<area coords=\"66,2,82,18\" onclick=\"setColor('quadrant2Color','#33CC00')\">\n");
            htm.append("<area coords=\"82,2,98,18\" onclick=\"setColor('quadrant2Color','#33FF00')\">\n");
            htm.append("<area coords=\"98,2,114,18\" onclick=\"setColor('quadrant2Color','#66FF00')\">\n");
            htm.append("<area coords=\"114,2,130,18\" onclick=\"setColor('quadrant2Color','#66CC00')\">\n");
            htm.append("<area coords=\"130,2,146,18\" onclick=\"setColor('quadrant2Color','#669900')\">\n");
            htm.append("<area coords=\"146,2,162,18\" onclick=\"setColor('quadrant2Color','#666600')\">\n");
            htm.append("<area coords=\"162,2,178,18\" onclick=\"setColor('quadrant2Color','#663300')\">\n");
            htm.append("<area coords=\"178,2,194,18\" onclick=\"setColor('quadrant2Color','#660000')\">\n");
            htm.append("<area coords=\"194,2,210,18\" onclick=\"setColor('quadrant2Color','#FF0000')\">\n");
            htm.append("<area coords=\"210,2,226,18\" onclick=\"setColor('quadrant2Color','#FF3300')\">\n");
            htm.append("<area coords=\"226,2,242,18\" onclick=\"setColor('quadrant2Color','#FF6600')\">\n");
            htm.append("<area coords=\"242,2,258,18\" onclick=\"setColor('quadrant2Color','#FF9900')\">\n");
            htm.append("<area coords=\"258,2,274,18\" onclick=\"setColor('quadrant2Color','#FFCC00')\">\n");
            htm.append("<area coords=\"274,2,290,18\" onclick=\"setColor('quadrant2Color','#FFFF00')\">\n");
            htm.append("<!--- Row 2 --->\n");
            htm.append("<area coords=\"2,18,18,34\" onclick=\"setColor('quadrant2Color','#330033')\">\n");
            htm.append("<area coords=\"18,18,34,34\" onclick=\"setColor('quadrant2Color','#333333')\">\n");
            htm.append("<area coords=\"34,18,50,34\" onclick=\"setColor('quadrant2Color','#336633')\">\n");
            htm.append("<area coords=\"50,18,66,34\" onclick=\"setColor('quadrant2Color','#339933')\">\n");
            htm.append("<area coords=\"66,18,82,34\" onclick=\"setColor('quadrant2Color','#33CC33')\">\n");
            htm.append("<area coords=\"82,18,98,34\" onclick=\"setColor('quadrant2Color','#33FF33')\">\n");
            htm.append("<area coords=\"98,18,114,34\" onclick=\"setColor('quadrant2Color','#66FF33')\">\n");
            htm.append("<area coords=\"114,18,130,34\" onclick=\"setColor('quadrant2Color','#66CC33')\">\n");
            htm.append("<area coords=\"130,18,146,34\" onclick=\"setColor('quadrant2Color','#669933')\">\n");
            htm.append("<area coords=\"146,18,162,34\" onclick=\"setColor('quadrant2Color','#666633')\">\n");
            htm.append("<area coords=\"162,18,178,34\" onclick=\"setColor('quadrant2Color','#663333')\">\n");
            htm.append("<area coords=\"178,18,194,34\" onclick=\"setColor('quadrant2Color','#660033')\">\n");
            htm.append("<area coords=\"194,18,210,34\" onclick=\"setColor('quadrant2Color','#FF0033')\">\n");
            htm.append("<area coords=\"210,18,226,34\" onclick=\"setColor('quadrant2Color','#FF3333')\">\n");
            htm.append("<area coords=\"226,18,242,34\" onclick=\"setColor('quadrant2Color','#FF6633')\">\n");
            htm.append("<area coords=\"242,18,258,34\" onclick=\"setColor('quadrant2Color','#FF9933')\">\n");
            htm.append("<area coords=\"258,18,274,34\" onclick=\"setColor('quadrant2Color','#FFCC33')\">\n");
            htm.append("<area coords=\"274,18,290,34\" onclick=\"setColor('quadrant2Color','#FFFF33')\">\n");
            htm.append("<!--- Row 3 --->\n");
            htm.append("<area coords=\"2,34,18,50\" onclick=\"setColor('quadrant2Color','#330066')\">\n");
            htm.append("<area coords=\"18,34,34,50\" onclick=\"setColor('quadrant2Color','#333366')\">\n");
            htm.append("<area coords=\"34,34,50,50\" onclick=\"setColor('quadrant2Color','#336666')\">\n");
            htm.append("<area coords=\"50,34,66,50\" onclick=\"setColor('quadrant2Color','#339966')\">\n");
            htm.append("<area coords=\"66,34,82,50\" onclick=\"setColor('quadrant2Color','#33CC66')\">\n");
            htm.append("<area coords=\"82,34,98,50\" onclick=\"setColor('quadrant2Color','#33FF66')\">\n");
            htm.append("<area coords=\"98,34,114,50\" onclick=\"setColor('quadrant2Color','#66FF66')\">\n");
            htm.append("<area coords=\"114,34,130,50\" onclick=\"setColor('quadrant2Color','#66CC66')\">\n");
            htm.append("<area coords=\"130,34,146,50\" onclick=\"setColor('quadrant2Color','#669966')\">\n");
            htm.append("<area coords=\"146,34,162,50\" onclick=\"setColor('quadrant2Color','#666666')\">\n");
            htm.append("<area coords=\"162,34,178,50\" onclick=\"setColor('quadrant2Color','#663366')\">\n");
            htm.append("<area coords=\"178,34,194,50\" onclick=\"setColor('quadrant2Color','#660066')\">\n");
            htm.append("<area coords=\"194,34,210,50\" onclick=\"setColor('quadrant2Color','#FF0066')\">\n");
            htm.append("<area coords=\"210,34,226,50\" onclick=\"setColor('quadrant2Color','#FF3366')\">\n");
            htm.append("<area coords=\"226,34,242,50\" onclick=\"setColor('quadrant2Color','#FF6666')\">\n");
            htm.append("<area coords=\"242,34,258,50\" onclick=\"setColor('quadrant2Color','#FF9966')\">\n");
            htm.append("<area coords=\"258,34,274,50\" onclick=\"setColor('quadrant2Color','#FFCC66')\">\n");
            htm.append("<area coords=\"274,34,290,50\" onclick=\"setColor('quadrant2Color','#FFFF66')\">\n");
            htm.append("<!--- Row 4 --->\n");
            htm.append("<area coords=\"2,50,18,66\" onclick=\"setColor('quadrant2Color','#330099')\">\n");
            htm.append("<area coords=\"18,50,34,66\" onclick=\"setColor('quadrant2Color','#333399')\">\n");
            htm.append("<area coords=\"34,50,50,66\" onclick=\"setColor('quadrant2Color','#336699')\">\n");
            htm.append("<area coords=\"50,50,66,66\" onclick=\"setColor('quadrant2Color','#339999')\">\n");
            htm.append("<area coords=\"66,50,82,66\" onclick=\"setColor('quadrant2Color','#33CC99')\">\n");
            htm.append("<area coords=\"82,50,98,66\" onclick=\"setColor('quadrant2Color','#33FF99')\">\n");
            htm.append("<area coords=\"98,50,114,66\" onclick=\"setColor('quadrant2Color','#66FF99')\">\n");
            htm.append("<area coords=\"114,50,130,66\" onclick=\"setColor('quadrant2Color','#66CC99')\">\n");
            htm.append("<area coords=\"130,50,146,66\" onclick=\"setColor('quadrant2Color','#669999')\">\n");
            htm.append("<area coords=\"146,50,162,66\" onclick=\"setColor('quadrant2Color','#666699')\">\n");
            htm.append("<area coords=\"162,50,178,66\" onclick=\"setColor('quadrant2Color','#663399')\">\n");
            htm.append("<area coords=\"178,50,194,66\" onclick=\"setColor('quadrant2Color','#660099')\">\n");
            htm.append("<area coords=\"194,50,210,66\" onclick=\"setColor('quadrant2Color','#FF0099')\">\n");
            htm.append("<area coords=\"210,50,226,66\" onclick=\"setColor('quadrant2Color','#FF3399')\">\n");
            htm.append("<area coords=\"226,50,242,66\" onclick=\"setColor('quadrant2Color','#FF6699')\">\n");
            htm.append("<area coords=\"242,50,258,66\" onclick=\"setColor('quadrant2Color','#FF9999')\">\n");
            htm.append("<area coords=\"258,50,274,66\" onclick=\"setColor('quadrant2Color','#FFCC99')\">\n");
            htm.append("<area coords=\"274,50,290,66\" onclick=\"setColor('quadrant2Color','#FFFF99')\">\n");
            htm.append("<!--- Row 5 --->\n");
            htm.append("<area coords=\"2,66,18,82\" onclick=\"setColor('quadrant2Color','#3300CC')\">\n");
            htm.append("<area coords=\"18,66,34,82\" onclick=\"setColor('quadrant2Color','#3333CC')\">\n");
            htm.append("<area coords=\"34,66,50,82\" onclick=\"setColor('quadrant2Color','#3366CC')\">\n");
            htm.append("<area coords=\"50,66,66,82\" onclick=\"setColor('quadrant2Color','#3399CC')\">\n");
            htm.append("<area coords=\"66,66,82,82\" onclick=\"setColor('quadrant2Color','#33CCCC')\">\n");
            htm.append("<area coords=\"82,66,98,82\" onclick=\"setColor('quadrant2Color','#33FFCC')\">\n");
            htm.append("<area coords=\"98,66,114,82\" onclick=\"setColor('quadrant2Color','#66FFCC')\">\n");
            htm.append("<area coords=\"114,66,130,82\" onclick=\"setColor('quadrant2Color','#66CCCC')\">\n");
            htm.append("<area coords=\"130,66,146,82\" onclick=\"setColor('quadrant2Color','#6699CC')\">\n");
            htm.append("<area coords=\"146,66,162,82\" onclick=\"setColor('quadrant2Color','#6666CC')\">\n");
            htm.append("<area coords=\"162,66,178,82\" onclick=\"setColor('quadrant2Color','#6633CC')\">\n");
            htm.append("<area coords=\"178,66,194,82\" onclick=\"setColor('quadrant2Color','#6600CC')\">\n");
            htm.append("<area coords=\"194,66,210,82\" onclick=\"setColor('quadrant2Color','#FF00CC')\">\n");
            htm.append("<area coords=\"210,66,226,82\" onclick=\"setColor('quadrant2Color','#FF33CC')\">\n");
            htm.append("<area coords=\"226,66,242,82\" onclick=\"setColor('quadrant2Color','#FF66CC')\">\n");
            htm.append("<area coords=\"242,66,258,82\" onclick=\"setColor('quadrant2Color','#FF99CC')\">\n");
            htm.append("<area coords=\"258,66,274,82\" onclick=\"setColor('quadrant2Color','#FFCCCC')\">\n");
            htm.append("<area coords=\"274,66,290,82\" onclick=\"setColor('quadrant2Color','#FFFFCC')\">\n");
            htm.append("<!--- Row 6 --->\n");
            htm.append("<area coords=\"2,82,18,98\" onclick=\"setColor('quadrant2Color','#3300FF')\">\n");
            htm.append("<area coords=\"18,82,34,98\" onclick=\"setColor('quadrant2Color','#3333FF')\">\n");
            htm.append("<area coords=\"34,82,50,98\" onclick=\"setColor('quadrant2Color','#3366FF')\">\n");
            htm.append("<area coords=\"50,82,66,98\" onclick=\"setColor('quadrant2Color','#3399FF')\">\n");
            htm.append("<area coords=\"66,82,82,98\" onclick=\"setColor('quadrant2Color','#33CCFF')\">\n");
            htm.append("<area coords=\"82,82,98,98\" onclick=\"setColor('quadrant2Color','#33FFFF')\">\n");
            htm.append("<area coords=\"98,82,114,98\" onclick=\"setColor('quadrant2Color','#66FFFF')\">\n");
            htm.append("<area coords=\"114,82,130,98\" onclick=\"setColor('quadrant2Color','#66CCFF')\">\n");
            htm.append("<area coords=\"130,82,146,98\" onclick=\"setColor('quadrant2Color','#6699FF')\">\n");
            htm.append("<area coords=\"146,82,162,98\" onclick=\"setColor('quadrant2Color','#6666FF')\">\n");
            htm.append("<area coords=\"162,82,178,98\" onclick=\"setColor('quadrant2Color','#6633FF')\">\n");
            htm.append("<area coords=\"178,82,194,98\" onclick=\"setColor('quadrant2Color','#6600FF')\">\n");
            htm.append("<area coords=\"194,82,210,98\" onclick=\"setColor('quadrant2Color','#FF00FF')\">\n");
            htm.append("<area coords=\"210,82,226,98\" onclick=\"setColor('quadrant2Color','#FF33FF')\">\n");
            htm.append("<area coords=\"226,82,242,98\" onclick=\"setColor('quadrant2Color','#FF66FF')\">\n");
            htm.append("<area coords=\"242,82,258,98\" onclick=\"setColor('quadrant2Color','#FF99FF')\">\n");
            htm.append("<area coords=\"258,82,274,98\" onclick=\"setColor('quadrant2Color','#FFCCFF')\">\n");
            htm.append("<area coords=\"274,82,290,98\" onclick=\"setColor('quadrant2Color','#FFFFFF')\">\n");
            htm.append("<!--- Row 7 --->\n");
            htm.append("<area coords=\"2,98,18,114\" onclick=\"setColor('quadrant2Color','#0000FF')\">\n");
            htm.append("<area coords=\"18,98,34,114\" onclick=\"setColor('quadrant2Color','#0033FF')\">\n");
            htm.append("<area coords=\"34,98,50,114\" onclick=\"setColor('quadrant2Color','#0066FF')\">\n");
            htm.append("<area coords=\"50,98,66,114\" onclick=\"setColor('quadrant2Color','#0099FF')\">\n");
            htm.append("<area coords=\"66,98,82,114\" onclick=\"setColor('quadrant2Color','#00CCFF')\">\n");
            htm.append("<area coords=\"82,98,98,114\" onclick=\"setColor('quadrant2Color','#00FFFF')\">\n");
            htm.append("<area coords=\"98,98,114,114\" onclick=\"setColor('quadrant2Color','#99FFFF')\">\n");
            htm.append("<area coords=\"114,98,130,114\" onclick=\"setColor('quadrant2Color','#99CCFF')\">\n");
            htm.append("<area coords=\"130,98,146,114\" onclick=\"setColor('quadrant2Color','#9999FF')\">\n");
            htm.append("<area coords=\"146,98,162,114\" onclick=\"setColor('quadrant2Color','#9966FF')\">\n");
            htm.append("<area coords=\"162,98,178,114\" onclick=\"setColor('quadrant2Color','#9933FF')\">\n");
            htm.append("<area coords=\"178,98,194,114\" onclick=\"setColor('quadrant2Color','#9900FF')\">\n");
            htm.append("<area coords=\"194,98,210,114\" onclick=\"setColor('quadrant2Color','#CC00FF')\">\n");
            htm.append("<area coords=\"210,98,226,114\" onclick=\"setColor('quadrant2Color','#CC33FF')\">\n");
            htm.append("<area coords=\"226,98,242,114\" onclick=\"setColor('quadrant2Color','#CC66FF')\">\n");
            htm.append("<area coords=\"242,98,258,114\" onclick=\"setColor('quadrant2Color','#CC99FF')\">\n");
            htm.append("<area coords=\"258,98,274,114\" onclick=\"setColor('quadrant2Color','#CCCCFF')\">\n");
            htm.append("<area coords=\"274,98,290,114\" onclick=\"setColor('quadrant2Color','#CCFFFF')\">\n");
            htm.append("<!--- Row 8 --->\n");
            htm.append("<area coords=\"2,114,18,130\" onclick=\"setColor('quadrant2Color','#0000CC')\">\n");
            htm.append("<area coords=\"18,114,34,130\" onclick=\"setColor('quadrant2Color','#0033CC')\">\n");
            htm.append("<area coords=\"34,114,50,130\" onclick=\"setColor('quadrant2Color','#0066CC')\">\n");
            htm.append("<area coords=\"50,114,66,130\" onclick=\"setColor('quadrant2Color','#0099CC')\">\n");
            htm.append("<area coords=\"66,114,82,130\" onclick=\"setColor('quadrant2Color','#00CCCC')\">\n");
            htm.append("<area coords=\"82,114,98,130\" onclick=\"setColor('quadrant2Color','#00FFCC')\">\n");
            htm.append("<area coords=\"98,114,114,130\" onclick=\"setColor('quadrant2Color','#99FFCC')\">\n");
            htm.append("<area coords=\"114,114,130,130\" onclick=\"setColor('quadrant2Color','#99CCCC')\">\n");
            htm.append("<area coords=\"130,114,146,130\" onclick=\"setColor('quadrant2Color','#9999CC')\">\n");
            htm.append("<area coords=\"146,114,162,130\" onclick=\"setColor('quadrant2Color','#9966CC')\">\n");
            htm.append("<area coords=\"162,114,178,130\" onclick=\"setColor('quadrant2Color','#9933CC')\">\n");
            htm.append("<area coords=\"178,114,194,130\" onclick=\"setColor('quadrant2Color','#9900CC')\">\n");
            htm.append("<area coords=\"194,114,210,130\" onclick=\"setColor('quadrant2Color','#CC00CC')\">\n");
            htm.append("<area coords=\"210,114,226,130\" onclick=\"setColor('quadrant2Color','#CC33CC')\">\n");
            htm.append("<area coords=\"226,114,242,130\" onclick=\"setColor('quadrant2Color','#CC66CC')\">\n");
            htm.append("<area coords=\"242,114,258,130\" onclick=\"setColor('quadrant2Color','#CC99CC')\">\n");
            htm.append("<area coords=\"258,114,274,130\" onclick=\"setColor('quadrant2Color','#CCCCCC')\">\n");
            htm.append("<area coords=\"274,114,290,130\" onclick=\"setColor('quadrant2Color','#CCFFCC')\">\n");
            htm.append("<!--- Row 9 --->\n");
            htm.append("<area coords=\"2,130,18,146\" onclick=\"setColor('quadrant2Color','#000099')\">\n");
            htm.append("<area coords=\"18,130,34,146\" onclick=\"setColor('quadrant2Color','#003399')\">\n");
            htm.append("<area coords=\"34,130,50,146\" onclick=\"setColor('quadrant2Color','#006699')\">\n");
            htm.append("<area coords=\"50,130,66,146\" onclick=\"setColor('quadrant2Color','#009999')\">\n");
            htm.append("<area coords=\"66,130,82,146\" onclick=\"setColor('quadrant2Color','#00CC99')\">\n");
            htm.append("<area coords=\"82,130,98,146\" onclick=\"setColor('quadrant2Color','#00FF99')\">\n");
            htm.append("<area coords=\"98,130,114,146\" onclick=\"setColor('quadrant2Color','#99FF99')\">\n");
            htm.append("<area coords=\"114,130,130,146\" onclick=\"setColor('quadrant2Color','#99CC99')\">\n");
            htm.append("<area coords=\"130,130,146,146\" onclick=\"setColor('quadrant2Color','#999999')\">\n");
            htm.append("<area coords=\"146,130,162,146\" onclick=\"setColor('quadrant2Color','#996699')\">\n");
            htm.append("<area coords=\"162,130,178,146\" onclick=\"setColor('quadrant2Color','#993399')\">\n");
            htm.append("<area coords=\"178,130,194,146\" onclick=\"setColor('quadrant2Color','#990099')\">\n");
            htm.append("<area coords=\"194,130,210,146\" onclick=\"setColor('quadrant2Color','#CC0099')\">\n");
            htm.append("<area coords=\"210,130,226,146\" onclick=\"setColor('quadrant2Color','#CC3399')\">\n");
            htm.append("<area coords=\"226,130,242,146\" onclick=\"setColor('quadrant2Color','#CC6699')\">\n");
            htm.append("<area coords=\"242,130,258,146\" onclick=\"setColor('quadrant2Color','#CC9999')\">\n");
            htm.append("<area coords=\"258,130,274,146\" onclick=\"setColor('quadrant2Color','#CCCC99')\">\n");
            htm.append("<area coords=\"274,130,290,146\" onclick=\"setColor('quadrant2Color','#CCFF99')\">\n");
            htm.append("<!--- Row 10 --->\n");
            htm.append("<area coords=\"2,146,18,162\" onclick=\"setColor('quadrant2Color','#000066')\">\n");
            htm.append("<area coords=\"18,146,34,162\" onclick=\"setColor('quadrant2Color','#003366')\">\n");
            htm.append("<area coords=\"34,146,50,162\" onclick=\"setColor('quadrant2Color','#006666')\">\n");
            htm.append("<area coords=\"50,146,66,162\" onclick=\"setColor('quadrant2Color','#009966')\">\n");
            htm.append("<area coords=\"66,146,82,162\" onclick=\"setColor('quadrant2Color','#00CC66')\">\n");
            htm.append("<area coords=\"82,146,98,162\" onclick=\"setColor('quadrant2Color','#00FF66')\">\n");
            htm.append("<area coords=\"98,146,114,162\" onclick=\"setColor('quadrant2Color','#99FF66')\">\n");
            htm.append("<area coords=\"114,146,130,162\" onclick=\"setColor('quadrant2Color','#99CC66')\">\n");
            htm.append("<area coords=\"130,146,146,162\" onclick=\"setColor('quadrant2Color','#999966')\">\n");
            htm.append("<area coords=\"146,146,162,162\" onclick=\"setColor('quadrant2Color','#996666')\">\n");
            htm.append("<area coords=\"162,146,178,162\" onclick=\"setColor('quadrant2Color','#993366')\">\n");
            htm.append("<area coords=\"178,146,194,162\" onclick=\"setColor('quadrant2Color','#990066')\">\n");
            htm.append("<area coords=\"194,146,210,162\" onclick=\"setColor('quadrant2Color','#CC0066')\">\n");
            htm.append("<area coords=\"210,146,226,162\" onclick=\"setColor('quadrant2Color','#CC3366')\">\n");
            htm.append("<area coords=\"226,146,242,162\" onclick=\"setColor('quadrant2Color','#CC6666')\">\n");
            htm.append("<area coords=\"242,146,258,162\" onclick=\"setColor('quadrant2Color','#CC9966')\">\n");
            htm.append("<area coords=\"258,146,274,162\" onclick=\"setColor('quadrant2Color','#CCCC66')\">\n");
            htm.append("<area coords=\"274,146,290,162\" onclick=\"setColor('quadrant2Color','#CCFF66')\">\n");
            htm.append("<!--- Row 11 --->\n");
            htm.append("<area coords=\"2,162,18,178\" onclick=\"setColor('quadrant2Color','#000033')\">\n");
            htm.append("<area coords=\"18,162,34,178\" onclick=\"setColor('quadrant2Color','#003333')\">\n");
            htm.append("<area coords=\"34,162,50,178\" onclick=\"setColor('quadrant2Color','#006633')\">\n");
            htm.append("<area coords=\"50,162,66,178\" onclick=\"setColor('quadrant2Color','#009933')\">\n");
            htm.append("<area coords=\"66,162,82,178\" onclick=\"setColor('quadrant2Color','#00CC33')\">\n");
            htm.append("<area coords=\"82,162,98,178\" onclick=\"setColor('quadrant2Color','#00FF33')\">\n");
            htm.append("<area coords=\"98,162,114,178\" onclick=\"setColor('quadrant2Color','#99FF33')\">\n");
            htm.append("<area coords=\"114,162,130,178\" onclick=\"setColor('quadrant2Color','#99CC33')\">\n");
            htm.append("<area coords=\"130,162,146,178\" onclick=\"setColor('quadrant2Color','#999933')\">\n");
            htm.append("<area coords=\"146,162,162,178\" onclick=\"setColor('quadrant2Color','#996633')\">\n");
            htm.append("<area coords=\"162,162,178,178\" onclick=\"setColor('quadrant2Color','#993333')\">\n");
            htm.append("<area coords=\"178,162,194,178\" onclick=\"setColor('quadrant2Color','#990033')\">\n");
            htm.append("<area coords=\"194,162,210,178\" onclick=\"setColor('quadrant2Color','#CC0033')\">\n");
            htm.append("<area coords=\"210,162,226,178\" onclick=\"setColor('quadrant2Color','#CC3333')\">\n");
            htm.append("<area coords=\"226,162,242,178\" onclick=\"setColor('quadrant2Color','#CC6633')\">\n");
            htm.append("<area coords=\"242,162,258,178\" onclick=\"setColor('quadrant2Color','#CC9933')\">\n");
            htm.append("<area coords=\"258,162,274,178\" onclick=\"setColor('quadrant2Color','#CCCC33')\">\n");
            htm.append("<area coords=\"274,162,290,178\" onclick=\"setColor('quadrant2Color','#CCFF33')\">\n");
            htm.append("<!--- Row 12 --->\n");
            htm.append("<area coords=\"2,178,18,194\" onclick=\"setColor('quadrant2Color','#000000')\">\n");
            htm.append("<area coords=\"18,178,34,194\" onclick=\"setColor('quadrant2Color','#003300')\">\n");
            htm.append("<area coords=\"34,178,50,194\" onclick=\"setColor('quadrant2Color','#006600')\">\n");
            htm.append("<area coords=\"50,178,66,194\" onclick=\"setColor('quadrant2Color','#009900')\">\n");
            htm.append("<area coords=\"66,178,82,194\" onclick=\"setColor('quadrant2Color','#00CC00')\">\n");
            htm.append("<area coords=\"82,178,98,194\" onclick=\"setColor('quadrant2Color','#00FF00')\">\n");
            htm.append("<area coords=\"98,178,114,194\" onclick=\"setColor('quadrant2Color','#99FF00')\">\n");
            htm.append("<area coords=\"114,178,130,194\" onclick=\"setColor('quadrant2Color','#99CC00')\">\n");
            htm.append("<area coords=\"130,178,146,194\" onclick=\"setColor('quadrant2Color','#999900')\">\n");
            htm.append("<area coords=\"146,178,162,194\" onclick=\"setColor('quadrant2Color','#996600')\">\n");
            htm.append("<area coords=\"162,178,178,194\" onclick=\"setColor('quadrant2Color','#993300')\">\n");
            htm.append("<area coords=\"178,178,194,194\" onclick=\"setColor('quadrant2Color','#990000')\">\n");
            htm.append("<area coords=\"194,178,210,194\" onclick=\"setColor('quadrant2Color','#CC0000')\">\n");
            htm.append("<area coords=\"210,178,226,194\" onclick=\"setColor('quadrant2Color','#CC3300')\">\n");
            htm.append("<area coords=\"226,178,242,194\" onclick=\"setColor('quadrant2Color','#CC6600')\">\n");
            htm.append("<area coords=\"242,178,258,194\" onclick=\"setColor('quadrant2Color','#CC9900')\">\n");
            htm.append("<area coords=\"258,178,274,194\" onclick=\"setColor('quadrant2Color','#CCCC00')\">\n");
            htm.append("<area coords=\"274,178,290,194\" onclick=\"setColor('quadrant2Color','#CCFF00')\">\n");
            htm.append("</map>\n");
            htm.append("</li>\n");

            // cuadrante 3
            htm.append("        <li class=\"swbform-li\">\n");
            htm.append("          <label for=\"quadrant3Color\" class=\"swbform-label\">Color del cuadrante III <i>(hexadecimal)</i></label>\n");
            htm.append("          <input type=\"text\" dojoType=\"dijit.form.TextBox\" id=\"quadrant3Color\" name=\"quadrant3Color\" value=\""+base.getAttribute("quadrant3Color","#00FF00")+"\" onblur=\"setColor('quadrant3Color',this.value);\" maxlength=\"7\"  />\n");
            htm.append("        </li>\n");
            htm.append("        <li class=\"swbform-li\">\n");
            htm.append("          <label class=\"swbform-label\"></label>\n");
            htm.append("          <img src=\"/swbadmin/images/colorgrid.gif\" width=\"292\" height=\"196\" border=\"0\" alt=\"RGB color mixer\" usemap=\"#gridmap3\" ismap=\"ismap\">\n");
            htm.append("          <map name=\"gridmap3\">\n");
            htm.append("<!--- Row 1 --->\n");
            htm.append("<area coords=\"2,2,18,18\" onclick=\"setColor('quadrant3Color','#330000')\">\n");
            htm.append("<area coords=\"18,2,34,18\" onclick=\"setColor('quadrant3Color','#333300')\">\n");
            htm.append("<area coords=\"34,2,50,18\" onclick=\"setColor('quadrant3Color','#336600')\">\n");
            htm.append("<area coords=\"50,2,66,18\" onclick=\"setColor('quadrant3Color','#339900')\">\n");
            htm.append("<area coords=\"66,2,82,18\" onclick=\"setColor('quadrant3Color','#33CC00')\">\n");
            htm.append("<area coords=\"82,2,98,18\" onclick=\"setColor('quadrant3Color','#33FF00')\">\n");
            htm.append("<area coords=\"98,2,114,18\" onclick=\"setColor('quadrant3Color','#66FF00')\">\n");
            htm.append("<area coords=\"114,2,130,18\" onclick=\"setColor('quadrant3Color','#66CC00')\">\n");
            htm.append("<area coords=\"130,2,146,18\" onclick=\"setColor('quadrant3Color','#669900')\">\n");
            htm.append("<area coords=\"146,2,162,18\" onclick=\"setColor('quadrant3Color','#666600')\">\n");
            htm.append("<area coords=\"162,2,178,18\" onclick=\"setColor('quadrant3Color','#663300')\">\n");
            htm.append("<area coords=\"178,2,194,18\" onclick=\"setColor('quadrant3Color','#660000')\">\n");
            htm.append("<area coords=\"194,2,210,18\" onclick=\"setColor('quadrant3Color','#FF0000')\">\n");
            htm.append("<area coords=\"210,2,226,18\" onclick=\"setColor('quadrant3Color','#FF3300')\">\n");
            htm.append("<area coords=\"226,2,242,18\" onclick=\"setColor('quadrant3Color','#FF6600')\">\n");
            htm.append("<area coords=\"242,2,258,18\" onclick=\"setColor('quadrant3Color','#FF9900')\">\n");
            htm.append("<area coords=\"258,2,274,18\" onclick=\"setColor('quadrant3Color','#FFCC00')\">\n");
            htm.append("<area coords=\"274,2,290,18\" onclick=\"setColor('quadrant3Color','#FFFF00')\">\n");
            htm.append("<!--- Row 2 --->\n");
            htm.append("<area coords=\"2,18,18,34\" onclick=\"setColor('quadrant3Color','#330033')\">\n");
            htm.append("<area coords=\"18,18,34,34\" onclick=\"setColor('quadrant3Color','#333333')\">\n");
            htm.append("<area coords=\"34,18,50,34\" onclick=\"setColor('quadrant3Color','#336633')\">\n");
            htm.append("<area coords=\"50,18,66,34\" onclick=\"setColor('quadrant3Color','#339933')\">\n");
            htm.append("<area coords=\"66,18,82,34\" onclick=\"setColor('quadrant3Color','#33CC33')\">\n");
            htm.append("<area coords=\"82,18,98,34\" onclick=\"setColor('quadrant3Color','#33FF33')\">\n");
            htm.append("<area coords=\"98,18,114,34\" onclick=\"setColor('quadrant3Color','#66FF33')\">\n");
            htm.append("<area coords=\"114,18,130,34\" onclick=\"setColor('quadrant3Color','#66CC33')\">\n");
            htm.append("<area coords=\"130,18,146,34\" onclick=\"setColor('quadrant3Color','#669933')\">\n");
            htm.append("<area coords=\"146,18,162,34\" onclick=\"setColor('quadrant3Color','#666633')\">\n");
            htm.append("<area coords=\"162,18,178,34\" onclick=\"setColor('quadrant3Color','#663333')\">\n");
            htm.append("<area coords=\"178,18,194,34\" onclick=\"setColor('quadrant3Color','#660033')\">\n");
            htm.append("<area coords=\"194,18,210,34\" onclick=\"setColor('quadrant3Color','#FF0033')\">\n");
            htm.append("<area coords=\"210,18,226,34\" onclick=\"setColor('quadrant3Color','#FF3333')\">\n");
            htm.append("<area coords=\"226,18,242,34\" onclick=\"setColor('quadrant3Color','#FF6633')\">\n");
            htm.append("<area coords=\"242,18,258,34\" onclick=\"setColor('quadrant3Color','#FF9933')\">\n");
            htm.append("<area coords=\"258,18,274,34\" onclick=\"setColor('quadrant3Color','#FFCC33')\">\n");
            htm.append("<area coords=\"274,18,290,34\" onclick=\"setColor('quadrant3Color','#FFFF33')\">\n");
            htm.append("<!--- Row 3 --->\n");
            htm.append("<area coords=\"2,34,18,50\" onclick=\"setColor('quadrant3Color','#330066')\">\n");
            htm.append("<area coords=\"18,34,34,50\" onclick=\"setColor('quadrant3Color','#333366')\">\n");
            htm.append("<area coords=\"34,34,50,50\" onclick=\"setColor('quadrant3Color','#336666')\">\n");
            htm.append("<area coords=\"50,34,66,50\" onclick=\"setColor('quadrant3Color','#339966')\">\n");
            htm.append("<area coords=\"66,34,82,50\" onclick=\"setColor('quadrant3Color','#33CC66')\">\n");
            htm.append("<area coords=\"82,34,98,50\" onclick=\"setColor('quadrant3Color','#33FF66')\">\n");
            htm.append("<area coords=\"98,34,114,50\" onclick=\"setColor('quadrant3Color','#66FF66')\">\n");
            htm.append("<area coords=\"114,34,130,50\" onclick=\"setColor('quadrant3Color','#66CC66')\">\n");
            htm.append("<area coords=\"130,34,146,50\" onclick=\"setColor('quadrant3Color','#669966')\">\n");
            htm.append("<area coords=\"146,34,162,50\" onclick=\"setColor('quadrant3Color','#666666')\">\n");
            htm.append("<area coords=\"162,34,178,50\" onclick=\"setColor('quadrant3Color','#663366')\">\n");
            htm.append("<area coords=\"178,34,194,50\" onclick=\"setColor('quadrant3Color','#660066')\">\n");
            htm.append("<area coords=\"194,34,210,50\" onclick=\"setColor('quadrant3Color','#FF0066')\">\n");
            htm.append("<area coords=\"210,34,226,50\" onclick=\"setColor('quadrant3Color','#FF3366')\">\n");
            htm.append("<area coords=\"226,34,242,50\" onclick=\"setColor('quadrant3Color','#FF6666')\">\n");
            htm.append("<area coords=\"242,34,258,50\" onclick=\"setColor('quadrant3Color','#FF9966')\">\n");
            htm.append("<area coords=\"258,34,274,50\" onclick=\"setColor('quadrant3Color','#FFCC66')\">\n");
            htm.append("<area coords=\"274,34,290,50\" onclick=\"setColor('quadrant3Color','#FFFF66')\">\n");
            htm.append("<!--- Row 4 --->\n");
            htm.append("<area coords=\"2,50,18,66\" onclick=\"setColor('quadrant3Color','#330099')\">\n");
            htm.append("<area coords=\"18,50,34,66\" onclick=\"setColor('quadrant3Color','#333399')\">\n");
            htm.append("<area coords=\"34,50,50,66\" onclick=\"setColor('quadrant3Color','#336699')\">\n");
            htm.append("<area coords=\"50,50,66,66\" onclick=\"setColor('quadrant3Color','#339999')\">\n");
            htm.append("<area coords=\"66,50,82,66\" onclick=\"setColor('quadrant3Color','#33CC99')\">\n");
            htm.append("<area coords=\"82,50,98,66\" onclick=\"setColor('quadrant3Color','#33FF99')\">\n");
            htm.append("<area coords=\"98,50,114,66\" onclick=\"setColor('quadrant3Color','#66FF99')\">\n");
            htm.append("<area coords=\"114,50,130,66\" onclick=\"setColor('quadrant3Color','#66CC99')\">\n");
            htm.append("<area coords=\"130,50,146,66\" onclick=\"setColor('quadrant3Color','#669999')\">\n");
            htm.append("<area coords=\"146,50,162,66\" onclick=\"setColor('quadrant3Color','#666699')\">\n");
            htm.append("<area coords=\"162,50,178,66\" onclick=\"setColor('quadrant3Color','#663399')\">\n");
            htm.append("<area coords=\"178,50,194,66\" onclick=\"setColor('quadrant3Color','#660099')\">\n");
            htm.append("<area coords=\"194,50,210,66\" onclick=\"setColor('quadrant3Color','#FF0099')\">\n");
            htm.append("<area coords=\"210,50,226,66\" onclick=\"setColor('quadrant3Color','#FF3399')\">\n");
            htm.append("<area coords=\"226,50,242,66\" onclick=\"setColor('quadrant3Color','#FF6699')\">\n");
            htm.append("<area coords=\"242,50,258,66\" onclick=\"setColor('quadrant3Color','#FF9999')\">\n");
            htm.append("<area coords=\"258,50,274,66\" onclick=\"setColor('quadrant3Color','#FFCC99')\">\n");
            htm.append("<area coords=\"274,50,290,66\" onclick=\"setColor('quadrant3Color','#FFFF99')\">\n");
            htm.append("<!--- Row 5 --->\n");
            htm.append("<area coords=\"2,66,18,82\" onclick=\"setColor('quadrant3Color','#3300CC')\">\n");
            htm.append("<area coords=\"18,66,34,82\" onclick=\"setColor('quadrant3Color','#3333CC')\">\n");
            htm.append("<area coords=\"34,66,50,82\" onclick=\"setColor('quadrant3Color','#3366CC')\">\n");
            htm.append("<area coords=\"50,66,66,82\" onclick=\"setColor('quadrant3Color','#3399CC')\">\n");
            htm.append("<area coords=\"66,66,82,82\" onclick=\"setColor('quadrant3Color','#33CCCC')\">\n");
            htm.append("<area coords=\"82,66,98,82\" onclick=\"setColor('quadrant3Color','#33FFCC')\">\n");
            htm.append("<area coords=\"98,66,114,82\" onclick=\"setColor('quadrant3Color','#66FFCC')\">\n");
            htm.append("<area coords=\"114,66,130,82\" onclick=\"setColor('quadrant3Color','#66CCCC')\">\n");
            htm.append("<area coords=\"130,66,146,82\" onclick=\"setColor('quadrant3Color','#6699CC')\">\n");
            htm.append("<area coords=\"146,66,162,82\" onclick=\"setColor('quadrant3Color','#6666CC')\">\n");
            htm.append("<area coords=\"162,66,178,82\" onclick=\"setColor('quadrant3Color','#6633CC')\">\n");
            htm.append("<area coords=\"178,66,194,82\" onclick=\"setColor('quadrant3Color','#6600CC')\">\n");
            htm.append("<area coords=\"194,66,210,82\" onclick=\"setColor('quadrant3Color','#FF00CC')\">\n");
            htm.append("<area coords=\"210,66,226,82\" onclick=\"setColor('quadrant3Color','#FF33CC')\">\n");
            htm.append("<area coords=\"226,66,242,82\" onclick=\"setColor('quadrant3Color','#FF66CC')\">\n");
            htm.append("<area coords=\"242,66,258,82\" onclick=\"setColor('quadrant3Color','#FF99CC')\">\n");
            htm.append("<area coords=\"258,66,274,82\" onclick=\"setColor('quadrant3Color','#FFCCCC')\">\n");
            htm.append("<area coords=\"274,66,290,82\" onclick=\"setColor('quadrant3Color','#FFFFCC')\">\n");
            htm.append("<!--- Row 6 --->\n");
            htm.append("<area coords=\"2,82,18,98\" onclick=\"setColor('quadrant3Color','#3300FF')\">\n");
            htm.append("<area coords=\"18,82,34,98\" onclick=\"setColor('quadrant3Color','#3333FF')\">\n");
            htm.append("<area coords=\"34,82,50,98\" onclick=\"setColor('quadrant3Color','#3366FF')\">\n");
            htm.append("<area coords=\"50,82,66,98\" onclick=\"setColor('quadrant3Color','#3399FF')\">\n");
            htm.append("<area coords=\"66,82,82,98\" onclick=\"setColor('quadrant3Color','#33CCFF')\">\n");
            htm.append("<area coords=\"82,82,98,98\" onclick=\"setColor('quadrant3Color','#33FFFF')\">\n");
            htm.append("<area coords=\"98,82,114,98\" onclick=\"setColor('quadrant3Color','#66FFFF')\">\n");
            htm.append("<area coords=\"114,82,130,98\" onclick=\"setColor('quadrant3Color','#66CCFF')\">\n");
            htm.append("<area coords=\"130,82,146,98\" onclick=\"setColor('quadrant3Color','#6699FF')\">\n");
            htm.append("<area coords=\"146,82,162,98\" onclick=\"setColor('quadrant3Color','#6666FF')\">\n");
            htm.append("<area coords=\"162,82,178,98\" onclick=\"setColor('quadrant3Color','#6633FF')\">\n");
            htm.append("<area coords=\"178,82,194,98\" onclick=\"setColor('quadrant3Color','#6600FF')\">\n");
            htm.append("<area coords=\"194,82,210,98\" onclick=\"setColor('quadrant3Color','#FF00FF')\">\n");
            htm.append("<area coords=\"210,82,226,98\" onclick=\"setColor('quadrant3Color','#FF33FF')\">\n");
            htm.append("<area coords=\"226,82,242,98\" onclick=\"setColor('quadrant3Color','#FF66FF')\">\n");
            htm.append("<area coords=\"242,82,258,98\" onclick=\"setColor('quadrant3Color','#FF99FF')\">\n");
            htm.append("<area coords=\"258,82,274,98\" onclick=\"setColor('quadrant3Color','#FFCCFF')\">\n");
            htm.append("<area coords=\"274,82,290,98\" onclick=\"setColor('quadrant3Color','#FFFFFF')\">\n");
            htm.append("<!--- Row 7 --->\n");
            htm.append("<area coords=\"2,98,18,114\" onclick=\"setColor('quadrant3Color','#0000FF')\">\n");
            htm.append("<area coords=\"18,98,34,114\" onclick=\"setColor('quadrant3Color','#0033FF')\">\n");
            htm.append("<area coords=\"34,98,50,114\" onclick=\"setColor('quadrant3Color','#0066FF')\">\n");
            htm.append("<area coords=\"50,98,66,114\" onclick=\"setColor('quadrant3Color','#0099FF')\">\n");
            htm.append("<area coords=\"66,98,82,114\" onclick=\"setColor('quadrant3Color','#00CCFF')\">\n");
            htm.append("<area coords=\"82,98,98,114\" onclick=\"setColor('quadrant3Color','#00FFFF')\">\n");
            htm.append("<area coords=\"98,98,114,114\" onclick=\"setColor('quadrant3Color','#99FFFF')\">\n");
            htm.append("<area coords=\"114,98,130,114\" onclick=\"setColor('quadrant3Color','#99CCFF')\">\n");
            htm.append("<area coords=\"130,98,146,114\" onclick=\"setColor('quadrant3Color','#9999FF')\">\n");
            htm.append("<area coords=\"146,98,162,114\" onclick=\"setColor('quadrant3Color','#9966FF')\">\n");
            htm.append("<area coords=\"162,98,178,114\" onclick=\"setColor('quadrant3Color','#9933FF')\">\n");
            htm.append("<area coords=\"178,98,194,114\" onclick=\"setColor('quadrant3Color','#9900FF')\">\n");
            htm.append("<area coords=\"194,98,210,114\" onclick=\"setColor('quadrant3Color','#CC00FF')\">\n");
            htm.append("<area coords=\"210,98,226,114\" onclick=\"setColor('quadrant3Color','#CC33FF')\">\n");
            htm.append("<area coords=\"226,98,242,114\" onclick=\"setColor('quadrant3Color','#CC66FF')\">\n");
            htm.append("<area coords=\"242,98,258,114\" onclick=\"setColor('quadrant3Color','#CC99FF')\">\n");
            htm.append("<area coords=\"258,98,274,114\" onclick=\"setColor('quadrant3Color','#CCCCFF')\">\n");
            htm.append("<area coords=\"274,98,290,114\" onclick=\"setColor('quadrant3Color','#CCFFFF')\">\n");
            htm.append("<!--- Row 8 --->\n");
            htm.append("<area coords=\"2,114,18,130\" onclick=\"setColor('quadrant3Color','#0000CC')\">\n");
            htm.append("<area coords=\"18,114,34,130\" onclick=\"setColor('quadrant3Color','#0033CC')\">\n");
            htm.append("<area coords=\"34,114,50,130\" onclick=\"setColor('quadrant3Color','#0066CC')\">\n");
            htm.append("<area coords=\"50,114,66,130\" onclick=\"setColor('quadrant3Color','#0099CC')\">\n");
            htm.append("<area coords=\"66,114,82,130\" onclick=\"setColor('quadrant3Color','#00CCCC')\">\n");
            htm.append("<area coords=\"82,114,98,130\" onclick=\"setColor('quadrant3Color','#00FFCC')\">\n");
            htm.append("<area coords=\"98,114,114,130\" onclick=\"setColor('quadrant3Color','#99FFCC')\">\n");
            htm.append("<area coords=\"114,114,130,130\" onclick=\"setColor('quadrant3Color','#99CCCC')\">\n");
            htm.append("<area coords=\"130,114,146,130\" onclick=\"setColor('quadrant3Color','#9999CC')\">\n");
            htm.append("<area coords=\"146,114,162,130\" onclick=\"setColor('quadrant3Color','#9966CC')\">\n");
            htm.append("<area coords=\"162,114,178,130\" onclick=\"setColor('quadrant3Color','#9933CC')\">\n");
            htm.append("<area coords=\"178,114,194,130\" onclick=\"setColor('quadrant3Color','#9900CC')\">\n");
            htm.append("<area coords=\"194,114,210,130\" onclick=\"setColor('quadrant3Color','#CC00CC')\">\n");
            htm.append("<area coords=\"210,114,226,130\" onclick=\"setColor('quadrant3Color','#CC33CC')\">\n");
            htm.append("<area coords=\"226,114,242,130\" onclick=\"setColor('quadrant3Color','#CC66CC')\">\n");
            htm.append("<area coords=\"242,114,258,130\" onclick=\"setColor('quadrant3Color','#CC99CC')\">\n");
            htm.append("<area coords=\"258,114,274,130\" onclick=\"setColor('quadrant3Color','#CCCCCC')\">\n");
            htm.append("<area coords=\"274,114,290,130\" onclick=\"setColor('quadrant3Color','#CCFFCC')\">\n");
            htm.append("<!--- Row 9 --->\n");
            htm.append("<area coords=\"2,130,18,146\" onclick=\"setColor('quadrant3Color','#000099')\">\n");
            htm.append("<area coords=\"18,130,34,146\" onclick=\"setColor('quadrant3Color','#003399')\">\n");
            htm.append("<area coords=\"34,130,50,146\" onclick=\"setColor('quadrant3Color','#006699')\">\n");
            htm.append("<area coords=\"50,130,66,146\" onclick=\"setColor('quadrant3Color','#009999')\">\n");
            htm.append("<area coords=\"66,130,82,146\" onclick=\"setColor('quadrant3Color','#00CC99')\">\n");
            htm.append("<area coords=\"82,130,98,146\" onclick=\"setColor('quadrant3Color','#00FF99')\">\n");
            htm.append("<area coords=\"98,130,114,146\" onclick=\"setColor('quadrant3Color','#99FF99')\">\n");
            htm.append("<area coords=\"114,130,130,146\" onclick=\"setColor('quadrant3Color','#99CC99')\">\n");
            htm.append("<area coords=\"130,130,146,146\" onclick=\"setColor('quadrant3Color','#999999')\">\n");
            htm.append("<area coords=\"146,130,162,146\" onclick=\"setColor('quadrant3Color','#996699')\">\n");
            htm.append("<area coords=\"162,130,178,146\" onclick=\"setColor('quadrant3Color','#993399')\">\n");
            htm.append("<area coords=\"178,130,194,146\" onclick=\"setColor('quadrant3Color','#990099')\">\n");
            htm.append("<area coords=\"194,130,210,146\" onclick=\"setColor('quadrant3Color','#CC0099')\">\n");
            htm.append("<area coords=\"210,130,226,146\" onclick=\"setColor('quadrant3Color','#CC3399')\">\n");
            htm.append("<area coords=\"226,130,242,146\" onclick=\"setColor('quadrant3Color','#CC6699')\">\n");
            htm.append("<area coords=\"242,130,258,146\" onclick=\"setColor('quadrant3Color','#CC9999')\">\n");
            htm.append("<area coords=\"258,130,274,146\" onclick=\"setColor('quadrant3Color','#CCCC99')\">\n");
            htm.append("<area coords=\"274,130,290,146\" onclick=\"setColor('quadrant3Color','#CCFF99')\">\n");
            htm.append("<!--- Row 10 --->\n");
            htm.append("<area coords=\"2,146,18,162\" onclick=\"setColor('quadrant3Color','#000066')\">\n");
            htm.append("<area coords=\"18,146,34,162\" onclick=\"setColor('quadrant3Color','#003366')\">\n");
            htm.append("<area coords=\"34,146,50,162\" onclick=\"setColor('quadrant3Color','#006666')\">\n");
            htm.append("<area coords=\"50,146,66,162\" onclick=\"setColor('quadrant3Color','#009966')\">\n");
            htm.append("<area coords=\"66,146,82,162\" onclick=\"setColor('quadrant3Color','#00CC66')\">\n");
            htm.append("<area coords=\"82,146,98,162\" onclick=\"setColor('quadrant3Color','#00FF66')\">\n");
            htm.append("<area coords=\"98,146,114,162\" onclick=\"setColor('quadrant3Color','#99FF66')\">\n");
            htm.append("<area coords=\"114,146,130,162\" onclick=\"setColor('quadrant3Color','#99CC66')\">\n");
            htm.append("<area coords=\"130,146,146,162\" onclick=\"setColor('quadrant3Color','#999966')\">\n");
            htm.append("<area coords=\"146,146,162,162\" onclick=\"setColor('quadrant3Color','#996666')\">\n");
            htm.append("<area coords=\"162,146,178,162\" onclick=\"setColor('quadrant3Color','#993366')\">\n");
            htm.append("<area coords=\"178,146,194,162\" onclick=\"setColor('quadrant3Color','#990066')\">\n");
            htm.append("<area coords=\"194,146,210,162\" onclick=\"setColor('quadrant3Color','#CC0066')\">\n");
            htm.append("<area coords=\"210,146,226,162\" onclick=\"setColor('quadrant3Color','#CC3366')\">\n");
            htm.append("<area coords=\"226,146,242,162\" onclick=\"setColor('quadrant3Color','#CC6666')\">\n");
            htm.append("<area coords=\"242,146,258,162\" onclick=\"setColor('quadrant3Color','#CC9966')\">\n");
            htm.append("<area coords=\"258,146,274,162\" onclick=\"setColor('quadrant3Color','#CCCC66')\">\n");
            htm.append("<area coords=\"274,146,290,162\" onclick=\"setColor('quadrant3Color','#CCFF66')\">\n");
            htm.append("<!--- Row 11 --->\n");
            htm.append("<area coords=\"2,162,18,178\" onclick=\"setColor('quadrant3Color','#000033')\">\n");
            htm.append("<area coords=\"18,162,34,178\" onclick=\"setColor('quadrant3Color','#003333')\">\n");
            htm.append("<area coords=\"34,162,50,178\" onclick=\"setColor('quadrant3Color','#006633')\">\n");
            htm.append("<area coords=\"50,162,66,178\" onclick=\"setColor('quadrant3Color','#009933')\">\n");
            htm.append("<area coords=\"66,162,82,178\" onclick=\"setColor('quadrant3Color','#00CC33')\">\n");
            htm.append("<area coords=\"82,162,98,178\" onclick=\"setColor('quadrant3Color','#00FF33')\">\n");
            htm.append("<area coords=\"98,162,114,178\" onclick=\"setColor('quadrant3Color','#99FF33')\">\n");
            htm.append("<area coords=\"114,162,130,178\" onclick=\"setColor('quadrant3Color','#99CC33')\">\n");
            htm.append("<area coords=\"130,162,146,178\" onclick=\"setColor('quadrant3Color','#999933')\">\n");
            htm.append("<area coords=\"146,162,162,178\" onclick=\"setColor('quadrant3Color','#996633')\">\n");
            htm.append("<area coords=\"162,162,178,178\" onclick=\"setColor('quadrant3Color','#993333')\">\n");
            htm.append("<area coords=\"178,162,194,178\" onclick=\"setColor('quadrant3Color','#990033')\">\n");
            htm.append("<area coords=\"194,162,210,178\" onclick=\"setColor('quadrant3Color','#CC0033')\">\n");
            htm.append("<area coords=\"210,162,226,178\" onclick=\"setColor('quadrant3Color','#CC3333')\">\n");
            htm.append("<area coords=\"226,162,242,178\" onclick=\"setColor('quadrant3Color','#CC6633')\">\n");
            htm.append("<area coords=\"242,162,258,178\" onclick=\"setColor('quadrant3Color','#CC9933')\">\n");
            htm.append("<area coords=\"258,162,274,178\" onclick=\"setColor('quadrant3Color','#CCCC33')\">\n");
            htm.append("<area coords=\"274,162,290,178\" onclick=\"setColor('quadrant3Color','#CCFF33')\">\n");
            htm.append("<!--- Row 12 --->\n");
            htm.append("<area coords=\"2,178,18,194\" onclick=\"setColor('quadrant3Color','#000000')\">\n");
            htm.append("<area coords=\"18,178,34,194\" onclick=\"setColor('quadrant3Color','#003300')\">\n");
            htm.append("<area coords=\"34,178,50,194\" onclick=\"setColor('quadrant3Color','#006600')\">\n");
            htm.append("<area coords=\"50,178,66,194\" onclick=\"setColor('quadrant3Color','#009900')\">\n");
            htm.append("<area coords=\"66,178,82,194\" onclick=\"setColor('quadrant3Color','#00CC00')\">\n");
            htm.append("<area coords=\"82,178,98,194\" onclick=\"setColor('quadrant3Color','#00FF00')\">\n");
            htm.append("<area coords=\"98,178,114,194\" onclick=\"setColor('quadrant3Color','#99FF00')\">\n");
            htm.append("<area coords=\"114,178,130,194\" onclick=\"setColor('quadrant3Color','#99CC00')\">\n");
            htm.append("<area coords=\"130,178,146,194\" onclick=\"setColor('quadrant3Color','#999900')\">\n");
            htm.append("<area coords=\"146,178,162,194\" onclick=\"setColor('quadrant3Color','#996600')\">\n");
            htm.append("<area coords=\"162,178,178,194\" onclick=\"setColor('quadrant3Color','#993300')\">\n");
            htm.append("<area coords=\"178,178,194,194\" onclick=\"setColor('quadrant3Color','#990000')\">\n");
            htm.append("<area coords=\"194,178,210,194\" onclick=\"setColor('quadrant3Color','#CC0000')\">\n");
            htm.append("<area coords=\"210,178,226,194\" onclick=\"setColor('quadrant3Color','#CC3300')\">\n");
            htm.append("<area coords=\"226,178,242,194\" onclick=\"setColor('quadrant3Color','#CC6600')\">\n");
            htm.append("<area coords=\"242,178,258,194\" onclick=\"setColor('quadrant3Color','#CC9900')\">\n");
            htm.append("<area coords=\"258,178,274,194\" onclick=\"setColor('quadrant3Color','#CCCC00')\">\n");
            htm.append("<area coords=\"274,178,290,194\" onclick=\"setColor('quadrant3Color','#CCFF00')\">\n");
            htm.append("</map>\n");
            htm.append("</li>\n");

            // cuadrante 4
            htm.append("        <li class=\"swbform-li\">\n");
            htm.append("          <label for=\"quadrant4Color\" class=\"swbform-label\">Color del cuadrante IV <i>(hexadecimal)</i></label>\n");
            htm.append("          <input type=\"text\" dojoType=\"dijit.form.TextBox\" id=\"quadrant4Color\" name=\"quadrant4Color\" value=\""+base.getAttribute("quadrant4Color","#F7FE2E")+"\" onblur=\"setColor('quadrant4Color',this.value);\" maxlength=\"7\"  />\n");
            htm.append("        </li>\n");
            htm.append("        <li class=\"swbform-li\">\n");
            htm.append("          <label class=\"swbform-label\"></label>\n");
            htm.append("          <img src=\"/swbadmin/images/colorgrid.gif\" width=\"292\" height=\"196\" border=\"0\" alt=\"RGB color mixer\" usemap=\"#gridmap4\" ismap=\"ismap\">\n");
            htm.append("          <map name=\"gridmap4\">\n");
            htm.append("<!--- Row 1 --->\n");
            htm.append("<area coords=\"2,2,18,18\" onclick=\"setColor('quadrant4Color','#330000')\">\n");
            htm.append("<area coords=\"18,2,34,18\" onclick=\"setColor('quadrant4Color','#333300')\">\n");
            htm.append("<area coords=\"34,2,50,18\" onclick=\"setColor('quadrant4Color','#336600')\">\n");
            htm.append("<area coords=\"50,2,66,18\" onclick=\"setColor('quadrant4Color','#339900')\">\n");
            htm.append("<area coords=\"66,2,82,18\" onclick=\"setColor('quadrant4Color','#33CC00')\">\n");
            htm.append("<area coords=\"82,2,98,18\" onclick=\"setColor('quadrant4Color','#33FF00')\">\n");
            htm.append("<area coords=\"98,2,114,18\" onclick=\"setColor('quadrant4Color','#66FF00')\">\n");
            htm.append("<area coords=\"114,2,130,18\" onclick=\"setColor('quadrant4Color','#66CC00')\">\n");
            htm.append("<area coords=\"130,2,146,18\" onclick=\"setColor('quadrant4Color','#669900')\">\n");
            htm.append("<area coords=\"146,2,162,18\" onclick=\"setColor('quadrant4Color','#666600')\">\n");
            htm.append("<area coords=\"162,2,178,18\" onclick=\"setColor('quadrant4Color','#663300')\">\n");
            htm.append("<area coords=\"178,2,194,18\" onclick=\"setColor('quadrant4Color','#660000')\">\n");
            htm.append("<area coords=\"194,2,210,18\" onclick=\"setColor('quadrant4Color','#FF0000')\">\n");
            htm.append("<area coords=\"210,2,226,18\" onclick=\"setColor('quadrant4Color','#FF3300')\">\n");
            htm.append("<area coords=\"226,2,242,18\" onclick=\"setColor('quadrant4Color','#FF6600')\">\n");
            htm.append("<area coords=\"242,2,258,18\" onclick=\"setColor('quadrant4Color','#FF9900')\">\n");
            htm.append("<area coords=\"258,2,274,18\" onclick=\"setColor('quadrant4Color','#FFCC00')\">\n");
            htm.append("<area coords=\"274,2,290,18\" onclick=\"setColor('quadrant4Color','#FFFF00')\">\n");
            htm.append("<!--- Row 2 --->\n");
            htm.append("<area coords=\"2,18,18,34\" onclick=\"setColor('quadrant4Color','#330033')\">\n");
            htm.append("<area coords=\"18,18,34,34\" onclick=\"setColor('quadrant4Color','#333333')\">\n");
            htm.append("<area coords=\"34,18,50,34\" onclick=\"setColor('quadrant4Color','#336633')\">\n");
            htm.append("<area coords=\"50,18,66,34\" onclick=\"setColor('quadrant4Color','#339933')\">\n");
            htm.append("<area coords=\"66,18,82,34\" onclick=\"setColor('quadrant4Color','#33CC33')\">\n");
            htm.append("<area coords=\"82,18,98,34\" onclick=\"setColor('quadrant4Color','#33FF33')\">\n");
            htm.append("<area coords=\"98,18,114,34\" onclick=\"setColor('quadrant4Color','#66FF33')\">\n");
            htm.append("<area coords=\"114,18,130,34\" onclick=\"setColor('quadrant4Color','#66CC33')\">\n");
            htm.append("<area coords=\"130,18,146,34\" onclick=\"setColor('quadrant4Color','#669933')\">\n");
            htm.append("<area coords=\"146,18,162,34\" onclick=\"setColor('quadrant4Color','#666633')\">\n");
            htm.append("<area coords=\"162,18,178,34\" onclick=\"setColor('quadrant4Color','#663333')\">\n");
            htm.append("<area coords=\"178,18,194,34\" onclick=\"setColor('quadrant4Color','#660033')\">\n");
            htm.append("<area coords=\"194,18,210,34\" onclick=\"setColor('quadrant4Color','#FF0033')\">\n");
            htm.append("<area coords=\"210,18,226,34\" onclick=\"setColor('quadrant4Color','#FF3333')\">\n");
            htm.append("<area coords=\"226,18,242,34\" onclick=\"setColor('quadrant4Color','#FF6633')\">\n");
            htm.append("<area coords=\"242,18,258,34\" onclick=\"setColor('quadrant4Color','#FF9933')\">\n");
            htm.append("<area coords=\"258,18,274,34\" onclick=\"setColor('quadrant4Color','#FFCC33')\">\n");
            htm.append("<area coords=\"274,18,290,34\" onclick=\"setColor('quadrant4Color','#FFFF33')\">\n");
            htm.append("<!--- Row 3 --->\n");
            htm.append("<area coords=\"2,34,18,50\" onclick=\"setColor('quadrant4Color','#330066')\">\n");
            htm.append("<area coords=\"18,34,34,50\" onclick=\"setColor('quadrant4Color','#333366')\">\n");
            htm.append("<area coords=\"34,34,50,50\" onclick=\"setColor('quadrant4Color','#336666')\">\n");
            htm.append("<area coords=\"50,34,66,50\" onclick=\"setColor('quadrant4Color','#339966')\">\n");
            htm.append("<area coords=\"66,34,82,50\" onclick=\"setColor('quadrant4Color','#33CC66')\">\n");
            htm.append("<area coords=\"82,34,98,50\" onclick=\"setColor('quadrant4Color','#33FF66')\">\n");
            htm.append("<area coords=\"98,34,114,50\" onclick=\"setColor('quadrant4Color','#66FF66')\">\n");
            htm.append("<area coords=\"114,34,130,50\" onclick=\"setColor('quadrant4Color','#66CC66')\">\n");
            htm.append("<area coords=\"130,34,146,50\" onclick=\"setColor('quadrant4Color','#669966')\">\n");
            htm.append("<area coords=\"146,34,162,50\" onclick=\"setColor('quadrant4Color','#666666')\">\n");
            htm.append("<area coords=\"162,34,178,50\" onclick=\"setColor('quadrant4Color','#663366')\">\n");
            htm.append("<area coords=\"178,34,194,50\" onclick=\"setColor('quadrant4Color','#660066')\">\n");
            htm.append("<area coords=\"194,34,210,50\" onclick=\"setColor('quadrant4Color','#FF0066')\">\n");
            htm.append("<area coords=\"210,34,226,50\" onclick=\"setColor('quadrant4Color','#FF3366')\">\n");
            htm.append("<area coords=\"226,34,242,50\" onclick=\"setColor('quadrant4Color','#FF6666')\">\n");
            htm.append("<area coords=\"242,34,258,50\" onclick=\"setColor('quadrant4Color','#FF9966')\">\n");
            htm.append("<area coords=\"258,34,274,50\" onclick=\"setColor('quadrant4Color','#FFCC66')\">\n");
            htm.append("<area coords=\"274,34,290,50\" onclick=\"setColor('quadrant4Color','#FFFF66')\">\n");
            htm.append("<!--- Row 4 --->\n");
            htm.append("<area coords=\"2,50,18,66\" onclick=\"setColor('quadrant4Color','#330099')\">\n");
            htm.append("<area coords=\"18,50,34,66\" onclick=\"setColor('quadrant4Color','#333399')\">\n");
            htm.append("<area coords=\"34,50,50,66\" onclick=\"setColor('quadrant4Color','#336699')\">\n");
            htm.append("<area coords=\"50,50,66,66\" onclick=\"setColor('quadrant4Color','#339999')\">\n");
            htm.append("<area coords=\"66,50,82,66\" onclick=\"setColor('quadrant4Color','#33CC99')\">\n");
            htm.append("<area coords=\"82,50,98,66\" onclick=\"setColor('quadrant4Color','#33FF99')\">\n");
            htm.append("<area coords=\"98,50,114,66\" onclick=\"setColor('quadrant4Color','#66FF99')\">\n");
            htm.append("<area coords=\"114,50,130,66\" onclick=\"setColor('quadrant4Color','#66CC99')\">\n");
            htm.append("<area coords=\"130,50,146,66\" onclick=\"setColor('quadrant4Color','#669999')\">\n");
            htm.append("<area coords=\"146,50,162,66\" onclick=\"setColor('quadrant4Color','#666699')\">\n");
            htm.append("<area coords=\"162,50,178,66\" onclick=\"setColor('quadrant4Color','#663399')\">\n");
            htm.append("<area coords=\"178,50,194,66\" onclick=\"setColor('quadrant4Color','#660099')\">\n");
            htm.append("<area coords=\"194,50,210,66\" onclick=\"setColor('quadrant4Color','#FF0099')\">\n");
            htm.append("<area coords=\"210,50,226,66\" onclick=\"setColor('quadrant4Color','#FF3399')\">\n");
            htm.append("<area coords=\"226,50,242,66\" onclick=\"setColor('quadrant4Color','#FF6699')\">\n");
            htm.append("<area coords=\"242,50,258,66\" onclick=\"setColor('quadrant4Color','#FF9999')\">\n");
            htm.append("<area coords=\"258,50,274,66\" onclick=\"setColor('quadrant4Color','#FFCC99')\">\n");
            htm.append("<area coords=\"274,50,290,66\" onclick=\"setColor('quadrant4Color','#FFFF99')\">\n");
            htm.append("<!--- Row 5 --->\n");
            htm.append("<area coords=\"2,66,18,82\" onclick=\"setColor('quadrant4Color','#3300CC')\">\n");
            htm.append("<area coords=\"18,66,34,82\" onclick=\"setColor('quadrant4Color','#3333CC')\">\n");
            htm.append("<area coords=\"34,66,50,82\" onclick=\"setColor('quadrant4Color','#3366CC')\">\n");
            htm.append("<area coords=\"50,66,66,82\" onclick=\"setColor('quadrant4Color','#3399CC')\">\n");
            htm.append("<area coords=\"66,66,82,82\" onclick=\"setColor('quadrant4Color','#33CCCC')\">\n");
            htm.append("<area coords=\"82,66,98,82\" onclick=\"setColor('quadrant4Color','#33FFCC')\">\n");
            htm.append("<area coords=\"98,66,114,82\" onclick=\"setColor('quadrant4Color','#66FFCC')\">\n");
            htm.append("<area coords=\"114,66,130,82\" onclick=\"setColor('quadrant4Color','#66CCCC')\">\n");
            htm.append("<area coords=\"130,66,146,82\" onclick=\"setColor('quadrant4Color','#6699CC')\">\n");
            htm.append("<area coords=\"146,66,162,82\" onclick=\"setColor('quadrant4Color','#6666CC')\">\n");
            htm.append("<area coords=\"162,66,178,82\" onclick=\"setColor('quadrant4Color','#6633CC')\">\n");
            htm.append("<area coords=\"178,66,194,82\" onclick=\"setColor('quadrant4Color','#6600CC')\">\n");
            htm.append("<area coords=\"194,66,210,82\" onclick=\"setColor('quadrant4Color','#FF00CC')\">\n");
            htm.append("<area coords=\"210,66,226,82\" onclick=\"setColor('quadrant4Color','#FF33CC')\">\n");
            htm.append("<area coords=\"226,66,242,82\" onclick=\"setColor('quadrant4Color','#FF66CC')\">\n");
            htm.append("<area coords=\"242,66,258,82\" onclick=\"setColor('quadrant4Color','#FF99CC')\">\n");
            htm.append("<area coords=\"258,66,274,82\" onclick=\"setColor('quadrant4Color','#FFCCCC')\">\n");
            htm.append("<area coords=\"274,66,290,82\" onclick=\"setColor('quadrant4Color','#FFFFCC')\">\n");
            htm.append("<!--- Row 6 --->\n");
            htm.append("<area coords=\"2,82,18,98\" onclick=\"setColor('quadrant4Color','#3300FF')\">\n");
            htm.append("<area coords=\"18,82,34,98\" onclick=\"setColor('quadrant4Color','#3333FF')\">\n");
            htm.append("<area coords=\"34,82,50,98\" onclick=\"setColor('quadrant4Color','#3366FF')\">\n");
            htm.append("<area coords=\"50,82,66,98\" onclick=\"setColor('quadrant4Color','#3399FF')\">\n");
            htm.append("<area coords=\"66,82,82,98\" onclick=\"setColor('quadrant4Color','#33CCFF')\">\n");
            htm.append("<area coords=\"82,82,98,98\" onclick=\"setColor('quadrant4Color','#33FFFF')\">\n");
            htm.append("<area coords=\"98,82,114,98\" onclick=\"setColor('quadrant4Color','#66FFFF')\">\n");
            htm.append("<area coords=\"114,82,130,98\" onclick=\"setColor('quadrant4Color','#66CCFF')\">\n");
            htm.append("<area coords=\"130,82,146,98\" onclick=\"setColor('quadrant4Color','#6699FF')\">\n");
            htm.append("<area coords=\"146,82,162,98\" onclick=\"setColor('quadrant4Color','#6666FF')\">\n");
            htm.append("<area coords=\"162,82,178,98\" onclick=\"setColor('quadrant4Color','#6633FF')\">\n");
            htm.append("<area coords=\"178,82,194,98\" onclick=\"setColor('quadrant4Color','#6600FF')\">\n");
            htm.append("<area coords=\"194,82,210,98\" onclick=\"setColor('quadrant4Color','#FF00FF')\">\n");
            htm.append("<area coords=\"210,82,226,98\" onclick=\"setColor('quadrant4Color','#FF33FF')\">\n");
            htm.append("<area coords=\"226,82,242,98\" onclick=\"setColor('quadrant4Color','#FF66FF')\">\n");
            htm.append("<area coords=\"242,82,258,98\" onclick=\"setColor('quadrant4Color','#FF99FF')\">\n");
            htm.append("<area coords=\"258,82,274,98\" onclick=\"setColor('quadrant4Color','#FFCCFF')\">\n");
            htm.append("<area coords=\"274,82,290,98\" onclick=\"setColor('quadrant4Color','#FFFFFF')\">\n");
            htm.append("<!--- Row 7 --->\n");
            htm.append("<area coords=\"2,98,18,114\" onclick=\"setColor('quadrant4Color','#0000FF')\">\n");
            htm.append("<area coords=\"18,98,34,114\" onclick=\"setColor('quadrant4Color','#0033FF')\">\n");
            htm.append("<area coords=\"34,98,50,114\" onclick=\"setColor('quadrant4Color','#0066FF')\">\n");
            htm.append("<area coords=\"50,98,66,114\" onclick=\"setColor('quadrant4Color','#0099FF')\">\n");
            htm.append("<area coords=\"66,98,82,114\" onclick=\"setColor('quadrant4Color','#00CCFF')\">\n");
            htm.append("<area coords=\"82,98,98,114\" onclick=\"setColor('quadrant4Color','#00FFFF')\">\n");
            htm.append("<area coords=\"98,98,114,114\" onclick=\"setColor('quadrant4Color','#99FFFF')\">\n");
            htm.append("<area coords=\"114,98,130,114\" onclick=\"setColor('quadrant4Color','#99CCFF')\">\n");
            htm.append("<area coords=\"130,98,146,114\" onclick=\"setColor('quadrant4Color','#9999FF')\">\n");
            htm.append("<area coords=\"146,98,162,114\" onclick=\"setColor('quadrant4Color','#9966FF')\">\n");
            htm.append("<area coords=\"162,98,178,114\" onclick=\"setColor('quadrant4Color','#9933FF')\">\n");
            htm.append("<area coords=\"178,98,194,114\" onclick=\"setColor('quadrant4Color','#9900FF')\">\n");
            htm.append("<area coords=\"194,98,210,114\" onclick=\"setColor('quadrant4Color','#CC00FF')\">\n");
            htm.append("<area coords=\"210,98,226,114\" onclick=\"setColor('quadrant4Color','#CC33FF')\">\n");
            htm.append("<area coords=\"226,98,242,114\" onclick=\"setColor('quadrant4Color','#CC66FF')\">\n");
            htm.append("<area coords=\"242,98,258,114\" onclick=\"setColor('quadrant4Color','#CC99FF')\">\n");
            htm.append("<area coords=\"258,98,274,114\" onclick=\"setColor('quadrant4Color','#CCCCFF')\">\n");
            htm.append("<area coords=\"274,98,290,114\" onclick=\"setColor('quadrant4Color','#CCFFFF')\">\n");
            htm.append("<!--- Row 8 --->\n");
            htm.append("<area coords=\"2,114,18,130\" onclick=\"setColor('quadrant4Color','#0000CC')\">\n");
            htm.append("<area coords=\"18,114,34,130\" onclick=\"setColor('quadrant4Color','#0033CC')\">\n");
            htm.append("<area coords=\"34,114,50,130\" onclick=\"setColor('quadrant4Color','#0066CC')\">\n");
            htm.append("<area coords=\"50,114,66,130\" onclick=\"setColor('quadrant4Color','#0099CC')\">\n");
            htm.append("<area coords=\"66,114,82,130\" onclick=\"setColor('quadrant4Color','#00CCCC')\">\n");
            htm.append("<area coords=\"82,114,98,130\" onclick=\"setColor('quadrant4Color','#00FFCC')\">\n");
            htm.append("<area coords=\"98,114,114,130\" onclick=\"setColor('quadrant4Color','#99FFCC')\">\n");
            htm.append("<area coords=\"114,114,130,130\" onclick=\"setColor('quadrant4Color','#99CCCC')\">\n");
            htm.append("<area coords=\"130,114,146,130\" onclick=\"setColor('quadrant4Color','#9999CC')\">\n");
            htm.append("<area coords=\"146,114,162,130\" onclick=\"setColor('quadrant4Color','#9966CC')\">\n");
            htm.append("<area coords=\"162,114,178,130\" onclick=\"setColor('quadrant4Color','#9933CC')\">\n");
            htm.append("<area coords=\"178,114,194,130\" onclick=\"setColor('quadrant4Color','#9900CC')\">\n");
            htm.append("<area coords=\"194,114,210,130\" onclick=\"setColor('quadrant4Color','#CC00CC')\">\n");
            htm.append("<area coords=\"210,114,226,130\" onclick=\"setColor('quadrant4Color','#CC33CC')\">\n");
            htm.append("<area coords=\"226,114,242,130\" onclick=\"setColor('quadrant4Color','#CC66CC')\">\n");
            htm.append("<area coords=\"242,114,258,130\" onclick=\"setColor('quadrant4Color','#CC99CC')\">\n");
            htm.append("<area coords=\"258,114,274,130\" onclick=\"setColor('quadrant4Color','#CCCCCC')\">\n");
            htm.append("<area coords=\"274,114,290,130\" onclick=\"setColor('quadrant4Color','#CCFFCC')\">\n");
            htm.append("<!--- Row 9 --->\n");
            htm.append("<area coords=\"2,130,18,146\" onclick=\"setColor('quadrant4Color','#000099')\">\n");
            htm.append("<area coords=\"18,130,34,146\" onclick=\"setColor('quadrant4Color','#003399')\">\n");
            htm.append("<area coords=\"34,130,50,146\" onclick=\"setColor('quadrant4Color','#006699')\">\n");
            htm.append("<area coords=\"50,130,66,146\" onclick=\"setColor('quadrant4Color','#009999')\">\n");
            htm.append("<area coords=\"66,130,82,146\" onclick=\"setColor('quadrant4Color','#00CC99')\">\n");
            htm.append("<area coords=\"82,130,98,146\" onclick=\"setColor('quadrant4Color','#00FF99')\">\n");
            htm.append("<area coords=\"98,130,114,146\" onclick=\"setColor('quadrant4Color','#99FF99')\">\n");
            htm.append("<area coords=\"114,130,130,146\" onclick=\"setColor('quadrant4Color','#99CC99')\">\n");
            htm.append("<area coords=\"130,130,146,146\" onclick=\"setColor('quadrant4Color','#999999')\">\n");
            htm.append("<area coords=\"146,130,162,146\" onclick=\"setColor('quadrant4Color','#996699')\">\n");
            htm.append("<area coords=\"162,130,178,146\" onclick=\"setColor('quadrant4Color','#993399')\">\n");
            htm.append("<area coords=\"178,130,194,146\" onclick=\"setColor('quadrant4Color','#990099')\">\n");
            htm.append("<area coords=\"194,130,210,146\" onclick=\"setColor('quadrant4Color','#CC0099')\">\n");
            htm.append("<area coords=\"210,130,226,146\" onclick=\"setColor('quadrant4Color','#CC3399')\">\n");
            htm.append("<area coords=\"226,130,242,146\" onclick=\"setColor('quadrant4Color','#CC6699')\">\n");
            htm.append("<area coords=\"242,130,258,146\" onclick=\"setColor('quadrant4Color','#CC9999')\">\n");
            htm.append("<area coords=\"258,130,274,146\" onclick=\"setColor('quadrant4Color','#CCCC99')\">\n");
            htm.append("<area coords=\"274,130,290,146\" onclick=\"setColor('quadrant4Color','#CCFF99')\">\n");
            htm.append("<!--- Row 10 --->\n");
            htm.append("<area coords=\"2,146,18,162\" onclick=\"setColor('quadrant4Color','#000066')\">\n");
            htm.append("<area coords=\"18,146,34,162\" onclick=\"setColor('quadrant4Color','#003366')\">\n");
            htm.append("<area coords=\"34,146,50,162\" onclick=\"setColor('quadrant4Color','#006666')\">\n");
            htm.append("<area coords=\"50,146,66,162\" onclick=\"setColor('quadrant4Color','#009966')\">\n");
            htm.append("<area coords=\"66,146,82,162\" onclick=\"setColor('quadrant4Color','#00CC66')\">\n");
            htm.append("<area coords=\"82,146,98,162\" onclick=\"setColor('quadrant4Color','#00FF66')\">\n");
            htm.append("<area coords=\"98,146,114,162\" onclick=\"setColor('quadrant4Color','#99FF66')\">\n");
            htm.append("<area coords=\"114,146,130,162\" onclick=\"setColor('quadrant4Color','#99CC66')\">\n");
            htm.append("<area coords=\"130,146,146,162\" onclick=\"setColor('quadrant4Color','#999966')\">\n");
            htm.append("<area coords=\"146,146,162,162\" onclick=\"setColor('quadrant4Color','#996666')\">\n");
            htm.append("<area coords=\"162,146,178,162\" onclick=\"setColor('quadrant4Color','#993366')\">\n");
            htm.append("<area coords=\"178,146,194,162\" onclick=\"setColor('quadrant4Color','#990066')\">\n");
            htm.append("<area coords=\"194,146,210,162\" onclick=\"setColor('quadrant4Color','#CC0066')\">\n");
            htm.append("<area coords=\"210,146,226,162\" onclick=\"setColor('quadrant4Color','#CC3366')\">\n");
            htm.append("<area coords=\"226,146,242,162\" onclick=\"setColor('quadrant4Color','#CC6666')\">\n");
            htm.append("<area coords=\"242,146,258,162\" onclick=\"setColor('quadrant4Color','#CC9966')\">\n");
            htm.append("<area coords=\"258,146,274,162\" onclick=\"setColor('quadrant4Color','#CCCC66')\">\n");
            htm.append("<area coords=\"274,146,290,162\" onclick=\"setColor('quadrant4Color','#CCFF66')\">\n");
            htm.append("<!--- Row 11 --->\n");
            htm.append("<area coords=\"2,162,18,178\" onclick=\"setColor('quadrant4Color','#000033')\">\n");
            htm.append("<area coords=\"18,162,34,178\" onclick=\"setColor('quadrant4Color','#003333')\">\n");
            htm.append("<area coords=\"34,162,50,178\" onclick=\"setColor('quadrant4Color','#006633')\">\n");
            htm.append("<area coords=\"50,162,66,178\" onclick=\"setColor('quadrant4Color','#009933')\">\n");
            htm.append("<area coords=\"66,162,82,178\" onclick=\"setColor('quadrant4Color','#00CC33')\">\n");
            htm.append("<area coords=\"82,162,98,178\" onclick=\"setColor('quadrant4Color','#00FF33')\">\n");
            htm.append("<area coords=\"98,162,114,178\" onclick=\"setColor('quadrant4Color','#99FF33')\">\n");
            htm.append("<area coords=\"114,162,130,178\" onclick=\"setColor('quadrant4Color','#99CC33')\">\n");
            htm.append("<area coords=\"130,162,146,178\" onclick=\"setColor('quadrant4Color','#999933')\">\n");
            htm.append("<area coords=\"146,162,162,178\" onclick=\"setColor('quadrant4Color','#996633')\">\n");
            htm.append("<area coords=\"162,162,178,178\" onclick=\"setColor('quadrant4Color','#993333')\">\n");
            htm.append("<area coords=\"178,162,194,178\" onclick=\"setColor('quadrant4Color','#990033')\">\n");
            htm.append("<area coords=\"194,162,210,178\" onclick=\"setColor('quadrant4Color','#CC0033')\">\n");
            htm.append("<area coords=\"210,162,226,178\" onclick=\"setColor('quadrant4Color','#CC3333')\">\n");
            htm.append("<area coords=\"226,162,242,178\" onclick=\"setColor('quadrant4Color','#CC6633')\">\n");
            htm.append("<area coords=\"242,162,258,178\" onclick=\"setColor('quadrant4Color','#CC9933')\">\n");
            htm.append("<area coords=\"258,162,274,178\" onclick=\"setColor('quadrant4Color','#CCCC33')\">\n");
            htm.append("<area coords=\"274,162,290,178\" onclick=\"setColor('quadrant4Color','#CCFF33')\">\n");
            htm.append("<!--- Row 12 --->\n");
            htm.append("<area coords=\"2,178,18,194\" onclick=\"setColor('quadrant4Color','#000000')\">\n");
            htm.append("<area coords=\"18,178,34,194\" onclick=\"setColor('quadrant4Color','#003300')\">\n");
            htm.append("<area coords=\"34,178,50,194\" onclick=\"setColor('quadrant4Color','#006600')\">\n");
            htm.append("<area coords=\"50,178,66,194\" onclick=\"setColor('quadrant4Color','#009900')\">\n");
            htm.append("<area coords=\"66,178,82,194\" onclick=\"setColor('quadrant4Color','#00CC00')\">\n");
            htm.append("<area coords=\"82,178,98,194\" onclick=\"setColor('quadrant4Color','#00FF00')\">\n");
            htm.append("<area coords=\"98,178,114,194\" onclick=\"setColor('quadrant4Color','#99FF00')\">\n");
            htm.append("<area coords=\"114,178,130,194\" onclick=\"setColor('quadrant4Color','#99CC00')\">\n");
            htm.append("<area coords=\"130,178,146,194\" onclick=\"setColor('quadrant4Color','#999900')\">\n");
            htm.append("<area coords=\"146,178,162,194\" onclick=\"setColor('quadrant4Color','#996600')\">\n");
            htm.append("<area coords=\"162,178,178,194\" onclick=\"setColor('quadrant4Color','#993300')\">\n");
            htm.append("<area coords=\"178,178,194,194\" onclick=\"setColor('quadrant4Color','#990000')\">\n");
            htm.append("<area coords=\"194,178,210,194\" onclick=\"setColor('quadrant4Color','#CC0000')\">\n");
            htm.append("<area coords=\"210,178,226,194\" onclick=\"setColor('quadrant4Color','#CC3300')\">\n");
            htm.append("<area coords=\"226,178,242,194\" onclick=\"setColor('quadrant4Color','#CC6600')\">\n");
            htm.append("<area coords=\"242,178,258,194\" onclick=\"setColor('quadrant4Color','#CC9900')\">\n");
            htm.append("<area coords=\"258,178,274,194\" onclick=\"setColor('quadrant4Color','#CCCC00')\">\n");
            htm.append("<area coords=\"274,178,290,194\" onclick=\"setColor('quadrant4Color','#CCFF00')\">\n");
            htm.append("</map>\n");
            htm.append("</li>\n");
            htm.append("</ul>\n");
            htm.append("</fieldset>\n");
            htm.append("</div>\n");

            htm.append("<fieldset>\n");
//            htm.append("   <legend></legend>\n");
//            htm.append("   <ul class=\"swbform-ul\">\n");
//            htm.append("      <li>\n");
            htm.append("         <button type=\"submit\" dojoType=\"dijit.form.Button\">Guardar</button>\n");
            htm.append("         <button type=\"reset\" dojoType=\"dijit.form.Button\">Reestablecer</button>\n");
//            htm.append("      </li>\n");
//            htm.append("   </ul>\n");
            htm.append("</fieldset>\n");
            htm.append("</form>\n");
            htm.append("</div>\n");        
            out.print(htm.toString());
        }
        else if(Action_UPDATE.equals(action))
        {
            out.println("<script type=\"text/javascript\" language=\"JavaScript\">");
            out.println("   alert('Se actualizó exitosamente el recurso con identificador "+base.getId()+"');");
            out.println("   window.location.href='"+paramRequest.getRenderUrl().setAction(SWBParamRequest.Action_EDIT)+"';");
            out.println("</script>");
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        if(Action_UPDATE.equals(action)) {
            Resource base = response.getResourceBase();
            base.setAttribute("width", request.getParameter("width"));
            base.setAttribute("height", request.getParameter("height"));
            base.setAttribute("quadrant1Color", request.getParameter("quadrant1Color"));
            base.setAttribute("quadrant2Color", request.getParameter("quadrant2Color"));
            base.setAttribute("quadrant3Color", request.getParameter("quadrant3Color"));
            base.setAttribute("quadrant4Color", request.getParameter("quadrant4Color"));
            try {
                base.updateAttributesToDB();
                response.setAction(Action_UPDATE);
            }catch(SWBException e) {
                log.error(e);
                response.setAction(SWBActionResponse.Action_EDIT);
            }
        }
    }
    
    public Document getDom() throws XPathExpressionException, NumberFormatException
    {
        Resource base = getResourceBase();
        final BSC scorecard = (BSC)base.getWebSite();
        int width = assertValue(base.getAttribute("width", "1050"));
        int height = assertValue(base.getAttribute("height", "800"));
        Document documentBSC = scorecard.getDom();
        Element bsc = documentBSC.getDocumentElement();
        
        Document map = SWBUtils.XML.getNewDocument();
        Element root = map.createElement("riskmap");
        root.setAttribute("id", bsc.getAttribute("id"));
        root.setAttribute("width", Integer.toString(width));
        root.setAttribute("height", Integer.toString(height));
        map.appendChild(root);
        
                
        XPath xPath = XPathFactory.newInstance().newXPath();
        Node node;
        String expression;
        Element e;
        
        //header
        Element header = map.createElement("header");
        header.setAttribute("width", Integer.toString(width));
        header.setAttribute("height", Integer.toString(HEADER_HEIGHT));
        header.setAttribute("x", Integer.toString(PADDING_LEFT));
        header.setAttribute("y", Integer.toString(PADDING_TOP));
        root.appendChild(header);
        
        
        expression = "/bsc/title";
        node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        if(node!=null && node.getNodeType()==Node.ELEMENT_NODE) {
            e = map.createElement("title");
            e.appendChild(map.createTextNode(node.getFirstChild().getNodeValue()));
            header.appendChild(e);
        }
        
        expression = "/bsc/logo";
        node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        if(node!=null && node.getNodeType()==Node.ELEMENT_NODE) {
            NamedNodeMap attrs = node.getAttributes();
            e = map.createElement("logo");
            e.setAttribute("src", attrs.getNamedItem("src").getNodeValue());
            e.setAttribute("title", attrs.getNamedItem("title").getNodeValue());
            header.appendChild(e);
        }
        
        //riesgos
        expression = "/bsc/risks/risk";
        NodeList nlRisks = (NodeList)xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
        final int nlRisksCount = nlRisks.getLength();
        if(nlRisks.getLength()>0) {
            Element riskgroup = map.createElement("risks");
            root.appendChild(riskgroup);
            for(int k=0; k<nlRisksCount; k++) {
                node = nlRisks.item(k);
                if(node!=null && node.getNodeType()==Node.ELEMENT_NODE)
                {
                    NamedNodeMap attrs = node.getAttributes();
                    e = map.createElement("risk");
                    e.setAttribute("id", attrs.getNamedItem("id").getNodeValue());
                    e.setAttribute("prefix", attrs.getNamedItem("prefix").getNodeValue());
                    e.setAttribute("likehood", attrs.getNamedItem("likehood").getNodeValue());
                    e.setAttribute("impact", attrs.getNamedItem("impact").getNodeValue());
                    e.appendChild(map.createTextNode(node.getFirstChild().getNodeValue()));
                    riskgroup.appendChild(e);
                }
            }
        }
        return map;
    }
    
    private String getSvg() throws SWBResourceException, XPathExpressionException, NumberFormatException
    {
        Resource base = getResourceBase();
        final int width, height;
        int x, y=BOX_SPACING_TOP, w, h;
        Node node;
        NamedNodeMap attrs;
        String expression;
        String txt;
        
        Document map;
        try {
            map = getDom();
        }catch(XPathExpressionException xpathe) {
            return xpathe.getMessage();
        }
        
        User user = SWBContext.getSessionUser(getResourceBase().getWebSite().getUserRepository().getId());
        String lang = user.getLanguage();
        String bundle = getClass().getName();
        Locale locale = new Locale(lang);
        
        Element root = map.getDocumentElement();
        XPath xPath = XPathFactory.newInstance().newXPath();        
        width = assertValue(root.getAttribute("width"), 1050);
        height = assertValue(root.getAttribute("height"), 800); 
        StringBuilder SVGjs = new StringBuilder();
        
        SVGjs.append("<script type=\"text/javascript\">").append("\n");
        SVGjs.append(" var SVG_ = '"+SVG_NS_URI+"';").append("\n");
        SVGjs.append(" var XLINK_ = '"+XLNK_NS_URI+"';").append("\n");
        SVGjs.append(" var svg = document.createElementNS(SVG_,'svg'); ").append("\n");
        SVGjs.append(" svg.setAttributeNS(null,'id','"+root.getAttribute("id")+"');").append("\n");
        SVGjs.append(" svg.setAttributeNS(null,'width','"+width+"');").append("\n");
        SVGjs.append(" svg.setAttributeNS(null,'height','"+height+"');").append("\n");
        SVGjs.append(" svg.setAttributeNS(null,'viewBox','0,0,"+width+","+height+"');").append("\n");
        SVGjs.append(" svg.setAttributeNS(null,'version','1.1');").append("\n");
        SVGjs.append(" svg.setAttributeNS(null,'onload','init(evt)');").append("\n");
        SVGjs.append(" document.body.appendChild(svg);").append("\n");
        
        SVGjs.append(" var defs = document.createElementNS(SVG_, 'defs');").append("\n");
        SVGjs.append(" svg.appendChild(defs);").append("\n");
        
        SVGjs.append(" var pto;").append("\n");     // anchura
        SVGjs.append(" var g;").append("\n");       // grupo
        SVGjs.append(" var txt;").append("\n");     // texto
        SVGjs.append(" var rect;").append("\n");    // elemento
        SVGjs.append(" var path;").append("\n");    // flecha de la relación causa/efecto
        SVGjs.append(" var lnk;").append("\n");     // liga
        SVGjs.append(" var use;").append("\n");     // etiqueta use
        
        // Encabezado del mapa
        expression = "/riskmap/header";
        node = (Node) xPath.compile(expression).evaluate(map, XPathConstants.NODE);
        if(node!=null && node.getNodeType()==Node.ELEMENT_NODE) {
            SVGjs.append(" g = document.createElementNS(SVG_,'g');").append("\n");
            SVGjs.append(" svg.appendChild(g);").append("\n");
            
            attrs = node.getAttributes();
            x = assertValue(attrs.getNamedItem("x").getNodeValue(), PADDING_LEFT);
            y = assertValue(attrs.getNamedItem("y").getNodeValue(), PADDING_TOP);
            w = assertValue(attrs.getNamedItem("width").getNodeValue(), width);
            h = assertValue(attrs.getNamedItem("height").getNodeValue(), height);
            
            // Título mapa
            expression = "/riskmap/header/title";
            txt = (String) xPath.compile(expression).evaluate(map, XPathConstants.STRING);
            //txt = paramRequest.getLocaleString("lblRiskMap")+" "+paramRequest.getLocaleString("lblOf")+" "+txt;
            txt = SWBUtils.TEXT.getLocaleString(bundle, "lblRiskMap", locale)+" "+SWBUtils.TEXT.getLocaleString(bundle, "lblOf", locale)+" "+txt;
            SVGjs.append(" txt = createText('"+txt+"',"+(x+w/2)+","+(y+HEADER_1)+","+HEADER_1+",'Verdana');").append("\n");
            SVGjs.append(" txt.setAttributeNS(null,'text-anchor','middle');").append("\n");
            SVGjs.append(" g.appendChild(txt);").append("\n");
            
            // logo mapa
            expression = "/riskmap/header/logo";
            node = (Node) xPath.compile(expression).evaluate(map, XPathConstants.NODE);
            if(node!=null && node.getNodeType()==Node.ELEMENT_NODE) {
                attrs = node.getAttributes();
                if(attrs.getNamedItem("src").getNodeValue().isEmpty()) {
//                    SVGjs.append(" rect = createRect('"+root.getAttribute("id")+"_lg"+"',"+(w-BOX_SPACING)+","+(h-BOX_SPACING)+","+(x_+w_+PADDING_LEFT)+","+(y_-HEADER_2+PADDING_TOP)+",0,0,'none',1,'red',1,1);").append("\n");
//                    SVGjs.append(" g.appendChild(rect);").append("\n");
                }else {
//                    SVGjs.append(" var img = document.createElementNS(SVG_,'image');").append("\n");
//                    SVGjs.append(" img.setAttributeNS(null,'width',"+(w_-BOX_SPACING)+");").append("\n");
//                    SVGjs.append(" img.setAttributeNS(null,'height',"+(h_-BOX_SPACING)+");").append("\n");
//                    SVGjs.append(" img.setAttributeNS(null,'x',"+(x_+w_+PADDING_LEFT)+");").append("\n");
//                    SVGjs.append(" img.setAttributeNS(null,'y',"+(y_-HEADER_2+PADDING_TOP)+");").append("\n");
//                    SVGjs.append(" img.setAttributeNS(XLINK_,'href', '"+request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+attrs.getNamedItem("src").getNodeValue()+"');").append("\n");
//                    SVGjs.append(" img.setAttributeNS(null, 'visibility', 'visible');").append("\n");
//                    SVGjs.append(" g.appendChild(img);").append("\n");
                }
            }
        }
        
        // Plano cartesiano
        x = MARGEN_LEFT;
        y += HEADER_HEIGHT + MARGEN_TOP;
        int x_ = 0;
        int y_ = 0;
        int w_ = 120;
        int h_ = 120;
        
        SVGjs.append(" var x = "+x+";").append("\n");
        SVGjs.append(" var y = "+y+";").append("\n");
        
        // Gráfica de riestos
        SVGjs.append(" g = document.createElementNS(SVG_,'g');").append("\n");
        SVGjs.append(" svg.appendChild(g);").append("\n");
        SVGjs.append(" g.setAttributeNS(null,'transform','translate('+x+','+y+')');").append("\n");
        
        // Etiqueta "Mapa de riesgos XXX"
        //SVGjs.append(" txt = createText('"+paramRequest.getLocaleString("lblRiskMap")+"',"+w_+","+y_+","+HEADER_2+",'Verdana');").append("\n");
        SVGjs.append(" txt = createText('"+SWBUtils.TEXT.getLocaleString(bundle, "lblRiskMap", locale)+"',"+w_+","+y_+","+HEADER_2+",'Verdana');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'text-anchor','middle');").append("\n");
        SVGjs.append(" g.appendChild(txt);").append("\n");
        //SVGjs.append(" y_ = y_ + "+(HEADER_2+BOX_SPACING_BOTTOM)).append("\n");
        y_ = y_+HEADER_2+PADDING_DOWN;
        
        // def eje de coordenadas
        SVGjs.append(" txt = document.createElementNS(SVG_,'text');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'id','axis');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'font-size',"+HEADER_3+");").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'font-family','Verdana');").append("\n");
        SVGjs.append(" defs.appendChild(txt);").append("\n");
        // Escala 0
        SVGjs.append(" var text_node = document.createTextNode('0');").append("\n");
        SVGjs.append(" var tspan_element = document.createElementNS(SVG_, 'tspan');").append("\n");
        SVGjs.append(" tspan_element.appendChild(text_node);").append("\n");
        SVGjs.append(" txt.appendChild(tspan_element);").append("\n");        
        // Escala 1  2  3  4  5...
        SVGjs.append(" text_node = document.createTextNode('12345678910');").append("\n");
        SVGjs.append(" tspan_element = document.createElementNS(SVG_, 'tspan');").append("\n");
        SVGjs.append(" tspan_element.setAttributeNS(null,'dx','"+WORD_SPACING+"');").append("\n");
        SVGjs.append(" tspan_element.appendChild(text_node);").append("\n");
        SVGjs.append(" txt.appendChild(tspan_element);").append("\n");
        // Fin def eje de coordenadas
        
        // Cuadrantes
        SVGjs.append(" rect = createRect('quadrant_"+1+"_lg"+"',"+w_+","+h_+","+(x_)+","+y_+",0,0,'"+base.getAttribute("quadrant1Color","#0000FF")+"',1,'"+base.getAttribute("quadrant1Color","#0000FF")+"',1,1);").append("\n");
        SVGjs.append(" g.appendChild(rect);").append("\n");
        SVGjs.append(" rect = createRect('quadrant_"+2+"_lg"+"',"+w_+","+h_+","+(x_+w_+1)+","+y_+",0,0,'"+base.getAttribute("quadrant2Color","#FF0000")+"',1,'"+base.getAttribute("quadrant2Color","#FF0000")+"',1,1);").append("\n");
        SVGjs.append(" g.appendChild(rect);").append("\n");
        SVGjs.append(" rect = createRect('quadrant_"+3+"_lg"+"',"+w_+","+h_+","+(x_)+","+(y_+h_+1)+",0,0,'"+base.getAttribute("quadrant3Color","#00FF00")+"',1,'"+base.getAttribute("quadrant3Color","#00FF00")+"',1,1);").append("\n");
        SVGjs.append(" g.appendChild(rect);").append("\n");
        SVGjs.append(" rect = createRect('quadrant_"+4+"_lg"+"',"+w_+","+h_+","+(x_+w_+1)+","+(y_+h_+1)+",0,0,'"+base.getAttribute("quadrant4Color","#F7FE2E")+"',1,'"+base.getAttribute("quadrant4Color","#F7FE2E")+"',1,1);").append("\n");
        SVGjs.append(" g.appendChild(rect);").append("\n");
        
        // abscisas (valores de impacto)
        SVGjs.append(" use = document.createElementNS(SVG_,'use');").append("\n");
        SVGjs.append(" use.setAttributeNS(null,'x','"+(x_)+"');").append("\n");
        SVGjs.append(" use.setAttributeNS(null,'y','"+(y_+2*h_+BOX_SPACING)+"');").append("\n");
        SVGjs.append(" use.setAttributeNS(null,'style','visibility:visible;fill:#000000');").append("\n");
        SVGjs.append(" use.setAttributeNS(XLINK_,'xlink:href','#axis');").append("\n");
        SVGjs.append(" g.appendChild(use);").append("\n");
        // Etiqueta "Impacto"
        //SVGjs.append(" txt = createText('"+paramRequest.getLocaleString("lblImpact")+"',"+w_+","+(y_+2*h_+BOX_SPACING+HEADER_3)+","+HEADER_3+",'Verdana');").append("\n");
        SVGjs.append(" txt = createText('"+SWBUtils.TEXT.getLocaleString(bundle, "lblImpact", locale)+"',"+w_+","+(y_+2*h_+BOX_SPACING+HEADER_3)+","+HEADER_3+",'Verdana');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'text-anchor','middle');").append("\n");
        SVGjs.append(" g.appendChild(txt);").append("\n");
        
        // Ordenadas (valores de probabilidad)
        SVGjs.append(" use = document.createElementNS(SVG_,'use');").append("\n");
        SVGjs.append(" use.setAttributeNS(null,'x','"+(x_+2*w_+HEADER_3)+"');").append("\n");
        SVGjs.append(" use.setAttributeNS(null,'y','"+(y_+2*h_+BOX_SPACING-HEADER_3)+"');").append("\n");
        SVGjs.append(" use.setAttributeNS(null,'style','visibility:visible;fill:#000000');").append("\n");
        SVGjs.append(" use.setAttributeNS(null,'transform','rotate(270,"+(x_+2*w_+HEADER_3)+","+(y_+2*h_+BOX_SPACING-HEADER_3)+")');").append("\n");
        SVGjs.append(" use.setAttributeNS(XLINK_,'xlink:href','#axis');").append("\n");
        SVGjs.append(" g.appendChild(use);").append("\n");
        // Etiqueta "Probabilidad"
        //SVGjs.append(" txt = createText('"+paramRequest.getLocaleString("lblProbability")+"',"+(x_+2*w_+BOX_SPACING+HEADER_3)+","+h_+","+HEADER_3+",'Verdana');").append("\n");
        SVGjs.append(" txt = createText('"+SWBUtils.TEXT.getLocaleString(bundle, "lblProbability", locale)+"',"+(x_+2*w_+BOX_SPACING+HEADER_3)+","+h_+","+HEADER_3+",'Verdana');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'text-anchor','middle');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'transform','rotate(270,"+(x_+2*w_+BOX_SPACING+HEADER_3)+","+h_+")');").append("\n");
        SVGjs.append(" g.appendChild(txt);").append("\n");
        
        // Puntos en el plano
        expression = "/riskmap/risks/risk";
        NodeList nlRisk = (NodeList) xPath.compile(expression).evaluate(map, XPathConstants.NODESET);
        int aux = y_+2*h_;
        for(int j=0; j<nlRisk.getLength(); j++) {
            node = nlRisk.item(j);
            if(node!=null && node.getNodeType()==Node.ELEMENT_NODE) {
                attrs = node.getAttributes();
                String rid = attrs.getNamedItem("id").getNodeValue();
                String prefix = attrs.getNamedItem("prefix").getNodeValue();
                int impact = Integer.parseInt(attrs.getNamedItem("impact").getNodeValue());
                int likehood = Integer.parseInt(attrs.getNamedItem("likehood").getNodeValue());
                //SVGjs.append(" pto = createCircle('"+rid+"',"+(impact*30)+","+(aux-likehood*30)+",6,'#000000',1,1,1,1);").append("\n");
                SVGjs.append(" pto = createCircle('"+rid+"',"+(impact*24)+","+(aux-likehood*24)+",5,'#000000',1,'none',1,1);").append("\n");
                SVGjs.append(" pto.setAttributeNS(null,'id','"+prefix+" ("+impact+","+likehood+")');").append("\n");
                SVGjs.append(" pto.addEventListener('mousemove', showTooltip, false);").append("\n");
                SVGjs.append(" pto.addEventListener('mouseout', hideTooltip, false);").append("\n");
                SVGjs.append("").append("\n");
                SVGjs.append(" g.appendChild(pto);").append("\n");
            }
        }
        
        // Recuadro de gráfica
        SVGjs.append(" rect = getBBoxAsRectElement(g);").append("\n");
        SVGjs.append(" framingRect(rect,rect.width.baseVal.value+"+(BOX_SPACING_LEFT+BOX_SPACING_RIGHT)+",'none',1,'#000000',1,0,0);").append("\n");
        SVGjs.append(" rect.x.baseVal.value = rect.x.baseVal.value-"+BOX_SPACING_LEFT+";").append("\n");
        SVGjs.append(" g.appendChild(rect);").append("\n");
        
        // Leyenda de creación
        SVGjs.append(" pto = svg.createSVGPoint();").append("\n");
        SVGjs.append(" pto.x = rect.x.baseVal.value;").append("\n");
        SVGjs.append(" pto.y = rect.y.baseVal.value + rect.height.baseVal.value + "+(BOX_SPACING_BOTTOM+HEADER_4)+";").append("\n");
        SVGjs.append(" pto = pto.matrixTransform(g.getCTM());").append("\n");
        //SVGjs.append(" txt = createText('"+paramRequest.getLocaleString("lblCreated")+" "+new Date()+"',pto.x,pto.y,"+HEADER_4+",'Verdana');").append("\n");
        SVGjs.append(" txt = createText('"+SWBUtils.TEXT.getLocaleString(bundle, "lblProbability", locale)+" "+new Date()+"',pto.x,pto.y,"+HEADER_4+",'Verdana');").append("\n");
        SVGjs.append(" svg.appendChild(txt);").append("\n");
                
        // Tabla de riesgos
        // Calcular la posición del rectángulo del diagrama para trasladar la tabla de riesgos (transform=translate)
        // Recuadro de la gráfica
        SVGjs.append(" rect = getBBoxAsRectElement(g);").append("\n");
        SVGjs.append(" var w = Math.round(rect.width.baseVal.value);").append("\n");
        SVGjs.append(" x = x + w + "+MARGEN_LEFT+";").append("\n");
        
        // Listado de riesgos
        x_ = 0;
        y_ = 0;
        w_ = CELL_WIDTH;
        h_ = 0;
        
        SVGjs.append(" g = document.createElementNS(SVG_,'g');").append("\n");
        SVGjs.append(" svg.appendChild(g);").append("\n");
        SVGjs.append(" g.setAttributeNS(null,'transform','translate('+x+','+y+')');").append("\n");
        
        // Encabezado tabla
        SVGjs.append(" var x_ = 0;").append("\n");
        SVGjs.append(" var y_ = 0;").append("\n");
        SVGjs.append(" var h_;").append("\n");
        // Prefijo
        //SVGjs.append(" txt = createText('"+paramRequest.getLocaleString("lblRiskNumber")+"',x_,y_,"+HEADER_3+",'Verdana');").append("\n");
        SVGjs.append(" txt = createText('"+SWBUtils.TEXT.getLocaleString(bundle, "lblRiskNumber", locale)+"',x_,y_,"+HEADER_3+",'Verdana');").append("\n");
        SVGjs.append(" g.appendChild(txt);").append("\n");
        SVGjs.append(" fixParagraphToWidth(txt,"+w_+",x_);").append("\n");
        SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
        SVGjs.append(" framingRect(rect,"+w_+",'#9370db',0.3,'#9370db',1,0,0);").append("\n");
        SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
        SVGjs.append(" x_ = x_ + rect.width.baseVal.value + "+PADDING_LEFT+";").append("\n");
        SVGjs.append(" h_ = rect.height.baseVal.value;").append("\n");
        // descripción
        //SVGjs.append(" txt = createText('"+paramRequest.getLocaleString("lblRiskDescription")+"',x_,y_,"+HEADER_3+",'Verdana');").append("\n");
        SVGjs.append(" txt = createText('"+SWBUtils.TEXT.getLocaleString(bundle, "lblRiskDescription", locale)+"',x_,y_,"+HEADER_3+",'Verdana');").append("\n");
        SVGjs.append(" g.appendChild(txt);").append("\n");
        SVGjs.append(" fixParagraphToWidth(txt,"+(4*w_)+",x_);").append("\n");
        SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
        SVGjs.append(" framingRect(rect,"+(4*w_)+",'#9370db',0.3,'#9370db',1,0,0);").append("\n");
        SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
        SVGjs.append(" x_ = x_ + rect.width.baseVal.value + "+PADDING_LEFT+";").append("\n");
        SVGjs.append(" if(rect.height.baseVal.value>h_) {").append("\n");
        SVGjs.append("   h_ = rect.height.baseVal.value;").append("\n");
        SVGjs.append(" }").append("\n");
        // Impacto
        //SVGjs.append(" txt = createText('"+paramRequest.getLocaleString("lblImpact")+"',x_,y_,"+HEADER_3+",'Verdana');").append("\n");
        SVGjs.append(" txt = createText('"+SWBUtils.TEXT.getLocaleString(bundle, "lblImpact", locale)+"',x_,y_,"+HEADER_3+",'Verdana');").append("\n");
        SVGjs.append(" g.appendChild(txt);").append("\n");
        SVGjs.append(" fixParagraphToWidth(txt,"+w_+",x_);").append("\n");
        SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
        SVGjs.append(" framingRect(rect,"+w_+",'#9370db',0.3,'#9370db',1,0,0);").append("\n");
        SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
        SVGjs.append(" x_ = x_ + rect.width.baseVal.value + "+PADDING_LEFT+";").append("\n");
        SVGjs.append(" if(rect.height.baseVal.value>h_) {").append("\n");
        SVGjs.append("   h_ = rect.height.baseVal.value;").append("\n");
        SVGjs.append(" }").append("\n");
        // Probabilidad
        //SVGjs.append(" txt = createText('"+paramRequest.getLocaleString("lblProbability")+"',x_,y_,"+HEADER_3+",'Verdana');").append("\n");
        SVGjs.append(" txt = createText('"+SWBUtils.TEXT.getLocaleString(bundle, "lblProbability", locale)+"',x_,y_,"+HEADER_3+",'Verdana');").append("\n");
        SVGjs.append(" g.appendChild(txt);").append("\n");
        SVGjs.append(" fixParagraphToWidth(txt,"+w_+",x_);").append("\n");
        SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
        SVGjs.append(" framingRect(rect,"+w_+",'#9370db',0.3,'#9370db',1,0,0);").append("\n");
        SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
        SVGjs.append(" if(rect.height.baseVal.value>h_) {").append("\n");
        SVGjs.append("   h_ = rect.height.baseVal.value;").append("\n");
        SVGjs.append(" }").append("\n");
        // fin Encabezado tabla
        
        // Lista de riesgos
        for(int j=0; j<nlRisk.getLength(); j++) {
            node = nlRisk.item(j);
            if(node!=null && node.getNodeType()==Node.ELEMENT_NODE) {
                attrs = node.getAttributes();
                //String rid = attrs.getNamedItem("id").getNodeValue();
                String prefix = attrs.getNamedItem("prefix").getNodeValue();
                int impact = Integer.parseInt(attrs.getNamedItem("impact").getNodeValue());
                int likehood = Integer.parseInt(attrs.getNamedItem("likehood").getNodeValue());
                String title = node.getFirstChild().getNodeValue();
                // prefijo del riesgo
                SVGjs.append(" x_ = 0;").append("\n");
                SVGjs.append(" y_ = y_ + h_ + "+PADDING_LEFT+";").append("\n");
                SVGjs.append(" txt = createText('"+prefix+"',x_,y_,"+HEADER_3+",'Verdana');").append("\n");
                SVGjs.append(" g.appendChild(txt);").append("\n");
                SVGjs.append(" fixParagraphToWidth(txt,"+w_+",x_);").append("\n");
                SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
                SVGjs.append(" framingRect(rect,"+w_+",'#ffffff',1,'#ffffff',1,0,0);").append("\n");
                SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
                SVGjs.append(" h_ = rect.height.baseVal.value;").append("\n");
                // título del riesgo
                SVGjs.append(" x_ = x_+rect.width.baseVal.value+"+PADDING_LEFT+";").append("\n");
                SVGjs.append(" txt = createText('"+title+"',x_,y_,"+HEADER_3+",'Verdana');").append("\n");
                SVGjs.append(" g.appendChild(txt);").append("\n");
                SVGjs.append(" fixParagraphToWidth(txt,"+(4*w_)+",x_);").append("\n");
                SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
                SVGjs.append(" framingRect(rect,"+(4*w_)+",'#ffffff',1,'#ffffff',1,0,0);").append("\n");
                SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
                SVGjs.append(" if(rect.height.baseVal.value>h_) {").append("\n");
                SVGjs.append("   h_ = rect.height.baseVal.value;").append("\n");
                SVGjs.append(" }").append("\n");
                // impacto del riesgo
                SVGjs.append(" x_ = x_+rect.width.baseVal.value+"+PADDING_LEFT+";").append("\n");
                SVGjs.append(" txt = createText('"+impact+"',x_+"+(CELL_WIDTH-BOX_SPACING_RIGHT)+",y_,"+HEADER_3+",'Verdana');").append("\n");
                SVGjs.append(" txt.setAttributeNS(null,'text-anchor','end');").append("\n");
                SVGjs.append(" g.appendChild(txt);").append("\n");
                SVGjs.append(" fixParagraphToWidth(txt,"+w_+",x_);").append("\n");
                SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
                SVGjs.append(" framingRect(rect,"+w_+",'#ffffff',1,'#ffffff',1,0,0);").append("\n");
                SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
                SVGjs.append(" if(rect.height.baseVal.value>h_) {").append("\n");
                SVGjs.append("   h_ = rect.height.baseVal.value;").append("\n");
                SVGjs.append(" }").append("\n");
                // probabilidad del riesgo
                SVGjs.append(" x_ = x_+rect.width.baseVal.value+"+PADDING_LEFT+";").append("\n");
                SVGjs.append(" txt = createText('"+likehood+"',x_+"+(CELL_WIDTH-BOX_SPACING_RIGHT)+",y_,"+HEADER_3+",'Verdana');").append("\n");
                SVGjs.append(" txt.setAttributeNS(null,'text-anchor','end');").append("\n");
                SVGjs.append(" g.appendChild(txt);").append("\n");
                SVGjs.append(" fixParagraphToWidth(txt,"+w_+",x_);").append("\n");
                SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
                SVGjs.append(" framingRect(rect,"+w_+",'#ffffff',1,'#ffffff',1,0,0);").append("\n");
                SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
                SVGjs.append(" if(rect.height.baseVal.value>h_) {").append("\n");
                SVGjs.append("   h_ = rect.height.baseVal.value;").append("\n");
                SVGjs.append(" }").append("\n");
            }
        }
        
        // Tooltip
        SVGjs.append("").append("\n");
        SVGjs.append("").append("\n");
        SVGjs.append(" txt = createText('',0,0,"+HEADER_3+",'Verdana');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'id','tooltip');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'font-weight','bold');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'fill','#000000');").append("\n");
        SVGjs.append(" txt.setAttributeNS(null,'visibility','hidden');").append("\n");
        SVGjs.append(" svg.appendChild(txt);").append("\n");
//        SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
//        SVGjs.append(" rect.setAttributeNS(null,'id','tooltipbg');").append("\n");
//        SVGjs.append(" rect.setAttributeNS(null,'visibility','hidden');").append("\n");
//        SVGjs.append(" framingRect(rect,"+w_+",'#F2F5A9',1,'#F4FA58',1,5,5);").append("\n");
//        SVGjs.append(" svg.insertBefore(rect,txt);").append("\n");
        
        SVGjs.append("").append("\n");
        SVGjs.append("").append("\n");
        SVGjs.append("//---------------").append("\n");
        SVGjs.append("").append("\n");
        SVGjs.append("").append("\n");

        SVGjs.append(" function showTooltip(evt) {").append("\n");
        SVGjs.append("   var tooltip = evt.target.ownerDocument.getElementById('tooltip');").append("\n");
        SVGjs.append("   tooltip.setAttributeNS(null,'x',evt.clientX+5);").append("\n");
        SVGjs.append("   tooltip.setAttributeNS(null,'y',evt.clientY+5);").append("\n");
        SVGjs.append("   tooltip.textContent = evt.target.getAttribute('id');").append("\n");
        SVGjs.append("   tooltip.setAttributeNS(null,'visibility','visible');").append("\n");
        SVGjs.append(" }").append("\n");

        SVGjs.append(" function hideTooltip(evt) {").append("\n");
        SVGjs.append("   var tooltip = evt.target.ownerDocument.getElementById('tooltip');").append("\n");
        SVGjs.append("   tooltip.setAttributeNS(null,'visibility','hidden');").append("\n");
        SVGjs.append(" }").append("\n");
        
        // funciones
        SVGjs.append("function createLink(url) {").append("\n");
        SVGjs.append("    var a = document.createElementNS(SVG_ ,'a');").append("\n");
        SVGjs.append("    a.setAttributeNS(XLINK_,'xlink:href',url);").append("\n");
        SVGjs.append("    a.setAttributeNS(null,'title','Ver detalle...');").append("\n");
        SVGjs.append("    return a;").append("\n");
        SVGjs.append("}").append("\n");
        
        SVGjs.append("function createPath(id,x1,y1,x2,y2) {").append("\n");
        SVGjs.append("  var path = document.createElementNS(SVG_,'path');").append("\n");
        SVGjs.append("  var d = 'M'+x1+','+y1+' L'+x2+','+y2;").append("\n");
        SVGjs.append("  path.setAttributeNS(null, 'id', id);").append("\n");
        SVGjs.append("  path.setAttributeNS(null, 'd', d);").append("\n");
        SVGjs.append("  path.style.fill = 'none';").append("\n");
        SVGjs.append("  path.style.stroke = '#000000';").append("\n");
        SVGjs.append("  path.style.strokeWidth = '1';").append("\n");
        SVGjs.append("  return path;").append("\n");
        SVGjs.append("}").append("\n");

        SVGjs.append("function createText(text,x,y,fontsize,fontfamily) {").append("\n");
        SVGjs.append("  var txt = document.createElementNS(SVG_,'text');").append("\n");
        SVGjs.append("  txt.setAttributeNS(null,'x',x);").append("\n");
        SVGjs.append("  txt.setAttributeNS(null,'y',y);").append("\n");
        SVGjs.append("  txt.setAttributeNS(null,'font-size',fontsize);").append("\n");
        SVGjs.append("  txt.setAttributeNS(null,'font-family',fontfamily);").append("\n");
        SVGjs.append("  txt.textContent=text;").append("\n");
        SVGjs.append("  return txt;").append("\n");
        SVGjs.append("}").append("\n");
        
        SVGjs.append("function createCircle(id,cx,cy,r,fill,fillopacity,stroke,strokewidth, strokeopacity) {").append("\n");
        SVGjs.append("  var circle = document.createElementNS(SVG_,'circle');").append("\n");
        SVGjs.append("  circle.setAttributeNS(null,'id',id);").append("\n");
        SVGjs.append("  circle.setAttributeNS(null,'cx',cx);").append("\n");
        SVGjs.append("  circle.setAttributeNS(null,'cy',cy);").append("\n");
        SVGjs.append("  circle.setAttributeNS(null,'r',r);").append("\n");
        SVGjs.append("  circle.setAttributeNS(null,'fill',fill);").append("\n");
        SVGjs.append("  circle.setAttributeNS(null,'fill-opacity',fillopacity);").append("\n");
        SVGjs.append("  circle.setAttributeNS(null,'stroke',stroke);").append("\n");
        SVGjs.append("  circle.setAttributeNS(null, 'stroke-width',strokewidth);").append("\n");
        SVGjs.append("  circle.setAttributeNS(null, 'stroke-opacity',strokeopacity);").append("\n");
        SVGjs.append("  return circle;").append("\n");
        SVGjs.append("}").append("\n");

        SVGjs.append("function createRect(id,width,height,x,y,rx,ry,fill,fillopacity,stroke,strokewidth, strokeopacity) {").append("\n");
        SVGjs.append("  var rect = document.createElementNS(SVG_,'rect');").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'id',id);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'width',width);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'height',height);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'x',x);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'y',y);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'rx',rx);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'ry',ry);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'fill',fill);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'fill-opacity',fillopacity);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'stroke',stroke);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null, 'stroke-width',strokewidth);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null, 'stroke-opacity',strokeopacity);").append("\n");
        SVGjs.append("  return rect;").append("\n");
        SVGjs.append("}").append("\n");

        SVGjs.append("function framingRect(rect,width, fill, fillOpacity, stroke, strokeOpacity, rx, ry) {").append("\n");
        SVGjs.append("  rect.width.baseVal.value = width;").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'fill',fill);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'fill-opacity',fillOpacity);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'stroke',stroke);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null, 'stroke-width','1');").append("\n");
        SVGjs.append("  rect.setAttributeNS(null, 'stroke-opacity',strokeOpacity);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null, 'rx',rx);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null, 'ry',ry);").append("\n");
        SVGjs.append("}").append("\n");

        SVGjs.append(" function fixParagraphAtBounding(text_element, width, height, x, y) {").append("\n");
        SVGjs.append("     var dy = getFontSize(text_element);").append("\n");
        SVGjs.append("     if(dy<13) {").append("\n");
        SVGjs.append("     	createParagraph(text_element, width, height, x, y);").append("\n");
        SVGjs.append("     }else {").append("\n");
        SVGjs.append("     	fixParagraphAtBounding_(text_element, width, height, x, y, dy);").append("\n");
        SVGjs.append("     }").append("\n");
        SVGjs.append(" }").append("\n");

        SVGjs.append(" function fixParagraphAtBounding_(text_element, width, height, x, y, dy) {").append("\n");
        SVGjs.append("     var text = text_element.textContent;").append("\n");
        SVGjs.append("     var words = text.split(' ');").append("\n");
        SVGjs.append("     var tspan_element = document.createElementNS(SVG_, 'tspan');").append("\n");
        SVGjs.append("     var text_node = document.createTextNode(words[0]);").append("\n");
        SVGjs.append("     text_element.textContent='';").append("\n");
        SVGjs.append("     tspan_element.appendChild(text_node);").append("\n");
        SVGjs.append("     text_element.appendChild(tspan_element);").append("\n");
        SVGjs.append("     var h;").append("\n");
        SVGjs.append("     for(var i=1; i<words.length; i++) {").append("\n");
        SVGjs.append("         h = getBoundingHeight(text_element);").append("\n");
        SVGjs.append("         var len = tspan_element.firstChild.data.length;").append("\n");
        SVGjs.append("         tspan_element.firstChild.data += ' ' + words[i];").append("\n");
        SVGjs.append("         if (tspan_element.getComputedTextLength() > width) {").append("\n");
        SVGjs.append("             dy = dy - (h/height);").append("\n");
        SVGjs.append("             text_element.setAttributeNS(null, 'font-size', dy);").append("\n");
        SVGjs.append("             var childElements = text_element.getElementsByTagName('tspan');").append("\n");
        SVGjs.append("             for (var j=0; j<childElements.length; j++) {").append("\n");
        SVGjs.append("                 if(childElements[j].getAttribute('dy')) {").append("\n");
        SVGjs.append("                     childElements[j].setAttributeNS(null,'dy',dy);").append("\n");
        SVGjs.append("                 }").append("\n");
        SVGjs.append("             }").append("\n");
        SVGjs.append("             h = getBoundingHeight(text_element);").append("\n");
        SVGjs.append("             if (tspan_element.getComputedTextLength() > width) {").append("\n");
        SVGjs.append("                 tspan_element.firstChild.data = tspan_element.firstChild.data.slice(0, len);").append("\n");
        SVGjs.append("                 var tspan_element = document.createElementNS(SVG_, 'tspan');").append("\n");
        SVGjs.append("                 tspan_element.setAttributeNS(null, 'x', x);").append("\n");
        SVGjs.append("                 tspan_element.setAttributeNS(null, 'dy', dy);").append("\n");
        SVGjs.append("                 text_node = document.createTextNode(words[i]);").append("\n");
        SVGjs.append("                 tspan_element.appendChild(text_node);").append("\n");
        SVGjs.append("                 text_element.appendChild(tspan_element);").append("\n");
        SVGjs.append("             }").append("\n");
        SVGjs.append("         }").append("\n");
        SVGjs.append("     }").append("\n");
        SVGjs.append("     h = getBoundingHeight(text_element);").append("\n");
        SVGjs.append("     while(h>height && dy>0) {").append("\n");
        SVGjs.append("         dy--;").append("\n");
        SVGjs.append("         text_element.setAttributeNS(null, 'font-size', dy);").append("\n");
        SVGjs.append("         var childElements = text_element.getElementsByTagName('tspan');").append("\n");
        SVGjs.append("         for (var i=0; i < childElements.length; i++) {").append("\n");
        SVGjs.append("             if(childElements[i].getAttribute('dy')) {").append("\n");
        SVGjs.append("                 childElements[i].setAttributeNS(null,'dy',dy-0.5);").append("\n");
        SVGjs.append("             }").append("\n");
        SVGjs.append("         }").append("\n");
        SVGjs.append("         h = getBoundingHeight(text_element);").append("\n");
        SVGjs.append("     }").append("\n");
        SVGjs.append(" }").append("\n");

        SVGjs.append(" function getFontSize(text_element) {").append("\n");
        SVGjs.append("  var fs_ = window.getComputedStyle(text_element, null).getPropertyValue('font-size');").append("\n");
        SVGjs.append("  var fs = fs_.replace( /\\D+/g, '');").append("\n");
        SVGjs.append("  return fs;").append("\n");
        SVGjs.append(" }").append("\n");

        SVGjs.append(" function getBoundingHeight(objNode) {").append("\n");
        SVGjs.append("  if(!objNode.getBBox) {").append("\n");
        SVGjs.append("      if(objNode.correspondingUseElement) {").append("\n");
        SVGjs.append("          objNode = objNode.correspondingUseElement;").append("\n");
        SVGjs.append("      }").append("\n");
        SVGjs.append("  }").append("\n");
        SVGjs.append("  var bbox = objNode.getBBox();").append("\n");
        SVGjs.append("  return bbox.height;").append("\n");
        SVGjs.append(" }").append("\n");

        SVGjs.append(" function getBBoxAsRectElement(objNode) {").append("\n");
        SVGjs.append("  if(!objNode.getBBox) {").append("\n");
        SVGjs.append("    if(objNode.correspondingUseElement)").append("\n");
        SVGjs.append("      objNode = objNode.correspondingUseElement;").append("\n");
        SVGjs.append("  }").append("\n");
        SVGjs.append("  var bbox = objNode.getBBox();").append("\n");
        SVGjs.append("  var rect = document.createElementNS(SVG_, 'rect');").append("\n");
        SVGjs.append("  rect.x.baseVal.value = bbox.x;").append("\n");
        SVGjs.append("  rect.y.baseVal.value = bbox.y;").append("\n");
        SVGjs.append("  rect.width.baseVal.value = bbox.width;").append("\n");
        SVGjs.append("  rect.height.baseVal.value = bbox.height;").append("\n");
        SVGjs.append("  return rect;").append("\n");
        SVGjs.append(" }").append("\n");
        
        SVGjs.append("function createParagraph(text_element, width, height, x, y) {").append("\n");
        SVGjs.append("    fixParagraphToWidth(text_element, width, x);").append("\n");
        SVGjs.append("    fixParagraphToHeight(text_element, height);").append("\n");
        SVGjs.append("}").append("\n");

        SVGjs.append("function fixParagraphToWidth(text_element, width, x) {").append("\n");
        SVGjs.append("    var dy = getFontSize(text_element);").append("\n");
        SVGjs.append("    var text = text_element.textContent;").append("\n");
        SVGjs.append("    var words = text.split(' ');").append("\n");
        SVGjs.append("    var tspan_element = document.createElementNS(SVG_, 'tspan');").append("\n");
        SVGjs.append("    var text_node = document.createTextNode(words[0]);").append("\n");

        SVGjs.append("    text_element.textContent='';").append("\n");
        SVGjs.append("    tspan_element.appendChild(text_node);").append("\n");
        SVGjs.append("    text_element.appendChild(tspan_element);").append("\n");

        SVGjs.append("    for(var i=1; i<words.length; i++)").append("\n");
        SVGjs.append("    {").append("\n");
        SVGjs.append("        var len = tspan_element.firstChild.data.length;").append("\n");
        SVGjs.append("        tspan_element.firstChild.data += ' ' + words[i];").append("\n");

        SVGjs.append("        if (tspan_element.getComputedTextLength() > width)").append("\n");
        SVGjs.append("        {").append("\n");
        SVGjs.append("            tspan_element.firstChild.data = tspan_element.firstChild.data.slice(0, len);  // Remove added word").append("\n");

        SVGjs.append("            var tspan_element = document.createElementNS(SVG_, 'tspan');").append("\n");
        SVGjs.append("            tspan_element.setAttributeNS(null, 'x', x);").append("\n");
        SVGjs.append("            tspan_element.setAttributeNS(null, 'dy', dy);").append("\n");
        SVGjs.append("            text_node = document.createTextNode(words[i]);").append("\n");
        SVGjs.append("            tspan_element.appendChild(text_node);").append("\n");
        SVGjs.append("            text_element.appendChild(tspan_element);").append("\n");
        SVGjs.append("        }").append("\n");
        SVGjs.append("    }").append("\n");
        SVGjs.append("}").append("\n");

        SVGjs.append("function fixParagraphToHeight(text_element, height) {").append("\n");
        SVGjs.append("    var fs = getFontSize(text_element);").append("\n");
        SVGjs.append("    var h = getBoundingHeight(text_element);").append("\n");
        SVGjs.append("    while(h>height) {").append("\n");
        SVGjs.append("        fs--;").append("\n");
        SVGjs.append("        text_element.setAttributeNS(null, 'font-size', fs);").append("\n");
        SVGjs.append("        var childElements = text_element.getElementsByTagName('tspan');").append("\n");
        SVGjs.append("        for (var i=0; i < childElements.length; i++) {").append("\n");
        SVGjs.append("            if(childElements[i].getAttribute('dy')) {").append("\n");
        SVGjs.append("                childElements[i].setAttributeNS(null,'dy',fs-0.5);").append("\n");
        SVGjs.append("            }").append("\n");
        SVGjs.append("        }").append("\n");
        SVGjs.append("        h = getBoundingHeight(text_element);").append("\n");
        SVGjs.append("    }").append("\n");
        SVGjs.append("}").append("\n");

        SVGjs.append("</script>").append("\n");
        return SVGjs.toString();
    }
    
    private int assertValue(final String textVal)
    {
        int val;
        try {
            val = Integer.parseInt(textVal);
        }catch(NumberFormatException nfe) {
            val = 0;
        }catch(NullPointerException nulle) {
            val = 0;
        }
        return val;
    }
    
    private int assertValue(final String textVal, int defvalue)
    {
        int val;
        try {
            val = Integer.parseInt(textVal);
        }catch(NumberFormatException nfe) {
            val = defvalue;
        }catch(NullPointerException nulle) {
            val = defvalue;
        }
        return val;
    }
      
}
