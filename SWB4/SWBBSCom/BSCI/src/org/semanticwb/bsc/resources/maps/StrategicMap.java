package org.semanticwb.bsc.resources.maps;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
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
 * Recurso Mapa Estrat&eacute;gico. Permite generar un recurso para SWB que se
 * encarga de visualizar Perspectivas, Temas, Grupo de diferenciadores,
 * Objectivos y Diferenciadores.
 *
 * @author Carlos Ramos
 * @version %I%, %G%
 * @since 1.0
 */
public class StrategicMap extends GenericResource
{
    private static final Logger log = SWBUtils.getLogger(StrategicMap.class);

    public static final String Action_UPDATE = "update";
    
    public static final String HEADER_PREFIX = "head_";
    public static final int MARGEN_LEFT = 18; // Especifica el margen izquierdo del rectángulo de una perspectiva
    public static final int MARGEN_RIGHT = 100; // Especifica el margen derecho del rectángulo de una perspectiva
    public static final int MARGEN_TOP = 20; // Especifica el margen superior del rectángulo de una perspectiva
    public static final int MARGEN_BOTTOM = 30; // Especifica el margen inferior del rectángulo de una perspectiva
    public static final int HEADER_HEIGHT = 150; // altura del encabezado
    public static final int HEADER_1 = 24;              // tamaño de fuente para título del mapa
    public static final int HEADER_2 = (int)(HEADER_1*.90);    // tamaño de fuente para misión, visión
    public static final int HEADER_3 = (int)(HEADER_1*.80);    // tamaño de fuente para temas
    public static final int HEADER_4 = (int)(HEADER_1*.70);    // tamaño de fuente para diferenciadores
    public static final int HEADER_5 = (int)(HEADER_1*.60);    // tamaño de fuente para objetivos
    public static final int BOX_SPACING = 16; // Especifica el espacio entre rectángulos internos de una perspectiva
    public static final int BOX_SPACING_LEFT = 15; // Especifica el espacio entre rectángulos internos de una perspectiva
    public static final int BOX_SPACING_RIGHT = 8; // Especifica el espacio entre rectángulos internos de una perspectiva
    public static final int BOX_SPACING_TOP = 8; // Especifica el espacio entre rectángulos internos de una perspectiva
    public static final int BOX_SPACING_BOTTOM = 8; // Especifica el espacio entre rectángulos internos de una perspectiva
    public static final int PADDING = 4; // Especifica el espacio libre
    public static final int PADDING_TOP = 4; // Especifica el espacio libre arriba entre rectángulos para pintar las ligas
    public static final int PADDING_LEFT = 2; // Especifica el espacio libre a la izquieerda entre rectángulos para pintar las ligas
    public static final int PADDING_RIGHT = 2; // Especifica el espacio libre a la derecha entre rectángulos para pintar las ligas
    public static final int PADDING_DOWN = 4; // Especifica el espacio libre a la derecha entre rectángulos para pintar las ligas
    public static final String SVG_NS_URI = "http://www.w3.org/2000/svg";
    public static final String XLNK_NS_URI = "http://www.w3.org/1999/xlink";

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final String mode = paramRequest.getMode();
        if(Mode_StreamPNG.equalsIgnoreCase(mode)) {
            doGetPNGImage(request, response, paramRequest);
        }else if(Mode_StreamPDF.equalsIgnoreCase(mode)) {
            doGetPDFDocument(request, response, paramRequest);
        }else {
            super.processRequest(request, response, paramRequest);
        }
        /*switch (mode) {
            case Mode_StreamPNG:
                doGetPNGImage(request, response, paramRequest);
                break;
            case Mode_StreamPDF:
                doGetPDFDocument(request, response, paramRequest);
                break;
            default:
                super.processRequest(request, response, paramRequest);
                break;
        }*/
    }

    /**
     * Genera la vista del mapa Estrat&eacute;gico.
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
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        if(paramRequest.getCallMethod()==SWBParamRequest.Call_STRATEGY)
        {
            final String suri = request.getParameter("suri");
            PrintWriter out = response.getWriter();
            SWBResourceURL url = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT);
            
            
            out.println(" <form id=\"exportsmfrm\" style=\"display:none;\" accept-charset=\"utf-8\" method=\"post\" action=\"#\">");
            out.println("  <input type=\"hidden\" name=\"suri\" value=\"" + suri + "\" />");
            out.println("  <input type=\"hidden\" id=\"data\" name=\"data\" value=\"\" />");
            out.println(" <script type=\"text/javascript\">");
            out.println("  function getFile(url) {");
            out.println("   var form = document.getElementById('exportsmfrm');");
            out.println("   var svg = document.getElementsByTagName('svg')[0];");
            out.println("   var svg_xml = (new XMLSerializer).serializeToString(svg);");
            out.println("   form.action = url;");
            out.println("   form['data'].value = svg_xml;");
            out.println("   form.submit();");
            out.println("  };");
            out.println(" </script>");
            out.println(" </form>");
            // impresión PDF
            out.println("<button type=\"button\" class=\"btn btn-default\" onclick=\"getFile('"+url.setMode(Mode_StreamPDF)+"')\"><span class=\"glyphicon glyphicon-export\"></span></button>");            
            // impresión PNG
            out.println("<button type=\"button\" class=\"btn btn-default\" onclick=\"getFile('"+url.setMode(Mode_StreamPNG)+"')\"><span class=\"glyphicon glyphicon-picture\"></span></button>");
        }
        else
        {
            PrintWriter out = response.getWriter();
            String SVGjs = null;
            try {
                SVGjs = getSvg(request);
            }catch (XPathExpressionException xpe) {
                System.out.println(xpe.toString());
            }
            out.println(SVGjs);
        }
    }

    public void doGetPNGImage(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        WebSite webSite = getResourceBase().getWebSite();
        response.setContentType("image/png; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + webSite.getTitle() + ".png\"");
//        if (webSite instanceof BSC) {
//            Period period = getPeriod(request);
//            if(period != null) {
//
//            } else {
//                out.println("<p>" + paramRequest.getLocaleString("errorPeriod") + "</p>");
//            }
            final String data = request.getParameter("data");
            Document svg = SWBUtils.XML.xmlToDom(data);
            PNGTranscoder t = new PNGTranscoder();
            //t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.8));
            TranscoderInput input = new TranscoderInput(svg);
            TranscoderOutput output = new TranscoderOutput(response.getOutputStream());
            try {
                t.transcode(input, output);
                response.getOutputStream().flush();
                response.getOutputStream().close();
            } catch (TranscoderException tcdre) {
            }
//        }
    }

    public void doGetPDFDocument(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        WebSite webSite = getResourceBase().getWebSite();
        response.setContentType("application/pdf; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + webSite.getTitle() + ".pdf\"");

//        if (webSite instanceof BSC) {
//            Period period = getPeriod(request);
//            if(period != null) {
//
//            } else {
//                out.println("<p>" + paramRequest.getLocaleString("errorPeriod") + "</p>");
//            }
            final String data = request.getParameter("data");
            Document svg = SWBUtils.XML.xmlToDom(data);
            PDFTranscoder t = new PDFTranscoder();
            //t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.8));
            TranscoderInput input = new TranscoderInput(svg);
            TranscoderOutput output = new TranscoderOutput(response.getOutputStream());
            try {
                t.transcode(input, output);
                response.getOutputStream().flush();
                response.getOutputStream().close();
            } catch (TranscoderException tcdre) {
            }
//        }
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
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
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
            htm.append("  dojo.require('dijit.form.Button');\n");
            htm.append("</script>\n");

            htm.append("<div class=\"swbform\">\n");
            htm.append("<form id=\"frmPromo\"  method=\"post\" action=\""+url+"\">\n");
            htm.append("<div title=\"Configuración del estilo\" open=\"true\" dojoType=\"dijit.TitlePane\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">\n");
            htm.append("<fieldset>\n");
            htm.append("    <legend>Estilo</legend>\n");
            htm.append("    <ul class=\"swbform-ul\">\n");

            htm.append("        <li class=\"swbform-li\">\n");
            htm.append("          <label for=\"imgWidth\" class=\"swbform-label\">Anchura de la imagen <i>(pixeles)</i></label>\n");
            htm.append("          <input type=\"text\" id=\"width\" name=\"width\" regExp=\"\\d{2,4}\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("width","1024")+"\" maxlength=\"4\" />\n");
            htm.append("        </li>\n");
            htm.append("        <li class=\"swbform-li\">\n");
            htm.append("          <label for=\"imgHeight\" class=\"swbform-label\">Altura de la imagen <i>(pixeles)</i></label>\n");
            htm.append("          <input type=\"text\" id=\"height\" name=\"height\" regExp=\"\\d{2,4}\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("height","1400")+"\" maxlength=\"4\" />\n");
            htm.append("        </li>\n");
            
            htm.append("        <li class=\"swbform-li\">\n");
            htm.append("          <label for=\"viewBox\" class=\"swbform-label\">Atributo viewBox </label>\n");
            htm.append("          <input type=\"text\" id=\"viewBox\" name=\"viewBox\" regExp=\"\\d{1,4}(\\s|,)\\d{1,4}(\\s|,)\\d{1,4}(\\s|,)\\d{1,4}\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("viewBox","0,0,1024,1400")+"\" />\n");
            htm.append("        </li>\n");
            
            htm.append("        <li class=\"swbform-li\">\n");
            htm.append("          <label for=\"parentId\" class=\"swbform-label\">Identificador de la etiqueta padre </label>\n");
            htm.append("          <input type=\"text\" id=\"parentId\" name=\"parentId\" dojoType=\"dijit.form.TextBox\" value=\""+base.getAttribute("parentId","")+"\" />\n");
            htm.append("        </li>\n");
            
            htm.append("</ul>\n");
            htm.append("</fieldset>\n");
            htm.append("</div>\n");

            htm.append("<fieldset>\n");
            htm.append("    <button type=\"submit\" dojoType=\"dijit.form.Button\">Guardar</button>\n");
            htm.append("    <button type=\"reset\" dojoType=\"dijit.form.Button\">Reestablecer</button>\n");
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
    public void doXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/xml:; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Content-Disposition", "attachment; filename=\"abc.pdf\"");

        WebSite webSite = getResourceBase().getWebSite();
        if (webSite instanceof BSC) {
            BSC model = (BSC) webSite;
            PrintWriter out = response.getWriter();
            Document dom = model.getDom();
            out.println(SWBUtils.XML.domToXml(dom));
            out.flush();
            out.close();
        }
    }

    /**
     * Realiza las operaciones de almacenamiento de la configuraci&oacute;n para
     * la visualizaci&oacute;n del mapa estrat&eacute;gico.
     *
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta
     * con los recursos necesarios para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n
     * problema con la generaci&oacute;n o escritura de la respuesta
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        if(Action_UPDATE.equals(action)) {
            Resource base = response.getResourceBase();
            base.setAttribute("width", request.getParameter("width").isEmpty()?null:request.getParameter("width"));
            base.setAttribute("height", request.getParameter("height").isEmpty()?null:request.getParameter("height"));
            base.setAttribute("viewBox", request.getParameter("viewBox").isEmpty()?null:request.getParameter("viewBox"));
            base.setAttribute("parentId", request.getParameter("parentId").isEmpty()?null:request.getParameter("parentId"));
            try {
                base.updateAttributesToDB();
                response.setAction(Action_UPDATE);
            }catch(SWBException e) {
                log.error(e);
                response.setAction(SWBActionResponse.Action_EDIT);
            }
        }
    }

    /**
     * Obtiene el periodo seleccionado actual
     *
     * @param request la petici&oacute;n enviada por el cliente
     * @param ws sitio web
     * @return un objeto {@code Periodo} que representa el Periodo actual
     * seleccionado
     */
    private Period getPeriod(HttpServletRequest request) {
        WebSite model = getResourceBase().getWebSite();
        Period period = null;
System.out.println("model="+model+", id="+model.getId());
        HttpSession session = request.getSession(true);
        final String pid = (String) session.getAttribute(model.getId());
System.out.println("pid="+pid);
        if (Period.ClassMgr.hasPeriod(pid, model)) {
            period = Period.ClassMgr.getPeriod(pid, model);
        }
System.out.println("1. period="+period);
        if(period == null) {
            BSC scorecard = (BSC)model;
            try {
                period = scorecard.listPeriods().next();
            }catch(NoSuchElementException nsee) {
            }
        }
System.out.println("2. period="+period.getTitle());
        return period;
    }
    private String urlBase = null;

    @Override
    public void setResourceBase(Resource base) throws SWBResourceException {
        super.setResourceBase(base);
        WebPage wp = base.getWebSite().getWebPage(Objective.class.getSimpleName());
        urlBase = wp.getUrl();
    }
    
    public Document getDom(final Period period) throws XPathExpressionException, ClassCastException, NumberFormatException
    {
        Resource base = getResourceBase();
        final BSC scorecard = (BSC)base.getWebSite();
        int width = assertValue(base.getAttribute("width", "1024"));
        int height = assertValue(base.getAttribute("height", "1400"));
        
        Document documentBSC;
        if(period==null) {
            documentBSC = scorecard.getDom();
        }else {
            documentBSC = scorecard.getDom(period);
        }
        
        Element root = documentBSC.getDocumentElement();
        root.setAttribute("width", Integer.toString(width));
        root.setAttribute("height", Integer.toString(height));

        XPath xPath = XPathFactory.newInstance().newXPath();

        //header
        Element header = documentBSC.createElement("header");
        header.setAttribute("id", HEADER_PREFIX + "DADT");
        header.setAttribute("width", Integer.toString(width));
        header.setAttribute("height", Integer.toString(HEADER_HEIGHT));
        header.setAttribute("x", Integer.toString(PADDING_LEFT));
        header.setAttribute("y", "1");
        String expression = "/bsc/title";
        Node node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        header.appendChild(node);
        expression = "/bsc/mission";
        node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        header.appendChild(node);
        expression = "/bsc/vision";
        node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        header.appendChild(node);
        expression = "/bsc/logo";
        node = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        header.appendChild(node);

        expression = "/bsc/perspective[1]";
        Node p1 = (Node) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODE);
        root.insertBefore(header, p1);

        final int px;
        final int pw;
        final int perspCount;
        String uri;
        String href;

        //para cada perspectiva: width, height, x, y
        expression = "/bsc/perspective";
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
        perspCount = nodeList.getLength();
        pw = width - MARGEN_LEFT - MARGEN_RIGHT;
        px = MARGEN_LEFT;

        //lista de perspectivas
        for (int j = 0; j < perspCount; j++) {
            Node nodep = nodeList.item(j);
            if (nodep.getNodeType() == Node.ELEMENT_NODE) {
                //perspectiva
                Element p = (Element) nodep;
                uri = p.getAttribute("id");
                p.setAttribute("width", Integer.toString(pw));
                p.setAttribute("x", Integer.toString(px));

                //diferenciadores de la perspectiva
                expression = "/bsc/perspective[@id='" + uri + "']/diffgroup[1]/diff";
                NodeList nlDiffs = (NodeList) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                final int nlDiffsCount = nlDiffs.getLength();
                final boolean hasDifferentiators = nlDiffsCount > 0;
                if (hasDifferentiators) {
                    final int dw = pw / nlDiffsCount;
                    for (int k = 0; k < nlDiffsCount; k++) {
                        Node noded = nlDiffs.item(k);
                        Element d = (Element) noded;
                        d.setAttribute("width", Integer.toString(dw - BOX_SPACING_RIGHT));
                        d.setAttribute("x", Integer.toString(px + k * dw + PADDING_RIGHT));
                    }
                }

                //lista de temas por perspectiva                
                expression = "/bsc/perspective[@id='" + uri + "']/themes/theme";
                NodeList nlThms = (NodeList) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                final int nlThmsCount = nlThms.getLength();
                final boolean hasThemes = nlThmsCount > 0;
                if (hasThemes) {
                    final int tw = pw / nlThmsCount;
                    int tx;
                    for (int k = 0; k < nlThmsCount; k++) {
                        Node nodet = nlThms.item(k);
                        if (nodet.getNodeType() == Node.ELEMENT_NODE) {
                            Element t = (Element) nodet;
                            uri = t.getAttribute("id");
                            t.setAttribute("width", Integer.toString(tw - BOX_SPACING_RIGHT));
                            tx = px + k * tw;
                            t.setAttribute("x", Integer.toString(tx));

                            //relaciones con este tema
                            expression = "//rel[@to='" + uri + "']";
                            NodeList nlRels = (NodeList) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                            for (int v = 0; v < nlRels.getLength(); v++) {
                                Node noder = nlRels.item(v);
                                if (noder != null && noder.getNodeType() == Node.ELEMENT_NODE) {
                                    Element rel = (Element) noder;
                                    rel.setAttribute("rx", Integer.toString(tx + tw / 2));
                                }
                            }

                            //lista de objetivos por tema
                            expression = "//theme[@id='" + uri + "']/obj";
                            NodeList nlObjs = (NodeList) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                            final int nlObjsCount = nlObjs.getLength();
                            if (nlObjsCount > 0) {
                                for (int l = 0; l < nlObjsCount; l++) {
                                    Node nodeo = nlObjs.item(l);
                                    int ox = tx;
                                    if (nodeo.getNodeType() == Node.ELEMENT_NODE) {
                                        Element o = (Element) nodeo;
                                        uri = o.getAttribute("id");
                                        href = o.getAttribute("href");
                                        o.setAttribute("width", Integer.toString(tw - BOX_SPACING_LEFT));
                                        o.setAttribute("x", Integer.toString(ox + BOX_SPACING_LEFT));
                                        o.setAttribute("href", urlBase+"?suri="+href);

                                        //relaciones con este objetivo
                                        expression = "//rel[@to='" + uri + "']";
                                        nlRels = (NodeList) xPath.compile(expression).evaluate(documentBSC, XPathConstants.NODESET);
                                        for (int m = 0; m < nlRels.getLength(); m++) {
                                            Node noder = nlRels.item(m);
                                            if (noder.getNodeType() == Node.ELEMENT_NODE) {
                                                Element rel = (Element) noder;
                                                rel.setAttribute("rx", Integer.toString(ox + tw / 2));
                                            }
                                        }
                                    }
                                } //lista de objetivos por tema
                            }
                        }
                    } //lista de temas
                }
            }
        } //lista de perspectivas
        return documentBSC;
    }
    
    public String getSvg(HttpServletRequest request) throws XPathExpressionException {
        Resource base = getResourceBase();
        String id, txt, expression;
        int w, h, w_, h_;
        int x, y = 0, x_, y_;
        StringBuilder SVGjs = new StringBuilder();

        final Period period = getPeriod(request);
        Document map = getDom(period);
        Element rootBSC = map.getDocumentElement();
        int width = assertValue(rootBSC.getAttribute("width"));
        int height = assertValue(rootBSC.getAttribute("height"));
        
        User user = SWBContext.getSessionUser(base.getWebSite().getUserRepository().getId());
        String lang = user.getLanguage();
        String bundle = getClass().getName();
        Locale locale = new Locale(lang);

        SVGjs.append("<script type=\"text/javascript\">").append("\n");
        SVGjs.append(" var width = " + width + ";").append("\n");
        SVGjs.append(" var height = " + height + ";").append("\n");
        SVGjs.append(" var SVG_ = '" + SVG_NS_URI + "';").append("\n");
        SVGjs.append(" var XLINK_ = '" + XLNK_NS_URI + "';").append("\n");
        SVGjs.append(" window.onload = function() {").append("\n");
        SVGjs.append(" var svg = document.createElementNS(SVG_,'svg'); ").append("\n");
        SVGjs.append(" svg.setAttributeNS(null,'id','" + base.getWebSiteId() + "');").append("\n");
        SVGjs.append(" svg.setAttributeNS(null,'width','" + width + "');").append("\n");
        SVGjs.append(" svg.setAttributeNS(null,'height','" + height + "');").append("\n");
        SVGjs.append(" svg.setAttributeNS(null,'viewBox','"+base.getAttribute("viewBox","0 0 1024 1400")+"');").append("\n");
        SVGjs.append(" svg.setAttributeNS(null,'version','1.1');").append("\n");
        
        String parentId = base.getAttribute("parentId");
        if(parentId==null) {
            SVGjs.append(" document.body.appendChild(svg);").append("\n");
        }else {
            SVGjs.append(" var parentId = dojo.byId('"+parentId+"');").append("\n");
            SVGjs.append(" if(parentId) {").append("\n");
            SVGjs.append("   parentId.appendChild(svg);").append("\n");
            SVGjs.append(" }else {").append("\n");
            SVGjs.append("   document.body.appendChild(svg);").append("\n");
            SVGjs.append(" }").append("\n");
        }
        
        SVGjs.append(" var defs = document.createElementNS('http://www.w3.org/2000/svg', 'defs');").append("\n");
        // Cabeza de flecha para las relaciones causa/efecto
        SVGjs.append(" var marker = document.createElementNS('http://www.w3.org/2000/svg', 'marker');").append("\n");
        SVGjs.append(" marker.setAttributeNS(null,'id', 'arrow_1');").append("\n");
        SVGjs.append(" marker.setAttributeNS(null,'viewBox', '0 0 10 10');").append("\n");
        SVGjs.append(" marker.setAttributeNS(null,'refX', '8');").append("\n");
        SVGjs.append(" marker.setAttributeNS(null,'refY', '5');").append("\n");
        SVGjs.append(" marker.setAttributeNS(null,'markerUnits', 'strokeWidth');").append("\n");
        SVGjs.append(" marker.setAttributeNS(null,'markerWidth', '4');").append("\n");
        SVGjs.append(" marker.setAttributeNS(null,'markerHeight', '4');").append("\n");
        SVGjs.append(" marker.setAttributeNS(null,'orient', 'auto');").append("\n");
        SVGjs.append(" var arrow = document.createElementNS('http://www.w3.org/2000/svg', 'path');").append("\n");
        SVGjs.append(" arrow.setAttributeNS(null,'d', 'M0 0 L10 5 L0 10 z');").append("\n");
        SVGjs.append(" arrow.setAttributeNS(null,'fill', '#FF0099');").append("\n");
        SVGjs.append(" arrow.setAttributeNS(null,'stroke', '#FF0099');").append("\n");
        SVGjs.append(" arrow.setAttributeNS(null,'stroke-width', '0');").append("\n");
        SVGjs.append(" marker.appendChild(arrow);").append("\n");
        SVGjs.append(" defs.appendChild(marker);").append("\n");
        SVGjs.append(" svg.appendChild(defs);").append("\n");
        // Cabeza de flecha. Fin

        SVGjs.append(" var stat;").append("\n");    // figura para representar el estatus de objetivo
        SVGjs.append(" var r;").append("\n");       // relación causa/efecto
        SVGjs.append(" var w;").append("\n");       // width
        SVGjs.append(" var g;").append("\n");       // perspectiva
        SVGjs.append(" var txt;").append("\n");     // texto
        SVGjs.append(" var rect;").append("\n");    // elemento
        SVGjs.append(" var path;").append("\n");    // flecha de la relación causa/efecto
        SVGjs.append(" var lnk;").append("\n");     // liga
        SVGjs.append(" var to;").append("\n");      // objetivo destino(target) de la relación
        SVGjs.append(" var parent;").append("\n");  // perspectiva del objetivo destino de la relación
        SVGjs.append(" var matxTo;").append("\n");  // matriz del objetivo destino de la relación
        SVGjs.append(" var matxFrm;").append("\n"); // matriz del objetivo fuente(source) de la relación
        SVGjs.append(" var posTo;").append("\n");   // posición del objetivo destino(target) de la relación
        SVGjs.append(" var posFrm;").append("\n");  // posición del objetivo fuente(source) de la relación
        SVGjs.append(" var tspan;").append("\n");   // objeto tipo tspan
        
        // Encabezado
        XPath xPath = XPathFactory.newInstance().newXPath();
        expression = "/bsc/header";
        Node node = (Node) xPath.compile(expression).evaluate(map, XPathConstants.NODE);
        if (node != null && node instanceof Element)
        {
            NamedNodeMap attrs = node.getAttributes();
            id = attrs.getNamedItem("id").getNodeValue();
            w = assertValue(attrs.getNamedItem("width").getNodeValue());
            h = assertValue(attrs.getNamedItem("height").getNodeValue());
            x = assertValue(attrs.getNamedItem("x").getNodeValue());
            y = assertValue(attrs.getNamedItem("y").getNodeValue());

            SVGjs.append(" g = document.createElementNS(SVG_,'g');").append("\n");
            SVGjs.append(" g.setAttributeNS(null,'id','" + id + "');").append("\n");
            SVGjs.append(" svg.appendChild(g);").append("\n");

            x_ = x;
            y_ = y + topPadding(HEADER_1);
            w_ = w;

            // Título mapa
            expression = "/bsc/header/title";
            txt = (String) xPath.compile(expression).evaluate(map, XPathConstants.STRING);
            SVGjs.append(" txt = document.createElementNS(SVG_,'text');").append("\n");
            SVGjs.append(" txt.setAttributeNS(null,'x',"+(x_ + w_ / 2)+");").append("\n");
            SVGjs.append(" txt.setAttributeNS(null,'y',"+y_+");").append("\n");
            SVGjs.append(" txt.setAttributeNS(null,'font-family','Verdana');").append("\n");
            SVGjs.append(" txt.setAttributeNS(null,'text-anchor','middle');").append("\n");
            // Nombre del scorecard
//            SVGjs.append(" txt.setAttributeNS(null,'class','title');").append("\n");
            SVGjs.append(" tspan = document.createElementNS(SVG_, 'tspan');").append("\n");
            SVGjs.append(" tspan.setAttributeNS(null,'style','fill:#373737;font-weight:bold;font-size:"+HEADER_1+"px;');").append("\n");
            SVGjs.append(" var text_node = document.createTextNode('"+txt+"');").append("\n");
            SVGjs.append(" tspan.appendChild(text_node);").append("\n");
            SVGjs.append(" txt.appendChild(tspan);").append("\n");
            // periodo de consulta
            SVGjs.append(" tspan = document.createElementNS(SVG_, 'tspan');").append("\n");
            SVGjs.append(" tspan.setAttributeNS(null,'dx','10');");
            SVGjs.append(" tspan.setAttributeNS(null,'style','fill:#5d5d5d;font-size:"+HEADER_3+"px;');").append("\n");
            SVGjs.append(" text_node = document.createTextNode('"+(period.getTitle(lang)==null?period.getTitle():period.getTitle(lang))+"');").append("\n");
            SVGjs.append(" tspan.appendChild(text_node);").append("\n");
            SVGjs.append(" txt.appendChild(tspan);").append("\n");
            
            SVGjs.append(" g.appendChild(txt);").append("\n");
//            SVGjs.append(" fixParagraphAtBounding(txt,"+w_+","+HEADER_1+","+x_+","+y_+");").append("\n");
//            SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
//            SVGjs.append(" framingRect(rect,'"+id+"_ptitle',"+w_+","+vPadding(HEADER_1)+","+x_+","+y_+");").append("\n");
//            SVGjs.append(" g.insertBefore(rect,txt);").append("\n");

            // pleca Mision
            y_ = y + vPadding(HEADER_1) + topPadding(HEADER_2) + PADDING_DOWN;
            w_ = w / 3;
            h_ = h - vPadding(HEADER_1) - PADDING_DOWN;

            SVGjs.append(" txt = createText('"+SWBUtils.TEXT.getLocaleString(bundle, "lblMission", locale)+"'," + (x_ + w_ / 2) + "," + y_ + "," + HEADER_3 + ",'Verdana');").append("\n");
            SVGjs.append(" txt.setAttributeNS(null,'text-anchor','middle');").append("\n");
            SVGjs.append(" txt.setAttributeNS(null,'style','fill:#265f92;font-weight:bold;font-size:"+HEADER_4+"px;');").append("\n");
            SVGjs.append(" g.appendChild(txt);").append("\n");
//            SVGjs.append(" fixParagraphAtBounding(txt,"+w_+","+HEADER_2+","+x_+","+y_+");").append("\n");
//            SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
//            SVGjs.append(" framingRect(rect,'"+id+"_pmission',"+w_+","+vPadding(HEADER_2)+","+x_+","+y_+");").append("\n");
//            SVGjs.append(" g.insertBefore(rect,txt);").append("\n");

            // Pleca Vision
            SVGjs.append(" txt = createText('"+SWBUtils.TEXT.getLocaleString(bundle, "lblVision", locale)+"'," + (x_ + 5 * w_ / 2) + "," + y_ + "," + HEADER_3 + ",'Verdana');").append("\n");
            SVGjs.append(" txt.setAttributeNS(null,'text-anchor','middle');").append("\n");
            SVGjs.append(" txt.setAttributeNS(null,'style','fill:#265f92;font-weight:bold;font-size:"+HEADER_4+"px;');").append("\n");
            SVGjs.append(" g.appendChild(txt);").append("\n");
//            SVGjs.append(" fixParagraphAtBounding(txt,"+w_+","+HEADER_2+","+(x_+2*w_)+","+y_+");").append("\n");
//            SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
//            SVGjs.append(" framingRect(rect,'"+id+"_pvision',"+w_+","+vPadding(HEADER_2)+","+(x_+2*w_)+","+y_+");").append("\n");
//            SVGjs.append(" g.insertBefore(rect,txt);").append("\n");

            // Imagen de logo
            expression = "/bsc/header/logo";
            node = (Node) xPath.compile(expression).evaluate(map, XPathConstants.NODE);
            if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
                attrs = node.getAttributes();
                if (attrs.getNamedItem("src").getNodeValue().isEmpty()) {
                    SVGjs.append(" rect = createRect('" + id + "_lg" + "'," + (w_ - BOX_SPACING) + "," + (h_ - BOX_SPACING) + "," + (x_ + w_ + PADDING_LEFT) + "," + (y_ - HEADER_2 + PADDING_TOP) + ",0,0,'none',1,'red',1,1);").append("\n");
                    SVGjs.append(" rect.setAttributeNS(null,'rx',3);").append("\n");
                    SVGjs.append(" rect.setAttributeNS(null,'ry',3);").append("\n");
                    SVGjs.append(" g.appendChild(rect);").append("\n");
                } else {
                    SVGjs.append(" var img = document.createElementNS(SVG_,'image');").append("\n");
                    SVGjs.append(" img.setAttributeNS(null,'width'," + (w_ - BOX_SPACING) + ");").append("\n");
                    SVGjs.append(" img.setAttributeNS(null,'height'," + (h_ - BOX_SPACING) + ");").append("\n");
                    SVGjs.append(" img.setAttributeNS(null,'x'," + (x_ + w_ + PADDING_LEFT) + ");").append("\n");
                    SVGjs.append(" img.setAttributeNS(null,'y'," + (y_ - HEADER_2 + PADDING_TOP) + ");").append("\n");
                    SVGjs.append(" img.setAttributeNS(XLINK_,'href', '" + request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + attrs.getNamedItem("src").getNodeValue() + "');").append("\n");
                    SVGjs.append(" img.setAttributeNS(null, 'visibility', 'visible');").append("\n");
                    SVGjs.append(" g.appendChild(img);").append("\n");
                }
            }

            // contenido Mision
            y_ = y_ + vPadding(HEADER_2);
            h_ = h_ - vPadding(HEADER_2);
            expression = "/bsc/header/mission";
            txt = (String) xPath.compile(expression).evaluate(map, XPathConstants.STRING);
            SVGjs.append(" txt = createText('" + txt + "'," + x_ + "," + y_ + ",14,'Verdana');").append("\n");
            SVGjs.append(" g.appendChild(txt);").append("\n");
            SVGjs.append(" fixParagraphAtBounding(txt," + w_ + "," + h_ + "," + x_ + "," + y_ + ");").append("\n");
            SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
            SVGjs.append(" framingRect(rect,'" + id + "_cmission'," + w_ + "," + h_ + "," + x_ + "," + y_ + ");").append("\n");
            SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
            // contenido Vision
            expression = "/bsc/header/vision";
            txt = (String) xPath.compile(expression).evaluate(map, XPathConstants.STRING);
            SVGjs.append(" txt = createText('" + txt + "'," + (x_ + 2 * w_) + "," + y_ + ",14,'Verdana');").append("\n");
            SVGjs.append(" g.appendChild(txt);").append("\n");
            SVGjs.append(" fixParagraphAtBounding(txt," + w_ + "," + h_ + "," + (x_ + 2 * w_) + "," + y_ + ");").append("\n");
            SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
            SVGjs.append(" framingRect(rect,'" + id + "_cvision'," + w_ + "," + h_ + "," + (x_ + 2 * w_) + "," + y_ + ");").append("\n");
            SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
        }



        String title, perspectiveName;
        y = y + HEADER_HEIGHT + MARGEN_TOP;
        SVGjs.append(" var y = " + y + ";").append("\n");
        StringBuilder info;
        
        
        // lista de perspectivas
        expression = "/bsc/perspective";
        NodeList nlPersp = (NodeList) xPath.compile(expression).evaluate(map, XPathConstants.NODESET);
        for (int j = 0; j < nlPersp.getLength(); j++) {
            node = nlPersp.item(j);
            if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
                NamedNodeMap attrs = node.getAttributes();
                String pid = attrs.getNamedItem("id").getNodeValue();
                int pw = assertValue(attrs.getNamedItem("width").getNodeValue());
                int px = assertValue(attrs.getNamedItem("x").getNodeValue());
                
                // título de la perspectiva
                expression = "/bsc/perspective[@id='" + pid + "']/title";
                perspectiveName = (String) xPath.compile(expression).evaluate(map, XPathConstants.STRING);
                SVGjs.append(" g = document.createElementNS(SVG_,'g');").append("\n");
                SVGjs.append(" g.setAttributeNS(null,'id','" + pid + "');").append("\n");
                SVGjs.append(" svg.appendChild(g);").append("\n");
                SVGjs.append(" g.setAttributeNS(null,'transform','translate(" + px + ",'+y+')');").append("\n");
                SVGjs.append(" var y_ = " + PADDING_TOP + ";").append("\n");

                // diferenciadores de la perspectiva
                expression = "/bsc/perspective[@id='" + pid + "']/diffgroup[1]/diff";
                SVGjs.append(" var x;").append("\n");
                NodeList nlDiffs = (NodeList) xPath.compile(expression).evaluate(map, XPathConstants.NODESET);
                boolean hasDifferentiators = nlDiffs.getLength() > 0;
                if (hasDifferentiators) {
                    SVGjs.append(" x = "+BOX_SPACING+";").append("\n");
                    SVGjs.append(" y_ += " + BOX_SPACING + ";").append("\n");
                    for (int k = 0; k < nlDiffs.getLength(); k++) {
                        Node nodeD = nlDiffs.item(k);
                        if (nodeD != null && nodeD.getNodeType() == Node.ELEMENT_NODE) {
                            attrs = nodeD.getAttributes();
                            String did = attrs.getNamedItem("id").getNodeValue();
                            
attrs = nodeD.getAttributes();
//String tid = attrs.getNamedItem("id").getNodeValue();
w_ = assertValue(attrs.getNamedItem("width").getNodeValue());
x_ = assertValue(attrs.getNamedItem("x").getNodeValue());                            
                            
                            
                            
SVGjs.append(" txt = createText('" + nodeD.getFirstChild().getNodeValue() + "',"+x_+",y_," + HEADER_4 + ",'Verdana');").append("\n");
                            SVGjs.append(" txt.setAttributeNS(null,'style','fill:#ffffff;font-weight:normal;font-size:"+HEADER_4+"px;');").append("\n");
                            SVGjs.append(" g.appendChild(txt);").append("\n");
                            SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
SVGjs.append(" framingRect(rect,'" + did + "',rect.width.baseVal.value,rect.height.baseVal.value,"+x_+",y_);").append("\n");
                            SVGjs.append(" rect.setAttributeNS(null,'style','stroke-width:0;fill:#662d91;');").append("\n");
//                            SVGjs.append(" rect.setAttributeNS(null,'rx',3);").append("\n");
//                            SVGjs.append(" rect.setAttributeNS(null,'ry',3);").append("\n");
                            SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
                            SVGjs.append(" x = x + rect.width.baseVal.value + "+BOX_SPACING_RIGHT+";").append("\n");
                        }
                    }
                }

                SVGjs.append(" var y__;").append("\n");

                // temas de la perspectiva
                if(hasDifferentiators) {
                    SVGjs.append(" y__ = y_ + rect.height.baseVal.value + " + BOX_SPACING + ";").append("\n");
                }else {
                    SVGjs.append(" y__ = y_ + " + BOX_SPACING + ";").append("\n");
                }
                
                //String tid = null; // identificador de tema
                expression = "/bsc/perspective[@id='" + pid + "']/themes/theme";
                NodeList nlThms = (NodeList) xPath.compile(expression).evaluate(map, XPathConstants.NODESET);
                for (int l = 0; l < nlThms.getLength(); l++) {
                    Node nodeT = nlThms.item(l);
                    if (nodeT != null && nodeT.getNodeType() == Node.ELEMENT_NODE) {
                        attrs = nodeT.getAttributes();
                        boolean isHidden = Boolean.parseBoolean(attrs.getNamedItem("hidden").getNodeValue());
                        String tid = attrs.getNamedItem("id").getNodeValue();
                        w_ = assertValue(attrs.getNamedItem("width").getNodeValue());
                        x_ = assertValue(attrs.getNamedItem("x").getNodeValue());
                        // r guarda algunas valores de la perspectiva actual para después recuperarlas por su identificador
                        // esto resulta muy conveniente para construir los paths de relaciones causa/efecto
                        SVGjs.append(" r = document.createElementNS(SVG_,'rect');").append("\n");
                        SVGjs.append(" r.setAttributeNS(null,'id','w_" + pid + "');").append("\n");
                        SVGjs.append(" r.setAttributeNS(null,'width'," + w_ + ");").append("\n");
                        SVGjs.append(" defs.appendChild(r);").append("\n");

                        // rectángulo tema
                        if(!isHidden) {
                            expression = "/bsc/perspective[@id='" + pid + "']/themes/theme[@id='" + tid + "']/title";
                            title = (String) xPath.compile(expression).evaluate(map, XPathConstants.STRING);
                            SVGjs.append(" txt = createText('" + title + "'," + x_ + ",y__," + HEADER_3 + ",'Verdana');").append("\n");
                            SVGjs.append(" txt.setAttributeNS(null,'style','fill:#ffffff;font-weight:normal;font-size:"+HEADER_3+"px;');").append("\n");
                            SVGjs.append(" g.appendChild(txt);").append("\n");
                            SVGjs.append(" fixParagraphToWidth(txt," + w_ + "," + x_ + ");").append("\n");
                            SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
                            SVGjs.append(" framingRect(rect,'" + tid + "'," + w_ + ",rect.height.baseVal.value," + x_ + ",y__);").append("\n");
                            SVGjs.append(" rect.setAttributeNS(null,'style','stroke-width:0;fill:#30b8ae;');").append("\n");
                            SVGjs.append(" rect.setAttributeNS(null,'rx',3);").append("\n");
                            SVGjs.append(" rect.setAttributeNS(null,'ry',3);").append("\n");
                            SVGjs.append(" g.insertBefore(rect,txt);").append("\n");
                        }
                            
                        if(!isHidden) {
                            // relaciones causa-efecto con este tema
                            expression = "//theme[@id='" + tid + "']/rel";
                            NodeList nlRels = (NodeList) xPath.compile(expression).evaluate(map, XPathConstants.NODESET);
                            for (int n = 0; n < nlRels.getLength(); n++)
                            {
                                Node nodeR = nlRels.item(n);
                                if (nodeR != null && nodeR.getNodeType() == Node.ELEMENT_NODE)
                                {
                                    attrs = nodeR.getAttributes();
                                    String to = attrs.getNamedItem("to").getNodeValue();
                                    String parent = attrs.getNamedItem("parent").getNodeValue();
                                    SVGjs.append(" to = document.getElementById('" + to + "');").append("\n");
                                    SVGjs.append(" parent = document.getElementById('" + parent + "');").append("\n");

                                    SVGjs.append(" r = document.getElementById('w_" + parent + "');").append("\n");
                                    SVGjs.append(" if(r) {").append("\n");
                                    SVGjs.append("     w = r.width.baseVal.value;").append("\n");
                                    SVGjs.append("     w = w/2;").append("\n");
                                    SVGjs.append(" }else {").append("\n");
                                    SVGjs.append("     w = 0;").append("\n");
                                    SVGjs.append(" }").append("\n");

                                    SVGjs.append(" if(to && parent) {").append("\n");
                                    SVGjs.append("   matxTo = parent.getCTM();").append("\n");
                                    SVGjs.append("   posTo = svg.createSVGPoint();").append("\n");
                                    SVGjs.append("   posTo.x = to.x.baseVal.value + w;").append("\n");
                                    SVGjs.append("   posTo.y = to.y.baseVal.value;").append("\n");
                                    SVGjs.append("   posTo = posTo.matrixTransform(matxTo);").append("\n");
                                    SVGjs.append("   matxFrm = g.getCTM();").append("\n");
                                    SVGjs.append("   posFrm = svg.createSVGPoint();").append("\n");
                                    SVGjs.append("   posFrm.x = rect.x.baseVal.value;").append("\n");
                                    SVGjs.append("   posFrm.y = rect.y.baseVal.value;").append("\n");
                                    SVGjs.append("   posFrm = posFrm.matrixTransform(matxFrm);").append("\n");
                                    SVGjs.append("   path = createArrow(posFrm.x+'_'+posFrm.y+'_'+posTo.x+'_'+posTo.y,posFrm.x+" + (w_ / 2) + ",posFrm.y,posTo.x,posTo.y);").append("\n");
                                    SVGjs.append("   svg.appendChild(path);").append("\n");
                                    SVGjs.append(" }").append("\n");
                                }
                            }
                        }

                        // lista de objetivos
                        if (!isHidden) {
                            SVGjs.append(" y_ = y__ + rect.height.baseVal.value + " + BOX_SPACING + ";").append("\n");
                        } else {
                            SVGjs.append(" y_ = y__ + " + BOX_SPACING + ";").append("\n");
                        }
                        
                        expression = "//theme[@id='" + tid + "']/obj";
                        NodeList nlObjs = (NodeList) xPath.compile(expression).evaluate(map, XPathConstants.NODESET);
                        for (int m = 0; m < nlObjs.getLength(); m++)
                        {
                            Node nodeO = nlObjs.item(m);
                            if (nodeO != null && nodeO.getNodeType() == Node.ELEMENT_NODE)
                            {
                                attrs = nodeO.getAttributes();
                                String oid = attrs.getNamedItem("id").getNodeValue();
                                String href = attrs.getNamedItem("href").getNodeValue();
                                w_ = assertValue(attrs.getNamedItem("width").getNodeValue());
                                x_ = assertValue(attrs.getNamedItem("x").getNodeValue());
                                String color = attrs.getNamedItem("status").getNodeValue();
                                
                                // objetivo
                                SVGjs.append(" lnk = createLink('" + href + "');").append("\n");
                                SVGjs.append(" g.appendChild(lnk);").append("\n");
                                
                                SVGjs.append(" txt = document.createElementNS(SVG_,'text');").append("\n");
                                
                                SVGjs.append(" txt.setAttributeNS(null,'x',"+x_+");").append("\n");
                                SVGjs.append(" txt.setAttributeNS(null,'y',y_);").append("\n");
                                SVGjs.append(" txt.setAttributeNS(null,'font-family','Verdana');").append("\n");
                                SVGjs.append(" txt.setAttributeNS(null,'text-anchor','start');").append("\n");
                                // prefijo y título de objetivo
                                info = new StringBuilder();
                                expression = "//theme[@id='" + tid + "']/obj[@id='" + oid + "']/prefix";
                                info.append(xPath.compile(expression).evaluate(map, XPathConstants.STRING));
                                info.append(". ");
                                expression = "//theme[@id='" + tid + "']/obj[@id='" + oid + "']/title";
                                info.append(xPath.compile(expression).evaluate(map, XPathConstants.STRING));
                                info.append(".");
                                SVGjs.append(" tspan = document.createElementNS(SVG_, 'tspan');").append("\n");
                                SVGjs.append(" tspan.setAttributeNS(null,'style','fill:#373737;font-weight:normal;font-size:"+HEADER_5+"px;');").append("\n");
                                SVGjs.append(" var text_node = document.createTextNode('"+info+"');").append("\n");
                                SVGjs.append(" tspan.appendChild(text_node);").append("\n");
                                SVGjs.append(" txt.appendChild(tspan);").append("\n");
                                
                                SVGjs.append(" lnk.appendChild(txt);").append("\n");
                                SVGjs.append(" fixParagraphToWidth(txt," + w_ + "," + x_ + ");").append("\n");
                                // sponsor y frecuencia de objetivo
                                info = new StringBuilder();
                                expression = "//theme[@id='" + tid + "']/obj[@id='" + oid + "']/sponsor";
                                info.append(xPath.compile(expression).evaluate(map, XPathConstants.STRING));
                                info.append(" - ");
                                expression = "//theme[@id='" + tid + "']/obj[@id='" + oid + "']/frequency";
                                info.append(xPath.compile(expression).evaluate(map, XPathConstants.STRING));
                                SVGjs.append(" tspan = document.createElementNS(SVG_, 'tspan');").append("\n");
                                SVGjs.append(" tspan.setAttributeNS(null,'x',"+x_+");");
                                SVGjs.append(" tspan.setAttributeNS(null,'dy',"+HEADER_5+");");
                                SVGjs.append(" tspan.setAttributeNS(null,'style','fill:#318bc7;font-weight:bold;font-size:"+HEADER_5+"px;');").append("\n");
                                SVGjs.append(" text_node = document.createTextNode('"+info+"');").append("\n");
                                SVGjs.append(" tspan.appendChild(text_node);").append("\n");
                                SVGjs.append(" txt.appendChild(tspan);").append("\n");
                                // rectángulo objetivo
                                SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
                                SVGjs.append(" framingRect(rect,'" + oid + "'," + w_ + ",0," + x_ + ",y_);").append("\n");
                                SVGjs.append(" rect.setAttributeNS(null,'style','fill:#dedede;stroke:#c1c1c1;stroke-width:1px;');");
                                SVGjs.append(" g.insertBefore(rect,lnk);").append("\n");
                                SVGjs.append(" y_ = y_ + rect.height.baseVal.value + " + BOX_SPACING + ";").append("\n");
                                // estado del objetivo
                                SVGjs.append(" stat = createCircle('stts_" + oid + "',rect.x.baseVal.value-6,rect.y.baseVal.value+5,4,'"+color+"',1,'black',1,1);").append("\n");
                                SVGjs.append(" g.insertBefore(stat,lnk)").append("\n");
                                //.................
                            } //objetivo
                        } //lista de objetivos
                    } //tema
                } // lista de temas
                
                //relaciones causa-efecto de los objetivos de este tema
                expression = "/bsc/perspective[@id='" + pid + "']//obj";
                NodeList nlObjs = (NodeList) xPath.compile(expression).evaluate(map, XPathConstants.NODESET);
                for (int m = 0; m < nlObjs.getLength(); m++)
                {
                    Node nodeO = nlObjs.item(m);
                    if (nodeO != null && nodeO.getNodeType() == Node.ELEMENT_NODE)
                    {
                        attrs = nodeO.getAttributes();
                        String oid = attrs.getNamedItem("id").getNodeValue();
                        w_ = assertValue(attrs.getNamedItem("width").getNodeValue());
                        x_ = assertValue(attrs.getNamedItem("x").getNodeValue());

                        expression = "//obj[@id='" + oid + "']//rel";
                        NodeList nlRels = (NodeList) xPath.compile(expression).evaluate(map, XPathConstants.NODESET);
                        for (int n = 0; n < nlRels.getLength(); n++)
                        {
                            Node nodeR = nlRels.item(n);
                            if (nodeR != null && nodeR.getNodeType() == Node.ELEMENT_NODE)
                            {
                                attrs = nodeR.getAttributes();
                                String to = attrs.getNamedItem("to").getNodeValue();
                                String parent = attrs.getNamedItem("parent").getNodeValue();
                                SVGjs.append(" to = document.getElementById('" + to + "');").append("\n");
                                SVGjs.append(" parent = document.getElementById('" + parent + "');").append("\n");
                                SVGjs.append(" r = document.getElementById('w_" + parent + "');").append("\n");
                                SVGjs.append(" if(r) {").append("\n");
                                SVGjs.append("     w = r.width.baseVal.value;").append("\n");
                                SVGjs.append("     w = w/2;").append("\n");
                                SVGjs.append(" }else {").append("\n");
                                SVGjs.append("     w = 0;").append("\n");
                                SVGjs.append(" }").append("\n");

                                SVGjs.append(" if(to && parent) {").append("\n");
                                SVGjs.append("   to.addEventListener('mouseover', fadeout, false);").append("\n");
                                SVGjs.append("   to.addEventListener('mouseout', fadein, false);").append("\n");
                                SVGjs.append("   matxTo = parent.getCTM();").append("\n");
                                SVGjs.append("   posTo = svg.createSVGPoint();").append("\n");
                                SVGjs.append("   posTo.x = to.x.baseVal.value + w;").append("\n");
                                SVGjs.append("   posTo.y = to.y.baseVal.value;").append("\n");
                                SVGjs.append("   posTo = posTo.matrixTransform(matxTo);").append("\n");
                                SVGjs.append("   matxFrm = g.getCTM();").append("\n");
                                SVGjs.append("   posFrm = svg.createSVGPoint();").append("\n");
                                SVGjs.append("   rect = document.getElementById('" + oid + "');").append("\n");
                                SVGjs.append("   posFrm.x = rect.x.baseVal.value;").append("\n");
                                SVGjs.append("   posFrm.y = rect.y.baseVal.value;").append("\n");
                                SVGjs.append("   posFrm = posFrm.matrixTransform(matxFrm);").append("\n");
                                SVGjs.append("   path = createArrow(posFrm.x+'_'+posFrm.y+'_'+posTo.x+'_'+posTo.y,posFrm.x+" + (w_ / 2) + ",posFrm.y,posTo.x,posTo.y);").append("\n");
                                SVGjs.append("   svg.appendChild(path);").append("\n");
                                SVGjs.append(" }").append("\n");
                            }
                        } //relaciones de objetivo
                    }
                }
                
                
                
                
                

                // caja de la perspectiva
                SVGjs.append(" rect = getBBoxAsRectElement(g);").append("\n");
                SVGjs.append(" rect.setAttributeNS(null,'id','" + pid + "_rct');").append("\n");
                SVGjs.append(" if(rect.height.baseVal.value<150) {").append("\n");
                SVGjs.append("   rect.height.baseVal.value = 150;").append("\n");
                SVGjs.append(" }").append("\n");
                SVGjs.append(" var h_ = Math.round(rect.height.baseVal.value);").append("\n");
                SVGjs.append(" rect.height.baseVal.value = h_;").append("\n");
                SVGjs.append(" rect.setAttributeNS(null,'fill','#f2f2f2');").append("\n");
                SVGjs.append(" rect.setAttributeNS(null,'fill-opacity','1');").append("\n");
                SVGjs.append(" rect.setAttributeNS(null,'stroke','#c1c1c1');").append("\n");
                SVGjs.append(" rect.setAttributeNS(null, 'stroke-width','1');").append("\n");
                SVGjs.append(" rect.setAttributeNS(null, 'stroke-opacity','1');").append("\n");
                SVGjs.append(" g.insertBefore(rect,g.firstChild);").append("\n");
                // título de la perspectiva
                SVGjs.append(" txt = createText('" + perspectiveName + "',(" + px + "+h_/2),(h_-" + BOX_SPACING_RIGHT + ")," + HEADER_3 + ",'Verdana');").append("\n");
                SVGjs.append(" g.appendChild(txt);").append("\n");
//                SVGjs.append("if(txt.getComputedTextLength() > h_) {").append("\n");
                SVGjs.append("  txt.setAttributeNS(null,'textLength',h_);").append("\n");
                SVGjs.append("  if(txt.getComputedTextLength() > 1.5*h_) {").append("\n");
                SVGjs.append("    txt.setAttributeNS(null,'lengthAdjust','spacingAndGlyphs');").append("\n");
                SVGjs.append("  }else {").append("\n");
                SVGjs.append("    txt.setAttributeNS(null,'lengthAdjust','spacing');").append("\n");
                SVGjs.append("  }").append("\n");
//                SVGjs.append("}").append("\n");
                SVGjs.append(" txt.setAttributeNS(null,'style','fill:#ffffff;');").append("\n");
                SVGjs.append(" txt.setAttributeNS(null,'transform','rotate(270," + px + ",'+h_+')');").append("\n");
                SVGjs.append(" txt.setAttributeNS(null,'text-anchor','middle');").append("\n");
                // Resalte del título de la perspectiva                
                SVGjs.append(" rect = getBBoxAsRectElement(txt);").append("\n");
                SVGjs.append(" rect.setAttributeNS(null,'style','stroke-width:0;fill:#0071BC;');").append("\n");
                SVGjs.append(" rect.setAttributeNS(null,'transform','rotate(270," + px + ",'+h_+')');").append("\n");
                SVGjs.append(" g.insertBefore(rect,txt);").append("\n");                

                SVGjs.append(" y = y + h_ + " + MARGEN_BOTTOM + ";").append("\n");
            } // perspectiva
        } // lista de perspectivas
        SVGjs.append("};").append("\n"); // window.onload

        // funciones
        SVGjs.append("function createLink(url) {").append("\n");
        SVGjs.append("  var a = document.createElementNS(SVG_ ,'a');").append("\n");
        SVGjs.append("  a.setAttributeNS(XLINK_,'href',url);").append("\n");
        SVGjs.append("  a.setAttributeNS(null,'title','Ver detalle...');").append("\n");
        SVGjs.append("  return a;").append("\n");
        SVGjs.append("}").append("\n");

        


        SVGjs.append("function fadeout(evt) {").append("\n");
        //SVGjs.append("    evt.target.style.stroke = '#0000FF';").append("\n");
        SVGjs.append("    evt.target.style.strokeWidth = '7';").append("\n");
        SVGjs.append("    evt.target.setAttributeNS(null,'stroke-opacity',1);").append("\n");
        SVGjs.append("}").append("\n");
        SVGjs.append("function fadein(evt) {").append("\n");
        //SVGjs.append("    evt.target.style.stroke = '#FF0099';").append("\n");
        SVGjs.append("    evt.target.style.strokeWidth = '2';").append("\n");
        SVGjs.append("    evt.target.setAttributeNS(null,'stroke-opacity',1);").append("\n");
        SVGjs.append("}").append("\n");

SVGjs.append("var colors=new Array('#d02090','#ee82ee','#8a2be2','#0000ff','#00008b','#8ee5ee','#00ff00','#008b00','#adff2f','#ffff00','#8b8b00','#ffa500','#8b5a00','#ff4500','#ff0000','#8b0000','#8a8a8a','#1a1a1a');").append("\n");
SVGjs.append("var colorIndex = 0;").append("\n");   // recupera el siguiente código de color almacenado en el arreglo colors
SVGjs.append("function resetColorIndex() {").append("\n");
SVGjs.append(" colorIndex = 0;").append("\n");
SVGjs.append("}").append("\n");
SVGjs.append("function colorIndexOf(index) {").append("\n");
SVGjs.append("  var color = null;").append("\n");
SVGjs.append("  try {").append("\n");
SVGjs.append("    index++; ").append("\n");
SVGjs.append("  }catch(err) {").append("\n");
SVGjs.append("    index=0;").append("\n");
SVGjs.append("    resetColorIndex();").append("\n");
SVGjs.append("  }").append("\n");
SVGjs.append("  color = colors[index%colors.length]").append("\n");
SVGjs.append("  return color;").append("\n");
SVGjs.append("}").append("\n");
SVGjs.append("var rp_=new Array();").append("\n");        
SVGjs.append("var offset_v = 0;").append("\n");
SVGjs.append("var offset_h = 5;").append("\n");
        

        SVGjs.append("function createArrow(id,x1,y1,x2,y2) {").append("\n");
        //SVGjs.append("  var color = '#'+Math.floor(Math.random()*16777215).toString(16);").append("\n");
        //SVGjs.append("  percent_+=15;").append("\n");
        SVGjs.append("  console.log('1.-colorIndex='+colorIndex);").append("\n");
        SVGjs.append("  var color = colorIndexOf(colorIndex++);").append("\n");
        SVGjs.append("  console.log('2.-colorIndex='+colorIndex+', color='+color);").append("\n");
        SVGjs.append("  var arrow = createPath(id,x1,y1,x2,y2,color);").append("\n");
        SVGjs.append("  arrow.setAttributeNS(null, 'marker-end', 'url(#arrow_1)');").append("\n");
        SVGjs.append("  arrow.setAttributeNS(null, 'stroke-opacity', 1);").append("\n");
        SVGjs.append("  arrow.addEventListener('mouseover', fadeout, false);").append("\n");
        //SVGjs.append("  arrow.addEventListener('mouseout', fadein, false);").append("\n");
        SVGjs.append("  arrow.addEventListener('click', fadeout, false);").append("\n");
        SVGjs.append("  arrow.addEventListener('dblclick', fadein, false);").append("\n");
        SVGjs.append("  return arrow;").append("\n");
        SVGjs.append("}").append("\n");

        SVGjs.append("function createPath(id,x1,y1,x2,y2,color) {").append("\n");
SVGjs.append("  if(srch1(x1,y1)) {").append("\n");
SVGjs.append("    offset_h+=3;").append("\n");
SVGjs.append("    x1-=3;").append("\n");
SVGjs.append("  }else if(srch2(y1)) {").append("\n");
SVGjs.append("    offset_h+=3;").append("\n");
SVGjs.append("  }else {").append("\n");
SVGjs.append("    offset_h = 5;").append("\n");
SVGjs.append("    rp_.push(new Array(x1,y1));").append("\n");
SVGjs.append("  }").append("\n");

SVGjs.append("  if( srch2(y2) ) {").append("\n");
SVGjs.append("    rp_.push(new Array(x2,y2));").append("\n");
SVGjs.append("    y2-=9;").append("\n");
SVGjs.append("  }else {").append("\n");
SVGjs.append("    rp_.push(new Array(x2,y2));").append("\n");
SVGjs.append("  }").append("\n");
SVGjs.append("  y2-=2;").append("\n");
        
                
        SVGjs.append("  var path = document.createElementNS(SVG_,'path');").append("\n");
        SVGjs.append("  var d = 'M'+x1+','+y1+' L'+(x1)+','+(y1-offset_h)+' L'+(width-offset_v)+','+(y1-offset_h)+' L'+(width-offset_v)+','+y2+' L'+x2+','+y2;").append("\n");
        SVGjs.append("  offset_v+=8;").append("\n");
        SVGjs.append("  path.setAttributeNS(null, 'id', id);").append("\n");
        SVGjs.append("  path.setAttributeNS(null, 'd', d);").append("\n");
        SVGjs.append("  path.style.fill = 'none';").append("\n");
        SVGjs.append("  path.style.stroke = color;").append("\n");
        SVGjs.append("  path.style.strokeWidth = '2';").append("\n");
        //SVGjs.append("  rp_.push(new Array(x1,y1));").append("\n");
        SVGjs.append("  return path;").append("\n");
        SVGjs.append("}").append("\n");
        
SVGjs.append("function srch1(x,y) {").append("\n");
SVGjs.append("  for(var i=0; i<rp_.length; i++) {").append("\n");
SVGjs.append("    if(rp_[i][0]==x && rp_[i][1]==y) {").append("\n");
SVGjs.append("      return true;").append("\n");
SVGjs.append("    }").append("\n");
SVGjs.append("  }").append("\n");
SVGjs.append("  return false;").append("\n");
SVGjs.append("}").append("\n");

SVGjs.append("function srch2(y) {").append("\n");
SVGjs.append("  for(var i=0; i<rp_.length; i++) {").append("\n");
SVGjs.append("    if(rp_[i][1]==y) {").append("\n");
SVGjs.append("      return true;").append("\n");
SVGjs.append("    }").append("\n");
SVGjs.append("  }").append("\n");
SVGjs.append("  return false;").append("\n");
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

        SVGjs.append("function framingRect(rect,id,width, height, x, y) {").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'id',id);").append("\n");
        SVGjs.append("  //rect.x.baseVal.value = x;").append("\n");
        SVGjs.append("  //rect.y.baseVal.value = y;").append("\n");
        SVGjs.append("  rect.width.baseVal.value = width;").append("\n");
        SVGjs.append("  //rect.height.baseVal.value = height;").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'fill','#f2f2f2');").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'fill-opacity',1);").append("\n");
        SVGjs.append("  rect.setAttributeNS(null,'stroke','#e5e5e5');").append("\n");
        SVGjs.append("  rect.setAttributeNS(null, 'stroke-width','1');").append("\n");
        SVGjs.append("  rect.setAttributeNS(null, 'stroke-opacity',1);").append("\n");
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

    private int assertValue(final String textVal) {
        int val;
        try {
            val = Integer.parseInt(textVal);
        } catch (NumberFormatException nfe) {
            val = 0;
        } catch (NullPointerException nulle) {
            val = 0;
        }
        return val;
    }

    private int vPadding(int value) {
        return value + PADDING_TOP + PADDING_DOWN;
    }

    private int topPadding(int value) {
        return value + PADDING_TOP;
    }
}
