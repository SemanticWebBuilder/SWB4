<%-- 
    Document   : twitterDiscover
    Created on : 28/05/2013, 05:55:31 PM
    Author     : francisco.jimenez
--%>

<%@page import="java.util.Iterator"%>
<%@page import="static org.semanticwb.social.admin.resources.Timeline.doPrintTweet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.social.admin.resources.SocialUserStreamListener"%>
<%@page import="java.io.Writer"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="org.semanticwb.social.admin.resources.Timeline"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="org.semanticwb.social.Twitter"%>
<%@page import="org.semanticwb.social.base.TwitterBase"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.semanticwb.social.SocialTopic"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="twitter4j.*"%>
<%@page import="twitter4j.conf.ConfigurationBuilder"%>

<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<jsp:useBean id="twitterBean" scope="request" type="twitter4j.Twitter"/>

<%    
    String objUri = (String) request.getParameter("suri");
    SWBResourceURL renderURL = paramRequest.getRenderUrl().setParameter("suri", objUri);
    long maxTweetID = 0L;

%>

<div dojoType="dojox.layout.ContentPane">
    <script type="dojo/method">
        resetTabTitle('<%=objUri%>','/discover', 'Discover' );
   </script>
</div>
   
   <div>
       Busqueda: <input type="text"/>
   </div>
   
<div class="swbform">
<%
    try {
            //gets Twitter4j instance with account credentials
            out.println("<div align=\"center\"><h2>Discover</h2><br/></div>");            

            Paging paging = new Paging(); //used to set maxId and count
            paging.count(5);//Gets a number of tweets of timeline. Max value is 200           
            int i = 0;            
            Trends dailyTrends = twitterBean.getPlaceTrends(1);

            // Print the trends.
            for (Trend trends : dailyTrends.getTrends()) {
                System.out.println("get name : " + trends.getName());
                System.out.println("get URL : " + trends.getURL());
                System.out.println("get Query : " + trends.getQuery());
                out.write("<p><a href=\"" + trends.getURL() + "\" target=\"_blank\">" + trends.getName() +"</a></p>");                
                i++;
            }
            
            System.out.println("Total of trends" + i);
            ResponseList<Category> categories = twitterBean.getSuggestedUserCategories();
            Iterator<Category> catIt = categories.iterator();
            while(catIt.hasNext()){
                Category c = catIt.next();
                System.out.println("Category:" + c.getName() + " slug:" + c.getSlug());
            }
            
            ResponseList<User> users = twitterBean.getUserSuggestions("negocios");
            
            for (User user : users) {
                if (user.getStatus() != null) {
                    System.out.println("@" + user.getScreenName() + " - " + user.getStatus().getText());
                } else {
                    // the user is protected
                    System.out.println("@" + user.getScreenName());
                }
            }
              
        } catch (Exception te) {
            System.out.println("Se presento un error en Discover!!");
            te.printStackTrace();
        }            
%>    
</div>