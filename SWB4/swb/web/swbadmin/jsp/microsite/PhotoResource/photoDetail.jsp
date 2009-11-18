<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.lib.*,java.text.*,org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<script type="text/javascript" charset="utf-8">
    dojo.require("dojox.image.Lightbox");
    //dojo.require("dojo.parser");
</script>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    User user = paramRequest.getUser();
    WebPage wpage = paramRequest.getWebPage();
    Member member = Member.getMember(user, wpage);

    String lang = user.getLanguage();

    String uri = request.getParameter("uri");
    PhotoElement photo = (PhotoElement) SemanticObject.createSemanticObject(uri).createGenericInstance();
    java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd-MMM-yyyy");

    DecimalFormat df = new DecimalFormat("#0.0#");
    String rank = df.format(photo.getRank());
    if (photo != null && photo.canView(member))
    {
        photo.incViews();  //Incrementar apariciones
%>
<div class="columnaIzquierda">
    <p><a dojoType="dojox.image.Lightbox" title="<%= photo.getTitle()%>" href="<%= SWBPortal.getWebWorkPath() + photo.getImageURL()%>">
            <img id="img_<%=photo.getId()%>" src="<%= SWBPortal.getWebWorkPath() + photo.getImageURL()%>" alt="<%= photo.getTitle()%>" border="0" width="300" height="100%" />
        </a></p>        
    <script type="text/javascript">
        var img = document.getElementById('img_<%=photo.getId()%>');
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
</div>
<%
    }

    SWBResponse res = new SWBResponse(response);
    photo.renderGenericElements(request, res, paramRequest);
    out.write(res.toString());
%>

<div class="columnaCentro">
    <h2 class="blogTitle"><%=photo.getTitle()%></h2><br>
    <p><%= photo.getDescription()%></p>
    <p>Autor: <%= photo.getCreator().getFullName()%></p>
    <p>Creado el: <%=dateFormat.format(photo.getCreated())%></p>
    <p><%= photo.getViews()%> vistas</p>
    <p>Calificación: <%=rank%></p>
    <p><a href="<%=paramRequest.getRenderUrl()%>">[Ver todas las fotos]</a></p>
    <%if (photo.canModify(member))
            {%>
    <p><a href="<%=paramRequest.getRenderUrl().setParameter("act", "edit").setParameter("uri", photo.getURI())%>">[Editar información]</a></p>
    <%}%>
    <%if (photo.canModify(member))
            {%>
    <p><a href="<%=paramRequest.getActionUrl().setParameter("act", "remove").setParameter("uri", photo.getURI())%>">[Eliminar]</a></p>
    <%}%>
    <ul class="miContenido">
<%
    SWBResourceURL urla = paramRequest.getActionUrl();
    if (user.isRegistered())
    {
        if (member == null)
        {
            urla.setParameter("act", "subscribe");
%>
        <li><a href="<%=urla%>">Suscribirse a esta comunidad</a></li>
<%
        }
        else
        {
            urla.setParameter("act", "unsubscribe");
%>
        <li><a href="<%=urla%>">Cancelar suscripción a comunidad</a></li>
<%
        }
    }
    String pageUri = "/swbadmin/jsp/microsite/rss/rss.jsp?photo=" + java.net.URLEncoder.encode(wpage.getURI());
%>
        <li><a class="rss" href="<%=pageUri%>">Suscribirse via RSS al canal de fotos de la comunidad</a></li>
    </ul>
</div>