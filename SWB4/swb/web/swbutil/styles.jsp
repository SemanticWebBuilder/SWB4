<%@page import="org.semanticwb.resource.office.sem.WordResource"%>
<%@page import="org.semanticwb.resource.office.sem.OfficeResource"%>
<%@page import="org.semanticwb.repository.office.OfficeContent"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.servlet.SWBHttpServletResponseWrapper"%>
<%@page import="org.semanticwb.portal.api.SWBResource"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.*"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.User"%>

<%
    int iresources=0;
    if(request.getParameter("site")!=null)
    {
	    String id=request.getParameter("site");
	    WebSite site=WebSite.ClassMgr.getWebSite(id);
	    
	    if(site!=null)
	    {
		Iterator<Resource> resources=Resource.ClassMgr.listResources(site);
		while(resources.hasNext())
		{
		    Resource resource=resources.next();
                    if(resource.getResourceType()!=null && resource.getResourceType().getResourceClassName()!=null)
                    {
                        if(resource.getResourceType().getResourceClassName().equals(WordResource.class.getCanonicalName()))
                        {
                            SWBResource go=SWBPortal.getResourceMgr().getResource(resource);
                            if(go!=null && go instanceof WordResource)
                            {
                                WordResource wr=(WordResource)go;
                                wr.setDeletestyles(true);
                                iresources++;
                            }
                            
                        }
                    }
		}
	    }
    }
%>
Recursos modificados <%=iresources%>