<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%
            int count = 0;
            WebSite site = ((WebPage) request.getAttribute("webpage")).getWebSite();
            User owner = (User) request.getAttribute("user");
            User user = owner;            
            if (request.getParameter("user") != null)
            {
                SemanticObject semObj = SemanticObject.createSemanticObject(request.getParameter("user"));
                user = (User) semObj.createGenericInstance();
            }
            Iterator<MicroSiteElement> elements = PostElement.listMicroSiteElementByCreator(user,site);
            while (elements.hasNext())
            {                
                if (elements.next() instanceof PostElement)
                {
                    count++;
                }
            }
%>
<li><a class="rss" href="#" >Mis artículos publicados (<%=count%>)</a></li>