<%--
    Document   : FacebookAuth.jsp
    Created on : 24/04/2012, 04:26:06 PM
    Author     : jose.jimenez
--%><%@page import="org.json.JSONException"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.semanticwb.social.Facebook, java.util.*, org.semanticwb.SWBUtils, java.net.URLEncoder, java.net.*, org.semanticwb.model.*"
%><jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/><%

String facebookCode = request.getParameter("code");
String requestState = request.getParameter("state");
String error_reason = request.getParameter("error_reason");
String thisPage = "http://localhost:8080/firmaFacebook";
String action = paramRequest.getAction();
String facebookAppId = null;
String facebookSecret = null;

//WebSite wsite = paramRequest.getWebPage().getWebSite();
Iterator<Facebook> listFacebook = Facebook.ClassMgr.listFacebooks(); //wsite
while (listFacebook.hasNext()) {
    Facebook face = listFacebook.next();
    facebookAppId = face.getAppKey();
    facebookSecret = face.getSecretKey();
    if (facebookAppId != null && !"null".equalsIgnoreCase(facebookAppId) &&
            facebookSecret != null && !"null".equalsIgnoreCase(facebookSecret)) {
        session.setAttribute("facebookId", face.getId());
        break;
    }
}

if(action == null || !action.equals("saveToken")) {
    if (facebookCode == null) {

        //Se pide al usuario firmarse en facebook
        String state = (String) request.getAttribute("hashedId");
        session.setAttribute("state", state);  //CSRF protection
        String dialog_url = "\"https://www.facebook.com/dialog/oauth?client_id="
                + facebookAppId + "&redirect_uri=\" + encodeURI(\""
                + thisPage + "\") + \"&state="
                + state + "&scope=publish_stream,read_stream\"";

        out.println("<script> var urlFace = " + dialog_url + "; top.location.href=urlFace;</script>");

    } else if (/*requestState != null && requestState.equals(session.getAttribute("state")) && */
            (error_reason == null || (error_reason != null && error_reason.equals("user_denied")))) {

        String accessToken = null;
        long secsToExpiration = 0L;
        //Si el usuario otorgo los permisos necesarios, se obtiene el token de acceso
        String token_url = "https://graph.facebook.com/oauth/access_token?"
                + "client_id=" + facebookAppId + "&redirect_uri=" + thisPage
                + "&client_secret=" + facebookSecret + "&code=" + facebookCode;
        if (session.getAttribute("accessToken") != null) {
            accessToken = (String) session.getAttribute("accessToken");
            secsToExpiration = ((Long) session.getAttribute("secsToExpiration")).longValue();
        }
        
        if (accessToken == null) {
            URL pagina = new URL(token_url);
            URLConnection conex = null;
            try {
                String host = pagina.getHost();
                String header = request.getHeader("user-agent");
                //Se realiza la peticion a la página externa
                conex = pagina.openConnection();
                if (header != null) {
                    conex.setRequestProperty("user-agent", header);
                }
                if (host != null) {
                    conex.setRequestProperty("host", host);
                }
                conex.setConnectTimeout(5000);
            } catch (Exception nexc) {
                conex = null;
            }

            //Analizar la respuesta a la peticion y obtener el access token
            if (conex != null) {
                String answer = SWBUtils.IO.readInputStream(conex.getInputStream());
                String aux = null;
                //System.out.println("Respuesta de Facebook para el accessToken: \n" + answer);

                if (answer.indexOf("&") > 0) {
                    aux = answer.split("&")[0];
                    if (aux.indexOf("=") > 0) {
                        accessToken = aux.split("=")[1];
                    }
                    session.setAttribute("accessToken", accessToken);
                    aux = answer.split("&")[1];
                    if (aux.indexOf("=") > 0) {
                        secsToExpiration = Long.parseLong(aux.split("=")[1]);
                    }
                    session.setAttribute("secsToExpiration", Long.valueOf(secsToExpiration));
                }
            }
        }
        if (accessToken != null) {

            //Hacer consulta a los datos del usuario a través del grafo
            String graph_url = "https://graph.facebook.com/me?access_token="
                + accessToken;
            String me = Facebook.graphRequest(graph_url, request.getHeader("user-agent"));
            JSONObject userData = new JSONObject(me);
            String userId = userData != null && userData.get("id") != null ? (String) userData.get("id") : "";

            out.println("Para habilitar la publicaci&oacute;n en Facebook haz clic <a href=\""
                    + paramRequest.getActionUrl().setAction("saveToken").setParameter("token", accessToken).setParameter("userId", userId)
                    + "\">aqu&iacute;</a>");
        }
    } else {
        out.println("Se ha encontrado un problema con la respuesta obtenida, se considera no aut&eacute;ntica. " + session.getAttribute("state"));
    }
} else {
    out.println("Ya puedes hacer publicaciones en Facebook!");
}
%>