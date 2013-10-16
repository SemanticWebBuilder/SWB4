<%-- 
    Document   : socialNetReletions
    Created on : 16-oct-2013, 13:29:05
    Author     : jorge.jimenez
--%>

<%@page import="org.semanticwb.social.PostOut"%>
<%@page import="org.semanticwb.model.CalendarRef"%>
<%@page import="org.semanticwb.social.SocialCalendar"%>
<%@page import="org.semanticwb.social.SocialTopic"%>
<%@page import="org.semanticwb.social.SocialPFlowRef"%>
<%@page import="org.semanticwb.social.SocialPFlow"%>
<%@page import="org.semanticwb.social.SocialRuleRef"%>
<%@page import="org.semanticwb.social.SocialRule"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.base.WebSiteBase"%>
<%@page import="org.semanticwb.social.Stream"%>
<%@page import="org.semanticwb.social.base.StreamBase"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.social.SocialNetwork"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<div class="netStreamsRelation">
    <ul>
<%
    User user=paramRequest.getUser();
    String suri=request.getParameter("suri");
    if(suri==null) return;
    SemanticObject semObj=SemanticObject.getSemanticObject(suri);
    WebSite wsite=WebSite.ClassMgr.getWebSite(semObj.getModel().getName()); 
    //Cheking which streams are using this socialNetwork...
    if(semObj.getGenericInstance() instanceof SocialNetwork)
    {
        %>
            <p id="streamName">Streams Relacionados</p>
        <%
        SocialNetwork socialNetwork=(SocialNetwork)semObj.getGenericInstance();
        Iterator<Stream> itStreams=Stream.ClassMgr.listStreamBySocialNetwork(socialNetwork,wsite);
        while(itStreams.hasNext())
        {
            Stream stream=itStreams.next();
            %>
            <li>
                <a href="javascript:parent.addNewTab('<%=stream.getURI()%>','<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/objectTab.jsp','<%=SWBUtils.TEXT.scape4Script(stream.getDisplayTitle(user.getLanguage()))%>');">
                   <%=stream.getTitle()%>
                </a>
            </li>
            <%
        }
   }else if(semObj.getGenericInstance() instanceof SocialRule)
   {
       SocialRule socialRule=(SocialRule)semObj.getGenericInstance();
       %>
            <p id="streamName">Streams Relacionados</p>
       <%
       Iterator <SocialRuleRef> itSocialRuleRefs=socialRule.listSocialRuleRefInvs();
       while(itSocialRuleRefs.hasNext())
       {
           SocialRuleRef socialRuleRef=itSocialRuleRefs.next();
           if(socialRuleRef.isValid())
           {
               Iterator<Stream> itStreams=Stream.ClassMgr.listStreamBySocialRuleRef(socialRuleRef, wsite);
               while(itStreams.hasNext())
               {  
                   Stream stream=itStreams.next();
                %>
                <li>
                    <a href="javascript:parent.addNewTab('<%=stream.getURI()%>','<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/objectTab.jsp','<%=SWBUtils.TEXT.scape4Script(stream.getDisplayTitle(user.getLanguage()))%>');">
                       <%=stream.getTitle()%>
                    </a>
                </li>
                <%
               }
           }    
       }    
   }else if(semObj.getGenericInstance() instanceof SocialPFlow)
   {
       SocialPFlow socialPflow=(SocialPFlow)semObj.getGenericInstance();
       %>
            <p id="streamName">Temas Relacionados</p>
       <%
       Iterator <SocialPFlowRef> itSocialPflowRefs=socialPflow.listSocialPFlowRefInvs();
       while(itSocialPflowRefs.hasNext())
       {
           SocialPFlowRef socialPflowRef=itSocialPflowRefs.next();
           if(socialPflowRef.isValid())
           {
               Iterator<SocialTopic> itStreams=SocialTopic.ClassMgr.listSocialTopicByPFlowRef(socialPflowRef, wsite);
               while(itStreams.hasNext())
               {  
                   SocialTopic socialTopic=itStreams.next();
                %>
                <li>
                    <a href="javascript:parent.addNewTab('<%=socialTopic.getURI()%>','<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/objectTab.jsp','<%=SWBUtils.TEXT.scape4Script(socialTopic.getDisplayTitle(user.getLanguage()))%>');">
                       <%=socialTopic.getTitle()%>
                    </a>
                </li>
                <%
               }
           }    
       }    
   }else if(semObj.getGenericInstance() instanceof SocialCalendar)
   {
       SocialCalendar socialCalendar=(SocialCalendar)semObj.getGenericInstance();
       %>
            <p id="streamName">Mensajes Relacionados</p>
       <%
       Iterator <CalendarRef> itSocialPflowRefs=socialCalendar.listCalendarRefInvs();
       while(itSocialPflowRefs.hasNext())
       {
           CalendarRef calendarRef=itSocialPflowRefs.next();
           if(calendarRef.isValid())
           { 
               Iterator<PostOut> itPostOuts=PostOut.ClassMgr.listPostOutByCalendarRef(calendarRef, wsite);
               while(itPostOuts.hasNext())
               {  
                   PostOut postOut=itPostOuts.next();
                %>
                <li>
                    <a href="javascript:parent.addNewTab('<%=postOut.getURI()%>','<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/objectTab.jsp','<%=SWBUtils.TEXT.scape4Script(postOut.getId())%>');"> 
                        <%=SWBUtils.TEXT.scape4Script(postOut.getMsg_Text()!=null&&postOut.getMsg_Text().length()>100?postOut.getMsg_Text().substring(0, 100):postOut.getMsg_Text()!=null?postOut.getMsg_Text():postOut.getId())%>
                    </a>
                    <%if(postOut.getSocialTopic()!=null){%>
                        (Tema:
                    <a href="javascript:parent.addNewTab('<%=postOut.getSocialTopic().getURI()%>','<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/objectTab.jsp','<%=SWBUtils.TEXT.scape4Script(postOut.getSocialTopic().getDisplayTitle(user.getLanguage()))%>');">
                            <%=postOut.getSocialTopic().getDisplayTitle(user.getLanguage())%>
                    </a>
                        )
                    <%}%>
                </li>
                <%
               }
           }    
       }    
   }

%>
    </ul>
</div>