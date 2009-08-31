<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%@page import="org.semanticwb.model.Resource" %>
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
<br/>
<div id="panorama">
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
%>
</div>
<%  }%>

<div id="panorama">
<table border="0" width="90%">
    <tr>
    <td>
        <div id="photoc_<%= base.getId()%>" class="miembros">
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
            <div style="float:left; margin-top:30px; margin-left:5px; margin-right:5px; text-align:center" class="moreUsers">
                <a dojoType="dojox.image.Lightbox" title="<%= photo.getTitle()%>" href="<%= SWBPlatform.getWebWorkPath()+photo.getImageURL()%>">
                    <img id="img_<%=i+base.getId()%>" src="<%= SWBPlatform.getWebWorkPath()+photo.getPhotoThumbnail()%>" alt="<%= photo.getTitle()%>" border="0" />
                </a>
                <a href="<%=viewurl%>">
                    <p style="line-height:1px;">&nbsp;</p>
                    <p style="line-height:2px;"><%= photo.getTitle()%></p>                    
                    <p style="line-height:2px;"><%= photo.getDescription()%></p>
                    <p style="line-height:2px;">Autor: <%= photo.getCreator().getFirstName()%></p>
                    <p style="line-height:3px;">Fecha: <%= SWBUtils.TEXT.getStrDate(photo.getCreated(), lang, "dd/mm/yy") %> </p>
                    <p style="line-height:2px;"><%= SWBUtils.TEXT.getTimeAgo(photo.getCreated(),user.getLanguage())%></p>
                </a>
            </div>
<%
                i++;
            }
        }
%>                
        </div>
    </td>
    </tr>
</table>
</div>
