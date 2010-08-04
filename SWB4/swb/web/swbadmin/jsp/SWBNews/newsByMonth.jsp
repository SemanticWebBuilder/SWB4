<%@page import="org.semanticwb.model.Country"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.servlet.SWBHttpServletResponseWrapper"%>
<%@page import="org.semanticwb.portal.api.SWBResource"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="org.semanticwb.portal.resources.sem.news.*"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
    String usrlanguage = paramRequest.getUser().getLanguage();
    Locale locale=new Locale(usrlanguage);
    Calendar calendar = Calendar.getInstance(locale);   
    DateFormat sdf = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale(usrlanguage));
    
    List<SWBNewContent> contents=(List<SWBNewContent>)request.getAttribute("news");
    if(contents!=null && contents.size()>0)
    {
        %>
        <ul class="listaLinks">
        <%

        
        
        for(SWBNewContent content : contents)
        {            
            SWBResourceURL url=paramRequest.getRenderUrl();
            url.setParameter("uri",content.getResourceBase().getSemanticObject().getURI());
            url.setMode(paramRequest.Mode_VIEW);
            url.setCallMethod(paramRequest.Call_CONTENT);
            String title=SWBUtils.TEXT.encodeExtendedCharacters(content.getResourceBase().getTitle(usrlanguage));
            if(title!=null && title.trim().equals(""))
            {
                title=SWBUtils.TEXT.encodeExtendedCharacters(content.getResourceBase().getTitle());
            }
            String country="";
            if(content.getCountry()!=null)
            {
                country="("+SWBUtils.TEXT.encodeExtendedCharacters(content.getCountry().getTitle(usrlanguage))+")";
            }
            String date="";
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
                date=sdf.format(content.getPublishDate());    

            }
            %>
            <li><a href="<%=url%>" ><b><%=title%> <%=country%> <%=date%></b></a></li>
            <% 
        }
        %>
             </ul>
        <%
        SWBResourceURL urlall=paramRequest.getRenderUrl();
        urlall.setMode(urlall.Mode_VIEW);
        urlall.setCallMethod(urlall.Call_CONTENT);
        String viewAll="[Ver todas las noticias]";
        if(paramRequest.getUser().getLanguage()!=null && !paramRequest.getUser().getLanguage().equalsIgnoreCase("es"))
        {
            viewAll="[View all news]";
        }
        %>
        <p><a href="<%=urlall%>"><%=viewAll%></a></p>
        <%
    }
%>