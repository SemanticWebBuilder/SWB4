<%-- 
    Document   : showUserFavorites
    Created on : 01-oct-2013, 15:27:56
    Author     : jorge.jimenez
--%>

<%@page import="org.semanticwb.social.util.SocialLoader"%>
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="org.json.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.social.SocialFlow.SocialPFlowMgr"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>


<%
   User user=paramRequest.getUser();
   UserFavorite fav=user.getUserFavorite();
    if(fav!=null)
    {
        %>
        <div id="userFavorites">
        <ul>
        <%
        Iterator<SemanticObject> it=SWBComparator.sortSermanticObjects(fav.listObjects());
        while(it.hasNext())
        {
            SemanticObject obj=it.next();
            if(obj.getGenericInstance() instanceof Stream) 
            {
                Stream stream=(Stream)obj.getGenericInstance();
                %>
                <li class="stream">
                    <a href="javascript:parent.addNewTab('<%=stream.getURI()%>','<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/objectTab.jsp','<%=SWBUtils.TEXT.scape4Script(stream.getDisplayTitle(user.getLanguage()))%>');">
                          <%=stream.getTitle()%>
                    </a>
                </li>
                <%
            }else if(obj.getGenericInstance() instanceof SocialTopic)  
            {
                SocialTopic socialTopic=(SocialTopic)obj.getGenericInstance();
                %>
                <li class="socialTopic">
                    <a href="javascript:parent.addNewTab('<%=socialTopic.getURI()%>','<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/objectTab.jsp','<%=SWBUtils.TEXT.scape4Script(socialTopic.getDisplayTitle(user.getLanguage()))%>');">
                        <%=socialTopic.getTitle()%>
                    </a>
                </li>
                <%
            }else if(obj.getGenericInstance() instanceof Rss) 
            {
                Rss rss=(Rss)obj.getGenericInstance();
                %>
                <li class="rss">
                    <a href="javascript:parent.addNewTab('<%=rss.getURI()%>','<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/objectTab.jsp','<%=SWBUtils.TEXT.scape4Script(rss.getDisplayTitle(user.getLanguage()))%>');">
                        <%=rss.getTitle()%>
                    </a>
                </li>
                <%
            }else if(obj.getGenericInstance() instanceof SocialNetwork)  
            { 
                SocialNetwork socialNet=(SocialNetwork)obj.getGenericInstance();
                %>
                <li class="socialNetwork">
                    <a href="javascript:parent.addNewTab('<%=socialNet.getURI()%>','<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/objectTab.jsp','<%=SWBUtils.TEXT.scape4Script(socialNet.getDisplayTitle(user.getLanguage()))%>');">
                        <%=socialNet.getTitle()%>
                    </a>
                </li>
                <%
            }
        }
        %>
            </ul>
            </div>
        <%
    }
   
%>