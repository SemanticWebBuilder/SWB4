<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user=paramRequest.getUser();
    WebPage wpage=paramRequest.getWebPage();
    Member member=Member.getMember(user,wpage);
%>
<form enctype="multipart/form-data" method="post" action="<%=paramRequest.getActionUrl()%>">
    <input type="file" name="foto" />
    <input type="submit" value="subir" />
    <input type="hidden" name="act" value="add"/>
</form>
