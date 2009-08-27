<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.*,org.semanticwb.platform.*,java.text.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<script language="Javascript" type="text/javascript">
        function validateremove(url, title,uri)
        {
            if(confirm('¿Esta seguro de borrar la entrada '+title+'?'))
                {
                    var url=url+'&uri='+escape(uri);
                    document.URL=url;
                }
        }
    </script>
<div id="centerProfile">            
<%
        String pathIamge = SWBPlatform.getWebWorkPath();
        SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
        User user = paramRequest.getUser();
        WebPage wpage = paramRequest.getWebPage();
        Blog blog = (Blog) request.getAttribute("blog");
        if(blog!=null)
        {
            Member member = Member.getMember(user, wpage);
            String defaultFormat = "dd 'de' MMMM  'del' yyyy 'a las' HH:mm";
            SimpleDateFormat iso8601dateFormat = new SimpleDateFormat(defaultFormat);
            String created = iso8601dateFormat.format(blog.getCreated());
            String updated = iso8601dateFormat.format(blog.getUpdated());
            if(member.getAccessLevel()==member.LEVEL_OWNER)
            {
                SWBResourceURL urleditar=paramRequest.getRenderUrl();
                urleditar.setParameter("act", "edit");                
                urleditar.setParameter("mode", "editblog");
                %>
                <div class="editarInfo"><p><a href="<%=urleditar%>">Editar información</a></p></div>
                <%
            }
            if(member.canAdd())
            {
                SWBResourceURL urlAdd=paramRequest.getRenderUrl();
                urlAdd.setParameter("act","add");
                %>
                <div class="editarInfo"><p><a href="<%=urlAdd%>">Agregar Entrada</a></p></div>
                <%
            }
            %>
            <div class="clear">&nbsp;</div>
            <h2 class="tituloGrande"><%=blog.getTitle()%></h2>
            <p>Creado el: <%=created%></p>
            <p>Actualizado el: <%=updated%></p>
            <p><img src="images/solidLine.jpg" alt="" width="495" height="1" ></p>            
            <p><%=blog.getDescription()%></p>
            <p>&nbsp;</p>
            <div id="blog">
            <%
                ArrayList<PostElement> elements=new ArrayList();
                Iterator<PostElement> posts = SWBComparator.sortByCreated(blog.listPostElements(),false);
                int i=0;
                while (posts.hasNext())
                {
                    PostElement post=posts.next();                    
                    if(post.canView(member))
                    {
                        elements.add(post);
                        i++;
                        if(i==10) // sólo muestra hasta 10 últimas entradas
                        {
                            break;
                        }
                    }
                }
                for(PostElement post : elements)
                {
                    if(post.canView(member))
                    {
                        String description=post.getDescription();
                        if(description==null)
                        {
                            description="";
                        }
                        String postAuthor = post.getCreator().getFullName();
                        String createdPost = SWBUtils.TEXT.getTimeAgo(post.getCreated(), user.getLanguage());
                        SWBResourceURL urlDetail=paramRequest.getRenderUrl();
                        urlDetail.setParameter("act", "detail");
                        urlDetail.setParameter("uri", post.getURI());

                        String srcImageUsuario="";
                        if(post.getCreator().getPhoto()!=null)
                        {
                            srcImageUsuario=pathIamge+post.getCreator().getPhoto();
                        }
                        DecimalFormat df=new DecimalFormat("#0.0#");
                        String rank=df.format(post.getRank());
                        %>
                        <div class="entryBlog">
                        <p><img src="<%=srcImageUsuario%>" alt="<%=postAuthor%>" width="90" height="90" ></p>
                        <p class="tituloNaranja"><%=post.getTitle()%></p>
                        <p>Creado por: <%=postAuthor%> , <%=createdPost%></p>
                        <p>Visitas: <%=post.getViews()%>, Calificación: <%=rank%></p>
                        <p><%=description%></p>
                        <div class="vermasFloat"><p class="vermas"><a href="<%=urlDetail%>">Ver más</a></p></div>
                        <%
                        if (post.canModify(member))
                        {
                            SWBResourceURL sWBResourceURL=paramRequest.getRenderUrl();
                            sWBResourceURL.setParameter("act", "edit");
                            sWBResourceURL.setParameter("uri",post.getURI());
                            sWBResourceURL.setParameter("mode","editpost");

                            SWBResourceURL removeUrl=paramRequest.getActionUrl();
                            removeUrl.setParameter("act", "remove");
                            %>
                            <div class="editarInfo"><p><a href="<%=sWBResourceURL%>">Editar información</a></p></div>
                            <div class="editarInfo"><p><a href="javascript:validateremove('<%=removeUrl%>','<%=post.getTitle()%>','<%=post.getURI()%>')">Eliminar</a><p></div>
                            <%
                        }
                        %>
                        <div class="clear">&nbsp;</div>
                        </div> <!-- fin blogentry -->
                        <%
                    }
                }
                %>
                </div> <!-- fin blog div -->
            <%
        }
%>                
</div> <!-- fin  centerProfile -->
