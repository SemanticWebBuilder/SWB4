<%-- 
    Document   : edit
    Created on : 10-ene-2013, 12:16:11
    Author     : carlos.ramos
--%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.social.base.SocialSiteBase"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.social.Oauthable"%>
<%@page import="static org.semanticwb.social.Oauthable.*" %>
<%@page import="org.semanticwb.social.components.resources.SocialWebResource"%>
<%@page import="static org.semanticwb.social.components.resources.SocialWebResource.*"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.social.SocialSite"%>
<%@page import="org.semanticwb.social.SocialNetwork"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.model.GenericObject"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script type="text/javascript">
<!--
    dojo.require("dijit.layout.ContentPane");
    dojo.require("dijit.form.Form");
    dojo.require("dijit.form.ValidationTextBox");
    dojo.require("dijit.form.Button");
    dojo.require("dijit.form.CheckBox");
    dojo.require("dijit.form.SimpleTextarea");
    
    function enviar() {
        var objd=dijit.byId('form1ru');

        if(!objd.validate())
        {            
            alert("Datos incompletos");
            return false;
        }
        return true;
    }
    -->
</script>
<%
try{
    final String objUri = (String)request.getAttribute(ATTR_OBJURI);
    SocialNetwork socialNetwork;
    try {
        socialNetwork = (SocialNetwork)SemanticObject.getSemanticObject(objUri).getGenericInstance();
    }catch(Exception e) {
        socialNetwork = null;
        session.removeAttribute("sw");
    }
    String wsiteId = (String)request.getAttribute(ATTR_WSITEID);
    final String treeItem = (String)request.getAttribute(ATTR_TREEITEM);
    //final SocialSite wsite = SocialSite.ClassMgr.getSocialSite(wsiteId);
    //final String url = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setAction(SWBResourceURL.Action_ADD).toString();
    final String url = paramRequest.getRenderUrl().setMode("oauth").setAction(SWBResourceURL.Action_ADD).toString();
%>
<div>
    <div>
<%
    if(socialNetwork==null)
    {
%>
        <form id="nc" dojoType="dijit.form.Form" class="swbform" action="<%=url%>" method="post">
<%
   }
%>
            <div class="sm-div-grupo">
                <p class="sm-row">
                    <label for="socialweb"><%=paramRequest.getLocaleString("lblSocialWeb")%></label>
                    <select name="socialweb" id="socialweb" dojoType="dijit.form.FilteringSelect" required="true" promptMessage="<%=paramRequest.getLocaleString("promptMsgSocialWeb")%>">
<%
    SemanticClass sc;
    Iterator<SemanticClass> itsc = Oauthable.social_Oauthable.listSubClasses();
    while(itsc.hasNext()) {
        sc = itsc.next();       
        out.println("<option value=\""+sc.getURI()+"\" "+(socialNetwork!=null && socialNetwork.getSemanticObject().getSemanticClass().getURI().equals(sc.getURI())?"selected=\"selected\"":"")+">"+sc.getName()+"</option>");
    }
%>
                    </select>
                </p>
                <p class="sm-row">
                    <label for="title"><em>*</em><%=paramRequest.getLocaleString("lblTitle")%></label>
                    <input type="text" name="title" id="title" dojoType="dijit.form.ValidationTextBox" value="<%=(socialNetwork==null?"":socialNetwork.getTitle())%>" required="true" promptMessage="<%=paramRequest.getLocaleString("promptMsgTitle")%>" invalidMessage="<%=paramRequest.getLocaleString("lblTitleFault")%>" trim="true" regExp="[a-zA-Z\u00C0-\u00FF' ]+" />
                </p>
                <p class="sm-row">
                    <label for="desc"><em>*</em><%=paramRequest.getLocaleString("lblDescription")%></label>
                    <input type="text" name="desc" id="desc" dojoType="dijit.form.ValidationTextBox" value="<%=(socialNetwork==null?"":socialNetwork.getDescription())%>" required="true" promptMessage="<%=paramRequest.getLocaleString("promptMsgDescription")%>" invalidMessage="<%=paramRequest.getLocaleString("lblDescriptionFault")%>" trim="true" regExp="[a-zA-Z\u00C0-\u00FF' ]+"/>
                </p>
                <p class="sm-row">
                    <label for="appId"><em>*</em><%=paramRequest.getLocaleString("lblAppId")%></label>
                    <input type="text" name="appId" id="appId" dojoType="dijit.form.ValidationTextBox" value="<%=(socialNetwork==null?"":socialNetwork.getAppKey())%>" required="true" promptMessage="<%=paramRequest.getLocaleString("promptMsgAppId")%>" invalidMessage="<%=paramRequest.getLocaleString("lblAppIdFault")%>" trim="true" regExp="[a-zA-Z\u00C0-\u00FF' ]+"/>
                </p>
                <p class="sm-row">
                    <label for="sk"><em>*</em><%=paramRequest.getLocaleString("lblSK")%></label>
                    <input type="text" name="sk" id="sk" dojoType="dijit.form.ValidationTextBox" value="<%=(socialNetwork==null?"":socialNetwork.getSecretKey())%>" required="true" promptMessage="<%=paramRequest.getLocaleString("promptMsgSK")%>" invalidMessage="<%=paramRequest.getLocaleString("lblSKFault")%>" trim="true" regExp="[a-zA-Z\u00C0-\u00FF' ]+"/>
                </p>
                <div class="clearer">&nbsp;</div>
            </div>
            <div class="centro">
<%
    if(socialNetwork==null)
    {
%>
                <input type="submit" onclick="return enviar()" value="<%=paramRequest.getLocaleString("lblSend")%>"/>
                <input type="reset" value="<%=paramRequest.getLocaleString("lblReset")%>"/>
<%  
    }
%>
            </div>
<%
    if(objUri!=null)
    {
%>
            <input type="hidden" name="<%=ATTR_OBJURI%>" value="<%=(objUri==null?"":objUri)%>">
<%  
    }
%>
            <input type="hidden" name="<%=ATTR_WSITEID%>" value="<%=wsiteId%>">
            <input type="hidden" name="<%=ATTR_TREEITEM%>" value="<%=treeItem%>">
<%
    if(socialNetwork==null)
    {
%>
        </form>
<%
    }
%>
    </div>
</div>
<%
}catch(Exception e) {
    System.out.println("#############################");
    e.printStackTrace(System.out);
    System.out.println("#############################");
}
%>