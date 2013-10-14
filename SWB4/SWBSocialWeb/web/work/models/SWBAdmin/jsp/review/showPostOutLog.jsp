<%-- 
    Document   : showPostOutLog
    Created on : 10-jul-2013, 13:06:01
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
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
    User user=paramRequest.getUser(); 
    if(request.getAttribute("postOut")==null) return;
    
    SemanticObject semObj=(SemanticObject)request.getAttribute("postOut");
    if(semObj==null) return; 
    WebSite wsite=WebSite.ClassMgr.getWebSite(semObj.getModel().getName());
    if(wsite==null) return;
    
    PostOut postOut=(PostOut)semObj.getGenericInstance();
    %>
      <div class="swbform" style="width: 500px">
        <table style="width: 100%">
            <thead>
                <tr>
                    <th><%=paramRequest.getLocaleString("socialNetwork")%></th>
                    <th><%=paramRequest.getLocaleString("idOnSocialNet")%></th>
                    <th><%=paramRequest.getLocaleString("status")%></th>
                    <th><%=paramRequest.getLocaleString("errorMsg")%></th>
                    <th><%=SWBUtils.TEXT.encode(paramRequest.getLocaleString("action"),"utf-8")%></th>
                </tr>
            </thead>
    <%
    
    Iterator<PostOutNet> itPostOutNets=PostOutNet.ClassMgr.listPostOutNetBySocialPost(postOut, wsite);
    while(itPostOutNets.hasNext())
    {
        PostOutNet postOutNet=itPostOutNets.next();
        %>
            <tr>
                <td>
                    <%=postOutNet.getSocialNetwork().getDisplayTitle(user.getLanguage())%>
                </td>
                <td>
                    <%=postOutNet.getPo_socialNetMsgID()!=null?postOutNet.getPo_socialNetMsgID():""%>
                </td>
                <td>
                <%
                    if(postOutNet.getStatus()==0)
                    {
                        if(SocialLoader.getPostMonitor()!=null)
                        {
                            PostMonitor postMonitor=SocialLoader.getPostMonitor();
                            if(postMonitor.hasPostOutNet(postOutNet))
                            {
                                %>
                                    <%=paramRequest.getLocaleString("inProcess")%>
                                <%
                            }else{
                                %>
                                    <%=paramRequest.getLocaleString("notPublished")%>
                                <%
                            }
                        }
                    }else
                    {
                        %>
                            <%=paramRequest.getLocaleString("published")%>
                        <%
                    }
                %>
                </td>
                <td>
                    <%=postOutNet.getError()!=null?postOutNet.getError():"---"%>
                </td>
                <td>
                    <%
                        
                        if(postOutNet.getError()!=null)
                        {
                            SWBResourceURL actionUrl=paramRequest.getActionUrl();
                            actionUrl.setAction("publicByPostOut");
                            actionUrl.setParameter("postOutUri", postOutNet.getURI());
                            %>
                                <a href="#" onclick="submitUrl('<%=actionUrl%>',this); return false;">Publicar</a>
                            <%
                        }
                    %>
                </td>
            </tr>
        <%
    }
    
 %>
        </table>
      </div>