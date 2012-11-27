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
package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author juan.fernandez
 */
public class SWBAAdminSearch extends GenericAdmResource {

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        PrintWriter out = response.getWriter();

        User usr = paramRequest.getUser();
        String action = request.getParameter("act");
        String wsid = request.getParameter("wsid");
        if (null == action) {
            action = "";
        }


        // usedas = true; para buscar recurso o contenido
        // usedas = false; para buscar Página Webo Sección

        String usedas = getResourceBase().getAttribute("usedas", "true");
        boolean isResSearch = true;
        try {
            isResSearch = Boolean.parseBoolean(usedas);
        } catch (Exception e) {
            isResSearch = true;
        }

        SWBResourceURL urlsearch = paramRequest.getRenderUrl();
        urlsearch.setParameter("act", "search");

        out.println("<div class=\"swbform\">");
        out.println("<form id=\"" + getResourceBase().getId() + "/SearchModels\" action=\"" + urlsearch + "\" method=\"post\" onsubmit=\"submitForm('" + getResourceBase().getId() + "/SearchModels'); return false;\">");
        out.println("<fieldset>");

        out.println("<legend>Búsqueda de " + (isResSearch ? "Recurso o Contenido" : "Página Web o Sección") + "</legend>");

        out.println("<ul style=\"list-style-type:none\">");
        out.println("<li>");
        out.println("<label for=\"wsid" + getResourceBase().getId() + "\">");
        out.println("Selecciona sitio: ");
        out.println("</label>");
        out.println("<select name=\"wsid\" id=\"wsid" + getResourceBase().getId() + "\">");
        String selectws = "";

        Iterator<WebSite> itws = WebSite.ClassMgr.listWebSites();
        while (itws.hasNext()) {
            WebSite webSite = itws.next();
            if (webSite.isValid()) {
                selectws = "";
                if (wsid != null && wsid.equals(webSite.getId())) {
                    selectws = "selected";
                }
                out.println("<option value=\"" + webSite.getId() + "\" " + selectws + " >");
                out.println(webSite.getId());
                out.println("</option>");
            }
        }
        Iterator<SemanticClass> itsc = WebSite.sclass.listSubClasses();
        while (itsc.hasNext()) {
            SemanticClass semanticClass = itsc.next();
            Iterator<SemanticObject> itso = semanticClass.listInstances();
            while (itso.hasNext()) {
                SemanticObject semanticObject = itso.next();
                if (semanticObject.createGenericInstance() instanceof WebSite) {
                    WebSite webSite = (WebSite) semanticObject.createGenericInstance();
                    if (webSite.isValid()&&!webSite.getId().equals(SWBContext.WEBSITE_ADMIN)&&!webSite.getId().equals(SWBContext.WEBSITE_GLOBAL)&&!webSite.getId().equals(SWBContext.WEBSITE_ONTEDITOR)) {
                        selectws = "";
                        if (wsid != null && wsid.equals(webSite.getId())) {
                            selectws = "selected";
                        }
                        out.println("<option value=\"" + webSite.getId() + "\" " + selectws + " >");
                        out.println(webSite.getId());
                        out.println("</option>");
                    }
                }
            }

        }
        out.println("</select>");
        out.println("</li>");

        String searchtype = request.getParameter("searchtype");
        if (null == searchtype) {
            searchtype = "id";
        }

        String searctxt = request.getParameter("searctxt");
        if (null == searctxt) {
            searctxt = "";
        }

        out.println("<li><input type=\"radio\" id=\"searctype1\" name=\"searchtype\" value=\"id\" " + (searchtype.equals("id") ? "checked" : "") + "><label for=\"searctype1\">Por identificador</label>");
        out.println("</li>");
        out.println("<li><input type=\"radio\" id=\"searctype2\" name=\"searchtype\" value=\"title\" " + (searchtype.equals("title") ? "checked" : "") + "><label for=\"searctype2\">Por título</label>");
        out.println("</li>");

        out.println("<li><label for=\"textbox1\">Texto a buscar:</label><input type=\"text\" id=\"textbox1\" name=\"searctxt\" value=\"" + (searctxt) + "\" >");
        out.println("</li>");


        out.println("<ul>");

        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println("<button type=\"submit\">Buscar</button>");
        out.println("</fieldset>");
        out.println("</form>");



        if (action.equals("search")) {

            HashMap hmresult = new HashMap();

            out.println("<fieldset>");
            out.println("<legend>Resultado de la búsqueda de " + (isResSearch ? "Recurso o Contenido" : "Página Web o Sección") + "</legend>");
            if (wsid != null) {
                // considerar paginación y ordenarlos por creación
                WebSite ws = WebSite.ClassMgr.getWebSite(wsid);
                //System.out.println("ws:"+ws.getNameSpace());
                if (isResSearch) {
                    //System.out.println("Recurso...");
                    Resource resource = null;
                    resource = ws.getResource(searctxt);
                    if (searchtype.equals("id") && resource != null) {
                        hmresult.put(resource, resource);
                    } else {
                        Iterator<Resource> itres = ws.listResources();
                        while (itres.hasNext()) {
                            resource = itres.next();
                            //System.out.println("res:"+resource.getDisplayTitle(usr.getLanguage()));
                            if (!searchtype.equals("id") && resource.getDisplayTitle(usr.getLanguage())!=null&& resource.getDisplayTitle(usr.getLanguage()).indexOf(searctxt) > -1) {
                                hmresult.put(resource, resource);
                            }
                        }
                    }
                } else {

                    //System.out.println("Sección...");
                    WebPage webPage = null;
                    webPage = ws.getWebPage(searctxt);
                    if (searchtype.equals("id") && webPage != null) {
                        hmresult.put(webPage, webPage);
                    } else {
                        Iterator<WebPage> itwp = ws.listWebPages();
                        while (itwp.hasNext()) {
                            webPage = itwp.next();
                            //System.out.println("wp:"+webPage.getDisplayTitle(usr.getLanguage()));
                            if (!searchtype.equals("id") && webPage.getDisplayTitle(usr.getLanguage())!=null&&webPage.getDisplayTitle(usr.getLanguage()).indexOf(searctxt) > -1) {
                                hmresult.put(webPage, webPage);
                            }
                        }
                    }
                }
                if (hmresult.size() > 0) {
                    out.println("<table width=\"100%\">");
                    out.println("<tr>");
                    out.println("<th>");
                    out.println("Id");
                    out.println("</th>");
                    out.println("<th>");
                    out.println("Título");
                    out.println("</th>");
                    out.println("<th>");
                    out.println("Creado");
                    out.println("</th>");
                    out.println("<th>");
                    out.println("Actualizado");
                    out.println("</th>");
                    out.println("</tr>");

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy hh:mm", new Locale(usr.getLanguage()));

                    Iterator itresult = hmresult.values().iterator();
                    while (itresult.hasNext()) {
                        out.println("<tr>");
                        Object object = itresult.next();
                        if (object instanceof Resource) {
                            Resource res = (Resource) object;
                            out.println("<td>");
                            out.println(res.getId());
                            out.println("</td>");
                            out.println("<td>");
                            out.println("<a href=\"#\"  onclick=\"addNewTab('" + res.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + res.getDisplayTitle(usr.getLanguage()) + "');return false;\" >" + res.getDisplayTitle(usr.getLanguage()) + "</a>");
                            out.println("</td>");
                            out.println("<td>");
                            out.println(res.getCreated() != null ? sdf.format(res.getCreated()) : "--");
                            out.println("</td>");
                            out.println("<td>");
                            out.println(res.getUpdated() != null ? sdf.format(res.getUpdated()) : "--");
                            out.println("</td>");

                        } else if (object instanceof WebPage) {
                            WebPage wpres = (WebPage) object;
                            out.println("<td>");
                            out.println(wpres.getId());
                            out.println("</td>");
                            out.println("<td>");
                            out.println("<a href=\"#\"  onclick=\"addNewTab('" + wpres.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + wpres.getDisplayTitle(usr.getLanguage()) + "');return false;\" >" + wpres.getDisplayTitle(usr.getLanguage()) + "</a>");
                            out.println("</td>");
                            out.println("<td>");
                            out.println(wpres.getCreated() != null ? sdf.format(wpres.getCreated()) : "--");
                            out.println("</td>");
                            out.println("<td>");
                            out.println(wpres.getUpdated() != null ? sdf.format(wpres.getUpdated()) : "--");
                            out.println("</td>");
                        }
                        out.println("</tr>");
                    }
                    out.println("<table>");

                } else {
                    out.println("<h2>No se encontró ningún registro con la información proporcionada.</h2>");
                }
            } else {
                out.println("WebSite inválido. Selecciona otro de la lista.");
            }
            out.println("</fieldset>");
        }
        out.println("</div>");
    }
}
