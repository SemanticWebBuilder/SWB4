<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.lib.*,org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user=paramRequest.getUser();
    WebPage wpage=paramRequest.getWebPage();
    Member member=Member.getMember(user,wpage);
%>
<%
        String uri=request.getParameter("uri");
        VideoElement rec=(VideoElement)SemanticObject.createSemanticObject(uri).createGenericInstance();
        rec.incViews();                             //Incrementar apariciones
        if(rec!=null)
        {
%>
<div class="columnaIzquierda">
    
      <table border="0">
        <tr><td valign="top">
        <%=rec.getCode()%>
            </td><td valign="top" align="left">
        <%if(rec.canModify(member)){%><a href="<%=paramRequest.getRenderUrl().setParameter("act","edit").setParameter("uri",rec.getURI())%>">Editar Información</a><br><%}%>
        <%if(rec.canModify(member)){%><a href="<%=paramRequest.getActionUrl().setParameter("act","remove").setParameter("uri",rec.getURI())%>">Eliminar Video</a><br><%}%>
       </td></tr>
        <tr>
            <td>
                 <small>
        <%=rec.getTitle()%> <br>
        <%=rec.getDescription()%> <br>
        <%=rec.getCreated()%> <br>
        <%=rec.getViews()%> vistas<br>
        </small>
            </td>
        </tr>
      </table>
    <br>
<%
        }
%>        

<%
SWBResponse res=new SWBResponse(response);
rec.renderGenericElements(request, res, paramRequest);
out.write(res.toString());
%>
<br>
<div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl()%>">Regresar</a></p></div>

</div>
<div class="columnaCentro">
    
</div>