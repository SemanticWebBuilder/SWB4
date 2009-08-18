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
                <form id="fupload" name="fupload" enctype="multipart/form-data" class="swbform" dojoType="dijit.form.Form"
                        action="/swb/Ciudad_Digital/Registro_de_Usuarios/_aid/46/_mto/3/_act/upload"
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
            }else {
                 String userFirstName="", userLastName="", secondName="", email="", age="", sex="", userStatus="",userInterest="",userHobbies="",userInciso="";

                 if(user.getFirstName()!=null) userFirstName=user.getFirstName();
                 if(user.getLastName()!=null) userLastName=user.getLastName();
                 if(user.getSecondLastName()!=null) secondName=user.getSecondLastName();
                 if(user.getEmail()!=null) email=user.getEmail();
                 if(user.getExtendedAttribute(mapa.get("userAge"))!=null) age=""+user.getExtendedAttribute(mapa.get("userAge"));
                 if(user.getExtendedAttribute(mapa.get("userSex"))!=null) sex=""+user.getExtendedAttribute(mapa.get("userSex"));
                 if(user.getExtendedAttribute(mapa.get("userStatus"))!=null) userStatus=""+user.getExtendedAttribute(mapa.get("userStatus"));
                 if(user.getExtendedAttribute(mapa.get("userInterest"))!=null) userInterest=""+user.getExtendedAttribute(mapa.get("userInterest"));
                 if(user.getExtendedAttribute(mapa.get("userHobbies"))!=null) userHobbies=""+user.getExtendedAttribute(mapa.get("userHobbies"));
                 if(user.getExtendedAttribute(mapa.get("userInciso"))!=null) userInciso=""+user.getExtendedAttribute(mapa.get("userInciso"));
                 %>

                 <table>
                     <tr>
                         <td><br/><br/>
                             <%=userFirstName%> <%=userLastName%> <%=secondName%><br/>
                         </td>
                     </tr>
                     <%
                        if(owner==user || areFriends){ //Agregar datos privados (email, sexo, fotos, etc)
                            %>
                                <tr><td><br/><br/></br>
                                <table width="100%" border="0" cellspacing="10" cellpadding="0">
                                    <tr><td>Email</td><td><p><%=email%></p></td></tr>
                                    <tr><td>Edad</td><td><p><%=age%></p></td></tr>
                                    <tr><td>Sexo</td><td><p><%=sex%></p></td></tr>
                                    <tr><td>Estatus</td><td><p><%=userStatus%></p></td></tr>
                                    <tr><td>Interes</td><td><p><%=userInterest%></p></td></tr>
                                    <tr><td>Hobbies</td><td><p><%=userHobbies%></p></td></tr>
                                    <tr><td>Inciso</td><td><p><%=userInciso%></p></td></tr>
                               </table>
                               </td></tr>
                            <%
                        }
                     %>
                  </table>
                  <%
                }
          }
        %>
        