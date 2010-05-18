<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.resources.sem.newslite.*,java.util.*,java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    New onew=(New) request.getAttribute("new");
    String title=onew.getTitle();
    String description=onew.getDescription();
    String body=onew.getBody();
    String author=onew.getAuthor();
    if(author!=null && author.trim().equals(""))
    {
        author=null;
    }
    String source=onew.getAuthor();
    if(source!=null && source.trim().equals(""))
    {
        source=null;
    }
    String imgPhoto = SWBPortal.getContextPath() + "/swbadmin/jsp/SWBNewsLite/sinfoto.png";
    if (onew.getImage() != null)
    {
        int pos = onew.getImage().lastIndexOf("/");
        if (pos != -1)
        {
            String sphoto = onew.getImage().substring(pos + 1);
            onew.setImage(sphoto);
        }
        imgPhoto = SWBPortal.getWebWorkPath() + onew + "/" + onew.getImage();
    }
    %>
    <div class="nota_full">
        <div class="title_new"><%=title%></div>
        <div class="img_new"><img alt="Imagen noticia" src="<%=imgPhoto%>" /></div>
        <%
            if(author!=null)
            {
                %>
                <div class="auhtor_new"><%=author%></div>
                <%
            }
        %>
        <%
            if(source!=null)
            {
                %>
                <div class="source_new"><%=source%></div>
                <%
            }
        %>
        
        <div class="description_new"><%=description%></div>
        <div class="body_new">
            <%=body%>
        </div>
    </div>
    <%
    
%>


