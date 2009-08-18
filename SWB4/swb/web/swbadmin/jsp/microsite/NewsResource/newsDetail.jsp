<%@page contentType="text/html"%>
<%@page import="java.text.SimpleDateFormat, org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            Resource base = paramRequest.getResourceBase();
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            Member member = Member.getMember(user, wpage);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

            String path = SWBPlatform.getWebWorkPath() + base.getWorkPath() + "/";

            String uri = request.getParameter("uri");            
            NewsElement anew = (NewsElement) SemanticObject.createSemanticObject(uri).createGenericInstance();

            if(anew!=null && anew.canView(member)) {
                anew.incViews();                             //Incrementar apariciones
%>
<div class="news">    
    <div class="newstitle">
        <h2><%=anew.getTitle()%></h2>&nbsp;(<%=anew.getCitation()%>)
    </div>
    <div class="newsbody">
        <p><%=SWBUtils.TEXT.getTimeAgo(anew.getCreated(), user.getLanguage())%></p>
        <p>Por:&nbsp;<%=anew.getAuthor()%></p>
        <div>
            <img id="img_<%=anew.getId()%>" src="<%= path+anew.getNewsPicture() %>" alt="<%= anew.getTitle() %>"/>
            <strong><%=dateFormat.format(anew.getCreated())%>.</strong>
            <%=anew.getFullText()%>
        </div>
        <hr>
        <p><%=anew.getViews()%> vistas</p>
    </div>
    <%
            }
            anew.renderGenericElements(request, out, paramRequest);
    %>
    <div class="menuoptions">
        <center>
            <a href="<%=paramRequest.getRenderUrl()%>">Regresar</a>
            <%if (anew.canModify(member)) {%><a href="<%=paramRequest.getRenderUrl().setParameter("act", "edit").setParameter("uri", anew.getURI())%>">Editar Información</a><%}%>
            <%if (anew.canModify(member)) {%><a href="<%=paramRequest.getActionUrl().setParameter("act", "remove").setParameter("uri", anew.getURI())%>">Eliminar Noticia</a><%}%>
        </center>
    </div>
</div>
<script type="text/javascript">
    var img = document.getElementById('img_<%=anew.getId()%>');
    if( img.width>img.height && img.width>450) {
        img.width = 450;
        img.height = 370;
    }else {
        if(img.height>370) {
            img.width = 370;
            img.height = 450;
        }
    }
</script>