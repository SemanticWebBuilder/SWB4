<%-- 
    Document   : classifybyTopic
    Created on : 27-may-2013, 12:36:57
    Author     : jorge.jimenez
--%>

<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
    if (request.getAttribute("postUri") == null) {
        return;
    }

    SemanticObject semObjPost = (SemanticObject) request.getAttribute("postUri");

    WebSite wsite = WebSite.ClassMgr.getWebSite(semObjPost.getModel().getName());
    if (wsite == null) {
        return;
    }

    Post post = (Post) semObjPost.getGenericInstance();

    SocialTopic socialTopic = post.getSocialTopic();

    User user = paramRequest.getUser();

    SWBResourceURL actionUrl = paramRequest.getActionUrl();
    actionUrl.setAction("changeSocialTopic");
    actionUrl.setParameter("postUri", post.getURI());
    actionUrl.setParameter("wsite", wsite.getURI());
    actionUrl.setParameter("suri",request.getParameter("suri"));
%>

<div class="swbform swbpopup retema-pop">   

    <p>
        <span>
            <%=paramRequest.getLocaleString("actualTopic")%>:
        </span>

        <%
            if (socialTopic == null) {
                System.out.println("postUri-5");
        %>
        ---           
        <%
        } else {
            System.out.println("postUri-6");
        %>  

        <%=socialTopic.getDisplayTitle(user.getLanguage())%>  

    </p>
    <%
        }
    %>
    <form id="<%=semObjPost.getSemanticClass().getClassId()%>/classbTopicForm" dojoType="dijit.form.Form" class="swbform" method="post" action="<%=actionUrl%>" method="post" onsubmit="submitForm('<%=semObjPost.getSemanticClass().getClassId()%>/classbTopicForm');return false;"> 
        <p>
            <span><%=paramRequest.getLocaleString("chooseTopic")%>:</span>
            <select name="newSocialTopic">
                <option value="none"><%=paramRequest.getLocaleString("none")%></option>
                <%
                    System.out.println("postUri-7");
                    Iterator<SocialTopic> itSocialTopics = SocialTopic.ClassMgr.listSocialTopics(wsite);
                    while (itSocialTopics.hasNext()) {
                        System.out.println("postUri-8");
                        SocialTopic siteSocialTopic = itSocialTopics.next();
                        if ((socialTopic == null || !siteSocialTopic.getURI().equals(socialTopic.getURI()))) {
                %>
                <option value="<%=siteSocialTopic.getURI()%>"><%=siteSocialTopic.getDisplayTitle(user.getLanguage())%></option>
                <%
                } else if (socialTopic != null && siteSocialTopic.getURI().equals(socialTopic.getURI())) {
                %>
                <option value="<%=siteSocialTopic.getURI()%>" selected="true"><%=siteSocialTopic.getDisplayTitle(user.getLanguage())%></option>  
                <%
                        }
                    }
                %> 
            </select>
        </p>
        <button dojoType="dijit.form.Button" type="submit" ><%=paramRequest.getLocaleString("btnSend")%></button>
        <button dojoType="dijit.form.Button" onclick="hideDialog(); return false;"><%=paramRequest.getLocaleString("btnCancel")%></button>
    </form>
</div>




