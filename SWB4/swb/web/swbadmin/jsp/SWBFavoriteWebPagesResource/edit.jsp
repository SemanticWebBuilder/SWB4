<%@page import="org.semanticwb.model.Country"%><%@page import="org.semanticwb.platform.SemanticObject"%><%@page import="org.semanticwb.servlet.SWBHttpServletResponseWrapper"%><%@page import="org.semanticwb.portal.api.SWBResource"%><%@page import="org.semanticwb.portal.api.SWBResourceURL"%><%@page import="org.semanticwb.SWBPortal"%><%@page import="org.semanticwb.*"%><%@page import="java.text.DateFormat"%><%@page import="java.util.Locale"%><%@page import="org.semanticwb.portal.resources.sem.favoriteWebPages.*"%><%@page import="org.semanticwb.model.Resource"%><%@page import="java.util.*"%><%@page import="org.semanticwb.model.WebPage"%><%@page import="org.semanticwb.model.User"%><jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
            if (!paramRequest.getUser().isSigned())
            {
                return;
            }
            String mode = (String) request.getAttribute("mode");

            ArrayList<WebPage> pages = (ArrayList<WebPage>) request.getAttribute("pages");
            boolean exists = false;
            String id = paramRequest.getWebPage().getId();
            SWBResourceURL url = paramRequest.getRenderUrl();
            url.setParameter("id", id);
            url.setParameter("mode", "add");
            url.setMode("edit");
            url.setCallMethod(url.Call_DIRECT);

            if (pages.contains(paramRequest.getWebPage()))
            {
                exists = true;
            }
            if (!exists)
            {
%>
<script type="text/javascript">
    
    function addFav(url,divid)
    {
        //<%=mode%>
        var element=document.getElementById(divid);
        if(element)
        {
            if(window.XMLHttpRequest) {
                peticion_http = new XMLHttpRequest();
            }
            else if(window.ActiveXObject) {
                peticion_http = new ActiveXObject("Microsoft.XMLHTTP");
            }
            peticion_http.onreadystatechange = function muestraContenido()
            {
                if(peticion_http.readyState == 4) {
                    if(peticion_http.status == 200) {
                        element.innerHTML=peticion_http.responseText;
                    }
                }

            };
            peticion_http.open('GET', url, false);
            peticion_http.send(null);
            

        }
    }
    function removeFav(url,divid)
    {
        var element=document.getElementById(divid);
        if(element)
        {
            if(window.XMLHttpRequest) {
                peticion_http = new XMLHttpRequest();
            }
            else if(window.ActiveXObject) {
                peticion_http = new ActiveXObject("Microsoft.XMLHTTP");
            }
            peticion_http.onreadystatechange = function muestraContenido()
            {
                if(peticion_http.readyState == 4) {
                    if(peticion_http.status == 200) {
                        element.innerHTML=peticion_http.responseText;
                    }
                }

            };
            peticion_http.open('GET', url, true);
            peticion_http.send(null);


        }
    }

</script>
<input type="button" value="Agregar" onClick="addFav('<%=url%>','edit')">
<%                        }
            else
            {
                url = paramRequest.getRenderUrl();
                url.setParameter("id", id);
                url.setParameter("mode", "remove");
                url.setMode("edit");
                url.setCallMethod(url.Call_DIRECT);
%>
<script type="text/javascript">
    
    function addFav(url,divid)
    {
        //<%=mode%>
        var element=document.getElementById(divid);
        if(element)
        {
            if(window.XMLHttpRequest) {
                peticion_http = new XMLHttpRequest();
            }
            else if(window.ActiveXObject) {
                peticion_http = new ActiveXObject("Microsoft.XMLHTTP");
            }
            peticion_http.onreadystatechange = function muestraContenido()
            {
                if(peticion_http.readyState == 4) {
                    if(peticion_http.status == 200) {
                        element.innerHTML=peticion_http.responseText;
                    }
                }

            };
            peticion_http.open('GET', url, false);
            peticion_http.send(null);


        }
    }
    function removeFav(url,divid)
    {
        var element=document.getElementById(divid);
        if(element)
        {
            if(window.XMLHttpRequest) {
                peticion_http = new XMLHttpRequest();
            }
            else if(window.ActiveXObject) {
                peticion_http = new ActiveXObject("Microsoft.XMLHTTP");
            }
            peticion_http.onreadystatechange = function muestraContenido()
            {
                if(peticion_http.readyState == 4) {
                    if(peticion_http.status == 200) {
                        element.innerHTML=peticion_http.responseText;
                    }
                }

            };
            peticion_http.open('GET', url, true);
            peticion_http.send(null);
            

        }
    }

</script>

<input type="button" value="Eliminar" onClick="removeFav('<%=url%>','edit')">
<%
            }

%>