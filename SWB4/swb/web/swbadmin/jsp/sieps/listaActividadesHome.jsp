<%@page import="java.util.List"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.platform.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.scian.*"%>
<%@page import="org.semanticwb.unspsc.*"%>
<%@page import="org.semanticwb.sieps.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<div class="tablaHome">
        <div class="tituloTablaServicios">Industrias y servicios más populares</div>
        <table id="serviciosPopulares" summary="Lista de industrias y servicios más populares">

<%


User user = (User) request.getAttribute("user");
Iterator<Clase> classes=Clase.ClassMgr.listClases();
int i=1;
while(classes.hasNext())
{
    Clase clase=classes.next();
    String name=clase.getSemanticObject().getLabel(user.getLanguage());
    if(i==1)
        {
        %>
            <tr>
            <td class="firstTD"><%=name%></td>
        <%
        }
    else if(i==2)
        {
         %>
            <td class="firstTD"><%=name%></td>
            </tr>
         <%
        }
    else
    {
        if(i%2==1)
        {
            %>
             <tr>
            <td><%=name%></td>
            <%
        }
        else
        {
            %>
            <td><%=name%></td>
            </tr>
            <%
        }

    }
    if(i==10)
    {
        break;
    }
    i++;
}

%>


          
           
          
          
        </table>
      </div>
      