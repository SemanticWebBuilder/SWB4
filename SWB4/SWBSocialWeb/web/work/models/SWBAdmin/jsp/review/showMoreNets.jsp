<%-- 
    Document   : showMoreNets
    Created on : 15-oct-2013, 15:53:50
    Author     : jorge.jimenez
--%>

<%-- 
    Document   : showPostIn
    Created on : 03-jun-2013, 13:01:48
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
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<ul class="showMoreNets">
<%
    org.semanticwb.model.User user = paramRequest.getUser();
    if (request.getAttribute("postOut") == null) {
        return;
    }

    SemanticObject semObj = (SemanticObject) request.getAttribute("postOut");
    if (semObj == null) {
        return;
    }
    WebSite wsite = WebSite.ClassMgr.getWebSite(semObj.getModel().getName());
    if (wsite == null) {
        return;
    }

    PostOut postOut = (PostOut) semObj.getGenericInstance();
    
    int cont=1;
    String nets="";
    Iterator<SocialNetwork> itPostSocialNets = postOut.listSocialNetworks();
    while (itPostSocialNets.hasNext()) {
        SocialNetwork socialNet = itPostSocialNets.next();
        //System.out.println("socialNet-1:"+socialNet);
        String sSocialNet = socialNet.getDisplayTitle(user.getLanguage());
        //System.out.println("socialNet-2:"+sSocialNet);
        if (sSocialNet != null && sSocialNet.trim().length() > 0) {
            //System.out.println("socialNet-3:"+sSocialNet);
            //Sacar privacidad
            String sPrivacy=null;
            //Si es necesario, cambiar esto por querys del Jei despues.
            Iterator<PostOutPrivacyRelation> itpostOutPriRel=PostOutPrivacyRelation.ClassMgr.listPostOutPrivacyRelationByPopr_postOut(postOut, wsite);
            while(itpostOutPriRel.hasNext())
            {
                PostOutPrivacyRelation poPrivRel=itpostOutPriRel.next();
                if(poPrivRel.getPopr_socialNetwork().getURI().equals(socialNet.getURI()))
                {
                    sPrivacy=poPrivRel.getPopr_privacy().getTitle(user.getLanguage());
                }
            }
            if(sPrivacy==null)
            {
                Iterator<PostOutNet> itpostOutNet=PostOutNet.ClassMgr.listPostOutNetBySocialPost(postOut, wsite);
                while(itpostOutNet.hasNext())
                {
                    PostOutNet postOutnet=itpostOutNet.next();
                    if(postOutnet.getSocialNetwork().getURI().equals(socialNet.getURI()) && postOutnet.getPo_privacy()!=null)
                    {
                        sPrivacy=postOutnet.getPo_privacy().getTitle(user.getLanguage());
                    }
                }
            }
            if(sPrivacy==null) sPrivacy=paramRequest.getLocaleString("public");

            //Termina privacidad
            if (cont==1) {
                nets = "<li>" + sSocialNet+"("+sPrivacy+")"+"</li>";
                cont++;
            } else {
                nets += "<li>"+ sSocialNet+"("+sPrivacy+")"+"</li>";
            }
        }
    }
%>
    <%=nets%>
</ul>