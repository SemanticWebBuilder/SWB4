<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            User user = (User) request.getAttribute("user");
            WebSite site = ((WebPage) request.getAttribute("webpage")).getWebSite();
            Iterator<Friendship> itMyFriends = Friendship.listFriendshipByFriend(user, site);
            int count=0;
            while (itMyFriends.hasNext())
            {
                Friendship friendShip = itMyFriends.next();
                Iterator<User> itfriendUser = friendShip.listFriends();
                while (itfriendUser.hasNext())
                {
                    User friendUser = itfriendUser.next();
                    if (!friendUser.getURI().equals(user.getURI()))
                    {
                        count++;
                    }
                }
            }
            String url=site.getWebPage("Amigos").getUrl();
%>
          <li><a href="<%=url%>" >Mis amigos (<%=count%>)</a></li>