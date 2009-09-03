<%@page import="org.semanticwb.portal.community.utilresources.CommunityActivity,java.text.*,java.net.*,org.semanticwb.platform.SemanticObject,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*" %>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            User user = paramRequest.getUser();
            if(request.getParameter("user")!=null)
            {
                return;
            }
            int numrec = (Integer) request.getAttribute("numrec");
            Iterator<CommunityActivity> activities = (Iterator<CommunityActivity>) request.getAttribute("activities");
%>
<div id="contactos">
    <h2>Mis Actividades</h2>
    <ul>
        <%

            CommunityActivity ca = null;
            MicroSiteElement mse = null;
            MicroSite ms = null;
            if (activities.hasNext())
            {
                int num = 0;
                while (activities.hasNext())
                {
                    num++;
                    if (num > numrec)
                    {
                        break;
                    }
                    ca = activities.next();
                    user = ca.getUser();
                    mse = ca.getElement();
                    ms = ca.getCommunity();
                    if (mse != null && user != null && ms != null)
                    {
        %>
        <li><a class="contactos_nombre" href="<%=mse.getURL()%>"><img src="/work/models/Ciudad_Digital/css/boton_contacto.png" alt=""></a>
            Usted agregó <a class="contactos_nombre" href="<%=mse.getURL()%>"><%=mse.getDisplayTitle(user.getLanguage())%></a>
            <%=SWBUtils.TEXT.getTimeAgo(mse.getUpdated(), user.getLanguage())%>.</li>
            <%

                        //out.println("("+mse.getSemanticObject().getSemanticClass().getDisplayName(user.getLanguage())+")</a>");

                    }
                }
            }
            else
            {
            %>
        <li>No hay actividades que reportar.</li>
        <%            }
        %>
    </ul>
</div>
<%

%>