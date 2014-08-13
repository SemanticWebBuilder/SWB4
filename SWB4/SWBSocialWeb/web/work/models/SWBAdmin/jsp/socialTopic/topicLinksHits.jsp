<%-- 
    Document   : topicLinksHits
    Created on : 29-abr-2014, 16:02:08
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
<%@page import="org.semanticwb.portal.SWBFormMgr" %>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>


<div class="swbform">
 <fieldset>
<%
    String suri=request.getParameter("suri");
    User user=paramRequest.getUser();
    try{
        SemanticObject semObj=SemanticObject.createSemanticObject(suri);
        SocialTopic socialTopic=(SocialTopic)semObj.createGenericInstance();
        Iterator<PostOut> itPostOut=socialTopic.listPosts();
        while(itPostOut.hasNext())
        {
            PostOut postOut=itPostOut.next();
            Iterator<PostOutLinksHits> itPostOutsLinkHits=PostOutLinksHits.ClassMgr.listPostOutLinksHitsByPostOut(postOut);
            if(itPostOutsLinkHits.hasNext())
            {
                SWBResourceURL urlPrev = paramRequest.getRenderUrl().setMode("preview").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postOut.getURI());
                String msgText="";
                if (postOut.getMsg_Text() != null) {
                    msgText = SWBUtils.TEXT.cropText(SWBUtils.TEXT.scape4Script(postOut.getMsg_Text()), 25);
                    msgText = msgText.replaceAll("\n", " ");
                }
                %>
                   <table class="postOut">
                    <tr class="msg">
                        <td>
                            <span><a href="#" title="Vista del mensaje" onclick="showDialog('<%=urlPrev%>','Vista del mensaje'); return false;"><%=msgText%></a></span>
                        </td>
                    </tr>
                <%
                HashMap<String, ArrayList> hmap=new HashMap();
                while(itPostOutsLinkHits.hasNext())
                {
                    PostOutLinksHits postOutLinksHit=itPostOutsLinkHits.next();
                    String targetUrl=postOutLinksHit.getTargetUrl();
                    if(!hmap.containsKey(targetUrl))
                    {
                       ArrayList<PostOutLinksHits> aPostOutLinkHits=new ArrayList();    
                       aPostOutLinkHits.add(postOutLinksHit);
                       hmap.put(targetUrl, aPostOutLinkHits);
                    }else{
                        ArrayList<PostOutLinksHits> aPostOutLinkHits=(ArrayList)hmap.get(targetUrl);
                        aPostOutLinkHits.add(postOutLinksHit);
                        hmap.remove(targetUrl);
                        hmap.put(targetUrl, aPostOutLinkHits);
                    }
                }
                SWBResourceURL urlpostOutLinksHits = paramRequest.getRenderUrl().setMode("linkHitsData").setCallMethod(SWBResourceURL.Call_DIRECT); 
                Iterator<String> itLinkHits=hmap.keySet().iterator();
                while(itLinkHits.hasNext())
                {
                    String strLink=itLinkHits.next();
                    Iterator<PostOutLinksHits> itHits=hmap.get(strLink).iterator(); 
                    %>
                    <tr>
                        <td class="link">
                            <span><a target="_blank" href="<%=strLink%>"><%=strLink%></a></span>
                        </td>
                    </tr>
                        <%
                            while(itHits.hasNext())
                            {
                                PostOutLinksHits postOutLinksHit=itHits.next();
                                %>
                                <tr class="socialNetHits">
                                    <td>
                                        <%=postOutLinksHit.getSocialNet().getDisplayTitle(user.getLanguage())%>
                                    </td>
                                    <td>
                                        <%if(postOutLinksHit.getPol_hits()>0){%>                            
                                            <a href="#" title="Mostrar" onclick="showDialog('<%=urlpostOutLinksHits.setParameter("uri", postOutLinksHit.getURI())%>','Datos de Hits de Link'); return false;"><%=postOutLinksHit.getPol_hits()%></a>
                                        <%}else{%>
                                            <%=postOutLinksHit.getPol_hits()%>
                                        <%}%>
                                    </td>
                                </tr>
                                <%
                            }
                        %>
                    <%
                }
                %>  
                   </table>
                <%
            }
        }
    }catch(Exception e){
        e.printStackTrace();
    }
%>
 </fieldset>
</div>

