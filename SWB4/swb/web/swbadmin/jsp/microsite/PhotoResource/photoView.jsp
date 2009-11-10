<%@page contentType="text/html"%>
<%@page import="org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%@page import="org.semanticwb.model.Resource" %>
<%@page import="java.text.*,org.semanticwb.portal.SWBFormMgr"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            Resource base = paramRequest.getResourceBase();
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            MicroSiteWebPageUtil wputil = MicroSiteWebPageUtil.getMicroSiteWebPageUtil(wpage);
            Member member = Member.getMember(user, wpage);
            String urlAddPhoto = paramRequest.getRenderUrl().setParameter("act", "add").toString();
%>
<script type="text/javascript">
    dojo.require("dojox.image.Lightbox");
    dojo.require("dojo.parser");
</script>

<div class="columnaIzquierda">
    <div class="adminTools">
        <a class="adminTool" href="<%=urlAddPhoto%>">Agregar foto</a>
        
    </div>
    <%
            Iterator<PhotoElement> it = PhotoElement.listPhotoElementByPhotoWebPage(wpage, wpage.getWebSite());
            it = SWBComparator.sortByCreated(it, false);
            int i = 0;
            while (it.hasNext())
            {
                PhotoElement photo = it.next();
                if (photo.canView(member))
                {
                    String postAuthor = photo.getCreator().getFullName();
                    SWBResourceURL urlDetail = paramRequest.getRenderUrl();
                    urlDetail.setParameter("act", "detail");
                    urlDetail.setParameter("uri", photo.getURI());                    
                    SWBResourceURL viewurl = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("uri", photo.getURI());
                    java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0#");
                    String rank = df.format(photo.getRank());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                    String editEventURL = paramRequest.getRenderUrl().setParameter("act", "edit").setParameter("uri", photo.getURI()).toString();

    %>
    <div class="noticia">        
        <a dojoType="dojox.image.Lightbox" title="<%= photo.getTitle()%>" href="<%= SWBPortal.getWebWorkPath() + photo.getImageURL()%>">
            <img id="img_<%=i + base.getId()%>" src="<%= SWBPortal.getWebWorkPath() + photo.getPhotoThumbnail()%>" alt="<%= photo.getTitle()%>" border="0" width="140" height="140" />
            </a>
        <div class="noticiaTexto">
            <h2><%=photo.getTitle()%></h2>
            <p>&nbsp;<br>Por:<%=postAuthor%><br><%=dateFormat.format(photo.getCreated())%> - <%=SWBUtils.TEXT.getTimeAgo(photo.getCreated(), user.getLanguage())%></p>
            <p>
                <%=photo.getDescription()%> | <a href="<%=viewurl%>">Ver más</a>
                <%
                    if(photo.canModify(member))
                        {
                        %>
                        | <a href="<%=editEventURL%>">Editar</a> | <a href="<%=viewurl%>">Eliminar</a>
                        <%
                        }
                %>
            </p>
            <p class="stats">
            	Puntuación: <%=rank%><br>
                <%=photo.getViews()%> vistas
            </p>
        </div>
    </div>
    <%
                }
            }
    %>

    <!--div id="paginacion">
        <a href="#"><img src="images/pageArrowLeft.gif" alt="anterior"></a> <a href="#">1</a><a href="#">2</a><a href="#">3</a><a href="#">4</a><a href="#">5</a> <a href="#"><img src="images/pageArrowRight.gif" alt="siguiente"></a>
    </div-->
</div>
<div class="columnaCentro">
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
            String pageUri="/swbadmin/jsp/microsite/rss/rss.jsp?photo="+java.net.URLEncoder.encode(wpage.getURI());
        %>
        <li><a class="rss" href="<%=pageUri%>">Suscribirse via RSS al canal de fotos de la comunidad</a></li>
        </ul>
</div>
