<%@page contentType="text/html"%>
<%@page import="org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    Resource base = paramRequest.getResourceBase();
    User user=paramRequest.getUser();
    WebPage wpage=paramRequest.getWebPage();
    Member member=Member.getMember(user,wpage);

    String lang = user.getLanguage();
    String path = SWBPlatform.getWebWorkPath()+base.getWorkPath()+"/";
%>
<%
        String uri=request.getParameter("uri");
        PhotoElement rec=(PhotoElement)SemanticObject.createSemanticObject(uri).createGenericInstance();        
        if(rec!=null)
        {
            rec.incViews();  //Incrementar apariciones
%>
    <div>
      <table border="0">
        <tr>
            <td valign="top">
                <img src="<%= path+rec.getImageURL() %>" alt="<%= rec.getTitle() %>" />
            </td>
            <td valign="top" align="left">
                <%if(rec.canModify(member)){%><a href="<%=paramRequest.getRenderUrl().setParameter("act","edit").setParameter("uri",rec.getURI())%>">Editar Información</a><BR/><%}%>
                <%if(rec.canModify(member)){%><a href="<%=paramRequest.getActionUrl().setParameter("act","remove").setParameter("uri",rec.getURI())%>">Eliminar foto</a><BR/><%}%>
                <small>
                    <p style="line-height:3px;"><%= rec.getTitle() %> </p>
                    <p style="line-height:3px;"><%= rec.getCreator().getFirstName() %> </p>
                    <%--<p style="line-height:3px;"><%= SWBUtils.TEXT.getStrDate(rec.getCreated(), lang, "dd/mm/yy") %> </p>--%>
                    <p style="line-height:3px;"><%= rec.getDescription() %> </p>
                    <p style="line-height:3px;"><%= rec.getRank() %> </p>
                    <p style="line-height:3px;"><%= rec.getViews() %> vistas </p>
                </small>
            </td>
        </tr>
      </table>
    </div><br/>
<%
        }
%>


<%
    rec.renderGenericElements(request, out, paramRequest);
%>
<center><a href="<%=paramRequest.getRenderUrl()%>">Regresar</a></center>