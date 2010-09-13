<%@page import="org.semanticwb.office.comunication.OfficeDocument"%>
<%@page import="org.semanticwb.portal.resources.sem.HTMLContent"%>
<%@page contentType="text/html"%>
<%@page import="java.util.Date, java.util.Calendar, java.util.GregorianCalendar, java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    OfficeDocument document=new OfficeDocument();
    
        User user = SWBContext.getAdminUser();
        if(user!=null)
        {
            document.setUser(user.getLogin());
            document.setPassword(user.getLogin());
            String siteID=request.getParameter("site");
            if(siteID!=null || siteID.trim().equals(""))
            {
                WebSite site=WebSite.ClassMgr.getWebSite(siteID);
                if(site!=null)
                {
                    int total=0;
                    Iterator<Resource> resources=site.listResources();
                    while(resources.hasNext())
                    {
                        Resource resource=resources.next();                        
                        if(document.convertResource(resource,user))
                        {
                            total++;
                        }
                    }
                     %>
                        Contenidos migrados, total <%=total%> recursos
                <%
                }
                else
                    {
                    %>
                No se encontro el sitio <%=siteID%>
                <%
                    }
            }
            else
            {
                %>
                    No se indico el sitio
                <%
            }
        }
        else
            {
            %>
                    Usuario admin no existe, o no esta logueado a la administración
                <%
            }
    
%>
