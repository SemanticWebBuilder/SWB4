<%@page import="org.semanticwb.portal.community.*,java.util.*" %>
<%
    ArrayList<MicroSiteElement> elements=(ArrayList<MicroSiteElement>)request.getAttribute("elements");
    if(elements.size()>0)
        {
    
%>
<div id="recentEntries">
  <div class="recentEntry">
    <h2 class="titulo">Lo último</h2>

    <%
        for(MicroSiteElement element : elements)
        {
            String title=element.getTitle();
            String description=element.getDescription();
            if(description==null)
            {
                description="";
            }
            if(description.length()>150)
            {
                description=description.substring(0, 97)+" ...";
            }
            %>
              <div class="entry">
              <p><img src="/swb/work/models/demo2/Template/1/1/images/ico_sound.gif" alt="Nota reciente" width="57" height="55" ></p>
              <h3 class="titulo"><%=title%></h3>
              <p><%=description%></p>
              </div>
            <%
        }
    %>

    <p class="vermas"><a href="#" >Ver m&aacute;s</a></p>
  </div>
</div>
<%
}
%>