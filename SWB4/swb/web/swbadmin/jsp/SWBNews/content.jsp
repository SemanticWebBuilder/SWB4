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
        %>
        <ul class="listaLinks">
        <%
        
        // muestra las 15 primeras noticias
        int inew=0;
        for(SWBNewContent content : contents)
        {
            inew++;
            SWBResourceURL url=paramRequest.getRenderUrl();
            url.setMode(paramRequest.Mode_VIEW);
            url.setParameter("uri",content.getResourceBase().getSemanticObject().getEncodedURI());
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
            if(content.getCountry()!=null)
            {
                country="("+SWBUtils.TEXT.encodeExtendedCharacters(content.getCountry().getTitle(usrlanguage))+")";
            }
            String urlcontent=url.toString().replace("&", "&amp;");
            
            %>
            <li><a href="<%=urlcontent%>" ><b><%=title%> <%=country%> <%=date%></b></a></li>
            <%
            if(inew>=limit)
            {
                break;
            }
        }
        


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
