<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.platform.SemanticObject,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>

<%
    //SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    ArrayList<NewsElement> elements=(ArrayList<NewsElement>)request.getAttribute("elements");
    if(elements.size()>0)
    {
%>
    <div class="recentEntry_last">
        <h2 class="titulo">Noticias recientes</h2>
        <%
            for(NewsElement element : elements)
            {
                String href=null;
                String src=null;
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
                  
                  <h3 class="titulo"><%=title%></h3>
                  <p><%=description%></p>
                  <%
                    if(href!=null)
                    {
                        %>
                        <p class="vermas"><a href="<%=href%>" >Ver m&aacute;s</a></p>
                        <%
                    }
                  %>
                  
                </div>
                <%
            }
        %>
      </div>
<%
    }
%>