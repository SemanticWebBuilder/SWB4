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
<%@page import="org.semanticwb.portal.resources.sem.videolibrary.*"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.model.GenericIterator"%>
<%@page import="org.semanticwb.model.WebPage"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%!
    String idPage="Videos";
%>
<%
    WebPage wp= paramRequest.getWebPage().getWebSite().getWebPage(idPage);
    if(wp!=null)
    {        
        SWBResourceURL urldetail=null;        
        GenericIterator<Resource> resources=wp.listResources();
        while(resources.hasNext())
        {
            Resource resource=resources.next();
            if(resource.getResourceData()!=null)
            {
               ((org.semanticwb.portal.api.SWBParamRequestImp)paramRequest).setResourceBase(resource);
               ((org.semanticwb.portal.api.SWBParamRequestImp)paramRequest).setVirtualResource(resource);
               ((org.semanticwb.portal.api.SWBParamRequestImp)paramRequest).setTopic(wp);
               urldetail=paramRequest.getRenderUrl();
               urldetail.setMode(paramRequest.Mode_VIEW);
            }
        }
        String usrlanguage = paramRequest.getUser().getLanguage();
        DateFormat sdf = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale(usrlanguage));
        int limit = 5;
        ArrayList<VideoContent> contentsToShow=new ArrayList<VideoContent>();
        List<VideoContent> contents=(List<VideoContent>)request.getAttribute("list");
        if(urldetail!=null)
        {
            int i=0;
            for(VideoContent content : contents)
            {
                if(content.isHomeShow())
                {
                    contentsToShow.add(content);
                    if(i>=limit)
                    {
                        break;
                    }
                }
            }
            i=1;
            contents=contentsToShow;

            if(contentsToShow.size()>0)
            {
                String code=contentsToShow.get(0).getCode();
                //code=SWBUtils.TEXT.decodeExtendedCharacters(code);
                %>
                        <%=code%>
                <%
                contentsToShow.remove(0);
            }
            if(contentsToShow.size()>0)
            {

                %>
                <div id="listadoVideos">
                <ul>
                <%
                for(VideoContent content : contentsToShow)
                {
                        String pathPhoto = SWBPortal.getContextPath() + "/swbadmin/jsp/SWBVideoLibrary/sinfoto.png";
                        String url="#";
                        i++;
                        urldetail.setParameter("uri",content.getResourceBase().getSemanticObject().getURI());
                        url=urldetail.toString();
                        String title=SWBUtils.TEXT.encodeExtendedCharacters(content.getResourceBase().getTitle(usrlanguage));
                        if(title!=null && title.trim().equals(""))
                        {
                            title=SWBUtils.TEXT.encodeExtendedCharacters(content.getResourceBase().getTitle());
                        }
                        title=SWBUtils.TEXT.cropText(title, 65);
                        %>
                        <li><a rel="destino" href="<%=urldetail%>"><%=title%></a></li>
                        <%

                }
                %>
                </ul>
                <p class="listaVideos"><a href="<%=wp.getUrl()%>">sección completa de videos</a></p>
                </div>
                <%
            }            
        }
    }

%>











