        <jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
        <%@page import="org.semanticwb.model.User"%>
        <%@page import="org.semanticwb.portal.community.*"%>
        <%@page import="org.semanticwb.model.WebPage"%>
        <%@page import="java.util.*"%>
        <%@page import="org.semanticwb.platform.SemanticObject"%>

        <%
        User user=paramRequest.getUser();
        WebPage wpage=paramRequest.getWebPage();

        Iterator<User> itUsers=paramRequest.getWebPage().getWebSite().getUserRepository().listUsers();
        while(itUsers.hasNext())
        {
            User userprosp=itUsers.next();
            %>
                <ul>
                    <li><a href="<%=wpage.getUrl()%>?userprosp=<%=userprosp.getEncodedURI()%>"><%=userprosp.getFullName()%></a></li>
                </ul>
            <%
        }
        if(request.getParameter("userprosp")!=null){
            String userProsp=request.getParameter("userprosp");
            SemanticObject semObj=SemanticObject.createSemanticObject(userProsp);
            User user2Friend=(User)semObj.createGenericInstance();
            FriendshipProspect newFriendShip=FriendshipProspect.createFriendshipProspect(wpage.getWebSite());
            newFriendShip.setFriendShipRequester(user);
            newFriendShip.setFriendShipRequested(user2Friend);
            System.out.println("propuesta de relación creada entre los usuarios:"+user.getURI()+",y :"+user2Friend.getURI());
        }
     %>