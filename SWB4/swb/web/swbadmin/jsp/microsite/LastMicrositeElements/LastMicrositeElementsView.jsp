<%@page import="java.net.URLEncoder,org.semanticwb.*,java.text.*,org.semanticwb.portal.community.*,java.util.*" %>

    <div class="recentEntry">
    <h2 class="titulo">Lo último en la ciudad digital</h2>
<%
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
            String uri=element.getURI();
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
              <h3 class="titulo"><%=title%></h3>
              <p class="titulo"><%=created%></p>
              <p class="vermas"><a href="<%=url%>" >Ver m&aacute;s</a></p>
              <!-- <p><%=description%></p> -->

              </div>
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