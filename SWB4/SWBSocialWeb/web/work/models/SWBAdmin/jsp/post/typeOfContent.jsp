<%-- 
    Document   : typeOfContent
    Created on : 20/03/2013, 06:04:12 PM
    Author     : Jorge.Jimenez
    Modified by: Francisco.Jimenez
--%>
<%@page import="org.semanticwb.social.util.SocialLoader"%>
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="org.semanticwb.social.admin.resources.util.SWBSocialResUtil"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%//@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.social.SocialFlow.SocialPFlowMgr"%>
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
    //--
    String username = "";
    SocialNetwork socialNetToUser = null;
    if(request.getParameter("username") != null && request.getParameter("netSuri") != null){
        username = request.getParameter("username");
        socialNetToUser = (SocialNetwork)SemanticObject.createSemanticObject(request.getParameter("netSuri")).createGenericInstance();
    }

    String sourceCall = request.getParameter("source");
    if (sourceCall == null) { //When typeOfContent is called from Tema/Responder the param source is not being sent
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
    ArrayList<String> apostOutCalendars = new ArrayList();
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
        if (postOut.listCalendarRefs().hasNext()) {
            Iterator<CalendarRef> itCalendarRefs = postOut.listCalendarRefs();
            while (itCalendarRefs.hasNext()) {
                CalendarRef calendarRef = itCalendarRefs.next();
                if (calendarRef.isActive() && calendarRef.getCalendar() != null) {
                    apostOutCalendars.add(calendarRef.getCalendar().getURI());
                }
            }
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
    ArrayList<PostOutPrivacy> selectFacebook = new ArrayList<PostOutPrivacy>();
    ArrayList<PostOutPrivacy> selectYoutube = new ArrayList<PostOutPrivacy>();
    Iterator<PostOutPrivacy> postOutPs = PostOutPrivacy.ClassMgr.listPostOutPrivacies();
    while (postOutPs.hasNext()) {
        PostOutPrivacy postOutP = postOutPs.next();
        Iterator<SemanticObject> nets = postOutP.listNetworkTypes();
        while (nets.hasNext()) {
            SemanticObject semObjNetw = nets.next();
            SemanticClass sClass = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(semObjNetw.getURI());
            if (sClass.equals(Facebook.social_Facebook)) {
                if (!postOutP.getId().equals("PUBLIC")) {//Skip default public value
                    selectFacebook.add(postOutP);
                }
            }
            if (sClass.equals(Youtube.social_Youtube)) {
                if (!postOutP.getId().equals("PUBLIC")) {//Skip default public value
                    selectYoutube.add(postOutP);
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
                <p>
                <div class="etiqueta"><label for="msj"><%=Message.social_Message.getDisplayName(lang)%>: </label></div>
                <%
                    int posicion = 0;
                    String postOutNull = messageFormMgr.renderElement(request, Message.social_msg_Text, messageFormMgr.MODE_CREATE);

                    posicion = postOutNull.indexOf("style");
                    //String id = objUri + sourceCall + "_textArea";
                    String id = sourceCall + "_textArea" + objUri;

                    postOutNull = postOutNull.substring(0, posicion - 1) + " required  onKeyDown=\"count('" + id + "','" + id + "_Text')\" onKeyUp=\"count('" + id + "','" + id + "_Text')\" id=\"" + id + "\" " + postOutNull.substring(posicion, postOutNull.length());

                    String postOutNNull = messageFormMgr.renderElement(request, Message.social_msg_Text, messageFormMgr.MODE_EDIT);
                    posicion = postOutNNull.indexOf("style");
                    postOutNNull = postOutNNull.substring(0, posicion - 1) + " required onKeyDown=\"count('" + id + "','" + id + "_Text')\" onKeyUp=\"count('" + id + "','" + id + "_Text')\"  id=\"" + id + "\" " + postOutNNull.substring(posicion, postOutNNull.length());
                    // System.out.println("postOutNNull"+postOutNNull);

                    posicion = postOutNNull.indexOf(">");
                    int posicion1 = postOutNNull.indexOf("</textarea>");
                    String value = postOutNNull.substring(posicion + 1, posicion1);
                    //-- 
                    //TODO: don't replace username this way
                    if(!username.isEmpty() && socialNetToUser != null && socialNetToUser instanceof Twitter){//Must have the username to send a message to some user
                        postOutNull = postOutNull.replace("></textarea>", ">@" + username + "</textarea>");
                    }

                    //System.out.println("imp"+value); 
%>
                                                               <!-- <div class="campo"><%=postOut == null ? messageFormMgr.renderElement(request, Message.social_msg_Text, messageFormMgr.MODE_CREATE) : messageFormMgr.renderElement(request, Message.social_msg_Text, messageFormMgr.MODE_EDIT)%></div>-->
                <div class="campo"><%=postOut == null ? postOutNull : postOutNNull%><br> <%if (postOut == null) {%><input  id="<%=id%>_Text" name="<%=id%>_Text" type="text" size="4" class="nobord" readonly ><label class="labelInfo"><img class="swbIconTwitter" src="/swbadmin/css/images/trans.png"/> 140  <img class="swbIconFacebook" src="/swbadmin/css/images/trans.png"/> 2000  <img class="swbIconYouTube" src="/swbadmin/css/images/trans.png"/> 5000  </label><%} else {%><input id="<%=id%>_Text" name="<%=id%>_Text" type="text" size="4" class="nobord" readonly  value="<%=value.length()%>"><label class="labelInfo"><img class="swbIconTwitter" src="/swbadmin/css/images/trans.png"/> 140  <img class="swbIconFacebook" src="/swbadmin/css/images/trans.png"/> 2000  <img class="swbIconYouTube" src="/swbadmin/css/images/trans.png"/> 5000  </label><%}%></div>
                </p>
                <!--Documents-->
                 <p>
                    <%
                        if (postOut != null) {
                    %>

                    <a href="#" title="Mostrar" onclick="showDialog('<%=urlAction.setAction("showPhotos").setParameter("postOut", postOut.toString())%>','<%=paramRequest.getLocaleString("source")%>'); return false;">Mostrar documentos</a>

                    <%
                        }
                    %>
                </p>
                <p>
                    <div class="etiqueta"><label for="file"><%=messageFormMgr.renderLabel(request, FileAble.social_hasFile, messageFormMgr.MODE_CREATE)%>: </label></div>
                    <%
                        SemanticObject obj2 = new SemanticObject(paramRequest.getWebPage().getWebSite().getSemanticModel(), Message.sclass);
                        String formElementStr = messageFormMgr.getFormElement(FileAble.social_hasFile).renderElement(request, obj2, FileAble.social_hasFile, SWBFormMgr.TYPE_DOJO, SWBFormMgr.MODE_CREATE, lang);
                        formElementStr = formElementStr.replaceFirst("hasFile_new", "hasFile_new_#swbsocial_" + objUri + sourceCall);
                        formElementStr = formElementStr.replaceFirst("hasFile_new_dynamic", "hasFile_new_#swbsocial_" + objUri + sourceCall + "_dynamic");
                    %>       
                    <%=formElementStr%>
                </p>
                <!--Ends Documents-->
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
                <div class="etiqueta"><label for="socialFlow"><%=SWBSocialResUtil.Util.getStringFromGenericLocale("publishFlow", user.getLanguage())%></label></div>
                <div class="campo">
                    <select name="socialFlow" id="flu">
                        <%
                            boolean noFlows = true;
                            while (itSocialPFlowRefs.hasNext()) {
                                SocialPFlowRef socialFlowRef = itSocialPFlowRefs.next();
                                if (socialFlowRef.isActive()) {
                                    SocialPFlow socialPFlow = socialFlowRef.getPflow();
                                    if (socialPFlow.isActive()) {
                                        if (pfmgr.isManagedByPflow(socialPFlow, Message.sclass)) {
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
                        <option value=""><%=SWBSocialResUtil.Util.getStringFromGenericLocale("withOutFlow", user.getLanguage())%></option>
                        <%
                            }
                        %>   
                    </select></p>
                </div>


                <%if (!noFlows && firstTime) {%>
                <div class="etiqueta"><label for="socialFlowComment"><%=SWBSocialResUtil.Util.getStringFromGenericLocale("comment", user.getLanguage())%></label></div>
                <div class="campo">
                    <textarea name="socialFlowComment" id="socialFlowComment" cols="35" rows="5"></textarea>
                </div>
                <%
                        }
                    }

                %>                
                <div id="<%=objUri%><%=sourceCall%>pub-calendar" dojoType="dijit.TitlePane" title="Calendarizar" open="false" duration="150" minSize_="20" splitter_="true" region="bottom">
                    <div class="calendar-fast">
                        <p>Individual</p>
                        <%
                            String date = new Date().toString();
                            String starthour = "";
                            if (postOut != null && postOut.getFastCalendar() != null) {
                                try {
                                    String minutes = "00";
                                    Date initDate = postOut.getFastCalendar().getFc_date();
                                    date = getDateFormat(initDate);
                                    if (initDate.getMinutes() != 0) {
                                        minutes = "" + initDate.getMinutes();
                                    }
                                    String hour = "" + initDate.getHours();
                                    if (hour.length() == 1) {
                                        hour = "0" + hour;
                                    }
                                    starthour = hour + ":" + minutes;
                                } catch (Exception ignore) {
                                }
                            }                            
                            //Current date and time for restrictions
                            Date todayDate = new Date();
                            java.util.Calendar cal = java.util.Calendar.getInstance();
                            cal.setTime(todayDate);
                            int year = cal.get(java.util.Calendar.YEAR);
                            int month = cal.get(java.util.Calendar.MONTH) + 1;
                            int day = cal.get(java.util.Calendar.DAY_OF_MONTH);
                            int hour = cal.get(java.util.Calendar.HOUR_OF_DAY);
                            int minute = cal.get(java.util.Calendar.MINUTE);
                        %>
                        <input type="hidden" id="<%=objUri%>_today_hidden" name="today_hidden" value="<%=year+"-" +month+"-"+day%>"/>
                        <div>Día:<input type="text" name="postOut_inidate" id="<%=objUri%>_postOut_inidate" dojoType="dijit.form.DateTextBox"  size="11" style="width:110px;" hasDownArrow="true" value="<%=date%>" constraints="{min:'<%=year%>-<%=String.format("%02d", month)%>-<%=String.format("%02d", day)%>'}" onchange="removeMin(this, '<%=objUri%>_postOut_starthour', document.getElementById('<%=objUri%>_today_hidden').value, '<%=hour%>', '<%=minute%>');"/>
                            Hora:<input dojoType="dijit.form.TimeTextBox" name="postOut_starthour" id="<%=objUri%>_postOut_starthour"  value="<%=(starthour != null && starthour.trim().length() > 0 ? "T" + starthour + ":00" : "")%>" constraints={formatLength:'short',selector:'timeOnly',timePattern:'HH:mm',min:'T<%=String.format("%02d", hour)%>:<%=String.format("%02d", minute)%>:00'} disabled="true" />
                        </div>
                    </div>
                    <!--Termina Calendario Rapido-->
                    <!--Comienzan Calendarios Avanzados-->

                    <%
                        if (SocialCalendar.ClassMgr.listSocialCalendars(wsite).hasNext()) {
                    %>
                    <div class="calendar-advanced"> 
                        <p>Globales</p>
                        <select name="postOutAdvCal" id="postOutAdvCal" multiple="multiple">
                            <%
                                boolean existSelected = false;
                                Iterator<SocialCalendar> itSocialCalendars = SocialCalendar.ClassMgr.listSocialCalendars(wsite);
                                while (itSocialCalendars.hasNext()) {
                                    String selected = "";
                                    SocialCalendar socialCalendar = itSocialCalendars.next();
                                    if (apostOutCalendars.contains(socialCalendar.getURI())) {
                                        selected = "selected=selected";
                                        existSelected = true;
                                    }
                            %>
                            <option value="<%=socialCalendar.getURI()%>" <%=selected%>><%=socialCalendar.getTitle()%></option>
                            <%
                                }
                            %>
                        </select>
                    </div>
                    <%}%>
                    <!--Terminan Calendarios Avanzados-->
                </div>
                    
                <button class="submit" type="submit" onclick="return checksRedesText('<%=objUri%>','<%=sourceCall%>');"><%=SWBSocialResUtil.Util.getStringFromGenericLocale("send", user.getLanguage())%></button>

            </div>
                <%
                    if (postInSN == null || postIn != null || (!username.isEmpty() && socialNetToUser != null)) {
                %>
                <div class="pub-redes">
                    <p class="titulo">Redes disponibles</p>
                    <ul><b><%=SWBSocialResUtil.Util.getStringFromGenericLocale("chooseSocialNets", user.getLanguage())%></b></ul>
                    <%
                        Iterator<SocialNetwork> it = SocialNetwork.ClassMgr.listSocialNetworks(wsite);
                        while (it.hasNext()) {
                            SocialNetwork socialNetwork = (SocialNetwork) it.next();
                            if (socialNetwork instanceof Messageable && socialNetwork.isActive() && socialNetwork.isValid()) {
                                if (socialNetwork instanceof Youtube && postIn == null && postOut == null) {//Only show youtube networks if is a response
                                    continue;
                                }
                                boolean isSelected = false;
                                //System.out.println("Las Redes:" + socialNetwork);
                                if (apostOutNets.contains(socialNetwork.getURI())) {
                                    //System.out.println("La Chida--:" + socialNetwork);
                                    isSelected = true;
                                }
                                String typeClass = "";
                                if (postIn != null) {//If it is a response to some post, show only the nets of the post Type
                                    if (!socialNetwork.getClass().equals(postIn.getPostInSocialNetwork().getClass())) {
                                        continue;
                                    }
                                }
                                if (postOut != null) {//If it is a response to some post, show only the nets of the post Type
                                    if (postOut.getPostInSource() != null) {
                                        if (!socialNetwork.getClass().equals(postOut.getPostInSource().getPostInSocialNetwork().getClass())) {
                                            continue;
                                        }
                                    }
                                }
                                if (socialNetToUser != null){//Write message to some user
                                    if(socialNetwork.getURI().equals(socialNetToUser.getURI())){
                                        isSelected = true;
                                    }else{                                    
                                        continue;
                                    }
                                }
                                if (socialNetwork instanceof Youtube) {
                                    typeClass = "ico-ytb";
                                } else if (socialNetwork instanceof Facebook) {
                                    typeClass = "ico-fcb";
                                } else if (socialNetwork instanceof Twitter) {
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
                            if (socialNetwork instanceof Facebook && postIn == null && postOut == null) {
                        %>
                        <select id="postoutPrivacy" name="postoutPrivacy" style="display:none;" disabled="disabled">
                            <option value="<%=socialNetwork.getURI() + "|PUBLIC"%>">Público</option>
                            <%
                                for (int i = 0; i < selectFacebook.size(); i++) {
                            %>                            
                            <option value="<%=socialNetwork.getURI() + "|" + selectFacebook.get(i).getId()%>"><%=selectFacebook.get(i).getDisplayTitle(user.getLanguage())%></option>                            
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
 
    <%} else if (contentType.equals("uploadPhoto")) {       ///////////////////////////////POSTEO DE FOTOS/////////////////////////////
        System.out.println("Entro A uploadPhoto");
        urlAction.setParameter("toPost", "photo");
        SWBFormMgr photoMgr = null;
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
                    <%
                        int position = 0;
                        //String id = objUri + sourceCall + "_texAreaPhoto";
                        String id = sourceCall + "_texAreaPhoto" + objUri;

                        String create = photoMgr.renderElement(request, Photo.social_msg_Text, photoMgr.MODE_CREATE);
                        position = create.indexOf("style");
                        create = create.substring(0, position - 1) + " required  onKeyDown=\"count('" + id + "','" + id + "_Text')\" onKeyUp=\"count('" + id + "','" + id + "_Text')\" id=\"" + id + "\" " + create.substring(position, create.length());


                        String edit = photoMgr.renderElement(request, Photo.social_msg_Text, photoMgr.MODE_EDIT);
                        position = edit.indexOf("style");
                        edit = edit.substring(0, position - 1) + " required onKeyDown=\"count('" + id + "','" + id + "_Text')\" onKeyUp=\"count('" + id + "','" + id + "_Text')\"  id=\"" + id + "\" " + edit.substring(position, edit.length());
                        position = edit.indexOf(">");
                        int posicion1 = edit.indexOf("</textarea>");
                        String value = edit.substring(position + 1, posicion1);
                        //-- 
                        //TODO: don't replace username this way
                        if(!username.isEmpty() && socialNetToUser != null && socialNetToUser instanceof Twitter){//Must have the username to send a message to some user
                            create = create.replace("></textarea>", ">@" + username + "</textarea>");
                        }
                    %>
                   <!-- <div class="campo"><%=postOut == null ? photoMgr.renderElement(request, Photo.social_msg_Text, photoMgr.MODE_CREATE) : photoMgr.renderElement(request, Photo.social_msg_Text, photoMgr.MODE_EDIT)%></div>-->
                    <div class="campo"><%=postOut == null ? create : edit%> <%if (postOut == null) {%><input  id="<%=id%>_Text" name="<%=id%>_Text" type="text" size="4"class="nobord" readonly > <label class="labelInfo"><img class="swbIconTwitter" src="/swbadmin/css/images/trans.png"/> 140  <img class="swbIconFacebook" src="/swbadmin/css/images/trans.png"/> 2000  <img class="swbIconYouTube" src="/swbadmin/css/images/trans.png"/> 5000  </label><%} else {%><br><input id="<%=id%>_Text" name="<%=id%>_Text" type="text" size="4" class="nobord" readonly value="<%=value.length()%>"><label class="labelInfo"><img class="swbIconTwitter" src="/swbadmin/css/images/trans.png"/> 140  <img class="swbIconFacebook" src="/swbadmin/css/images/trans.png"/> 2000  <img class="swbIconYouTube" src="/swbadmin/css/images/trans.png"/> 5000  </label><%}%></div>
                    </p>
                    <p>
                        <%
                            if (postOut != null) {
                        %>

                        <a href="#" title="Mostrar" onclick="showDialog('<%=urlAction.setAction("showPhotos").setParameter("postOut", postOut.toString())%>','<%=paramRequest.getLocaleString("source")%>'); return false;">Mostrar fotos</a>

                        <%
                            }
                        %>
                    </p>
                    <p>

                    <div class="etiqueta"><label for="photo"><%=photoMgr.renderLabel(request, PostImageable.social_hasPhoto, photoMgr.MODE_CREATE)%>: </label></div>
                    <%
                        String formElementStr = photoMgr.getFormElement(PostImageable.social_hasPhoto).renderElement(request, obj2, PostImageable.social_hasPhoto, SWBFormMgr.TYPE_DOJO, SWBFormMgr.MODE_CREATE, lang);
                        formElementStr = formElementStr.replaceFirst("hasPhoto_new", "hasPhoto_new_#swbsocial_" + objUri + sourceCall);
                        formElementStr = formElementStr.replaceFirst("hasPhoto_new_dynamic", "hasPhoto_new_#swbsocial_" + objUri + sourceCall + "_dynamic");
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
                    <div class="etiqueta"><label for="socialFlow"><%=SWBSocialResUtil.Util.getStringFromGenericLocale("publishFlow", user.getLanguage())%></label></div>
                    <div class="campo">
                        </p>
                        <select name="socialFlow" id="flu">
                            <%
                                System.out.println("select");
                                boolean noFlows = true;
                                while (itSocialPFlowRefs.hasNext()) {
                                    SocialPFlowRef socialFlowRef = itSocialPFlowRefs.next();
                                    if (socialFlowRef.isActive()) {
                                        SocialPFlow socialPFlow = socialFlowRef.getPflow();
                                        if (socialPFlow.isActive()) {
                                            if (pfmgr.isManagedByPflow(socialPFlow, Photo.sclass)) {
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
                            <option value=""><%=SWBSocialResUtil.Util.getStringFromGenericLocale("withOutFlow", user.getLanguage())%></option>
                            <%
                                }
                            %>
                        </select>
                    </div>
                    <%if (!noFlows && firstTime) {%>
                    <p>
                    <div class="etiqueta"><label for="socialFlowComment"><%=SWBSocialResUtil.Util.getStringFromGenericLocale("comment", user.getLanguage())%></label></div>
                    <div class="campo">
                        <textarea name="socialFlowComment" id="socialFlowComment" cols="35" rows="5"></textarea>
                    </div>
                    </p>
                    <%
                            }
                        }
                    %>
                    <!--Calendario Rapido-->

                    <div class="etiqueta"><label>Calendarios de envío: </label></div>
                    <div id="<%=objUri%><%=sourceCall%>pub-calendar" dojoType="dijit.TitlePane" title="Calendarizar" open="false" duration="150" minSize_="20" splitter_="true" region="bottom">
                        <div class="calendar-fast">
                            <p>Individual</p>
                            <%
                                String date = new Date().toString();
                                String starthour = "";
                                if (postOut != null && postOut.getFastCalendar() != null) {
                                    try {
                                        String minutes = "00";
                                        Date initDate = postOut.getFastCalendar().getFc_date();
                                        date = getDateFormat(initDate);
                                        if (initDate.getMinutes() != 0) {
                                            minutes = "" + initDate.getMinutes();
                                        }
                                        String hour = "" + initDate.getHours();
                                        if (hour.length() == 1) {
                                            hour = "0" + hour;
                                        }
                                        starthour = hour + ":" + minutes;
                                    } catch (Exception ignore) {
                                    }
                                }
                                //Current date and time for restrictions
                                Date todayDate = new Date();
                                java.util.Calendar cal = java.util.Calendar.getInstance();
                                cal.setTime(todayDate);
                                int year = cal.get(java.util.Calendar.YEAR);
                                int month = cal.get(java.util.Calendar.MONTH) + 1;
                                int day = cal.get(java.util.Calendar.DAY_OF_MONTH);
                                int hour = cal.get(java.util.Calendar.HOUR_OF_DAY);
                                int minute = cal.get(java.util.Calendar.MINUTE);
                            %>
                            <input type="hidden" id="<%=objUri%>_today_hidden" name="today_hidden" value="<%=year+"-" +month+"-"+day%>" />
                            <div>Día:<input type="text" name="postOut_inidate" id="<%=objUri%>_postOut_inidate" dojoType="dijit.form.DateTextBox"  size="11" style="width:110px;" hasDownArrow="true" value="<%=date%>" constraints="{min:'<%=year%>-<%=String.format("%02d", month)%>-<%=String.format("%02d", day)%>'}" onchange="removeMin(this, '<%=objUri%>_postOut_starthour', document.getElementById('<%=objUri%>_today_hidden').value, '<%=hour%>', '<%=minute%>');"/>
                                Hora:<input dojoType="dijit.form.TimeTextBox" name="postOut_starthour" id="<%=objUri%>_postOut_starthour"  value="<%=(starthour != null && starthour.trim().length() > 0 ? "T" + starthour + ":00" : "")%>" constraints=constraints={formatLength:'short',selector:'timeOnly',timePattern:'HH:mm',min:'T<%=String.format("%02d", hour)%>:<%=String.format("%02d", minute)%>:00'} disabled="true" />
                            </div>
                        </div>
                        <!--Termina Calendario Rapido-->
                        <%
                            if (SocialCalendar.ClassMgr.listSocialCalendars(wsite).hasNext()) {
                        %>

                        <!--Comienzan Calendarios Avanzados-->
                        <div class="calendar-advanced"> 
                            <p>Globales</p>   

                            <select name="postOutAdvCal" id="postOutAdvCal" multiple="multiple">
                                <%
                                    boolean existSelected = false;
                                    Iterator<SocialCalendar> itSocialCalendars = SocialCalendar.ClassMgr.listSocialCalendars(wsite);
                                    while (itSocialCalendars.hasNext()) {
                                        String selected = "";
                                        SocialCalendar socialCalendar = itSocialCalendars.next();
                                        if (apostOutCalendars.contains(socialCalendar.getURI())) {
                                            selected = "selected=selected";
                                            existSelected = true;
                                        }
                                %>
                                <option value="<%=socialCalendar.getURI()%>" <%=selected%>><%=socialCalendar.getTitle()%></option>
                                <%
                                    }
                                %>
                            </select>
                        </div>
                        <%}%>
                        <!--Terminan Calendarios Avanzados-->
                    </div>
                    <button class="submit" type="submit" onclick="return validateImages('hasPhoto_new_#swbsocial_<%=objUri + sourceCall%>_dynamic','<%=objUri + sourceCall%>frmUploadPhoto');"><%=SWBSocialResUtil.Util.getStringFromGenericLocale("send", user.getLanguage())%></button>
                    <!--<button class="submit" type="submit" onclick="return validateTypeFile('hasPhoto_new_dynamic4'); checksRedesPhoto('<%=objUri%>','<%=sourceCall%>',<%=(postInSN == null || postIn != null ? "true" : "false")%>);"><%=SWBSocialResUtil.Util.getStringFromGenericLocale("send", user.getLanguage())%></button>-->                    
                </div>

                    <%
                        if (postInSN == null || postIn != null) {
                    %>
                    <div class="pub-redes">
                        <p class="titulo">Redes disponibles</p>
                        <ul><b><%=SWBSocialResUtil.Util.getStringFromGenericLocale("chooseSocialNets", user.getLanguage())%></b></ul>
                        <%
                            System.out.println("redes disponibles");
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
                                    if (postIn != null) {//If it is a response to some post, show only the nets of the post Type
                                        if (!socialNetwork.getClass().equals(postIn.getPostInSocialNetwork().getClass())) {
                                            continue;
                                        }
                                    }
                                    if (postOut != null) {//If it is a response to some post, show only the nets of the post Type
                                        if (postOut.getPostInSource() != null) {
                                            if (!socialNetwork.getClass().equals(postOut.getPostInSource().getPostInSocialNetwork().getClass())) {
                                                continue;
                                            }
                                        }
                                    }
                                    if (socialNetToUser != null){//Write message to some user
                                        if(socialNetwork.getURI().equals(socialNetToUser.getURI())){
                                            isSelected = true;
                                        }else{
                                            continue;
                                        }
                                    }
                                    if (socialNetwork instanceof Youtube) {
                                        typeClass = "ico-ytb";
                                    } else if (socialNetwork instanceof Facebook) {
                                        typeClass = "ico-fcb";
                                    } else if (socialNetwork instanceof Twitter) {
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
                                if (socialNetwork instanceof Facebook && postIn == null && postOut == null) {
                            %>
                            <select id="postoutPrivacy" name="postoutPrivacy" style="display:none;" disabled="disabled">
                                <option value="<%=socialNetwork.getURI() + "|PUBLIC"%>">PUBLIC</option>
                                <%
                                    for (int i = 0; i < selectFacebook.size(); i++) {
                                %>
                                <option value="<%=socialNetwork.getURI() + "|" + selectFacebook.get(i)%>"><%=selectFacebook.get(i).getDisplayTitle(user.getLanguage())%></option>
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
    


    <%} else if (contentType.equals("uploadVideo")) {       ///////////////////////////////POSTEO DE VIDEOS/////////////////////////////
        System.out.println("Entra a TypeOfContent..2");
        urlAction.setParameter("toPost", "video");

        SWBFormMgr videoMgr = null;
        if (postOut == null) //Creation
        {
            videoMgr = new SWBFormMgr(Video.sclass.getSemanticObject(), null, SWBFormMgr.MODE_CREATE);
        } else //Update
        {
            videoMgr = new SWBFormMgr(postOut.getSemanticObject(), null, SWBFormMgr.MODE_EDIT);
        }
        System.out.println("Entra a TypeOfContent..3:" + videoMgr);

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
                        <%
                            int position = 0;
                            //String id = objUri + sourceCall + "_texAreaVideo";
                            String id = sourceCall + "_texAreaVideo" + objUri;

                            String create = videoMgr.renderElement(request, Video.social_msg_Text, videoMgr.MODE_CREATE);
                            position = create.indexOf("style");
                            create = create.substring(0, position - 1) + " required  onKeyDown=\"count('" + id + "','" + id + "_Text')\" onKeyUp=\"count('" + id + "','" + id + "_Text')\" id=\"" + id + "\" " + create.substring(position, create.length());
                            position = create.indexOf(">");
                            int posicion1 = create.indexOf("</textarea>");
                            String value = create.substring(position + 1, posicion1);
                        %>
                    <div class="etiqueta"><label for="description"><%=Video.social_msg_Text.getDisplayName()%>:</label></div>
                    <div class="campo"><%=create%><br><input id="<%=id%>_Text" name="<%=id%>_Text" type="text" size="4" class="nobord" readonly value="<%=value.length()%>"><label class="labelInfo"><img class="swbIconTwitter" src="/swbadmin/css/images/trans.png"/> 140  <img class="swbIconFacebook" src="/swbadmin/css/images/trans.png"/> 2000  <img class="swbIconYouTube" src="/swbadmin/css/images/trans.png"/> 5000  </label></div>
                    </p>
                    <p>
                    <div class="etiqueta"><label for="keywords">Palabras clave:</label></div>
                    <div class="campo"><%=videoMgr.renderElement(request, Video.swb_tags, videoMgr.MODE_CREATE)%></div>
                    </p>
                    <p>
                    <div class="etiqueta"><label for="title"><%=videoMgr.renderLabel(request, Video.social_video, videoMgr.MODE_CREATE)%>: </label></div>

                    <%
                        String fileUpload = videoMgr.getFormElement(Video.social_video).renderElement(request, videoSemObj, Video.social_video, SWBFormMgr.TYPE_DOJO, SWBFormMgr.MODE_CREATE, lang).replaceAll("video_new_defaultAuto", "video_new_defaultAuto" + objUri + sourceCall);

                        // Removemos la propiedad startup
                        int nuevo = fileUpload.indexOf("startup");
                        int posicion = fileUpload.indexOf("fileMask");
                        String new1 = fileUpload.substring(0, nuevo);
                        String new2 = fileUpload.substring(posicion, fileUpload.length());
                        fileUpload = new1 + new2;

                    %>
                    <div class="campo"><%=fileUpload%></div>       

                    </p>
                    <!--Show Existing Video-->
                    <%
                        if (postOut != null) {
                            Video video = (Video) postOut;
                            System.out.println("Entra a TypeOfContent..4:" + video);
                            String videoFormat = "";
                            String videoUrl = video.getVideo();
                            String fileext = null;
                            int pos = videoUrl.lastIndexOf(".");
                            if (pos > -1) {
                                fileext = videoUrl.substring(pos);
                                int pos1 = fileext.indexOf("?");
                                if (pos1 > -1) {
                                    fileext = fileext.substring(0, pos1);
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
                            } else if (fileext.equals(".wmv")) {
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
                        } else if (postIn == null && postOut != null) {
                            socialTopic = postOut.getSocialTopic();
                        }
                        if (socialTopic != null) {
                            Iterator<SocialPFlowRef> itSocialPFlowRefs = socialTopic.listInheritPFlowRefs();
                    %>     
                    <p>

                    <div class="etiqueta"><label for="socialFlow"><%=SWBSocialResUtil.Util.getStringFromGenericLocale("publishFlow", user.getLanguage())%></label></div>
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
                                            if (pfmgr.isManagedByPflow(socialPFlow, Video.sclass)) {
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
                            <option value=""><%=SWBSocialResUtil.Util.getStringFromGenericLocale("withOutFlow", user.getLanguage())%></option>
                            <%
                                }
                            %>
                        </select>
                    </div>  
                    </p>
                    <p>
                        <%if (!noFlows && firstTime) {%>
                    <div class="etiqueta"><label for="socialFlowComment"><%=SWBSocialResUtil.Util.getStringFromGenericLocale("comment", user.getLanguage())%></label></div>
                    <div class="campo">
                        <textarea name="socialFlowComment" id="socialFlowComment" cols="35" rows="5"></textarea>
                    </div>
                    <%
                            }
                        }
                    %>  
                    </p>
                    <!--Calendario Rapido-->
                    <div class="etiqueta"><label>Calendarios de envío: </label></div>

                    <div id="<%=objUri%><%=sourceCall%>pub-calendar" dojoType="dijit.TitlePane" title="Calendarizar" open="false" duration="150" minSize_="20" splitter_="true" region="bottom">
                        <div class="calendar-fast">                            
                            <p>Individual</p>
                            <%
                                String date = new Date().toString();
                                String starthour = "";
                                if (postOut != null && postOut.getFastCalendar() != null) {
                                    try {
                                        String minutes = "00";
                                        Date initDate = postOut.getFastCalendar().getFc_date();
                                        date = getDateFormat(initDate);
                                        if (initDate.getMinutes() != 0) {
                                            minutes = "" + initDate.getMinutes();
                                        }
                                        String hour = "" + initDate.getHours();
                                        if (hour.length() == 1) {
                                            hour = "0" + hour;
                                        }
                                        starthour = hour + ":" + minutes;
                                    } catch (Exception ignore) {
                                    }
                                }
                                //Current date and time for restrictions
                                Date todayDate = new Date();
                                java.util.Calendar cal = java.util.Calendar.getInstance();
                                cal.setTime(todayDate);
                                int year = cal.get(java.util.Calendar.YEAR);
                                int month = cal.get(java.util.Calendar.MONTH) + 1;
                                int day = cal.get(java.util.Calendar.DAY_OF_MONTH);
                                int hour = cal.get(java.util.Calendar.HOUR_OF_DAY);
                                int minute = cal.get(java.util.Calendar.MINUTE);
                            %>
                            <input type="hidden" id="<%=objUri%>_today_hidden" name="today_hidden" value="<%=year+"-" +month+"-"+day%>" />
                            <div>Día:<input type="text" name="postOut_inidate" id="<%=objUri%>_postOut_inidate" dojoType="dijit.form.DateTextBox"  size="11" style="width:110px;" hasDownArrow="true" value="<%=date%>" constraints="{min:'<%=year%>-<%=String.format("%02d", month)%>-<%=String.format("%02d", day)%>'}" onchange="removeMin(this, '<%=objUri%>_postOut_starthour', document.getElementById('<%=objUri%>_today_hidden').value, '<%=hour%>', '<%=minute%>');">
                                Hora:<input dojoType="dijit.form.TimeTextBox" name="postOut_starthour" id="<%=objUri%>_postOut_starthour"  value="<%=(starthour != null && starthour.trim().length() > 0 ? "T" + starthour + ":00" : "")%>" constraints=constraints={formatLength:'short',selector:'timeOnly',timePattern:'HH:mm',min:'T<%=String.format("%02d", hour)%>:<%=String.format("%02d", minute)%>:00'} disabled="true" />
                            </div>
                        </div>
                        <!--Termina Calendario Rapido-->
                        <%
                            if (SocialCalendar.ClassMgr.listSocialCalendars(wsite).hasNext()) {
                        %>
                        <!--Comienzan Calendarios Avanzados-->
                        <div class="calendar-advanced"> 
                            <p>Globales</p>   
                            <select name="postOutAdvCal" id="postOutAdvCal" multiple="multiple">
                                <%
                                    boolean existSelected = false;
                                    Iterator<SocialCalendar> itSocialCalendars = SocialCalendar.ClassMgr.listSocialCalendars(wsite);
                                    while (itSocialCalendars.hasNext()) {
                                        String selected = "";
                                        SocialCalendar socialCalendar = itSocialCalendars.next();
                                        if (apostOutCalendars.contains(socialCalendar.getURI())) {
                                            selected = "selected=selected";
                                            existSelected = true;
                                        }
                                %>
                                <option value="<%=socialCalendar.getURI()%>" <%=selected%>><%=socialCalendar.getTitle()%></option>
                                <%
                                    }
                                %>
                            </select>
                        </div>
                        <%}%>
                        <!--Terminan Calendarios Avanzados-->
                    </div>
                    
                    <button class="submit" type="submit" onclick="return validateVideo('<%="video_new_defaultAuto" + objUri + sourceCall%>', '<%=objUri + sourceCall%>frmUploadVideo')"><%=SWBSocialResUtil.Util.getStringFromGenericLocale("send", user.getLanguage())%></button>
                </div>

                    <%
                        if (postInSN == null || postIn != null) {
                    %>
                    <div class="pub-redes">
                        <p class="titulo">Redes disponibles</p>
                        <ul><b>selecciona las redes a las cuales deseas publicar</b></ul>
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

                                    if (postIn != null) {//If it is a response to some post, show only the nets of the post Type
                                        if (!socialNetwork.getClass().equals(postIn.getPostInSocialNetwork().getClass())) {
                                            continue;
                                        }
                                    }
                                    if (postOut != null) {//If it is a response to some post, show only the nets of the post Type
                                        if (postOut.getPostInSource() != null) {
                                            if (!socialNetwork.getClass().equals(postOut.getPostInSource().getPostInSocialNetwork().getClass())) {
                                                continue;
                                            }
                                        }
                                    }
                                    if (socialNetwork instanceof Youtube) {
                                        typeClass = "ico-ytb";
                                    } else if (socialNetwork instanceof Facebook) {
                                        typeClass = "ico-fcb";
                                    } else if (socialNetwork instanceof Twitter) {
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
                                if (socialNetwork instanceof Youtube && postIn == null && postOut == null) {
                            %>
                            <select id="postoutPrivacy" name="postoutPrivacy" style="display:none;" disabled="disabled">
                                <option value="<%=socialNetwork.getURI() + "|PUBLIC"%>">PUBLIC</option>
                                <%
                                    for (int i = 0; i < selectYoutube.size(); i++) {
                                %>
                                <option value="<%=socialNetwork.getURI() + "|" + selectYoutube.get(i)%>"><%=selectYoutube.get(i).getDisplayTitle(user.getLanguage())%></option>
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
                                if (socialNetwork instanceof Facebook && postIn == null) {
                            %>
                            <select id="postoutPrivacy" name="postoutPrivacy" style="display:none;" disabled="disabled">
                                <option value="<%=socialNetwork.getURI() + "|PUBLIC"%>">PUBLIC</option>
                                <%
                                    for (int i = 0; i < selectFacebook.size(); i++) {
                                %>
                                <option value="<%=socialNetwork.getURI() + "|" + selectFacebook.get(i)%>"><%=selectFacebook.get(i).getDisplayTitle(user.getLanguage())%></option>
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

    <%
            }
        }
    %>


    <%!
        private String getDateFormat(Date date) {
            StringTokenizer st = new StringTokenizer(date.toString(), "-");
            String nf = date.toString();
            String y = "";
            String m = "";
            String d = "";
            if (st.hasMoreTokens()) {
                y = st.nextToken();
                if (st.hasMoreTokens()) {
                    m = st.nextToken();
                }
                if (st.hasMoreTokens()) {
                    d = st.nextToken();
                    int pos = -1;
                    pos = d.indexOf(" ");
                    if (pos > -1) {
                        d = d.substring(0, pos);
                    }
                }
                nf = y + "-" + m + "-" + d;
            }
            return nf;
        }
    %>      