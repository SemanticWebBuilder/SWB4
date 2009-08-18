<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%@page import="org.semanticwb.model.Resource" %>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            Resource base = paramRequest.getResourceBase();
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            MicroSiteWebPageUtil wputil = MicroSiteWebPageUtil.getMicroSiteWebPageUtil(wpage);
            Member member = Member.getMember(user, wpage);
%>

<table border="0" width="90%">
    <tr>
    <td>
        <div id="photoc_<%= base.getId()%>">
<%
        Iterator<PhotoElement> it = PhotoElement.listPhotoElementByPhotoWebPage(wpage, wpage.getWebSite());
        int i = 0;
        while (it.hasNext())
        {
            PhotoElement photo = it.next();
            if(photo.canView(member))
            {
                SWBResourceURL viewurl = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("uri", photo.getURI());
%>
            <div style="float:left; margin-top:30px; margin-left:30px; margin-right:30px; text-align:center">
                <a href="<%=viewurl%>">
                    <img id="img_<%=i+base.getId()%>" src="<%=photo.getPhotoThumbnail()%>" alt="<%= photo.getDescription()%>" />
                    <p style="line-height:2px;"><%= photo.getTitle()%></p>
                    <p style="line-height:2px;"><%= photo.getCreator().getFirstName()%></p>
                    <p style="line-height:2px;"><%= photo.getDescription()%></p>
                    <p style="line-height:2px;"><%= SWBUtils.TEXT.getTimeAgo(photo.getCreated(),user.getLanguage())%></p>
                </a>
            </div><%
                i++;
            }
        }
%>                
        </div>
    </td>
    </tr>
</table>
<%
    if (member.canAdd()) {
%>
<center>
    <a href="<%=paramRequest.getRenderUrl().setParameter("act", "add").toString()%>">Agregar foto</a>
    <%
        if (wputil != null && member.canView()) {
            if(!wputil.isSubscribed(member)) {
    %>
    <a href="<%=paramRequest.getActionUrl().setParameter("act", "subscribe").toString()%>">Suscribirse a este elemento</a>
    <%
            }else {
    %>
    <a href="<%=paramRequest.getActionUrl().setParameter("act", "unsubscribe").toString()%>">Cancelar suscripción</a>
    <%
            }
        }
    %>
</center>
<%  }else {%>
<p>no puedes ver el menu</p>
<%  }%>