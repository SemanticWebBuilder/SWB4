<%@page import="java.net.URLEncoder,org.semanticwb.*,java.text.*,org.semanticwb.portal.community.*,java.util.*" %>
<%
    String webpath=SWBPlatform.getContextPath()+"/swbadmin/jsp/microsite/LastMicrositeElements/";
    String defaultFormat = "dd 'de' MMMM 'del' yyyy 'a las' HH:mm";
    SimpleDateFormat iso8601dateFormat = new SimpleDateFormat(defaultFormat);
    ArrayList<MicroSiteElement> elements=(ArrayList<MicroSiteElement>)request.getAttribute("elements");
    if(elements.size()>0)
    {
    
%>

  <div class="recentEntry">

    <h2 class="titulo">Lo último en la ciudad digital</h2>

    <%
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
            String url=null;
            String uri=null;
            if(element instanceof PostElement)
            {
                PostElement post=(PostElement)element;
                Iterator<Blog> blogs=Blog.listBlogByPostElement(post);
                if(blogs.hasNext())
                {
                    url=blogs.next().getWebPage().getUrl();
                }
                uri=post.getURI();
                src="ico_mensaje.gif";
            }
            else if(element instanceof PhotoElement)
            {
                url=((PhotoElement)element).getPhotoWebPage().getUrl();
                uri=((PhotoElement)element).getURI();
                src="ico_foto.gif";
            }
            else if(element instanceof VideoElement)
            {
                url=((VideoElement)element).getWebPage().getUrl();
                uri=((VideoElement)element).getURI();
                src="ico_foto.gif";
            }
            src=webpath+src;
            if(url!=null && uri!=null)
            {
                url+="?&act=detail&uri="+URLEncoder.encode(uri);
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
        }
        }else
            {
        %>
        <p>No hay blogs, videos, fotos, etc, publicados en el sitio</p>
        <%
        }
    %>
    
  </div>

<%
}
%>