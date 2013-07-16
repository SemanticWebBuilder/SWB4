<%-- 
    Document   : facebookPageProfile
    Created on : 24/06/2013, 06:08:28 PM
    Author     : francisco.jimenez
--%>

<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="com.sun.org.apache.regexp.internal.REUtil"%>
<%@page import="javax.print.attribute.standard.MediaSize.Other"%>
<%@page import="org.semanticwb.social.Facebook"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="java.io.Writer"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONException"%>
<%@page import="org.json.JSONObject"%>

<%@page import="static org.semanticwb.social.admin.resources.FacebookWall.*"%>

<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>
<!DOCTYPE html>

<%!
    public static String getFullPageProfileFromId(String id, Facebook facebook){
        HashMap<String, String> params1 = new HashMap<String, String>(3);    
        params1.put("q", "SELECT about, company_overview, description, fan_count, release_date, general_info FROM page WHERE page_id = " + id);
        params1.put("access_token", facebook.getAccessToken());
    
        String fbResponse = null;
        try{
         fbResponse = getRequest(params1, "https://graph.facebook.com/fql",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");

        }catch(Exception e){
            System.out.println("Error getting page information"  + e.getMessage());
        }
        return fbResponse;
    }

%>
<div class="swbform">
<%
    String target = (String) request.getParameter("id");
    String objUri = (String) request.getParameter("suri");
    if(target == null || objUri == null){
        return;
    }
    
    SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
    Facebook facebook = (Facebook)semanticObject.createGenericInstance();
      
    String pageProfile = getFullPageProfileFromId(target, facebook);
    JSONObject pageResp = new JSONObject(pageProfile);
    JSONArray pageData = pageResp.getJSONArray("data");
    
    String locationName= "";
    String locationCoordinates="";
    String locationId = "";
    String about = "";
    String companyOverview="";
    String description="";
    String profileUrl = "";
    String releaseDate = "";
    String generalInfo = "";
    long fanCount = 0;
    
    if(pageData.length()==1){
        System.out.println("Displaying page information!!");
        JSONObject information = pageData.getJSONObject(0);
        
        if(!information.isNull("about")){
            about = information.getString("about");
        }
        
        if(!information.isNull("company_overview")){
            companyOverview = information.getString("company_overview");
        }
        if(!information.isNull("description")){
            description = information.getString("description");
        }
                
        if(!information.isNull("fan_count")){
            fanCount = information.getLong("fan_count");
        }
        
        if(!information.isNull("release_date")){
            releaseDate = information.getString("release_date");
        }
        
        if(!information.isNull("general_info")){
            generalInfo = information.getString("general_info");
        }
%>
</div>

<div class="swbform" style="width: 500px">
    <fieldset>
        <div align="center"><a href="http://www.facebook.com/<%=target%>" title="View profile on Facebook"  target="_blank"><img src="https://graph.facebook.com/<%=target%>/picture?width=150&height=150"/></a></div>
    </fieldset>
<%
    if(!about.isEmpty()){
%>
    <fieldset>
        <legend>About:</legend>
        <div align="left"><%=about%></div>
    </fieldset>
<%
    }
%>

<%
    if(!releaseDate.isEmpty()){
%>
    <fieldset>
        <legend>Released on:</legend>
        <div align="left"><%=releaseDate%></div>
    </fieldset>
<%
    }
%>

<%
    if(!generalInfo.isEmpty()){
%>
    <fieldset>
        <legend>General information:</legend>
        <div align="left"><%=generalInfo%></div>
    </fieldset>
<%
    }
%>

<%
    if(!companyOverview.isEmpty()){
%>
    <fieldset>
        <legend>Company Overview:</legend>
        <div align="left"><%=companyOverview%></div>
    </fieldset>
<%
    }
%>

<%
    if(!description.isEmpty()){
%>
    <fieldset>
        <legend>Description:</legend>
        <div align="left"><%=description%></div>
    </fieldset>
<%
    }
%>

<%
    if(fanCount > 0){
%>
    <fieldset>
         <legend>Fans:</legend>
         <%if(fanCount>0){%>
            <div align="left">
                Total likes received: <%=fanCount%>
            </div>
         <%}%>
    </fieldset>
<%
    }
%>

<%
    if(!locationName.isEmpty()){
%>
    <fieldset>
        <legend>Location:</legend>
        <div align="left">
             View on Facebook: <a href="http://www.facebook.com/profile.php?id=<%=locationId%>" title="View profile on Facebook"  target="_blank"><%=locationName%></a>
        </div>
        <%if(!locationCoordinates.isEmpty()){%>
        <div align="left">
            View on Google: <a href="https://maps.google.com/maps?q=<%=locationCoordinates%>" title="View profile on Google Maps"  target="_blank">(<%=locationCoordinates%>)</a>
        </div>
        <%}%>
                  
    </fieldset>
<%
    }
%>


<%
    if(!profileUrl.isEmpty()){
%>
    <fieldset>
        <legend>Profile URL:</legend>
        <div align="left">
             <a href="<%=profileUrl%>" title="View profile on Facebook"  target="_blank"><%=profileUrl%></a>
        </div>
    </fieldset>
<%
    }
%>

</div>

<%
    }
%>