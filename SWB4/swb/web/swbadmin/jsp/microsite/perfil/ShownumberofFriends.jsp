<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            User user = (User) request.getAttribute("user");           
            WebSite site = ((WebPage) request.getAttribute("webpage")).getWebSite();
            if(!user.isSigned()) return;
            Iterator<Friendship> itMyFriends = Friendship.listFriendshipByFriend(user, site);
            int count=0;
            while (itMyFriends.hasNext())
            {
                itMyFriends.next();
                count++;                
            }
            String url=site.getWebPage("Amigos").getUrl();
%>
          <li><a href="<%=url%>" >Mis amigos (<%=count%>)</a></li>