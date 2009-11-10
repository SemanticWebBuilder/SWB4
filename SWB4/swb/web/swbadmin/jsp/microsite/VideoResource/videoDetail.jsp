<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.lib.*,org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user=paramRequest.getUser();
    WebPage wpage=paramRequest.getWebPage();
    Member member=Member.getMember(user,wpage);
    java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd-MMM-yyyy");
    java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0#");
%>
<%
        String uri=request.getParameter("uri");
        VideoElement rec=(VideoElement)SemanticObject.createSemanticObject(uri).createGenericInstance();
        rec.incViews();                             //Incrementar apariciones
        if(rec!=null)
        {
            String rank = df.format(rec.getRank());
%>
<div class="columnaIzquierda">
    
      <table border="0">
        <tr><td valign="top">
        <%=rec.getCode()%>
            </td><td valign="top" align="left">
        <%if(rec.canModify(member)){%><a href="<%=paramRequest.getRenderUrl().setParameter("act","edit").setParameter("uri",rec.getURI())%>">Editar Información</a><br><%}%>
        <%if(rec.canModify(member)){%><a href="<%=paramRequest.getActionUrl().setParameter("act","remove").setParameter("uri",rec.getURI())%>">Eliminar Video</a><br><%}%>
       </td></tr>        
      </table>
    <br>
     

<%
SWBResponse res=new SWBResponse(response);
rec.renderGenericElements(request, res, paramRequest);
out.write(res.toString());
%>
<br>
<div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl()%>">Regresar</a></p></div>

</div>
<div class="columnaCentro">
    <h2 class="blogTitle"><%=rec.getTitle()%></h2><br>
    <p><%=rec.getDescription()%></p>
    <p>Creado el: <%=dateFormat.format(rec.getCreated())%></p>
    <p><%=rec.getViews()%> vistas</p>
    <p>Calificación: <%=rank%></p>
</div>


<%
        }
%>   