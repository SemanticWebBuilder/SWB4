<%-- 
    Document   : typeOfContent
    Created on : 20/03/2013, 06:04:12 PM
    Author     : Jorge.Jimenez
    Modified by: Francisco.Jimenez
--%>
<%@page import="org.semanticwb.social.util.SocialLoader"%>
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.social.SocialFlow.SocialPFlowMgr"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
    System.out.println("Entra a TypeOfContent..1");
    WebSite wsite = null;
    if (request.getParameter("wsite") != null) {
        wsite = WebSite.ClassMgr.getWebSite(request.getParameter("wsite"));
    }
    if (wsite == null && request.getAttribute("wsite") != null) {
        wsite = WebSite.ClassMgr.getWebSite((String) request.getAttribute("wsite"));
    }
    if (wsite == null) {
        return;
    }

    String objUri = request.getParameter("objUri");
    String sourceCall = request.getParameter("source");
        if(sourceCall == null){ //When typeOfContent is called from Tema/Responder the param source is not being sent
            sourceCall = "reply";
        }
    //System.out.println("****EL source es: " +sourceCall);
    
    SemanticObject semObj = null;
    if (objUri == null && request.getAttribute("objUri") != null) {
        objUri = (String) request.getAttribute("objUri");
    }
    if (objUri == null) {
        return;
    }
    String contentType = (String) request.getAttribute("contentType");
    semObj = SemanticObject.createSemanticObject(objUri);
    SocialTopic socialTopic = null;
    PostIn postIn = null;
    PostOut postOut = null;
    String postOutPFlowUri = null;
    ArrayList apostOutNets = new ArrayList();
    boolean firstTime = false;
    if (semObj.getSemanticClass().isSubClass(PostIn.social_PostIn)) {//Publish in response to PostIn
        postIn = (PostIn) semObj.createGenericInstance();
        //System.out.println("Es un POST IN");
        apostOutNets.add(postIn.getPostInSocialNetwork().getURI());
    } else if (semObj.getGenericInstance() instanceof SocialTopic) {//Publish from Social Topic
        //System.out.println("Es un SOCIALTOPIC");
        socialTopic = (SocialTopic) semObj.createGenericInstance();
        firstTime = true;
    } else if (semObj.getSemanticClass().isSubClass(PostOut.social_PostOut)) {
        //System.out.println("Es un POST OUT");
        postOut = (PostOut) semObj.createGenericInstance();
        if (postOut instanceof Message) {
            contentType = "postMessage";
        } else if (postOut instanceof Photo) {
            contentType = "uploadPhoto";
        } else if (postOut instanceof Video) {
            contentType = "uploadVideo";
        }

        Iterator<SocialNetwork> itPostOutSocialNets = postOut.listSocialNetworks();
        while (itPostOutSocialNets.hasNext()) {
            SocialNetwork socialNet = itPostOutSocialNets.next();
            //System.out.println("Red de PostOut:" + socialNet);
            apostOutNets.add(socialNet.getURI());
        }
        if (postOut.getPflowInstance() != null && postOut.getPflowInstance().getPflow() != null) {
            postOutPFlowUri = postOut.getPflowInstance().getPflow().getURI();
            //System.out.println("postOutPFlowUri++G++:" + postOutPFlowUri);
        }
    }
    if (contentType == null) {
        return;
    }

    User user = paramRequest.getUser();

    //SocialTopic socialTopic = (SocialTopic)SemanticObject.getSemanticObject(objUri).getGenericInstance(); // creates social topic to get Model Name
    //String brand = socialTopic.getSemanticObject().getModel().getName(); //gets brand name
    SWBResourceURL urlAction = paramRequest.getActionUrl();

    String postInSN = request.getParameter("postInSN");

    urlAction.setParameter("objUri", objUri);
    //urlAction.setParameter("wsite", brand);           
    urlAction.setParameter("wsite", wsite.getSemanticObject().getModel().getName());

    SocialPFlowMgr pfmgr = SocialLoader.getPFlowManager();
    
    //Recovering privacy options
    ArrayList<String> selectFacebook= new ArrayList<String>();
    ArrayList<String> selectYoutube= new ArrayList<String>();        
    Iterator <PostOutPrivacy> postOutPs = PostOutPrivacy.ClassMgr.listPostOutPrivacies();
    while(postOutPs.hasNext()){
        PostOutPrivacy postOutP = postOutPs.next();
        //SemanticObject sObj =postOutP.getNetworkType();
        Iterator<SemanticObject> nets = postOutP.listNetworkTypes();
        while(nets.hasNext()){
            SemanticObject semObjNetw=nets.next(); 
            SemanticClass sClass=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(semObjNetw.getURI());
            if(sClass.equals(Facebook.social_Facebook)){
                if(!postOutP.getId().equals("PUBLIC")){
                    selectFacebook.add(postOutP.getId());
                }
            }
            if(sClass.equals(Youtube.social_Youtube)){
                if(!postOutP.getId().equals("PUBLIC")){
                    selectYoutube.add(postOutP.getId());
                }
            }
        }        
    }
     
    ///////////////////////////////POSTEO DE MENSAJES/////////////////////////////

    if (contentType.equals("postMessage")) {
        urlAction.setParameter("toPost", "msg");
        SWBFormMgr messageFormMgr = null;
        if (postOut == null) //Creation
        {
            messageFormMgr = new SWBFormMgr(Message.sclass.getSemanticObject(), null, SWBFormMgr.MODE_CREATE);
        } else //Update
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
<div id="pub-detalle">
    <span class="sel-txtdiv"></span>
    <div class="swbform">
        <form dojoType="dijit.form.Form" id="<%=objUri%><%=sourceCall%>frmUploadText" action="<%=urlAction.setAction("postMessage")%>" onsubmit="submitForm('<%=objUri%><%=sourceCall%>frmUploadText');
                return false;" method="post">

            <div class="pub-redes">
                <%= messageFormMgr.getFormHiddens()%>
                <p class="titulo">Detalles de la publicaci&oacute;n</p>
                <p><div class="etiqueta"><label for="msj"><%=Message.social_Message.getDisplayName(lang)%>: </label></div>
                <div class="campo"><%=postOut == null ? messageFormMgr.renderElement(request, Message.social_msg_Text, messageFormMgr.MODE_CREATE) : messageFormMgr.renderElement(request, Message.social_msg_Text, messageFormMgr.MODE_EDIT)%></div>
                </p>
                <%
                    if (postIn != null && postOut == null) {
                        socialTopic = postIn.getSocialTopic();
                    } else if (postIn == null && postOut != null) {
                        socialTopic = postOut.getSocialTopic();
                    }
                    if (socialTopic != null) {
                        Iterator<SocialPFlowRef> itSocialPFlowRefs = socialTopic.listInheritPFlowRefs();
                %>    
                <p>
                <div class="etiqueta"><label for="socialFlow"><%=SWBSocialUtil.Util.getStringFromGenericLocale("publishFlow", user.getLanguage())%></label></div>
                <div class="campo">
                    <select name="socialFlow" id="flu">
                        <%
                            boolean noFlows = true;
                            while (itSocialPFlowRefs.hasNext()) {
                                SocialPFlowRef socialFlowRef = itSocialPFlowRefs.next();
                                if (socialFlowRef.isActive()) {
                                    SocialPFlow socialPFlow = socialFlowRef.getPflow();
                                    if (socialPFlow.isActive()) {
                                       if(pfmgr.isManagedByPflow(socialPFlow, Message.sclass))
                                        {
                                            boolean isChecked = false;
                                            if (postOutPFlowUri != null && postOutPFlowUri.equals(socialPFlow.getURI())) {
                                                isChecked = true;
                                            }
                                            noFlows = false;
                                            %> 
                                            <option value="<%=socialPFlow.getURI()%>" <%=isChecked ? "selected" : ""%>><%=socialPFlow.getDisplayTitle(lang)%> </option>
                                            <%
                                        }
                                    }
                                }
                            }
                            if (noFlows) {
                        %>
                        <option value=""><%=SWBSocialUtil.Util.getStringFromGenericLocale("withOutFlow", user.getLanguage())%></option>
                        <%
                            }
                        %>   
                    </select></p>
                </div>


                <%if (!noFlows && firstTime) {%>
                <div class="etiqueta"><label for="socialFlowComment"><%=SWBSocialUtil.Util.getStringFromGenericLocale("comment", user.getLanguage())%></label></div>
                <div class="campo">
                    <textarea name="socialFlowComment" cols="35" rows="5"></textarea>
                </div>
                <%
                        }
                    }

                %>
            
                    <button class="submit" type="submit" onclick="return checksRedesText('<%=objUri%>','<%=sourceCall%>', <%=(postInSN == null || postIn != null ? "true" : "false")%>);"><%=SWBSocialUtil.Util.getStringFromGenericLocale("send", user.getLanguage())%></button>
               
            </div>      
            <%                
                if (postInSN == null || postIn != null ) {
            %>
            <div class="pub-redes">
                <p class="titulo">Redes disponibles</p>
                <ul><b><%=SWBSocialUtil.Util.getStringFromGenericLocale("chooseSocialNets", user.getLanguage())%></b></ul>
                <%
                    Iterator<SocialNetwork> it = SocialNetwork.ClassMgr.listSocialNetworks(wsite);
                    while (it.hasNext()) {
                        SocialNetwork socialNetwork = (SocialNetwork) it.next();
                        if (socialNetwork instanceof Messageable && socialNetwork.isActive() && socialNetwork.isValid()) {
                            if(socialNetwork instanceof Youtube && postIn == null && postOut == null){//Only show youtube networks if is a response
                                continue;
                            }
                            boolean isSelected = false;
                            //System.out.println("Las Redes:" + socialNetwork);
                            if (apostOutNets.contains(socialNetwork.getURI())) {
                                //System.out.println("La Chida--:" + socialNetwork);
                                isSelected = true;
                            }
                            String typeClass = "";
                            if(postIn != null){//If it is a response to some post, show only the nets of the post Type
                                if(!socialNetwork.getClass().equals(postIn.getPostInSocialNetwork().getClass())){
                                    continue;
                                }
                            }
                            if(postOut != null){//If it is a response to some post, show only the nets of the post Type
                                if(postOut.getPostInSource() != null){
                                    if(!socialNetwork.getClass().equals(postOut.getPostInSource().getPostInSocialNetwork().getClass())){
                                        continue;
                                    }
                                }
                            }
                            
                            if (socialNetwork instanceof Youtube) {
                                typeClass = "ico-ytb";
                            }else if (socialNetwork instanceof Facebook){
                             typeClass = "ico-fcb";
                            }else if (socialNetwork instanceof Twitter) {
                                typeClass = "ico-twt";
                            }
                            String selected = "";
                            if (isSelected) {
                                selected = "checked=\"true\"";
                            }                            
                %>
                <li class="<%=typeClass%>">
                    <input type="checkbox" id="checkRedes" name="<%=socialNetwork.getURI()%>" <%=selected%> onClick="disableSelect(this);"/>
                    <label for="t1"><span></span><%=socialNetwork.getTitle()%></label> 
                    <%
                    if(socialNetwork instanceof Facebook && postIn == null && postOut == null){
                    %>
                    <select id="postoutPrivacy" name="postoutPrivacy" style="display:none;" disabled="disabled">
                        <option value="<%=socialNetwork.getURI() + "|PUBLIC"%>">PUBLIC</option>
                        <%
                        for(int i = 0; i < selectFacebook.size(); i++){
                        %>
                            <option value="<%=socialNetwork.getURI() + "|" + selectFacebook.get(i)%>"><%=selectFacebook.get(i)%></option>
                        <%
                        }
                        %>
                    </select>
                    <%
                    }
                    %>
                </li>                                    
                <%
                        }
                    }
                }/* else {
                    SocialNetwork socialNet = (SocialNetwork) SemanticObject.getSemanticObject(postInSN).createGenericInstance();
                    if (socialNet == null) {
                        return;
                    }*/
                %>
                <!--<input type="hidden" name="socialNetUri" value="<%//=socialNet.getURI()%>"/>-->
                <%
                    //}
                %>
            </div>
        </form> 
    </div>
    <%} else if (contentType.equals("uploadPhoto")) {       ///////////////////////////////POSTEO DE FOTOS/////////////////////////////
        urlAction.setParameter("toPost", "photo");
        
        SWBFormMgr photoMgr=null;
        if (postOut == null) //Creation
        {
            photoMgr = new SWBFormMgr(Photo.sclass.getSemanticObject(), null, SWBFormMgr.MODE_CREATE);
        } else //Update
        {
            photoMgr = new SWBFormMgr(postOut.getSemanticObject(), null, SWBFormMgr.MODE_EDIT);
        }
        
        
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
        //System.out.println("postOut en Photo:"+postOut);
    %>
    <div id="pub-detalle">
        <span class="sel-imgdiv"></span>
        <div class="swbform">
            <form dojoType="dijit.form.Form" id="<%=objUri%><%=sourceCall%>frmUploadPhoto" action="<%=urlAction.setAction("uploadPhoto")%>" method="post" onsubmit="submitForm('<%=objUri%><%=sourceCall%>frmUploadPhoto');
                return false;">
                <%= photoMgr.getFormHiddens()%>
                <div class="pub-redes">
                    <p class="titulo">Detalles de la publicaci&oacute;n</p>
                    <p>
                    <div class="etiqueta"><label for="description"><%=Photo.social_msg_Text.getDisplayName()%>:</label></div>
                    <div class="campo"><%=postOut == null ? photoMgr.renderElement(request, Photo.social_msg_Text, photoMgr.MODE_CREATE) : photoMgr.renderElement(request, Photo.social_msg_Text, photoMgr.MODE_EDIT)%></div>
                    </p>
                    <p>
                    <div class="etiqueta"><label for="photo"><%=photoMgr.renderLabel(request, PostImageable.social_hasPhoto, photoMgr.MODE_CREATE)%>: </label></div>
                    <%
                        String formElementStr=photoMgr.getFormElement(PostImageable.social_hasPhoto).renderElement(request, obj2, PostImageable.social_hasPhoto, SWBFormMgr.TYPE_DOJO, SWBFormMgr.MODE_CREATE, lang);
                        formElementStr=formElementStr.replaceFirst("hasPhoto_new", "hasPhoto_new_#swbsocial_" + objUri+sourceCall);
                        formElementStr=formElementStr.replaceFirst("hasPhoto_new_dynamic", "hasPhoto_new_#swbsocial_" + objUri+sourceCall+"_dynamic");
                        //System.out.println("formElementStr:"+formElementStr);
                    %>       
                    <%=formElementStr%>

                    <%
                        if (postIn != null) {
                            socialTopic = postIn.getSocialTopic();
                        } else if (postIn == null && postOut != null) {
                            socialTopic = postOut.getSocialTopic();
                        }
                        if (socialTopic != null) {
                            Iterator<SocialPFlowRef> itSocialPFlowRefs = socialTopic.listInheritPFlowRefs();
                    %> 
                    <div class="etiqueta"><label for="socialFlow"><%=SWBSocialUtil.Util.getStringFromGenericLocale("publishFlow", user.getLanguage())%></label></div>
                    <div class="campo">

                        </p>
                        <select name="socialFlow" id="flu">
                            <%
                                boolean noFlows = true;
                                while (itSocialPFlowRefs.hasNext()) {
                                    SocialPFlowRef socialFlowRef = itSocialPFlowRefs.next();
                                    if (socialFlowRef.isActive()) {
                                        SocialPFlow socialPFlow = socialFlowRef.getPflow();
                                        if (socialPFlow.isActive()) {
                                           if(pfmgr.isManagedByPflow(socialPFlow, Photo.sclass))
                                            {
                                                boolean isChecked = false;
                                                if (postOutPFlowUri != null && postOutPFlowUri.equals(socialPFlow.getURI())) {
                                                    isChecked = true;
                                                }
                                                noFlows = false;
                                                %> 
                                                <option value="<%=socialPFlow.getURI()%>" <%=isChecked ? "selected" : ""%>><%=socialPFlow.getDisplayTitle(lang)%> </option>
                                                <%
                                            }
                                        }
                                    }
                                }
                                if (noFlows) {
                            %>
                            <option value=""><%=SWBSocialUtil.Util.getStringFromGenericLocale("withOutFlow", user.getLanguage())%></option>
                            <%
                                }
                            %>
                        </select>
                    </div>
                    <%if (!noFlows && firstTime) {%>
                    <p>
                    <div class="etiqueta"><label for="socialFlowComment"><%=SWBSocialUtil.Util.getStringFromGenericLocale("comment", user.getLanguage())%></label></div>
                    <div class="campo">
                        <textarea name="socialFlowComment" cols="35" rows="5"></textarea>
                    </div>
                    </p>
                    <%
                            }
                        }
                    %>

                        <button class="submit" type="submit" onclick="return checksRedesPhoto('<%=objUri%>','<%=sourceCall%>',<%=(postInSN == null || postIn != null ? "true" : "false")%>);"><%=SWBSocialUtil.Util.getStringFromGenericLocale("send", user.getLanguage())%></button>
                </div>

                <%
                    if (postInSN == null || postIn != null) {
                %>
                <div class="pub-redes">
                <p class="titulo">Redes disponibles</p>
                <ul><b><%=SWBSocialUtil.Util.getStringFromGenericLocale("chooseSocialNets", user.getLanguage())%></b></ul>
                <%
                    Iterator<SocialNetwork> it = SocialNetwork.ClassMgr.listSocialNetworks(wsite);
                    while (it.hasNext()) {
                        SocialNetwork socialNetwork = (SocialNetwork) it.next();
                        if (socialNetwork instanceof Photoable && socialNetwork.isActive() && socialNetwork.isValid()) {
                            String typeClass = "";
                            boolean isSelected = false;
                            if (apostOutNets.contains(socialNetwork.getURI())) {
                                System.out.println("Net found--:" + socialNetwork);
                                isSelected = true;
                            }
                            if(postIn != null){//If it is a response to some post, show only the nets of the post Type
                                if(!socialNetwork.getClass().equals(postIn.getPostInSocialNetwork().getClass())){
                                    continue;
                                }
                            }
                            if(postOut != null){//If it is a response to some post, show only the nets of the post Type
                                if(postOut.getPostInSource() != null){
                                    if(!socialNetwork.getClass().equals(postOut.getPostInSource().getPostInSocialNetwork().getClass())){
                                        continue;
                                    }
                                }
                            }
                            
                             if (socialNetwork instanceof Youtube){
                                 typeClass = "ico-ytb";
                             }else if (socialNetwork instanceof Facebook){
                                 typeClass = "ico-fcb";
                             }else if (socialNetwork instanceof Twitter){
                                 typeClass = "ico-twt";
                             }
                            
                            String selected = "";
                            if (isSelected) {
                                selected = "checked=\"true\"";
                            }
                %>
                <li class="<%=typeClass%>">
                    <input type="checkbox" id="checkRedes" name="<%=socialNetwork.getURI()%>" <%=selected%> onClick="disableSelect(this);"/>
                    <label for="t1"><span></span><%=socialNetwork.getTitle()%></label>
                    <%
                    if(socialNetwork instanceof Facebook && postIn == null && postOut == null){                        
                    %>
                    <select id="postoutPrivacy" name="postoutPrivacy" style="display:none;" disabled="disabled">
                        <option value="<%=socialNetwork.getURI() + "|PUBLIC"%>">PUBLIC</option>
                        <%
                        for(int i = 0; i < selectFacebook.size(); i++){
                        %>
                            <option value="<%=socialNetwork.getURI() + "|" + selectFacebook.get(i)%>"><%=selectFacebook.get(i)%></option>
                        <%
                        }
                        %>
                    </select>
                    <%
                    }
                    %>
                </li>
                <%
                        }
                    }
                } else {
                    SocialNetwork socialNet = (SocialNetwork) SemanticObject.getSemanticObject(postInSN).createGenericInstance();
                    if (socialNet == null) {
                        return;
                    }
                %>
                <input type="hidden" name="socialNetUri" value="<%=socialNet.getURI()%>"/>
                <%
                    }
                %>
                </div>
            </form>
        </div>
    </div>

<%} else if (contentType.equals("uploadVideo")) {       ///////////////////////////////POSTEO DE VIDEOS/////////////////////////////
        System.out.println("Entra a TypeOfContent..2");
        urlAction.setParameter("toPost", "video");
        
        SWBFormMgr videoMgr =null;
        if (postOut == null) //Creation
        {
            videoMgr = new SWBFormMgr(Video.sclass.getSemanticObject(), null, SWBFormMgr.MODE_CREATE);
        } else //Update
        {
            videoMgr = new SWBFormMgr(postOut.getSemanticObject(), null, SWBFormMgr.MODE_EDIT);
        }
        System.out.println("Entra a TypeOfContent..3:"+videoMgr);
        
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
    <div id="pub-detalle">
       <span class="sel-viddiv"></span>
    <div class="swbform">
        <form dojoType="dijit.form.Form" id="<%=objUri%><%=sourceCall%>frmUploadVideo" action="<%=urlAction.setAction("uploadVideo")%>" method="post" onsubmit="submitForm('<%=objUri%><%=sourceCall%>frmUploadVideo');
                return false;">
            <%= videoMgr.getFormHiddens()%>
            
             <div class="pub-redes">
                 <p class="titulo">Detalles de la publicaci&oacute;n</p>
                    <p>
                      <div id="<%=objUri%><%=sourceCall%>divCategory" style="display:none;" class="etiqueta"><label for="description">Categoría:</label>
                <select name="<%=Video.social_category.getName()%>">
                    <option value="">Selecciona...</option>
                    <%
                        SWBModel model = WebSite.ClassMgr.getWebSite(paramRequest.getWebPage().getWebSiteId());
                        Iterator<YouTubeCategory> itYtube = YouTubeCategory.ClassMgr.listYouTubeCategories(model);
                        while (itYtube.hasNext()) {
                            YouTubeCategory socialCategory = (YouTubeCategory) itYtube.next();
                    %>
                    <option value="<%=socialCategory.getId()%>"><%=socialCategory.getTitle()%></option>
                    <%
                        }
                    %>
                </select>
            </div>
    
                    </p>
                    <p>   
                        <div class="etiqueta"><label for="title"><%=Video.swb_title.getDisplayName()%>: </label></div>
                        <div class="campo"><%=postOut == null ? videoMgr.renderElement(request, Video.swb_title, videoMgr.MODE_CREATE) : videoMgr.renderElement(request, Video.swb_title, videoMgr.MODE_EDIT)%></div>
                    </p>
                    <p>
                       <div class="etiqueta"><label for="description"><%=Video.social_msg_Text.getDisplayName()%>:</label></div>
                       <div class="campo"><%=videoMgr.renderElement(request, Video.social_msg_Text, videoMgr.MODE_CREATE)%></div>
                    </p>
                    <p>
                        <div class="etiqueta"><label for="keywords"><%=Video.swb_Tagable.getDisplayName(lang)%>:</label></div>
                        <div class="campo"><%=videoMgr.renderElement(request, Video.swb_tags, videoMgr.MODE_CREATE)%></div>
                    </p>
                    <p>
                        <div class="etiqueta"><label for="title"><%=videoMgr.renderLabel(request, Video.social_video, videoMgr.MODE_CREATE)%>: </label></div>
                        <div class="campo"><%=videoMgr.getFormElement(Video.social_video).renderElement(request, videoSemObj, Video.social_video, SWBFormMgr.TYPE_DOJO, SWBFormMgr.MODE_CREATE, lang).replaceAll("video_new_defaultAuto", "video_new_defaultAuto" + objUri+sourceCall)%></div>       
                    </p>
                    <!--Show Existing Video-->
                    <%
                    if(postOut!=null)
                    {
                        Video video=(Video)postOut;
                        System.out.println("Entra a TypeOfContent..4:"+video);
                        String videoFormat = "";
                        String videoUrl = video.getVideo();
                        String fileext=null;
                        int pos=videoUrl.lastIndexOf(".");
                        if(pos>-1)
                        {
                            fileext=videoUrl.substring(pos);
                            int pos1=fileext.indexOf("?");
                            if(pos1>-1)
                            {
                                fileext=fileext.substring(0, pos1);
                            }
                        }
                        if (videoUrl.toLowerCase().contains("www.youtube.com")) {//show player from youtube
                            videoFormat = "youtube";
                        } else if (fileext.toLowerCase().equals(".mp4")) {
                            videoFormat = "mp4";
                        } else if (fileext.toLowerCase().equals(".swf") || fileext.toLowerCase().equals(".mov")) { 
                            videoFormat = "flash";
                        } else if (fileext.toLowerCase().equals(".flv")) {
                            videoFormat = "flv";
                        } else if (fileext.toLowerCase().equals(".wav")) {
                            videoFormat = "wav";
                        }else if (fileext.equals(".wmv")) {
                            videoFormat = "wmv";
                        }
                        %>    

                        <%
                            if (videoFormat.equals("flv")) {
                        %>

                        <%=SWBUtils.TEXT.encode(video.getMsg_Text(), "utf8")%>
                        <br>
                        <object id="video" type="application/x-shockwave-flash" data="<%=SWBPlatform.getContextPath()%>/swbadmin/player_flv_maxi.swf" width="400" height="200">
                            <param name="movie" value="<%=SWBPlatform.getContextPath()%>/swbadmin/player_flv_maxi.swf" />
                            <param name="FlashVars" value="flv=<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>"/>
                        </object>
                        <%
                        } else if (videoFormat.equals("flash")) {
                        %>

                        <%=SWBUtils.TEXT.encode(video.getMsg_Text(), "utf8")%>
                        <br>
                        <object width="400" height="200" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"   codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0"> 
                            <param name="SRC" value="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>">
                            <embed src="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>" width="400" height="200"></embed>
                        </object>



                        <%} else if (videoFormat.equals("mp4")) {
                        %>   
                        <%=SWBUtils.TEXT.encode(video.getMsg_Text(), "utf8")%>
                        <br>
                        <video width="400" height="200" controls>
                            <source src="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>" type="video/mp4">
                            <object data="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>" width="400" height="200">
                                <embed src="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>" width="400" height="200" autostart="false">    
                            </object>
                        </video>

                    <%
                    } else if (videoFormat.equals("wav")) {
                    %>
                    <%=SWBUtils.TEXT.encode(video.getMsg_Text(), "utf8")%>
                    <br>
                    <object width="400" height="200" classid="clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B" codebase="http://www.apple.com/qtactivex/qtplugin.cab">
                        <param name="src" value="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>">
                        <param name="controller" value="true">
                    </object>

                    <%
                    } else if (videoFormat.equals("wmv")) {

                    %>    

                    <%=SWBUtils.TEXT.encode(video.getMsg_Text(), "utf8")%>
                    <br>
                    <object width="400" height="200" type="video/x-ms-asf" url="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>" data="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>" classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6">
                        <param name="url" value="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>">
                        <param name="filename" value="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>">
                        <param name="autostart" value="1">
                        <param name="uiMode" value="full">
                        <param name="autosize" value="1">
                        <param name="playcount" value="1"> 
                        <embed type="application/x-mplayer2" src="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>" width="400" height="200" autostart="true" showcontrols="true" pluginspage="http://www.microsoft.com/Windows/MediaPlayer/"></embed>
                    </object>

                    <%
                        }
                    }
                    %>
                 <!--Ends Show Existing Video-->
                <%        
                //Showing SocialFlows
                if (postIn != null) {
                    socialTopic = postIn.getSocialTopic();
                }else if (postIn == null && postOut != null) {
                            socialTopic = postOut.getSocialTopic();
                }
                if (socialTopic != null) {
                    Iterator<SocialPFlowRef> itSocialPFlowRefs = socialTopic.listInheritPFlowRefs();
                %>     
                    <p>

            <div class="etiqueta"><label for="socialFlow"><%=SWBSocialUtil.Util.getStringFromGenericLocale("publishFlow", user.getLanguage())%></label></div>
            <div class="campo">
                <%
                    if (postIn != null) {
                        socialTopic = postIn.getSocialTopic();
                    }
                    if (socialTopic != null) {
                %>
                <select name="socialFlow">
                    <%
                        boolean noFlows = true;
                        while (itSocialPFlowRefs.hasNext()) {
                            SocialPFlowRef socialFlowRef = itSocialPFlowRefs.next();
                            if (socialFlowRef.isActive()) {
                                SocialPFlow socialPFlow = socialFlowRef.getPflow();
                                if (socialPFlow.isActive()) {
                                   if(pfmgr.isManagedByPflow(socialPFlow, Video.sclass))
                                    {
                                        boolean isChecked = false;
                                        if (postOutPFlowUri != null && postOutPFlowUri.equals(socialPFlow.getURI())) {
                                            isChecked = true;
                                        }
                                        noFlows = false;
                                        %> 
                                        <option value="<%=socialPFlow.getURI()%>" <%=isChecked ? "selected" : ""%>><%=socialPFlow.getDisplayTitle(lang)%> </option>
                                        <%
                                    }
                                }
                            }
                        }
                        if (noFlows) {
                    %>
                    <option value=""><%=SWBSocialUtil.Util.getStringFromGenericLocale("withOutFlow", user.getLanguage())%></option>
                    <%
                        }
                    %>
                </select>
            </div>  
                    </p>
                    <p>
                       <%if (!noFlows && firstTime) {%>
            <div class="etiqueta"><label for="socialFlowComment"><%=SWBSocialUtil.Util.getStringFromGenericLocale("comment", user.getLanguage())%></label></div>
            <div class="campo">
                <textarea name="socialFlowComment" cols="35" rows="5"></textarea>
            </div>
            <%
                    }
                }
            %>  
                    </p>
             <button class="submit" type="submit" onclick="return validateChecks('<%=objUri%>','<%=sourceCall%>',<%=(postInSN == null || postIn != null? "true" : "false")%>);"><%=SWBSocialUtil.Util.getStringFromGenericLocale("send", user.getLanguage())%></button>
             </div>
                
            <%
                if (postInSN == null || postIn != null) {
            %>
             <div class="pub-redes">
                <p class="titulo">Redes disponibles</p>
            <ul><b><%=SWBSocialUtil.Util.getStringFromGenericLocale("chooseSocialNets", user.getLanguage())%></b></ul>
            <%
                Iterator<SocialNetwork> it = SocialNetwork.ClassMgr.listSocialNetworks(wsite);
                while (it.hasNext()) {
                    SocialNetwork socialNetwork = (SocialNetwork) it.next();
                    if (socialNetwork instanceof Videoable && socialNetwork.isActive() && socialNetwork.isValid()) {
                    String typeClass = "";
                    boolean isSelected = false;
                    if (apostOutNets.contains(socialNetwork.getURI())) {
                        System.out.println("Net found--:" + socialNetwork);
                        isSelected = true;
                    }
                    
                    if(postIn != null){//If it is a response to some post, show only the nets of the post Type
                        if(!socialNetwork.getClass().equals(postIn.getPostInSocialNetwork().getClass())){
                            continue;
                        }
                    }
                    if(postOut != null){//If it is a response to some post, show only the nets of the post Type
                        if(postOut.getPostInSource() != null){
                            if(!socialNetwork.getClass().equals(postOut.getPostInSource().getPostInSocialNetwork().getClass())){
                                continue;
                            }
                        }
                    }
                    if (socialNetwork instanceof Youtube){
                        typeClass = "ico-ytb";
                    }else if (socialNetwork instanceof Facebook){
                        typeClass = "ico-fcb";
                    }else if (socialNetwork instanceof Twitter){
                        typeClass = "ico-twt";
                    }
                    
                    String selected = "";
                    if (isSelected) {
                        selected = "checked=\"true\"";
                    }
                   
                    if (socialNetwork instanceof Youtube) {
            %>
            <li class="<%=typeClass%>">
                <input id="checkYT" type="checkbox" name="<%=socialNetwork.getURI()%>" onClick="showListCategory('<%=objUri%>','<%=sourceCall%>'); disableSelect(this);" <%=selected%>/>
                <label><span></span><%=socialNetwork.getTitle()%></label>
                <%
                    if(socialNetwork instanceof Youtube && postIn == null && postOut == null){                        
                    %>
                    <select id="postoutPrivacy" name="postoutPrivacy" style="display:none;" disabled="disabled">
                        <option value="<%=socialNetwork.getURI() + "|PUBLIC"%>">PUBLIC</option>
                        <%
                        for(int i = 0; i < selectYoutube.size(); i++){
                        %>
                            <option value="<%=socialNetwork.getURI() + "|" + selectYoutube.get(i)%>"><%=selectYoutube.get(i)%></option>
                        <%
                        }
                        %>
                    </select>
                    <%
                    }
                    %>
            </li>
            <%
                    } else {
            %>
            <li class="<%=typeClass%>">
                <input type="checkbox" id="checkRedes" name="<%=socialNetwork.getURI()%>" <%=selected%> onClick="disableSelect(this);"/>
                <label><span></span><%=socialNetwork.getTitle()%></label>
                <%
                if(socialNetwork instanceof Facebook && postIn == null){                        
                %>
                <select id="postoutPrivacy" name="postoutPrivacy" style="display:none;" disabled="disabled">
                    <option value="<%=socialNetwork.getURI() + "|PUBLIC"%>">PUBLIC</option>
                    <%
                    for(int i = 0; i < selectFacebook.size(); i++){
                    %>
                        <option value="<%=socialNetwork.getURI() + "|" + selectFacebook.get(i)%>"><%=selectFacebook.get(i)%></option>
                    <%
                    }
                    %>
                </select>
                <%
                }
                %>
            </li>
            <%
                        }
                    }
                }
            } else {
                SocialNetwork socialNet = (SocialNetwork) SemanticObject.getSemanticObject(postInSN).createGenericInstance();
                if (socialNet == null) {
                    return;
                }
            %>
            <input type="hidden" name="socialNetUri" value="<%=socialNet.getURI()%>"/>
            <%
                }
            %>
             </div>
        </form>
    </div>
    </div>
    <%
            }
        }
    %>