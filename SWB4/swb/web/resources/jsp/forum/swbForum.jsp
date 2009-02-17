<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.GenericIterator"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.forum.FrmCategory"%>
<%@page import="org.semanticwb.forum.FrmForum"%>
<%@page import="org.semanticwb.forum.FrmThread"%>
<%@page import="org.semanticwb.forum.FrmPost"%>
<%@page import="org.semanticwb.forum.FrmAttachments"%>
<%@page import="org.semanticwb.forum.FrmThreadTypeCat"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.SWBPlatform"%>


<table>
    <tr><td>
            <%
        WebSite website = paramRequest.getTopic().getWebSite();
        SWBResourceURL url = paramRequest.getRenderUrl();
        String lang = paramRequest.getUser().getLanguage();
        String action = (String) request.getAttribute("action");
        if (action != null && action.equals("viewThreads")) {
            url.setMode("addThread");
            url.setParameter("forumUri", request.getParameter("forumUri"));
            %>
            <a href="<%=url.toString()%>">Nuevo tema</a>
            <table border="1" width="95%" cellspacing="0" cellpadding="3" align="center">
                <tr bgcolor="#666669">
                    <td colspan="2"><font color="#FFFFFF">Tema</font></td>
                    <td align="center"><font color="#FFFFFF">Autor</font></td>
                    <td align="center"><font color="#FFFFFF">Replicas</font></td>
                    <td align="center"><font color="#FFFFFF">Vistas</font></td>
                    <td align="center"><font color="#FFFFFF">Ultimo mensaje</font></td>
                </tr>
                <%
                url.setMode(url.Mode_VIEW);
                url.setAction("viewPost");
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("forumUri"));
                FrmForum forum = FrmForum.getFrmForum(semObject.getId(), website);
                Iterator<FrmThreadTypeCat> itThreadTypes = FrmThreadTypeCat.listFrmThreadTypeCats();
                while (itThreadTypes.hasNext()) {
                    FrmThreadTypeCat threadType = itThreadTypes.next();
                    boolean flag=false;
                    GenericIterator<FrmThread> itThreads = forum.listthreads();
                    while (itThreads.hasNext()) {
                        FrmThread thread = itThreads.next();
                        if (thread.getType().getURI().equals(threadType.getURI())) {
                            url.setParameter("threadUri", thread.getURI());
                            if(!flag){
                                flag=true;
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
                        <%=thread.getReplyCount()%>
                    </td>
                    <td align="center">
                        <%if (thread.getLastpostdate() != null) {%>
                        <%=thread.getLastpostdate()%><br/> by
                        <%}%>
                        <%if (thread.getLastpostmember() != null) {%>
                        <%=thread.getLastpostmember().getName()%>
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
            } else if (action != null && action.equals("viewPost")) {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
                FrmThread thread = FrmThread.getFrmThread(semObject.getId(), website);
                thread.setViewcount(thread.getViewcount()+1);
                url.setParameter("threadUri", thread.getURI());
                url.setParameter("forumUri",request.getParameter("forumUri"));
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
                    <%url.setMode("editPost");
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
                GenericIterator<FrmPost> itPost = thread.listPosts();
                while (itPost.hasNext()) {
                    FrmPost post = itPost.next();
                    url.setParameter("postUri", post.getURI());
                %>
                <tr>
                <td colspan="2">
                    <%if(post.getCreator()!=null){%>
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

            <%} else if (action != null && action.equals("removePost")) {
                SWBResourceURL actionURL = paramRequest.getActionUrl();
                if(request.getParameter("isthread")!=null){
                    SemanticObject soThread=SemanticObject.createSemanticObject(request.getParameter("threadUri"));
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
                                <%if(thread.getCreator()!=null){%>
                                    <%=thread.getCreator().getName()%>
                                <%}%>
                                &nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td>Número de Mensajes</td>
                            <%
                            int postSize=0;
                            GenericIterator <FrmPost> itPost=thread.listPosts();
                            while(itPost.hasNext()){
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
                        <input type="hidden" name="forumUri" value="<%=request.getParameter("forumUri")%>">
                         <tr><td>
                            <input type="submit" value="eliminar">
                         </tr></td>
                         </form>
                    </table>
                <%}else{
                    actionURL.setAction("removePost");
                    SemanticObject semObject=SemanticObject.createSemanticObject(request.getParameter("postUri"));
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
                                <%if(post.getCreator()!=null){%>
                                    <%=post.getCreator().getName()%>
                                <%}%>
                                &nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td>Número de Respuestas</td>
                            <%
                            int postSize=0;
                            Iterator <FrmPost> itPost=post.listchildPosts();
                            while(itPost.hasNext()){
                               itPost.next();
                               postSize++;
                            }
                            %>
                            <td><%=postSize%></td>
                        </tr>
                        <tr>
                            <td>Número de Archivos adjuntos</td>
                            <%
                            int attchmentsSize=0;
                            GenericIterator <FrmAttachments> itattach=post.listAttachs();
                            while(itattach.hasNext()){
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
                         <tr><td>
                            <input type="submit" value="eliminar">
                         </tr></td>
                         </form>
                    </table>
                    <%
                }
            %>
            <%} else {
            %>
            <table border="1" width="95%" cellspacing="0" cellpadding="3" align="center">
                <tr bgcolor="#666669">
                    <td colspan="2"><font color="#FFFFFF">Foro/Descripción</font></td>
                    <td align="center"><font color="#FFFFFF">Temas</font></td>
                    <td align="center"><font color="#FFFFFF">Mensajes</font></td>
                    <td align="center"><font color="#FFFFFF">Ultimo mensaje</font></td>
                </tr>
                <%
                url.setAction("viewThreads");
                Iterator<FrmCategory> itCategories = FrmCategory.listFrmCategorys(website);
                while (itCategories.hasNext()) {
                    FrmCategory category = itCategories.next();
                %>
                <tr bgcolor="#C0C0C0" font-size="12px" font-weight="normal" ine-height="20px">
                    <td colspan="5"><b><%=category.getName()%></b><br/><%=category.getDescription()%><br/></td>
                </tr>
                <%
        GenericIterator<FrmForum> gitForums = category.listForums();
        while (gitForums.hasNext()) {
            FrmForum forum = gitForums.next();
                %>
                <tr bgcolor="#FFFFFF" font-size="12px">
                    <td width="20" align="center" nowrap="nowrap">&nbsp</td>
                    <%url.setParameter("forumUri", forum.getURI());%>
                    <td width="30%"><a href="<%=url.toString()%>"><b><%=forum.getTitle()%></b></a><br/>
                        <%if (forum.getDescription() != null) {%>
                    <%=forum.getDescription()%><br/></td>
                    <%}%>
                    <td align="center">
                        <%=forum.getThreadcount()%>
                    </td>
                    <td align="center">
                        <%=forum.getPostcount()%>
                    </td>
                    <td align="center">
                        <%=forum.getUpdated()%><br/>by
                        <%if (forum.getModifiedBy() != null) {%>
                        <%=forum.getModifiedBy().getName()%>
                        <%}%>
                    </td>
                </tr>
                <%
                    }
                }
                %>
            </table>
            <%
        }
            %>
    </td></tr>
</table>
