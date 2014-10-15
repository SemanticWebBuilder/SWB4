<%-- 
    Document   : engagementRate
    Created on : 1/10/2014, 06:42:12 PM
    Author     : francisco.jimenez
--%>

<%@page import="java.text.ParseException"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Comparator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.social.admin.resources.util.SWBSocialResUtil"%>
<%@page import="org.semanticwb.social.PostIn"%>
<%@page import="org.semanticwb.model.UserGroup"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="java.io.Closeable"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.UnsupportedEncodingException"%>
<%@page import="java.util.Collection"%>
<%@page import="org.semanticwb.social.SocialNetwork"%>
<%@page import="org.semanticwb.social.Facebook"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.SWBModel"%>
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
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONException"%>
<%@page import="org.json.JSONObject"%>

<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>

<%!
    

    public static class OrderByLikesComparator implements Comparator<JSONObject>
    {

        @Override
        public int compare(JSONObject a, JSONObject b) {
            //throw new UnsupportedOperationException("Not supported yet.");
            int response = 0;
            try{
                int alikes = 0;
                int blikes = 0;
                if(!a.isNull("likes")){            
                    if(!a.getJSONObject("likes").isNull("summary")){
                        if(!a.getJSONObject("likes").getJSONObject("summary").isNull("total_count")){
                            alikes = a.getJSONObject("likes").getJSONObject("summary").getInt("total_count");
                        }
                    }
                }
                if(!b.isNull("likes")){            
                    if(!b.getJSONObject("likes").isNull("summary")){
                        if(!b.getJSONObject("likes").getJSONObject("summary").isNull("total_count")){
                            blikes = b.getJSONObject("likes").getJSONObject("summary").getInt("total_count");
                        }
                    }
                }
                //System.out.print("a:"+ alikes + "vs" + "b:" + blikes +"...");
                //Mos likes first
                if(alikes > blikes)
                    response = -1;
                if(alikes < blikes)
                    response =  1;
                //return 0; 
                //return a.compareTo(b);
            }catch(Exception e){
                //System.out.print("e");
            }
            return response;
        }                
    }

    public static class OrderByCommentsComparator implements Comparator<JSONObject>
        {

        @Override
        public int compare(JSONObject a, JSONObject b) {
            //throw new UnsupportedOperationException("Not supported yet.");
            int response = 0;
            try{
                int acomments = 0;
                int bcomments = 0;
                if(!a.isNull("comments")){            
                    if(!a.getJSONObject("comments").isNull("summary")){
                        if(!a.getJSONObject("comments").getJSONObject("summary").isNull("total_count")){
                            acomments = a.getJSONObject("comments").getJSONObject("summary").getInt("total_count");
                        }
                    }
                }
                if(!b.isNull("comments")){            
                    if(!b.getJSONObject("comments").isNull("summary")){
                        if(!b.getJSONObject("comments").getJSONObject("summary").isNull("total_count")){
                            bcomments = b.getJSONObject("comments").getJSONObject("summary").getInt("total_count");
                        }
                    }
                }
                //System.out.print("a:"+ alikes + "vs" + "b:" + blikes +"...");
                //Mos likes first
                if(acomments > bcomments)
                    response = -1;
                if(acomments < bcomments)
                    response =  1;
                //return 0; 
                //return a.compareTo(b);
            }catch(Exception e){
                //System.out.print("e");
            }
            return response;
        }
    }
%>
    <style type="text/css">
        iframe {
            /*position: absolute;*/
            top: 0; left: 0; width: 100%; height: 100%;
            border: none; padding-top: 32px;
            box-sizing: border-box; -moz-box-sizing: border-box; -webkit-box-sizing: border-box;
        }
    </style>
<%
    String suri = request.getParameter("suri");    
    if(suri == null)return;
    SemanticObject semObj = SemanticObject.createSemanticObject(suri);
    if(semObj == null)return;
    
    //System.out.println("Social Net 1");
    SocialNetwork socialNet = (SocialNetwork)SemanticObject.getSemanticObject(suri).createGenericInstance();
    Date dateSince = null;
    Date dateTo = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    if(request.getParameter("chart") == null){ //if the param exists don't show the calendars again
%>
    <div>
        <div align="center" style="width:100%">
            <p>ENGAGEMENT POR PERIODO DE TIEMPO</p>
        </div>
    </div>
<form name="engagement<%=socialNet.getURI()%>" id="engagement<%=socialNet.getURI()%>" action="<%=paramRequest.getRenderUrl().setParameter("suri", socialNet.getURI())%>" onsubmit="submitForm('engagement<%=socialNet.getURI()%>'); try{document.getElementById('csLoading<%=socialNet.getURI()%>').style.display='inline';}catch(noe){}; return false;">
    <table style="width:100%;">
        <tr>
            <td style="text-align: right; ">
                    <%
                    Date todayDate = new Date();
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    cal.setTime(todayDate);                    
                    
                    String sinceDate = request.getParameter("engagement_inidate");
                    String toDate = request.getParameter("engagement_enddate");
                    SimpleDateFormat formatSince = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat formatTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            
                    
                    try{
                            if(sinceDate != null && !sinceDate.trim().isEmpty() &&
                                toDate != null && !toDate.trim().isEmpty()){
                            
                                toDate += " 23:59:59";
                                dateSince = formatSince.parse(sinceDate);
                                dateTo = formatTo.parse(toDate);
                            }
                        
                    }catch(ParseException pe){
                    
                    }
                        %>
                <div><label for="engagement_inidate">Desde el dia: </label><input type="text" name="engagement_inidate" data-dojo-id="engagement_inidate<%=socialNet.getId() + socialNet.getClass().getSimpleName()%>" dojoType="dijit.form.DateTextBox"  size="11" style="width:110px;" hasDownArrow="true" value="<%= formatSince.format(dateSince != null ? dateSince : todayDate)%>" onChange="engagement_enddate<%=socialNet.getId() + socialNet.getClass().getSimpleName()%>.constraints.min = arguments[0];"/>
                </div>
            </td>
            <td>
                <div><label for="engagement_enddate">Hasta el dia: </label><input type="text" name="engagement_enddate" data-dojo-id="engagement_enddate<%=socialNet.getId() + socialNet.getClass().getSimpleName()%>" dojoType="dijit.form.DateTextBox"  size="11" style="width:110px;" hasDownArrow="true" value="<%= formatSince.format(dateTo != null ? dateTo : todayDate)%>" onChange="engagement_inidate<%=socialNet.getId()+ socialNet.getClass().getSimpleName()%>.constraints.max = arguments[0];"/>
                </div>
            </td>
        </tr>
        
        <tr>
            <td colspan="2" style="text-align: center;">
                    <button dojoType="dijit.form.Button" type="submit">Calcular</button>
            </td>
        </tr>
    </table>
</form>
    <%
    }
    if(dateTo != null && dateSince != null){
%>
    
        <iframe src="<%=paramRequest.getRenderUrl().setMode("showChart").setParameter("suri", socialNet.getURI()).setParameter("chart","chart").setParameter("engagement_inidate",sdf.format(dateSince)).setParameter("engagement_enddate",sdf.format(dateTo))%>"  width="100%" height="550">
        </iframe>
    
<%
        }
       
%>
<div align="center"><span id="csLoading<%=socialNet.getURI()%>" style="width: 100px; display: none" align="center"><img src="<%=SWBPlatform.getContextPath()%>/swbadmin/images/loading.gif"/></span></div>