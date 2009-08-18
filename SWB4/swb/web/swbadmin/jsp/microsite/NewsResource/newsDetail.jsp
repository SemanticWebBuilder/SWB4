<%@page contentType="text/html"%>
<%@page import="java.text.SimpleDateFormat, org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            Resource base = paramRequest.getResourceBase();
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            Member member = Member.getMember(user, wpage);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

            String uri = request.getParameter("uri");
            String path = SWBPlatform.getWebWorkPath() + base.getWorkPath() + "/";
            NewsElement rec = (NewsElement) SemanticObject.createSemanticObject(uri).createGenericInstance();
            if (rec != null) {
                rec.incViews();                             //Incrementar apariciones
%>
<div class="news">    
    <div class="newstitle">
        <h2><%=rec.getTitle()%> (<%=rec.getCitation()%>)</h2>
    </div>
    <div class="newsbody">
        <p><%=SWBUtils.TEXT.getTimeAgo(rec.getCreated(), user.getLanguage())%></p>
        <p>Por&nbsp;<%=rec.getAuthor()%></p>
        <div><img src="<%= path+rec.getNewsPicture() %>" alt="<%= rec.getTitle() %>"/><%=rec.getFullText()%></div>
        <hr>
        <p><%=rec.getViews()%> vistas</p>
    </div>
    <%
            }
            rec.renderGenericElements(request, out, paramRequest);
    %>
    <div class="menuoptions">
        <center>
            <a href="<%=paramRequest.getRenderUrl()%>">Regresar</a>
            <%if (rec.canModify(member)) {%><a href="<%=paramRequest.getRenderUrl().setParameter("act", "edit").setParameter("uri", rec.getURI())%>">Editar Información</a><%}%>
            <%if (rec.canModify(member)) {%><a href="<%=paramRequest.getActionUrl().setParameter("act", "remove").setParameter("uri", rec.getURI())%>">Eliminar Noticia</a><%}%>
        </center>
    </div>
</div>