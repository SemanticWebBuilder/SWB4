<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            Blog blog = (Blog) request.getAttribute("blog");
            if(blog!=null)
                {
            Member member = Member.getMember(user, wpage);
%>
<%
            String defaultFormat = "dd 'de' MMMM 'del' yyyy";
            SimpleDateFormat iso8601dateFormat = new SimpleDateFormat(defaultFormat);
            String updatedBlog = iso8601dateFormat.format(blog.getUpdated());
            %>
            <p><%=updatedBlog%></p>
            <h1><%=blog.getTitle()%></h1>
            <%
                //if(member.getAccessLevel()==member.LEVEL_OWNER)
                //    {
                    SWBResourceURL url=paramRequest.getRenderUrl();
                    url.setParameter("act", "edit");
                    url.setParameter("uri", blog.getURI());
                    url.setParameter("mode", "editblog");
                    %>
                    &nbsp;&nbsp;&nbsp;<a href="<%=url%>">Editar</a>
                    <%
                //}
            %>

            <h2><%=blog.getDescription()%></h2>
            <%
            Iterator<PostElement> posts = blog.listPostElements();
            while (posts.hasNext())
            {
                PostElement post = posts.next();
                String postAuthor = post.getCreator().getFirstName();
                String updated = iso8601dateFormat.format(post.getUpdated());                
                %>

                <table>
                <tr>
                <td>
                    <img src="<%=user.getPhoto()%>">
                </td>
                <td>
                    <%=postAuthor%>&nbsp;&nbsp;&nbsp;<%=updated%>
                </td>
                </tr>

            </table>
                <%
                    if (post.canModify(member) && post.getCreator().getLogin().equals(user.getLogin()))
                    {
                        SWBResourceURL sWBResourceURL=paramRequest.getRenderUrl();
                        sWBResourceURL.setParameter("act", "edit");
                        sWBResourceURL.setParameter("uri",post.getURI());
                        sWBResourceURL.setParameter("mode","editpost");
                        %>
                        &nbsp;&nbsp;&nbsp;<a href="<%=sWBResourceURL%>">Editar</a>
                        &nbsp;&nbsp;&nbsp;<a href="<%=paramRequest.getRenderUrl().setParameter("act", "remove").setParameter("uri",post.getURI())%>">Borrar</a>
                        <%
                    }
                %>
                </p><hr>
                <h3><%=post.getTitle()%></h3>
                <%
            }
%>

<%
    //if(member.canAdd())
    //{
        %>
        <center>
            <a href="<%=paramRequest.getRenderUrl().setParameter("act","add")%>">Agregar Entrada</a>
        </center>
        <%
    //}
            }
    %>
