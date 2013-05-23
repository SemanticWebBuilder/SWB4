<%-- 
    Document   : typeOfContent
    Created on : 20/03/2013, 06:04:12 PM
    Author     : Jorge.Jimenez
    Modified by:Francisco.Jimenez
--%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%
    WebSite wsite = WebSite.ClassMgr.getWebSite(request.getParameter("wsite"));
    String contentType = (String) request.getAttribute("valor");    
    String objUri = request.getParameter("objUri"); 
    SemanticObject semObj=SemanticObject.createSemanticObject(objUri); 
    PostIn postIn=null;
    if(semObj.getSemanticClass().isSubClass(PostIn.social_PostIn)) 
    {
        postIn=(PostIn)semObj.createGenericInstance();
    }
    
    
    //SocialTopic socialTopic = (SocialTopic)SemanticObject.getSemanticObject(objUri).getGenericInstance(); // creates social topic to get Model Name
    //String brand = socialTopic.getSemanticObject().getModel().getName(); //gets brand name
    SWBResourceURL urlAction = paramRequest.getActionUrl();
   
    String postInSN=request.getParameter("postInSN"); 
      
    urlAction.setParameter("objUri", objUri);
    //urlAction.setParameter("wsite", brand);           
    urlAction.setParameter("wsite", wsite.getSemanticObject().getModel().getName());           
    if (contentType.equals("postMessage")) {
        urlAction.setParameter("toPost", "msg");
        SWBFormMgr messageFormMgr = new SWBFormMgr(Message.sclass.getSemanticObject(), null, SWBFormMgr.MODE_CREATE);
        //messageFormMgr.setAction(SWBResourceURL.Action_ADD);
        messageFormMgr.setType(SWBFormMgr.TYPE_DOJO);
        messageFormMgr.setFilterRequired(false);
        String lang = "";
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }
        messageFormMgr.setLang(lang);
        messageFormMgr.addButton(SWBFormButton.newSaveButton());
        messageFormMgr.addButton(SWBFormButton.newBackButton());
%>
<div class="swbform">
    <form dojoType="dijit.form.Form" id="frmUploadText" action="<%=urlAction.setAction("postMessage")%>" onsubmit="submitForm('frmUploadText'); return false;" method="post">
        <%= messageFormMgr.getFormHiddens()%>
        <%
            if(postInSN==null)
            {
                %>
                <ul><b>Seleccione las redes sociales a las cuales desea enviar el post</b></ul>
                <%
                    Iterator<SocialNetwork> it = SocialNetwork.ClassMgr.listSocialNetworks(wsite);
                    while (it.hasNext()) {
                        SocialNetwork socialNetwork = (SocialNetwork) it.next();
                        if (socialNetwork instanceof Messageable) {
                            %>
                            <li>
                                <input type="checkbox" name="<%=socialNetwork.getURI()%>"/><%=socialNetwork.getTitle()%>
                            </li>
                            <%
                        }
                    }
            }else
            {
                SocialNetwork socialNet=(SocialNetwork)SemanticObject.getSemanticObject(postInSN).createGenericInstance(); 
                if(socialNet==null) return;
                %>
                    <input type="hidden" name="socialNetUri" value="<%=socialNet.getURI()%>"/>
                <%
            }
         %>
        <div class="etiqueta"><label for="title"><%=Message.social_Message.getDisplayName(lang)%>: </label></div>
        <div class="campo"><%=messageFormMgr.renderElement(request, Message.social_msg_Text, messageFormMgr.MODE_CREATE)%></div>
        
         <%
        if(postIn!=null)
        {
            SocialTopic socialTopic=postIn.getSocialTopic();
            Iterator<SocialPFlowRef> itSocialPFlowRefs=socialTopic.listInheritPFlowRefs();
             %>    
            <br/><br/><br/><br/>
            <div class="etiqueta"><label for="socialFlow">Flujo de publicación:</label></div>
            <div class="campo">
            <select name="socialFlow">
            <%
            
            boolean firstTime=false;
            while(itSocialPFlowRefs.hasNext())
            {
                SocialPFlowRef socialFlowRef=itSocialPFlowRefs.next();
                SocialPFlow socialPFlow=socialFlowRef.getPflow();
                if(socialPFlow.isActive())
                {
                    %>
                    <option value="<%=socialPFlow.getURI()%>"><%=socialPFlow.getDisplayTitle(lang)%> </option>
                    <%
                }
            }
            %>
            </select>
            </div>
            <%if(firstTime){%>
                <div class="etiqueta"><label for="socialFlowComment">Comentario:</label></div>
                <div class="campo">
                    <textarea name="socialFlowComment"></textarea>
                </div>
            <%
           }
        }
        %>
        
        
        <ul class="btns_final">
            <button dojoType="dijit.form.Button" type="submit">Enviar</button>
        </ul>
    </form>
</div> 
<%} else if (contentType.equals("uploadPhoto")) {
    urlAction.setParameter("toPost", "photo");
    SWBFormMgr photoMgr = new SWBFormMgr(Photo.sclass, paramRequest.getWebPage().getWebSite().getSemanticObject(), null);
    photoMgr.setType(SWBFormMgr.TYPE_DOJO);
    photoMgr.setFilterRequired(false);
    String lang = "es";
    if (paramRequest.getUser() != null) {
        lang = paramRequest.getUser().getLanguage();
    }
    photoMgr.setLang(lang);
    photoMgr.addButton(SWBFormButton.newSaveButton());
    photoMgr.addButton(SWBFormButton.newBackButton());
//StringBuffer ret = new StringBuffer();
    SemanticObject obj2 = new SemanticObject(paramRequest.getWebPage().getWebSite().getSemanticModel(), Photo.sclass);
%>
<div class="swbform">
    <form dojoType="dijit.form.Form" id="frmUploadPhoto" action="<%=urlAction.setAction("uploadPhoto")%>" method="post" onsubmit="submitForm('frmUploadPhoto'); return false;">
        <%= photoMgr.getFormHiddens()%>
        <ul><b>Seleccione las redes sociales a las cuales desea enviar el post</b></ul>
        <%
            Iterator<SocialNetwork> it = SocialNetwork.ClassMgr.listSocialNetworks(wsite);
            while (it.hasNext()) {
                SocialNetwork socialNetwork = (SocialNetwork) it.next();
                if (socialNetwork instanceof Photoable) {
        %>
        <li>
            <input type="checkbox" name="<%=socialNetwork.getURI()%>"/><%=socialNetwork.getTitle()%>
        </li>
        <%
                }
            }%>
        <!--
        <div class="etiqueta"><label for="title"><%--=Photo.swb_title.getDisplayName()--%>: </label></div>
        <div class="campo"><%--=photoMgr.renderElement(request, Photo.swb_title, photoMgr.MODE_CREATE)--%></div>
        -->

        <div class="etiqueta"><label for="description"><%=Photo.social_msg_Text.getDisplayName()%>:</label></div>
        <div class="campo"><%=photoMgr.renderElement(request, Photo.social_msg_Text, photoMgr.MODE_CREATE)%></div>
        <div class="etiqueta"><label for="keywords"><%=Photo.swb_Tagable.getDisplayName(lang)%>:</label></div>
        <div class="etiqueta"><%=photoMgr.renderElement(request, Photo.swb_tags, photoMgr.MODE_CREATE)%></div>

        <div class="etiqueta"><label for="photo"><%=photoMgr.renderLabel(request, Photo.social_photo, photoMgr.MODE_CREATE)%>: </label></div>
        <%=photoMgr.getFormElement(Photo.social_photo).renderElement(request, obj2, Photo.social_photo, SWBFormMgr.TYPE_DOJO, SWBFormMgr.MODE_CREATE, lang)%>       
        
        <%
        if(postIn!=null)
        {
            SocialTopic socialTopic=postIn.getSocialTopic();
            Iterator<SocialPFlowRef> itSocialPFlowRefs=socialTopic.listInheritPFlowRefs();
             %>    
            <br/><br/><br/><br/>
            <div class="etiqueta"><label for="socialFlow">Flujo de publicación:</label></div>
            <div class="campo">
            <%
            boolean firstTime=false;
            while(itSocialPFlowRefs.hasNext())
            {
                SocialPFlowRef socialFlowRef=itSocialPFlowRefs.next();
                SocialPFlow socialPFlow=socialFlowRef.getPflow();
                if(socialPFlow.isActive())
                {
                    if(!firstTime){%><select name="socialFlow"><%}%>
                    <option value="<%=socialPFlow.getURI()%>"><%=socialPFlow.getDisplayTitle(lang)%> </option>
                <%
                    if(!firstTime){%></select><%firstTime=true;}
                }
            }
            %>
            </div>
             <%if(firstTime){%>
            <div class="etiqueta"><label for="socialFlowComment">Comentario:</label></div>
            <div class="campo">
                <textarea name="socialFlowComment"></textarea>
            </div>
            <%
            }
        }
        %>
        
        <ul class="btns_final">
            <button dojoType="dijit.form.Button" type="submit">Enviar</button>
        </ul>
    </form>
</div>

<%} else if (contentType.equals("uploadVideo")) {
    urlAction.setParameter("toPost", "video");
    SWBFormMgr videoMgr = new SWBFormMgr(Video.sclass, paramRequest.getWebPage().getWebSite().getSemanticObject(), null);
    videoMgr.setType(SWBFormMgr.TYPE_DOJO);
    videoMgr.setFilterRequired(false);
    String lang = "";
    if (paramRequest.getUser() != null) {
        lang = paramRequest.getUser().getLanguage();
    }
    videoMgr.setLang(lang);
    videoMgr.addButton(SWBFormButton.newSaveButton());
    videoMgr.addButton(SWBFormButton.newBackButton());
    SemanticObject videoSemObj = new SemanticObject(paramRequest.getWebPage().getWebSite().getSemanticModel(), Video.sclass);
%>
<div class="swbform">
    <form dojoType="dijit.form.Form" id="frmUploadVideo" action="<%=urlAction.setAction("uploadVideo")%>" method="post" onsubmit="submitForm('frmUploadVideo'); return false;">
        <%= videoMgr.getFormHiddens()%>
        <ul><b>Seleccione las redes sociales a las cuales desea enviar el post</b></ul>
        <%
            Iterator<SocialNetwork> it = SocialNetwork.ClassMgr.listSocialNetworks(wsite);
            while (it.hasNext()) {
                SocialNetwork socialNetwork = (SocialNetwork) it.next();
                if (socialNetwork instanceof Videoable) {
        %>
        <li>
            <input type="checkbox" name="<%=socialNetwork.getURI()%>"/><%=socialNetwork.getTitle()%>
        </li>
        <%
                }
            }%>
        <!--    
        <div class="etiqueta"><label for="title"><%--=Video.swb_title.getDisplayName()--%>: </label></div>
        <div class="campo"><%--=videoMgr.renderElement(request, Video.swb_title, videoMgr.MODE_CREATE)--%></div>
        -->
        <div class="etiqueta"><label for="description"><%=Video.social_msg_Text.getDisplayName()%>:</label></div>
        <div class="campo"><%=videoMgr.renderElement(request, Video.social_msg_Text, videoMgr.MODE_CREATE)%></div>
        <div class="etiqueta"><label for="keywords"><%=Video.swb_Tagable.getDisplayName(lang)%>:</label></div>
        <div class="campo"><%=videoMgr.renderElement(request, Video.swb_tags, videoMgr.MODE_CREATE)%></div>
        <div class="etiqueta"><label for="title"><%=videoMgr.renderLabel(request, Video.social_video, videoMgr.MODE_CREATE)%>: </label></div>
        <div class="campo"><%=videoMgr.getFormElement(Video.social_video).renderElement(request, videoSemObj, Video.social_video, SWBFormMgr.TYPE_DOJO, SWBFormMgr.MODE_CREATE, lang)%></div>       
        <%
        if(postIn!=null)
        {
            SocialTopic socialTopic=postIn.getSocialTopic();
            Iterator<SocialPFlowRef> itSocialPFlowRefs=socialTopic.listInheritPFlowRefs();
        %>    
            <div class="etiqueta">Flujo de Publicación</div>        
            <div class="etiqueta"><label for="socialFlow">Flujo de publicación</label></div>
            <div class="campo">
            <%
            boolean firstTime=false;
            while(itSocialPFlowRefs.hasNext())
            {
                SocialPFlowRef socialFlowRef=itSocialPFlowRefs.next();
                SocialPFlow socialPFlow=socialFlowRef.getPflow();
                if(socialPFlow.isActive())
                {
                    if(!firstTime){%><select name="socialFlow"><option value="test">test</option><%}%>
                    <option value="<%=socialPFlow.getURI()%>"><%=socialPFlow.getDisplayTitle(lang)%></option>
                <%
                    if(!firstTime){%></select><%firstTime=true;}
                }
            }
            %>
            </div>
            <%if(firstTime){%>
            <div class="etiqueta"><label for="socialFlowComment">Comentario</label></div>
            <div class="campo">
                <textarea name="socialFlowComment"></textarea>
            </div>
            <%
            }
        }
        %>
        
        <ul class="btns_final">
            <button dojoType="dijit.form.Button" type="submit">Enviar</button>
        </ul>
    </form>
</div>
<%    
    }
%>