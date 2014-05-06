<%-- 
    Document   : showPostOutLinksHist
    Created on : 24-abr-2014, 18:43:49
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
<%@page import="java.util.*"%>

<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%
    String postOutUri=request.getParameter("postUri");
    if(postOutUri!=null)
    {
        try{
            User user=paramRequest.getUser();
            PostOut postOut=(PostOut)SemanticObject.createSemanticObject(postOutUri).createGenericInstance();
            HashMap<String, ArrayList> hmap=new HashMap();
            Iterator<PostOutLinksHits> itPostOutsLinkHits=PostOutLinksHits.ClassMgr.listPostOutLinksHitsByPostOut(postOut);
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
            %>
           <div class="swbform">
            <fieldset>
            <%
            SWBResourceURL urlpostOutLinksHits = paramRequest.getRenderUrl().setMode("linkHitsData").setCallMethod(SWBResourceURL.Call_DIRECT); 
            Iterator<String> itLinkHits=hmap.keySet().iterator();
            while(itLinkHits.hasNext())
            {
                String strLink=itLinkHits.next();
                Iterator<PostOutLinksHits> itHits=hmap.get(strLink).iterator(); 
                %>
                <table class="tabla1">
                <thead>
                <tr>
                    <th class="accion" colspan="2">
                        <span><%=strLink%></span>
                    </th>
                </tr>
                </thead>
                <body>
                    <%
                        while(itHits.hasNext())
                        {
                            PostOutLinksHits postOutLinksHit=itHits.next();
                            %>
                            <tr>
                                <td>
                                    <%=postOutLinksHit.getSocialNet().getDisplayTitle(user.getLanguage())%>
                                </td>
                                <td>
                                    <%
                                    if(postOutLinksHit.getPol_hits()>0){%>
                                    <a href="#" title="Mostrar" onclick="showDialog('<%=urlpostOutLinksHits.setParameter("uri", postOutLinksHit.getURI())%>','Datos de Hits de Link'); return false;">
                                    <%}%>
                                        <%=postOutLinksHit.getPol_hits()%>
                                    <%if(postOutLinksHit.getPol_hits()>0){%></a><%}%>
                                </td>
                            </tr>
                            <%
                        }
                    %>
                </body>
                </table>
                <%
            }
            %>
            </fieldset>
           </div>
            <%
        }catch(Exception e)
        {
            e.printStackTrace();
        }
       
    }
%>