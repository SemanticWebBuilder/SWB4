<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.GenericIterator"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Vector"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.model.Descriptiveable"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>
<%@page import="org.semanticwb.model.FormElement"%>
<%@page import="org.semanticwb.portal.SWBFormButton"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.portal.community.Restaurant"%>


<%
    HashMap map=new HashMap();
    map.put("separator", "-");
    WebPage wpage=paramRequest.getWebPage();
    Resource base=paramRequest.getResourceBase();
    String perfilPath=wpage.getWebSite().getWebPage("perfil").getUrl();
    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
    DirectoryObject dirObj=(DirectoryObject)semObject.createGenericInstance();
    SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_VIEW);
    String path=SWBPlatform.getWebWorkPath()+base.getWorkPath()+"/"+semObject.getId()+"/";
    //Obtener valores de propiedades genericas
    String dirPhoto=semObject.getProperty(dirObj.swbcomm_dirPhoto);

    String[] sImgs=null;
    int cont=0;
    Iterator<String> itExtraPhotos=dirObj.listExtraPhotos();
    while(itExtraPhotos.hasNext()){
        cont++;
        itExtraPhotos.next();
    }
    boolean bprincipalPhoto=false;
    if(dirPhoto!=null) {
        cont++;
        bprincipalPhoto=true;
    }
    sImgs=new String[cont];

    cont=-1;
    if(bprincipalPhoto) {
        sImgs[0]=path+dirPhoto;
        cont=0;
    }
    itExtraPhotos=dirObj.listExtraPhotos();
    while(itExtraPhotos.hasNext()){
        cont++;
        String photo=itExtraPhotos.next();
        sImgs[cont]=path+photo;
    }
   
    String imggalery=null;
    if(sImgs.length>0){
        imggalery=SWBPortal.UTIL.getGalleryScript(sImgs);
    }else{
        imggalery="<img src=\""+SWBPlatform.getContextPath()+"/swbadmin/images/noDisponible.gif\"/>";
    }
   
    String title=semObject.getProperty(dirObj.swb_title);
    String description=semObject.getProperty(dirObj.swb_description);
    String tags=semObject.getProperty(dirObj.swb_tags);
    String creator="";
    SemanticObject semUser=semObject.getObjectProperty(DirectoryObject.swb_creator);
    if(semUser!=null){
        User userObj=(User)semUser.createGenericInstance();
        creator="<a href=\""+perfilPath+"?user="+userObj.getEncodedURI()+"\">"+userObj.getFullName()+"</a>";
    }
    String created=semObject.getProperty(dirObj.swb_created);
    String mapa=null;
    Iterator<SemanticProperty> itProps=semObject.listProperties();
    while(itProps.hasNext()){
         SemanticProperty semProp=itProps.next();
         if(semProp==Geolocalizable.swb_latitude){
            mapa=mgr.renderElement(request, semProp.getName());
            break;
         }
    }
    
    String streetName=semObject.getProperty(Commerce.swbcomm_streetName);
    String intNumber=semObject.getProperty(Commerce.swbcomm_intNumber);
    String extNumber=semObject.getProperty(Commerce.swbcomm_extNumber);
    String city=semObject.getProperty(Commerce.swbcomm_city);
    /*----------  Personal Data ---------*/
    String contactName=semObject.getProperty(Commerce.swbcomm_contactName);
    String contactPhoneNumber=semObject.getProperty(Commerce.swbcomm_contactPhoneNumber);
    String contactEmail=semObject.getProperty(Commerce.swbcomm_contactEmail);
    String website=semObject.getProperty(Commerce.swbcomm_webSite);
    /*---------- Facilities ------------*/
    String paymentType=semObject.getProperty(Commerce.swbcomm_paymentType);
    String impairedPeopleAccessible=semObject.getProperty(Commerce.swbcomm_impairedPeopleAccessible);
    String parkingLot=semObject.getProperty(Commerce.swbcomm_parkingLot);
    String elevator=semObject.getProperty(Commerce.swbcomm_elevator);
    String foodCourt=semObject.getProperty(Commerce.swbcomm_foodCourt);
    String serviceHours=semObject.getProperty(Commerce.swbcomm_serviceHours);

%>

<div id="contenidoDetalle">
     <div class="detalle">
        <div class="detalleImagen">
         <%if(imggalery!=null){%><%=imggalery%><%}%>
        </div>
            <div class="productInfo">
                <p class="tituloNaranja"><%=title%></p>
                <p><%=wpage.getPath(map)%></p>
                <%if(tags!=null){%><p>Palabras clave:<strong><%=tags%></strong></p><%}%>
                <!---localizable Data -->
                <fieldset>
                 <legend>Ubicación</legend>
                <%if(streetName!=null){%><p>calle:<strong><%=streetName%></strong></p><%}%>
                <%if(intNumber!=null){%><p>Número Interior:<strong><%=intNumber%></strong></p><%}%>
                <%if(extNumber!=null){%><p>Número Exterior:<strong><%=extNumber%></strong></p><%}%>
                <%if(city!=null){%><p>Ciudad:<strong><%=city%></strong></p><%}%>
                <%if(creator!=null){%><p>Creado por:<strong><%=creator%></strong></p><%}%>
                <%if(created!=null){%><p>Fecha de publicación:<strong><%=created%></strong></p><%}%>
                </fieldset>
                <!--User Data-->
                 <fieldset>
                 <legend>Datos de contacto</legend>
                <%if(contactName!=null){%><p>Contacto:<strong><%=contactName%></strong></p><%}%>
                <%if(contactPhoneNumber!=null){%><p>Télefono:<strong><%=contactPhoneNumber%></strong></p><%}%>
                <%if(contactEmail!=null){%><p>correo electrónico:<strong><%=contactEmail%></strong></p><%}%>
                <%if(website!=null){%><p>Página web:<strong><%=website%></strong></p><%}%>
                </fieldset>
                <!--Cuenta con-->
                <fieldset>
                 <legend>Extras</legend>
                <%if(paymentType!=null){%><p>Forma de pago:<strong><%=paymentType%></strong></p><%}%>
                <%if(impairedPeopleAccessible!=null){%><p>Habilitado para discapacitados:<strong><%=impairedPeopleAccessible%></strong></p><%}%>
                <%if(parkingLot!=null){%><p>Estacionamiento:<strong><%=parkingLot%></strong></p><%}%>
                <%if(elevator!=null){%><p>Elevador:<strong><%=elevator%></strong></p><%}%>
                <%if(foodCourt!=null){%><p>Area de comida:<strong><%=foodCourt%></strong></p><%}%>
                <%if(serviceHours!=null){%><p>Horario:<strong><%=serviceHours%></strong></p><%}%>
                </fieldset>
         </div>
     </div>
     <div class="descripcion">
        <h3>Descripción</h3>
        <%if(description!=null){%><p><%=description%></p><%}%>
        <%if(mapa!=null){%><p><%=mapa%><%}%></p><br/>
     </div>
     

      <%
         SWBResourceURL url = paramRequest.getActionUrl();
         url.setParameter("uri", semObject.getURI());
         url.setAction(url.Action_REMOVE);
      %>
      <form method="post" action="<%=url%>">
        <table>
          <tr>
      <%
        if(paramRequest.getAction().equals(paramRequest.Action_REMOVE))
        {
            %>
             
                       <td><input type="submit" name="delete" value="Borrar"/></td>
            <%
        }
      %>
             <td><input type="button" name="back" value="Regresar" onclick="history.go(-1)"/></td>
           </tr>
         </table>
       </form>
</div>
   
   

