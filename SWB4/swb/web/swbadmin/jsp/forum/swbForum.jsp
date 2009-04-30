<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.Resource"%>
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

<link href="/swb/swbadmin/css/forum.css" rel="stylesheet" type="text/css" />


<table>
<tr><td>
        <%
        WebPage webpage = paramRequest.getTopic();
        Resource base = paramRequest.getResourceBase();
        WebSite website = webpage.getWebSite();
        SWBResourceURL urlthread = paramRequest.getRenderUrl();
        SWBResourceURL url = paramRequest.getRenderUrl();
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        User user = paramRequest.getUser();
        String lang = user.getLanguage();
        String action = paramRequest.getAction();
        if (action != null && action.equals("viewPost")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
            Thread thread = Thread.getThread(semObject.getId(), website);
            thread.setViewCount(thread.getViewCount() + 1);
            url.setParameter("threadUri", thread.getURI());
            //url.setParameter("forumUri", request.getParameter("forumUri"));
            urlthread.setParameter("threadUri", thread.getURI());
            //urlp.setParameter("forumUri", request.getParameter("forumUri"));
        %>
<table border="0" cellspacing="1" cellpadding="2" width="100%">
    <tr>
        <td align="left" valign="top"  height="25" class="block-title"><%=thread.getTitle()%>&nbsp;</td>
        <td nowrap>
        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><!-- start newtopic.thtml -->
                    <%urlthread.setMode("addThread");%>
<a href="<%=urlthread.toString()%>"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/post_newtopic.gif" border="0" align="absmiddle" alt="<%=paramRequest.getLocaleString("newthread")%>" TITLE="<%=paramRequest.getLocaleString("newthread")%>"></a>
<!-- end newtopic.thtml --></td>
                    <td><!-- start replytopic.thtml -->
<!-- Post Reply -->
<%urlthread.setMode("replyPost");%>
<a href="<%=urlthread.toString()%>"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/post_reply.gif" border="0" align="absmiddle" alt="<%=paramRequest.getLocaleString("replyPost")%>" TITLE="P<%=paramRequest.getLocaleString("replyPost")%>"></a>
<!-- end replytopic.thtml --></td>
                </tr>
            </table>
        </td>
    </tr>
</table>        
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="pluginCellTitle alignleft">
    <tr>
        <td class="pluginBreadCrumbs alignleft" style="padding-left:5px;" nowrap></td>
        <td width="90%" class="alignright pluginBreadCrumbs" nowrap><!-- start subscribe.thtml -->
<a href="">Subscribirse</a>
<!-- end subscribe.thtml --></td>
        <td class="pluginBreadCrumbs" nowrap>&nbsp;|&nbsp;</td>
        <td class="pluginBreadCrumbs" style="padding-right:5px;" nowrap><!-- start print.thtml -->
<a href="">Versión para imprimir</a>
    </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="0" class="pluginSolidOutline alignleft">
<!-- end topic_navbar.thtml -->
<%
   String userCreated="";
   String threadCreator="";
   User userThread=null;
   if (thread.getCreator() != null) {
        userThread=thread.getCreator();
        userCreated=""+userThread.getCreated();
        threadCreator=userThread.getName();
   }
%>
<!-- start topic.thtml -->
<tr class="pluginRow1">
        <td height="30" style="padding-left:6px; padding-right:6px;"><a href="" class="authorname 1"><b><%=threadCreator%></b></a><a name="34151"></a></td>
        <td style="padding-left:6px; padding-right:6px;">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                                <td width="8" class="aligncenter"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/icon_minipost.gif"></td>
                                <td nowrap>&nbsp;<%=thread.getCreated()%> &nbsp;(<%=paramRequest.getLocaleString("read")%> <%=thread.getViewCount()%> <%=paramRequest.getLocaleString("times")%>) &nbsp;
                                <%
                                if(userThread!=null && user.getURI().equals(userThread.getURI())){ //imprimir combo de edición y eliminar
                                url.setParameter("threadUri", thread.getURI());
                                %>
                                    <form name="admActions">
                                    <select name="admAction">
                                        <option value="0">Editar</option>
                                        <option value="1">Eliminar</option>
                                    </select>
                                    <button type="submit" onclick="redirect(this.form);">ir kkk</button>
                                    </form>
                                    <script type="text/javascript">
                                        function redirect(forma){
                                            alert(forma.admAction.selectedIndex);
                                       }
                                    </script>
                                <%
                                }
                                %>
                                </td>
                                <td class="alignright">
                                    <div style="vertical-align:top; padding-top:2px;" class="alignright">

                                    </div>
                                </td>
                        </tr>
                </table>
        </td>
</tr>
<tr class="pluginRow1">
    <td style="vertical-align:top; padding:6px;" nowrap>
        <div>
                    <br>

        </div>
        <div>

                    <div style="padding-top:3px;"><br>EStatus: offline</div>
                    <p />
                    Registrado: <%=userCreated%>
                    <br>
                    Mensajes: 5<br>

                </div>
        <div style="background:transparent; width:110px; height:1px;"></div>
    </td>
    <td width="100%" style="vertical-align:top; padding:6px;" class="pluginRow1">
            <div style="min-height:100px;">
                <p><%=thread.getBody()%></p>

            </div>
            <div style="height:45px; padding-top:10px;display:none;">
                    <br>

                </div>
    </td>
</tr>
<tr class="pluginRow1" style="display:;">
    <td style="height:30px;">&nbsp;</td>
    <td style="padding-left:6px; padding-right:6px;">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td nowrap>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <!-- start profile.thtml -->
<td style="padding-right:3px;"><a href=""><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/profile.gif" border="0" align="absmiddle" alt="<%=paramRequest.getLocaleString("Profile")%>" TITLE="<%=paramRequest.getLocaleString("Profile")%>"></a></td>
<!-- end profile.thtml -->
                            <!-- start email.thtml -->
<td style="padding-right:3px;"><a href=""><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/email.gif" border="0" align="absmiddle" alt="<%=paramRequest.getLocaleString("email")%>" TITLE="<%=paramRequest.getLocaleString("email")%>"></a></td>
<!-- end email.thtml -->


                        </tr>
                    </table>
                <td class="alignright" style="float:right;" nowrap>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>

                            <!-- start quotetopic.thtml -->
                            <%url.setMode("replyPost");%>
                            <td><a href="<%=url.toString()%>"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/quote.gif" border="0" align="absmiddle" alt="<%=paramRequest.getLocaleString("reply")%>" TITLE="<%=paramRequest.getLocaleString("reply")%>"></a></td>
<!-- end quotetopic.thtml -->
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
        <td colspan="2" class="pluginSolidOutline"><div style="background:transparent; width:1px; height:10px;"></div></td>
</tr>
<!-- end topic.thtml -->


<!-- start topic.thtml -->

<%
            boolean cambiaColor = true;
            GenericIterator<Post> itPost = thread.listPosts();
            while (itPost.hasNext()) {
                Post post = itPost.next();
                url.setParameter("postUri", post.getURI());

               String postCreator="";
               String postCreated="";
               if (post.getCreator() != null) {
                    postCreator=post.getCreator().getName();
                    postCreated=""+post.getCreator().getCreated();
              }
            String rowClass = "pluginRow2";
            if (!cambiaColor) {
                rowClass = "pluginRow1";
            }
            cambiaColor = !(cambiaColor);
%>

<tr class="<%=rowClass%>">
        <td height="30" style="padding-left:6px; padding-right:6px;"><a href="" class="authorname 2"><b><%=postCreator%></b></a><a name="34152"></a></td>
        <td style="padding-left:6px; padding-right:6px;">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                                <td width="8" class="aligncenter"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/icon_minipost.gif"></td>
                                <td nowrap>&nbsp;<%=post.getCreated()%> &nbsp;&nbsp;
                                 
                               </td>
                                <td class="alignright">
                                    <div style="vertical-align:top; padding-top:2px;" class="alignright">

                                    </div>
                                </td>
                        </tr>
                </table>
        </td>
</tr>
<tr class="<%=rowClass%>">
    <td style="vertical-align:top; padding:6px;" nowrap>
        <div>
                    <IMG src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/rank3.gif" ALT="Forum - Usuario regular" TITLE="Forum - Usuario regular"><br>
                    - Usuario regular
        </div>
        <div>

                    <div style="padding-top:3px;"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/ascariote.jpg" alt="" class="userphoto"><br>EStatus: offline</div>
                    <p />
                    Registrado: <%=postCreated%><br>
                    Mensajes: 90<br>

                </div>
        <div style="background:transparent; width:110px; height:1px;"></div>
    </td>
    <td width="100%" style="vertical-align:top; padding:6px;" class="<%=rowClass%>">
            <div style="min-height:100px;">
                <%=post.getBody()%>
            </div>
            <div style="height:45px; padding-top:10px;display:none;">
                    <br>

                </div>
    </td>
</tr>
<tr class="<%=rowClass%>" style="display:;">
    <td style="height:30px;">&nbsp;</td>
    <td style="padding-left:6px; padding-right:6px;">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td nowrap>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <!-- start profile.thtml -->
<td style="padding-right:3px;"><a href=""><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/profile.gif" border="0" align="absmiddle" alt="Profile" TITLE="Profile"></a></td>
<!-- end profile.thtml -->
                            <!-- start email.thtml -->
<td style="padding-right:3px;"><a href=""><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/email.gif" border="0" align="absmiddle" alt="Email" TITLE="Email"></a></td>
<!-- end email.thtml -->


                        </tr>
                    </table>
                <td class="alignright" style="float:right;" nowrap>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>

                            <!-- start quotetopic.thtml -->
                            <%url.setMode("replyPost");%>
<td><a href="<%=url.toString()%>"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/quote.gif" border="0" align="absmiddle" alt="Quote" TITLE="Quote"></a></td>
<!-- end quotetopic.thtml -->
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
        <td colspan="2" class="pluginSolidOutline"><div style="background:transparent; width:1px; height:10px;"></div></td>
</tr>
<!-- end topic.thtml -->
<%
}
%>

<!-- start topicfooter.thtml -->
</table>
<table border="0" cellspacing="1" cellpadding="2" width="100%">
    <tr>
        <td width="95%" class="alignleft" height="24" nowrap style="padding-left:6px;">&nbsp;</td>
        <td nowrap>
        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><!-- start newtopic.thtml -->
                    <%urlthread.setMode("addThread");%>
<a href="<%=urlthread.toString()%>"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/post_newtopic.gif" border="0" align="absmiddle" alt="<%=paramRequest.getLocaleString("newthread")%>" TITLE="<%=paramRequest.getLocaleString("newthread")%>"></a>
<!-- end newtopic.thtml --></td>
                    <td><!-- start replytopic.thtml -->
<!-- Post Reply -->
<%urlthread.setMode("replyPost");%>
<a href="<%=urlthread.toString()%>"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/post_reply.gif" border="0" align="absmiddle" alt="<%=paramRequest.getLocaleString("replyPost")%>" TITLE="P<%=paramRequest.getLocaleString("replyPost")%>"></a>
<!-- end replytopic.thtml --></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<p />
<table border="0" cellspacing="0" cellpadding="2" width="100%">
    <tr>
        <td class="aligncenter" nowrap></td>
    </tr>
</table>
<p/><br>
<!-- end topicfooter.thtml -->


<%} else if (action != null && action.equals("removePost")) {
            if (request.getParameter("isthread") != null) {
                SemanticObject soThread = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
                Thread thread = Thread.getThread(soThread.getId(), website);
                actionURL.setAction("removeThread");
%>
<table class="forumline" width="100%">
    <tr>
        <th colspan="2" class="thtop"><%=paramRequest.getLocaleString("thread")%><%=thread.getTitle()%></th>
    </tr>
    <tr>
        <td  class="catleft"><%=paramRequest.getLocaleString("msg")%></td>
        <td  class="catleft"><%=thread.getBody()%></td>
    </tr>
    <tr>
        <td class="catleft"><%=paramRequest.getLocaleString("autor")%></td>
        <td class="catleft">
            <%if (thread.getCreator() != null) {%>
            <%=thread.getCreator().getName()%>
            <%}%>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td class="catleft"><%=paramRequest.getLocaleString("noMsgs")%></td>
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
    <tr>
        <td class="catleft"><%=paramRequest.getLocaleString("noAttachments")%></td>
        <%
    int attchmentsSize = 0;
    GenericIterator<Attachment> itattach = thread.listAttachments();
    while (itattach.hasNext()) {
        itattach.next();
        attchmentsSize++;
    }
        %>
        <td class="catleft">
            <%=attchmentsSize%>
        </td>
    </tr>
    <form name="removeThread" action="<%=actionURL.toString()%>">
        <input type="hidden" name="threadUri" value="<%=thread.getURI()%>">
        <input type="hidden" name="forumUri" value="<%=request.getParameter("forumUri")%>">
        <tr><td><input type="submit" value="<%=paramRequest.getLocaleString("remove")%>"></td>
            <td><input type="button" value="<%=paramRequest.getLocaleString("cancel")%>" onClick="retorna(this.form);"></td>
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
        <td colspan="2"><%=paramRequest.getLocaleString("msgRemove")%><%=post.getThread().getTitle(lang)%></td>
    </tr>
    <tr>
        <td><%=paramRequest.getLocaleString("body")%></td>
        <td><%=post.getBody()%></td>
    </tr>
    <tr>
        <td><%=paramRequest.getLocaleString("autor")%></td>
        <td>
            <%if (post.getCreator() != null) {%>
            <%=post.getCreator().getName()%>
            <%}%>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td><%=paramRequest.getLocaleString("noResponse")%></td>
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
        <td><%=paramRequest.getLocaleString("noAttachments")%></td>
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
        <tr><td><input type="submit" value="<%=paramRequest.getLocaleString("remove")%>"></td>
            <td><input type="button" value="<%=paramRequest.getLocaleString("cancel")%>" onClick="retorna(this.form);"></td>
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
<form name="viewthreads" action="<%=url.toString()%>">
        <table width="100%">
        <tr>
            <td align="right">
                <img onClick="document.viewthreads.submit();" src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/post_newtopic.gif" alt="<%=paramRequest.getLocaleLogString("newthread")%>"/>
            </td>
        </tr>
        <tr>
            <td>
         <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin:2px;">   
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="pluginCellTitle alignleft">
    <tr>
        <td class="pluginBreadCrumbs alignleft" style="padding-left:5px;" nowrap><div class="pagenav">Primero | Anterior | <b>1</b> <a href="">2</a> | <a href="">Siguiente</a> | <a href="">Ultimo</a></div></td>
        <td width="80%" class="pluginBreadCrumbs alignright" style="padding-right:5px;" nowrap>&nbsp;<!-- start subscribe_forum.thtml -->

<a href="">Track this forum</a>
<!--   Alternative layout to use Images -->
<!--
<a href="http://www.linuxparatodos.net/portal/forum/index.php?op=subscribe&amp;forum=27"><img src="http://www.linuxparatodos.net/portal/layout/FactorEvolucion2008/forum/image_set/forumnotify_on.gif" border="0" align="absmiddle" alt="Track this forum" TITLE="Track this forum"></a>
-->

<!-- end subscribe_forum.thtml --></td>
    </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="alignleft">
        <tr>
                <td>
                        <table width="100%" border="0" cellspacing="1" cellpadding="2" class="pluginSolidOutline">
                                <tr>
                                    <td colspan="2" class="aligncenter pluginCellSubTitle">&nbsp;   &nbsp;<%=paramRequest.getLocaleString("thread")%><br><a href=""><img src="http://www.linuxparatodos.net/portal/layout/FactorEvolucion2008/forum/image_set/asc.gif" border="0"></a>&nbsp;<a href=""><img src="http://www.linuxparatodos.net/portal/layout/FactorEvolucion2008/forum/image_set/desc.gif" border="0"></a></td>
                                    <td class="aligncenter pluginCellSubTitle"><%=paramRequest.getLocaleString("views")%><br><a href="index.php?forum=27&amp;order=0&amp;sort=2"><img src="http://www.linuxparatodos.net/portal/layout/FactorEvolucion2008/forum/image_set/asc.gif" border="0"></a>&nbsp;<a href="index.php?forum=27&amp;order=1&amp;sort=2"><img src="http://www.linuxparatodos.net/portal/layout/FactorEvolucion2008/forum/image_set/desc.gif" border="0"></a></td>
                                    <td class="aligncenter pluginCellSubTitle"><%=paramRequest.getLocaleString("replies")%><br><a href="index.php?forum=27&amp;order=0&amp;sort=3"><img src="http://www.linuxparatodos.net/portal/layout/FactorEvolucion2008/forum/image_set/asc.gif" border="0"></a>&nbsp;<a href="index.php?forum=27&amp;order=1&amp;sort=3"><img src="http://www.linuxparatodos.net/portal/layout/FactorEvolucion2008/forum/image_set/desc.gif" border="0"></a></td>
                                    <td class="aligncenter pluginCellSubTitle"><%=paramRequest.getLocaleString("lastMsg")%><br><a href="index.php?forum=27&amp;order=0&amp;sort=5"><img src="http://www.linuxparatodos.net/portal/layout/FactorEvolucion2008/forum/image_set/asc.gif" border="0"></a>&nbsp;<a href="index.php?forum=27&amp;order=1&amp;sort=5"><img src="http://www.linuxparatodos.net/portal/layout/FactorEvolucion2008/forum/image_set/desc_on.gif" border="0"></a></td>
                                    <td class="aligncenter pluginCellSubTitle"><%=paramRequest.getLocaleString("favorite")%></td>
                                </tr>
                                <%
                                String autor="";
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
                                            autor=thread.getCreator().getName();
                                        }
                                %>
<tr class="pluginRollOut" onMouseOver="className='pluginRollOver';" onMouseOut="className='pluginRollOut pluginLinks';">
    <td width="25" class="aligncenter pluginCol"><img src="http://www.linuxparatodos.net/portal/layout/FactorEvolucion2008/forum/image_set/newposts.png" border="0" align="absmiddle" alt="<%=paramRequest.getLocaleString("replyPost")%>" TITLE="P<%=paramRequest.getLocaleString("replyPost")%>">
    <td width="65%" onMouseOver="this.style.cursor='pointer';" onclick="window.location.href='<%=url.toString()%>'">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr class="pluginLinks">
                        <td nowrap width="50%"><a class="tooltip" style="text-decoration:none;" href="<%=url.toString()%>"><%=thread.getTitle()%><span style="left:50px;"><b>Started by:<%=autor%>, <%=thread.getCreated()%>&nbsp;</b><br /><%=thread.getBody()%>
                        </span></a></td>
                        <td colspan="2" class="alignright pluginLinks">&nbsp;</td>
                </tr>
        </table>
    </td>
    <td class="aligncenter pluginCol" width="45" nowrap><%=thread.getViewCount()%></td>
    <td class="aligncenter pluginCol" width="45" nowrap><%=thread.getReplyCount()%></td>
    <td width="25%" onMouseOver="this.style.cursor='pointer';" onclick="window.location.href='<%=url.toString()%>'" TITLE="Click to go directly to last post">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                            <td nowrap style="padding-left:2px;" class="pluginLinks">By:<%=autor%></td>
                    </tr>
                    <tr>
                            <td style="padding-left:2px" class="pluginLinks"><%=thread.getCreated()%>&nbsp;</td>
                    </tr>
            </table>
    </td>
    <td class="aligncenter pluginCol" width="45" nowrap align="center">
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
                <a href="<%=actionURL.toString()%>"><img onClick="document.viewthreads.submit();" src="<%=SWBPlatform.getContextPath()%>/swbadmin/icons/FAVORITOS.png" alt="<%=paramRequest.getLocaleLogString("addfavorite")%>"/></a>
                <%
                    }
                }
                %>
            </td>
</tr>
<%
}
}
%>
</table>
</td>
</tr>
</table>
</td>
</tr>

<tr><td align="left" valign="top"  height="25" class="block-title"><br>Favoritos&nbsp;</td></tr>
<tr>    
<td>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="pluginCellTitle alignleft">
    <tr>
        <td class="pluginBreadCrumbs alignleft" style="padding-left:5px;" nowrap><div class="pagenav">Primero | Anterior | <b>1</b> <a href="http://www.linuxparatodos.net/portal/forum/index.php?forum=27&amp;show=20&amp;order=0&amp;sort=0&amp;page=2">2</a> | <a href="http://www.linuxparatodos.net/portal/forum/index.php?forum=27&amp;show=20&amp;order=0&amp;sort=0&amp;page=2">Siguiente</a> | <a href="http://www.linuxparatodos.net/portal/forum/index.php?forum=27&amp;show=20&amp;order=0&amp;sort=0&amp;page=2">Ultimo</a></div></td>
        <td width="80%" class="pluginBreadCrumbs alignright" style="padding-right:5px;" nowrap>&nbsp;<!-- start subscribe_forum.thtml -->

<a href="http://www.linuxparatodos.net/portal/forum/index.php?op=subscribe&amp;forum=27">Track this forum</a>
<!--   Alternative layout to use Images -->
<!--
<a href="http://www.linuxparatodos.net/portal/forum/index.php?op=subscribe&amp;forum=27"><img src="http://www.linuxparatodos.net/portal/layout/FactorEvolucion2008/forum/image_set/forumnotify_on.gif" border="0" align="absmiddle" alt="Track this forum" TITLE="Track this forum"></a>
-->

<!-- end subscribe_forum.thtml --></td>
    </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="alignleft">
        <tr>
                <td>
                        <table width="100%" border="0" cellspacing="1" cellpadding="2" class="pluginSolidOutline">
                                <tr>
                                    <td colspan="2" class="aligncenter pluginCellSubTitle"><%=paramRequest.getLocaleString("thread")%><br><a href="index.php?forum=27&amp;order=0&amp;sort=1"><img src="http://www.linuxparatodos.net/portal/layout/FactorEvolucion2008/forum/image_set/asc.gif" border="0"></a>&nbsp;<a href="index.php?forum=27&amp;order=1&amp;sort=1"><img src="http://www.linuxparatodos.net/portal/layout/FactorEvolucion2008/forum/image_set/desc.gif" border="0"></a></td>
                                    <td class="aligncenter pluginCellSubTitle"><%=paramRequest.getLocaleString("views")%><br><a href="index.php?forum=27&amp;order=0&amp;sort=2"><img src="http://www.linuxparatodos.net/portal/layout/FactorEvolucion2008/forum/image_set/asc.gif" border="0"></a>&nbsp;<a href="index.php?forum=27&amp;order=1&amp;sort=2"><img src="http://www.linuxparatodos.net/portal/layout/FactorEvolucion2008/forum/image_set/desc.gif" border="0"></a></td>
                                    <td class="aligncenter pluginCellSubTitle"><%=paramRequest.getLocaleString("replies")%><br><a href="index.php?forum=27&amp;order=0&amp;sort=3"><img src="http://www.linuxparatodos.net/portal/layout/FactorEvolucion2008/forum/image_set/asc.gif" border="0"></a>&nbsp;<a href="index.php?forum=27&amp;order=1&amp;sort=3"><img src="http://www.linuxparatodos.net/portal/layout/FactorEvolucion2008/forum/image_set/desc.gif" border="0"></a></td>
                                    <td class="aligncenter pluginCellSubTitle"><%=paramRequest.getLocaleString("lastMsg")%><br><a href="index.php?forum=27&amp;order=0&amp;sort=5"><img src="http://www.linuxparatodos.net/portal/layout/FactorEvolucion2008/forum/image_set/asc.gif" border="0"></a>&nbsp;<a href="index.php?forum=27&amp;order=1&amp;sort=5"><img src="http://www.linuxparatodos.net/portal/layout/FactorEvolucion2008/forum/image_set/desc_on.gif" border="0"></a></td>
                                </tr>
                                <%
                                    if (user != null && user.isSigned()) {
                                        HashMap hmapForos = getFavoriteThreads(user, webpage);
                                        Iterator itForos = hmapForos.keySet().iterator();
                                        while (itForos.hasNext()) {
                                            String strForo = (String) itForos.next();
                                            Iterator<Thread> itThreads2 = ((ArrayList) hmapForos.get(strForo)).iterator();
                                            while (itThreads2.hasNext()) {
                                                Thread frmThread = itThreads2.next();
                                                url.setParameter("threadUri", frmThread.getURI());
                                                url.setAction("viewPost");
                                %>
<tr class="pluginRollOut" onMouseOver="className='pluginRollOver';" onMouseOut="className='pluginRollOut pluginLinks';">
    <td width="25" class="aligncenter pluginCol"><img src="http://www.linuxparatodos.net/portal/layout/FactorEvolucion2008/forum/image_set/newposts.png" border="0" align="absmiddle" alt="New Post" TITLE="New Post"></td>
    <td width="65%" onMouseOver="this.style.cursor='pointer';" onclick="window.location.href='<%=url.toString()%>'">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr class="pluginLinks">
                        <td nowrap width="50%"><a class="tooltip" style="text-decoration:none;" href="<%=url.toString()%>"><%=frmThread.getTitle()%><span style="left:50px;"><b>Started by:<%=autor%>, <%=frmThread.getCreated()%>&nbsp;</b><br /><%=frmThread.getBody()%>
                        </span></a></td>
                        <td colspan="2" class="alignright pluginLinks">&nbsp;</td>
                </tr>
        </table>
    </td>
    <td class="aligncenter pluginCol" width="45" nowrap><%=frmThread.getViewCount()%></td>
    <td class="aligncenter pluginCol" width="45" nowrap><%=frmThread.getReplyCount()%></td>
    <td width="25%" onMouseOver="this.style.cursor='pointer';" onclick="window.location.href='<%=url.toString()%>'" TITLE="Click to go directly to last post">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                            <td nowrap style="padding-left:2px;" class="pluginLinks">By:<%=autor%></td>
                    </tr>
                    <tr>
                            <td style="padding-left:2px" class="pluginLinks"><%=frmThread.getCreated()%>&nbsp;</td>
                    </tr>
            </table>
    </td>
</tr>
<%
    }
  }
}
%>
</table>
</td>
</tr>
</table>

</td>
</tr>
</table>


</form>
<%
        }
%>
</td></tr>
</table>


<%!
    private HashMap getFavoriteThreads(User user, WebPage webpage) {
        HashMap hmapForos = new HashMap();
        Iterator<UserFavThread> itFrmUserThread = UserFavThread.listUserFavThreads(webpage.getWebSite());
        while (itFrmUserThread.hasNext()) {
            UserFavThread usrThread = itFrmUserThread.next();
            if (usrThread.getUser()!=null && usrThread.getUser().getURI().equals(user.getURI())) {
                SemanticObject semObject = SemanticObject.createSemanticObject(usrThread.getThread().getURI());
                Thread favThread = Thread.getThread(semObject.getId(), webpage.getWebSite());
                WebPage forum = favThread.getParent();
                if (!hmapForos.containsKey(forum.getURI())) {
                    ArrayList<Thread> aThreads = new ArrayList();
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
