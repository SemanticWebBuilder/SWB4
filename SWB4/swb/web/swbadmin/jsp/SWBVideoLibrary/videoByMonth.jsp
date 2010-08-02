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
            %>
            <div class="bloqueVideos">
                <img src="<%=preview%>" alt="Gobierno Federal Presidencia México Felipe Calderón"/>
                <div class="listadoVideos">
                    <h2>
                    <script type="text/javascript">
                        <!--
                        document.write('Gobierno Federal Presidencia México Felipe Calderón');
                        -->
                    </script></h2>
                    <p>&nbsp;<br/>
                <%
                    if(source!=null)
                    {
                        if(content.getVideoWebPage()==null)
                        {
                            %>
                            Fuente: <%=source%><br/>
                            <%
                        }
                        else
                        {
                            %>
                            Fuente: <a href="<%=content.getVideoWebPage()%>"><%=source%></a><br/>
                            <%
                        }
                    }
                %>

                <%
                    if(date!=null && !date.trim().equals(""))
                    {
                        String ago=SWBUtils.TEXT.getTimeAgo(content.getPublishDate(), usrlanguage);
                        %>
                        <%=date%> - <%=ago%>
                        <%
                    }
                %>

            </p>
            <p>
                <script type="text/javascript">
                    <!--
                    document.write('<%=title%>');
                    -->
                </script>
                    | <a href="<%=url%>">Ver M&aacute;s</a>

            </p>
        </div>
            </div>
            <%
        }
        
    }
%>