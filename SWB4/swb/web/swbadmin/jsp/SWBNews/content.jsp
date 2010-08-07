<%@page import="org.semanticwb.model.Country"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.servlet.SWBHttpServletResponseWrapper"%>
<%@page import="org.semanticwb.portal.api.SWBResource"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.*"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="org.semanticwb.portal.resources.sem.news.*"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.User"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%!
    private static String mensaje = "Noticias de ";
    private static String mesage = "News of ";
    private static String ultmsg = "Noticias del mes";
    private static String lastmsg = "Previous news";
    static String[] meses =
    {
        "Enero", "Febrero", "Marzo", "Abril", "Mayo",
        "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };
    static String[] months =
    {
        "January", "February", "March", "April", "May",
        "June", "July", "August", "September", "October", "November", "December"
    };
    private String getMonth(int month,User user)
    {
        String getMonth=months[month];
        if(user.getLanguage()!=null && user.getLanguage().equalsIgnoreCase("es"))
        {
            getMonth=meses[month];
        }
        return getMonth;
    }
%>
<%

    // muestra lista de noticias en listado
    String usrlanguage = paramRequest.getUser().getLanguage();
    Locale locale=new Locale(usrlanguage);
    Calendar calendar = Calendar.getInstance(locale);

    int currentMonth=calendar.get(Calendar.MONTH);
    
    DateFormat sdf = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale(usrlanguage));
    int limit = 15;
    List<SWBNewContent> contents=(List<SWBNewContent>)request.getAttribute("news");
   
    if(contents!=null && contents.size()>0)
    {
               
        // muestra las 15 primeras noticias
        int inew=0;
        for(SWBNewContent content : contents)
        {
            inew++;
            SWBResourceURL url=paramRequest.getRenderUrl();
            url.setMode(paramRequest.Mode_VIEW);
            url.setParameter("uri",content.getResourceBase().getSemanticObject().getURI());
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
            String country="";
            if(content.getCountry()!=null && content.getCountry().getDisplayTitle(usrlanguage)!=null && !content.getCountry().getDisplayTitle(usrlanguage).equals(""))
            {
                country="("+SWBUtils.TEXT.encodeExtendedCharacters(content.getCountry().getDisplayTitle(usrlanguage))+")";
            }
            String originalTitle="";
            if(content.getOriginalTitle()!=null)
            {
                originalTitle=SWBUtils.TEXT.encodeExtendedCharacters(content.getOriginalTitle());
            }
            String urlcontent=url.toString().replace("&", "&amp;");
            String ago="";
            String source=content.getSource();
            if(date!=null && !date.trim().equals(""))
            {
                ago=SWBUtils.TEXT.getTimeAgo(content.getPublishDate(), usrlanguage);
            }
            String pathPhoto = SWBPortal.getContextPath() + "/swbadmin/jsp/SWBNews/sinfoto.png";
            String image="";
            if(content.getImage()!=null)
            {
                image=content.getImage();
                pathPhoto=SWBPortal.getWebWorkPath()+content.getSemanticObject().getWorkPath()+"/thmb_image_"+image;
            }
            String titleImage=title.replace('"', '\'');
            %>
            <div class="entradaVideos">
        <div class="thumbVideo">
            <%
                if(pathPhoto!=null)
                {
                    %>
                    <img width="120" height="120" alt="<%=titleImage%>" src="<%=pathPhoto%>" />
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
            if(inew>=limit)
            {
                break;
            }
        }

        %>
        <ul>
        <%


        String[] years=SWBNews.getYears(contents);
        for(String year : years)
        {
            int iyear=Integer.parseInt(year);
            // muestra liga para noticias por mes
            
            for(int month=11;month>=0;month--)
            {
                if(SWBNews.hasNews(contents, month,iyear))
                {                       
                    String titleMonth=" "+getMonth(month,paramRequest.getUser())+" "+iyear;
                    SWBResourceURL url=paramRequest.getRenderUrl();
                    url.setMode("month");
                    url.setParameter("month", String.valueOf(month));
                    url.setParameter("year", year);
                    String currentyear=String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
                    String urlcontent=url.toString().replace("&", "&amp;");
                    if(currentMonth==month && currentyear.equals(year))
                    {
                        %>
                        <li class="listaLinksMes"><a href="<%=urlcontent%>"><%=ultmsg%></a></li>
                        <%
                    }
                    else
                    {
                        %>
                        <li class="listaLinksMes"><a href="<%=urlcontent%>"><%=mensaje%><%=titleMonth%></a></li>
                        <%
                    }

                }
            }
        }
        %>
             </ul>   
        <%
    }   
%>
