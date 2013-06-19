<%-- 
    Document   : wall
    Created on : 8/04/2013, 10:10:48 AM
    Author     : francisco.jimenez
--%>

<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.HashMap"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<jsp:useBean id="facebookBean" scope="request" type="org.semanticwb.social.Facebook"/>
<%@page import="static org.semanticwb.social.admin.resources.FacebookWall.*"%>

<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>

<script type="text/javascript" id="appendsFB">
    appendHtmlAt = function(url, tagid, location){
     dojo.xhrPost({
        url: url,
        load: function(response)
        {
            var tag=dojo.byId(tagid);
            if(tag){
                var pan=dijit.byId(tagid);
                if(pan && pan.attr)
                {
                    if(location == "bottom"){
                       pan.attr('content', tag.innerHTML + response);
                    }else if(location == "top"){
                       pan.attr('content', response + tag.innerHTML);
                    }
                }else
                {
                    if(location == "bottom"){
                       tag.innerHTML = tag.innerHTML + response;
                    }else if(location == "top"){
                       tag.innerHTML = response + tag.innerHTML;
                    }
                }
            }else {
                //alert("No existe ningun elemento con id " + tagid);
            }
            return response;
        },
        error: function(response)
        {
            if(dojo.byId(tagid)) {
                dojo.byId(tagid).innerHTML = "<p>Ocurrio un error con respuesta:<br />" + response + "</p>";
            }else {
                //alert("No existe ningun elemento con id " + tagid);
            }
            return response;
        },
        handleAs: "text"
    });
    }
    
    imageLoad = function (imgObject, imgParam) {
        var tag = dojo.byId(imgParam);
        var img = new Image();
        img = imgObject;

        // what's the size of this image and it's parent
        var w = img.width;        
        var h = img.height;
        var tw = tag.style.width.replace('px', '');
        var th = tag.style.height.replace('px', '');
        
        var scale;
        var wi;
        var he;
        
        // compute the new size and offsets
        if(w < tw && h < th){//If the image fits
            tag.style.width = w + 'px';
            tag.style.height = h + 'px';
            return;
        }else if(w > h){//landscape
            scale = tw / w ;            
        }else if(h > w){//portrait
            scale = th / h ;            
        }else{//squared
            scale = th / w ;
        }
        wi = scale * w;
        he = scale * h;
        img.width = wi;
        img.height = he;
        
        tag.style.width = wi + 'px';
        tag.style.height = he + 'px';
    }
    
    loadLargeImage = function (imgObject, imgParam) {
        var tag = dojo.byId(imgParam);
        var img = new Image();
        img = imgObject;

        // what's the size of this image and it's parent
        var w = img.width;        
        var h = img.height;
        var tw = tag.style.width.replace('px', '');
        var th = tag.style.height.replace('px', '');
        
        var scale;
        var wi;
        var he;
        
        // compute the new size and offsets
        if(w < tw && h < th){//If the image fits
            tag.style.width = w + 'px';
            tag.style.height = h + 'px';
            return;
        }else if(w > h){//landscape
            scale = tw / w ;            
        }else if(h > w){//portrait
            scale = th / h ;            
        }else{//squared
            scale = th / w ;
        }
        wi = scale * w;
        he = scale * h;
        img.width = wi;
        img.height = he;
        
        tag.style.width = wi + 'px';
        tag.style.height = he + 'px';
    }
</script>

<style type="text/css">
    div.bar{
      background-color: #F5F5F5;
      border-top: 1px solid #DDDDDD;
      box-shadow: 0 3px 8px rgba(0, 0, 0, 0.05) inset;
      cursor: pointer;
      display: block;
      font-size: 13px;
      font-weight: normal;
      padding: 10px 1px;
      position: relative;
      text-align: center;          
    }
    </style>    
      
<div class="swbform">
<div align="center"><h2>Showing <%=facebookBean.getTitle()%> wall.</h2><br/></div>
<div class="bar" id="newPostsAvailable" dojoType="dojox.layout.ContentPane"></div>
<div id="facebookStream" dojoType="dojox.layout.ContentPane"></div>
<%

    HashMap<String, String> params = new HashMap<String, String>(2);
    params.put("access_token", facebookBean.getAccessToken());
    System.out.println("AccessToken:\n" + facebookBean.getAccessToken());
    
    params.put("limit", "3");
    System.out.println("SURI jsp: " + paramRequest.getMode().toString());
    String objUri = (String) request.getParameter("suri");
    String since = (String)session.getAttribute("since");
    System.out.println("session.getAttribute(since):" + session.getAttribute("since"));
    
    //params.put("callback", "?");
    String fbResponse = postRequest(params, "https://graph.facebook.com/me/home",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
    
    //System.out.println("++++++++++++++++FBResponse:" + fbResponse);
        
    String untilPost = parseResponse(fbResponse, out, true, request, paramRequest);//Gets the newest post and saves the ID of the last one    
    SWBResourceURL renderURL = paramRequest.getRenderUrl();
%>

<div dojoType="dojox.layout.ContentPane">
    <script type="dojo/method">
        <%
            System.out.println("SINCE: " +  since);
            
            if(since == null || since.equals("0")){
                System.out.println("Calling the funtion!");
        %>
                setInterval(function(){ postHtml('<%=renderURL.setMode("newPostsAvailable").setParameter("suri", objUri)%>','newPostsAvailable'); },60000);
        <%
            }
        %>
        eval(document.getElementById("appendsFB").innerHTML);
   </script>
</div>
<div align="center" id="getMorePosts" dojoType="dijit.layout.ContentPane">
    <label id="morePostsLabel"><a href="#" onclick="appendHtmlAt('<%=renderURL.setMode("getMorePosts").setParameter("until", untilPost).setParameter("suri", objUri)%>','getMorePosts', 'bottom');try{this.parentNode.parentNode.removeChild( this.parentNode );}catch(noe){}; return false;">More posts</a></label>
</div>
</div>