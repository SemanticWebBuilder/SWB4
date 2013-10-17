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
    System.out.println("suri EN JSP:"+request.getParameter("suri")); 
    String suri=request.getParameter("suri");
    if(suri==null) return;
    User user=paramRequest.getUser(); 
    if(request.getAttribute("postOut")==null) return;
    System.out.println("postOut EN JSP:"+request.getParameter("postOut")); 
    
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
                    <th><%=paramRequest.getLocaleString("actions")%></th>
                    <th><%=paramRequest.getLocaleString("socialNetwork")%></th>
                    <th><%=paramRequest.getLocaleString("idOnSocialNet")%></th>
                    <th><%=paramRequest.getLocaleString("status")%></th>
                    <th><%=paramRequest.getLocaleString("errorMsg")%></th>
                    <th><%=SWBUtils.TEXT.encode(paramRequest.getLocaleString("action"),"utf-8")%></th>
                </tr>
            </thead>
    <%
    boolean entersToWhile=false; 
    Iterator<PostOutNet> itPostOutNets=PostOutNet.ClassMgr.listPostOutNetBySocialPost(postOut, wsite);
    while(itPostOutNets.hasNext())
    {
        entersToWhile=true;
        PostOutNet postOutNet=itPostOutNets.next();
        %>
            <tr>
                <td>
                <%
                if(postOutNet.getError()!=null)
                {
                    SWBResourceURL urlr = paramRequest.getActionUrl();
                    urlr.setParameter("suri", suri);
                    urlr.setParameter("postOut", postOut.getURI());
                    urlr.setParameter("postOutNetUri", postOutNet.getURI());
                    urlr.setAction("removePostOutNet");
                    
                    String msgText = postOutNet.getURI();
                    if (postOutNet.getError() != null) {
                        msgText = SWBUtils.TEXT.scape4Script(postOutNet.getError());
                        msgText = SWBSocialUtil.Util.replaceSpecialCharacters(msgText, false);
                    }
                    %>
                    
                        <a href="#" title="<%=paramRequest.getLocaleString("remove")%>" class="eliminar" onclick="if(confirm('<%=paramRequest.getLocaleString("confirm_remove")%>  <%=msgText%> ?')){ submitUrl('<%=urlr%>',this); } else { return false;}">Eliminar</a>
                <%
                }
                %>
                </td>
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
    if(!entersToWhile)
    {
        %>
        <script type="javascript">
            hideDialog();
            reloadTab('<%=suri%>'); 
        </script>
        <%
    }
 %>
        </table>
      </div>