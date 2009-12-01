<%@page import="java.text.*,java.net.*,org.semanticwb.platform.SemanticObject,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*" %>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            String defaultFormat = "dd/MM/yy HH:mm";
            SimpleDateFormat iso8601dateFormat = new SimpleDateFormat(defaultFormat);
            ArrayList<MicroSiteElement> elements = (ArrayList<MicroSiteElement>) request.getAttribute("elements");
            if (paramRequest.getCallMethod() == paramRequest.Call_CONTENT)
            {
%>
<jsp:include flush="true" page="LastMicrositeElementsContent.jsp"></jsp:include>
<%            }
            else
            {
                String path = paramRequest.getWebPage().getWebSite().getWebPage("Lo_ultimo").getUrl();
%>
    <h2>Actividad m&aacute;s reciente en el sitio</h2>
        <ul class="listaElementos">
            <%
            if(elements.size()==0)
                {
                %>
                <li>&nbsp;</li>
                <%
                }

    for (MicroSiteElement element : elements)
    {
        User user = paramRequest.getUser();
        boolean canview = false;
        if (element.getWebPage() != null && user != null)
        {
            Member member = Member.getMember(user, element.getWebPage());
            if (member != null)
            {
                canview = element.canView(member);                
            }
        }
        String created = "Sin fecha";
        if (element.getCreated() != null)
        {
            //created = iso8601dateFormat.format(element.getCreated());
            created=SWBUtils.TEXT.getTimeAgo(element.getCreated(),user.getLanguage());
        }
        String title = element.getTitle();
        String textcreated = "creó el elemento ";
        if (element instanceof NewsElement)
        {
            textcreated = "creó la noticia ";
        }
        if (element instanceof PostElement)
        {
            textcreated = "creó la entrada ";
        }
        if (element instanceof VideoElement)
        {
            textcreated = "subió el video ";
        }
        if (element instanceof PhotoElement)
        {
            textcreated = "subió la imagen ";
        }
        if (element instanceof EventElement)
        {
            textcreated = "registró el evento ";
        }


            %>
            <li><%=element.getCreator().getFullName()%>  <%=textcreated%>
                <%
                    if (canview)
                    {
                %>
                <a href="<%=element.getURL()%>">
                    <%=title%>
                </a>    
                <%
                    }
                    else
                    {
                %>
                <%=title%>
                <%
                    }
                %>

						(<%=created%>)</li>
                <%            }
                %>
        </ul>
        <p class="vermas"><a href="<%=path%>" >Ver m&aacute;s</a></p>
<%
            }
%>

