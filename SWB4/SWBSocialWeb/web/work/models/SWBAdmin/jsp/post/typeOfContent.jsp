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
    System.out.println("****EL source es: " +sourceCall);
    
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
    if (semObj.getSemanticClass().isSubClass(PostIn.social_PostIn)) {
        postIn = (PostIn) semObj.createGenericInstance();
    } else if (semObj.getGenericInstance() instanceof SocialTopic) {
        socialTopic = (SocialTopic) semObj.createGenericInstance();
        firstTime = true;
    } else if (semObj.getSemanticClass().isSubClass(PostOut.social_PostOut)) {
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
            System.out.println("Red de PostOut:" + socialNet);
            apostOutNets.add(socialNet.getURI());
        }
        if (postOut.getPflowInstance() != null && postOut.getPflowInstance().getPflow() != null) {
            postOutPFlowUri = postOut.getPflowInstance().getPflow().getURI();
            System.out.println("postOutPFlowUri++G++:" + postOutPFlowUri);
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
                    <textarea name="socialFlowComment"></textarea>
                </div>
                <%
                        }
                    }

                %>
            
                    <button class="submit" type="submit" onclick="return checksRedesText('<%=objUri%>','<%=sourceCall%>');"><%=SWBSocialUtil.Util.getStringFromGenericLocale("send", user.getLanguage())%></button>
               
            </div>      
            <%
                if (postInSN == null) {
            %>
            <div class="pub-redes">
                <p class="titulo">Redes disponibles</p>
                <ul><b><%=SWBSocialUtil.Util.getStringFromGenericLocale("chooseSocialNets", user.getLanguage())%></b></ul>
                <%
                    Iterator<SocialNetwork> it = SocialNetwork.ClassMgr.listSocialNetworks(wsite);
                    while (it.hasNext()) {
                        SocialNetwork socialNetwork = (SocialNetwork) it.next();
                        if (socialNetwork instanceof Messageable && socialNetwork.isActive()) {
                            boolean isSelected = false;
                            System.out.println("Las Redes:" + socialNetwork);
                            if (apostOutNets.contains(socialNetwork.getURI())) {
                                System.out.println("La Chida--:" + socialNetwork);
                                isSelected = true;
                            }
                            String typeClass = "";
                            if (socialNetwork instanceof Youtube) {
                                typeClass = "ico-ytb";
                            }
                            if (socialNetwork instanceof Facebook){
                                
                             typeClass = "ico-fcb";
                             }
                            if (socialNetwork instanceof Twitter) {
                                typeClass = "ico-twt";
                            }
                            String selected = "";
                            if (isSelected) {
                                selected = "checked=\"true\"";
                            }
                %>
                <li class="<%=typeClass%>">
                    <input type="checkbox" id="checkRedes" name="<%=socialNetwork.getURI()%>" <%=selected%> />
                    <label for="t1"><span></span><%=socialNetwork.getTitle()%></label> 
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
                    <div class="campo"><%=photoMgr.renderElement(request, Photo.social_msg_Text, photoMgr.MODE_CREATE)%></div>
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
                                    SocialPFlow socialPFlow = socialFlowRef.getPflow();
                                    if (socialPFlow.isActive()) {
                                        noFlows = false;
                            %>
                            <option value="<%=socialPFlow.getURI()%>"><%=socialPFlow.getDisplayTitle(lang)%> </option>
                            <%
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
                        <textarea name="socialFlowComment"></textarea>
                    </div>
                    </p>
                    <%
                            }
                        }
                    %>

                        <button class="submit" type="submit" onclick="return checksRedesPhoto('<%=objUri%>','<%=sourceCall%>');"><%=SWBSocialUtil.Util.getStringFromGenericLocale("send", user.getLanguage())%></button>
                </div>

                <%
                    if (postInSN == null) {
                %>
                <div class="pub-redes">
                <p class="titulo">Redes disponibles</p>
                <ul><b><%=SWBSocialUtil.Util.getStringFromGenericLocale("chooseSocialNets", user.getLanguage())%></b></ul>
                <%
                    Iterator<SocialNetwork> it = SocialNetwork.ClassMgr.listSocialNetworks(wsite);
                    while (it.hasNext()) {
                        SocialNetwork socialNetwork = (SocialNetwork) it.next();
                        if (socialNetwork instanceof Photoable && socialNetwork.isActive()) {
                            String typeClass = "";
                             if (socialNetwork instanceof Youtube){
                                 typeClass = "ico-ytb";
                             }
                              if (socialNetwork instanceof Facebook){
                                 typeClass = "ico-fcb";
                             }
                               if (socialNetwork instanceof Twitter){
                                 typeClass = "ico-twt";
                             }
                %>
                <li class="<%=typeClass%>">
                    <input type="checkbox" id="checkRedes" name="<%=socialNetwork.getURI()%>"/>
                    <label for="t1"><span></span><%=socialNetwork.getTitle()%></label>
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
                    <option>Selecciona...</option>
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
        <div class="campo"><%=videoMgr.renderElement(request, Video.swb_title, videoMgr.MODE_CREATE)%></div>
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

            <%if (postIn != null) {
                    socialTopic = postIn.getSocialTopic();
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
                            SocialPFlow socialPFlow = socialFlowRef.getPflow();
                            if (socialPFlow.isActive()) {
                                noFlows = false;
                    %>
                    <option value="<%=socialPFlow.getURI()%>"><%=socialPFlow.getDisplayTitle(lang)%> </option>
                    <%
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
                <textarea name="socialFlowComment"></textarea>
            </div>
            <%
                    }
                }
            %>  
                    </p>
             <button class="submit" type="submit" onclick="return validateChecks('<%=objUri%>','<%=sourceCall%>');"><%=SWBSocialUtil.Util.getStringFromGenericLocale("send", user.getLanguage())%></button>
             </div>
                
            <%
                if (postInSN == null) {
            %>
             <div class="pub-redes">
                <p class="titulo">Redes disponibles</p>
            <ul><b><%=SWBSocialUtil.Util.getStringFromGenericLocale("chooseSocialNets", user.getLanguage())%></b></ul>
            <%
                Iterator<SocialNetwork> it = SocialNetwork.ClassMgr.listSocialNetworks(wsite);
                while (it.hasNext()) {
                    SocialNetwork socialNetwork = (SocialNetwork) it.next();
                    if (socialNetwork instanceof Videoable && socialNetwork.isActive()) {
                    String typeClass = "";
                             if (socialNetwork instanceof Youtube){
                                 typeClass = "ico-ytb";
                             }
                              if (socialNetwork instanceof Facebook){
                                 typeClass = "ico-fcb";
                             }
                               if (socialNetwork instanceof Twitter){
                                 typeClass = "ico-twt";
                             }
                   
                        if (socialNetwork instanceof Youtube) {
            %>
            <li class="<%=typeClass%>">
                <input id="checkYT" type="checkbox" name="<%=socialNetwork.getURI()%>" onClick="showListCategory('<%=objUri%>','<%=sourceCall%>');" />
                <label><span></span><%=socialNetwork.getTitle()%></label>
            </li>
            <%
            } else {
            %>
            <li class="<%=typeClass%>">
                <input type="checkbox" id="checkRedes" name="<%=socialNetwork.getURI()%>" />
                <label><span></span><%=socialNetwork.getTitle()%></label>
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