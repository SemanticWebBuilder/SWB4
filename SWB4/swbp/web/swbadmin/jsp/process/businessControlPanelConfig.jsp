<%--
    SemanticWebBuilder Process (SWBP) es una plataforma para la gestión de procesos de negocio mediante el uso de 
     tecnología semántica, que permite el modelado, configuración, ejecución y monitoreo de los procesos de negocio
     de una organización, así como el desarrollo de componentes y aplicaciones orientadas a la gestión de procesos.

     Mediante el uso de tecnología semántica, SemanticWebBuilder Process puede generar contextos de información
     alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes fuentes asociadas a
     un proceso de negocio, donde a la información se le asigna un significado, de forma que pueda ser interpretada
     y procesada por personas y/o sistemas. SemanticWebBuilder Process es una creación original del Fondo de 
     Información y Documentación para la Industria INFOTEC.

     INFOTEC pone a su disposición la herramienta SemanticWebBuilder Process a través de su licenciamiento abierto 
     al público (?open source?), en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC 
     lo ha diseñado y puesto a su disposición; aprender de él; distribuirlo a terceros; acceder a su código fuente,
     modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los términos y 
     condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización de SemanticWebBuilder Process. 

     INFOTEC no otorga garantía sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni implícita ni 
     explícita, siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los 
     riesgos que puedan derivar de la misma. 

     Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposición la
     siguiente dirección electrónica: 
     http://www.semanticwebbuilder.org.mx

    Document   : businessControlPanelConfig
    Created on : 1/08/2011, 07:12:02 PM
    Author     : Hasdai Pacheco {haxdai@gmail.com}
--%>

<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>

<%
SWBParamRequest paramRequest = (SWBParamRequest)request.getAttribute("paramRequest");
String displayCols = (String) request.getAttribute("displayCols");

if (paramRequest.getMode().equals("config")) {
    SWBResourceURL configActionUrl = paramRequest.getActionUrl().setAction("setDisplay");
    %>
    <h2>Configuración del despliegue</h2>
    <form action="<%=configActionUrl%>" method="post">
        <fieldset><legend>Seleccione las columnas a desplegar:</legend>
            <input type="checkbox" name="idCol" <%=displayCols.contains("idCol")?"checked=\"checked\"":""%>/>
            <label for="idCol">Identificador de la instancia</label><br>
            <input type="checkbox" name="priorityCol" <%=displayCols.contains("priorityCol")?"checked=\"checked\"":""%>/>
            <label for="priorityCol">Prioridad de la instancia</label><br>
            <input type="checkbox" name="nameCol" <%=displayCols.contains("nameCol")?"checked=\"checked\"":""%>/>
            <label for="nameCol">Nombre del proceso</label><br>
            <input type="checkbox" name="sdateCol" <%=displayCols.contains("sdateCol")?"checked=\"checked\"":""%>/>
            <label for="sdateCol">Fecha de inicio de la instancia</label><br>
            <input type="checkbox" name="edateCol" <%=displayCols.contains("edateCol")?"checked=\"checked\"":""%>/>
            <label for="edateCol">Fecha de fin de la instancia</label><br>
            <input type="checkbox" name="pendingCol" <%=displayCols.contains("pendingCol")?"checked=\"checked\"":""%>/>
            <label for="pendingCol">Actividades pendientes</label><br>
            <input type="checkbox" name="rolesCol" <%=displayCols.contains("rolesCol")?"checked=\"checked\"":""%>/>
            <label for="rolesCol">Responsables de las actividades pendientes</label><br>
            <!--input type="checkbox" name="actionsCol" <%=displayCols.contains("actionsCol")?"checked=\"checked\"":""%>/-->
            <!--label for="actionsCol">Acciones</label><br-->
        </fieldset>
        <fieldset>
            <input type="submit" value="Guardar" >
            <input type="button" value="Regresar" onclick="window.location='<%=paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW)%>';" >
        </fieldset>
    </form>
    <%
}
%>