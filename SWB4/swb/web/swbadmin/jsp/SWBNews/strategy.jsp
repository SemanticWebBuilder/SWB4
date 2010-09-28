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

    class SWBNewContentComparator implements Comparator<SWBNewContent>
    {
        public int compare(SWBNewContent o1,SWBNewContent o2)
        {
            return o1.getResourceBase().getPriority()>=o2.getResourceBase().getPriority()?1:-1;            
        }
    }
%>
<%
    WebPage noticias= paramRequest.getWebPage().getWebSite().getWebPage(idNoticias);
    if(noticias!=null && noticias.isActive())
    {
        String usrlanguage = paramRequest.getUser().getLanguage();
        DateFormat sdf = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale(usrlanguage));
        int limit = 8;
        List<SWBNewContent> contents=(List<SWBNewContent>)request.getAttribute("news");
        Collections.sort(contents, new SWBNewContentComparator());
        ArrayList<SWBNewContent> contentstoshow= new ArrayList<SWBNewContent>();
        
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
            
            if(contentstoshow.size()>0 && contents.get(0).getImage()!=null)
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
                url=noticias.getUrl()+"?uri="+content.getResourceBase().getSemanticObject().getEncodedURI();
                String pathPhoto = null;//SWBPortal.getContextPath() + "/swbadmin/jsp/SWBNews/sinfoto.png";
                String image="";
                if(content.getImage()!=null)
                {
                    image=content.getImage();
                    pathPhoto=SWBPortal.getWebWorkPath()+content.getSemanticObject().getWorkPath()+"/thmb_image_"+image;
                }
                String titleImage=title.replace('"', '\'');
                
                %>
                    <div class="mainNews">
                      <p><a href="<%=url%>"><%=title%></a></p>
                      <%
                        if(pathPhoto!=null)
                        {
                            %>
                            <img src="<%=pathPhoto%>" alt="<%=titleImage%>" />
                            <%
                        }
                      %>
                      
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
                    url=noticias.getUrl()+"?uri="+content.getResourceBase().getSemanticObject().getEncodedURI();
                    %>
                            <li><a href="<%=url%>"><%=title%></a></li>
                    <%
                }
                %>
                        </ul>
                <%
            }
            String urlNews=noticias.getUrl();
            String titleviewoldNews="Ver más";            
            %>
                <p class="vermas"><a href="<%=urlNews%>"><%=titleviewoldNews%></a></p>
            <%
        
    }

%>
   
