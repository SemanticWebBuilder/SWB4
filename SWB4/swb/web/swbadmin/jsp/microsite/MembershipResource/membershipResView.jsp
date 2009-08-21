<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>

<%

    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user = paramRequest.getUser();
    WebPage wp=paramRequest.getWebPage();

    MicroSite site=MicroSite.getMicroSite(paramRequest.getWebPage());

    Member member=Member.getMember(user, wp);

    //out.println("User:"+user.getFullName());
%>

<div id="leftProfile">
    <p><img src="/swbadmin/jsp/microsite/MembershipResource/userIMG.jpg" alt="Usuario" width="174" height="174" ></p>
    <p class="addOn"><a href="#">Cambiar imagen de la comunidad</a></p>
<%
    if(user.isRegistered())
    {
        if(member==null)
        {
            out.println("<p class=\"addOn\"><a href=\""+paramRequest.getActionUrl().setParameter("act", "subscribe")+"\">Suscribirse a esta comunidad</a></p>");
        }else
        {
            out.println("<p class=\"addOn\"><a href=\""+paramRequest.getActionUrl().setParameter("act", "unsubscribe")+"\">Cancelar suscripción de esta comunidad</a></p>");
        }
    }

    site.
%>
</div>

<div id="centerProfile">
        <!--p class="editarInfo"><a href="#">Editar información</a></p -->
        <div class="clear">&nbsp;</div>
<%
        out.println("<h2 class=\"tituloGrande\">"+site.getDisplayName()+"</h2>");
        out.println("<p class=\"tituloGrande\"><img src=\"/swbadmin/jsp/microsite/MembershipResource/solidLine.jpg\" alt=\"\" width=\"495\" height=\"1\" ></p>");
        if(site.getDescription()!=null)
        {
            out.println("<p>Descripción: "+site.getDescription()+"</p>");
        }
        out.println("<p>Creador: "+site.getCreator().getFullName()+"</p>");
        out.println("<p>Creada: "+SWBUtils.TEXT.getTimeAgo(site.getCreated(),user.getLanguage())+"</p>");
        out.println("<p>Modificada: "+SWBUtils.TEXT.getTimeAgo(site.getUpdated(),user.getLanguage())+"</p>");
%>
        <!--h2 class="titulo">Contenidos</h2>
        <div id="contenidosComunidad">
          <ul>
            <li><a class="contenidosComunidad" href=""><img src="images/arrowBullet.jpg" alt="" >Sed et lectus in massa imperdiet tincidunt. Praesent neque tortor, sollicitudin non, euismod a, adipiscing a, est. Mauris diam metus, varius nec</a></li>
  			<li><a class="contenidosComunidad" href=""><img src="images/arrowBullet.jpg" alt="" >Sed et lectus in massa imperdiet tincidunt. Praesent neque tortor, sollicitudin non, euismod a, adipiscing a, est. Mauris diam metus, varius nec</a></li>
            <li><a class="contenidosComunidad" href=""><img src="images/arrowBullet.jpg" alt="" >Sed et lectus in massa imperdiet tincidunt. Praesent neque tortor, sollicitudin non, euismod a, adipiscing a, est. Mauris diam metus, varius nec</a></li>
            <li><a class="contenidosComunidad" href=""><img src="images/arrowBullet.jpg" alt="" >Sed et lectus in massa imperdiet tincidunt. Praesent neque tortor, sollicitudin non, euismod a, adipiscing a, est. Mauris diam metus, varius nec</a></li>
            <li><a class="contenidosComunidad" href=""><img src="images/arrowBullet.jpg" alt="" >Sed et lectus in massa imperdiet tincidunt. Praesent neque tortor, sollicitudin non, euismod a, adipiscing a, est. Mauris diam metus, varius nec</a></li>
          </ul>
          <div id="bottomContComunidad">&nbsp;</div>
        </div-->
        <!--p class="vermas"><a href="#">Ver Todos</a></p-->
</div>







