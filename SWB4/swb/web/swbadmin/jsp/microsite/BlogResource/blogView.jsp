<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.*,org.semanticwb.platform.*,java.text.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%!    private static final int ELEMENETS_BY_PAGE = 5;
%>
<script language="Javascript" type="text/javascript">
    function validateremove(url, title)
    {
        if(confirm('¿Esta seguro de borrar la entrada: '+title+'?'))
        {            
            window.location.href=url;
        }
    }
</script>
<%
            java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0#");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            User user = paramRequest.getUser();
            String lang = "es";
            if (user.getLanguage() != null)
            {
                lang = user.getLanguage();
            }
            WebPage wpage = paramRequest.getWebPage();
            MicroSiteWebPageUtil wputil = MicroSiteWebPageUtil.getMicroSiteWebPageUtil(wpage);
            Blog blog = (Blog) request.getAttribute("blog");
            String defaultFormat = "d 'de' MMMM  'del' yyyy 'a las' HH:mm";
            SimpleDateFormat iso8601dateFormat = new SimpleDateFormat(defaultFormat, new Locale(lang));
            String createdBlog = iso8601dateFormat.format(blog.getCreated());
            String updatedBlog = iso8601dateFormat.format(blog.getUpdated());
            Member member = Member.getMember(user, wpage);
            SWBResourceURL urlAdd = paramRequest.getRenderUrl();
            urlAdd.setParameter("act", "add");
            String titleBlog = blog.getTitle();
            String descriptionBlog = blog.getDescription();
            SWBResourceURL urleditar = paramRequest.getRenderUrl();
            urleditar.setParameter("act", "edit");
            urleditar.setParameter("mode", "editblog");
            boolean editarblog = false;
            if (member.getAccessLevel() == member.LEVEL_OWNER)
            {
                editarblog = true;
            }
            boolean canadd = false;
            canadd = member.canAdd();
            String cssPath = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/css/images/";
%>
<div class="columnaIzquierda">
    <%
            ArrayList<PostElement> elements = new ArrayList();
            int elementos = 0;
            Iterator<PostElement> posts = blog.listPostElements();
            posts = SWBComparator.sortByCreated(posts, false);
            while (posts.hasNext())
            {
                PostElement post = posts.next();
                if (post.canView(member))
                {
                    elements.add(post);
                    elementos++;
                }
            }
            int paginas = elementos / ELEMENETS_BY_PAGE;
            if (elementos % ELEMENETS_BY_PAGE != 0)
            {
                paginas++;
            }
            int inicio = 0;
            int fin = ELEMENETS_BY_PAGE;
            int ipage = 1;
            if (request.getParameter("ipage") != null)
            {
                try
                {
                    ipage = Integer.parseInt(request.getParameter("ipage"));
                    inicio = (ipage * ELEMENETS_BY_PAGE) - ELEMENETS_BY_PAGE;
                    fin = (ipage * ELEMENETS_BY_PAGE);
                }
                catch (NumberFormatException nfe)
                {
                    ipage = 1;
                }
            }
            if (ipage < 1 || ipage > paginas)
            {
                ipage = 1;
            }
            if (inicio < 0)
            {
                inicio = 0;
            }
            if (fin < 0)
            {
                fin = ELEMENETS_BY_PAGE;
            }
            if (fin > elementos)
            {
                fin = elementos;
            }
            if (inicio > fin)
            {
                inicio = 0;
                fin = ELEMENETS_BY_PAGE;
            }
            if (fin - inicio > ELEMENETS_BY_PAGE)
            {
                inicio = 0;
                fin = ELEMENETS_BY_PAGE;
            }
            inicio++;

    %>
    <%
            if (paginas > 1)
            {
    %>    
    <div id="paginacion">


        <%
                String nextURL = "#";
                String previusURL = "#";
                if (ipage < paginas)
                {
                    nextURL = paramRequest.getWebPage().getUrl() + "?ipage=" + (ipage + 1);
                }
                if (ipage > 1)
                {
                    previusURL = paramRequest.getWebPage().getUrl() + "?ipage=" + (ipage - 1);
                }
                if (ipage > 1)
                {
        %>
        <a href="<%=previusURL%>"><img src="<%=cssPath%>pageArrowLeft.gif" alt="anterior"></a>
            <%
                }
                for (int i = 1; i <= paginas; i++)
                {

            %>
        <a href="<%=wpage.getUrl()%>?ipage=<%=i%>">
            <%
                if(i==ipage)
                    {
                    %>
                    <strong>
                    <%
                    }
            %>
            <%=i%>
            <%
            if(i==ipage)
                    {
                    %>
                    </strong>
                    <%
                    }
                %>
        </a>
        <%
                }
        %>
        <%
                if (ipage != paginas)
                {
        %>
        <a href="<%=nextURL%>"><img src="<%=cssPath%>pageArrowRight.gif" alt="siguiente"></a>
            <%
                }
            %>

    </div>
    <%
            }
    %>


    <div class="adminTools">
        <%
            if (canadd)
            {
        %>
        <a class="adminTool" href="<%=urlAdd%>">Agregar entrada</a>
        <%
            }
        %>

        <%
            if (editarblog)
            {
        %>
        <a class="adminTool" href="<%=urleditar%>">Administrar</a>
        <%
            }
        %>

    </div>

    <h2 class="hidden"><%=titleBlog%></h2>
    <%

            if (elements.size() == 0)
            {
    %>
    <p>No hay entradas para el blog</p>
    <%            }

            int iElement = 0;
            for (PostElement post : elements)
            {
                if (post.canView(member))
                {
                    iElement++;
                    if (iElement > fin)
                    {
                        break;
                    }
                    if (iElement >= inicio && iElement <= fin)
                    {
                        String description = post.getDescription();
                        String title = post.getTitle();
                        if (description == null)
                        {
                            description = "";
                        }

                        SWBResourceURL urlEditPost = paramRequest.getRenderUrl();
                        urlEditPost.setParameter("act", "edit");
                        urlEditPost.setParameter("uri", post.getURI());
                        urlEditPost.setParameter("mode", "editpost");

                        String postAuthor = post.getCreator().getFullName();
                        SWBResourceURL urlDetail = paramRequest.getRenderUrl();
                        urlDetail.setParameter("act", "detail");
                        urlDetail.setParameter("uri", post.getURI());
                        SWBResourceURL removeUrl = paramRequest.getActionUrl();
                        removeUrl.setParameter("act", "remove");
                        removeUrl.setParameter("uri", post.getEncodedURI());
                        String removeurl = "javascript:validateremove('" + removeUrl + "','" + post.getTitle() + "')";
                        boolean canEditPost = post.canModify(member);
                        String rank = df.format(post.getRank());
                        String visited = String.valueOf(post.getViews());
                        int comments = 0;
                        GenericIterator it = post.listComments();
                        while (it.hasNext())
                        {
                            it.next();
                            comments++;
                        }

    %>
    <div class="blogEntry">
        <h3 class="blogEntryTitle"><%=title%></h3>
        <%
                        if (canEditPost)
                        {
        %>
        <a class="editar" href="<%=removeurl%>">[eliminar]</a> <a class="editar" href="<%=urlEditPost%>">[editar]</a>
        <%
                        }
        %>

        <p><span class="itemTitle">Por:</span> <%=postAuthor%><br>
            <span class="itemTitle">Creada:</span> <%=dateFormat.format(post.getCreated())%> - <%=SWBUtils.TEXT.getTimeAgo(post.getCreated(), user.getLanguage())%><br>
            <span class="itemTitle">Modificada:</span> <%=dateFormat.format(post.getUpdated())%> - <%=SWBUtils.TEXT.getTimeAgo(post.getUpdated(), user.getLanguage())%><br>
            <span class="itemTitle">Calificación:</span> <%=rank%><br>
            <span class="itemTitle">Visitada:</span> <%=visited%> veces
        </p>
        <p><%=description%></p>
        <p><a href="<%=urlDetail%>">Leer entrada completa</a>&nbsp;<span class="notificaciones"><%=comments%> comentarios</span> </p>


    </div>
    <%
                    }

                }
            }
    %>
    <!-- paginacion -->
    <%
            if (paginas > 1)
            {
    %>
    <div id="paginacion">


        <%
                String nextURL = "#";
                String previusURL = "#";
                if (ipage < paginas)
                {
                    nextURL = paramRequest.getWebPage().getUrl() + "?ipage=" + (ipage + 1);
                }
                if (ipage > 1)
                {
                    previusURL = paramRequest.getWebPage().getUrl() + "?ipage=" + (ipage - 1);
                }
                if (ipage > 1)
                {
        %>
        <a href="<%=previusURL%>"><img src="<%=cssPath%>pageArrowLeft.gif" alt="anterior"></a>
            <%
                }
                for (int i = 1; i <= paginas; i++)
                {
            %>
        <a href="<%=wpage.getUrl()%>?ipage=<%=i%>"><%
                if(i==ipage)
                    {
                    %>
                    <strong>
                    <%
                    }
            %>
            <%=i%>
            <%
            if(i==ipage)
                    {
                    %>
                    </strong>
                    <%
                    }
                %></a>
        <%
                }
        %>
        <%
                        if (ipage != paginas)
                        {
        %>
        <a href="<%=nextURL%>"><img src="<%=cssPath%>pageArrowRight.gif" alt="siguiente"></a>
            <%
                }
            %>


    </div>
    <%
            }
    %>
    <!-- fin paginacion -->
</div>
<div class="columnaCentro">
    <%
            if (paginas > 1)
            {
    %>
    <br><br>
    <%            }
    %>
    <h2 class="blogTitle"><%=titleBlog%></h2>
    <p> <%=descriptionBlog%> </p>
    <p> Creado el: <%=createdBlog%> </p>
    <p> Actualizado el: <%=updatedBlog%> </p>

    <ul class="miContenido">

        <%
            SWBResourceURL urla = paramRequest.getActionUrl();
            if (member.canView())
            {
                if (!wputil.isSubscribed(member))
                {
                    urla.setParameter("act", "subscribe");
        %>
        <li><a href="<%=urla%>">Suscribirse a blog</a></li>
        <%
                }
                else
                {
                    urla.setParameter("act", "unsubscribe");
        %>
        <li><a href="<%=urla%>">Cancelar suscripción a blog</a></li>
        <%
                }
            }
            String pageUri = "/swbadmin/jsp/microsite/rss/rss.jsp?blog=" + java.net.URLEncoder.encode(blog.getURI());
        %>
        <li><a class="rss" href="<%=pageUri%>">Suscribirse via RSS al blog de la comunidad</a></li>
    </ul>
</div>


