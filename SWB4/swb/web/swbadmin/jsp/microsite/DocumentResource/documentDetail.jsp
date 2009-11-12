<%@page contentType="text/html"%>
<%@page import="org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%@page import="org.semanticwb.model.Resource" %>
<%@page import="java.text.*,org.semanticwb.portal.SWBFormMgr"%>

<%
    java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0#");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    String cssPath = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/css/images/";
    Resource base = paramRequest.getResourceBase();
    User user = paramRequest.getUser();
    WebPage wpage = paramRequest.getWebPage();
    MicroSiteWebPageUtil wputil = MicroSiteWebPageUtil.getMicroSiteWebPageUtil(wpage);
    Member member = Member.getMember(user, wpage);
    String urlAddPhoto = paramRequest.getRenderUrl().setParameter("act", "add").toString();
    ArrayList<PhotoElement> elements = new ArrayList();
    int elementos = 0;
    Iterator<PhotoElement> it = PhotoElement.ClassMgr.listPhotoElementByPhotoWebPage(wpage, wpage.getWebSite());
    it = SWBComparator.sortByCreated(it, false);
    while (it.hasNext())
    {
        PhotoElement event = it.next();
        if (event.canView(member))
        {
            elements.add(event);
            elementos++;
        }
    }
%>
    <div class="adminTools">
    <%
    if (member.canAdd())
        {
        %>
            <a class="adminTool" href="<%=urlAddPhoto%>">Agregar foto</a>
        <%
        }
    %>
    </div>

<%
    if (elements.size() == 0)
    {
%>
    <p>No hay fotos registradas en la comunidad</p>
<%
    }
    int iElement = 0;
    for (PhotoElement photo : elements)
    {
                if (photo.canView(member))
                {
                    iElement++;
                    /*if (iElement >= inicio && iElement <= fin)
                    {*/
                        String postAuthor = photo.getCreator().getFullName();
                        SWBResourceURL urlDetail = paramRequest.getRenderUrl();
                        urlDetail.setParameter("act", "detail");
                        urlDetail.setParameter("uri", photo.getURI());
                        SWBResourceURL viewurl = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("uri", photo.getURI());
                        String rank = df.format(photo.getRank());
                        String editEventURL = paramRequest.getRenderUrl().setParameter("act", "edit").setParameter("uri", photo.getURI()).toString();
                        SWBResourceURL removeUrl = paramRequest.getActionUrl();
                        removeUrl.setParameter("act", "remove");
                        String removeurl = "javascript:validateremove('" + removeUrl + "','" + photo.getTitle() + "','" + photo.getURI() + "')";

    %>
    <div class="noticia">
        <a dojoType="dojox.image.Lightbox" title="<%= photo.getTitle()%>" href="<%= SWBPortal.getWebWorkPath() + photo.getImageURL()%>">
            <img id="img_<%=iElement + base.getId()%>" src="<%= SWBPortal.getWebWorkPath() + photo.getPhotoThumbnail()%>" alt="<%= photo.getTitle()%>" border="0" width="140" height="140" />
        </a>
        <div class="noticiaTexto">
            <h2><%=photo.getTitle()%></h2>
            <p>&nbsp;<br>Por: <%=postAuthor%><br><%=dateFormat.format(photo.getCreated())%> - <%=SWBUtils.TEXT.getTimeAgo(photo.getCreated(), user.getLanguage())%></p>
            <p>
                <%=photo.getDescription()%> | <a href="<%=viewurl%>">Ver más</a>
                <%
                        if (photo.canModify(member))
                        {
                %>
                | <a href="<%=editEventURL%>">Editar</a> | <a href="<%=removeurl%>">Eliminar</a>
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
                    /*}*/
                }
            }
    %>



<div class="columnaCentro">
    <ul class="miContenido">
        <%
            SWBResourceURL urla = paramRequest.getActionUrl();
           if (member.canView())
            {
                if (!wputil.isSubscribed(member))
                {
                    urla.setParameter("act", "subscribe");
        %>
        <li><a href="<%=urla%>">Suscribirse a fotos</a></li>
        <%
                }
                else
                {
                    urla.setParameter("act", "unsubscribe");
        %>
        <li><a href="<%=urla%>">Cancelar suscripción a fotos</a></li>
        <%
                }
            }
            String pageUri = "/swbadmin/jsp/microsite/rss/rss.jsp?photo=" + java.net.URLEncoder.encode(wpage.getURI());
        %>
        <li><a class="rss" href="<%=pageUri%>">Suscribirse via RSS al canal de fotos de la comunidad</a></li>
    </ul>
</div>
