<%-- 
    Document   : importFPFacebook
    Created on : 1/07/2014, 01:52:52 PM
    Author     : francisco.jimenez
--%>

<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.social.SocialSite"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.social.SocialNetwork"%>
<%@page import="org.apache.commons.validator.UrlValidator"%>
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="org.semanticwb.social.PostIn"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.social.Facebook"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.Reader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="java.io.Writer"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.HashMap"%>
<%@page import="static org.semanticwb.social.admin.resources.ImportFanPages.*"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONException"%>
<%@page import="org.json.JSONObject"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>

<%
    String suri = request.getParameter("suri");
    SocialNetwork sn = (SocialNetwork) SemanticObject.createSemanticObject(suri).createGenericInstance();
    Facebook fb = null;
    if(!(sn instanceof Facebook)){return;}else{fb = (Facebook)sn;}
    String wsite = fb.getSemanticObject().getModel().getName();
    try{        
        HashMap<String, String> params = new HashMap<String, String>(2);
        
        //Access token de Facebook (normal)        
        //params.put("access_token", "CAAIhP9SXDVgBAPXsemvmlXZBJVQZBu0MYLOc3JWjH91l6Q9NXGZAE9TpPxXRiowHIZBR8qsnm3Kds3D2I4A3k4YGZASYj8lch7p0eXHDZAoW3bApH47McsjvdX0QQZBpIZAGuULUAypOfGNIVqRdIZBJUWeJuY77azOJoM27n08tEwOtSpMgWVJR1YJNT7UIXHbEZD
        params.put("access_token", fb.getAccessToken());
        String respFanPage = postRequest(params, "https://graph.facebook.com/me/accounts",
                                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
        //System.out.println("pageAccessToken:" + respFanPage);
        JSONObject responseFP = new JSONObject(respFanPage);        
        if(!responseFP.isNull("data")){
            if(responseFP.getJSONArray("data").length()>0){
                JSONArray jarr = responseFP.getJSONArray("data");
%>


<form name="formSites<%=fb.getFacebookUserId()%>" id="formSites<%=fb.getFacebookUserId()%>" action="<%=paramRequest.getActionUrl().setAction(IMPORT_FB_PAGES).setParameter("suri", suri)%>" onsubmit="submitForm('formSites<%=fb.getFacebookUserId()%>'); try{document.getElementById('csLoading<%=fb.getFacebookUserId()%>').style.display='inline';}catch(noe){}; return false;">
<table width="50%" border="0px">            
   <tr>
       <td colspan="3" style="text-align: center;" class="titulo">
           <div id="msj-eliminar">
                 <div class="bloque bloque2" style="margin-left: 20%;">
                    <p class="bloqtit">Paginas de Facebook</p>
                    <select name="pages" multiple size="5" class="bloqsel">
<%
                for(int i = 0 ; i <  jarr.length() ; i++){
                    //System.out.println(jarr.getJSONObject(i));
                    JSONObject entryFP = jarr.getJSONObject(i);                    
                    if(!entryFP.isNull("id") && !entryFP.isNull("name")){
%>
                      <option value="<%=entryFP.getString("id")%>"><%=entryFP.getString("name")%></option>
<%
                    }
                }
%>
                    </select>
                </div>                    
            </div>           
       </td>
   </tr>
</table>
<input type="hidden" name="site" id="site" value="<%=wsite%>"/>
            
<div style="width:50%;" align="center">
    <button dojoType="dijit.form.Button" type="submit">Importar</button>
</div>
</form>

<span id="csLoading<%=fb.getFacebookUserId()%>" style="width: 100px; display: none" align="center"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/loading.gif"/></span>
<%
            }
        }
%>
    <%
        }catch(Exception e){
        out.print("Problem displaying News feed: " + e.getMessage());
    }   
%>