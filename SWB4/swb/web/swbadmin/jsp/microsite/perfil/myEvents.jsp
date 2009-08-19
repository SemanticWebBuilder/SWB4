<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>

<div id="contactos">
  <h2>Mis Subscripciones</h2>
  <ul>
<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
User user = paramRequest.getUser();
WebPage wpage = paramRequest.getWebPage();
Member member = Member.getMember(user, wpage);
String lang="es";
String year = request.getParameter("y");
String month = request.getParameter("m");
String day = request.getParameter("d");
Date current = null;

if (day != null && month != null && year != null) {
    current = new Date(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day));
}

System.out.println(current);
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
