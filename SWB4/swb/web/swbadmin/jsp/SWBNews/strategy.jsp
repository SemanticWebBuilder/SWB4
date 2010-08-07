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
    if(noticias!=null && noticias.isActive())
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
        int limit = 5;
        List<SWBNewContent> contents=(List<SWBNewContent>)request.getAttribute("news");
        ArrayList<SWBNewContent> contentstoshow= new ArrayList<SWBNewContent>();
        if(urldetail!=null)
        {            
            int i=0;            
            for(SWBNewContent content : contents)
            {
                if(content.isHomeShow())
                {
                    i++;
                    contentstoshow.add(content);                    
                    if(i>=limit)
                    {
                        break;
                    }
                }                
            }
            if(contentstoshow.size()>0)
            {
                SWBNewContent content=contentstoshow.get(0);
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

                String url="#";
                String pathPhoto = SWBPortal.getContextPath() + "/swbadmin/jsp/SWBNews/sinfoto.png";
                String image="";
                if(content.getImage()!=null)
                {
                    image=content.getImage();
                    pathPhoto=SWBPortal.getWebWorkPath()+content.getSemanticObject().getWorkPath()+"/thmb_image_"+image;
                }
                %>
                    <div class="mainNews">
                      <p><a href="<%=url%>"><%=title%></a></p>
                      <img src="<%=pathPhoto%>" alt="<%=title%>" />
                      <div class="clear">&nbsp;</div>
                    </div>
                <%
                contentstoshow.remove(0);
            }
            if(contentstoshow.size()>0)
            {
                %>
                <ul class="underline">
                <%
                for(SWBNewContent content : contentstoshow)
                {
                    String title=SWBUtils.TEXT.encodeExtendedCharacters(content.getResourceBase().getTitle(usrlanguage));
                    if(title!=null && title.trim().equals(""))
                    {
                        title=SWBUtils.TEXT.encodeExtendedCharacters(content.getResourceBase().getTitle());
                    }
                    String url="#";
                    urldetail.setParameter("uri",content.getResourceBase().getSemanticObject().getURI());
                    url=urldetail.toString();
                    %>
                            <li><a href="<%=url%>"><%=title%></a></li>
                    <%
                }
                %>
                        </ul>
                <%
            }
            String urlNews=noticias.getUrl();
            String titleviewoldNews="Ver noticias anteriores";
            if(usrlanguage!=null && !usrlanguage.equals("es"))
            {
                titleviewoldNews="View old news";
            }
            %>
                <p class="vermas"><a href="<%=urlNews%>"><%=titleviewoldNews%></a></p>
            <%
        }
    }

%>
   
