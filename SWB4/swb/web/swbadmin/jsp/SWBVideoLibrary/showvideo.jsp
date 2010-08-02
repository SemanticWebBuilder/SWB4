<%@page import="org.semanticwb.model.Country"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.servlet.SWBHttpServletResponseWrapper"%>
<%@page import="org.semanticwb.portal.api.SWBResource"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.*"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="org.semanticwb.portal.resources.sem.videolibrary.*"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.User"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%

    String usrlanguage = paramRequest.getUser().getLanguage();
    Locale locale=new Locale(usrlanguage);
    Calendar calendar = Calendar.getInstance(locale);
    DateFormat sdf = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale(usrlanguage));
    VideoContent content=(VideoContent)request.getAttribute("content");
    String title=SWBUtils.TEXT.encodeExtendedCharacters(content.getResourceBase().getTitle(usrlanguage));
    if(title!=null && title.trim().equals(""))
    {
        title=SWBUtils.TEXT.encodeExtendedCharacters(content.getResourceBase().getTitle());
    }
    String date="";
    if(content.getPublishDate()!=null)
    {
        date=sdf.format(content.getPublishDate());
    }
    String duration="";
    duration=SWBUtils.TEXT.encodeExtendedCharacters(String.valueOf(content.getDuration()));
    if(duration!=null && duration.trim().equals(""))
    {
        duration=SWBUtils.TEXT.encodeExtendedCharacters(String.valueOf(content.getDuration()));
    }

%>
<%=content.getCode()%><br>
<%=title%><br>
<%
    if(duration!=null && !duration.equals(""))
    {
        %>
            <%=duration%> segundos<br>
        <%
    }
%>

<%=date%><br>
