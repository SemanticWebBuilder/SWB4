<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.GenericIterator"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.portal.resources.sem.forum.UserFavThread"%>
<%@page import="org.semanticwb.portal.resources.sem.forum.SWBForum"%>
<%@page import="org.semanticwb.portal.resources.sem.forum.Thread"%>
<%@page import="org.semanticwb.portal.resources.sem.forum.Post"%>
<%@page import="org.semanticwb.portal.resources.sem.forum.Attachment"%>
<%@page import="org.semanticwb.portal.resources.sem.forum.ThreadTypeCat"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.model.SWBModel"%>

<link href="/swb/swbadmin/css/forum1.css" rel="stylesheet" type="text/css" />


<table>
    <tr><td>
            <%
        WebPage webpage = paramRequest.getTopic();
        WebSite website = webpage.getWebSite();
        SWBResourceURL url = paramRequest.getRenderUrl();
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        User user = paramRequest.getUser();
        String lang = user.getLanguage();
        String action = (String) request.getAttribute("action");

        if (action != null && action.equals("viewPost")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
            Thread thread = Thread.getThread(semObject.getId(), website);
            thread.setViewCount(thread.getViewCount() + 1);
            url.setParameter("threadUri", thread.getURI());
            url.setParameter("forumUri", request.getParameter("forumUri"));
            %>

            <table class="forumline" width="95%" cellspacing="3" cellpadding="3" align="center">
                <tr>
                    <th class="thtop" colspan="2"><font color="#FFFFFF">Autor</font></td>
                    <th class="thtop" colspan="4" align="center"><font color="#FFFFFF">This topic has been viewed <%=thread.getViewCount()%> times and has <%=thread.getReplyCount()%> replies</font></td>
                </tr>
                <tr>
                    <td colspan="2" class="catleft">
                        <%if (thread.getCreator() != null) {%>
                        <%=thread.getCreator().getName()%>
                        <%}%>
                    </td>
                    <td colspan="4" class="catleft">
                        <%=thread.getTitle()%>
                        <br/>
                        <hr width="100%">
                        <br/>
                        <%=thread.getBody()%>
                        <br/>
                        <hr width="100%">
                        <br/>
                        <%=thread.getCreated()%>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" class="catleft">
                        &nbsp;
                    </td>
                    <td colspan="4" class="catleft">
                        <%url.setMode("replyPost");%>
                        <a href="<%=url.toString()%>">replicar</a>/
                        <%url.setMode("editThread");
                url.setParameter("postUri", thread.getURI());
                        %>
                        <a href="<%=url.toString()%>">Editar</a>/
                        <%url.setMode(url.Mode_VIEW);
                url.setAction("removePost");
                        %>
                        <a href="<%=url.toString()%>&isthread=1">Eliminar Tema</a>
                    </td>
                </tr>
                <%
                GenericIterator<Post> itPost = thread.listPosts();
                while (itPost.hasNext()) {
                    Post post = itPost.next();
                    url.setParameter("postUri", post.getURI());
                %>
                <tr>
                    <td colspan="2" class="catleft">
                        <%if (post.getCreator() != null) {%>
                        <%=post.getCreator().getName()%>
                        <%}%>
                    </td>
                    <td colspan="4" class="catleft">
                        <%=thread.getTitle()%>
                        <br/>
                        <hr width="100%">
                        <br/>
                        <%=post.getBody()%>
                        <br/>
                        <hr width="100%">
                        <br/>
                        <%=post.getCreated()%>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" class="catleft">
                        &nbsp;
                    </td>
                    <td colspan="4" class="catleft">
                        <%url.setMode("replyPost");%>
                        <a href="<%=url.toString()%>">replicar</a>/
                        <%url.setMode("editPost");%>
                        <a href="<%=url.toString()%>">Editar</a>/
                        <%url.setMode(url.Mode_VIEW);
                    url.setAction("removePost");%>
                        <a href="<%=url.toString()%>">Eliminar Mensaje</a>
                    </td>
                </tr>
                <%
                }
                %>
            </table>

            <%} else if (action != null && action.equals("removePost")) {
                if (request.getParameter("isthread") != null) {
                    SemanticObject soThread = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
                    Thread thread = Thread.getThread(soThread.getId(), website);
                    actionURL.setAction("removeThread");
            %>
            <table class="forumline" width="100%">
                <tr>
                    <th colspan="2" class="thtop">Tema:<%=thread.getTitle()%></th>
                </tr>
                <tr>
                    <td  class="catleft">Mensaje</td>
                    <td  class="catleft"><%=thread.getBody()%></td>
                </tr>
                <tr>
                    <td class="catleft">Autor</td>
                    <td class="catleft">
                        <%if (thread.getCreator() != null) {%>
                        <%=thread.getCreator().getName()%>
                        <%}%>
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td class="catleft">Número de Mensajes</td>
                    <%
                int postSize = 0;
                GenericIterator<Post> itPost = thread.listPosts();
                while (itPost.hasNext()) {
                    itPost.next();
                    postSize++;
                }
                    %>
                    <td class="catleft">
                        <%=postSize%>
                    </td>
                </tr>
                <form name="removeThread" action="<%=actionURL.toString()%>">
                    <input type="hidden" name="threadUri" value="<%=thread.getURI()%>">
                    <input type="hidden" name="forumUri" value="<%=request.getParameter("forumUri")%>">
                    <tr><td><input type="submit" value="eliminar"></td>
                        <td><input type="button" value="cancelar" onClick="retorna(this.form);"></td>
                    </tr>
                </form>
            </table>
            <%} else {
                actionURL.setAction("removePost");
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                Post post = Post.getPost(semObject.getId(), paramRequest.getTopic().getWebSite());
            %>
            <table>
                <tr>
                    <td colspan="2">Usted esta a punto de eliminar el siguiente Mensaje:<%=post.getThread().getTitle(lang)%></td>
                </tr>
                <tr>
                    <td>Cuerpo</td>
                    <td><%=post.getBody()%></td>
                </tr>
                <tr>
                    <td>Autor</td>
                    <td>
                        <%if (post.getCreator() != null) {%>
                        <%=post.getCreator().getName()%>
                        <%}%>
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td>Número de Respuestas</td>
                    <%
                int postSize = 0;
                Iterator<Post> itPost = post.listchildPosts();
                while (itPost.hasNext()) {
                    itPost.next();
                    postSize++;
                }
                    %>
                    <td><%=postSize%></td>
                </tr>
                <tr>
                    <td>Número de Archivos adjuntos</td>
                    <%
                int attchmentsSize = 0;
                GenericIterator<Attachment> itattach = post.listAttachmentss();
                while (itattach.hasNext()) {
                    itattach.next();
                    attchmentsSize++;
                }
                    %>
                    <td>
                        <%=attchmentsSize%>
                    </td>
                </tr>
                <form name="removePost" action="<%=actionURL.toString()%>">
                    <input type="hidden" name="postUri" value="<%=post.getURI()%>">
                    <input type="hidden" name="threadUri" value="<%=request.getParameter("threadUri")%>">
                    <tr><td><input type="submit" value="eliminar"></td>
                        <td><input type="button" value="cancelar" onClick="retorna(this.form);"></td>
                    </tr>
                </form>
            </table>
            <%
                }
                url.setAction("viewPost");
            %>
            <script type="text/javascript">
                function retorna(forma){
                    forma.action="<%=url.toString()%>";
                    forma.submit();
                }
            </script>
            <%} else {
                url.setMode("addThread");
            %>
            <form action="<%=url.toString()%>">
            <table class="forumline" border="1" width="95%" cellspacing="0" cellpadding="3" align="center">
                <tr>
                    <td>
                        <input type="button" class="mainoption" name="newThread" value="Nuevo Tema" onClick="this.form.submit();"/>
                    </td>
                </tr>
                <tr bgcolor="#666669">
                    <th class="thcornerl" nowrap="nowrap" colspan="2" height="25" align="center" valign="middle">Tema</th>
                    <th class="thtop" nowrap="nowrap" width="50">Autor</th>
                    <th class="thtop" nowrap="nowrap" width="50">Replicas</th>
                    <th class="thtop" nowrap="nowrap" width="50">Vistas</th>
                    <th class="thtop" nowrap="nowrap" width="50">Ultimo mensaje</th>
                    <th class="thtop" nowrap="nowrap" width="50">Favorito</th>
                </tr>
                <%
                System.out.println("entra J");
                url.setMode(url.Mode_VIEW);
                url.setAction("viewPost");
                System.out.println("entra J0");
                boolean flag = false;
                GenericIterator<WebPage> itThreads = webpage.listChilds();
                while (itThreads.hasNext()) {
                    WebPage wp = itThreads.next();
                    Thread thread = (Thread) wp.getSemanticObject().createGenericInstance();
                    url.setParameter("threadUri", thread.getURI());
                %>
                <tr font-size="12px" font-weight="normal" ine-height="20px">
                    <td colspan="2" class="catleft"><a href="<%=url.toString()%>"><b><%=thread.getTitle()%></b></a><br/><%=thread.getBody()%>
                    <br/></td>
                    <td align="center" class="catleft">
                        <%if (thread.getCreator() != null) {%>
                        <%=thread.getCreator().getName()%>
                        <%} else {%>&nbsp;<%}%>
                    </td>
                    <td align="center" class="catleft">
                        <%=thread.getReplyCount()%>
                    </td>
                    <td align="center" class="catleft">
                        <%=thread.getViewCount()%>
                    </td>
                    <td align="center" class="catleft">
                        <%if (thread.getLastPostDate()!= null) {%>
                        <%=thread.getLastPostDate()%><br/> by
                        <%}%>
                        <%if (thread.getLastPostMember() != null) {%>
                        <%=thread.getLastPostMember().getName()%>
                        <%} else {%>&nbsp;<%}%>
                    </td>
                    <td class="catleft">
                        <%
                        System.out.println(user.isRegistered() + "," + user.isSigned() + ",uri:" + user.getURI());
                        System.out.println(user.getLogin());
                        if (user.isSigned()) {
                            boolean isFavThread = false;
                            Iterator<UserFavThread> itFrmUserThread = UserFavThread.listUserFavThreads();
                            while (itFrmUserThread.hasNext()) {
                                UserFavThread usrThread = itFrmUserThread.next();
                                if (usrThread.getThread().getURI().equals(thread.getURI()) && usrThread.getUser().getURI().equals(user.getURI())) {
                                    isFavThread = true;
                                    break;
                                }
                            }
                            if (!isFavThread) {
                                actionURL.setAction("addFavoriteThread");
                                actionURL.setParameter("threadUri", thread.getURI());
                        %>
                        <a href="<%=actionURL.toString()%>">Favorito</a>
                        <%
                            }
                        }
                        %>
                    </td>
                </tr>
                <%
                }
                System.out.println("entra J3");
                %>
            </table>
            </form>
            <%
            }
            %>
    </td></tr>
</table>
