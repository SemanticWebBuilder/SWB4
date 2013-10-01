<%-- 
    Document   : userHistory
    Created on : 26-jun-2013, 11:48:57
    Author     : jorge.jimenez
--%>


<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.social.util.*"%>
<%@page import="java.util.*"%>
<%@page import="twitter4j.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
    String suri=(String)request.getAttribute("suri");
    if(suri==null) return; 
    org.semanticwb.model.User user = paramRequest.getUser();
    if (request.getAttribute("swbSocialUser") == null) {
        return;
    }

    SemanticObject semObj = (SemanticObject) request.getAttribute("swbSocialUser");
    if (semObj == null) {
        return;
    }

    WebSite wsite = WebSite.ClassMgr.getWebSite(semObj.getModel().getName());
    if (wsite == null) {
        return;
    }

    SocialNetworkUser socialNetUser = (SocialNetworkUser) semObj.getGenericInstance();
    //Un mensaje de entrada siempre debe estar atachado a un usuario de la red social de la que proviene, de esta manera, es como desde swbsocial
    //se respondería a un mensaje
    if (socialNetUser == null) {
        return;
    }

    String userPhoto = socialNetUser.getSnu_photoUrl(); //Sacar la foto de la redSocial;
    if (userPhoto == null) {
        userPhoto = "/swbadmin/css/images/profileDefImg.jpg";
    }

%>

<div class="swbform swbpopup usr-pop">
    <div class="perfilgral">
        <div class="perfil">
            <img src="<%=userPhoto%>"/>        
            <p>
                <%=SWBUtils.TEXT.encode(socialNetUser.getSnu_name(), "utf-8")%>
                <!-- <%=socialNetUser.getSnu_SocialNetworkObj() != null ? socialNetUser.getSnu_SocialNetworkObj().getId() : "---"%>-->
            </p>
        </div>
        <p><strong><%=socialNetUser.getFollowers()%></strong> <%=SWBSocialUtil.Util.getStringFromGenericLocale("followers", user.getLanguage())%></p>
        <p><strong><%=socialNetUser.getFriends()%></strong> <%=SWBSocialUtil.Util.getStringFromGenericLocale("friends", user.getLanguage())%></p>
        <p><strong><%=socialNetUser.getSnu_klout()%></strong> Klout</p>
        <p>   
            <%
                long cont=0;
                SemanticObject semObjTab=SemanticObject.getSemanticObject(suri);
                if(semObjTab.getGenericInstance() instanceof Stream) 
                {
                    Stream stream=(Stream)semObjTab.getGenericInstance();
                    Iterator<PostIn> itPostIns = socialNetUser.listPostInInvs();
                    while(itPostIns.hasNext())
                    {
                        PostIn postIn=itPostIns.next();
                        if(postIn.getPostInStream().getURI().equals(stream.getURI()))
                        {
                            cont++; 
                        }
                    }                    
                }else if(semObjTab.getGenericInstance() instanceof SocialTopic) 
                {
                    SocialTopic socialTopic=(SocialTopic)semObjTab.getGenericInstance();
                    Iterator<PostIn> itPostIns = socialNetUser.listPostInInvs();
                    while(itPostIns.hasNext())
                    {
                        PostIn postIn=itPostIns.next();
                        if(postIn.getSocialTopic().getURI().equals(socialTopic.getURI()))
                        {
                            cont++; 
                        }
                    }                    
                }
                SWBResourceURL url = paramRequest.getRenderUrl();
                url.setMode(SWBResourceURL.Action_EDIT);
                url.setParameter("swbSocialUser", socialNetUser.getId());
                url.setParameter("dialog", "close");
                url.setParameter("suri", (String) request.getAttribute("suri"));
                url.setParameter("reloadTap", "true");
            %>
            <strong><a href="#" onclick="submitUrl('<%=url.setParameter("swbSocialUser", socialNetUser.getId())%>',this); return false;"><%=cont%></a></strong>  Mensajes de entrada
        </p>
        <div class="clear"></div>
    </div>
</div>



