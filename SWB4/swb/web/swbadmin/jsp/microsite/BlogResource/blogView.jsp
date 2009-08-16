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
            String defaultFormat = "dd 'de' MMMM  'del' yyyy";
            SimpleDateFormat iso8601dateFormat = new SimpleDateFormat(defaultFormat);
            String created = iso8601dateFormat.format(blog.getCreated()); //SWBUtils.TEXT.(blog.getCreated(), user.getLanguage());//iso8601dateFormat.format(blog.getCreated());
            
            
            %>
            <table width="100%" cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td>
                        <h1><%=blog.getTitle()%></h1>
                    </td>
                    <td>
                        <%
                if(member.getAccessLevel()==member.LEVEL_OWNER)
                {
                    SWBResourceURL url=paramRequest.getRenderUrl();
                    url.setParameter("act", "edit");
                    url.setParameter("uri", blog.getURI());
                    url.setParameter("mode", "editblog");
                    %>
                    &nbsp;&nbsp;&nbsp;<a href="<%=url%>">Editar</a>
                    <%
                }
            %>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <%=created%>
                    </td>
                </tr>
            </table>
                    <hr>
            
            
                  <table width="100%" cellpadding="2" cellspacing="2" border="0">
            <%
            Iterator<PostElement> posts = blog.listPostElements();
            while (posts.hasNext())
            {
                PostElement post = posts.next();
                String postAuthor = post.getCreator().getFirstName();
                String updated = SWBUtils.TEXT.getTimeAgo(post.getUpdated(), user.getLanguage());//iso8601dateFormat.format(post.getUpdated());
                SWBResourceURL url=paramRequest.getRenderUrl();
                url.setParameter("act", "detail");
                url.setParameter("uri", post.getURI());


                %>

                
                    <tr>
                    <td colspan="3">
                        <a href="<%=url%>"><%=post.getTitle()%></a>
                    </td>
                    <td>
                     <%
                    if (post.canModify(member))
                    {
                        SWBResourceURL sWBResourceURL=paramRequest.getRenderUrl();
                        sWBResourceURL.setParameter("act", "edit");
                        sWBResourceURL.setParameter("uri",post.getURI());
                        sWBResourceURL.setParameter("mode","editpost");
                        %>
                        &nbsp;&nbsp;&nbsp;<a href="<%=sWBResourceURL%>">Editar</a>
                        &nbsp;&nbsp;&nbsp;<a href="<%=paramRequest.getActionUrl().setParameter("act", "remove").setParameter("uri",post.getURI())%>">Borrar</a>
                        <%
                    }
                %>
                </td>
                </tr>
                <tr>                   
                <td>
                    <%if(user.getPhoto()!=null)
                    {
                        %>
                    <img src="<%=user.getPhoto()%>">
                      <%
                   }
                %>
                </td>
              
                <td>
                    Escrito por: <%=postAuthor%>, <%=updated%>
                </td>
            
                
                
                <%
            }
%>
</table>                                            
<%
    if(member.canAdd())
    {
        %>
        <center>
            <a href="<%=paramRequest.getRenderUrl().setParameter("act","add")%>">Agregar Entrada</a>
        </center>
        <%
    }
            }
    %>
