<%@page import="org.semanticwb.model.GenericObject"%>
<%@page import="org.semanticwb.model.Country"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.servlet.SWBHttpServletResponseWrapper"%>
<%@page import="org.semanticwb.portal.api.SWBResource"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="org.semanticwb.portal.resources.sem.news.*"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.model.GenericIterator"%>
<%@page import="org.semanticwb.model.WebPage"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%!
    String idNoticias="Noticias";
%>
<%
    WebPage noticias= paramRequest.getWebPage().getWebSite().getWebPage(idNoticias);
    if(noticias!=null)
    {
        SWBResourceURL urldetail=null;        
        SWBResourceURL urlrss=null;
        GenericIterator<Resource> resources=noticias.listResources();
        while(resources.hasNext())
        {
            Resource resource=resources.next();
            if(resource.getResourceData()!=null)
            {

               GenericObject obj=resource.getResourceData().createGenericInstance();
               if(obj instanceof SWBNews)
               {
                   SWBNews semResource=(SWBNews)obj;
                   ((org.semanticwb.portal.api.SWBParamRequestImp)paramRequest).setResourceBase(resource);
                   ((org.semanticwb.portal.api.SWBParamRequestImp)paramRequest).setVirtualResource(resource);
                   ((org.semanticwb.portal.api.SWBParamRequestImp)paramRequest).setTopic(noticias);
                    urldetail=paramRequest.getRenderUrl();
                    urlrss=paramRequest.getRenderUrl();
                    urlrss.setMode("rss");
                    urlrss.setCallMethod(urlrss.Call_DIRECT);
                    urldetail.setMode(paramRequest.Mode_VIEW);
               }
            }
        }
        String usrlanguage = paramRequest.getUser().getLanguage();
        DateFormat sdf = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale(usrlanguage));
        int limit = 3;
        List<SWBNewContent> contents=(List<SWBNewContent>)request.getAttribute("news");
        if(urldetail!=null)
        {            
            int i=0;
            for(SWBNewContent content : contents)
            {
                if(content.isHomeShow())
                {
                    String pathPhoto = SWBPortal.getContextPath() + "/swbadmin/jsp/SWBNews/noevent.jpg";
                    String url="#";
                    i++;
                    urldetail.setParameter("uri",content.getResourceBase().getSemanticObject().getEncodedURI());
                    url=urldetail.toString();
                    String title=SWBUtils.TEXT.encodeExtendedCharacters(content.getResourceBase().getTitle(usrlanguage));
                    if(title!=null && title.trim().equals(""))
                    {
                        title=SWBUtils.TEXT.encodeExtendedCharacters(content.getResourceBase().getTitle());
                    }
                    String description=SWBUtils.TEXT.encodeExtendedCharacters(content.getResourceBase().getDescription(usrlanguage));
                    if(title!=null && title.trim().equals(""))
                    {
                        description=SWBUtils.TEXT.encodeExtendedCharacters(content.getResourceBase().getDescription());
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
                    String image="";
                    if(content.getImage()!=null)
                    {
                        image=content.getImage();
                    }
                    %>
                    <div class="nota">
                    <a href="<%=url%>">
                        <img border="0" alt="Imagen noticia" src="<%=pathPhoto%>" />
                    </a><br>
                    <a href="<%=url%>"><%=title%></a><br>
                    <p><i><%=description%></i></p>

            </div>
                    <%

                    if(i>=limit)
                    {
                        break;
                    }
                }
            }
            String urlnoticias=noticias.getUrl();
            %>
            
            <%
        }
    }

%>
   
