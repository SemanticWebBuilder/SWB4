<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.lib.*,java.text.*,org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<script type="text/javascript" charset="utf-8">
  dojo.require("dojox.image.Lightbox");  
  //dojo.require("dojo.parser");
</script>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user=paramRequest.getUser();
    WebPage wpage=paramRequest.getWebPage();
    Member member=Member.getMember(user,wpage);

    String lang = user.getLanguage();

    String uri=request.getParameter("uri");
    PhotoElement photo=(PhotoElement)SemanticObject.createSemanticObject(uri).createGenericInstance();
    

    DecimalFormat df = new DecimalFormat("#0.0#");
                        String rank = df.format(photo.getRank());
    if(member.canView() && photo!=null)
    {
        photo.incViews();  //Incrementar apariciones
%>


<br/><br/>
 
<div id="detalleFoto">
    <h2 class="tituloGrande"><%= photo.getTitle()%></h2>
    <div class="entry_listadoFotos">
        <!-- <a dojoType="dojox.image.Lightbox" title="<%= photo.getTitle()%>" href="<%= SWBPortal.getWebWorkPath()+photo.getImageURL()%>"> -->
        <a href="<%= SWBPortal.getWebWorkPath()+photo.getImageURL() %>" title="<%=photo.getTitle()%>">
            <img id="img_<%=photo.getId()%>" src="<%= SWBPortal.getWebWorkPath()+photo.getImageURL() %>" alt="<%= photo.getTitle() %>" border="0" width="300" height="100%" />
            </a>
        <!-- </a>                  -->
    </div>
    
    <div class="detalleFotoInfo">
        <p class="tituloNaranja">Autor: <%= photo.getCreator().getFirstName()%></p>
        <p class="fotoInfo">Fecha: <%= SWBUtils.TEXT.getStrDate(photo.getCreated(), lang, "dd/mm/yy")%>   | <span class="linkNaranja"><%= photo.getViews()%> Vistas</span></p>
        <p class="descripcion"><%= photo.getDescription()%></p>  
        <p class="descripcion">Calificación: <%=rank%></p>
        <%if(photo.canModify(member)){%>
        <div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl().setParameter("act","edit").setParameter("uri",photo.getURI())%>">Editar información</a></p></div>
        <%}%>
        <%if(photo.canModify(member)){%>
        <div class="editarInfo"><p><a href="<%=paramRequest.getActionUrl().setParameter("act","remove").setParameter("uri",photo.getURI())%>">Eliminar</a></p></div>
        <%}%>
        <div class="clear">&nbsp;</div>
    </div>
</div>
       
<script type="text/javascript">
    var img = document.getElementById('img_<%=photo.getId()%>');
    if( img.width>img.height && img.width>450) {
        img.width = 450;
        img.height = 370;
    }else {
        if(img.height>370) {
            img.width = 370;
            img.height = 450;
        }
    }
</script>
<%
    }
%>

 <div class="clear">&nbsp;</div>
<div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl()%>">Regresar</a></p></div>
<%
    SWBResponse res=new SWBResponse(response);
photo.renderGenericElements(request, res, paramRequest);
out.write(res.toString());
%>

