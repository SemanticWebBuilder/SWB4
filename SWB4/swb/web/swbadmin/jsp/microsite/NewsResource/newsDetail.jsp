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
    NewsElement anew = (NewsElement) SemanticObject.createSemanticObject(uri).createGenericInstance();
%>
<br />
<div id="panorama">    
    <%if (anew.canModify(member)) {%>
    <div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl().setParameter("act", "edit").setParameter("uri", anew.getURI())%>">Editar Información</a></p></div>
    <%}%>
    <%if (anew.canModify(member)) {%>
    <div class="editarInfo"><p><a href="<%=paramRequest.getActionUrl().setParameter("act", "remove").setParameter("uri", anew.getURI())%>">Eliminar Noticia</a></p></div>
    <%}%>
    <div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl()%>">Regresar</a></p></div>
</div>
<div class="entryNews">
<%  if(anew!=null && anew.canView(member)) {
        anew.incViews();
%>
    <a href="<%= SWBPlatform.getWebWorkPath()+anew.getNewsImage()%>" target="_self">
        <img id="img_<%=anew.getId()%>" src="<%= SWBPlatform.getWebWorkPath()+anew.getNewsImage() %>" alt="<%= anew.getTitle() %>" border="0" />
    </a>
    <h3><%=anew.getTitle()%></h3>
    <%=anew.getCitation()%>
    <strong><%=dateFormat.format(anew.getCreated())%>.</strong>
    <%=anew.getFullText()%>
    <br />
<%  }
    anew.renderGenericElements(request, out, paramRequest);
%>
</div>
<script type="text/javascript">
    var img = document.getElementById('img_<%=anew.getId()%>');
    if( img.width>img.height && img.width>350) {
        img.width = 350;
        img.height = 270;
    }else {
        if(img.height>270) {
            img.width = 270;
            img.height = 350;
        }
    }
</script>