<%-- 
    Document   : typeOfContent
    Created on : 20/03/2013, 06:04:12 PM
    Author     : Jorge.Jimenez
    Modified by:Francisco.Jimenez
--%>
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%
     WebSite wsite=null;
     if(request.getParameter("wsite")!=null)
     {
        wsite = WebSite.ClassMgr.getWebSite(request.getParameter("wsite"));
     }
    if(wsite==null && request.getAttribute("wsite")!=null) {
        wsite = WebSite.ClassMgr.getWebSite((String)request.getAttribute("wsite")); 
    } 
    if(wsite==null) return; 
    
    String objUri = request.getParameter("objUri"); 
    SemanticObject semObj=null;
    if(objUri==null && request.getAttribute("objUri")!=null)
    {
        objUri=(String)request.getAttribute("objUri"); 
    }
    if(objUri==null) return;
    String contentType = (String) request.getAttribute("contentType");    
    semObj=SemanticObject.createSemanticObject(objUri); 
    SocialTopic socialTopic=null;
    PostIn postIn=null;
    PostOut postOut=null;
    String postOutPFlowUri=null;
    ArrayList apostOutNets=new ArrayList(); 
    boolean firstTime=false;
    if(semObj.getSemanticClass().isSubClass(PostIn.social_PostIn)) 
    {
        postIn=(PostIn)semObj.createGenericInstance();
    }else if(semObj.getGenericInstance() instanceof SocialTopic){
        socialTopic=(SocialTopic)semObj.createGenericInstance(); 
        firstTime=true; 
    }else if(semObj.getSemanticClass().isSubClass(PostOut.social_PostOut)) 
    {
        postOut=(PostOut)semObj.createGenericInstance();
        if(postOut instanceof Message) contentType="postMessage";
               else if(postOut instanceof Photo) contentType="uploadPhoto"; 
               else if(postOut instanceof Video) contentType="uploadVideo"; 
        
        Iterator<SocialNetwork> itPostOutSocialNets=postOut.listSocialNetworks();
        while(itPostOutSocialNets.hasNext())
        {
            SocialNetwork socialNet=itPostOutSocialNets.next();
            System.out.println("Red de PostOut:"+socialNet);
            apostOutNets.add(socialNet.getURI()); 
        }
        if(postOut.getPflowInstance()!=null && postOut.getPflowInstance().getPflow()!=null)
        {
            postOutPFlowUri=postOut.getPflowInstance().getPflow().getURI(); 
            System.out.println("postOutPFlowUri++G++:"+postOutPFlowUri);
        }
    }
    if(contentType==null) return; 
    
    User user=paramRequest.getUser(); 
    
    //SocialTopic socialTopic = (SocialTopic)SemanticObject.getSemanticObject(objUri).getGenericInstance(); // creates social topic to get Model Name
    //String brand = socialTopic.getSemanticObject().getModel().getName(); //gets brand name
    SWBResourceURL urlAction = paramRequest.getActionUrl();
   
    String postInSN=request.getParameter("postInSN"); 
      
    urlAction.setParameter("objUri", objUri);
    //urlAction.setParameter("wsite", brand);           
    urlAction.setParameter("wsite", wsite.getSemanticObject().getModel().getName());           
    
    
    ///////////////////////////////POSTEO DE MENSAJES/////////////////////////////
    
    if (contentType.equals("postMessage")) {
        urlAction.setParameter("toPost", "msg");
        SWBFormMgr messageFormMgr=null;
        if(postOut==null)   //Creation
        {
            messageFormMgr = new SWBFormMgr(Message.sclass.getSemanticObject(), null, SWBFormMgr.MODE_CREATE);
        }else //Update
        {
            messageFormMgr = new SWBFormMgr(postOut.getSemanticObject(), null, SWBFormMgr.MODE_EDIT);     
        }        
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
                <ul><b><%=SWBSocialUtil.Util.getStringFromGenericLocale("chooseSocialNets", user.getLanguage())%></b></ul>
                <%
                    Iterator<SocialNetwork> it = SocialNetwork.ClassMgr.listSocialNetworks(wsite);
                    while (it.hasNext()) {
                        SocialNetwork socialNetwork = (SocialNetwork) it.next();
                        if (socialNetwork instanceof Messageable) {
                            boolean isSelected=false;
                            System.out.println("Las Redes:"+socialNetwork); 
                            if(apostOutNets.contains(socialNetwork.getURI())) 
                            {
                                System.out.println("La Chida--:"+socialNetwork); 
                                isSelected=true; 
                            }
                            String selected="";
                            if(isSelected) selected="checked=\"true\"";
                            %>
                            <li>
                                <input type="checkbox" name="<%=socialNetwork.getURI()%>" <%=selected%> /><%=socialNetwork.getTitle()%> 
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
        <div class="campo"><%=postOut==null?messageFormMgr.renderElement(request, Message.social_msg_Text, messageFormMgr.MODE_CREATE):messageFormMgr.renderElement(request, Message.social_msg_Text, messageFormMgr.MODE_EDIT)%></div>
        
        <%
        if(postIn!=null && postOut==null)
        {
            socialTopic=postIn.getSocialTopic();
        }else if(postIn==null && postOut!=null)
        {
            socialTopic=postOut.getSocialTopic();
        }
        if(socialTopic!=null)
        {
            Iterator<SocialPFlowRef> itSocialPFlowRefs=socialTopic.listInheritPFlowRefs();
             %>    
            <br/><br/><br/><br/>
            <div class="etiqueta"><label for="socialFlow"><%=SWBSocialUtil.Util.getStringFromGenericLocale("publishFlow", user.getLanguage())%></label></div>
            <div class="campo">
            <select name="socialFlow">
            <%
            boolean noFlows=true;
            while(itSocialPFlowRefs.hasNext())
            {
                SocialPFlowRef socialFlowRef=itSocialPFlowRefs.next();
                if(socialFlowRef.isActive())
                {
                    SocialPFlow socialPFlow=socialFlowRef.getPflow();
                    if(socialPFlow.isActive())
                    {
                        boolean isChecked=false;
                        if(postOutPFlowUri!=null && postOutPFlowUri.equals(socialPFlow.getURI())) isChecked=true;
                        noFlows=false; 
                        %>
                        <option value="<%=socialPFlow.getURI()%>" <%=isChecked?"selected":""%>><%=socialPFlow.getDisplayTitle(lang)%> </option>
                        <%
                    }
                }
            }
            if(noFlows)
            {
            %>
                <option value=""><%=SWBSocialUtil.Util.getStringFromGenericLocale("withOutFlow", user.getLanguage())%></option>
            <%
            }
            %>
            </select>
            </div>
            <%if(!noFlows && firstTime){%>
                <div class="etiqueta"><label for="socialFlowComment"><%=SWBSocialUtil.Util.getStringFromGenericLocale("comment", user.getLanguage())%></label></div>
                <div class="campo">
                    <textarea name="socialFlowComment"></textarea>
                </div>
            <%
           }
        }
        
        %>
        
        
        <ul class="btns_final">
            <button dojoType="dijit.form.Button" type="submit"><%=SWBSocialUtil.Util.getStringFromGenericLocale("send", user.getLanguage())%></button>
        </ul>
    </form>
</div> 
<%} else if (contentType.equals("uploadPhoto")) {       ///////////////////////////////POSTEO DE FOTOS/////////////////////////////
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
        <ul><b><%=SWBSocialUtil.Util.getStringFromGenericLocale("chooseSocialNets", user.getLanguage())%></b></ul>
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
            socialTopic=postIn.getSocialTopic();
        }
        if(socialTopic!=null)
        {
            Iterator<SocialPFlowRef> itSocialPFlowRefs=socialTopic.listInheritPFlowRefs();
             %>    
            <br/><br/><br/><br/>
            <div class="etiqueta"><label for="socialFlow"><%=SWBSocialUtil.Util.getStringFromGenericLocale("publishFlow", user.getLanguage())%></label></div>
            <div class="campo">
            <select name="socialFlow">
            <%
            boolean noFlows=true;
            while(itSocialPFlowRefs.hasNext())
            {
                SocialPFlowRef socialFlowRef=itSocialPFlowRefs.next();
                SocialPFlow socialPFlow=socialFlowRef.getPflow();
                if(socialPFlow.isActive())
                {
                    noFlows=false; 
                    %>
                    <option value="<%=socialPFlow.getURI()%>"><%=socialPFlow.getDisplayTitle(lang)%> </option>
                    <%
                }
            }
            if(noFlows)
            {
            %>
                <option value=""><%=SWBSocialUtil.Util.getStringFromGenericLocale("withOutFlow", user.getLanguage())%></option>
            <%
            }
            %>
            </select>
            </div>
            <%if(!noFlows && firstTime){%>
                <div class="etiqueta"><label for="socialFlowComment"><%=SWBSocialUtil.Util.getStringFromGenericLocale("comment", user.getLanguage())%></label></div>
                <div class="campo">
                    <textarea name="socialFlowComment"></textarea>
                </div>
            <%
           }
        }
        
        %>
        
        <ul class="btns_final">
            <button dojoType="dijit.form.Button" type="submit"><%=SWBSocialUtil.Util.getStringFromGenericLocale("send", user.getLanguage())%></button>
        </ul>
    </form>
</div>

<%} else if (contentType.equals("uploadVideo")) {       ///////////////////////////////POSTEO DE VIDEOS/////////////////////////////
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
        <ul><b><%=SWBSocialUtil.Util.getStringFromGenericLocale("chooseSocialNets", user.getLanguage())%></b></ul>
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
            socialTopic=postIn.getSocialTopic();
        }
        if(socialTopic!=null)
        {
            Iterator<SocialPFlowRef> itSocialPFlowRefs=socialTopic.listInheritPFlowRefs();
            %>    
            <div class="etiqueta">Flujo de Publicación</div>        
            <div class="etiqueta"><label for="socialFlow"><%=SWBSocialUtil.Util.getStringFromGenericLocale("publishFlow", user.getLanguage())%></label></div>
            <div class="campo">
            <%
            if(postIn!=null)
            {
                socialTopic=postIn.getSocialTopic();
            }
            if(socialTopic!=null)
            {
            %>
                <select name="socialFlow">
                <%
                boolean noFlows=true;
                while(itSocialPFlowRefs.hasNext())
                {
                    SocialPFlowRef socialFlowRef=itSocialPFlowRefs.next();
                    SocialPFlow socialPFlow=socialFlowRef.getPflow();
                    if(socialPFlow.isActive())
                    {
                        noFlows=false; 
                        %>
                        <option value="<%=socialPFlow.getURI()%>"><%=socialPFlow.getDisplayTitle(lang)%> </option>
                        <%
                    }
                }
                if(noFlows)
                {
                %>
                    <option value=""><%=SWBSocialUtil.Util.getStringFromGenericLocale("withOutFlow", user.getLanguage())%></option>
                <%
                }
                %>
                </select>
                </div>
                <%if(!noFlows && firstTime){%>
                    <div class="etiqueta"><label for="socialFlowComment"><%=SWBSocialUtil.Util.getStringFromGenericLocale("comment", user.getLanguage())%></label></div>
                    <div class="campo">
                        <textarea name="socialFlowComment"></textarea>
                    </div>
                <%
               }
            }
                %>

                <ul class="btns_final">
                    <button dojoType="dijit.form.Button" type="submit"><%=SWBSocialUtil.Util.getStringFromGenericLocale("send", user.getLanguage())%></button>
                </ul>
            </form>
        </div>
    <%    
        }
    }
    %>