<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.resources.sem.newslite.*,java.util.*,java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    SWBResourceURL action=paramRequest.getActionUrl();
    paramRequest.getResourceBase().getAttribute("nummax");
    SWBResourceURL cancel=paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_ADMIN);
%>
<script type="text/javascript">
    <!--
        function cancelar()
        {
           window.location='<%=cancel%>';
        }
    -->

</script>

<form method="post" action="<%=action%>">
    <input type="hidden" name="act" value="config"/>
    <table width="100%" cellpadding="2" cellspacing="2">
        <tr>
            <td>
                Número máximos de noticias a despluegar:
            </td>
            <td>
                <select name="nummax">
                    <option value="all">Todas</option>
                    <%
                        for(int i=1;i<=50;i++)
                        {
                            %>
                            <option value="<%=i%>"><%=i%></option>
                            <%
                        }
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                Modo simple:
            </td>
            <td>
                <input type="radio" name="modo" value="simplemode">Sí&nbsp;&nbsp;&nbsp;
                <input type="radio" name="modo" value="content">No
            </td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Guardar"><input type="button" value="Cancelar" onClick="javascript:cancelar()"></td>
        </tr>
    </table>
</form>