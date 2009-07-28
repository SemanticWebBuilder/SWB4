<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>

<%
   SWBResourceURL url = paramRequest.getActionUrl();
%>
<form action="<%=url.toString()%>">
<table>
    <tr>
        <td>Que estas haciendo ahora?</td>
        <td><textarea name="status" cols="50" rows="5"></textarea></td>
    </tr>
    <tr>
        <td><input type="submit" value="enviar"></td>
    </tr>
</table>
</form>