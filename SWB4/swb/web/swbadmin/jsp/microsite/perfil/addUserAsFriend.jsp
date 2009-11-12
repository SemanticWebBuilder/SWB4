<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.SWBPortal"%>

<%
            User user = (User) request.getAttribute("user");
            WebSite site = ((WebPage) request.getAttribute("webpage")).getWebSite();
            if (user != null && request.getParameter("user") != null)
            {
                SemanticObject semObj = SemanticObject.createSemanticObject(request.getParameter("user"));
                User propect = (User) semObj.createGenericInstance(); // es un tercero
                String url = "";
                boolean isFriend = false;
                Iterator<Friendship> itMyFriends = Friendship.listFriendshipByFriend(user, site);
                while (itMyFriends.hasNext())
                {
                    Friendship friendShip = itMyFriends.next();
                    Iterator<User> itfriendUser = friendShip.listFriends();
                    while (itfriendUser.hasNext())
                    {
                        User userfriend = itfriendUser.next();
                        if (userfriend.getURI().equalsIgnoreCase(propect.getURI()))
                        {
                            isFriend = true;
                            break;
                        }
                    }
                    if (isFriend)
                    {
                        break;
                    }
                }
                if (!isFriend) // puede ser un invitado
                {
                    Iterator<FriendshipProspect> itFriendshipProspect = FriendshipProspect.listFriendshipProspectByFriendShipRequested(user, site);
                    if (itFriendshipProspect.hasNext())
                    {
                        FriendshipProspect fp = itFriendshipProspect.next();
                        if (fp.getFriendShipRequester().getURI().equalsIgnoreCase(propect.getURI()))
                        {
                            isFriend = true;
                        }                        
                    }
                }

                if (!isFriend)
                {
%>
<li><a href="<%=url%>" >Invitar a <%=propect.getFullName()%> a ser tu amigo</a></li>
<%
                }
            }
%>
