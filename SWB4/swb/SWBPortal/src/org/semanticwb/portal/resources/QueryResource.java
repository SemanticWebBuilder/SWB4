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
import java.sql.*;
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


// TODO: Auto-generated Javadoc
/**
 * Despliega el resultado de un consulta a base de datos proporcionada por el usuario.
 * <p>Displays the results of a database query provided by the user.</p>
 *
 * @author: Javier Solis
 * @version 1.1
 */
public class QueryResource extends GenericAdmResource {


    /**
     * la plantilla XSLT para generar la vista de los resultados de la consulta
     * de base de datos. <p>the XSLT template that generates the database query
     * results' view.</p>
     */
    private javax.xml.transform.Templates tpl;

    /**
     * la ruta del directorio en que se encuentran los archivos utilizados por 
     * el recurso para generar la vista de los resutados de la consulta a base 
     * de datos. <p>the path of the directory which files used by this resource
     * for generating the database query results' view are stored in.</p>
     */
    String path = SWBPlatform.getContextPath() + "/swbadmin/xsl/QueryResource/";

    /**
     * objeto encargado de crear mensajes en los archivos log de SemanticWebBuilder (SWB).
     * <p>object that creates messages in SWB's log file.</p>
     */
    private static Logger log = SWBUtils.getLogger(QueryResource.class);


    /**
     * Determina el m&eacute;todo a ejecutar de acuerdo al modo indicado en la petici&oacute;n
     * HTTP enviada por el usuario. <p>Determines which method is going to be
     * executed based on the mode indicated in the user's HTTP request.</p>
     * 
     * @param request la petici&oacute;n HTTP generada por el usuario. <p>the user's HTTP request</p>
     * @param response la respuesta hacia el usuario.<p>the response to the user</p>
     * @param paramsRequest el objeto generado por SWB y asociado a la petici&oacute;n
     * del usuario.<p>the object gnerated by SWB and asociated to the user's request</p>
     * @throws org.semanticwb.portal.api.SWBResourceException si el m&eacute;todo ejecutado
     * la propaga. <p>if the executed method propagates the exception</p>
     * @throws java.io.IOException si el m&eacute;todo ejecutado la propaga. <p>if the
     * executed method propagates the exception</p>
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
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

    /**
     * Asocia la plantilla XSLT indicada por el usuario en la vista de administraci&oacute;n.
     * Si el usuario no indica alguna, se utiliza la proporcionada por defecto.
     * <p>Associates the XSLT template indicated by the user in the administration
     * view. If none is indicated, the one that is provided by default is used.</p>
     * @param base    un {@code resource} con la informaci&oacute;n (plantilla XSLT)
     *                seleccionada en la vista de administraci&oacute;n de este recurso
     *                <p>a resource with the information (the XSLT template) selected
     *                in this resource's administration view</p>
     */
    @Override
    public void setResourceBase(Resource base) {

        try {
            super.setResourceBase(base);
        } catch(Exception e) {
            log.error("Error while setting resource base: " + base.getId(), e);
        }
        if (!"".equals(base.getAttribute("template", "").trim())) {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT(
                        SWBUtils.IO.getStreamFromString(
                        SWBUtils.IO.getFileFromPath(base.getWorkPath() + "/"
                        + base.getAttribute("template").trim())));
                path = SWBPortal.getWebWorkPath() + base.getWorkPath() + "/";
            } catch(Exception e) {
                log.error("Error while loading resource template: " 
                          + base.getId(), e);
            }
        }
        if (tpl == null) {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT(
                        SWBPortal.getAdminFileStream(
                        "/swbadmin/xsl/QueryResource/QueryResource.xslt"));
            } catch (Exception e) {
                log.error("Error while loading default resource template: " 
                          + base.getId(), e);
            }
        }
    }
    
    /**
     * Genera un {@code Document} con la informaci&oacute;n devuelta por la consulta
     * a la base de datos. <p>Generates a {@code Document} with the data extracted
     * by the database query</p>.
     * 
     * @param request la petici&oacute;n HTTP generada por el usuario. <p>the user's HTTP request</p>
     * @param response la respuesta hacia el usuario.<p>the response to the user</p>
     * @param paramsRequest el objeto generado por SWB y asociado a la petici&oacute;n
     * del usuario.<p>the object generated by SWB and asociated to the user's request</p>
     * @return the dom
     * @throws SWBResourceException si no existe el archivo de mensajes del idioma utilizado.
     * <p>if there is no file message of the corresponding language.</p>
     */
    private Document getDom(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramsRequest)
            throws SWBResourceException {
        
        Resource base = getResourceBase();
        Document doc = null;
        //ByteArrayOutputStream bout = new ByteArrayOutputStream();
        String query = parse(base.getAttribute("query"), request, paramsRequest);
        String dbcon = base.getAttribute("dbcon");
        if (query != null) {
            try {
                doc = SWBUtils.XML.getNewDocument();
                Element equery = doc.createElement("QUERY");
                doc.appendChild(equery);
                equery.setAttribute("query", query);
                equery.setAttribute("dbcon", dbcon);
                equery.setAttribute("path",
                        "http://" + request.getServerName()
                        + (request.getServerPort() != 80 
                        ? ":" + request.getServerPort()
                        : "")
                        + SWBPlatform.getContextPath() + "/swbadmin/css/");
                equery.setAttribute("styleClass",
                        base.getAttribute("styleClass", "").equals("")
                        ? ""
                        : "<div class=\"" + base.getAttribute("styleClass", "") + "\">");
                equery.setAttribute("styleClassClose",
                        base.getAttribute("styleClass", "").equals("")
                        ? ""
                        : "</div>");
                addElem(doc, equery, "title", base.getTitle());
                addElem(doc, equery, "description", base.getDescription());
                Connection con = null;
                if (dbcon != null && dbcon.length() > 0) {
                    con = SWBUtils.DB.getConnection(dbcon,
                            "From QueryResource.getDom()");
                    if (con != null) {
                        Statement st = con.createStatement();
                        if (st != null) {
                            ResultSet rs = st.executeQuery(query);
                            ResultSetMetaData md = rs.getMetaData();
                            int col = md.getColumnCount();
                            equery.setAttribute("ncol", "" + col);
                            Element eheader = doc.createElement("header");
                            equery.appendChild(eheader);
                            
                            for (int x = 1; x <= col; x++) {
                                Element ecol = addElem(doc, eheader, "col_name",
                                                       md.getColumnName(x));
                            }
                            Element eresult = doc.createElement("result");
                            equery.appendChild(eresult);
                            while (rs.next()) {
                                Element erow = doc.createElement("row");
                                eresult.appendChild(erow);
                                for (int x = 1; x <= col; x++) {
                                    String aux = rs.getString(x);
                                    if (aux == null) {
                                        aux = "";
                                    }
                                    Element ecol = addElem(doc, erow, "col", aux);
                                }
                            }
                            rs.close();
                            st.close();
                            con.close();
                        }
                    }
                }
            } catch (Exception e) {
                log.error(paramsRequest.getLocaleString(
                        "error_QueryResource_getDom"), e);
            }
        }
//        System.out.println("doc generado:" + SWBUtils.XML.domToXml(doc));
        return doc;
    }

    /**
     * Presenta los resultados de la consulta a base de datos en el navegador con
     * la distribuci&oacute;n de datos contenida en la plantilla XSLT.
     * <p>Presents the database query results in the browser with the layout
     * defined in the XSLT template.</p>
     * @param request la petici&oacute;n HTTP generada por el usuario. <p>the user's HTTP request</p>
     * @param response la respuesta hacia el usuario.<p>the response to the user</p>
     * @param paramsRequest el objeto generado por SWB y asociado a la petici&oacute;n
     *        del usuario.<p>the object generated by SWB and asociated to the user's request</p>
     * @throws SWBResourceException si no existe el archivo de mensajes del idioma utilizado.
     *         <p>if there is no file message of the corresponding language.</p>
     * @throws IOException al obtener el <code>Writer</code> del <code>response</code> correspondiente.
     *         </p>when getting the corresponding <code>response</code>'s <code>Writer</code>.</p>
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramsRequest)
            throws SWBResourceException, IOException {

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
            webOut.println("<br><a href=\"" 
                    + paramsRequest.getRenderUrl().setCallMethod(
                    SWBParamRequest.Call_DIRECT).setMode("excel_file")
                    + "\">" + paramsRequest.getLocaleLogString("usrmsg_ExcelFile")
                    + "</a>");
        }
    }

    /**
     * Agrega un elemento a un DOM, como hijo del elemento indicado, con el nombre
     * y valor especificados.
     * <p>Adds an element to a DOM, as child of the specified element with the
     * name and the value received.
     * 
     * @param doc el documento a modificar <p>the <code>document</code> to modify.</p>
     * @param parent el elemento padre del elemento a agregar, contenido en el
     * documento <p>the new <code>element</code>'s parent <code>element</code>.</p>
     * @param elemName la cadena con el nombre del nuevo elemento <p>the string with the new <code>element</code>'s name.
     * @param elemValue el valor del nuevo elemento <p>new <code>element</code>'s value.</p>
     * @return el elemento creado 
     */
    private Element addElem(Document doc, Element parent, String elemName,
                            String elemValue) {
        
        Element elem = doc.createElement(elemName);
        elem.appendChild(doc.createTextNode(elemValue));
        parent.appendChild(elem);
        return elem;
    }

    /**
     * Analiza la consulta a executar para agregar valores a los criterios
     * de selecci&oacute;n de datos.
     * <p>Parses the database query to execute in order to add values to the data
     * selection criteria.</p>
     * 
     * @param query la cadena con la consulta a ejecutar <p>the {@code string}
     * containing the database quey to execute.</p>
     * @param request la petici&oacute;n HTTP generada por el usuario. <p>the user's HTTP request</p>
     * @param paramsRequest el objeto generado por SWB y asociado a la petici&oacute;n
     * del usuario.<p>the object generated by SWB and asociated to the user's request</p>
     * @return la cadena completamente preparada para ejecutar la consulta a base de datos
     * 
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
     * Muestra los datos extraidos por la consulta a base de datos en una ventana
     * de Excel. <p>Shows the data extracted by the database query in an Excel window.</p>
     * @param request la petici&oacute;n HTTP generada por el usuario. <p>the user's HTTP request</p>
     * @param response la respuesta hacia el usuario.<p>the response to the user</p>
     * @param paramsRequest el objeto generado por SWB y asociado a la petici&oacute;n
     *        del usuario.<p>the object generated by SWB and asociated to the user's request</p>
     * @throws SWBResourceException si no existe el archivo de mensajes del idioma utilizado.
     *         <p>if there is no file message of the corresponding language.</p>
     * @throws IOException al obtener el <code>Writer</code> del <code>response</code> correspondiente.
     *         when getting the corresponding <code>response</code>'s <code>Writer</code>.
     */
    public void doExcel(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramsRequest)
            throws SWBResourceException, IOException {
        
        request.setAttribute("excelFile", "yes");
        doView(request, response, paramsRequest);
    }
}
