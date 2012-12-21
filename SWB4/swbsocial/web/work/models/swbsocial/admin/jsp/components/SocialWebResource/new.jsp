<%-- 
    Document   : newjsp
    Created on : 15-oct-2012, 15:49:54
    Author     : carlos.ramos
--%>

<%@page import="org.semanticwb.SWBPortal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0//EN" "http://www.w3.org/TR/REC-html40/strict.dtd">
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
<%-- 
    Document   : new
    Created on : 11-oct-2012, 18:19:52
    Author     : carlos.ramos
--%>
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
    SocialWebResource socialWebResource = (SocialWebResource) request.getAttribute(ATTR_THIS);
System.out.println("\n\n***********jsp");
System.out.println("action="+request.getAttribute(ATTR_AXN));
    final String objUri = (String)request.getAttribute(ATTR_OBJURI);
System.out.println("objUri="+objUri);
System.out.println("wsiteId="+request.getAttribute(ATTR_WSITE));
    final String wsiteId = (String)request.getAttribute(ATTR_WSITE);
    final SocialSite wsite = SocialSite.ClassMgr.getSocialSite(wsiteId);
System.out.println("wsite="+wsite);
    final String treeItem = (String)request.getAttribute(ATTR_TREEITEM);
System.out.println("treeItem="+treeItem);
System.out.println("url permisos="+request.getParameter(URL_REQ_PERMISSIONS));
    
    SocialNetwork socialWeb;
    try {
        socialWeb = (SocialNetwork)SemanticObject.getSemanticObject(objUri).getGenericInstance();
    }catch(Exception e) {
        socialWeb = null;
    }

    String sclassURI = socialWeb==null?"":socialWeb.getSemanticObject().getSemanticClass().getClassName();
System.out.println("socialWeb="+socialWeb);
System.out.println("sclassURI="+sclassURI);
    final String url = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setAction(Oauthable.PHASE_1).toString();
%>
<div>
    <fieldset>
        <legend></legend>
    <div>
        <form id="nc" dojoType="dijit.form.Form" class="swbform" action="<%=url%>" method="post">
            <div class="sm-div-grupo">
                <p class="sm-row">
                    <label for="socialweb"><%=paramRequest.getLocaleString("lblSocialWeb")%></label>
                    <select name="socialweb" id="socialweb" dojoType="dijit.form.FilteringSelect" required="true" promptMessage="<%=paramRequest.getLocaleString("promptMsgSocialWeb")%>">
<%
    SemanticClass sc;
    Iterator<SemanticClass> itsc = Oauthable.social_Oauthable.listSubClasses();
    while(itsc.hasNext()) {
        sc = itsc.next();
        out.println("<br/>sc.getName()="+sc.getName()+", uri="+sc.getURI());
        out.println("<option value=\""+sc.getURI()+"\" "+(sclassURI.equals(sc.getName())?"selected=\"selected\"":"")+">"+sc.getName()+"</option>");
    }
%>
                    </select>
                </p>
                <p class="sm-row">
                    <label for="title"><em>*</em><%=paramRequest.getLocaleString("lblTitle")%></label>
                    <input type="text" name="title" id="title" dojoType="dijit.form.ValidationTextBox" value="<%=(socialWeb==null?"":socialWeb.getTitle())%>" required="true" promptMessage="<%=paramRequest.getLocaleString("promptMsgTitle")%>" invalidMessage="<%=paramRequest.getLocaleString("lblTitleFault")%>" trim="true" regExp="[a-zA-Z\u00C0-\u00FF' ]+" />
                </p>
                <p class="sm-row">
                    <label for="desc"><em>*</em><%=paramRequest.getLocaleString("lblDescription")%></label>
                    <input type="text" name="desc" id="desc" dojoType="dijit.form.ValidationTextBox" value="<%=(socialWeb==null?"":(socialWeb.getDescription()==null?"":socialWeb.getDescription()))%>" required="true" promptMessage="<%=paramRequest.getLocaleString("promptMsgDescription")%>" invalidMessage="<%=paramRequest.getLocaleString("lblDescriptionFault")%>" trim="true" regExp="[a-zA-Z\u00C0-\u00FF' ]+"/>
                </p>
                <p class="sm-row">
                    <label for="appId"><em>*</em><%=paramRequest.getLocaleString("lblAppId")%></label>
                    <input type="text" name="appId" id="appId" dojoType="dijit.form.ValidationTextBox" value="<%=(socialWeb==null?"":socialWeb.getAppKey())%>" required="true" promptMessage="<%=paramRequest.getLocaleString("promptMsgAppId")%>" invalidMessage="<%=paramRequest.getLocaleString("lblAppIdFault")%>" trim="true" regExp="[a-zA-Z\u00C0-\u00FF' ]+"/>
                </p>
                <p class="sm-row">
                    <label for="sk"><em>*</em><%=paramRequest.getLocaleString("lblSK")%></label>
                    <input type="text" name="sk" id="sk" dojoType="dijit.form.ValidationTextBox" value="<%=((socialWeb==null?"":socialWeb.getSecretKey()))%>" required="true" promptMessage="<%=paramRequest.getLocaleString("promptMsgSK")%>" invalidMessage="<%=paramRequest.getLocaleString("lblSKFault")%>" trim="true" regExp="[a-zA-Z\u00C0-\u00FF' ]+"/>
                </p>
<%
    if(request.getParameter(URL_REQ_PERMISSIONS)!=null)
    {
        final String uri = request.getParameter(URL_REQ_PERMISSIONS);
%>                
                <p class="sm-row">
                    <a href="<%=uri%>" target="_blank" title="<%=paramRequest.getLocaleString("promptMsgAuthentic")%>"><%=paramRequest.getLocaleString("lblAuthentic")%></a>
                </p>
<%
   }else if(request.getParameter(URL_REQ_ACCESS)!=null)
   {
        final String uri = request.getParameter(URL_REQ_ACCESS);
%>                
                <p class="sm-row">
                    <a href="<%=uri%>" target="_blank" title="<%=paramRequest.getLocaleString("promptMsgAuthentic")%>"><%=paramRequest.getLocaleString("lblAuthentic")%></a>
                </p>
<%
   }
%>
                <div class="clearer">&nbsp;</div>
            </div>
            <div class="centro">
                <input type="submit" onclick="return enviar()" value="<%=paramRequest.getLocaleString("lblSend")%>"/>
                <input type="reset" value="<%=paramRequest.getLocaleString("lblReset")%>"/>
            </div>
            <input type="hidden" name="<%=ATTR_OBJURI%>" value="<%=objUri%>">
            <input type="hidden" name="<%=ATTR_WSITE%>" value="<%=wsiteId%>">
            <input type="hidden" name="<%=ATTR_TREEITEM%>" value="<%=treeItem%>">
        </form>
    </div>
    </fieldset>
</div>