<%-- 
    Document   : facebookUserProfile
    Created on : 21/06/2013, 01:49:46 PM
    Author     : francisco.jimenez
--%>

<%@page import="org.semanticwb.platform.SemanticObject"%>
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
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>
<!DOCTYPE html>

<%!
    public static String getFullPageProfileFromId(String id, Facebook facebook){
        HashMap<String, String> params1 = new HashMap<String, String>(3);    
        params1.put("q", "SELECT id, name, type FROM profile where id = " + id);
        params1.put("access_token", facebook.getAccessToken());
    
        String fbResponse = null;
        try{
         fbResponse = getRequest(params1, "https://graph.facebook.com/fql",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");

        }catch(Exception e){
            System.out.println("Error getting user information"  + e.getMessage());
        }
        return fbResponse;
    }
    
    public static String getFullUserProfileFromId(String id, Facebook facebook){
        HashMap<String, String> params1 = new HashMap<String, String>(3);    
        params1.put("q", "SELECT uid, name, sex, about_me, birthday_date, current_location, devices, education, email, friend_count, likes_count, mutual_friend_count, online_presence, pic_big, profile_update_time, profile_url, status FROM user WHERE uid = " + id);
        params1.put("access_token", facebook.getAccessToken());
    
        String fbResponse = null;
        try{
         fbResponse = getRequest(params1, "https://graph.facebook.com/fql",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");

        }catch(Exception e){
            System.out.println("Error getting user information"  + e.getMessage());
        }
        return fbResponse;
    }
%>

<%
    String target = (String) request.getParameter("id");
    String objUri = (String) request.getParameter("suri");
    if(target == null || objUri == null){
        return;
    }
    SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
    Facebook facebook = (Facebook)semanticObject.createGenericInstance();
    
    String usrProfile = getFullUserProfileFromId(target, facebook);
        
    JSONObject usrResp = new JSONObject(usrProfile);
    JSONArray usrData = usrResp.getJSONArray("data");
    String sex = "";
    String birthday="";
    String locationName= "";
    String locationCountry = "";
    String locationCoordinates="";
    String locationId = "";
    String aboutMe = "";
    String picture = "";
    String profileUrl = "";
    int friendsCount = 0;
    int mutualFriendsCount = 0;
    
    if(usrData.length()==1){
        //System.out.println("Displaying user information!!");
        JSONObject information = usrData.getJSONObject(0);

        if(!information.isNull("sex")){
            sex = information.getString("sex");            
        }
        
        if(!information.isNull("about_me")){
            aboutMe = information.getString("about_me");
        }
        
        profileUrl =  information.getString("profile_url");
        if(!information.isNull("friend_count")){
            friendsCount = information.getInt("friend_count");
            //System.out.println("Friends:" + friendsCount);
        }
        
        if(!information.isNull("mutual_friend_count")){
            mutualFriendsCount = information.getInt("mutual_friend_count");
            //System.out.println("Mutual" + mutualFriendsCount);
        }
        if(!information.isNull("birthday_date")){
            birthday = information.getString("birthday_date");
        }
        
        picture = information.getString("pic_big");
        if(!information.isNull("current_location")){
            JSONObject usrlocation = information.getJSONObject("current_location");
            locationName = usrlocation.getString("name");
            locationCountry = usrlocation.getString("country");
            locationCoordinates = usrlocation.getDouble("latitude") + "," + usrlocation.getDouble("longitude");
            locationId = usrlocation.getLong("id") + "";
        }
        
%>
<div class="swbform" style="width: 500px;">
    <fieldset>
        <div align="center"><a href="http://www.facebook.com/<%=target%>" title="<%=paramRequest.getLocaleString("viewProfOnFB")%>"  target="_blank"><img src="https://graph.facebook.com/<%=target%>/picture?width=150&height=150" width="150" height="150"/></a></div>
    </fieldset>
<%
    if(!aboutMe.isEmpty()){
%>
    <fieldset>
        <legend><%=paramRequest.getLocaleString("aboutMe")%>:</legend>
        <div align="left"><%=aboutMe%></div>
    </fieldset>
<%
    }
%>

<%
    if(!birthday.isEmpty()){
%>
    <fieldset>
        <legend><%=paramRequest.getLocaleString("birthday")%>:</legend>
        <div align="left"><%=birthday%></div>
    </fieldset>
<%
    }
%>


<%
    if(friendsCount > 0 || mutualFriendsCount > 0){
%>
    <fieldset>
         <legend><%=paramRequest.getLocaleString("friendsInformation")%>:</legend>
         <%if(friendsCount>0){%>
            <div align="left">
                <%=paramRequest.getLocaleString("totalFriends")%>: <%=friendsCount%>
            </div>
         <%}%>
         
         <%if(mutualFriendsCount>0){%>
            <div align="left">
                <%=paramRequest.getLocaleString("mutualFriends")%>: <%=mutualFriendsCount%>
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
        <legend><%=paramRequest.getLocaleString("location")%>:</legend>
        <div align="left">
             <%=paramRequest.getLocaleString("viewOnFacebook")%>: <a href="http://www.facebook.com/profile.php?id=<%=locationId%>" title="<%=paramRequest.getLocaleString("viewLocFB")%>"  target="_blank"><%=locationName%></a>
        </div>
        <%if(!locationCoordinates.isEmpty()){%>
        <div align="left">
            <%=paramRequest.getLocaleString("viewOnGoogle")%>: <a href="https://maps.google.com/maps?q=<%=locationCoordinates%>" title="<%=paramRequest.getLocaleString("viewLocGoogle")%>"  target="_blank">(<%=locationCoordinates%>)</a>
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
        <legend><%=paramRequest.getLocaleString("profileUrl")%>:</legend>
        <div align="left">
             <a href="<%=profileUrl%>" title="<%=paramRequest.getLocaleString("viewProfOnFB")%>"  target="_blank"><%=profileUrl%></a>
        </div>
    </fieldset>
<%
    }
%>

<%
    if(!sex.isEmpty()){
%>
    <fieldset>
         <legend><%=paramRequest.getLocaleString("gender")%>:</legend>
         <div align="left"><%=sex%></div>
    </fieldset>
<%
    }
%>
</div>

<%
    }
%>