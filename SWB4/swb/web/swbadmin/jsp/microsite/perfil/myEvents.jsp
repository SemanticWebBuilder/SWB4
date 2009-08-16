<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>

<div id="contactos">
  <h2>Mis Subscripciones</h2>
  <ul>
<%
User user=paramRequest.getUser();
String lang="es";
if(user.getLanguage()!=null) lang=user.getLanguage();
Iterator<EventElement> itEvents=EventElement.listEventElementByHasAttendant(user);
while(itEvents.hasNext()){
    EventElement event=itEvents.next();
    %>
        <li><a href="<%=event.getEventWebPage().getUrl()%>?act=detail&uri=<%=event.getEncodedURI()%>">event.getTitle()</a></li>
    <%
}
%>
  </ul>
</div>
