<%@page import="java.text.*,java.net.*,org.semanticwb.platform.SemanticObject,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*" %>

    <div class="recentEntry">
    <h2 class="titulo">Lo último en la ciudad digital</h2>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    String webpath=SWBPlatform.getContextPath()+"/swbadmin/jsp/microsite/LastMicrositeElements/";
    String defaultFormat = "dd/MM/yyyy HH:mm";
    SimpleDateFormat iso8601dateFormat = new SimpleDateFormat(defaultFormat);
    ArrayList<MicroSiteElement> elements=(ArrayList<MicroSiteElement>)request.getAttribute("elements");
    if(elements.size()>0)
    {
        for(MicroSiteElement element : elements)
        {
            String src="ico_sound.gif";
            String title=element.getTitle();
            String description=element.getDescription();
            String created=iso8601dateFormat.format(element.getCreated());
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
            //url+="?&act=detail&uri="+URLEncoder.encode(uri);
            %>
              <div class="entry">
              <p><img src="<%=src%>" alt="<%=title%>" width="57" height="55" ></p>
              <h3 class="titulo"><a href="<%=url%>"><%=title%></a></h3>
              <p class="titulo"><%=created%></p>              
              <p><%=description%></p>
              </div>
            <%                        
       }
       if(paramRequest.getWebPage().getWebSite().getWebPage("Lo_ultimo")!=null)
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