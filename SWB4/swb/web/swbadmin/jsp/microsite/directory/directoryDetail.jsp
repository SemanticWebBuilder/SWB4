<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.GenericIterator"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Vector"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.model.Descriptiveable"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>
<%@page import="org.semanticwb.model.FormElement"%>
<%@page import="org.semanticwb.portal.SWBFormButton"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.portal.lib.SWBResponse"%>


<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
    SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_VIEW);

    WebPage wpage = paramRequest.getWebPage();
    Resource base = paramRequest.getResourceBase();
    String perfilPath = wpage.getWebSite().getWebPage("perfil").getUrl();
    String path = SWBPortal.getWebWorkPath() + base.getWorkPath() + "/" + semObject.getId() + "/";
    
    DirectoryObject dirObj = (DirectoryObject) semObject.createGenericInstance();

    String defaultFormat = "dd/MM/yy HH:mm";
    SimpleDateFormat iso8601dateFormat = new SimpleDateFormat(defaultFormat);
    
    //Obtener valores de propiedades genericas
    String dirPhoto = semObject.getProperty(DirectoryObject.ClassMgr.swbcomm_dirPhoto);
    String[] sImgs = null;

    int cont = 0;
    Iterator<String> itExtraPhotos = dirObj.listExtraPhotos();
    while (itExtraPhotos.hasNext()) {
        cont++;
        itExtraPhotos.next();
    }

    boolean bprincipalPhoto = false;
    if (dirPhoto != null) {
        cont++;
        bprincipalPhoto = true;
    }
    sImgs = new String[cont];

    cont = -1;
    if (bprincipalPhoto) {
        sImgs[0] = path + dirPhoto;
        cont = 0;
    }
    itExtraPhotos = dirObj.listExtraPhotos();
    
    while (itExtraPhotos.hasNext()) {
        cont++;
        String photo = itExtraPhotos.next();
        sImgs[cont] = path + photo;
    }

    String imggalery = null;
    if (sImgs.length > 0) {
        imggalery = SWBPortal.UTIL.getGalleryScript(sImgs);
    } else {
        imggalery = "<img src=\"" + SWBPortal.getContextPath() + "/swbadmin/images/noDisponible.gif\"/>";
    }

    String title = dirObj.getTitle();
    String description = dirObj.getDescription();
    String tags = dirObj.getTags();
    String creator = "";
    String lat = "";
    String lon = "";
    String mapa = "";
    
    User semUser = dirObj.getCreator();
    if (semUser != null) {
        creator = "<a href=\"" + perfilPath + "?user=" + semUser.getEncodedURI() + "\">" + semUser.getFullName() + "</a>";
    }

    Date created = dirObj.getCreated();
    Iterator<SemanticProperty> itProps = semObject.listProperties();
    boolean showLocation = false;
    while (itProps.hasNext()) {
        SemanticProperty semProp = itProps.next();
        if (semProp == Geolocalizable.swb_latitude) {
            mapa = mgr.renderElement(request, semProp.getName());
            showLocation = true;
            break;
        }
    }
    
    String streetName = semObject.getProperty(Commerce.swbcomm_streetName);
    String intNumber = semObject.getProperty(Commerce.swbcomm_intNumber);
    String extNumber = semObject.getProperty(Commerce.swbcomm_extNumber);
    String city = semObject.getProperty(Commerce.swbcomm_city);
    /*----------  Personal Data ---------*/
    String contactName = semObject.getProperty(Commerce.swbcomm_contactName);
    String contactPhoneNumber = semObject.getProperty(Commerce.swbcomm_contactPhoneNumber);
    String contactEmail = semObject.getProperty(Commerce.swbcomm_contactEmail);
    String website = semObject.getProperty(Commerce.ClassMgr.swbcomm_webSite);
    /*---------- Facilities ------------*/
    String paymentType = semObject.getProperty(Commerce.ClassMgr.swbcomm_paymentType);
    String impairedPeopleAccessible = semObject.getProperty(Commerce.swbcomm_impairedPeopleAccessible);
    String parkingLot = semObject.getProperty(Commerce.swbcomm_parkingLot);
    String elevator = semObject.getProperty(Commerce.swbcomm_elevator);
    String foodCourt = semObject.getProperty(Commerce.swbcomm_foodCourt);
    String serviceHours = semObject.getProperty(Commerce.ClassMgr.swbcomm_serviceHours);

    SWBResourceURL url = paramRequest.getActionUrl();
%>

<div class="columnaIzquierda">
    <div class="adminTools">
        <a class="adminTool" onclick="javascript:history.go(-1);" href="#">Regresar al indice</a>
        <%
        if (paramRequest.getAction().equals(paramRequest.Action_REMOVE)) {
            url.setParameter("uri", semObject.getURI());
            url.setAction(url.Action_REMOVE);
            %><a class="adminTool" href="<%=url%>">Borrar</a><%
        }
        %>
    </div>
    <p class="tituloRojo"><%=title%></p>
    <div class="resumenText">
        <%if (tags != null) {%><p><span class="itemTitle">Palabras clave: </span><%=tags%></p><%}%>
        <%if (creator != null) {%><p><span class="itemTitle">Creado por: </span><%=creator%></p><%}%>
        <%if (created != null) {%><p><span class="itemTitle">Fecha de publicación: </span><%=iso8601dateFormat.format(created)%></p><%}%>
        <%if (paymentType != null) {%><p><span class="itemTitle">Forma de pago: </span><%=paymentType%></p><%}%>
        <%
          if (impairedPeopleAccessible != null) {
            String sPeopleAccessible = (impairedPeopleAccessible.equals("true")?"Si":"No");
            %>
            <p><span class="itemTitle">Habilitado para discapacitados: </span><%=sPeopleAccessible%></p>
            <%
          }
    
          if (parkingLot != null) {
            String sparkingLot = (parkingLot.equals("true")?"Si":"No");
            %>
            <p><span class="itemTitle">Estacionamiento: </span><%=sparkingLot%></p>
            <%
          }

          if (elevator != null) {
            String selevator = (elevator.equals("true")?"Si":"No");
            %>
            <p><span class="itemTitle">Elevador: </span><%=selevator%></p>
            <%
          }
        
          if (foodCourt != null) {
            String sfoodCourt = (foodCourt.equals("true")?"Si":"No");
            %>
            <p><span class="itemTitle">Area de comida: </span><%=sfoodCourt%></p>
            <%
          }
        %>
        <%if (serviceHours != null) {%><p><span class="itemTitle">Horario: </span><%=serviceHours%></p><%}%>
    </div>
    <%if (description != null) {%><h2>Descripci&oacute;n</h2><p><%=description%></p><%}%>
    <%if (showLocation){%>
        <h2>Ubicaci&oacute;n</h2>
        <%if (streetName != null) {%><p><span class="itemTitle">Calle: </span><%=streetName%></p><%}%>
        <%if (intNumber != null) {%><p><span class="itemTitle">N&uacute;mero Interior: </span><%=intNumber%></p><%}%>
        <%if (extNumber != null) {%><p><span class="itemTitle">N&uacute;mero Exterior: </span><%=extNumber%></p><%}%>
        <%if (city != null) {%><p><span class="itemTitle">Ciudad: </span><%=city%></p><%}%>
        <div class="googleMapsResource">
            <%if (mapa != null) {%><%=mapa%><%}%>
        </div>
    <%
    }
    SWBResponse res=new SWBResponse(response);
    dirObj.renderGenericElements(request, res, paramRequest);
    out.write(res.toString());
    %>
</div>
<div class="columnaCentro">
    <div style="margin:20px; padding:0px;">
        <%if (imggalery != null) {%><%=imggalery%><%}%>
    </div>
    <h2>Datos de contacto</h2>
    <ul class="listaElementos">
        <%if (creator != null) {%><li><p class="itemTitle">Contacto:</p><span class="autor"><%=creator%></span></li><%}%>
        <%if (contactPhoneNumber != null) {%><li><p class="itemTitle">Tel&eacute;fono:</p><%=contactPhoneNumber%></li><%}%>
        <%if (contactEmail != null) {%><li><p class="itemTitle">Correo electr&oacute;nico:</p><a href="mailto:<%=contactEmail%>"><%=contactEmail%></a></li><%}%>
        <%if (tags != null) {%><li><p class="itemTitle">Palabras clave:</p><%=tags%></li><%}%>
    </ul>
</div>