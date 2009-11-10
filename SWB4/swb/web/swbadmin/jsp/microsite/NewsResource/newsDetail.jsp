<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.lib.*,java.text.SimpleDateFormat, org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            Resource base = paramRequest.getResourceBase();
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            Member member = Member.getMember(user, wpage);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0#");
            String uri = request.getParameter("uri");
            NewsElement anew = (NewsElement) SemanticObject.createSemanticObject(uri).createGenericInstance();
            String rank = df.format(anew.getRank());
%>

<div class="columnaIzquierda">
    <%if (anew.canModify(member))
            {%>
    <div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl().setParameter("act", "edit").setParameter("uri", anew.getURI())%>">Editar Información</a></p></div>
    <%}%>
    <%if (anew.canModify(member))
            {%>
    <div class="editarInfo"><p><a href="<%=paramRequest.getActionUrl().setParameter("act", "remove").setParameter("uri", anew.getURI())%>">Eliminar</a></p></div>
    <%}%>
    <div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl()%>">[Ver todas las noticias]</a></p></div>


    <%
                if (anew != null && anew.canView(member))
                {
                    anew.incViews();
    %>




    <p><img id="img_<%=anew.getId()%>" src="<%= SWBPortal.getWebWorkPath() + anew.getNewsImage()%>" alt="<%= anew.getTitle()%>" border="0" width="380" height="100%" /></p>



    <p class="descripcion" style="text-align:justify;"><%=anew.getFullText()%></p>

    <%  }
                SWBResponse res = new SWBResponse(response);
                anew.renderGenericElements(request, res, paramRequest);
                out.write(res.toString());
    %>
</div>
<div class="columnaCentro">
    <h2 class="blogTitle"><%=anew.getTitle()%></h2><br>
    <p><%=anew.getDescription()%></p>
    <p>Por: <%= anew.getAuthor()%></p>
    <p>Fuente: <%= anew.getCitation()%></p>
    <p>Creado el: <%=dateFormat.format(anew.getCreated())%></p>
    <p><%=anew.getViews()%> vistas</p>
    <p>Calificación: <%=rank%></p>
</div>