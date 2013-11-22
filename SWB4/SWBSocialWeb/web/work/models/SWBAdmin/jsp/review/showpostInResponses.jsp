<%-- 
    Document   : showpostInResponses
    Created on : 11-jul-2013, 11:02:54
    Author     : jorge.jimenez
--%>

<%@page import="org.semanticwb.social.admin.resources.util.SWBSocialResUtil"%>
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
<%@page import="org.semanticwb.social.SocialFlow.SocialPFlowMgr"%>
<%@page import="java.util.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="static org.semanticwb.social.admin.resources.SocialTopicInBox.*"%>

<%
    org.semanticwb.model.User user = paramRequest.getUser();
    //System.out.println("ShowPostInResponses/postInJsp:" + request.getAttribute("postUri"));
    if (request.getAttribute("postUri") == null) {
        return;
    }

    SemanticObject semObj = (SemanticObject) request.getAttribute("postUri");
    if (semObj == null) {
        return;
    }

    WebSite wsite = WebSite.ClassMgr.getWebSite(semObj.getModel().getName());
    if (wsite == null) {
        return;
    }

    PostIn postIn = (PostIn) semObj.getGenericInstance();

    String classifyBySentiment = SWBSocialUtil.Util.getModelPropertyValue(wsite, SWBSocialUtil.CLASSIFYSENTMGS_PROPNAME);
%>
<div class="swbform swbpopup answver-pop" style="width: 900px">
    <table class="tabla1">
        <thead>
            <tr>
                <th>
                    <span>
                        <%=SWBSocialResUtil.Util.getStringFromGenericLocale("previewdocument", user.getLanguage())%>
                    </span>
                </th>
                <th>
                    <span>
                        <%=SWBSocialResUtil.Util.getStringFromGenericLocale("message", user.getLanguage())%>
                    </span>
                </th>
                <th>
                    <span>
                        <%=SWBSocialResUtil.Util.getStringFromGenericLocale("postType", user.getLanguage())%>
                    </span>
                </th>
                <th>
                    <span>
                        <%=SWBSocialResUtil.Util.getStringFromGenericLocale("network", user.getLanguage())%>
                    </span>
                </th>
                <th>
                    <span>
                        <%=SWBSocialResUtil.Util.getStringFromGenericLocale("created", user.getLanguage())%>
                    </span>
                </th>
                <th>
                    <span>
                        <%=SWBSocialResUtil.Util.getStringFromGenericLocale("updated", user.getLanguage())%>
                    </span>
                </th>
                <%
                    if (classifyBySentiment != null && classifyBySentiment.equalsIgnoreCase("true")) {
                %>

                <th>
                    <span>
                        <%=SWBSocialResUtil.Util.getStringFromGenericLocale("sentiment", user.getLanguage())%>
                    </span>
                </th>
                <th>
                    <span>
                        <%=SWBSocialResUtil.Util.getStringFromGenericLocale("intensity", user.getLanguage())%>
                    </span>
                </th>
                <%
                    }
                %>
                <th>
                    <span>
                        <%=SWBSocialResUtil.Util.getStringFromGenericLocale("status", user.getLanguage())%>
                    </span>
                </th>
            </tr>
        </thead>
            <%
            SocialPFlowMgr pfmgr = SocialLoader.getPFlowManager();
            Iterator<PostOut> itPostOuts = postIn.listpostOutResponseInvs();
            while (itPostOuts.hasNext()) 
            {
                PostOut postOut = itPostOuts.next();
                boolean isInFlow = pfmgr.isInFlow(postOut);
                boolean isAuthorized = false;
                if(isInFlow){ 
                    isAuthorized = pfmgr.isAuthorized(postOut);
                }
                boolean needAuthorization = pfmgr.needAnAuthorization(postOut);
            %>
            <tbody>
            <tr>
                <td>
                    <%
                        SWBResourceURL urlPrev = paramRequest.getRenderUrl().setMode(Mode_SHOWPOSTOUT).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postOut", postOut.getURI());
                    %>     
                    <a href="#"  class="ver" title="<%=SWBSocialResUtil.Util.getStringFromGenericLocale("previewdocument", user.getLanguage())%>" onclick="showDialog('<%=urlPrev%>','<%=SWBSocialResUtil.Util.getStringFromGenericLocale("previewdocument", user.getLanguage())%>'); 
                        return false;"></a>

                </td>
                <td>
                    <p>
                    <%=postOut.getMsg_Text() != null ? postOut.getMsg_Text() : "---"%>
                    </p>
                </td>
                <td>
                    <%
                        if (postOut instanceof Message) {
                    %>
                    <!--<%=SWBSocialResUtil.Util.getStringFromGenericLocale("message", user.getLanguage())%>-->
                    <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/tipo-txt.jpg" border="0" width="25" height="25">
                    <%
                    } else if (postOut instanceof Photo) {
                    %>    
                    <!--<%=SWBSocialResUtil.Util.getStringFromGenericLocale("photo", user.getLanguage())%>-->
                    <img src="<%=SWBPlatform.getContextPath()%>/swbadmin/css/images/tipo-img.jpg" width="25" height="25">
                    <%
                    } else if (postOut instanceof Video) {
                    %>
                    <!--<%=SWBSocialResUtil.Util.getStringFromGenericLocale("video", user.getLanguage())%> -->
                    <img src="<%=SWBPlatform.getContextPath()%>/swbadmin/css/images/tipo-vid.jpg" border="0" width="25" height="25">
                    <%
                        }
                    %>    
                </td>
                <td>
                    <%
                        String nets = "---";
                        boolean firstTime = true;
                        Iterator<SocialNetwork> itPostSocialNets = postOut.listSocialNetworks();
                        while (itPostSocialNets.hasNext()) {
                            SocialNetwork socialNet = itPostSocialNets.next();
                            //System.out.println("socialNet-1:"+socialNet);
                            String sSocialNet = socialNet.getDisplayTitle(user.getLanguage());
                            //System.out.println("socialNet-2:"+sSocialNet);
                            if (sSocialNet != null && sSocialNet.trim().length() > 0) {
                                //System.out.println("socialNet-3:"+sSocialNet);
                                if (firstTime) {
                                    nets = "" + sSocialNet;
                                    firstTime = false;
                                } else {
                                    nets += "|" + sSocialNet;
                                }
                            }
                        }
                    %>
                    <%=nets%>
                </td>
                <td>
                    <%=SWBUtils.TEXT.encode(SWBUtils.TEXT.getTimeAgo(postOut.getCreated(), user.getLanguage()), "utf8")%>
                </td>
                <td>
                    <%=SWBUtils.TEXT.encode(SWBUtils.TEXT.getTimeAgo(postOut.getUpdated(), user.getLanguage()), "utf8")%> 
                </td>
                <%
                    if (classifyBySentiment != null && classifyBySentiment.equalsIgnoreCase("true")) {
                        //Sentiment
%>
                <td align="center">
                    <%
                        if (postOut.getPostSentimentalType() == 0) {
                    %>        
                    ---
                    <%                    } else if (postOut.getPostSentimentalType() == 1) {
                    %>        
                    <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/pos.png"/>
                    <%
                    } else if (postOut.getPostSentimentalType() == 2) {
                    %>        
                    <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/neg.png"/> 
                    <%
                        }
                    %>    
                </td>
                <!--Intensity-->
                <!--<td align="center">
                    <%=postOut.getPostIntesityType() == 0 ? SWBSocialResUtil.Util.getStringFromGenericLocale("low", user.getLanguage()) : postOut.getPostIntesityType() == 1 ? SWBSocialResUtil.Util.getStringFromGenericLocale("medium", user.getLanguage()) : postOut.getPostIntesityType() == 2 ? SWBSocialResUtil.Util.getStringFromGenericLocale("high", user.getLanguage()) : "---"%>
                </td>
                <td>-->
                    <td>
                    <%
                        if (postOut.getPostIntesityType() == 0) {
                    %>
                    <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/ibaja.png" width="25" height="25" alt="<%=SWBSocialResUtil.Util.getStringFromGenericLocale("low", user.getLanguage())%>">
                    <%        } else if (postOut.getPostIntesityType() == 1) {
                    %>    
                    <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/imedia.png" width="25" height="25" alt="<%=SWBSocialResUtil.Util.getStringFromGenericLocale("medium", user.getLanguage())%>">
                    <%
                    } else if (postOut.getPostIntesityType() == 2) {
                    %>
                    <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/ialta.png" width="25" height="25" alt="<%=SWBSocialResUtil.Util.getStringFromGenericLocale("high", user.getLanguage())%>">
                    <%
                    } else {
                    %>
                    ----
                    <%}%>
                    </td>
                <!--</td> -->
                <%
                    }
                %>
                <td>
                    <%
                        if (!postOut.isPublished()) 
                        {
                            String firstError = null;
                            boolean postOutwithPostOutNets = false;
                            boolean someOneIsNotPublished = false;
                            Iterator<PostOutNet> itPostOutNets = PostOutNet.ClassMgr.listPostOutNetBySocialPost(postOut, wsite);
                            while (itPostOutNets.hasNext()) {
                                PostOutNet postOutNet = itPostOutNets.next();
                                postOutwithPostOutNets = true;
                                if (postOutNet.getStatus() == 0) {
                                    someOneIsNotPublished = true;
                                    if (postOutNet.getError() != null) {
                                        firstError = postOutNet.getError();
                                        break;
                                    }
                                }
                            }
                            
                            //Si todos los PostOutNet referentes al PostOut estan con estatus de 1 o simplemente diferente de 0, quiere decir que ya estan publicados, 
                            //probablente se revisaron desde el MonitorMgr y en el metodo isPublished de c/red social de tipo MonitorAble se reviso el estatus en la red socal
                            // y la misma respondio que ya estaba publicado, por lo cual se le colocó en dicho metodo el estatus 1 (publicado) al PostOutNet de dicho PostOut,
                            //por lo tanto, ya podemos aqui poner el estatus de dicho PostOut como publicado en todas las redes sociales a las que se envíó, esto lo hacemos solo
                            //con colocar la porpiedad published del mismo=true, de esta manera la proxima vez entrara al if de los publicados y ya no se revisara en sus PostOutNets.


                            //Esto no es cierto, puede que si el flujo no manda a publicar durectamente, aun no haya ningun PostOutNet para un PostOut, y aunque no se haya enviado 
                            //a publicar aun, con la siguiente condición va a decir que ya esta publicado, revisar mañana, ya que ahorita ya estoy cansado.
                            if (!isInFlow && postOutwithPostOutNets && !someOneIsNotPublished) //Se supone que por lo menos, hay publicado un PostOutNet del Post                         
                            {
                                postOut.setPublished(true);
                            %>       
                            <%=SWBSocialResUtil.Util.getStringFromGenericLocale("published", user.getLanguage())%>
                            <%
                            } else 
                            {
                                //System.out.println("postOut:" + postOut + ",status:" + postOut.getPflowInstance());                        
                                if (!needAuthorization || postOut.getPflowInstance().getStatus() == 3) 
                                {
                                    if (someOneIsNotPublished) 
                                    {
                                        if (firstError != null) 
                                        {
                                            %>   
                                            <%=SWBUtils.TEXT.encode(firstError, "utf-8")%> 
                                            <%
                                        } else 
                                        {
                                        %>   
                                            <%=SWBSocialResUtil.Util.getStringFromGenericLocale("toReview", user.getLanguage())%> 
                                            <%
                                        }
                                    } else if (isInFlow && needAuthorization && postOut.getPflowInstance() != null && postOut.getPflowInstance().getStatus() == 3) 
                                    {
                                        if(postOut.getFastCalendar()!=null)
                                        {%>
                                            <%=SWBSocialResUtil.Util.getStringFromGenericLocale("toFinishFastCalendar", user.getLanguage())%> 
                                        <%    
                                        }else{
                                            %>    
                                                 <%=SWBSocialResUtil.Util.getStringFromGenericLocale("publish", user.getLanguage())%> 
                                            <%
                                        }
                                    } else if (!isInFlow && !needAuthorization && !postOutwithPostOutNets)
                                    {
                                        if(postOut.getFastCalendar()!=null)
                                        {
                                        %>
                                            <%=SWBSocialResUtil.Util.getStringFromGenericLocale("toFinishFastCalendar", user.getLanguage())%> 
                                        <%    
                                        }else
                                        {
                                            %>
                                                <%=SWBSocialResUtil.Util.getStringFromGenericLocale("publishing", user.getLanguage())%> 
                                            <%
                                        }
                                    }else 
                                    {
                                        if(postOut.getFastCalendar()!=null)
                                        {
                                            %>
                                                <%=SWBSocialResUtil.Util.getStringFromGenericLocale("toFinishFastCalendar", user.getLanguage())%> 
                                            <%
                                        }else{
                                            %>
                                                <%=SWBSocialResUtil.Util.getStringFromGenericLocale("publish", user.getLanguage())%> 
                                            <%
                                        }
                                    }
                                }else 
                                    {    //El PostOut ya se envío
                                        if (!isInFlow && needAuthorization && !isAuthorized) 
                                        {
                                            String sFlowRejected = "---";
                                            if (postOut.getPflowInstance() != null && postOut.getPflowInstance().getPflow() != null) {
                                                sFlowRejected = postOut.getPflowInstance().getPflow().getDisplayTitle(user.getLanguage());
                                            }
                                            %>    
                                            <%=SWBSocialResUtil.Util.getStringFromGenericLocale("rejected", user.getLanguage())%>(<%=sFlowRejected%>)
                                            <%
                                        } else if (isInFlow && needAuthorization && !isAuthorized) 
                                        {
                                            %>    
                                            <%=SWBSocialResUtil.Util.getStringFromGenericLocale("inFlow", user.getLanguage())%>(<%=postOut.getPflowInstance().getPflow().getDisplayTitle(user.getLanguage())%>)
                                            <%
                                        }
                                    }
                            }
                        } else 
                        {
                                %>
                                <%=SWBSocialResUtil.Util.getStringFromGenericLocale("published", user.getLanguage())%>
                                <%
                        }
                    %>
                </td>
            </tr>
            <%
                }
            %>             
    </tbody>
    </table>
</div>