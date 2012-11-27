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
package org.semanticwb.portal.resources;

import java.io.*;
//import java.sql.*;
import javax.servlet.http.*;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
//import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.portal.api.SWBResourceURL;

// TODO: Auto-generated Javadoc
/**
 * The Class SparqlQueryResource.
 * 
 * @author juan.fernandez
 */
public class SparqlQueryResource extends GenericAdmResource {

    /** The tpl. */
    private javax.xml.transform.Templates tpl;
    /**
     * XML form definition data.
     */
    /*private String xml;*/
    String path = SWBPlatform.getContextPath() + "/swbadmin/xsl/SparqlQueryResource/";
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SparqlQueryResource.class);

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void processRequest(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramsRequest)
            throws SWBResourceException, IOException {

        if (paramsRequest.getMode().equals("excel_file")) {
            doExcel(request, response, paramsRequest);
        } else {
            super.processRequest(request, response, paramsRequest);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#setResourceBase(org.semanticwb.model.Resource)
     */
    @Override
    public void setResourceBase(Resource base) {
        try {
            super.setResourceBase(base);
        } catch (Exception e) {
            log.error("Error while setting resource base: " + base.getId(), e);
        }
        if (!"".equals(base.getAttribute("template", "").trim())) {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT(
                        SWBUtils.IO.getStreamFromString(
                        SWBUtils.IO.getFileFromPath(base.getWorkPath() + "/" + base.getAttribute("template").trim())));
                path = SWBPortal.getWebWorkPath() + base.getWorkPath() + "/";
            } catch (Exception e) {
                log.error("Error while loading resource template: " + base.getId(), e);
            }
        }
        if (tpl == null) {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT(
                        SWBPortal.getAdminFileStream(
                        "/swbadmin/xsl/SparqlQueryResource/SparqlQueryResource.xslt"));
            } catch (Exception e) {
                log.error("Error while loading default resource template: " + base.getId(), e);
            }
        }
    }

    /**
     * Gets the document that represents the query results.
     * 
     * @param request the action request
     * @param response the action response
     * @param paramsRequest request arguments
     * @return the dom
     * @throws SWBResourceException if an exception occurs while trying to get
     * the document.
     */
    private Document getDom(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramsRequest)
            throws SWBResourceException {

        Resource base = getResourceBase();
        String excelFile = (String) request.getAttribute("excelFile");
        boolean excelf = false;
        if (excelFile != null && "yes".equalsIgnoreCase(excelFile)) {
            excelf = true;
        }
        String rowsHistory = "20";
        String pagesHistory = "5";
        String showPages = "1";
        if (base.getAttribute("rows") != null) {
            rowsHistory = base.getAttribute("rows");
        }
        if (base.getAttribute("pages") != null) {
            pagesHistory = base.getAttribute("pages");
        }
        if (base.getAttribute("showPages") != null) {
            showPages = base.getAttribute("showPages");
        }
        Document doc = null;
        //ByteArrayOutputStream bout = new ByteArrayOutputStream();
        String _query = parse(base.getAttribute("query"), request, paramsRequest);
        String dbcon = "";
        int maximo = 5;
        if (pagesHistory != null) {
            maximo = Integer.parseInt(pagesHistory);
        }
        int maxRows = 10;
        if (rowsHistory != null) {
            maxRows = Integer.parseInt(rowsHistory);
        }
        int actualPage = 1;
        if (request.getParameter("actualPage") != null) {
            actualPage = Integer.parseInt(request.getParameter("actualPage"));
        }
        //base.getAttribute("dbcon");
        if (_query != null) {
            try {
                doc = SWBUtils.XML.getNewDocument();
                Element equery = doc.createElement("QUERY");
                doc.appendChild(equery);
                equery.setAttribute("query", _query);
                equery.setAttribute("dbcon", dbcon);
                equery.setAttribute("path",
                        "http://" + request.getServerName() + (request.getServerPort() != 80
                        ? ":" + request.getServerPort()
                        : "") + SWBPlatform.getContextPath() + "/swbadmin/css/");
                addElem(doc, equery, "title", base.getTitle());
                addElem(doc, equery, "description", base.getDescription());

                try {
                    //Model model = SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();
                    SemanticModel model = null;
                    if(base.getAttribute("dbPedia","0").endsWith("1"))
                    {
                        model=SWBPlatform.getSemanticMgr().getModel("DBPedia");
                    }
                    if(model==null)model=paramsRequest.getWebPage().getWebSite().getSemanticModel();

                    String queryString = _query;

                    QueryExecution qexec = model.sparQLQuery(queryString);
                    // Or QueryExecutionFactory.create(queryString, model) ;
                    try {
                        // Assumption: it's a SELECT query.
                        ResultSet rs = qexec.execSelect();
                        int col = rs.getResultVars().size();
                        equery.setAttribute("ncol", "" + col);
                        Element eheader = doc.createElement("header");
                        equery.appendChild(eheader);

                        int x = 1;
                        Iterator<String> itcols = rs.getResultVars().iterator();
                        while (itcols.hasNext()) {
                            Element ecol = addElem(doc, eheader, "col_name", itcols.next());
                            x++;
                        }

                        Element eresult = doc.createElement("result");
                        equery.appendChild(eresult);
                        // The order of results is undefined.

                        int cuenta = 0;
                        if (request.getParameter("actualPage") != null) {
                            actualPage = Integer.parseInt(request.getParameter("actualPage"));
                        }
                        int rangoIni = 1;
                        int rangoFin = maxRows * actualPage;
                        if (actualPage > 1) {
                            rangoIni = (maxRows * (actualPage - 1)) + 1;
                        }

                        int numReg = 0; // número de registros

                        // Reporte
                        while (rs.hasNext()) {
                            QuerySolution rb = rs.nextSolution();
                            numReg++;
                            cuenta++;
                            if (cuenta >= rangoIni && cuenta <= rangoFin && !excelf) {
                                Element erow = doc.createElement("row");
                                eresult.appendChild(erow);

                                Iterator<String> it = rs.getResultVars().iterator();
                                while (it.hasNext()) {
                                    String name = it.next();
                                    RDFNode x_node = rb.get(name);
                                    String sval = (x_node != null ? x_node.toString() : " - ");

                                    if(x_node.isLiteral())
                                    {
                                        sval=x_node.asNode().getLiteralValue().toString();
                                    }else if(x_node.isResource())
                                    {
                                        sval=x_node.asNode().getLocalName();
                                    }
                                    if(sval.trim().length()==0)
                                    {
                                        sval=x_node.toString();
                                    }


                                    Element ecol = addElem(doc, erow, "col", sval);
                                }
                            } else if(excelf) {
                                Element erow = doc.createElement("row");
                                eresult.appendChild(erow);

                                Iterator<String> it = rs.getResultVars().iterator();
                                while (it.hasNext()) {
                                    String name = it.next();
                                    RDFNode x_node = rb.get(name);
                                    String sval = (x_node != null ? x_node.toString() : " - ");
                                    Element ecol = addElem(doc, erow, "col", sval);
                                }
                            }
                            
                        }
                        equery.setAttribute("nrow", "" + numReg);


                        // Paginación
                        if (!excelf) {
                            Element epages = doc.createElement("pages");
                            equery.appendChild(epages);

                            int numTotPages = (int) Math.round(numReg / maxRows);
                            if ((numReg % maxRows) > 0) {
                                numTotPages++;
                            }
                            maximo = maximo - 1;
                            StringBuffer numeros = new StringBuffer("");
                            if (showPages != null) {
                                if (showPages.equals("1")) {
                                    int numPages = 1;
                                    //estableciendo rangos para mostrar las paginas correspondientes
                                    int rInicio = 1;
                                    int rFinal = maximo;
                                    if (numTotPages >= (actualPage + maximo)) {
                                        rInicio = actualPage;
                                        rFinal = actualPage + maximo;
                                    } else {
                                        if (numTotPages < (actualPage + maximo)) {
                                            rInicio = numTotPages - maximo;
                                            rFinal = numTotPages;
                                        }
                                    }
                                    if (rInicio < 1) {
                                        rInicio = 1;
                                    }
                                    // armando números de páginas

                                    for (int i = 1; i <= numTotPages; i++) {

                                        SWBResourceURL urlNums = paramsRequest.getRenderUrl().setMode(paramsRequest.Mode_VIEW);
                                        urlNums.setParameter("actualPage", Integer.toString(i));
                                        if (i >= rInicio && i <= rFinal) {

                                            Element epage = doc.createElement("page");
                                            epages.appendChild(epage);

                                            numPages++;
                                            if (i != actualPage) {
                                                epage.setAttribute("link", "<a href=\"" + urlNums.toString() + "\" >" + i + "</a>");
                                            //numeros.append("<a href=\"#\" onclick=\"submitUrl('" + urlNums.toString() + "',this); return false;\" >" + i + "</a>");
                                            } else {
                                                epage.setAttribute("link", "" + i + "");
                                            //numeros.append("" + i + "");
                                            }
                                        }
                                        if (i < numTotPages) {
                                            //numeros.append("&nbsp;");
                                        }
                                    }
                                //numeros.append("&nbsp;");
                                }
                            }
                            SWBResourceURL urlBack = paramsRequest.getRenderUrl().setMode(paramsRequest.Mode_VIEW);
                            int tmpBack = actualPage;
                            if (actualPage > 1) {
                                tmpBack--;
                            }
                            urlBack.setParameter("actualPage", Integer.toString(tmpBack));

                            SWBResourceURL urlNext = paramsRequest.getRenderUrl().setMode(paramsRequest.Mode_VIEW);
                            urlNext.setParameter("actualPage", Integer.toString(actualPage + 1));
                            String nextHistory = paramsRequest.getLocaleString("defaultValueNext");
                            String backHistory = paramsRequest.getLocaleString("defaultValuePrevious");
                            if (numReg <= maxRows) {
                            } else {
                                //out.println("<fieldset>");
                                //out.println("<div align=\"center\">");
                                if (actualPage == 1) {
                                    Element epagenext = doc.createElement("pagenext");
                                    epages.appendChild(epagenext);
                                    epagenext.setAttribute("link", urlNext.toString());
                                    epagenext.setAttribute("texto", nextHistory);
                                //out.println(numeros + "<a href=\"#\" onclick=\"submitUrl('" + urlNext + "',this); return false;\" >" + nextHistory + "</a>");
                                } else if (actualPage == numTotPages) {
                                    Element epageback = doc.createElement("pageback");
                                    epages.appendChild(epageback);
                                    epageback.setAttribute("link", urlBack.toString());
                                    epageback.setAttribute("texto", backHistory);
//
                                //out.println("<a href=\"#\" onclick=\"submitUrl('" + urlBack + "',this); return false;\" >" + backHistory + "</a>&nbsp;" + numeros);
                                } else {
                                    Element epageback = doc.createElement("pageback");
                                    epages.appendChild(epageback);
                                    epageback.setAttribute("link", urlBack.toString());
                                    epageback.setAttribute("texto", backHistory);
                                    Element epagenext = doc.createElement("pagenext");
                                    epages.appendChild(epagenext);
                                    epagenext.setAttribute("link", urlNext.toString());
                                    epagenext.setAttribute("texto", nextHistory);
                                //out.println("<a href=\"#\" onclick=\"submitUrl('" + urlBack + "',this); return false;\" >" + backHistory + "</a>&nbsp;" + numeros + "<a href=\"#\" onclick=\"submitUrl('" + urlNext + "',this); return false;\" >" + nextHistory + "</a>");
                                }
                            //out.println("</div>");
                            //out.println("</fieldset>");
                            }
                        }

                    } finally {
                        // QueryExecution objects should be closed to free any system resources
                        qexec.close();
                    }

                } catch (Exception e) {
                    log.error("Error al ejecutar el SparqlQueryResource.", e);
                }

            } catch (Exception e) {
                log.error(paramsRequest.getLocaleString(
                        "error_QueryResource_getDom"), e);
            }
        }
        return doc;
    }

    /**
     * Called by <code>render()</code> method when the resource is in View mode.
     * Intended to contain logic that displays the view page for the resource.
     *
     * @param request the action request
     * @param response the action response
     * @param paramsRequest request arguments
     * @throws SWBResourceException if an exception occurs when displaying the
     *         view page.-
     * @throws IOException if streaming causes an I/O problem.
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PrintWriter out = new PrintWriter(bout);
        try {
            Document doc = getDom(request, response, paramsRequest);
            out.print(SWBUtils.XML.transformDom(tpl, doc));
        } catch (Exception e) {
            log.error(paramsRequest.getLocaleString(
                    "error_QueryResource_doView"), e);
            out.println(paramsRequest.getLocaleString(
                    "usrmsg_QueryResource_queryError"));
        }
        out.flush();

        PrintWriter webOut = response.getWriter();
        String excelFile = (String) request.getAttribute("excelFile");

        if (excelFile != null && "yes".equalsIgnoreCase(excelFile)) {
            response.setContentType("application/vnd.ms-excel");
        }
        webOut.println(new String(bout.toByteArray()));

        if (excelFile == null || !"yes".equalsIgnoreCase(excelFile)) {
            webOut.println("<br><a href=\"" + paramsRequest.getRenderUrl().setCallMethod(
                    SWBParamRequest.Call_DIRECT).setMode("excel_file") + "\">" + paramsRequest.getLocaleLogString("usrmsg_ExcelFile") + "</a>");
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doXML(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        PrintWriter out = response.getWriter();
        out.println(SWBUtils.XML.domToXml(getDom(request, response, paramRequest),true));
    }



    /**
     * Adds element to the document received as child of the specified element
     * with the name and the value received.
     * 
     * @param doc <code>Document</code> base.
     * @param parent new <code>Element</code>'s parent <code>Element</code>.
     * @param elemName new <code>Element</code>'s name.
     * @param elemValue new <code>Element</code>'s value.
     * @return elem  created.
     */
    private Element addElem(Document doc, Element parent, String elemName,
            String elemValue) {

        Element elem = doc.createElement(elemName);
        elem.appendChild(doc.createTextNode(elemValue));
        parent.appendChild(elem);
        return elem;
    }

    /**
     * Called by <code>getDom()</code> method when query string is analized.
     *
     * @param query Query String to parse.
     * @param request the action request.
     * @param paramsRequest request arguments.
     * @return string prepared for execute query
     */
    public String parse(String query,
            HttpServletRequest request,
            SWBParamRequest paramsRequest) {

        String ret = "";
        if (query == null) {
            return null;
        }
        int l = query.length();
        int a = 0;
        int i = 0;
        int f = 0;
        while ((i = query.indexOf("{", a)) > -1) {
            ret += query.substring(a, i);
            if ((f = query.indexOf("}", i)) > -1) {
                a = f + 1;
                String com = query.substring(i + 1, f);
                if (com.equals("topicmap")) {
                    ret += paramsRequest.getWebPage().getWebSiteId();
                } else if (com.equals("topicid")) {
                    ret += paramsRequest.getWebPage().getId();
                } else if (com.equals("userid")) {
                    ret += paramsRequest.getUser().getEmail();
                } else if (com.startsWith("getUserAttribute(") && com.endsWith(")")) {
                    String aux = com.substring(17, com.length() - 1);
                    ret += paramsRequest.getUser().getProperty(aux);
                } else if (com.startsWith("getParameter(") && com.endsWith(")")) {
                    String aux = com.substring(13, com.length() - 1);
                    ret += request.getParameter(aux);
                } else if (com.startsWith("getArgument(") && com.endsWith(")")) {
                    String aux = com.substring(12, com.length() - 1);
                    ret += paramsRequest.getArguments().get(aux);
                } else {
                    ret += query.substring(i, f + 1);
                }
            } else {
                a = l + 1;
            }
        }
        if (a < l) {
            ret += query.substring(a);
        }
        ret = ret.trim();
        log.debug("return " + ret);
        return ret;
    }

    /**
     * Do excel.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doExcel(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {

        request.setAttribute("excelFile", "yes");
        doView(request, response, paramsRequest);
    }
}