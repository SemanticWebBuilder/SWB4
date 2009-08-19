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
WebPage wpage = paramRequest.getWebPage();
String lang="es";
String year = request.getParameter("year");
String month = request.getParameter("month");
String day = request.getParameter("day");
Date current = null;

if (day != null && month != null && year != null) {
    current = new Date(Integer.valueOf(year) - 1900, Integer.valueOf(month), Integer.valueOf(day));
}

if(user.getLanguage()!=null) lang=user.getLanguage();
Iterator<EventElement> itEvents;

if (current == null) {
    itEvents = EventElement.listEventElementByAttendant(user, paramRequest.getWebPage().getWebSite());
} else {
    itEvents = EventElement.listEventElementsByDate(user, current, wpage, wpage.getWebSite());
}
while(itEvents.hasNext()){
    EventElement event=itEvents.next();    
    %>
        <li><a href="<%=event.getEventWebPage().getUrl()%>?act=detail&uri=<%=event.getEncodedURI()%>"><%=event.getTitle()%></a></li>
    <%
}
%>
  </ul>  
</div>
