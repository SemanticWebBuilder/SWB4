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
        
        for(SWBNewContent content : contents)
        {
            
            SWBResourceURL url=paramRequest.getRenderUrl();
            url.setParameter("uri",content.getResourceBase().getSemanticObject().getURI());
            url.setMode(paramRequest.Mode_VIEW);
            url.setCallMethod(paramRequest.Call_CONTENT);
            String urlcontent=url.toString().replace("&", "&amp;");
            String title=SWBUtils.TEXT.encodeExtendedCharacters(content.getResourceBase().getTitle(usrlanguage));
            if(title!=null && title.trim().equals(""))
            {
                title=SWBUtils.TEXT.encodeExtendedCharacters(content.getResourceBase().getTitle());
            }
            String ago="";
            String source=content.getSource();
            String date="";
            if(date!=null && !date.trim().equals(""))
            {
                ago=SWBUtils.TEXT.getTimeAgo(content.getPublishDate(), usrlanguage);
            }
            String country="";
            if(content.getCountry()!=null)
            {
                country="("+SWBUtils.TEXT.encodeExtendedCharacters(content.getCountry().getTitle(usrlanguage))+")";
            }
            String originalTitle="";
            if(content.getOriginalTitle()!=null)
            {
                originalTitle=SWBUtils.TEXT.encodeExtendedCharacters(content.getOriginalTitle());
            }
            String pathPhoto = SWBPortal.getContextPath() + "/swbadmin/jsp/SWBNews/sinfoto.png";
            String image="";
            if(content.getImage()!=null)
            {
                image=content.getImage();
                pathPhoto=SWBPortal.getWebWorkPath()+content.getSemanticObject().getWorkPath()+"/thmb_image_"+image;
            }            
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
            <div class="entradaVideos">
        <div class="thumbVideo">
            <%
                if(pathPhoto!=null)
                {
                    %>
                    <img width="120" height="120" alt="<%=title%>" src="<%=pathPhoto%>" />
                    <%
                }
            %>

        </div>
        <div class="infoVideo">
            <h3><%=title%><%
                    if(country!=null && !country.equals(""))
                    {
                                   %>&nbsp;<%=country%><%
                    }
                %>
            </h3>
                <%
                    if(originalTitle!=null && !originalTitle.trim().equals(""))
                        {
                        %>
                        <p><%=originalTitle%></p>
                        <%
                        }
                %>
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
                        if(content.getSourceURL()==null)
                        {

                            %>
                            <p>Fuente: <%=source%></p>
                            <%
                        }
                        else
                        {
                            String urlsource=content.getSourceURL();
                            urlsource=urlsource.replace("&", "&amp;");
                            %>
                            <p>Fuente: <a href="<%=urlsource%>"><%=source%></a></p>
                            <%
                        }
                    }
                %>
            <p class="vermas"><a href="<%=urlcontent%>">Ver Más</a></p>
        </div>
        <div class="clear">&nbsp;</div>
        </div>
            <% 
        }
        
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