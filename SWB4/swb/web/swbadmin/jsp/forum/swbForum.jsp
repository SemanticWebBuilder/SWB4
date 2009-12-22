<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.model.GenericIterator"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.portal.resources.sem.forum.UserFavThread"%>
<%@page import="org.semanticwb.portal.resources.sem.forum.SWBForum"%>
<%@page import="org.semanticwb.portal.resources.sem.forum.Thread"%>
<%@page import="org.semanticwb.portal.resources.sem.forum.Post"%>
<%@page import="org.semanticwb.portal.resources.sem.forum.Attachment"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.portal.SWBFormButton"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>

<style type="text/css">
* {
	margin: 0px;
	padding: 0px;
	font-size: 100%;
	vertical-align: baseline;
}

body {
	font-family: Arial, Helvetica, sans-serif;
	background-repeat: no-repeat;
}
p {margin-bottom: 10px; color: #626262;	font-size: 0.7em;}
a {text-decoration: none;}
a:hover {text-decoration: underline;}
</style>

      <%
        WebPage webpage = paramRequest.getWebPage();
        Resource base = paramRequest.getResourceBase();
        WebSite website = webpage.getWebSite();
        SWBResourceURL urlthread = paramRequest.getRenderUrl();
        SWBResourceURL url = paramRequest.getRenderUrl();
        SWBResourceURL urlRemovePost = paramRequest.getRenderUrl();
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        User user = paramRequest.getUser();
        String lang = user.getLanguage();
        String action = paramRequest.getAction();
        String autor = "";
        if (action != null && action.equals("viewPost")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
            Thread thread = Thread.getThread(semObject.getId(), website);
            if(request.getParameter("addView")!=null) thread.setViewCount(thread.getViewCount() + 1);
            url.setParameter("threadUri", thread.getURI());
            urlRemovePost.setParameter("threadUri", thread.getURI());
            urlthread.setParameter("threadUri", thread.getURI());
            urlthread.setMode("addThread");
            if (thread.getCreator() != null) {
                autor = thread.getCreator().getFullName();
            }
        %>
        <div id="contenido">
        <div class="innerContent">
           <div id="WBForo">
               <p class="agregarContenido"><a href="<%=urlthread%>"><%=paramRequest.getLocaleString("publicThread")%></a></p>
            <!-- INICIA ENTRADA -->
            <div class="entradaForo">
              <div class="readNotread_foro">
                <p><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/leido.png" alt="Leido" width="18" height="24" /></p>
              </div>
              <p class="tituloNota"><%=thread.getTitle()%></p>
              <p class="tituloNoticia"><a href="#"><%=autor%></a></p>
              <p><%=thread.getBody()%></p>
              <div class="vistasForo">
                <p> (<%=thread.getReplyCount()%>) <%=paramRequest.getLocaleString("responses")%> <img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/commentsForo.png" alt="<%=paramRequest.getLocaleString("responses")%>" width="14" height="12" /> |  (<%=thread.getViewCount()%>) <%=paramRequest.getLocaleString("visites")%> <img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/viewsForo.png" alt="<%=paramRequest.getLocaleString("visites")%>" width="10" height="9" />
                 |  <%urlthread.setMode("editThread");%><a href="<%=urlthread%>"><%=paramRequest.getLocaleString("edit")%></a> <img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/editar_foro.png" alt="<%=paramRequest.getLocaleString("edit")%>" width="7" height="15" />
                 |  <%url.setAction("removePost");url.setParameter("isthread", "1");
                         %> <a href="<%=url%>"><%=paramRequest.getLocaleString("remove")%></a> <img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/eliminar_foro.png" alt="<%=paramRequest.getLocaleString("remove")%>" width="11" height="12" /></p>
              </div>
              <p>&nbsp;</p>
            </div>
            <%
                actionURL.setParameter("threadUri", thread.getURI());
                actionURL.setAction("replyPost");
                request.setAttribute("formName", "postThread");
                %>
            <table>
            <form id="postThread" name="postThread" action="<%=actionURL%>" onSubmit="return valida(this);" method="post">
                <tr>
                    <td><%=paramRequest.getLocaleString("msg")%>:</td><td><textarea name="pstBody"></textarea></td>
                </tr>
                <tr>
                 <td>I<%=paramRequest.getLocaleString("img")%>:</td><td><%=SWBPortal.getFileUploadCtrlString("hasAttachments", request)%></td>
                </tr>
                <tr>
                 <td colspan="2"><input type="submit" value="<%=paramRequest.getLocaleString("send")%>"/></td>
                </tr>
            </form>
            </table>    
            <script language="javascript" type="text/javascript">
                function vacio(q) {
                     for ( i = 0; i < q.length; i++ ) {
                             if ( q.charAt(i) != " " ) {
                                   return true
                             }
                     }
                    return false
                }

                //valida que el campo no este vacio y no tenga solo espacios en blanco
                 function valida(F) {
                     if( vacio(F.pstBody.value) == false ) {
                             alert('<%=paramRequest.getLocaleString("msgField")%>')
                             F.pstBody.focus();
                             return false
                     }
                     return true
                 }
            </script>
            <%
                String photo=SWBPlatform.getContextPath()+"/swbadmin/images/defaultPhoto.png";
                boolean cambiaColor = true;
                GenericIterator<Post> itPost = thread.listPosts();
                while (itPost.hasNext()) {
                    Post post = itPost.next();
                    url.setParameter("postUri", post.getURI());
                    urlRemovePost.setParameter("postUri", post.getURI());

                    User userPost = null;
                    String postCreator = "";
                    String postCreated = "";
                    if (post.getCreator() != null) {
                        userPost = post.getCreator();
                        postCreator = post.getCreator().getFullName();
                        if(post.getCreator().getPhoto()!=null) photo=SWBPortal.getWebWorkPath()+post.getCreator().getPhoto();
                    }
                    String rowClass = "pluginRow2";
                    if (!cambiaColor) {
                        rowClass = "pluginRow1";
                    }
                    cambiaColor = !(cambiaColor);
             %>
                    <div class="replyForo">
                      <div class="img_ReplyForo">
                        <img src="<%=photo%>" alt="Usuario" width="80" height="80" />
                      </div>
                      <p class="tituloNoticia"><%=postCreator%></p>
                      <p><%=post.getBody()%></p>
                      <%urlRemovePost.setAction("removePost");%>
                      <div class="vistasForo_comment">
                          <%urlthread.setMode("replyPost");urlthread.setParameter("postUri", post.getURI());%>
                          <p> <a href="<%=urlthread%>"><%=paramRequest.getLocaleString("comment")%></a> <img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/commentsForo.png" alt="<%=paramRequest.getLocaleString("comment")%>" width="14" height="12" /> |  <%urlthread.setMode("editPost");%><a href="<%=urlthread%>"><%=paramRequest.getLocaleString("edit")%></a> <img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/editar_foro.png" alt="<%=paramRequest.getLocaleString("edit")%>" width="7" height="15" /> |  <%url.setAction("removePost");%>
                                <a href="<%=urlRemovePost%>"><%=paramRequest.getLocaleString("remove")%></a> <img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/eliminar_foro.png" alt="<%=paramRequest.getLocaleString("remove")%>" width="11" height="12" /></p>
                      </div>
                    </div>
                    <%
                    }
                  %>
          </div>
        </div>
        </div>
        <%} else if (action != null && action.equals("removePost")) {
                if (request.getParameter("isthread") != null) {
                    SemanticObject soThread = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
                    Thread thread = Thread.getThread(soThread.getId(), website);
                    actionURL.setAction("removeThread");
            %>
              <table class="eliminarDatos">
                  <caption>
                    <%=paramRequest.getLocaleString("threadData")%>
                  </caption>

                <tr>
                    <td><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/icon_minipost.gif"></td>
                    <th><%=paramRequest.getLocaleString("thread")%></th>
                    <td><%=thread.getTitle()%></td>
                  </tr>
                  <tr>
                    <td><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/icon_minipost.gif"></td>
                    <th><%=paramRequest.getLocaleString("msg")%></th>
                    <td><%=thread.getBody()%></td>
                  </tr>
                  <tr>
                    <td><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/icon_minipost.gif"></td>
                    <th><%=paramRequest.getLocaleString("autor")%></th>
                    <td>
                    <%if (thread.getCreator() != null) {%>
                     <%=thread.getCreator().getName()%>
                    <%}%>
                    </td>
                  </tr>
                  <tr>
                    <td><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/icon_minipost.gif"></td>
                    <th><%=paramRequest.getLocaleString("noMsgs")%></th>
                    <td>
                      <%
                        int postSize = 0;
                        GenericIterator<Post> itPost = thread.listPosts();
                        while (itPost.hasNext()) {
                            Post post = itPost.next();
                            postSize++;
                        }
                      %>
                        <%=postSize%>
                    </td>
                  </tr>
                  <tr>
                    <td><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/icon_minipost.gif"></td>
                    <th><%=paramRequest.getLocaleString("noAttachments")%></th>
                    <td>
                       <%
                        int attchmentsSize = 0;
                        GenericIterator<Attachment> itattach = thread.listAttachments();
                        while (itattach.hasNext()) {
                            itattach.next();
                            attchmentsSize++;
                        }
                       %>
                        <%=attchmentsSize%>
                    </td>
                  </tr>
                  <tr>
                    <td><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/icon_minipost.gif"></td>
                    <th><%=paramRequest.getLocaleString("totAttach")%></th>
                    <td>
                        <%
                        int attchmentsTotSize = attchmentsSize;
                        itPost = thread.listPosts();
                        while (itPost.hasNext()) {
                            attchmentsTotSize = getTotAttachments(itPost.next(), attchmentsTotSize);
                        }
                        %>
                        <%=attchmentsTotSize%>
                    </td>
                  </tr>
                  <form name="removeThread" action="<%=actionURL.toString()%>">
                    <input type="hidden" name="threadUri" value="<%=thread.getURI()%>">
                    <input type="hidden" name="forumUri" value="<%=request.getParameter("forumUri")%>">
                    <tr class="pluginRow1"><td><input type="submit" value="<%=paramRequest.getLocaleString("remove")%>"></td>
                        <td><input type="button" value="<%=paramRequest.getLocaleString("cancel")%>" onClick="retorna(this.form);"></td>
                    </tr>
                 </form>
                </table>
            <%} else {
                    actionURL.setAction("removePost");
                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                    Post post = Post.getPost(semObject.getId(), paramRequest.getWebPage().getWebSite());
            %>
            <table border="0" cellspacing="1" cellpadding="2" width="100%">
                <tr><td>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="pluginCellTitle alignleft">
                            <tr>
                                <td class="pluginBreadCrumbs alignleft" style="padding-left:5px;" nowrap></td>
                                <td width="90%" class="alignright pluginBreadCrumbs" nowrap><!-- start subscribe.thtml -->
                                    <%=paramRequest.getLocaleString("PostData")%>
                                </td>
                                <td class="pluginBreadCrumbs" nowrap>&nbsp; &nbsp;</td>
                                <td class="pluginBreadCrumbs" style="padding-right:5px;" nowrap><!-- start print.thtml -->
                                &nbsp; &nbsp;
                            </tr>
                        </table>
                </td></tr>
                <tr><td>
                        <table width="100%" border="0" cellspacing="1" cellpadding="0" class="pluginSolidOutline alignleft">
                            <tr class="pluginRow1">
                                <td style="padding-left:6px; padding-right:6px;">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="8" class="aligncenter"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/icon_minipost.gif"></td>
                                            <td height="30" style="padding-left:6px; padding-right:6px;"><b><%=paramRequest.getLocaleString("thread")%></b></td>
                                        </tr>
                                    </table>
                                </td>
                                <td style="padding-left:6px; padding-right:6px;">&nbsp; <%=post.getThread().getTitle()%> &nbsp;</td>
                            </tr>
                            <tr class="pluginRow2">
                                <td style="padding-left:6px; padding-right:6px;">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="8" class="aligncenter"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/icon_minipost.gif"></td>
                                            <td height="30" style="padding-left:6px; padding-right:6px;"><b><%=paramRequest.getLocaleString("msg")%></b></td>
                                        </tr>
                                    </table>
                                </td>
                                <td style="padding-left:6px; padding-right:6px;">&nbsp; <%=post.getBody()%> &nbsp;</td>
                            </tr>
                            <tr class="pluginRow1">
                                <td style="padding-left:6px; padding-right:6px;">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="8" class="aligncenter"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/icon_minipost.gif"></td>
                                            <td height="30" style="padding-left:6px; padding-right:6px;"><b><%=paramRequest.getLocaleString("autor")%></b></td>
                                        </tr>
                                    </table>
                                </td>
                                <td style="padding-left:6px; padding-right:6px;">&nbsp;
                                    <%if (post.getCreator() != null) {%>
                                    <%=post.getCreator().getName()%>
                                    <%}%>
                                &nbsp;</td>
                            </tr>
                            <%
                    int attchmentsSize = 0;
                    GenericIterator<Attachment> itattach = post.listAttachmentss();
                    while (itattach.hasNext()) {
                        itattach.next();
                        attchmentsSize++;
                    }
                    int noAttach = attchmentsSize;
                    int postSize = 0;
                    String postandAttach = "0/0";
                    Iterator<Post> itPost = post.listchildPosts();
                    while (itPost.hasNext()) {
                        postSize++;
                        if (postandAttach.equals("0/0")) {
                            postandAttach = postSize + "/" + noAttach;
                        } else {
                            String[] posattachX = postandAttach.split("/");
                            int postCount = Integer.parseInt(posattachX[0]) + 1;
                            int AttachCount = Integer.parseInt(posattachX[1]);
                            postandAttach = postCount + "/" + AttachCount;
                        }
                        postandAttach = getPostAndAttachments(itPost.next(), postandAttach);
                    }
                    String[] posattachX = postandAttach.split("/");
                    int postCount = Integer.parseInt(posattachX[0]);
                    int AttachCount = Integer.parseInt(posattachX[1]);
                            %>
                            <tr class="pluginRow2">
                                <td style="padding-left:6px; padding-right:6px;">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="8" class="aligncenter"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/icon_minipost.gif"></td>
                                            <td height="30" style="padding-left:6px; padding-right:6px;"><b><%=paramRequest.getLocaleString("noResponse")%></b></td>
                                        </tr>
                                    </table>
                                </td>
                                <td style="padding-left:6px; padding-right:6px;">&nbsp; <%=postSize%> &nbsp;</td>
                            </tr>
                            <tr class="pluginRow1">
                                <td style="padding-left:6px; padding-right:6px;">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="8" class="aligncenter"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/icon_minipost.gif"></td>
                                            <td height="30" style="padding-left:6px; padding-right:6px;"><b><%=paramRequest.getLocaleString("TotReply")%></b></td>
                                        </tr>
                                    </table>
                                </td>
                                <td style="padding-left:6px; padding-right:6px;">&nbsp; <%=postCount%> &nbsp;</td>
                            </tr>
                            <tr class="pluginRow2">
                                <td style="padding-left:6px; padding-right:6px;">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="8" class="aligncenter"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/icon_minipost.gif"></td>
                                            <td height="30" style="padding-left:6px; padding-right:6px;"><b><%=paramRequest.getLocaleString("noAttachments")%></b></td>
                                        </tr>
                                    </table>
                                </td>
                                <td style="padding-left:6px; padding-right:6px;">&nbsp; <%=attchmentsSize%> &nbsp;</td>
                            </tr>
                            <tr class="pluginRow1">
                                <td style="padding-left:6px; padding-right:6px;">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="8" class="aligncenter"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/icon_minipost.gif"></td>
                                            <td height="30" style="padding-left:6px; padding-right:6px;"><b><%=paramRequest.getLocaleString("totAttach")%></b></td>
                                        </tr>
                                    </table>
                                </td>
                                <td style="padding-left:6px; padding-right:6px;">&nbsp; <%=AttachCount%> &nbsp;</td>
                            </tr>

                            <form name="removePost" action="<%=actionURL.toString()%>">
                                <input type="hidden" name="postUri" value="<%=post.getURI()%>">
                                <input type="hidden" name="threadUri" value="<%=request.getParameter("threadUri")%>">
                                <tr><td><input type="submit" value="<%=paramRequest.getLocaleString("remove")%>"></td>
                                    <td><input type="button" value="<%=paramRequest.getLocaleString("cancel")%>" onClick="retorna(this.form);"></td>
                                </tr>
                            </form>
                        </table>
                </td></tr>
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
            <div id="contenido">
             <div class="innerContent">
                <div id="WBForo">
                    <p class="agregarContenido"><a href="<%=url%>"><%=paramRequest.getLocaleString("publicThread")%></a></p>
                    <%
                    autor = "";
                    url.setMode(url.Mode_VIEW);
                    url.setAction("viewPost");
                    GenericIterator<WebPage> itThreads = webpage.listChilds();
                    while (itThreads.hasNext()) {
                        WebPage wp = itThreads.next();
                        Thread thread = (Thread) wp.getSemanticObject().createGenericInstance();
                        SWBForum forum = thread.getForum();
                        if (forum.getId().equals(base.getId())) {
                            url.setParameter("threadUri", thread.getURI());
                            if (thread.getCreator() != null) {
                                autor = thread.getCreator().getFullName();
                            }
                    %>
                      <div class="entradaForo" >
                          <div class="readNotread_foro"><p>
                          <%
                            url.setParameter("addView", "1");
                            if(thread.getViewCount()==0){%> <img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/noleido.png" alt="Leido" width="18" height="24" />
                          <%}else{%> <img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/leido.png" alt="Leido" width="18" height="24" />
                          <%}%>
                          </p>
                          </div>
                            <p class="tituloNota"><a href="<%=url%>"><%=thread.getTitle()%></a></p>
                            <p class="tituloNoticia"><a href="#"><%=autor%></a></p>

                            <div class="lastView_foro">
                                <%String date=SWBUtils.TEXT.getStrDate(thread.getCreated(), user.getLanguage());%>
                                <p>Última entrada: <%=date%></p>
                            </div>
                            <div class="vistasForo">
                                <p> (<%=thread.getReplyCount()%>) <%=paramRequest.getLocaleString("responses")%> <img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/commentsForo.png" alt="<%=paramRequest.getLocaleString("responses")%>" width="14" height="12" /> |  (<%=thread.getViewCount()%>) <%=paramRequest.getLocaleString("visites")%> <img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/viewsForo.png" alt="<%=paramRequest.getLocaleString("visites")%>" width="10" height="9" /></p>
                            </div>
                            <div class="clearNosp">&nbsp;</div>                        
                       </div>
                 <%
                        }
                    }
                 %>
               </div>
           </div>
           </div>
           <%
           }
          %>



<%!
    private String getPostAndAttachments(Post post, String posattach) {
        String[] posattachX = posattach.split("/");
        int postCount = Integer.parseInt(posattachX[0]);
        int AttachCount = Integer.parseInt(posattachX[1]);
        GenericIterator<Attachment> gitAttach = post.listAttachmentss();
        while (gitAttach.hasNext()) {
            gitAttach.next();
            AttachCount++;
        }
        posattach = postCount + "/" + AttachCount;
        GenericIterator<Post> gitPost = post.listchildPosts();
        while (gitPost.hasNext()) {
            posattachX = posattach.split("/");
            postCount = Integer.parseInt(posattachX[0]) + 1;
            AttachCount = Integer.parseInt(posattachX[1]);
            posattach = getPostAndAttachments(gitPost.next(), postCount + "/" + AttachCount);
        }
        return posattach;
    }

    private int getTotAttachments(Post post, int attchCount) {
        GenericIterator<Attachment> gitAttach = post.listAttachmentss();
        while (gitAttach.hasNext()) {
            gitAttach.next();
            attchCount++;
        }
        GenericIterator<Post> gitPost = post.listchildPosts();
        while (gitPost.hasNext()) {
            attchCount = getTotAttachments(gitPost.next(), attchCount);
        }
        return attchCount;
    }

%>