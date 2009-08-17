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
            if(element.getSemanticObject().getSemanticClass().equals(PostElement.sclass))
            {
                src="ico_mensaje.gif";
            }
            if(element.getSemanticObject().getSemanticClass().equals(PhotoElement.sclass) || (element.getSemanticObject().getSemanticClass().equals(VideoElement.sclass)))
            {
                src="ico_foto.gif";
            }
            src=webpath+src;
            %>
              <div class="entry">
              <p><img src="<%=src%>" alt="<%=title%>" width="57" height="55" ></p>
              <h3 class="titulo"><%=title%> (<%=created%>)</h3>
              <p><%=description%></p>
              </div>
            <%
        }
    %>
    <!-- <p class="vermas"><a href="#" >Ver m&aacute;s</a></p> -->
  </div>

<%
}
%>