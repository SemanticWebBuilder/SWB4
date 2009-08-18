<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.*,java.net.*,org.semanticwb.platform.SemanticObject,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>

<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");    
    String pathIamge = SWBPlatform.getWebWorkPath();
    ArrayList<NewsElement> elements=(ArrayList<NewsElement>)request.getAttribute("elements");
    %>
    <div class="recentEntry_last">
        <h2 class="titulo">Noticias recientes</h2>

    <%
    if(elements.size()>0)
    {
%>
    
        <%
            String defaultFormat = "dd/MM/yyyy HH:mm";
            SimpleDateFormat iso8601dateFormat = new SimpleDateFormat(defaultFormat);
            
            
            for(NewsElement element : elements)
            {
                String created=iso8601dateFormat.format(element.getCreated());
                String href=element.getURL();                
                String src=pathIamge+element.getNewsPicture();
                if(element.getNewsPicture()!=null)
                {
                    src=pathIamge+element.getNewsPicture();
                }
                String title=element.getTitle();
                if(title==null)
                {
                    title="";
                }
                String description=element.getDescription();
                if(description==null)
                {
                    description="";
                }
                if(description.length()>200)
                {
                    description=description.substring(0, 197)+" ...";
                }
                %>
                  <div class="entry">
                      <%
                        if(src!=null)
                        {
                            %>
                            <p><img src="<%=src%>" alt="<%=title%>" width="49" height="49" ></p>
                            <%
                        }                
                      %>
                  
                  <h3 class="titulo"><a href="<%=href%>"><%=title%></a></h3>
                  <p class="titulo"><%=created%></p>
                  <p><%=description%></p>
                </div>
                <%
            }
            if(paramRequest.getWebPage().getWebSite().getWebPage("Ultimas_Noticias")!=null)
            {
                String path=paramRequest.getWebPage().getWebSite().getWebPage("Ultimas_Noticias").getUrl();
                %>
                <p class="vermas"><a href="<%=path%>" >Ver m&aacute;s</a></p>
                <%
            }
    }
    else
        {
        %>
        <p>No hay noticias publicadas</p>
        <%
        }
%></div>