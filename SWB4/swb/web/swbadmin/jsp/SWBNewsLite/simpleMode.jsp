<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.resources.sem.newslite.*,java.util.*,java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<div class="marco_cuadro_I">
<div class="titulo_seccion">Noticias <span class="titulo_seccion_b">Recientes</span></div>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    List<New> news=(List) request.getAttribute("news");
    for(New onew : news)
    {
        SWBResourceURL url=paramRequest.getRenderUrl();
        url.setMode("detail");
        url.setParameter("uri", onew.getEncodedURI());
        String title=onew.getTitle();
        String pathPhoto = SWBPortal.getContextPath() + "/swbadmin/jsp/SWBNewsLite/sinfoto.png";
        String path = onew.getWorkPath();
        if (onew.getNewsThumbnail() != null)
        {
            int pos = onew.getNewsThumbnail().lastIndexOf("/");
            if (pos != -1)
            {
                String sphoto = onew.getNewsThumbnail().substring(pos + 1);
                onew.setNewsThumbnail(sphoto);
            }
            pathPhoto = SWBPortal.getWebWorkPath() + path + "/" + onew.getNewsThumbnail();
        }
        %>
            <div class="nota_prima">
                    <a href="<%=url%>" >
                    <img alt="Imagen noticia" src="<%=pathPhoto%>" />
                    <%=title%>
                    </a>

            </div>
        <%
    }
%>					
</div>

