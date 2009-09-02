<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.GenericIterator"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Vector"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.model.Descriptiveable"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>
<%@page import="org.semanticwb.model.FormElement"%>
<%@page import="org.semanticwb.portal.SWBFormButton"%>
<%@page import="org.semanticwb.portal.community.*"%>


<%
    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
    SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_VIEW);

    String title="", description="", tags="", gmap="";
    Iterator<SemanticProperty> itProps=semObject.listProperties();
    while(itProps.hasNext()){
        SemanticProperty semProp=itProps.next();
        System.out.println("semProp:"+semProp.getName());
        if(semProp.equals(DirectoryObject.swb_title)) title=mgr.renderElement(request, semProp.getName());
        else if(semProp.equals(DirectoryObject.swb_description)) description=mgr.renderElement(request, semProp.getName());
        else if(semProp.equals("latitude")) gmap=mgr.renderElement(request, semProp.getName());
    }
    SWBResourceURL url = paramRequest.getActionUrl();
    url.setParameter("uri", semObject.getURI());
    url.setAction(url.Action_REMOVE);
    Resource base=paramRequest.getResourceBase();
   %>
   
       <form method="post" action="<%=url%>">
       <table>

       <%
            String path=SWBPlatform.getWorkPath()+base.getWorkPath()+"/"+semObject.getId();
            System.out.println("path:"+path);
            File file=new File(path);
            File[] imgFiles=file.listFiles();
            String[] sImgs=new String[imgFiles.length];
            for(int i=0;i<imgFiles.length;i++){
                System.out.println("entra:"+imgFiles[i].getName());
                //if(imgFiles[i].isFile())
                {
                     sImgs[i]=SWBPlatform.getWebWorkPath()+base.getWorkPath()+"/"+semObject.getId()+"/"+imgFiles[i].getName();
                     //System.out.println("text:"+sImgs[i]);
                }
            }
            for(int i=0;i<sImgs.length;i++){
                System.out.println("contenido:"+sImgs[i]);
                %>
                    <img src="<%=sImgs[i]%>">
                <%
            }
            String imggalery=SWBPortal.UTIL.getGalleryScript(sImgs);
       %>
       <tr>
           <td><%=imggalery%></td>
       </tr>
       <tr>
           <td>Título</td> <td><%=title%></td>
       </tr>
       <tr>
           <td>Descripción</td> <td><%=description%></td>
       </tr>
       <tr>
            <td>Palabras clave</td> <td><%=tags%></td>
       </tr>
       <tr>
           <td>gmap</td> <td><%=gmap%></td>
       </tr>
   <%
        if(paramRequest.getAction().equals(paramRequest.Action_REMOVE))
        {
            %>
               <tr><td colspan="2"><input type="submit" name="delete" value="Borrar"/></td></tr>
            <%
        }
   %>
   </table>
    </form>