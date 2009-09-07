<%@page import="org.semanticwb.portal.community.utilresources.CommunityActivity,java.text.*,java.net.*,org.semanticwb.platform.SemanticObject,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*" %>
<div id="contactos">
    <h2>Actividades</h2>
    <ul>
        <%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");

            int numrec = (Integer) request.getAttribute("numrec");
            WebPage wp = paramRequest.getWebPage();
            MicroSite ms = null;
            if (wp instanceof MicroSite)
            {
                ms = (MicroSite) wp;
            }
            Iterator<CommunityActivity> activities = (Iterator<CommunityActivity>) request.getAttribute("activities");



            CommunityActivity ca = null;
            User user = null;
            MicroSiteElement mse = null;
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

                    if (mse != null && user != null && ms != null)
                    {
        %>
        <li><a class="contactos_nombre" href="<%=mse.getURL()%>"><img src="/work/models/Ciudad_Digital/css/boton_contacto.png" alt=""></a>
            <%=user.getFullName()%> agregó <a class="contactos_nombre" href="<%=mse.getURL()%>"><%=mse.getDisplayTitle(user.getLanguage())%></a>
            <%=SWBUtils.TEXT.getTimeAgo(mse.getUpdated(), user.getLanguage())%>.</li>
            <%
                    }
                }
            }
            else
            {
                %>
                <li>No hay actividades.</li>
                <%
                
            }


            %>
    </ul>
</div>