<%@page contentType="text/html"%>
<%@page import="org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%@page import="org.semanticwb.model.Resource" %>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    Resource base = paramRequest.getResourceBase();
    User user = paramRequest.getUser();
    WebPage wpage = paramRequest.getWebPage();
    MicroSiteWebPageUtil wputil = MicroSiteWebPageUtil.getMicroSiteWebPageUtil(wpage);
    Member member = Member.getMember(user, wpage);

    String lang = user.getLanguage();
%>
<script type="text/javascript">
  dojo.require("dojox.image.Lightbox");
  dojo.require("dojo.parser");
</script>

<%
    if (member.canAdd()) {
%>
    <br />
    <div class="editarInfo">
        <p><a href="<%=paramRequest.getRenderUrl().setParameter("act", "add").toString()%>">Agregar foto</a></p>
    </div>

<%
    if( member.canView() ) {
        if(!wputil.isSubscribed(member)) {
%>
    <div class="editarInfo">
        <p><a href="<%= paramRequest.getActionUrl().setParameter("act", "subscribe").toString()%>">Suscribirse</a></p>
    </div>
<%
        }else {
%>
    <div class="editarInfo">
        <p><a href="<%= paramRequest.getActionUrl().setParameter("act", "unsubscribe").toString()%>">Cancelar suscripción</a></p>
    </div>
<%
        }
    }
  }%>

<br /><br />
<div class="listaFotos">
<%
        Iterator<PhotoElement> it = PhotoElement.listPhotoElementByPhotoWebPage(wpage, wpage.getWebSite());
        int i = 0;
        while (it.hasNext())
        {
            PhotoElement photo = it.next();
            if(photo.canView(member))
            {
                SWBResourceURL viewurl = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("uri", photo.getURI());
%>
            <div class="entry_listadoFotos">
                <a dojoType="dojox.image.Lightbox" title="<%= photo.getTitle()%>" href="<%= SWBPlatform.getWebWorkPath()+photo.getImageURL()%>">
                    <img id="img_<%=i+base.getId()%>" src="<%= SWBPlatform.getWebWorkPath()+photo.getPhotoThumbnail()%>" alt="<%= photo.getTitle()%>" border="0" />
                </a>
                <a href="<%=viewurl%>">
                <p class="tituloFoto"><%= photo.getTitle()%></p>
                <p class="autor-visitasFoto"><span class="autorFoto"><%= photo.getCreator().getFirstName()%></span>&nbsp;|&nbsp;<%= photo.getViews()%> visitas</p>
                </a>
            </div>
<%
                i++;
            }
        }
%>                
</div>

<%

/*it = PhotoElement.listPhotoElementByPhotoWebPage(wpage, wpage.getWebSite());
PhotoElement photo = it.next();

    String mapa=null;
    SemanticObject semObject = photo.getSemanticObject();
    SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_VIEW);
    Iterator<SemanticProperty> itProps=semObject.listProperties();
    System.out.println("afuera");
    while(itProps.hasNext()){
        System.out.println("entra");
         SemanticProperty semProp=itProps.next();
         if(semProp==Geolocalizable.swb_latitude){
            mapa=mgr.renderElement(request, semProp.getName());
            break;
         }
    }
    out.print("<hr>");
    out.print(mapa);*/
%>