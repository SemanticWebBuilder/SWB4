<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>

<div id="contactos">    
<%
User user=paramRequest.getUser();
String lang="es";
if(user.getLanguage()!=null) lang=user.getLanguage();
String year = request.getParameter("year");
String month = request.getParameter("month");
String day = request.getParameter("day");
WebPage wpage = paramRequest.getWebPage();
SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
Date current = new Date(Integer.valueOf(year) - 1900, Integer.valueOf(month), Integer.valueOf(day));
%>
<h2>Mis subscripciones del <%=dateFormat.format(current)%></h2>
<ul>
<%
Iterator<EventElement> itEvents=EventElement.listEventElementsByDate(user, current, wpage, wpage.getWebSite());
while(itEvents.hasNext()){
    EventElement event=itEvents.next();
    %>
        <li><a href="<%=event.getEventWebPage().getUrl()%>?act=detail&uri=<%=event.getEncodedURI()%>"><%=event.getTitle()%></a></li>
    <%
}
%>
  </ul>
  <a class="tooltip" href="<%=paramRequest.getRenderUrl()%>">Regresar</a>
</div>
