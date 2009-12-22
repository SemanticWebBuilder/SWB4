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

<%
    SWBResourceURL actionURL = paramRequest.getActionUrl();
    if(paramRequest.getMode().equals("replyPost")){
        request.setAttribute("formName", "postSomeThing");
        WebSite website=paramRequest.getWebPage().getWebSite();
        SemanticObject soThread = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
        Thread thread = Thread.getThread(soThread.getId(), website);
        SemanticObject soPost = null;
        Post post=null;
        if (request.getParameter("postUri") != null) {
            soPost = SemanticObject.createSemanticObject(request.getParameter("postUri"));
            post = Post.getPost(soPost.getId(), website);
        }
        actionURL.setParameter("threadUri", request.getParameter("threadUri"));
        actionURL.setParameter("postUri", request.getParameter("postUri"));

        actionURL.setAction("replyPost");
        %>
        <p><%=paramRequest.getLocaleString("thread")%>:<%=thread.getTitle()%></p>
        <%if(post!=null){%>
            <p><%=paramRequest.getLocaleString("msg")%>:<%=post.getBody()%></p>
        <%}else {%>
            <p><%=paramRequest.getLocaleString("msg")%>:<%=thread.getBody()%></p>
        <%}%>
        <br>
        <form id="postSomeThing" name="postSomeThing" action="<%=actionURL%>" onSubmit="return valida(this);" method="post">
             <p><%=paramRequest.getLocaleString("msg")%>:<textarea name="pstBody"></textarea></p>
             <p><%=paramRequest.getLocaleString("img")%>:<%=SWBPortal.getFileUploadCtrlString("hasAttachments", request)%></p>
             <p><input type="submit" value="<%=paramRequest.getLocaleString("send")%>"/><input type="button" name="Cancel" value="<%=paramRequest.getLocaleString("cancel")%>" onClick="history.go(-1);"/></p>
        </form>
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
    }else if(paramRequest.getMode().equals("addThread")){
        actionURL.setAction("addThread");
        request.setAttribute("formName", "createThread");
        %>
            <form id="createThread" name="createThread" action="<%=actionURL%>" onSubmit="return valida(this);">
                <table>
                <tr><td>
                <fieldset>
                 *T&iacute;tulo:<input type="text" name="title">
                  <p><%=paramRequest.getLocaleString("msg")%>:<textarea name="thBody"></textarea></p>
                  <p><%=paramRequest.getLocaleString("img")%>:<%=SWBPortal.getFileUploadCtrlString("hasThAttachments", request)%></p>
                </fieldset>
                <fieldset>
                    <input type="submit" value="<%=paramRequest.getLocaleString("send")%>"/><input type="button" value="<%=paramRequest.getLocaleString("cancel")%>" onclick="history.go(-1);">
                </fieldset>
                </td></tr>
                </table>
            </form>
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
                     if( vacio(F.title.value) == false ) {
                             alert('<%=paramRequest.getLocaleString("titleField")%>')
                             F.title.focus();
                             return false
                     }
                     return true
                 }
            </script>
        <%
    }
%>