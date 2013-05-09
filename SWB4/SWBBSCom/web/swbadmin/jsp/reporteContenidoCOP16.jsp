<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.Logger"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.w3c.dom.*"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/html4/loose.dtd">

<%
    final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    Document  dom = doFillReport(request, paramRequest);
%>
    <table class="content-report-cop16">
        <caption>Reporte de contenido</caption>
        <tr>
            <td>Sección</td>
            <td>ID</td>
            <td>Tipo</td>
            <td>Activo</td>
            <td>Localización</td>
            <td>Fecha de creación</td>
            <td>Fecha de modificación</td>
            <td>Idioma</td>
            <td>Autor</td>
        </tr>

<%
    NodeList items = dom.getElementsByTagName("item");
    System.out.println("length="+items.getLength());
    for(int i=0; i<items.getLength(); i++) {
        out.println("<tr>");
        Element e = (Element)items.item(i);        
        out.println("<td>"+e.getAttribute("section")+"</td>");
        out.println("<td>"+e.getAttribute("id")+"</td>");
        out.println("<td>"+e.getAttribute("type")+"</td>");
        out.println("<td>"+e.getAttribute("title")+"</td>");
        out.println("<td>"+e.getAttribute("priority")+"</td>");
        out.println("<td>"+e.getAttribute("status")+"</td>");
        out.println("<td>"+e.getAttribute("locale")+"</td>");
        String dl = e.getAttribute("created");
        Date d = new Date(dl);
        out.println("<td>"+dl+"</td>");
        dl = e.getAttribute("updated");
        //d = new Date(dl);
        out.println("<td>"+dl+"</td>");
        out.println("<td>"+e.getAttribute("language")+"</td>");
        out.println("<td>"+e.getAttribute("creator")+"</td>");
        out.println("</tr>");
    }
%>
    </table>


<%!
final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

%>



<%!
    private Document doFillReport(HttpServletRequest request, SWBParamRequest paramRequest) {
        Document  dom = SWBUtils.XML.getNewDocument();
        Element report = dom.createElement("ContentReport");
        dom.appendChild(report);

        String lang = paramRequest.getUser().getLanguage();
        WebSite webSite = paramRequest.getWebPage().getWebSite();
        WebPage home = webSite.getHomePage();
        
        Iterator<Resource> portlets = home.listResources();
        while(portlets.hasNext()) {
            Resource portlet = portlets.next();
            if(portlet.getResourceType().getResourceMode()==1) {
                Element item = dom.createElement("item");
                item.appendChild(dom.createTextNode(""));
                report.appendChild(item);

                item.setAttribute("section", home.getDisplayTitle(lang));
                /*Element section = dom.createElement("section");
                section.appendChild(dom.createTextNode(home.getDisplayTitle(lang)));
                item.appendChild(section);*/

                item.setAttribute("id", portlet.getId());
                /*Element id = dom.createElement("id");
                id.appendChild(dom.createTextNode(portlet.getId()));
                item.appendChild(id);*/

                item.setAttribute("type", portlet.getResourceType().getDisplayTitle(lang));
                /*Element type = dom.createElement("type");
                type.appendChild(dom.createTextNode(portlet.getResourceType().getDisplayTitle(lang)));
                item.appendChild(type);*/

                item.setAttribute("priority", Integer.toString(portlet.getPriority()));
                /*Element priority = dom.createElement("priority");
                priority.appendChild(dom.createTextNode(Integer.toString(portlet.getPriority())));
                item.appendChild(priority);*/

                item.setAttribute("status", Boolean.toString(portlet.isActive()));
                /*Element status = dom.createElement("status");
                status.appendChild(dom.createTextNode(Boolean.toString(portlet.isActive())));
                item.appendChild(status);*/

                item.setAttribute("locale", portlet.getWorkPath());
                /*Element locale = dom.createElement("locale");
                locale.appendChild(dom.createTextNode(portlet.getWorkPath()));
                item.appendChild(locale);*/

                item.setAttribute("created", sdf.format(portlet.getCreated()));
                /*Element created = dom.createElement("created");
                created.appendChild(dom.createTextNode(Long.toString(portlet.getCreated().getTime())));
                item.appendChild(created);*/

                item.setAttribute("updated", sdf.format(portlet.getUpdated()));
                /*Element updated = dom.createElement("updated");
                updated.appendChild(dom.createTextNode(Long.toString(portlet.getUpdated().getTime())));
                item.appendChild(updated);*/

                /*Element language = dom.createElement("language");*/
                String language;
                if(portlet.getLanguage()!=null)
                    language = portlet.getLanguage().toString();
                else
                    language = "-";
                /*item.appendChild(language);*/
                item.setAttribute("language", language);

                item.setAttribute("creator", portlet.getCreator().getFullName());
                /*Element creator = dom.createElement("creator");
                creator.appendChild(dom.createTextNode(portlet.getCreator().getFullName()));
                item.appendChild(creator);*/
            }
        }
        doDataStore(home, dom, report,  lang);
        return dom;
    }
%>
<%!
    private void doDataStore(WebPage node, Document dom, Element report, final String lang) {
        Iterator<WebPage> childs = node.listVisibleChilds(lang);

        while(childs.hasNext()) {
            WebPage webPage = childs.next();
            Iterator<Resource> portlets = webPage.listResources();
            while(portlets.hasNext()) {
                Resource portlet = portlets.next();
                if(portlet.getResourceType().getResourceMode()==1) {
                    Element item = dom.createElement("item");
                    item.appendChild(dom.createTextNode(""));
                    report.appendChild(item);

                    item.setAttribute("section", webPage.getDisplayTitle(lang));
                    /*Element section = dom.createElement("section");
                    section.appendChild(dom.createTextNode(home.getDisplayTitle(lang)));
                    item.appendChild(section);*/

                    item.setAttribute("id", portlet.getId());
                    /*Element id = dom.createElement("id");
                    id.appendChild(dom.createTextNode(portlet.getId()));
                    item.appendChild(id);*/

                    item.setAttribute("type", portlet.getResourceType().getDisplayTitle(lang));
                    /*Element type = dom.createElement("type");
                    type.appendChild(dom.createTextNode(portlet.getResourceType().getDisplayTitle(lang)));
                    item.appendChild(type);*/

                    item.setAttribute("priority", Integer.toString(portlet.getPriority()));
                    /*Element priority = dom.createElement("priority");
                    priority.appendChild(dom.createTextNode(Integer.toString(portlet.getPriority())));
                    item.appendChild(priority);*/

                    item.setAttribute("status", Boolean.toString(portlet.isActive()));
                    /*Element status = dom.createElement("status");
                    status.appendChild(dom.createTextNode(Boolean.toString(portlet.isActive())));
                    item.appendChild(status);*/

                    item.setAttribute("locale", portlet.getWorkPath());
                    /*Element locale = dom.createElement("locale");
                    locale.appendChild(dom.createTextNode(portlet.getWorkPath()));
                    item.appendChild(locale);*/

                    item.setAttribute("created", sdf.format(portlet.getCreated()));
                    /*Element created = dom.createElement("created");
                    created.appendChild(dom.createTextNode(Long.toString(portlet.getCreated().getTime())));
                    item.appendChild(created);*/

                    item.setAttribute("updated", sdf.format(portlet.getUpdated()));
                    /*Element updated = dom.createElement("updated");
                    updated.appendChild(dom.createTextNode(Long.toString(portlet.getUpdated().getTime())));
                    item.appendChild(updated);*/

                    /*Element language = dom.createElement("language");*/
                    String language;
                    if(portlet.getLanguage()!=null)
                        language = portlet.getLanguage().toString();
                    else
                        language = "-";
                    /*item.appendChild(language);*/
                    item.setAttribute("language", language);

                    item.setAttribute("creator", portlet.getCreator().getFullName());
                    /*Element creator = dom.createElement("creator");
                    creator.appendChild(dom.createTextNode(portlet.getCreator().getFullName()));
                    item.appendChild(creator);*/
                }
            }
            if(webPage.listChilds().hasNext()) {
                doDataStore(webPage, dom, report, lang);
            }
        }
    }
%>

