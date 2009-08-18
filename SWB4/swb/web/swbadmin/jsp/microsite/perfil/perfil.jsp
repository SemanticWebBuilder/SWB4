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

        if(request.getParameter("changePhoto")!=null && request.getParameter("changePhoto").equals("1")){
           %>
                <fieldset>
                <legend>Fotograf&iacute;a</legend>
                <table>
                    <tr><td width="200px" align="right"><label for="picture">Fotograf&iacute;a &nbsp;</label></td>
                            <td><iframe id="pictureTransferFrame" name="pictureTransferFrame" src="" style="display:none" ></iframe>
                                <form id="fupload" name="fupload" enctype="multipart/form-data" class="swbform"
                                action="/swb/Ciudad_Digital/Registro_de_Usuarios/_aid/46/_mto/3/_act/upload?changePhoto=0"
                                method="post" target="pictureTransferFrame" >
                                <input type="file" name="picture"
                                onchange="beginAsyncUpload(this,'picture');" />
                                <div class="progresscontainer" style="display: none;"><div class="progressbar" id="picture_progress"></div></div>
                                </form>
                            </td></tr>
                            <tr><td><a href="javascript:history.back(-1)">regresar</a></td></tr>
                </table>
                </fieldset>
           <%
          }else{


            if(!owner.getURI().equals(user.getURI())
                    && Friendship.areFriends(owner, user, paramRequest.getWebPage().getWebSite())) areFriends=true;


            if(paramRequest.getCallMethod() == paramRequest.Call_STRATEGY)
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
                                <tr><td>Email:<%=email%></td></tr>
                                <tr><td>Edad:<%=age%></td></tr>
                                <tr><td>Sexo:<%=sex%></td></tr>
                                <tr><td>Estatus:<%=userStatus%></td></tr>
                                <tr><td>Interes:<%=userInterest%></td></tr>
                                <tr><td>Hobbies<%=userHobbies%></td></tr>
                                <tr><td>Inciso:<%=userInciso%></td></tr>
                            <%
                        }
                     %>
                  </table>
                  <%
                }
          }
        %>
        