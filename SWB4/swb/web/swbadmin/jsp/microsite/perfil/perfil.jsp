<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>

     <%
        String registryPath=paramRequest.getWebPage().getWebSite().getWebPage("Registro_de_Usuarios").getUrl();
        String perfilPath=paramRequest.getWebPage().getWebSite().getWebPage("perfil").getUrl();
        HashMap <String, SemanticProperty> mapa = new HashMap();
        Iterator<SemanticProperty> list = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#_ExtendedAttributes").listProperties();
        while(list.hasNext()){
            SemanticProperty sp=list.next();
            mapa.put(sp.getName(),sp);
        }
        boolean isStrategy=false;
        if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY) isStrategy=true;

        boolean areFriends=false;
        SWBResourceURL urlAction=paramRequest.getActionUrl();
        WebPage wpage=paramRequest.getWebPage();
        User owner=paramRequest.getUser();
        User user=owner;
        if(request.getParameter("user")!=null) 
        {
            SemanticObject semObj=SemanticObject.createSemanticObject(request.getParameter("user"));
            user=(User)semObj.createGenericInstance();
        }
        if(!owner.isRegistered() || !user.isRegistered()) return;

        if(request.getParameter("changePhoto")!=null && request.getParameter("changePhoto").equals("1") && !isStrategy){
            
           %>
            <script type="text/javascript">
                var uploads_in_progress = 0;

                function beginAsyncUpload(ul,sid) {
                  ul.form.submit();
                    uploads_in_progress = uploads_in_progress + 1;
                    var pb = document.getElementById(ul.name + "_progress");
                    pb.parentNode.style.display='block';
                    new ProgressTracker(sid,{
                        progressBar: pb,
                        onComplete: function() {
                            var inp_id = pb.id.replace("_progress","");
                            uploads_in_progress = uploads_in_progress - 1;
                            var inp = document.getElementById(inp_id);
                            if(inp) {
                                inp.value = sid;
                            }
                            pb.parentNode.style.display='none';
                            document.location="<%=perfilPath%>";
                        },
                        onFailure: function(msg) {
                            pb.parentNode.style.display='none';
                            alert(msg);
                            uploads_in_progress = uploads_in_progress - 1;
                        },
                        url: '<%=registryPath%>/_rid/46/_mto/3/_mod/help'
                    });
                }

             </script>

                <form id="fupload" name="fupload" enctype="multipart/form-data" class="swbform" dojoType="dijit.form.Form"
                        action="<%=registryPath%>/_aid/46/_mto/3/_act/upload"
                        method="post" target="pictureTransferFrame" >
                        <fieldset>
                            <legend>Cambiar fotograf&iacute;a de perfil</legend>
                            <br/><br/>
                            <table>
                                <tr><td width="80px" align="right"><label for="picture">Fotograf&iacute;a &nbsp;</label></td>
                                    <td><iframe id="pictureTransferFrame" name="pictureTransferFrame" src="" style="display:none" ></iframe>
                                        <input type="file" name="picture" onchange="beginAsyncUpload(this,'picture');" size="30"/>
                                        <div class="progresscontainer" style="display: none;"><div class="progressbar" id="picture_progress"></div></div>
                                    </td></tr>
                                    <tr><td colspan="2" align="center"><br/><br/><input type="submit" value="enviar"></td></tr>
                            </table>
                        </fieldset>
                </form>
           <%
          }else{


            if(!owner.getURI().equals(user.getURI())
                    && Friendship.areFriends(owner, user, paramRequest.getWebPage().getWebSite())) areFriends=true;


            if(isStrategy)
            {
              if(areFriends){ //Si el usuario que esta en session(owner) es diferente que el que vino por parametro (user)
                  urlAction.setAction("remFriendRelship");
                  urlAction.setParameter("user", user.getURI());
                  %>
                    <p class="addOn"><a href="<%=urlAction%>">Eliminar como amigo</a></p>
                  <%
              }else if(!owner.getURI().equals(user.getURI()) && !FriendshipProspect.findFriendProspectedByRequester(owner, user, wpage.getWebSite())){
                  urlAction.setAction("addFriendRelship");
                  urlAction.setParameter("user", user.getURI());
                  %>
                    <p class="addOn"><a href="<%=urlAction%>">Agregar como amigo</a></p>
                  <%
              }
              if(!owner.getURI().equals(user.getURI())){
                %>
                  <p class="addOn"><a href="<%=perfilPath%>">Mi perfil</a></p>
                <%
              }
            }else {
                 String email="", age="", sex="", userStatus="",userInterest="",userHobbies="",userInciso="";

                 if(user.getEmail()!=null) email=user.getEmail();
                 if(user.getExtendedAttribute(mapa.get("userAge"))!=null) age=""+user.getExtendedAttribute(mapa.get("userAge"));
                 if(user.getExtendedAttribute(mapa.get("userSex"))!=null) sex=""+user.getExtendedAttribute(mapa.get("userSex"));
                 if(user.getExtendedAttribute(mapa.get("userStatus"))!=null) userStatus=""+user.getExtendedAttribute(mapa.get("userStatus"));
                 if(user.getExtendedAttribute(mapa.get("userInterest"))!=null) userInterest=""+user.getExtendedAttribute(mapa.get("userInterest"));
                 if(user.getExtendedAttribute(mapa.get("userHobbies"))!=null) userHobbies=""+user.getExtendedAttribute(mapa.get("userHobbies"));
                 if(user.getExtendedAttribute(mapa.get("userInciso"))!=null) userInciso=""+user.getExtendedAttribute(mapa.get("userInciso"));
                 if(sex.equalsIgnoreCase("M"))
                 {
                     sex="Masculino";
                 }
                 else
                 {
                     sex="Femenino";
                 }
                 %>
                 <div id="tabs">
          <div id="TabbedPanels1" class="TabbedPanels">
            <div class="TabbedPanelsContentGroup">
              <div class="TabbedPanelsContent">
              <h2 class="h2oculto">Información</h2>

                 <div id="informacionPersonal">
                     <div class="editarInfo">
                    <p><a href="<%=registryPath%>">Editar información</a></p>
                    </div>
                 <div class="clear">&nbsp;</div>
                 <h3><%=user.getFullName()%></h3>                                      
                     <%
                        if(owner==user || areFriends){ //Agregar datos privados (email, sexo, fotos, etc)
                            %>                                
                                <table width="100%" border="0" cellspacing="10" cellpadding="0">
                                    <tr><th valign="top">E-mail</th><td><p><%=email%></p></td></tr>
                                    <tr><th valign="top">Edad</th><td><p><%=age%></p></td></tr>
                                    <tr><th valign="top">Sexo</th><td><p><%=sex%></p></td></tr>
                                    <tr><th valign="top">Estado Civil</th><td><p><%=userStatus%></p></td></tr>
                                    <tr><th valign="top">Intereses</th><td><p><%=userInterest%></p></td></tr>
                                    <tr><th valign="top">Hobbies</th><td><p><%=userHobbies%></p></td></tr>
                                    <tr><th valign="top">Inciso</th><td><p><%=userInciso%></p></td></tr>
                               </table>                               
                            <%
                        }
                     %>
                 
                  </div>
                     <div class="clear">&nbsp;</div>
                     <div class="editarInfo">
                    <p><a href="<%=registryPath%>">Editar información</a></p>
                    </div>
              </div>
                                   </div>

                                   </div>
              </div>

                  <%
                }
          }
        %>
        