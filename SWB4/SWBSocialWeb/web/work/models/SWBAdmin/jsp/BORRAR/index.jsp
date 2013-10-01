<%@page import="org.json.JSONArray"%>
<%@page import="java.io.File"%>
<%@page import="java.io.StringReader"%>
<%@page import="javax.xml.parsers.DocumentBuilder"%>
<%@page import="javax.xml.parsers.DocumentBuilderFactory"%>
<%@page import="javax.xml.transform.OutputKeys"%>
<%@page import="javax.xml.transform.Result"%>
<%@page import="javax.xml.transform.Source"%>
<%@page import="javax.xml.transform.Transformer"%>
<%@page import="javax.xml.transform.TransformerFactory"%>
<%@page import="javax.xml.transform.dom.DOMSource"%>
<%@page import="javax.xml.transform.stream.StreamResult"%>
<%@page import="org.xml.sax.InputSource"%>

<%@page import="javax.swing.text.Document"%>
<%@page import="java.io.StringReader"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.io.Closeable"%>
<%@page import="java.io.Reader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.UnsupportedEncodingException"%>
<%@page import="java.util.Collection"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!
    private String postRequest(Map<String, String> params, String url,
            String userAgent, String method) throws IOException {

        URL serverUrl = new URL(url);
        CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);

        HttpURLConnection conex = null;
        OutputStream out = null;
        InputStream in = null;
        String response = null;

        if (method == null) {
            method = "POST";
        }
        try {
            conex = (HttpURLConnection) serverUrl.openConnection();
            if (userAgent != null) {
                conex.setRequestProperty("user-agent", userAgent);
            }
            conex.setConnectTimeout(30000);
            conex.setReadTimeout(60000);
            conex.setRequestMethod(method);
            conex.setDoOutput(true);
            conex.connect();
            out = conex.getOutputStream();
            out.write(paramString.toString().getBytes("UTF-8"));
            in = conex.getInputStream();
            response = getResponse(in);
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        } finally {
            close(in);
            close(out);
            if (conex != null) {
                conex.disconnect();
            }
        }
        if (response == null) {
            response = "";
        }
        return response;
    }

    CharSequence delimit(Collection<Map.Entry<String, String>> entries,
            String delimiter, String equals, boolean doEncode)
            throws UnsupportedEncodingException {

        if (entries == null || entries.isEmpty()) {
            return null;
        }
        StringBuilder buffer = new StringBuilder(64);
        boolean notFirst = false;
        for (Map.Entry<String, String> entry : entries) {
            if (notFirst) {
                buffer.append(delimiter);
            } else {
                notFirst = true;
            }
            CharSequence value = entry.getValue();
            buffer.append(entry.getKey());
            buffer.append(equals);
            buffer.append(doEncode ? encode(value) : value);
        }
        return buffer;
    }

    private String encode(CharSequence target) throws UnsupportedEncodingException {

        String result = "";
        if (target != null) {
            result = target.toString();
            result = URLEncoder.encode(result, "UTF8");
        }
        return result;
    }

    private static String getResponse(InputStream data) throws IOException {

        Reader in = new BufferedReader(new InputStreamReader(data, "UTF-8"));
        StringBuilder response = new StringBuilder(256);
        char[] buffer = new char[1000];
        int charsRead = 0;
        while (charsRead >= 0) {
            response.append(buffer, 0, charsRead);
            charsRead = in.read(buffer);
        }
        in.close();
        return response.toString();
    }

    private void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException ex) {
            }
        }
    }

    public String postRequest2(Map<String, String> params, String url,
            String userAgent, String method) throws IOException {

        CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);

        URL serverUrl = new URL(url + "?" + paramString);

        HttpURLConnection conex = null;
        OutputStream out = null;
        InputStream in = null;
        String response = null;

        if (method == null) {
            method = "POST";
        }
        try {
            conex = (HttpURLConnection) serverUrl.openConnection();
            if (userAgent != null) {
                conex.setRequestProperty("user-agent", userAgent);
            }

            conex.setConnectTimeout(30000);
            conex.setReadTimeout(60000);
            conex.setRequestMethod(method);
            conex.setDoOutput(true);
            conex.connect();

            System.out.println("CONNECT:" + conex);
            in = conex.getInputStream();
            response = getResponse(in);

        } catch (java.io.IOException ioe) {
            if (conex.getResponseCode() >= 400) {
                System.out.println("ERROR:" + getResponse(conex.getErrorStream()));
            }
            ioe.printStackTrace();
        } finally {
            close(in);
            if (conex != null) {
                conex.disconnect();
            }
        }
        if (response == null) {
            response = "";
        }
        return response;
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <%
            String code = request.getParameter("code");
            System.out.println("codigo" + code);
            if (code == null) {
        %>
        <h1>Hello World!</h1>
        <%
            out.println("<script type=\"text/javascript\">");
            out.println(" function ioauth() {");
            out.println("  mywin = window.open('https://accounts.google.com/o/oauth2/auth?client_id=372852525445.apps.googleusercontent.com&redirect_uri=http://localhost:8080/work/models/SWBAdmin/jsp/index.jsp&response_type=code&scope=https://gdata.youtube.com','_blank','width=840,height=680',true);");
            out.println("  mywin.focus();");
            out.println(" }");
            out.println(" if(confirm('¿Autenticar la cuenta en YouTube?')) {");
            out.println("  ioauth();");
            out.println(" }");
            out.println("</script>");
        } else {
        %>
        <h1>Regreso con el code y lo envio!</h1>
        <%

            Map<String, String> params = new HashMap<String, String>();
            params.put("code", code);
            params.put("client_id", "372852525445.apps.googleusercontent.com");
            params.put("client_secret", "zoivG7hdszZcY_jcZaF5UpHl");
            params.put("redirect_uri", "http://localhost:8080/work/models/SWBAdmin/jsp/index.jsp");
            params.put("grant_type", "authorization_code");

            String res = postRequest(params, "https://accounts.google.com/o/oauth2/token", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "POST");
            System.out.println("respuesta" + res);

            JSONObject userData = new JSONObject(res);
            String tokenAccess = userData.getString("access_token");
            String token_type = userData.getString("token_type");

            System.out.println("token access:  " + tokenAccess);
            System.out.println("tipo de token: " + token_type);

            if (tokenAccess != null) {
                System.out.println("Entra aqui");
        %>
        <h1>Regresa con el token de acceso y empieza a realizar operaciones.</h1>
        <%
                    Map<String, String> params2 = new HashMap<String, String>();
                    /*params2.put("q", "futbol");*/
                    params2.put("v", "2");
                    params2.put("max-results", "2");
                    params2.put("alt","jsonc");
                    try {
                        //Para buscar por categoria
                        //https://gdata.youtube.com/feeds/api/standardfeeds/regionID/feedID_CATEGORY_NAME?v=2
                        //Ejemplo:
                        //https://gdata.youtube.com/feeds/api/standardfeeds/MX/top_rated_Comedy
                        
                        String r2 = postRequest2(params2, "https://gdata.youtube.com/feeds/api/standardfeeds/MX/top_rated_Comedy", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
                        System.out.println("respuesta2 :" + r2);
                        //Convertir la String res2 a un objeto json
                        JSONObject resp = new JSONObject(r2);
                        System.out.println("resp :" +resp.getString("apiVersion"));
                        JSONObject data = new JSONObject(resp);
                        System.out.println("objetos de data: " + data.getString("updated"));   
                    } catch (Exception e) {
                        System.out.println("entra al error: " + e);
                    }
                }
            }
        %>
    </body>
</html>
