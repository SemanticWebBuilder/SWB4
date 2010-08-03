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
<%@page import="java.util.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%
    String pathPhoto = null;//SWBPortal.getContextPath() + "/swbadmin/jsp/SWBNews/sinfoto.png";
   
    User user=paramRequest.getUser();
    // muestra el recurso
    SWBNewContent content=(SWBNewContent)request.getAttribute("content");    
    
    String title=SWBUtils.TEXT.encodeExtendedCharacters(content.getResourceBase().getDisplayTitle(user.getLanguage()));
    String image="";
    if(content.getImage()!=null)
    {
        image=content.getImage();
        pathPhoto=SWBPortal.getWebWorkPath()+content.getSemanticObject().getWorkPath()+"/image_"+image;
    }
    %>
    <h2 class="sectionTitle"><%=title%>
    <%
    if(content.getCountry()!=null)
    {
        String country=content.getCountry().getDisplayTitle(user.getLanguage());
        %>
            (<%=country%>)
        <%
    }

    %>
    </h2>
    <%
        if(pathPhoto!=null)
        {
            %>
             <img alt="Imagen noticia" width="368" height="230" src="<%=pathPhoto%>" />
        <br/>
            <%
        }
    %>
   
    <%
    if(content.getOriginalTitle()!=null)
    {
        String originalTitle=SWBUtils.TEXT.encodeExtendedCharacters(content.getOriginalTitle());
        %>
            <%=originalTitle%><br/>
        <%
    }
    if(content.getSource()!=null)
    {
        String source=SWBUtils.TEXT.encodeExtendedCharacters(content.getSource());
        if(content.getSourceURL()!=null)
        {
            %>
                <a target="_blank" href="<%=content.getSourceURL()%>"><%=source%></a>
            <%
        }
        else
        {
            %>
                <%=source%>
            <%
        }
    }
    if(content.getAuthor()!=null)out.println(SWBUtils.TEXT.encodeExtendedCharacters(content.getAuthor())+"<br/>");
    if(content.getPublishDate()!=null)out.println(SWBUtils.TEXT.getStrDate(content.getPublishDate(),"es","dd/mm/yyyy")+"<br/>");
    

    SWBHttpServletResponseWrapper res = new SWBHttpServletResponseWrapper(response);
    ((org.semanticwb.portal.api.SWBParamRequestImp)paramRequest).setResourceBase(content.getResourceBase());
    ((org.semanticwb.portal.api.SWBParamRequestImp)paramRequest).setVirtualResource(content.getResourceBase());
    ((org.semanticwb.portal.api.SWBParamRequestImp)paramRequest).setMode(paramRequest.Mode_VIEW);
    ((org.semanticwb.portal.api.SWBParamRequestImp)paramRequest).setCallMethod(paramRequest.Call_CONTENT);
    content.doView(request, res, paramRequest);
    %>
        
        <%=res.toString()%>
    <%
%>
