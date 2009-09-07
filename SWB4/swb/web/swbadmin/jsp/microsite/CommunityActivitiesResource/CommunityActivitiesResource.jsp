<%@page import="org.semanticwb.portal.community.utilresources.CommunityActivity,java.text.*,java.net.*,org.semanticwb.platform.SemanticObject,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*" %>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    int numrec = (Integer) request.getAttribute("numrec");
    WebPage wp = paramRequest.getWebPage();
    MicroSite ms=null;
    if(wp instanceof MicroSite)
    {
            ms = (MicroSite)wp;
    }
    Iterator<CommunityActivity> activities = (Iterator<CommunityActivity>) request.getAttribute("activities");
    out.println("<div id=\"contactos\">");
            out.println("<h2>Actividades</h2>");

            out.println("<ul>");
            CommunityActivity ca = null;
            User user = null;
            MicroSiteElement mse = null;
            if(activities.hasNext())
            {
                int num = 0;
                while(activities.hasNext())
                {
                    num++;
                    if (num > numrec) {
                        break;
                    }

                    ca = activities.next();
                    user = ca.getUser();
                    mse = ca.getElement();

                    if(mse!=null&&user!=null&&ms!=null)
                    {
                        out.println("<li><a href=\""+mse.getURL()+"\">" + mse.getDisplayTitle(user.getLanguage()) );
                        out.println("("+mse.getSemanticObject().getSemanticClass().getDisplayName(user.getLanguage())+")</a>, ");
                        out.println("" + user.getFullName() + ", ");
                        out.println("<a class=\"contactos_nombre\" href=\"#\">"+SWBUtils.TEXT.getTimeAgo(mse.getUpdated(),user.getLanguage()) + "</a></li>");
                    }
                }
            }
            else
                out.println("<li>No hay actividades.</li>");
            out.println("</ul>");
            out.println("</div>");
%>