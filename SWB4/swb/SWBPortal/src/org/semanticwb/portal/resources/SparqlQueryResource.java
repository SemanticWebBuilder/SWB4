/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import java.util.Iterator;

/**
 *
 * @author juan.fernandez
 */
public class SparqlQueryResource extends GenericAdmResource {

    private javax.xml.transform.Templates tpl;
    /**
     * XML form definition data.
     */
    /*private String xml;*/
    String path = SWBPlatform.getContextPath() + "/swbadmin/xsl/SparqlQueryResource/";
    private static Logger log = SWBUtils.getLogger(SparqlQueryResource.class);

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
                path = SWBPlatform.getWebWorkPath() + base.getWorkPath() + "/";
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
     * @throws SWBResourceException if an exception occurs while trying to get
     *         the document.
     */
    private Document getDom(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramsRequest)
            throws SWBResourceException {

        Resource base = getResourceBase();
        Document doc = null;
        //ByteArrayOutputStream bout = new ByteArrayOutputStream();
        String _query = parse(base.getAttribute("query"), request, paramsRequest);
        String dbcon = "";
        base.getAttribute("dbcon");
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

                    Model model = SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();

                    String queryString = _query;

                    Query query = QueryFactory.create(queryString);
                    query.serialize(); //new IndentedWriter(response.getOutputStream(),true)) ;
                    // Create a single execution of this query, apply to a model
                    // which is wrapped up as a Dataset
                    QueryExecution qexec = QueryExecutionFactory.create(query, model);
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

                        for (; rs.hasNext();) {
                            QuerySolution rb = rs.nextSolution();

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
     *         view page.
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

    /**
     * Adds element to the document received as child of the specified element
     * with the name and the value received.
     *
     * @param doc <code>Document</code> base.
     * @param parent new <code>Element</code>'s parent <code>Element</code>.
     * @param elemName new <code>Element</code>'s name.
     * @param elemValue new <code>Element</code>'s value.
     * @return elem <code>Element</code> created.
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
                    ret += paramsRequest.getTopic().getWebSiteId();
                } else if (com.equals("topicid")) {
                    ret += paramsRequest.getTopic().getId();
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

    public void doExcel(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {

        request.setAttribute("excelFile", "yes");
        doView(request, response, paramsRequest);
    }
}