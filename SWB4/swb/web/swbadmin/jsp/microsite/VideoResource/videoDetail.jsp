<%@page contentType="text/html"%>
<%@page import="org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
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
    <div>
      <table border="0">
        <tr><td valign="top">
        <%=rec.getCode()%>
            </td><td valign="top" align="left">
        <%if(rec.canModify(member)){%><a href="<%=paramRequest.getRenderUrl().setParameter("act","edit").setParameter("uri",rec.getURI())%>">Editar Información</a><BR/><%}%>
        <%if(rec.canModify(member)){%><a href="<%=paramRequest.getActionUrl().setParameter("act","remove").setParameter("uri",rec.getURI())%>">Eliminar Video</a><BR/><%}%>
        <small>
        <%=rec.getTitle()%> <BR>
        <%=rec.getDescription()%> <BR>
        <%=rec.getCreated()%> <BR>
        <!--</small></td><td valign="top"><small>-->
        <%=rec.getViews()%> vistas<BR>
        </small></td></tr>
      </table>
    </div><br/>
<%
        }
%>        

<%
rec.renderGenericElements(request, out, paramRequest);
%>
<center><a href="<%=paramRequest.getRenderUrl()%>">Regresar</a></center>
