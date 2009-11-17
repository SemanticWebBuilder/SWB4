<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%@page import="org.semanticwb.platform.SemanticObject"%><%
            User user = (User) request.getAttribute("user");
            WebSite site = ((WebPage) request.getAttribute("webpage")).getWebSite();
            if (request.getParameter("user") != null && !request.getParameter("user").equals(user.getURI()))
            {
                SemanticObject semObj = SemanticObject.createSemanticObject(request.getParameter("user"));
                user = (User) semObj.createGenericInstance();
            }
            else
            {
                if (!user.isSigned())
                {
                    %>
                      <li>&nbsp</li>
                    <%

                    return;
                }
            }

            Iterator<Friendship> itMyFriends = Friendship.ClassMgr.listFriendshipByFriend(user, site);
            int count = 0;
            while (itMyFriends.hasNext())
            {
                itMyFriends.next();
                count++;
            }
            String url = site.getWebPage("Amigos").getUrl();
%>
<li><a href="<%=url%>" >Amigos (<%=count%>)</a></li>