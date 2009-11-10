<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.*,org.semanticwb.platform.*,java.text.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<script language="Javascript" type="text/javascript">
    function validateremove(url, title,uri)
    {
        if(confirm('¿Esta seguro de borrar la entrada '+title+'?'))
        {
            var url=url+'&uri='+escape(uri);
            window.location.href=url;
        }
    }
</script>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            User user = paramRequest.getUser();
            String lang = "es";
            if (user.getLanguage() != null)
            {
                lang = user.getLanguage();
            }
            WebPage wpage = paramRequest.getWebPage();
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
%>
<div class="columnaIzquierda">
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
            ArrayList<PostElement> elements = new ArrayList();
            Iterator<PostElement> posts = SWBComparator.sortByCreated(blog.listPostElements(), false);
            int i = 0;
            while (posts.hasNext())
            {
                PostElement post = posts.next();
                if (post.canView(member))
                {
                    elements.add(post);
                    i++;
                    if (i == 10) // sólo muestra hasta 10 últimas entradas
                    {
                        break;
                    }
                }
            }
            for (PostElement post : elements)
            {
                if (post.canView(member))
                {
                    String description = post.getDescription();
                    String title = post.getTitle();
                    if (description == null)
                    {
                        description = "";
                    }


                    SWBResourceURL removeUrl = paramRequest.getActionUrl();
                    removeUrl.setParameter("act", "remove");

                    SWBResourceURL urlEditPost = paramRequest.getRenderUrl();
                    urlEditPost.setParameter("act", "edit");
                    urlEditPost.setParameter("uri", post.getURI());
                    urlEditPost.setParameter("mode", "editpost");

                    String postAuthor = post.getCreator().getFullName();
                    String createdPost = SWBUtils.TEXT.getTimeAgo(post.getCreated(), user.getLanguage());
                    String updatedPost = SWBUtils.TEXT.getTimeAgo(post.getUpdated(), user.getLanguage());
                    SWBResourceURL urlDetail = paramRequest.getRenderUrl();
                    urlDetail.setParameter("act", "detail");
                    urlDetail.setParameter("uri", post.getURI());
                    String removeurl = "javascript:validateremove('" + removeUrl + "','" + post.getTitle() + "','" + post.getURI() + "')";
                    boolean canEditPost = post.canModify(member);
                    DecimalFormat df = new DecimalFormat("#0.0#");
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

        <p><span class="itemTitle">Autor:</span> <%=postAuthor%><br>
            <span class="itemTitle">Creada:</span> <%=createdPost%><br>
            <span class="itemTitle">Modificada:</span> <%=updatedPost%><br>
            <span class="itemTitle">Calificación:</span> <%=rank%><br>
            <span class="itemTitle">Visitada:</span> <%=visited%> veces
        </p>
        <p><%=description%></p>
        <p><a href="<%=urlDetail%>">Leer entrada completa</a>&nbsp;<span class="notificaciones"><%=comments%> comentarios</span> </p>
    </div>
    <%
                }
            }
    %>

</div>
<div class="columnaCentro">
    <h2 class="blogTitle"><%=titleBlog%></h2>
    <p> <%=descriptionBlog%> </p>
    <p> Creado el: <%=createdBlog%> </p>
    <p> Actualizado el: <%=updatedBlog%> </p>
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
            String pageUri="/swbadmin/jsp/microsite/rss/rss.jsp?blog="+java.net.URLEncoder.encode(blog.getURI());
        %>
        <li><a class="rss" href="<%=pageUri%>">Suscribirse via RSS al blog de la comunidad</a></li>
    </ul>
</div>


