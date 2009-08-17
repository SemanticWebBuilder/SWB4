<%@page import="org.semanticwb.*,java.text.*,org.semanticwb.portal.community.*,java.util.*" %>
<%
    String webpath=SWBPlatform.getContextPath()+"/swbadmin/jsp/microsite/LastMicrositeElements/";
    String defaultFormat = "dd 'de' MMMM 'del' yyyy 'a las' HH:mm";
    SimpleDateFormat iso8601dateFormat = new SimpleDateFormat(defaultFormat);
    ArrayList<MicroSiteElement> elements=(ArrayList<MicroSiteElement>)request.getAttribute("elements");
    if(elements.size()>0)
    {
    
%>

  <div class="recentEntry">
    <h2 class="titulo">Ultimos elementos agregados</h2>

    <%
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
            String url=null;
            if(element instanceof PostElement)
            {
                PostElement post=(PostElement)element;
                if(post.getBlog()!=null)
                {
                    url=post.getBlog().getWebPage().getUrl();
                }
                src="ico_mensaje.gif";
            }
            else if(element instanceof PhotoElement)
            {
                url=((PhotoElement)element).getPhotoWebPage().getUrl();
                src="ico_foto.gif";
            }
            else if(element instanceof VideoElement)
            {
                url=((VideoElement)element).getWebPage().getUrl();
                src="ico_foto.gif";
            }
            src=webpath+src;
            if(url!=null)
            {
                %>
                  <div class="entry">
                  <p><img src="<%=src%>" alt="<%=title%>" width="57" height="55" ></p>
                  <h3 class="titulo"><%=title%> (<%=created%>)</h3>
                  <p><%=description%></p>
                  <p class="vermas"><a href="<%=url%>" >Ver m&aacute;s</a></p>
                  </div>
                <%
            }
        }
    %>
    <!-- <p class="vermas"><a href="#" >Ver m&aacute;s</a></p> -->
  </div>

<%
}
%>