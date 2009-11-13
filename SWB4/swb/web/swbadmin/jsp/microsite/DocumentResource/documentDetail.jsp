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
    ArrayList<DocumentElement> elements = new ArrayList();
    int elementos = 0;
    Iterator<DocumentElement> it = DocumentElement.ClassMgr.listDocumentElements();
    it = SWBComparator.sortByCreated(it, false);
    while (it.hasNext())
    {
        DocumentElement doc = it.next();
        if (doc.canView(member))
        {
            elements.add(doc);
            elementos++;
        }
    }

    
%>
    <div class="adminTools">
        <p>recurso documentos</p>
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
    for (DocumentElement doc : elements)
    {
                if (doc.canView(member))
                {
                    iElement++;
                    /*if (iElement >= inicio && iElement <= fin)
                    {*/
                        String postAuthor = doc.getCreator().getFullName();
                        SWBResourceURL urlDetail = paramRequest.getRenderUrl();
                        urlDetail.setParameter("act", "detail");
                        urlDetail.setParameter("uri", doc.getURI());
                        SWBResourceURL viewurl = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("uri", doc.getURI());
                        String rank = df.format(doc.getRank());
                        String editEventURL = paramRequest.getRenderUrl().setParameter("act", "edit").setParameter("uri", doc.getURI()).toString();
                        SWBResourceURL removeUrl = paramRequest.getActionUrl();
                        removeUrl.setParameter("act", "remove");
                        String removeurl = "javascript:validateremove('" + removeUrl + "','" + doc.getTitle() + "','" + doc.getURI() + "')";

    %>
    <div class="noticia">
        <a href="<%= SWBPortal.getWebWorkPath() + doc.getDocumentURL() %>"><%= doc.getTitle() %><br/><%= doc.getDescription() %></a>

        <div class="noticiaTexto">
            <h2><%=doc.getTitle()%></h2>
            <p>&nbsp;<br>Por: <%=postAuthor%><br><%=dateFormat.format(doc.getCreated())%> - <%=SWBUtils.TEXT.getTimeAgo(doc.getCreated(), user.getLanguage())%></p>
            <p>
                <%=doc.getDescription()%> | <a href="<%=viewurl%>">Ver más</a>
                <%
                        if (doc.canModify(member))
                        {
                %>
                | <a href="<%=editEventURL%>">Editar</a> | <a href="<%=removeurl%>">Eliminar</a>
                <%
                        }
                %>
            </p>
            <p class="stats">
            	Puntuación: <%=rank%><br>
                <%=doc.getViews()%> vistas
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
