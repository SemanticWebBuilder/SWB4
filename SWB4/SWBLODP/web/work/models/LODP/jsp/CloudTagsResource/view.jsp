<%-- 
    Document   : view
    Created on : 23/05/2013, 09:40:31 AM
    Author     : Sabino
--%>

<%@page import="com.infotec.lodp.swb.resources.CloudTagsResource"%>
<%@page import="com.infotec.lodp.swb.Dataset"%>
<%@page import="com.infotec.lodp.swb.Tag"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.Resource"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" />
<%
    WebPage wpage = paramRequest.getWebPage();
    WebSite wsite = wpage.getWebSite();
    Iterator<Tag> listTags= Tag.ClassMgr.listTags(wsite); 
    Iterator<Dataset> listDataset= Dataset.ClassMgr.listDatasets(wsite);
    Resource base = paramRequest.getResourceBase();
    String strNumItems = base.getAttribute("numds");
    int maxTags = 10;
    try {
        maxTags = Integer.parseInt(strNumItems);
    } catch (Exception e) {
        maxTags = 10;
    }
    if(listTags.hasNext() && listDataset.hasNext()){
        Iterator<Tag> cloudTags = CloudTagsResource.getCloudTags(listTags, listDataset, maxTags);       
%>
    <ul>
       <%while(cloudTags.hasNext()){
           Tag tag = cloudTags.next();
           String urlData = wsite.getWebPage("Datos").getUrl();
           String urlTag = urlData+"?search="+tag.getTagName();
       %>
       <li class="<%=tag.getProperty("classCSS")%>">
           <a href="<%=urlTag%>" rel="<%=tag.getProperty("weight")%>">
               <%=tag.getTagName()%>
           </a>
       </li>       
       <%          
       }
    }else{
        %>No hay informacion disponible <%
    }  
       %>
    </ul>