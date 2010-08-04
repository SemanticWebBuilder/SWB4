<%@page import="org.semanticwb.model.Country"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.servlet.SWBHttpServletResponseWrapper"%>
<%@page import="org.semanticwb.portal.api.SWBResource"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="org.semanticwb.portal.resources.sem.videolibrary.*"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
    String usrlanguage = paramRequest.getUser().getLanguage();    
    DateFormat sdf = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale(usrlanguage));

    List<VideoContent> contents=(List<VideoContent>)request.getAttribute("list");
    if(contents!=null && contents.size()>0)
    {        
        for(VideoContent content : contents)
        {
            if(content.getPublishDate()!=null)
            {
                int month=-1;
                if(request.getParameter("month")!=null)
                {
                    try
                    {
                        month=Integer.parseInt(request.getParameter("month"));
                    }
                    catch(NumberFormatException e)
                    {
                        e.printStackTrace();
                    }
                }
                Calendar cal=Calendar.getInstance();
                cal.setTime(content.getPublishDate());
                int year=Calendar.getInstance().get(Calendar.YEAR);
                if(request.getParameter("year")!=null)
                {
                    try
                    {
                        year=Integer.parseInt(request.getParameter("year"));
                    }
                    catch(NumberFormatException e)
                    {
                        e.printStackTrace();
                    }
                }
                if(!(month==cal.get(Calendar.MONTH) && year==cal.get(Calendar.YEAR)))
                {
                    continue;
                }
            }
            SWBResourceURL url=paramRequest.getRenderUrl();
            url.setParameter("uri",content.getResourceBase().getSemanticObject().getURI());
            url.setMode(paramRequest.Mode_VIEW);
            url.setCallMethod(paramRequest.Call_CONTENT);
            String title=SWBUtils.TEXT.encodeExtendedCharacters(content.getResourceBase().getTitle(usrlanguage));             
            if(title!=null && title.trim().equals(""))
            {
                title=SWBUtils.TEXT.encodeExtendedCharacters(content.getResourceBase().getTitle());
            }
           
            String date=sdf.format(content.getPublishDate());
            String preview=content.getPreview();
            String source=content.getSource();
            String ago="";
            if(date!=null && !date.trim().equals(""))
            {
                ago=SWBUtils.TEXT.getTimeAgo(content.getPublishDate(), usrlanguage);
            }
            %>
            <div class="entradaVideos">
        <div class="thumbVideo">
          <img alt="<%=title%>" src="<%=preview%>" />
        </div>
        <div class="infoVideo">
            <h3><%=title%></h3>
            <p class="fechaVideo">
                <%
                    if(date!=null && !date.trim().equals(""))
                    {
                        %>
                        <%=date%> - <%=ago%>
                        <%
                    }
                %>

            </p>
            <%
                    if(source!=null)
                    {
                        if(content.getVideoWebPage()==null)
                        {
                            %>
                            <p>Fuente: <%=source%></p>
                            <%
                        }
                        else
                        {
                            %>
                            <p>Fuente: <a href="<%=content.getVideoWebPage()%>"><%=source%></a></p>
                            <%
                        }
                    }
                %>
            <p class="vermas"><a href="<%=url%>">Ver M�s</a></p>
        </div>
        <div class="clear">&nbsp;</div>
        </div>
            <%
        }
        SWBResourceURL urlall=paramRequest.getRenderUrl();
        urlall.setMode(urlall.Mode_VIEW);
        urlall.setCallMethod(urlall.Call_CONTENT);
        String viewAll="[Ver todos los videos]";
        if(paramRequest.getUser().getLanguage()!=null && !paramRequest.getUser().getLanguage().equalsIgnoreCase("es"))
        {
            viewAll="[View all videos]";
        }
        %>
        <p><a href="<%=urlall%>"><%=viewAll%></a></p>
        <%
        
    }
%>