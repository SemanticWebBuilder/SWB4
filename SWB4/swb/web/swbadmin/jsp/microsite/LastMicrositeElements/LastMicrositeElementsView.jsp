<%@page import="java.text.*,java.net.*,org.semanticwb.platform.SemanticObject,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*" %>

<%
SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
int columnas=1;

try
{
    columnas=Integer.parseInt(paramRequest.getResourceBase().getAttribute("columns", "1"));
}
catch(Exception e)
{
    
}
if(columnas<=0)
{
    columnas=1;
}
String classAtt="recentEntry";
if(paramRequest.getCallMethod()==paramRequest.Call_CONTENT)
{
    classAtt="panorama";
}
    %>
    <div class="<%=classAtt%>">
    <h1 class="titulo">Lo último en la ciudad digital</h1>
    <%
    
    String webpath=SWBPlatform.getContextPath()+"/swbadmin/jsp/microsite/LastMicrositeElements/";
    String defaultFormat = "dd/MM/yyyy HH:mm";
    SimpleDateFormat iso8601dateFormat = new SimpleDateFormat(defaultFormat);
    ArrayList<MicroSiteElement> elements=(ArrayList<MicroSiteElement>)request.getAttribute("elements");
    if(elements.size()>0)
    {
        %>
        <table width="100%" border="0">
        <%        
        int renglones=elements.size() / columnas;
        if((elements.size() % columnas) !=0 )
        {
            renglones++;
        }        
        MicroSiteElement[] elementsArray=elements.toArray(new MicroSiteElement[elements.size()]);
        for(int iRenglon=0;iRenglon<renglones;iRenglon++)
        {
            %>
            <tr>
            <%
            for(int col=0;col<columnas;col++)
            {
                int iElement=(iRenglon*columnas)+col;
                if(iElement<elementsArray.length)
                {
                    MicroSiteElement element=elementsArray[iElement];
                    String src="ico_sound.gif";
                    String title=element.getTitle();
                    String description=element.getDescription();
                    String created="Sin fecha";
                    if(element.getCreated()!=null)
                    {
                        iso8601dateFormat.format(element.getCreated());
                    }
                    if(description==null)
                    {
                        description="";
                    }
                    if(description.length()>150)
                    {
                        description=description.substring(0, 97)+" ...";
                    }
                    String url=element.getURL();
                    if(element instanceof PostElement)
                    {
                        src="ico_mensaje.gif";
                    }
                    else if(element instanceof VideoElement || element instanceof PhotoElement)
                    {
                        src="ico_foto.gif";
                    }
                    src=webpath+src;

                    %>
                      <td>
                      <div class="entry">
                      <p><img src="<%=src%>" alt="<%=title%>" width="57" height="55" ></p>
                      <h3 class="titulo"><a href="<%=url%>"><%=title%></a></h3>
                      <p class="titulo"><%=created%></p>
                      <p><%=description%></p>
                      </div>
                      </td>
                    <%                    
                }
                else
                {
                    %>
                    <td>&nbsp;</td>
                    <%
                }
            }
            %>
            </tr>
            <%
        }
        %>
        </table>
        <%
       if(paramRequest.getWebPage().getWebSite().getWebPage("Lo_ultimo")!=null && paramRequest.getCallMethod()!=paramRequest.Call_CONTENT)
       {
            String path=paramRequest.getWebPage().getWebSite().getWebPage("Lo_ultimo").getUrl();
            %>
            <p class="vermas"><a href="<%=path%>" >Ver m&aacute;s</a></p>
            <%
        }
}
else
{
        %>
        <p>No hay blogs, videos, fotos, etc, publicados en el sitio</p>
        <%
}

%>

</div>