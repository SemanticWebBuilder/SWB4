<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.GenericIterator"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.forum.FrmUserThread"%>
<%@page import="org.semanticwb.forum.FrmForum"%>
<%@page import="org.semanticwb.forum.FrmThread"%>
<%@page import="org.semanticwb.forum.FrmPost"%>
<%@page import="org.semanticwb.forum.FrmAttachments"%>
<%@page import="org.semanticwb.forum.FrmThreadTypeCat"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.model.SWBModel"%>


<table>
    <tr><td>
            <%
        WebSite website = paramRequest.getTopic().getWebSite();
        WebPage webpage = paramRequest.getTopic();
        SWBResourceURL url = paramRequest.getRenderUrl();
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        User user = paramRequest.getUser();
        String lang = user.getLanguage();
        String action = (String) request.getAttribute("action");
        if (action != null && action.equals("removePost")) {
            if (request.getParameter("isthread") != null) {
                SemanticObject soThread = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
                FrmThread thread = FrmThread.getFrmThread(soThread.getId(), paramRequest.getTopic().getWebSite());
                actionURL.setAction("removeThread");
            %>
            <table>
                <tr>
                    <td colspan="2">Usted esta a punto de eliminar el siguiente tema:<%=thread.getTitle()%></td>
                </tr>
                <tr>
                    <td>Cuerpo</td>
                    <td><%=thread.getBody()%></td>
                </tr>
                <tr>
                    <td>Autor</td>
                    <td>
                        <%if (thread.getCreator() != null) {%>
                        <%=thread.getCreator().getName()%>
                        <%}%>
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td>Número de Mensajes</td>
                    <%
                    int postSize = 0;
                    GenericIterator<FrmPost> itPost = thread.listPosts();
                    while (itPost.hasNext()) {
                        itPost.next();
                        postSize++;
                    }
                    %>
                    <td>
                        <%=postSize%>
                    </td>
                </tr>
                <form name="removeThread" action="<%=actionURL.toString()%>">
                    <input type="hidden" name="threadUri" value="<%=thread.getURI()%>">
                    <tr><td><input type="submit" value="eliminar"></td>
                        <td><input type="button" value="cancelar" onClick="retorna(this.form);"></td>
                    </tr>
                </form>
            </table>
            <%} else {
                    actionURL.setAction("removePost");
                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                    FrmPost post = FrmPost.getFrmPost(semObject.getId(), paramRequest.getTopic().getWebSite());
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
                    Iterator<FrmPost> itPost = post.listchildPosts();
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
                    GenericIterator<FrmAttachments> itattach = post.listAttachmentss();
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
            <%} else if (action != null && action.equals("viewPost")) {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
                FrmThread thread = FrmThread.getFrmThread(semObject.getId(), website);
                thread.setViewcount(thread.getViewcount() + 1);
                url.setParameter("threadUri", thread.getURI());
                System.out.println("threadUri:"+thread.getURI());
            %>

            <table border="1" width="95%" cellspacing="0" cellpadding="3" align="center">
                <tr bgcolor="#666669">
                    <td colspan="2"><font color="#FFFFFF">Autor</font></td>
                    <td colspan="4" align="center"><font color="#FFFFFF">This topic has been viewed <%=thread.getViewcount()%> times and has <%=thread.getReplyCount()%> replies</font></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <%if (thread.getUser() != null) {%>
                        <%=thread.getUser().getName()%>
                        <%}%>
                    </td>
                    <td colspan="4">
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
                    <td colspan="2">
                        &nbsp;
                    </td>
                    <td colspan="4">
                        <%url.setMode("replyPost");%>
                        <a href="<%=url.toString()%>">replicar</a>/
                        <%url.setMode("editThread");
                        url.setParameter("postUri", thread.getURI());
                        %>
                        <a href="<%=url.toString()%>&isthread=1">Editar</a>/
                        <%url.setMode(url.Mode_VIEW);
                         url.setAction("removePost");
                        %>
                        <a href="<%=url.toString()%>&isthread=1">Eliminar Tema</a>
                    </td>
                </tr>
                <%
                GenericIterator<FrmPost> itPost = thread.listPosts();
                while (itPost.hasNext()) {
                    FrmPost post = itPost.next();
                    url.setParameter("postUri", post.getURI());
                %>
                <tr>
                    <td colspan="2">
                        <%if (post.getCreator() != null) {%>
                        <%=post.getCreator().getName()%>
                        <%}%>
                    </td>
                    <td colspan="4">
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
                    <td colspan="2">
                        &nbsp;
                    </td>
                    <td colspan="4">
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
            <%} else {
                url.setMode("addThread");
            %>
            <a href="<%=url.toString()%>">Nuevo tema</a>
            <table border="1" width="95%" cellspacing="0" cellpadding="3" align="center">
                <tr bgcolor="#666669">
                    <td colspan="2"><font color="#FFFFFF">Tema</font></td>
                    <td align="center"><font color="#FFFFFF">Autor</font></td>
                    <td align="center"><font color="#FFFFFF">Replicas</font></td>
                    <td align="center"><font color="#FFFFFF">Vistas</font></td>
                    <td align="center"><font color="#FFFFFF">Ultimo mensaje</font></td>
                    <td align="center"><font color="#FFFFFF">Favorito</font></td>
                </tr>
                <%
                url.setMode(url.Mode_VIEW);
                url.setAction("viewPost");
                Iterator<FrmThreadTypeCat> itThreadTypes = FrmThreadTypeCat.listFrmThreadTypeCats();
                while (itThreadTypes.hasNext()) {
                    FrmThreadTypeCat threadType = itThreadTypes.next();
                    boolean flag = false;
                    GenericIterator<WebPage> itThreads = webpage.listChilds();
                    while (itThreads.hasNext()) {
                        WebPage wp = itThreads.next();
                        FrmThread thread = FrmThread.getFrmThread(wp.getSemanticObject().getId(), website);
                        if (thread.getType() != null && thread.getType().getURI().equals(threadType.getURI())) {
                            url.setParameter("threadUri", thread.getURI());
                            if (!flag) {
                                flag = true;
                %>
                <tr>
                    <td colspan="6">
                        <%=threadType.getTitle(lang)%>
                    </td>
                </tr>
                <%}%>
                <tr bgcolor="#C0C0C0" font-size="12px" font-weight="normal" ine-height="20px">
                    <td colspan="2"><a href="<%=url.toString()%>"><b><%=thread.getTitle()%></b></a><br/><%=thread.getBody()%>
                    <br/></td>
                    <td align="center">
                        <%if (thread.getCreator() != null) {%>
                        <%=thread.getCreator().getName()%>
                        <%} else {%>&nbsp;<%}%>
                    </td>
                    <td align="center">
                        <%=thread.getReplyCount()%>
                    </td>
                    <td align="center">
                        <%=thread.getViewcount()%>
                    </td>
                    <td align="center">
                        <%
                            if (thread.getLastpostdate() != null) {%>
                        <%=thread.getLastpostdate()%><br/> by
                        <%}%>
                        <%
                            if (thread.getLastpostmember() != null) {%>
                        <%=thread.getLastpostmember().getName()%>
                        <%} else {%>&nbsp;<%}
                        %>
                    </td>
                    <td>
                        <%
                            if (user != null && user.isSigned()) {
                                boolean isFavThread = false;
                                Iterator<FrmUserThread> itFrmUserThread = FrmUserThread.listFrmUserThreads(website);
                                while (itFrmUserThread.hasNext()) {
                                    FrmUserThread usrThread = itFrmUserThread.next();
                                    if (usrThread.getThread().getURI().equals(thread.getURI()) && usrThread.getUser()!=null && usrThread.getUser().getURI().equals(user.getURI())) {
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
                    }
                }
                %>
            </table>
            <br/>
            <p align="center">
                <b>Temas Favoritos</b>
            </p>
            <br/>
            <table border="1" width="95%" cellspacing="0" cellpadding="3" align="center">
                <tr bgcolor="#666669">
                    <td align="center"><font color="#FFFFFF">Nombre</font></td>
                    <td align="center"><font color="#FFFFFF">Vistas</font></td>
                    <td align="center"><font color="#FFFFFF">Replicas</font></td>
                    <td align="center"><font color="#FFFFFF">Ultimo mensaje</font></td>
                </tr>
                <%
                if (user != null && user.isSigned()) {
                    HashMap hmapForos = getFavoriteThreads(user, webpage);
                    Iterator itForos = hmapForos.keySet().iterator();
                    while (itForos.hasNext()) {
                        String strForo = (String) itForos.next();
                        Iterator<FrmThread> itThreads = ((ArrayList) hmapForos.get(strForo)).iterator();
                        while (itThreads.hasNext()) {
                            FrmThread frmThread = itThreads.next();
                            url.setParameter("threadUri", frmThread.getURI());
                            url.setAction("viewPost");
                %>

                <tr>
                    <td align="center"><a href="<%=url.toString()%>"><%=frmThread.getTitle()%></a></td>
                    <td align="center"><%=frmThread.getViewcount()%></td>
                    <td align="center"><%=frmThread.getReplyCount()%></td>
                    <td align="center">
                        <%if (frmThread.getLastpostdate() != null) {%>
                        <%=frmThread.getLastpostdate()%><br/> by
                        <%}%>
                        <%if (frmThread.getLastpostmember() != null) {%>
                        <%=frmThread.getLastpostmember().getName()%>
                        <%} else {%>&nbsp;<%}%>
                    </td>
                </tr>
                <%
                        }
                    }
                }
                %>
            </table>
            <%
            }
            %>
    </td></tr>
</table>

<%!
    private HashMap getFavoriteThreads(User user, WebPage webpage) {
        HashMap hmapForos = new HashMap();
        Iterator<FrmUserThread> itFrmUserThread = FrmUserThread.listFrmUserThreads(webpage.getWebSite());
        while (itFrmUserThread.hasNext()) {
            FrmUserThread usrThread = itFrmUserThread.next();
            if (usrThread.getUser()!=null && usrThread.getUser().getURI().equals(user.getURI())) {
                SemanticObject semObject = SemanticObject.createSemanticObject(usrThread.getThread().getURI());
                FrmThread favThread = FrmThread.getFrmThread(semObject.getId(), webpage.getWebSite());
                WebPage forum = favThread.getParent();
                if (!hmapForos.containsKey(forum.getURI())) {
                    ArrayList<FrmThread> aThreads = new ArrayList();
                    aThreads.add(favThread);
                    hmapForos.put(forum.getURI(), aThreads);
                } else {
                    ArrayList aThreads = (ArrayList) hmapForos.get(forum.getURI());
                    aThreads.add(favThread);
                }
            }
        }
        return hmapForos;
    }
%>
